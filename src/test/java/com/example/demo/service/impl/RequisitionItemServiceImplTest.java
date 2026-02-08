package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseRequisition;
import com.example.demo.entity.RequisitionItem;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ErrorMessages;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repo.PurchaseRequisitionRepository;
import com.example.demo.repo.RequisitionItemRepository;
import com.example.demo.util.Nomenclature;
import com.example.demo.util.NomenclatureDictionary;
import com.example.demo.util.Status;
import com.example.demo.web.dto.CreateRequisitionItemRequest;
import com.example.demo.web.dto.RequisitionItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.example.demo.exception.ErrorMessages.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequisitionItemServiceImplTest {

    @Mock
    private PurchaseRequisitionRepository requisitionRepository;

    @Mock
    private RequisitionItemRepository requisitionItemRepository;

    @Mock
    private NomenclatureDictionary nomenclatureDictionary;

    @InjectMocks
    private RequisitionItemServiceImpl service;

    private PurchaseRequisition requisition;
    private CreateRequisitionItemRequest request;

    @BeforeEach
    void setUp() {
        requisition = new PurchaseRequisition();
        requisition.setId(1L);
        requisition.setStatus(Status.DRAFT);
        requisition.setItems(new ArrayList<>());

        request = new CreateRequisitionItemRequest();
        request.setTruCode("329911.900.000031");
        request.setTruName("Полумаска");
        request.setQuantity(BigDecimal.TEN);
        request.setMkei("Штука");
        request.setPrice(BigDecimal.valueOf(100));
        request.setDesiredDeliveryDate(LocalDate.now().plusDays(5));
    }


    @Test
    void createItem_fail_whenRequisitionNotFound() {
        when(requisitionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.createRequisitionItem(1L, request)
        );
    }

    @Test
    void createItem_fail_whenNomenclatureNotFound() {
        when(requisitionRepository.findById(1L))
                .thenReturn(Optional.of(requisition));

        when(nomenclatureDictionary.get(request.getTruCode()))
                .thenReturn(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> service.createRequisitionItem(1L, request)
        );

        assertEquals(NOMENCLATURE_NOT_FOUND, ex.getErrorCode());
    }

    @Test
    void createItem_fail_whenInvalidUnit() {
        when(requisitionRepository.findById(1L))
                .thenReturn(Optional.of(requisition));

        when(nomenclatureDictionary.get(request.getTruCode()))
                .thenReturn(new Nomenclature(
                        request.getTruCode(),
                        request.getTruName(),
                        Collections.singleton("KG")
                ));

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> service.createRequisitionItem(1L, request)
        );

        assertEquals(INVALID_UNIT_FOR_NOMENCLATURE, ex.getErrorCode());
    }

    /* ---------- SUMMARY ---------- */

    @Test
    void getSummary_fail_whenNoItems() {
        when(requisitionRepository.findById(1L))
                .thenReturn(Optional.of(requisition));

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> service.getRequisitionSummary(1L)
        );

        assertEquals(ITEM_NOT_FOUND, ex.getErrorCode());
    }

    /* ---------- helpers ---------- */

    private Nomenclature validNomenclature() {
        return new Nomenclature(
                request.getTruCode(),
                request.getTruName(),
                Collections.singleton("Штука")
        );
    }
}
