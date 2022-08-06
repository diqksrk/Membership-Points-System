package com.example.membershipsystem.application.point;

import com.example.membershipsystem.domain.model.points.entity.Point;
import com.example.membershipsystem.domain.model.points.repository.PointRepository;
import com.example.membershipsystem.infrastructure.common.utils.BaseTest;
import com.example.membershipsystem.presentation.points.request.PointEarningRequest;
import com.example.membershipsystem.presentation.points.request.PointUsingRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PointServiceImplTest extends BaseTest {
    @Autowired
    private PointRepository pointRepository;

    @Test
    @DisplayName("포인트 적립, 사용 forupdate lock을 체크한다.")
    @Transactional
    public void usePointServiceLockTest() throws Exception {
        Point startPoint = pointRepository.getReferenceById(Long.valueOf(9));
        Long startReserveAmt = startPoint.getReserveAmt();

        int numOfExecute = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(numOfExecute);

        // 시작값 2525000
        for (int i = 0; i < numOfExecute; i++) {
            executorService.execute(() -> {
                try {
                    PointEarningRequest pointEarningRequest = PointEarningRequest.builder()
                            .storeId(Long.valueOf(1))
                            .barcodeNo("1000000000")
                            .reserveAmt(Long.valueOf(5000))
                            .build();
                    String content = objectMapper.writeValueAsString(pointEarningRequest);

                    // 시작 reseveAmt = 2040000
                    mockMvc.perform(post("/api/point/earn")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andDo(MockMvcResultHandlers.print());

                    PointUsingRequest pointUsingRequest = PointUsingRequest.builder()
                            .storeId(Long.valueOf(1))
                            .barcodeNo("1000000000")
                            .useAmt(Long.valueOf(5000))
                            .build();
                    content = objectMapper.writeValueAsString(pointUsingRequest);

                    // 시작 reseveAmt = 5730000
                    mockMvc.perform(post("/api/point/use")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andDo(MockMvcResultHandlers.print());
                } catch (Throwable th) {
                    System.err.println(th.getMessage());
                }
                countDownLatch.countDown();
            });
        }

        // 종료값 2525000
        countDownLatch.await();

        Point endPoint = pointRepository.getReferenceById(Long.valueOf(9));
        Long endReserveAmt = endPoint.getReserveAmt();

        assertThat(startReserveAmt).isEqualTo(endReserveAmt);
    }

    @Test
    @DisplayName("포인트 적립시 업장별로 중복 insert되는지 확인한다.")
    @Transactional
    public void earnPointServiceDuplTest() throws Exception {
        int numOfExecute = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(numOfExecute);

        for (int i = 0; i < numOfExecute; i++) {
            executorService.execute(() -> {
                try {
                    PointEarningRequest pointEarningRequest = PointEarningRequest.builder()
                            .storeId(Long.valueOf(2))
                            .barcodeNo("1000000000")
                            .reserveAmt(Long.valueOf(5000))
                            .build();
                    String content = objectMapper.writeValueAsString(pointEarningRequest);

                    mockMvc.perform(post("/api/point/earn")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andDo(MockMvcResultHandlers.print());
                } catch (Throwable th) {
                    System.err.println(th.getMessage());
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        Point endPoint = pointRepository.getReferenceById(Long.valueOf(11));

        assertThat(endPoint.getId()).isEqualTo(Long.valueOf(11));
    }
}