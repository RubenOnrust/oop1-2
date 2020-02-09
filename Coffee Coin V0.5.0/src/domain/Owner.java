package domain;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import util.hash.HasherFactory;
import util.hash.IHashable;

// A class used when a user wants to have a verifiable identity in the chain, for example when 
// sending messages. It should have the following properties:
// 1. A few attributes are always required, uniquely identify the owner, but are never shown or even
//    stored directly in the chain. Instead they are hashed using a global hashing algorithm and key,
//    so someone else cannot just take them, but can check the owner.
// 2. The owner also defines a number of items in the object which - in varying combinations - 
//    identify the owner to someone else. It might be a few phrases to a friend, a social security number
//    combined with the names of the parents to an official, etc.
// 3. Each of these attributes is encrypted using a different private key, which is kept locally by the
//    owner. He/ she can send a special transaction to a recipient containing the public keys of selected
//    attributes and the attribute name. The recipient now can decrypt those attributes to verify the
//    identity.
// 4. That person also receives keys to see selected identifiable information, for example username
//    A new key is generated for each person and each attribute, so the access can be revoked as well.
// 5. In the Owner object, a list of identified other owners is also kept.
// 6. An account can be connected to an owner (not mandatory.) -> Is to be done in this class. 
//    This allows the application to show the sender of coins - or a message - with it instead of only an address
// 7. Optional: if an account has an owner, but that one is not identified, it is still shown that there is
//    one. The user should be able to add labels himself, such as a name. 
// 8. In that case, maybe add functionality to ask for confirmation?
//
// Owners are not stored as separate objects in the chain, but as part of a CreateOwnerTransaction (which also 
// is used to update one.) Neither is the owner of an account stored with an account, as they do not exist
// independently on the chain.
//
// For performance reasons, keep a two-way link between an owner and his accounts
// To think about: how to prevent someone from 'stealing' someones account?
//
// Warning: this class is going to get pretty complicated, as it contains a lot of information which in the
// end will be handled by transactions, not by the object itself. The object will need to be build and
// kept in memory and updated by transactions.This might slow down initialization of the chain
// considerably if not cached somehow.
//
@SuppressWarnings("unused")
public class Owner implements IHashable {
	private static final long serialVersionUID = -2954846916713892980L;
	private transient String username;
	private transient String screenname;
	//
	// Attributes which are not stored as such in the chain, but hashed to ONE long hash
	// The user (owner) stores these in the local data store
	//
	private String fullName;
	private LocalDate dateOfBirth;
	private String streetName; // Current street the owner has as address
	private int houseNumber; // Current number of the house -> if no house numbers are used, put in -1.
	private String zipcode; // Current zipcode. Can be empty.
	private String muncipality; // Current living muncipality. Use the official name!
	private Locale country; // The current living region, country etc.
	
	// Attributes shown to other people who have been granted access
	private transient Map<Owner, Pair<String, String>> usernameAllowed;
	private transient Map<Owner, Pair<String, String>> screennameAllowed;
	
	// Other attributes
	private transient Map<Pair<Owner,IHashable>, Pair<String, String>> customAttributes; //Attributes set by the owner
	private transient Set<Owner> validatedOwners; // Owners identified and validated by this owner
	private transient Set<Account> accounts; // Accounts associated with this owner
	
	public Owner(String fullName, LocalDate dateOfBirth, String streetName, int houseNumber, String zipcode,
			String muncipality, Locale country, String username, String screenname) {
		super();
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.streetName = streetName;
		this.houseNumber = houseNumber;
		this.zipcode = zipcode;
		this.muncipality = muncipality;
		this.country = country;
		this.username = username;
		this.screenname = screenname;
		this.usernameAllowed = new HashMap<Owner, Pair<String, String>>();
		this.screennameAllowed = new HashMap<Owner, Pair<String, String>>();
		this.customAttributes = new HashMap<Pair<Owner,IHashable>, Pair<String, String>>();
		this.validatedOwners = new HashSet<Owner>();
		this.accounts = new HashSet<Account>();
	}
	
	@Override
	public byte[] hash() throws NoSuchAlgorithmException {
		return HasherFactory.hasher().hash(this);
	}
	
	@Override
	public boolean equals(IHashable other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		Owner theOther = (Owner) other;
		// Checks for null values done
			
		BigInteger hashSelf, hashOther;
		try {
			hashSelf = new BigInteger(this.hash());
			hashOther = new BigInteger(theOther.hash());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Oeps. Crypto algorithm unknown.");
		}
		return hashSelf.equals(hashOther);
	}

	private class Pair<T, S> {
		private final T key;
		private final S value;
		
		public Pair(T key, S value) {
			super();
			this.key = key;
			this.value = value;
		}
		
		public T getKey() {
			return key;
		}
		
		public S getValue() {
			return value;
		}
	}
	
}
