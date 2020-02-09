package domain.cleaner;
//
// Other threads in this package will disturb the time-ordering of the transactions,
// which might slow down applications using that considerably. The purpose
// of this class is to examine the chain, create new sets of blocks with
// transactions in the correct order and where they should go and what blocks
// and transactions to delete. It also contains the new hashes.
//
// After the required number of comfirmations, the OrderingBlock is executed to give a new chain
//
public class ChainOrderer {

}
