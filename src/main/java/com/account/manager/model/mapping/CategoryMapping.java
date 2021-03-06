package com.account.manager.model.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryMapping {

    private String name;
    private String type;
    private boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getType() {
        return type;
    }

    public void setType(String newType) {
        this.type = newType;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsAvtive(boolean newIsActive) {
        this.isActive = newIsActive;
    }

}
