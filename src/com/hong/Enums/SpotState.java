package com.hong.Enums;

public enum SpotState {
    NONE("   "),
    O(" O "),
    X(" X ");

    public final String value;

    SpotState(String value) {
        this.value = value;
    }
}
