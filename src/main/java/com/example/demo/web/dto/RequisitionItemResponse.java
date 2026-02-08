package com.example.demo.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Requisition item response")
public class RequisitionItemResponse {

    @Schema(description = "Id позиции", example = "10")
    private Long id;

    @Schema(description = "Id заявки", example = "10")
    private Long requisitionId;

    @Schema(description = "Порядковый номер внутри заявки ", example = "1,2,3..")
    private Integer rowNumber;

    @Schema(description = "Код номенклатуры", example = "062010.100.000000", required = true)
    private String truCode;

    @Schema(description = "Название номенклатуры", example = "Газ природный", required = true)
    private String truName;

    @Schema(description = "Количество", example = "25", required = true)
    private BigDecimal quantity;

    @Schema(description = "Единица измерения", example = "Литр", required = true)
    private String mkei;

    @Schema(description = "Цена", example = "2552.52", required = true)
    private BigDecimal price;

    @Schema(description = "Срок поставки", example = "2026-02-25", required = true)
    private LocalDate desiredDeliveryDate;

    @Schema(description = "Версия позиции", example = "1", required = true)
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Long requisitionId) {
        this.requisitionId = requisitionId;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
