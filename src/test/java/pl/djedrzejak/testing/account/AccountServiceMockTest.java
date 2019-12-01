package pl.djedrzejak.testing.account;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class AccountServiceMockTest {

	@Test
	void getAllActiveAccounts() {
		//given
		List<Account> accounts = prepareAccountData();
		AccountRepository accountRepository = mock(AccountRepository.class);
		AccountService accountService = new AccountService(accountRepository);
		given(accountRepository.getAllAccounts()).willReturn(accounts);
		
		//when
		List<Account> activeAaccounts = accountService.getAllActiveAccounts();
		
		//then
		assertThat(activeAaccounts, hasSize(2));
	}
	
	@Test
	void getNoActiveAccounts() {
		//given
		AccountRepository accountRepository = mock(AccountRepository.class);
		AccountService accountService = new AccountService(accountRepository);
		given(accountRepository.getAllAccounts()).willReturn(Collections.emptyList());
		
		//when
		List<Account> activeAaccounts = accountService.getAllActiveAccounts();
		
		//then
		assertThat(activeAaccounts, hasSize(0));
	}
	
	@Test
	void getAccountsByName() {
		//given
		AccountRepository accountRepository = mock(AccountRepository.class);
		AccountService accountService = new AccountService(accountRepository);
		given(accountRepository.getByName("John")).willReturn(Collections.singletonList("Doe"));
		
		//when
		List<String> accountNames = accountService.findByName("John");
		
		//then
		assertThat(accountNames.get(0), equalTo("Doe"));
	}
	
	private List<Account> prepareAccountData() {
		Address address1 = new Address("Kościuszki", "15b");
		Address address2 = new Address("Opolska", "4");
		
		Account account1 = new Account(address1);
		Account account2 = new Account();
		Account account3 = new Account(address2);
		
		return Arrays.asList(account1, account2, account3);
	}
	
}
