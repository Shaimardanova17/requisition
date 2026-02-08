package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseRequisition;
import com.example.demo.entity.RequisitionItem;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repo.PurchaseRequisitionRepository;
import com.example.demo.repo.RequisitionItemRepository;
import com.example.demo.service.RequisitionItemService;
import com.example.demo.service.mapper.RequisitionItemMapper;
import com.example.demo.util.Currency;
import com.example.demo.util.Nomenclature;
import com.example.demo.util.NomenclatureDictionary;
import com.example.demo.web.dto.CreateRequisitionItemRequest;
import com.example.demo.web.dto.RequisitionItemResponse;
import com.example.demo.web.dto.RequisitionSummaryResponse;
import com.example.demo.web.dto.UpdateRequisitionItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.demo.exception.ErrorMessages.ErrorCode.*;

@Service
public class RequisitionItemServiceImpl implements RequisitionItemService {

    private static final Logger log = LoggerFactory.getLogger(RequisitionItemServiceImpl.class);

    private final RequisitionItemRepository requisitionItemRepository;
    private final PurchaseRequisitionRepository purchaseRequisitionRepo;
    private final NomenclatureDictionary nomenclatureDictionary;

    public RequisitionItemServiceImpl(RequisitionItemRepository requisitionItemRepository,
                                      PurchaseRequisitionRepository purchaseRequisitionRepo,
                                      NomenclatureDictionary nomenclatureDictionary) {
        this.requisitionItemRepository = requisitionItemRepository;
        this.purchaseRequisitionRepo = purchaseRequisitionRepo;
        this.nomenclatureDictionary = nomenclatureDictionary;
    }

    @Transactional
    public RequisitionItemResponse createRequisitionItem(
            Long requisitionId,
            CreateRequisitionItemRequest item) {

        PurchaseRequisition requisition = getRequisition(requisitionId);

        validateNomenclature(item.getTruCode(), item.getMkei());

        RequisitionItem entity = RequisitionItemMapper.toEntity(item);

        requisition.addItem(entity);

        RequisitionItem saved =
                requisitionItemRepository.saveAndFlush(entity);

        log.info("Created requisition item for requisition {}", requisitionId);

        return RequisitionItemMapper.toDto(saved);
    }


    @Override
    @Transactional
    public RequisitionItemResponse updateRequisitionItem(Long requisitionId,
                                                         Long itemId,
                                                         UpdateRequisitionItemRequest request) {
        PurchaseRequisition requisition = getRequisition(requisitionId);

        RequisitionItem updatedItem = requisition.updateItem(
                itemId,
                request.getQuantity(),
                request.getDesiredDeliveryDate(),
                request.getComment());

        purchaseRequisitionRepo.save(requisition);

        return RequisitionItemMapper.toDto(updatedItem);
    }

    @Transactional
    @Override
    public void deleteRequisitionItem(Long requisitionId, Long itemId) {
        PurchaseRequisition requisition = getRequisition(requisitionId);

        requisition.removeItem(itemId);

        purchaseRequisitionRepo.save(requisition);
    }

    @Override
    public RequisitionSummaryResponse getRequisitionSummary(Long requisitionId) {

        PurchaseRequisition requisition = getRequisition(requisitionId);

        if (!requisition.hasItems()) {
            throw new BusinessException(ITEM_NOT_FOUND);
        }

        RequisitionSummaryResponse response = new RequisitionSummaryResponse();
        response.setRequisitionId(requisitionId);
        response.setTotalAmountWithoutVat(requisition.calculateTotalAmount());
        response.setTotalQuantity(requisition.calculateTotalQuantity());
        response.setMinDesiredDeliveryDate(requisition.getMinDesiredDeliveryDate());
        response.setMaxDesiredDeliveryDate(requisition.getMaxDesiredDeliveryDate());
        response.setItemsCount(requisition.getItems().size());
        response.setCurrency(Currency.KZT.name());

        return response;
    }


    private PurchaseRequisition getRequisition(Long id) {
        return purchaseRequisitionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(REQUISITION_NOT_FOUND));
    }

    private Nomenclature validateNomenclature(String truCode, String mkei) {
        Nomenclature nomenclature = nomenclatureDictionary.get(truCode);
        if (nomenclature == null) {
            throw new BusinessException(NOMENCLATURE_NOT_FOUND);
        }
        if (!nomenclature.getAllowedUnits().contains(mkei)) {
            throw new BusinessException(INVALID_UNIT_FOR_NOMENCLATURE);
        }
        return nomenclature;
    }

}





