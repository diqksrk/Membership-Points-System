package com.example.membershipsystem.presentation.barcodes;

import com.example.membershipsystem.infrastructure.common.utils.BaseTest;
import com.example.membershipsystem.infrastructure.exception.message.BarcodeIssueExceptionMessage;
import com.example.membershipsystem.presentation.barcodes.request.BarcodeIssueRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BarcodeIssueControllerTest extends BaseTest {

    @Test
    @DisplayName("기존 바코드를 반환하는 테스트")
    public void existBarcodeReturnTest() throws Exception {
        // Given
        BarcodeIssueRequest barcodeIssueRequest = BarcodeIssueRequest.builder()
                .userId(Long.valueOf(100000000))
                .build();

        // When & Then
        mockMvc.perform(post("/api/barcode/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(barcodeIssueRequest))
        )
                .andDo(print())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("userId").value(barcodeIssueRequest.getUserId()))
                .andExpect(jsonPath("userId").isNumber())
                .andExpect(jsonPath("barcodeNo").isString())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
        ;
    }

    @Test
    @DisplayName("신규 바코드를 발급하는 테스트")
    @Transactional
    public void issueBarcodeTest() throws Exception {
        // Given
        BarcodeIssueRequest barcodeIssueRequest = BarcodeIssueRequest.builder()
                .userId(Long.valueOf(100000002))
                .build();

        // When & Then
        mockMvc.perform(post("/api/barcode/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(barcodeIssueRequest))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("userId").value(barcodeIssueRequest.getUserId()))
                .andExpect(jsonPath("userId").isNumber())
                .andExpect(jsonPath("barcodeNo").isString())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
        ;
    }

    @Test
    @DisplayName("입력 값이 비어있는 경우에 에러가 발생하는 테스트")
    public void issueBarcode_Bad_Request_Empty_Input() throws Exception {
        // Given
        BarcodeIssueRequest barcodeIssueRequest = BarcodeIssueRequest.builder()
                .build();

        // When & Then
        mockMvc.perform(post("/api/barcode/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(barcodeIssueRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력 값이 잘못된 경우에 에러가 발생하는 테스트")
    public void issueBarcode_Bad_Request_Wrong_Input() throws Exception {
        // Given
        BarcodeIssueRequest barcodeIssueRequest = BarcodeIssueRequest.builder()
                .userId(Long.valueOf(1000))
                .build();

        // When & Then
        mockMvc.perform(post("/api/barcode/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(barcodeIssueRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").value(BarcodeIssueExceptionMessage.WrongUserException.getMessage()))
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].code").value(BarcodeIssueExceptionMessage.WrongUserException.getType()))
        ;
    }

    @Test
    @DisplayName("존재하지 않는 유저 발급을 요청할때 에러가 발생하는 테스트")
    public void issueBarcode_Bad_Request_Wrong_User() throws Exception {
        // Given
        BarcodeIssueRequest barcodeIssueRequest = BarcodeIssueRequest.builder()
                .userId(Long.valueOf(900000000))
                .build();

        // When & Then
        mockMvc.perform(post("/api/barcode/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(barcodeIssueRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").value(BarcodeIssueExceptionMessage.NotFoundException.getMessage()))
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].code").value(BarcodeIssueExceptionMessage.NotFoundException.getType()))
        ;
    }
}