package domain.cleaner;

// The purpose of this class is removing blocks having as the only transaction
// a RewardTransaction, where the account has been empty for more than the specified
// amount of time. That can be because the owner transferred the funds or because
// the InactiveAccountCleaner targeted it.
// The other transactions might not have existed or been removed by the EmptyAccountCleaner.
//
// A special transaction is created indicating which block will be removed, and put in a 
// CleaningBlock which will also deal with the rehashing (see EmptyAccountCleaner for details.)
//
// NOTE: proofs of excellence already found need to be kept, in order to check for duplicates!

public class EmptyBlockCleaner {

}
