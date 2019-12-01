package pl.djedrzejak.testing.account;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

class AddressTest {

	@ParameterizedTest
	@CsvSource({"Ulica, 10", "inna, 22/11", "kolejna, 21d"})
	void givenAddressesShoulNotBeEmptyAndHaveProperNames(String street, String number) {
		assertThat(street, notNullValue());
		assertThat(street, containsString("a"));
		assertThat(number, notNullValue());
		assertThat(number.length(), lessThan(8));
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/addresses.csv")
	void addressesShoulNotBeEmptyAndHaveProperNames(String street, String number) {
		assertThat(street, notNullValue());
		assertThat(street, containsString("a"));
		assertThat(number, notNullValue());
		assertThat(number.length(), lessThan(8));
	}

}
