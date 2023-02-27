package com.dcs.cdr.service;

import com.dcs.cdr.model.ChargeDetailRecord;
import com.dcs.cdr.repository.ChargeDetailRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChargeDetailRecorderServiceTest {

    @InjectMocks
    private ChargeDetailRecorderService underTest;

    @Mock
    private ChargeDetailRecordRepository cdrRrepository;

    @Mock
    private ChargeDetailsRecordValidationService cdrValidationService;

    @Test
    void shouldCreateChargeDetailRecord() {
        // Given
        var chargeDetailRecord = ChargeDetailRecord.builder().sessionId("session1")
                .startTime(LocalDateTime.now()).endTime(LocalDateTime.now()).totalCost(10.00).build();
        when(cdrValidationService.validateChargeDetailsRecord(any(ChargeDetailRecord.class))).thenReturn(true);
        when(cdrRrepository.save(chargeDetailRecord)).thenReturn(chargeDetailRecord);

        // When
        var savedChargedDetailRecord = underTest.createChargeDetailRecord(chargeDetailRecord);

        // Then
        assertNotNull(savedChargedDetailRecord);
        assertEquals(chargeDetailRecord.getSessionId(), savedChargedDetailRecord.getSessionId());
        //TODO add more assertions.
    }

    @Test
    void shouldGetChargeDetailRecordByIdIfAvailable() {
        // Given
        var chargeDetailRecord = ChargeDetailRecord.builder().id(1L).sessionId("session1").vehicleId(123L)
                .startTime(LocalDateTime.now()).endTime(LocalDateTime.now()).totalCost(10.00).build();
        when(cdrRrepository.findById(1L)).thenReturn(Optional.ofNullable(chargeDetailRecord));

        // When
        var detailRecord = underTest.getChargeDetailRecordById(1L);

        // Then
        assertNotNull(detailRecord);
        assertEquals(chargeDetailRecord.getSessionId(), detailRecord.getSessionId());
        //TODO add more assertions.
    }

    @Test
    void shouldGetNullChargeDetailRecordByIdIfNotAvailable() {
        // Given
        when(cdrRrepository.findById(1L)).thenReturn(Optional.empty());

        // When
        var detailRecord = underTest.getChargeDetailRecordById(1L);

        // Then
        assertNull(detailRecord);
    }

    @Test
    void shouldGetAllChargeDetailRecordsByVehicleId() {
        // Given
        var chargeDetailRecord = ChargeDetailRecord.builder().id(1L).sessionId("session1").vehicleId(123L)
                .startTime(LocalDateTime.now()).endTime(LocalDateTime.now()).totalCost(10.00).build();
        when(cdrRrepository.findAllByVehicleId(123L, Sort.by(Sort.Direction.ASC, "startTime")
                .and(Sort.by(Sort.Direction.DESC, "endTime"))))
                .thenReturn(List.of(chargeDetailRecord));

        // When
        var detailRecord = underTest
                .getAllChargeDetailRecordsByVehicleId(123L, Optional.empty(), Optional.empty());

        // Then
        assertNotNull(detailRecord);
        assertEquals(chargeDetailRecord.getSessionId(), detailRecord.get(0).getSessionId());
        //TODO add more assertions.
    }
}