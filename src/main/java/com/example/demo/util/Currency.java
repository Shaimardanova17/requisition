package com.example.demo.util;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Supported currencies")
public enum Currency {
    KZT, USD, EUR;
}
