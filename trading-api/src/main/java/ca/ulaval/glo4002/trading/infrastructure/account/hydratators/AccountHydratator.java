package ca.ulaval.glo4002.trading.infrastructure.account.hydratators;

import ca.ulaval.glo4002.trading.domain.account.Account;
import ca.ulaval.glo4002.trading.domain.account.AccountNumber;
import ca.ulaval.glo4002.trading.domain.account.dividend.DividendPayment;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedAccount;
import ca.ulaval.glo4002.trading.infrastructure.account.entities.PersistedDividendPayment;

import java.util.ArrayList;
import java.util.List;

public class AccountHydratator {

    private BalanceHistoryHydratator balanceHistoryHydratator;
    private InvestorHydratator investorHydratator;
    private DividendPaymentHydratator dividendPaymentHydratator;
    private WalletHydratator walletHydratator;

    public AccountHydratator() {
        this.investorHydratator = new InvestorHydratator();
        this.walletHydratator = new WalletHydratator();
        this.balanceHistoryHydratator = new BalanceHistoryHydratator();
        this.dividendPaymentHydratator = new DividendPaymentHydratator();
    }

    public PersistedAccount dehydrate(Account account) {
        PersistedAccount persistedAccount = new PersistedAccount();
        persistedAccount.setAccountNumber(account.getAccountNumber().getValue());
        persistedAccount.setBalanceHistory(balanceHistoryHydratator.dehydrate(account.getBalanceHistory()));
        persistedAccount.setInvestor(investorHydratator.dehydrate(account.getInvestor()));
        persistedAccount.setWallet(walletHydratator.dehydrate(account.getWallet()));
        List<PersistedDividendPayment> persistedDividendPayments = new ArrayList<>();
        for (DividendPayment dividendPayment : account.getDividendPayments()) {
            persistedDividendPayments.add(dividendPaymentHydratator.dehydrate(dividendPayment));
        }
        persistedAccount.setPersistedDividendPayments(persistedDividendPayments);
        return persistedAccount;
    }

    public Account hydrate(PersistedAccount persistedAccount) {
        Account account = new Account();
        account.setAccountNumber(new AccountNumber(persistedAccount.getAccountNumber()));
        account.setInvestor(investorHydratator.hydrate(persistedAccount.getInvestor()));
        account.setWallet(walletHydratator.hydrate(persistedAccount.getWallet()));
        account.setBalanceHistory(balanceHistoryHydratator.hydrate(persistedAccount.getBalanceHistory()));
        List<DividendPayment> dividendPayments = new ArrayList<>();
        for (PersistedDividendPayment persistedDividendPayment : persistedAccount.getPersistedDividendPayments()) {
            DividendPayment dividendPayment = dividendPaymentHydratator.hydrate(persistedDividendPayment);
            dividendPayments.add(dividendPayment);
        }
        account.setDividendPayments(dividendPayments);
        return account;
    }

    public PersistedAccount update(Account account, PersistedAccount persistedAccount) {
        persistedAccount.setAccountNumber(account.getAccountNumber().getValue());
        persistedAccount.setInvestor(investorHydratator.update(account.getInvestor(), persistedAccount.getInvestor()));
        persistedAccount.setWallet(walletHydratator.update(account.getWallet(), persistedAccount.getWallet()));
        persistedAccount.setBalanceHistory(balanceHistoryHydratator.update(account.getBalanceHistory(), persistedAccount.getBalanceHistory()));
        List<DividendPayment> dividendPayments = account.getDividendPayments();
        List<PersistedDividendPayment> persistedDividendPayments = persistedAccount.getPersistedDividendPayments();
        List<DividendPayment> rehydratedDividendPayments = new ArrayList<>();
        for (PersistedDividendPayment persistedDividendPayment : persistedDividendPayments) {
            rehydratedDividendPayments.add(dividendPaymentHydratator.hydrate(persistedDividendPayment));
        }
        dividendPayments.removeAll(rehydratedDividendPayments);
        for (DividendPayment dividendPayment : dividendPayments) {
            persistedDividendPayments.add(dividendPaymentHydratator.dehydrate(dividendPayment));
        }
        persistedAccount.setPersistedDividendPayments(persistedDividendPayments);
        return persistedAccount;
    }

}
