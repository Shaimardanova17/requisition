package com.example.demo.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Request for creating a requisition item")
public class CreateRequisitionItemRequest {
    @Schema(description = "Код номенклатуры", example = "062010.100.000000", required = true)
    @NotBlank
    private String truCode;

    @Schema(description = "Название номенклатуры", example = "Газ природный", required = true)
    @NotBlank
    private String truName;

    @Schema(description = "Количество", example = "25", required = true)
    @Positive
    private BigDecimal quantity;

    @Schema(description = "Единица измерения", example = "Литр", required = true)
    private String mkei;

    @Schema(description = "Цена", example = "2552.52", required = true)
    @Positive
    private BigDecimal price;

    @Schema(description = "Срок поставки", example = "2026-02-25", required = true)
    private LocalDate desiredDeliveryDate;

    public String getTruCode() {
        return truCode;
    }

    public void setTruCode(String truCode) {
        this.truCode = truCode;
    }

    public String getTruName() {
        return truName;
    }

    public void setTruName(String truName) {
        this.truName = truName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getMkei() {
        return mkei;
    }

    public void setMkei(String mkei) {
        this.mkei = mkei;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDesiredDeliveryDate() {
        return desiredDeliveryDate;
    }

    public void setDesiredDeliveryDate(LocalDate desiredDeliveryDate) {
        this.desiredDeliveryDate = desiredDeliveryDate;
    }
}
