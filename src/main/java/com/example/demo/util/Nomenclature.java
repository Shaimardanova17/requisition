package com.example.demo.util;

import java.util.Set;

public class Nomenclature {

    private final String code;
    private final String name;
    private final Set<String> allowedUnits;

    public Nomenclature(String code, String name, Set<String> allowedUnits) {
        this.code = code;
        this.name = name;
        this.allowedUnits = allowedUnits;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Set<String> getAllowedUnits() {
        return allowedUnits;
    }
}
