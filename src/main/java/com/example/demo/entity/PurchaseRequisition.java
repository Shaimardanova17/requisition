package com.example.demo.entity;

import com.example.demo.exception.BusinessException;
import com.example.demo.util.Status;
import com.example.demo.web.dto.UpdateRequisitionItemRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.exception.ErrorMessages.ErrorCode.*;

@Entity
@Table(name = "purchase_requisition")
@Schema(description = "Purchase requisition entity")
public class PurchaseRequisition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id заявки")
    private Long id;

    @Column(name = "number", nullable = false, unique = true, updatable = false)
    @Schema(description = "Номер заявки")
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Schema(description = "Статус заявки")
    private Status status;

    @Column(name = "organizer_id", nullable = false)
    @Schema(description = "Id организатора")
    private String organizerId;

    @Column(name = "created_at", nullable = false)
    @Schema(description = "Создано(дата)")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @Schema(description = "Обновлено(дата)")
    private LocalDateTime updatedAt;

    @Column(name = "total_lot_sum_no_nds", nullable = false, precision = 19, scale = 4)
    @Schema(description = "Общая сумма заявки без НДС")
    private BigDecimal totalLotSumNoNDS = BigDecimal.ZERO;

    @OneToMany(
            mappedBy = "requisition",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Schema(description = "Позиции")
    private List<RequisitionItem> items = new ArrayList<>();

    public List<RequisitionItem> getItems() {
        return items;
    }

    public void addItem(RequisitionItem item) {

        assertDraft();

        boolean duplicate = items.stream()
                .anyMatch(i -> i.getTruCode().equals(item.getTruCode()));
        if (duplicate) {
            throw new BusinessException(DUPLICATE_NOMENCLATURE_IN_REQUISITION);
        }


        item.setRowNumber(items.size() + 1);
        item.setRequisition(this);
        items.add(item);
        recalculateTotal();
    }

    public RequisitionItem updateItem(Long itemId, BigDecimal quantity, LocalDate deliveryDate, String comment) {
        assertDraft();

        RequisitionItem item = findItemById(itemId);
        item.update(quantity, deliveryDate, comment);

        recalculateTotal();

        return item;
    }

    public void removeItem(Long itemId) {
        if (items.size() == 1) {
            throw new BusinessException(LAST_ITEM_DELETE_FORBIDDEN);
        }

        RequisitionItem item = findItemById(itemId);

        items.remove(item);

        recalculateTotal();
    }


    private void assertDraft() {
        if (this.status != Status.DRAFT) {
            throw new BusinessException(REQUISITION_NOT_EDITABLE);
        }
    }

    private void recalculateTotal() {
        this.totalLotSumNoNDS = calculateTotalAmount();
    }

    public RequisitionItem findItemById(Long itemId) {
        return items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ITEM_NOT_FOUND));
    }

    public BigDecimal calculateTotalAmount() {
        return items.stream()
                .map(item -> item.getPrice().multiply(item.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalQuantity() {
        return items.stream()
                .map(RequisitionItem::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public LocalDate getMinDesiredDeliveryDate() {
        return items.stream()
                .map(RequisitionItem::getDesiredDeliveryDate)
                .filter(date -> date != null)
                .min(LocalDate::compareTo)
                .orElse(null);
    }

    public LocalDate getMaxDesiredDeliveryDate() {
        return items.stream()
                .map(RequisitionItem::getDesiredDeliveryDate)
                .filter(date -> date != null)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    public boolean hasItems() {
        return items != null && !items.isEmpty();
    }





    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void setItems(List<RequisitionItem> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getTotalLotSumNoNDS() {
        return totalLotSumNoNDS;
    }

    public void setTotalLotSumNoNDS(BigDecimal totalLotSumNoNDS) {
        this.totalLotSumNoNDS = totalLotSumNoNDS;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
