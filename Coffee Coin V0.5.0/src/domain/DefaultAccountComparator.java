package domain;

import java.util.Comparator;

public class DefaultAccountComparator implements Comparator<Account> {

	@Override
	public int compare(Account self, Account other) {
		return self.getTimestamp().compareTo(other.getTimestamp());
	}
}
