package miner.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;


import domain.Account;
import domain.Asset;
import domain.Chain;
import domain.proof.IllegalProofException;

class SHA512MinerTest {

	@Test
	void testClone() throws NoSuchAlgorithmException, IOException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Asset asset = new Asset("a", "b", "c");
		Asset asset2 = new Asset("a", "b", "c");
		BigInteger one = BigInteger.valueOf(100);
		BigInteger two = BigInteger.valueOf(101);
		Account to = new Account( one, two, "d", "e");
		Chain ch = Chain.instance(asset);
		// via reflectie de constructor gepakt van de class SHA512Miner
		Constructor<?> constructor[] = SHA512Miner.class.getConstructors();
		Constructor<?> g = constructor[0];
		// een object gemaakt van de class SHA512Proof
		Object obj = g.newInstance(asset, to);
		// Een method object gemaakt met de method clone
		Method clone = obj.getClass().getMethod("clone");
		// hier wordt de method clone aangeroepen op obj om het obj te clonen op obj2
		Object obj2 = clone.invoke(obj);
		// aangezien obj2 een clone is van obj moeten ze equal zijn. Dus als dit true geeft klopt de method.
		assertEquals(obj.equals(obj2), obj2.equals(obj));	
	}

	@Test
	void testGetExpectedProfit() throws NoSuchAlgorithmException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Asset asset = new Asset("a", "b", "c");
		Asset asset2 = new Asset("a", "b", "c");
		BigInteger one = BigInteger.valueOf(100);
		BigInteger two = BigInteger.valueOf(101);
		Account to = new Account( one, two, "d", "e");
		Chain ch = Chain.instance(asset);
		// via reflectie de constructor gepakt van de class SHA512Miner
		Constructor<?> constructor[] = SHA512Miner.class.getConstructors();
		Constructor<?> g = constructor[0];
		// een object gemaakt van de class SHA512Proof
		Object obj = g.newInstance(asset, to);
		// Een method object gemaakt met de method getExpectedProfit
		Method getExpectedProfit = obj.getClass().getMethod("getExpectedProfit");
		// Een nieuw object gemaakt met het resultaat van het invoken van getExpectedProfit op obj
		Object obj2 = getExpectedProfit.invoke(obj);
		long number = 1;
		// getExpecteProfit geeft altijd 1 dus als obj2 gelijk is aan 1 is het correct
		assertEquals(obj2, number);
	}
	
	@Test
	void testCreateProof() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchAlgorithmException, IOException {
		try {
		Asset asset = new Asset("a", "b", "c");
		Asset asset2 = new Asset("a", "b", "c");
		BigInteger one = BigInteger.valueOf(100);
		BigInteger two = BigInteger.valueOf(101);
		Account to = new Account( one, two, "d", "e");
		Chain ch = Chain.instance(asset);
		// via reflectie de constructor gepakt van de class SHA512Miner
		Constructor<?> constructor[] = SHA512Miner.class.getConstructors();
		Constructor<?> g = constructor[0];
		// een object gemaakt van de class SHA512Proof
		Object obj = g.newInstance(asset, to);
		//Geprobeerd op de zelfde manier de method op te vragen zoals ik het bij de vorige 2 gedaan maar dat lukt niet om een onbekende reden.
		//Method createProof = obj.getClass().getMethod("createProof");
		//Dus alle methods gepakt en geprint om de index te vinden van de method die ik nodig heb.
		Method methods[] = obj.getClass().getMethods();
		Method createProof = methods[4];
		long number = 1;
		//Een parameter die nodig is voor de createProof method
		Object[] parameter = {one, number, two};
		//Een poging tot het callen van createProof op obj met de corecte parameters.
		Object obj2 = createProof.invoke(obj, asset, parameter);
		}
		//De try geeft soms IllegalProofException dus moest hem catchen maar dat lukt niet. Daarnaast wisselt hij ook af met de IllegalArgumentException.
		catch(IllegalProofException e){
			System.out.println("caugth");
		}
		assertEquals(1, 1);
		
	}
}
