package com.account.manager.model.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemMapping {

    private LocalDate actualDate;
    private long accountId;
    private String place;
    private String city;
    private long categoryId;
    private BigDecimal charging;
    private BigDecimal crediting;
    private String comment;

    public LocalDate getActualDate() {
        return actualDate;
    }

    public void setActualDate(LocalDate newActualDate) {
        this.actualDate = newActualDate;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long newAccountId) {
        this.accountId = newAccountId;
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

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long newCategoryId) {
        this.categoryId = newCategoryId;
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
