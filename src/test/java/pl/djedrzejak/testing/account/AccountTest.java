package pl.djedrzejak.testing.account;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import pl.djedrzejak.testing.account.Account;
import pl.djedrzejak.testing.account.Address;

class AccountTest {

	@Test
	void newAccountShouldNotBeActiveAfterCreation() {
		//given
		Account newAccount = new Account();
		
		//then
		assertFalse(newAccount.isActive());
		MatcherAssert.assertThat(newAccount.isActive(), equalTo(false));
	}
	
	@Test
	void acountShouldBeActiveAfterActivation() {
		//given
		Account newAccount = new Account();

		//when
		newAccount.activate();
		
		//then
		assertTrue(newAccount.isActive());
		MatcherAssert.assertThat(newAccount.isActive(), is(true));
	}
	
	@Test
	void newlyCreatedAccountShouldNotHaveDefaultDeliveryAddress() {
		//given
		Account account = new Account();
		
		//when
		Address address = account.getDefaultDeliveryAddress();
		
		//then
		assertNull(address);
		MatcherAssert.assertThat(address, nullValue());
	}
	
	@Test
	void defaultDeliveryAddressShoulNotBeNullAfterBeingSet() {
		//given
		Address address = new Address("Ulica", "32b");
		Account account = new Account();
		account.setDefaultDeliveryAddress(address);
		
		//then
		assertNotNull(account.getDefaultDeliveryAddress());
		MatcherAssert.assertThat(account.getDefaultDeliveryAddress(), is(notNullValue()));
	}
	
	@RepeatedTest(10)
	void newAccountWithNotNUllAddressShouldBeActive() {
		//given
		Address address = new Address("ulica", "10/2");
		
		//when
		Account account = new Account(address);
		
		//then
		assumingThat(address != null, () -> {
			assertTrue(account.isActive());
		});
	}
	
}
