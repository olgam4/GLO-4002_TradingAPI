package ca.ulaval.glo4002.trading.application.account;

import ca.ulaval.glo4002.trading.application.ServiceLocator;
import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.AccountRepository;
import ca.ulaval.glo4002.trading.domain.account.creditBalance.Credits;
import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountAlreadyOpenException;
import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorType;
import ca.ulaval.glo4002.trading.domain.money.Currency;
import ca.ulaval.glo4002.trading.domain.money.CurrencyExchanger;
import ca.ulaval.glo4002.trading.domain.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountApplicationServiceTest {

    private static final AccountNumber ACCOUNT_NUMBER = new AccountNumber("JG-12");
    private static final Money VALID_MONEY_USD = new Money(5f, Currency.USD);
    private static final List<Money> MONIES = new ArrayList<>();
    private static final List<CreditDTO> CREDIT_DTOS = new ArrayList<>();
    private static final InvestorId INVESTOR_ID = new InvestorId(87L);
    private static final String INVESTOR_NAME = "Chesnaught Tynamo";
    private static final InvestorType INVESTOR_TYPE = InvestorType.CONSERVATIVE;
    private static final ArrayList INVESTOR_FOCUS_AREAS = new ArrayList();

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Credits credits;

    @Mock
    private Account account;

    @Mock
    private CurrencyExchanger currencyExchanger;

    private AccountApplicationService accountApplicationService;
    private AccountDTO accountDTO = new AccountDTO();
    private AccountDomainAssembler accountDomainAssembler = new AccountDomainAssembler();
    private CreditDTO creditDTO = new CreditDTO();
    private CreditDomainAssembler creditDomainAssembler = new CreditDomainAssembler();

    @Before
    public void setUp() {
        ServiceLocator.reset();
        ServiceLocator.register(CurrencyExchanger.class, currencyExchanger);
        setUpAccount();
        setUpAccountDTO();
        setUpCredits();
        setUpCreditsDTO();
        accountApplicationService = new AccountApplicationService(accountRepository, accountDomainAssembler);
        willReturn(false).given(accountRepository).isExistingInvestor(INVESTOR_ID);
        willReturn(account).given(accountRepository).findByAccountNumber(ACCOUNT_NUMBER);
    }

    @Test
    public void givenAnAccountRepositoryContainingAnAccount_whenRetrievingIt_thenDTOIsReturned() {
        willReturn(account).given(accountRepository).findByAccountNumber(ACCOUNT_NUMBER);
        AccountDTO accountReturned = accountApplicationService.getByAccountNumber(ACCOUNT_NUMBER);
        assertEquals(ACCOUNT_NUMBER, accountReturned.getAccountNumber());
    }

    @Test(expected = AccountNotFoundException.class)
    public void givenAnUnexistingAccountId_whenRetrievingTheAccount_thenThrows() {
        AccountNotFoundException exception = new AccountNotFoundException(ACCOUNT_NUMBER);
        willThrow(exception).given(accountRepository).findByAccountNumber(ACCOUNT_NUMBER);
        accountApplicationService.getByAccountNumber(ACCOUNT_NUMBER);
    }

    @Test
    public void whenCreatingAccount_thenAccountIsSaved() {
        accountApplicationService.createAccount(accountDTO);
        verify(accountRepository).save(any(Account.class));
    }

    @Test(expected = AccountAlreadyOpenException.class)
    public void givenAnExistingInvestor_whenCreatingAnAccountForThatInvestor_thenThrows() {
        willReturn(true).given(accountRepository).isExistingInvestor(INVESTOR_ID);
        accountApplicationService.createAccount(accountDTO);
    }

    private void setUpAccount() {
        willReturn(ACCOUNT_NUMBER).given(account).getAccountNumber();
        willReturn(INVESTOR_ID).given(account).getInvestorId();
        willReturn(INVESTOR_TYPE).given(account).getInvestorType();
        willReturn(INVESTOR_FOCUS_AREAS).given(account).getFocusAreas();
        willReturn(credits).given(account).getBalance();
        willReturn(VALID_MONEY_USD).given(account).getTotalBalance();
    }

    private void setUpAccountDTO() {
        accountDTO.setAccountNumber(ACCOUNT_NUMBER);
        accountDTO.setInvestorId(INVESTOR_ID);
        accountDTO.setInvestorName(INVESTOR_NAME);
        accountDTO.setCredits(creditDomainAssembler.from(credits));
    }

    private void setUpCredits() {
        willReturn(VALID_MONEY_USD).given(credits).getBy(VALID_MONEY_USD.getCurrency());
        willReturn(new HashMap<>()).given(credits).getMoneyByCurrency();
    }

    private void setUpCreditsDTO() {
        creditDTO.setAmount(VALID_MONEY_USD);
        creditDTO.setCurrency(VALID_MONEY_USD.getCurrency().toString());
    }

}