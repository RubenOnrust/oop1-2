package domain.cleaner;
//
// The purpose of this class is finding blocks with less than X transactions in them,
// creating new blocks combining them so the new blocks have Y transactions each,
// and offering this as a new CompactingBlock to the chain.
//
// The CompactingBlock tells what blocks to remove, what new blocks are going to be put
// in and what the new hashing is. As CompactingBlock is a subclass of OrderingBlock, it also contains 
// where the new blocks go in order to keep the best time-order, 
//
// After the required number of comfirmations, the CompactingBlock is executed to give a new chain
//
public class ChainCompacter {

}
