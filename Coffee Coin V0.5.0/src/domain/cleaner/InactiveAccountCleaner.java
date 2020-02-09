package domain.cleaner;

// The purpose of this class is removing accounts which have been inactive for more than a specified
// amount of time. 
//
// Objects of this class look for accounts which have not been active for too long.
// Accounts are considered inactive if both of the following criteria have been met:
// 1. No transactions have been done for more than a specified amount of time
// 2a. The account is connected to an Owner in the chain AND 
// 2b. this owner has not done any transactions with any account for a specified amount of time
//
// If an account meets those criteria, a transactions is created where the funds are transferred to a special
// account, and added to a CleaningBlock.
// The owner of the account now has until the EmptyAccountCleaner removes the account, for which purposes
// the timer starts at the moment of emptying this account, to add a transaction transferring the funds back.
// If that is not done, the funds become inaccessible. 
//
// Accounts who were paid by a RewardTransaction can be the target of this code, making the account empty.
// They will not be removed by the EmptyAccountCleaner, but by the EmptyBlockCleaner.

public class InactiveAccountCleaner {

}
