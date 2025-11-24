package model;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    private User owner;

    @Column(name = "balance")
    private double balance;

    protected Account() {}

    public Account(User owner) {
        this.owner = owner;
        this.balance = 0.0;
    }

    public Account(int accountId, User owner, double balance) {
        this.accountId = accountId;
        this.owner = owner;
        this.balance = balance;
    }

    public int getAccountId() { return accountId; }
    public User getOwner() { return owner; }
    public double getBalance() { return balance; }

    public void credit(double amount) {
        this.balance += amount;
    }

    public void debit(double amount) {
        this.balance -= amount;
    }
}