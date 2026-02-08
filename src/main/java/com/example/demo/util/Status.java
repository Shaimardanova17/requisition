package com.example.demo.util;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Requisition lifecycle status")
public enum Status {
    DRAFT,
    SUBMITTED,
    APPROVED,
    IN_PROCUREMENT,
    CLOSED,
    REJECTED
}
