package ca.ulaval.glo4002.trading.infrastructure.account;

import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.domain.account.investor.InvestorId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryCustomAccountRepositoryTest {

    private static final AccountNumber ACCOUNT_NUMBER = new AccountNumber("EC-565654");
    private static final InvestorId INVESTOR_ID = new InvestorId(1343L);

    private final InMemoryCustomAccountRepository inMemoryCustomAccountRepository = new InMemoryCustomAccountRepository();

    @Mock
    private Account account;

    @Before
    public void setUp() {
        willReturn(ACCOUNT_NUMBER).given(account).getAccountNumber();
    }

    @Test
    public void whenSaving_thenAccountIsInRepository() {
        inMemoryCustomAccountRepository.save(account);
        assertFalse(inMemoryCustomAccountRepository.isEmpty());
    }

    @Test
    public void givenAnExistingId_whenDeleting_thenAccountRemoved() {
        inMemoryCustomAccountRepository.save(account);
        inMemoryCustomAccountRepository.deleteByAccountNumber(ACCOUNT_NUMBER);
        assertTrue(inMemoryCustomAccountRepository.isEmpty());
    }

    @Test
    public void givenAnId_whenRetrieving_thenCorrectAccountRetrieved() {
        inMemoryCustomAccountRepository.save(account);
        Account theAccount = inMemoryCustomAccountRepository.findByAccountNumber(ACCOUNT_NUMBER);
        assertEquals(account, theAccount);
    }

    @Test(expected = AccountNotFoundException.class)
    public void givenIdNotInRepository_whenRetrieving_thenThrows() {
        inMemoryCustomAccountRepository.findByAccountNumber(ACCOUNT_NUMBER);
    }

    @Test
    public void whenCheckingIfExistingInvestorIsPersisted_thenInvestorIsInRepository() {
        willReturn(INVESTOR_ID).given(account).getInvestorId();
        inMemoryCustomAccountRepository.save(account);
        assertTrue(inMemoryCustomAccountRepository.isExistingInvestor(INVESTOR_ID));
    }

    @Test
    public void whenCheckingIfUnexistingInvestorIsPersisted_thenInvestorIsNotInRepository() {
        willReturn(INVESTOR_ID).given(account).getInvestorId();
        assertFalse(inMemoryCustomAccountRepository.isExistingInvestor(INVESTOR_ID));
    }

}