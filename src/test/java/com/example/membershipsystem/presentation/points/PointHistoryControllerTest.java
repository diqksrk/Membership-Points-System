package com.example.membershipsystem.presentation.points;

import com.example.membershipsystem.infrastructure.common.utils.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PointHistoryControllerTest extends BaseTest {
    @Test
    @DisplayName("포인트 이력 조회하기")
    public void searchPointHistoryTest() throws Exception {

        // When & Then
        this.mockMvc.perform(get("/api/point/history")
                .param("startDate", "2022-05-05")
                .param("endDate", "2022-10-10")
                .param("barcodeNo", "1000000000")
                .param("page", "0")
                .param("size", "30")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.pointHistoryReturnDtoList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
        ;
    }
}