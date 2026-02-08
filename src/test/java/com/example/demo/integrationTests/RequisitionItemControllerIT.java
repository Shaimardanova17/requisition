package com.example.demo.integrationTests;

import com.example.demo.exception.ApiErrorResponse;
import com.example.demo.web.dto.CreateRequisitionItemRequest;
import com.example.demo.web.dto.RequisitionItemResponse;
import com.example.demo.web.dto.RequisitionSummaryResponse;
import com.example.demo.web.dto.UpdateRequisitionItemRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class RequisitionItemControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createItem_ok() {
        CreateRequisitionItemRequest request = new CreateRequisitionItemRequest();
        request.setTruCode("062010.100.000000");
        request.setTruName("Газ природный");
        request.setQuantity(BigDecimal.TEN);
        request.setMkei("Литр");
        request.setPrice(BigDecimal.valueOf(100));
        request.setDesiredDeliveryDate(LocalDate.now().plusDays(5));

        ResponseEntity<RequisitionItemResponse> response =
                restTemplate.postForEntity(
                        "/api/v1/requisitions/1/items",
                        request,
                        RequisitionItemResponse.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void createItem_fail_whenInvalidUnit() {
        CreateRequisitionItemRequest request = new CreateRequisitionItemRequest();
        request.setTruCode("062010.100.000000");
        request.setTruName("Газ природный");
        request.setQuantity(BigDecimal.ONE);
        request.setMkei("INVALID");
        request.setPrice(BigDecimal.TEN);
        request.setDesiredDeliveryDate(LocalDate.now().plusDays(5));

        ResponseEntity<ApiErrorResponse> response =
                restTemplate.postForEntity(
                        "/api/v1/requisitions/1/items",
                        request,
                        ApiErrorResponse.class
                );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("INVALID_UNIT_FOR_NOMENCLATURE",
                response.getBody().getErrorCode());
    }

    @Test
    void getSummary_ok() {
        ResponseEntity<RequisitionSummaryResponse> response =
                restTemplate.getForEntity(
                        "/api/v1/requisitions/1/summary",
                        RequisitionSummaryResponse.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("KZT", response.getBody().getCurrency());
    }

    @Test
    void optimisticLocking_conflict() {
        RequisitionItemResponse item =
                restTemplate.getForObject(
                        "/api/v1/requisitions/1/items/1",
                        RequisitionItemResponse.class
                );

        UpdateRequisitionItemRequest req1 = new UpdateRequisitionItemRequest();
        req1.setQuantity(BigDecimal.valueOf(5));
        req1.setVersion(item.getVersion());

        restTemplate.patchForObject(
                "/api/v1/requisitions/1/items/1",
                req1,
                Void.class
        );

        UpdateRequisitionItemRequest req2 = new UpdateRequisitionItemRequest();
        req2.setQuantity(BigDecimal.valueOf(7));
        req2.setVersion(item.getVersion()); // старая версия

        ResponseEntity<ApiErrorResponse> response =
                restTemplate.exchange(
                        "/api/v1/requisitions/1/items/1",
                        HttpMethod.PATCH,
                        new HttpEntity<>(req2),
                        ApiErrorResponse.class
                );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("OPTIMISTIC_LOCKING_FAILURE",
                response.getBody().getErrorCode());
    }

}
