package io.munib.domain.entity;

import io.munib.domain.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account_balances", uniqueConstraints = @UniqueConstraint(name = "uk_account_currency", columnNames = {"account_id", "currency"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acct_bal_seq")
    @SequenceGenerator(name = "acct_bal_seq", sequenceName = "acct_bal_seq", allocationSize = 50)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private Currency currency;

    /*
        long, to enforce that it is not null, default to 0
        named minor to say that we'll store the smallest unit of currency
        i.e. cent; 100 means 1 EUR or 1 USD etc. 120 means 1 EUR/USD and 20 cent
    */
    @Column(nullable = false)
    private long amountMinor;
}