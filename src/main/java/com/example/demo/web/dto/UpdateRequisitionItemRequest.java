package com.example.demo.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Request for updating a requisition item")
public class UpdateRequisitionItemRequest {

    @Schema(description = "Количество", example = "25", required = true)
    @Positive
    private BigDecimal quantity;

    @Schema(description = "Срок поставки", example = "2026-02-25", required = true)
    private LocalDate desiredDeliveryDate;

    @Schema(description = "Версия позиции", example = "1", required = true)
    private Long version;

    @Schema(description = "Комментарий", example = "ТС прописана по требованию", required = true)
    private String comment;

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDesiredDeliveryDate() {
        return desiredDeliveryDate;
    }

    public void setDesiredDeliveryDate(LocalDate desiredDeliveryDate) {
        this.desiredDeliveryDate = desiredDeliveryDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
