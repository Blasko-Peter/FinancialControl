package com.account.manager.model;

public enum Types {

    Charging(0),
    Crediting(1);

    private Integer id;

    Types(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    public static Types fromId(Integer id) {
        for (Types type : Types.values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }

}
