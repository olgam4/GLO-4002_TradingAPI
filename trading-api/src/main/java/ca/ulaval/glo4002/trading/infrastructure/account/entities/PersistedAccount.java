package ca.ulaval.glo4002.trading.infrastructure.account.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class PersistedAccount extends PersistedBaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private PersistedInvestor investor;
    @Column
    private String accountNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private PersistedWallet wallet;
    @OneToOne(cascade = CascadeType.ALL)
    private PersistedBalanceHistory balanceHistory;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PersistedDividendPayment> persistedDividendPayments;

    public PersistedInvestor getInvestor() {
        return investor;
    }

    public void setInvestor(PersistedInvestor investor) {
        this.investor = investor;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public PersistedWallet getWallet() {
        return wallet;
    }

    public void setWallet(PersistedWallet wallet) {
        this.wallet = wallet;
    }

    public PersistedBalanceHistory getBalanceHistory() {
        return balanceHistory;
    }

    public void setBalanceHistory(PersistedBalanceHistory balanceHistory) {
        this.balanceHistory = balanceHistory;
    }

    public List<PersistedDividendPayment> getPersistedDividendPayments() {
        return persistedDividendPayments;
    }

    public void setPersistedDividendPayments(List<PersistedDividendPayment> persistedDividendPayments) {
        this.persistedDividendPayments = persistedDividendPayments;
    }

}
