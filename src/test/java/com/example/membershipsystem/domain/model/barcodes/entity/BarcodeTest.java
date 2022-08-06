package com.example.membershipsystem.domain.model.barcodes.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class BarcodeTest {
    @Test
    @DisplayName("할당 여부를 체크한다.")
    public void testAlign() {
        // Given
        Barcode barcode = Barcode.builder()
                .build();

        // When
        barcode.changeToisAlign();

        // Then
        assertThat(barcode.isAlignYn()).isEqualTo(true);
    }

}