package com.example.membershipsystem.presentation.points;

import com.example.membershipsystem.infrastructure.common.utils.BaseTest;
import com.example.membershipsystem.infrastructure.exception.message.PointExceptionMessage;
import com.example.membershipsystem.presentation.points.request.PointEarningRequest;
import com.example.membershipsystem.presentation.points.request.PointUsingRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PointControllerTest extends BaseTest {

    // 포인트 적립 API test start
    @Test
    @DisplayName("포인트를 적립하는 테스트")
    @Transactional
    public void earnPointTest() throws Exception {
        // Given
        PointEarningRequest pointEarningRequest = PointEarningRequest.builder()
                .storeId(Long.valueOf(1))
                .barcodeNo("1000000000")
                .reserveAmt(Long.valueOf(5000))
                .build();

        // When & Then
        mockMvc.perform(post("/api/point/earn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(pointEarningRequest))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("barcodeNo").exists())
                .andExpect(jsonPath("barcodeNo").isString())
                .andExpect(jsonPath("category").exists())
                .andExpect(jsonPath("reserveAmt").exists())
                .andExpect(jsonPath("reserveAmt").isNumber())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
        ;
    }

    @Test
    @DisplayName("입력 값이 비어있는 경우에 에러가 발생하는 테스트")
    public void earnPoint_Bad_Request_Empty_Input() throws Exception {
        // Given
        PointEarningRequest pointEarningRequest = PointEarningRequest.builder()
                .build();

        // When & Then
        mockMvc.perform(post("/api/point/earn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(pointEarningRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("10자리 바코드값이 아닐경우 에러가 발생하는 테스트")
    public void earnPoint_Bad_Request_Wrong_Barcode_no() throws Exception {
        // Given
        PointEarningRequest pointEarningRequest = PointEarningRequest.builder()
                .storeId(Long.valueOf(1))
                .barcodeNo("3000")
                .reserveAmt(Long.valueOf(5000))
                .build();

        // When & Then
        mockMvc.perform(post("/api/point/earn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(pointEarningRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").value(PointExceptionMessage.WrongBarcodeException.getMessage()))
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].code").value(PointExceptionMessage.WrongBarcodeException.getType()))
        ;
    }

    @Test
    @DisplayName("존재하지 않는 영업장을 요청할 경우 에러가 발생하는 테스트")
    public void earnPoint_Bad_Request_Wrong_store_id() throws Exception {
        // Given
        PointEarningRequest pointEarningRequest = PointEarningRequest.builder()
                .storeId(Long.valueOf(30000))
                .barcodeNo("1000000000")
                .reserveAmt(Long.valueOf(5000))
                .build();

        // When & Then
        mockMvc.perform(post("/api/point/earn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(pointEarningRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").value(PointExceptionMessage.NotFoundStoreException.getMessage()))
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].code").value(PointExceptionMessage.NotFoundStoreException.getType()))
        ;
    }

    @Test
    @DisplayName("존재하지 않는 바코드를 요청할 경우 에러가 발생하는 테스트")
    public void earnPoint_Bad_Request_Wrong_barcode_no2() throws Exception {
        // Given
        PointEarningRequest pointEarningRequest = PointEarningRequest.builder()
                .storeId(Long.valueOf(1))
                .barcodeNo("9999999999")
                .reserveAmt(Long.valueOf(5000))
                .build();

        // When & Then
        mockMvc.perform(post("/api/point/earn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(pointEarningRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").value(PointExceptionMessage.NotFoundBarcodeException.getMessage()))
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].code").value(PointExceptionMessage.NotFoundBarcodeException.getType()))
        ;
    }
    // 포인트 적립 API test End


    // 포인트 사용 API test start
    @Test
    @DisplayName("포인트를 사용하는 테스트")
    @Transactional
    public void usePointTest() throws Exception {
        // Given
        PointUsingRequest pointUsingRequest = PointUsingRequest.builder()
                .storeId(Long.valueOf(1))
                .barcodeNo("1000000000")
                .useAmt(Long.valueOf(1000))
                .build();

        // When & Then
        mockMvc.perform(post("/api/point/use")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(pointUsingRequest))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("barcodeNo").exists())
                .andExpect(jsonPath("barcodeNo").isString())
                .andExpect(jsonPath("category").exists())
                .andExpect(jsonPath("reserveAmt").exists())
                .andExpect(jsonPath("reserveAmt").isNumber())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
        ;
    }

    @Test
    @DisplayName("입력 값이 비어있는 경우에 에러가 발생하는 테스트")
    public void usePoint_Bad_Request_Empty_Input() throws Exception {
        // Given
        PointUsingRequest pointUsingRequest = PointUsingRequest.builder().build();

        // When & Then
        mockMvc.perform(post("/api/point/use")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(pointUsingRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("10자리 바코드값이 아닐경우 에러가 발생하는 테스트")
    public void usePoint_Bad_Request_Wrong_Barcode_no() throws Exception {
        // Given
        PointUsingRequest pointUsingRequest = PointUsingRequest.builder()
                .storeId(Long.valueOf(1))
                .barcodeNo("3000")
                .useAmt(Long.valueOf(5000))
                .build();

        // When & Then
        mockMvc.perform(post("/api/point/use")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(pointUsingRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").value(PointExceptionMessage.WrongBarcodeException.getMessage()))
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].code").value(PointExceptionMessage.WrongBarcodeException.getType()))
        ;
    }

    @Test
    @DisplayName("존재하지 않는 영업장을 요청할 경우 에러가 발생하는 테스트")
    public void usePoint_Bad_Request_Wrong_store_id() throws Exception {
        // Given
        PointUsingRequest pointUsingRequest = PointUsingRequest.builder()
                .storeId(Long.valueOf(30000))
                .barcodeNo("1000000000")
                .useAmt(Long.valueOf(5000))
                .build();

        // When & Then
        mockMvc.perform(post("/api/point/use")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(pointUsingRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").value(PointExceptionMessage.NotFoundStoreException.getMessage()))
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].code").value(PointExceptionMessage.NotFoundStoreException.getType()))
        ;
    }

    @Test
    @DisplayName("존재하지 않는 바코드를 요청할 경우 에러가 발생하는 테스트")
    public void usePoint_Bad_Request_Wrong_barcode_no2() throws Exception {
        // Given
        PointUsingRequest pointUsingRequest = PointUsingRequest.builder()
                .storeId(Long.valueOf(1))
                .barcodeNo("9999999999")
                .useAmt(Long.valueOf(5000))
                .build();

        // When & Then
        mockMvc.perform(post("/api/point/use")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(pointUsingRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").value(PointExceptionMessage.NotFoundBarcodeException.getMessage()))
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].code").value(PointExceptionMessage.NotFoundBarcodeException.getType()))
        ;
    }

    @Test
    @DisplayName("포인트 적립금이 부족할경우 에러가 발생하는 테스트")
    public void usePoint_Bad_Request_Wrong_useAmt() throws Exception {
        // Given
        PointUsingRequest pointUsingRequest = PointUsingRequest.builder()
                .storeId(Long.valueOf(1))
                .barcodeNo("1000000000")
                .useAmt(Long.valueOf(30000000))
                .build();

        // When & Then
        mockMvc.perform(post("/api/point/use")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(pointUsingRequest)))
                .andDo(print())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").value(PointExceptionMessage.UnAuthorizedPointUseException.getMessage()))
                .andExpect(jsonPath("errors[0].code").exists())
        ;
    }
    // 포인트 사용  API test End
}