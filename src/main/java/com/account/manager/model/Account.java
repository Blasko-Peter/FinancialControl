package com.account.manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId")
    private long id;
    private String name;
    private boolean active;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId", referencedColumnName = "accountId")
    private List<Item> items;
    private BigDecimal actualBalance;

    public long getId() {
        return id;
    }

    public void setId(long newId) {
        this.id = newId;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean newActive) {
        this.active = newActive;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> newItems) {
        this.items = newItems;
    }

    public BigDecimal getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(BigDecimal newActualBalance) {
        this.actualBalance = newActualBalance;
    }

}
