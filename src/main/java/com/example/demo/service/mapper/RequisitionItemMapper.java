package com.example.demo.service.mapper;

import com.example.demo.entity.RequisitionItem;
import com.example.demo.web.dto.CreateRequisitionItemRequest;
import com.example.demo.web.dto.RequisitionItemResponse;
import org.springframework.stereotype.Component;

@Component
public class RequisitionItemMapper {

    public static RequisitionItem toEntity(CreateRequisitionItemRequest dto) {
            RequisitionItem entity = new RequisitionItem();
        entity.setTruCode(dto.getTruCode());
        entity.setTruName(dto.getTruName());
        entity.setQuantity(dto.getQuantity());
        entity.setMkei(dto.getMkei());
        entity.setPrice(dto.getPrice());
        entity.setDesiredDeliveryDate(dto.getDesiredDeliveryDate());

            return entity;
    }

    public static RequisitionItemResponse toDto(RequisitionItem entity) {

        RequisitionItemResponse response = new RequisitionItemResponse();
        response.setId(entity.getId());
        response.setRequisitionId(entity.getRequisition().getId());
        response.setRowNumber(entity.getRowNumber());
        response.setTruCode(entity.getTruCode());
        response.setTruName(entity.getTruName());
        response.setQuantity(entity.getQuantity());
        response.setMkei(entity.getMkei());
        response.setPrice(entity.getPrice());
        response.setDesiredDeliveryDate(entity.getDesiredDeliveryDate());
        response.setVersion(entity.getVersion());
        return response;

    }
}
