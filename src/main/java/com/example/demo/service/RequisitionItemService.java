package com.example.demo.service;

import com.example.demo.entity.RequisitionItem;
import com.example.demo.web.dto.CreateRequisitionItemRequest;
import com.example.demo.web.dto.RequisitionItemResponse;
import com.example.demo.web.dto.RequisitionSummaryResponse;
import com.example.demo.web.dto.UpdateRequisitionItemRequest;

public interface RequisitionItemService {

    RequisitionItemResponse createRequisitionItem(Long requisitionId, CreateRequisitionItemRequest requisitionItem);

    RequisitionItemResponse updateRequisitionItem(Long requisitionId, Long itemId, UpdateRequisitionItemRequest requisitionItem);

    void deleteRequisitionItem(Long requisitionId, Long itemId);

    RequisitionSummaryResponse getRequisitionSummary(Long requisitionId);

}
