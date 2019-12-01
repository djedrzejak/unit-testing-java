package pl.djedrzejak.testing.account;

import java.util.Arrays;
import java.util.List;

public class AccountRepositoryStub implements AccountRepository {

	@Override
	public List<Account> getAllAccounts() {
		Address address1 = new Address("Kościuszki", "15b");
		Address address2 = new Address("Opolska", "4");
		
		Account account1 = new Account(address1);
		Account account2 = new Account();
		Account account3 = new Account(address2);
		
		return Arrays.asList(account1, account2, account3);
	}

	@Override
	public List<String> getByName(String name) {
		return null;
	}

}
