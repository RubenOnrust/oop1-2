package domain.cleaner;
//
// The purpose of this class is to set a colaterral in the chain to get the right to start a compacting operation.
// The higher the amount set, the more time the one doing this has to finish the job.
// If the job is not finished by the deadline, the colatteral is lost (transferred to the account where
// inactives funds go.)
// Only one colatteral can be active at any given time, so people do not interfere
// During the time 'bought', the compacter adds CleaningBlocks to the chain where necessary.
// These can have RewardTransactions with Rewards of zero (very easy proofs)
// Once finished wth the required calculations, either a CompactingBlock or an OrderingBlock
// is offered to the chain, having a special RewardsTransaction in them. 
// If this block is accepted, the colatteral is returned.
//
// The reward for the cleaning and compacting is calculated based on improvement in the compactness
// of the chain, not the number of changes needed.
//
public class CompacterTransaction {

}
