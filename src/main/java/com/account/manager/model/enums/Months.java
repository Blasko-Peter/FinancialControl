package com.account.manager.model.enums;

public enum Months {

    january(1),
    february(2),
    march(3),
    april(4),
    may(5),
    june(6),
    july(7),
    august(8),
    september(9),
    october(10),
    november(11),
    december(12);

    private Integer id;

    Months(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    public static Months fromId(Integer id) {
        for (Months month : Months.values()) {
            if (month.getId().equals(id)) {
                return month;
            }
        }
        return null;
    }

}