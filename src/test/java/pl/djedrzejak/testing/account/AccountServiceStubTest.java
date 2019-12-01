package pl.djedrzejak.testing.account;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class AccountServiceStubTest {

	@Test
	void getAllActiveAccounts() {
		//given
		AccountRepository accountRepositoryStub = new AccountRepositoryStub();
		AccountService accountService = new AccountService(accountRepositoryStub);
		
		//when
		List<Account> accounts = accountService.getAllActiveAccounts();
		
		//then
		assertThat(accounts, hasSize(2));
	}

}
