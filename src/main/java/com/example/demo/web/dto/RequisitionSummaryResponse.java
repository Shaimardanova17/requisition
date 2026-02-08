package com.example.demo.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RequisitionSummaryResponse {

    @Schema(description = "Id заявки", example = "10")
    private Long requisitionId;

    @Schema(description = "Итоговая сумма без НДС", example = "256231.778")
    private BigDecimal totalAmountWithoutVat;

    @Schema(description = "Общее количество", example = "10")
    private BigDecimal totalQuantity;

    @Schema(description = "Минимальный срок поставки", example = "2026-02-25")
    private LocalDate minDesiredDeliveryDate;

    @Schema(description = "Максимальный срок поставки", example = "2026-02-28")
    private LocalDate maxDesiredDeliveryDate;

    @Schema(description = "Общее количество позиции", example = "10")
    private Integer itemsCount;

    @Schema(description = "Валюта", example = "KZT")
    private String currency;

    public Long getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Long requisitionId) {
        this.requisitionId = requisitionId;
    }

    public BigDecimal getTotalAmountWithoutVat() {
        return totalAmountWithoutVat;
    }

    public void setTotalAmountWithoutVat(BigDecimal totalAmountWithoutVat) {
        this.totalAmountWithoutVat = totalAmountWithoutVat;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public LocalDate getMinDesiredDeliveryDate() {
        return minDesiredDeliveryDate;
    }

    public void setMinDesiredDeliveryDate(LocalDate minDesiredDeliveryDate) {
        this.minDesiredDeliveryDate = minDesiredDeliveryDate;
    }

    public LocalDate getMaxDesiredDeliveryDate() {
        return maxDesiredDeliveryDate;
    }

    public void setMaxDesiredDeliveryDate(LocalDate maxDesiredDeliveryDate) {
        this.maxDesiredDeliveryDate = maxDesiredDeliveryDate;
    }

    public Integer getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

