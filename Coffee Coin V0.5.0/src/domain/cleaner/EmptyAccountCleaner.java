package domain.cleaner;

// The purpose of this class is removing accounts with no activity or balance
//
// Objects of this class look for accounts which have a balance of zero and have not been used for more than a specified amount of time.
// All transactions depositing and withdrawing funds from this accounts are translated to new transactions having the same
// final effect, but not using the account in between.
// The recalculated transactions are added to a CleaningBlock which is added to the chain
//
// The CleaningBlock also has information on the necessary rehashing after the transactions will be removed from the chain altogether
// After the required number of confirmations have passed - which includes other miners conforming the rehashing is valid - 
// the now unnecessary transactions are removed from the chain and all hashes are changed to what had been recalculated and confirmed
// After that the recalculated hashes are removed from the CleaningBlock as well
//
// The first transaction in a block is the RewardTransaction; this one can never be targeted, as there is no source before
// the source of this one.

public class EmptyAccountCleaner {

}
