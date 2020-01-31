package com.account.manager.model.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IdMapping {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long newId) {
        this.id = newId;
    }

}
