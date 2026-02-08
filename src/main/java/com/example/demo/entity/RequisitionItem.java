package com.example.demo.entity;


import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.demo.exception.ErrorMessages.ErrorCode.INVALID_DELIVERY_DATE;

@Entity
@Table(name = "requisition_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"requisition_id", "tru_code"})})
@Schema(description = "Item belonging to a purchase requisition")
public class RequisitionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id позиции")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requisition_id", nullable = false)
    @Schema(description = "Заявка")
    private PurchaseRequisition requisition;

    @Column(name = "row_number", nullable = false)
    @Schema(description = "Порядковый номер внутри заявки ")
    private Integer rowNumber;

    @Column(name = "tru_code", nullable = false)
    @Schema(description = "Код номенклатуры")
    private String truCode;

    @Column(name = "tru_name", nullable = false)
    @Schema(description = "Название номенклатуры")
    private String truName;

    @Column(name = "quantity", nullable = false, precision = 19, scale = 4)
    @Schema(description = "Количество")
    private BigDecimal quantity;

    @Column(name = "mkei", nullable = false)
    @Schema(description = "Единица измерения")
    private String mkei;

    @Column(name  = "price", nullable = false, precision = 19, scale = 4)
    @Schema(description = "Цена")
    private BigDecimal price;

    @Column(name = "desired_delivery_date", nullable = false)
    private LocalDate desiredDeliveryDate;

    @Version
    @Column(name = "version", nullable = false)
    @Schema(description = "Версия")
    private Long version;

    @Column(name = "comment")
    @Schema(description = "Комментарий к позиции")
    private String comment;

    public void update(BigDecimal quantity, LocalDate deliveryDate, String comment) {

        setQuantity(quantity);
        setDesiredDeliveryDate(deliveryDate);
        setComment(comment);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PurchaseRequisition getRequisition() {
        return requisition;
    }

    public void setRequisition(PurchaseRequisition requisition) {
        this.requisition = requisition;
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
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorMessages.ErrorCode.INVALID_QUANTITY);
        }
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
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(ErrorMessages.ErrorCode.INVALID_PRICE);
        }
        this.price = price;
    }

    public LocalDate getDesiredDeliveryDate() {
        return desiredDeliveryDate;
    }

    public void setDesiredDeliveryDate(LocalDate date) {
        if (date == null || date.isBefore(LocalDate.now().plusDays(3))) {
            throw new BusinessException(INVALID_DELIVERY_DATE);
        }
        this.desiredDeliveryDate = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
