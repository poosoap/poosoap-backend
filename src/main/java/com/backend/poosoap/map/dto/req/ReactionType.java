package com.backend.poosoap.map.dto.req;

public enum ReactionType {
    LIKE(1),
    UNLIKE(-1),
    LOVE(1),
    HATE(-1);

    private final int value;

    ReactionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
