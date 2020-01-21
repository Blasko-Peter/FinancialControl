package com.account.manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "items")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long id;
    private LocalDate actualDate;
    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;
    private String place;
    private String city;
    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;
    private BigDecimal charging;
    private BigDecimal crediting;
    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long newId) {
        this.id = newId;
    }

    public LocalDate getActualDate() {
        return actualDate;
    }

    public void setActualDate(LocalDate newActualDate) {
        this.actualDate = newActualDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account newAccount) {
        this.account = newAccount;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String newPlace) {
        this.place = newPlace;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String newCity) {
        this.city = newCity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category newCategory) {
        this.category = newCategory;
    }

    public BigDecimal getCharging() {
        return charging;
    }

    public void setCharging(BigDecimal newCharging) {
        this.charging = newCharging;
    }

    public BigDecimal getCrediting() {
        return crediting;
    }

    public void setCrediting(BigDecimal newCrediting) {
        this.crediting = newCrediting;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String newComment) {
        this.comment = newComment;
    }

}
