package com.example.demo.web.rest;

import com.example.demo.exception.ApiErrorResponse;
import com.example.demo.service.RequisitionItemService;
import com.example.demo.util.Constants;
import com.example.demo.web.dto.CreateRequisitionItemRequest;
import com.example.demo.web.dto.RequisitionItemResponse;
import com.example.demo.web.dto.RequisitionSummaryResponse;
import com.example.demo.web.dto.UpdateRequisitionItemRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(Constants.REQUISITION_PATH)
@Tag(
        name = "Requisition Items",
        description = "Operations for managing purchase requisition items"
)
public class RequisitionItemController {

    private static final Logger log = LoggerFactory.getLogger(RequisitionItemController.class);

    private final RequisitionItemService requisitionItemService;

    public RequisitionItemController(RequisitionItemService requisitionItemService) {
        this.requisitionItemService = requisitionItemService;
    }

    @PostMapping(value = "/{requisitionId}/items",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create requisition item",
            description = "Creates a new item within a purchase requisition"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item successfully created"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<RequisitionItemResponse> createRequisitionItem(@PathVariable Long requisitionId,
                                                                         @RequestBody @Valid CreateRequisitionItemRequest itemDto) {

        log.info("REST request to create requisition item for requisition: {}", requisitionId);

        RequisitionItemResponse result = requisitionItemService.createRequisitionItem(requisitionId, itemDto);

        return ResponseEntity
                .created(URI.create(Constants.REQUISITION_PATH
                        + "/" + requisitionId
                        + "/items/" + result.getId()))
                .body(result);

    }


    @PatchMapping(value = "/{requisitionId}/items/{itemId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update requisition item",
            description = "Updates an existing requisition item by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item successfully updated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public ResponseEntity<RequisitionItemResponse> updateRequisitionItem(@PathVariable Long requisitionId,
                                                                         @PathVariable Long itemId,
                                                                         @RequestBody @Valid UpdateRequisitionItemRequest itemDto) {
        log.info(
                "REST request to update requisition item {} for requisition {}",
                itemId, requisitionId
        );

        return ResponseEntity.ok(requisitionItemService.updateRequisitionItem(requisitionId, itemId, itemDto));

    }

    @DeleteMapping("/{requisitionId}/items/{itemId}")
    @Operation(
            summary = "Delete requisition item"
    )
    @ApiResponse(responseCode = "204", description = "Item deleted")
    public ResponseEntity<Void> deleteRequisitionItem(@PathVariable Long requisitionId,
                                                      @PathVariable Long itemId) {

        log.info(
                "REST request to delete requisition item {} from requisition {}",
                itemId, requisitionId
        );

        requisitionItemService.deleteRequisitionItem(requisitionId, itemId);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{requisitionId}/summary")
    @Operation(
            summary = "Get requisition summary",
            description = "Returns aggregated information about the purchase requisition"
    )
    @ApiResponse(responseCode = "200", description = "Summary returned")
    public ResponseEntity<RequisitionSummaryResponse> getRequisitionItem(@PathVariable Long requisitionId) {

        log.info("REST request to get requisition items from requisition : {}", requisitionId);

        return ResponseEntity.ok(requisitionItemService.getRequisitionSummary(requisitionId));
    }

}
