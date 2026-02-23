package io.munib.domain.entity;

import io.munib.domain.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq", sequenceName = "account_seq", allocationSize = 50)
    private Long id;

    @Version
    private long version;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<AccountBalance> balances = new HashSet<>();

    public AccountBalance getOrCreateBalance(Currency currency) {
        return balances.stream().filter(b -> b.getCurrency() == currency).findFirst().orElseGet(() -> {
            AccountBalance created = AccountBalance.builder().account(this).currency(currency).amountMinor(0L).build();
            balances.add(created);
            return created;
        });
    }
}