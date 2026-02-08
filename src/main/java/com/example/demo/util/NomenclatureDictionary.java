package com.example.demo.util;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class NomenclatureDictionary {

    private final Map<String, Nomenclature> data = new HashMap<>();

    @PostConstruct
    public void init() {

        data.put(
                "062010.100.000000",
                new Nomenclature(
                        "062010.100.000000",
                        "Газ природный",
                        new HashSet<>(Arrays.asList("Литр"))
                )
        );

        data.put(
                "329911.900.000031",
                new Nomenclature(
                        "329911.900.000031",
                        "Полумаска",
                        new HashSet<>(Arrays.asList("Штука"))
                )
        );
        data.put(
                "271222.900.000015",
                new Nomenclature(
                        "271222.900.000015",
                        "Выключатель",
                        new HashSet<>(Arrays.asList("Штука"))
                )
        );
    }

    public Nomenclature get(String code) {
        return data.get(code);
    }

    public boolean isUnitAllowed(String code, String unit) {
        Nomenclature n = data.get(code);
        return n != null && n.getAllowedUnits().contains(unit);
    }
}

