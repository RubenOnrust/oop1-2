package domain.factory;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import domain.Account;
import domain.Asset;
import domain.MessageTransaction;
import domain.Transaction;
import domain.TransactionPool;
import domain.Wallet;
import domain.factory.AccountFactory;

public class TransactionGenerator {
	@SuppressWarnings("unused")
	private Thread innerThread;
	private Wallet wallet;
	private volatile boolean running;
	
	public TransactionGenerator() {
		Thread innerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				doWork();
			}
			
		});
		wallet = Wallet.instance();
		running = true;
		innerThread.start();
	}
	
	public boolean isRunning() {
		return running;
	}

	public void stop() {
		this.running = false;
	}

	@SuppressWarnings("deprecation")
	private synchronized void doWork() {
		Random random = new Random();
		TransactionPool pool = TransactionPool.instance();
		while (running) {
			Account from = Account.getNewAssetSource();
			Account to = from;
			long balance = Long.MIN_VALUE;
			long amount = Long.MAX_VALUE;
			long fee = 0;
			while ((from == to) || (balance < amount + fee)) {
				from = AccountFactory.instance().get((int) (random.nextDouble() * 7));
				
				// Calculate balance, including transactions still in the pool
				balance = from.getBalance().longValue();
				List<Transaction> pending = TransactionPool.instance().peek();
				for (Transaction t: pending) {
					if (t.getFrom().equals(from)) {
						balance -= t.getAmount().longValue();
						balance -= t.getAmountProcessing().longValue();
					}
				}
						
				to = wallet.get((int) (Math.random() * wallet.size()));
				amount = (long) (Math.random() * 50) + 2;
				fee = (amount / 100) + 1;
				if (fee >= amount) {
					fee = 1;
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Transaction transaction;
				if ((amount >= 10) && (amount <=20)) {
					transaction = new MessageTransaction(Asset.getDefault(), new BigInteger(new Long(amount).toString()), from, to, new BigInteger(new Long(fee).toString()), "A message to send", null);
				}
				else {
					transaction = new Transaction(Asset.getDefault(), new BigInteger(new Long(amount).toString()), from, to, new BigInteger(new Long(fee).toString()));
				}
				pool.queue(transaction);
				Thread.sleep((long) (Math.random() * 1000) + 1);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
