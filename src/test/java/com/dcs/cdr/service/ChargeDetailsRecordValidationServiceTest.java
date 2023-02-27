package com.dcs.cdr.service;

import com.dcs.cdr.exception.InvalidChangeDetailsRecordException;
import com.dcs.cdr.model.ChargeDetailRecord;
import com.dcs.cdr.repository.ChargeDetailRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChargeDetailsRecordValidationServiceTest {

    @InjectMocks
    private ChargeDetailsRecordValidationService underTest;

    @Mock
    private ChargeDetailRecordRepository cdrRepository;

    private ChargeDetailRecord chargeDetailRecord1;
    private ChargeDetailRecord chargeDetailRecord2;

    @BeforeEach
    void setUp() {
        chargeDetailRecord1 = ChargeDetailRecord.builder().sessionId("session1").vehicleId(123L)
                .startTime(LocalDateTime.of(2022, 2, 1, 10, 0))
                .endTime(LocalDateTime.of(2022, 2, 1, 10, 0))
                .build();

        chargeDetailRecord2 = ChargeDetailRecord.builder().sessionId("session2").vehicleId(124L)
                .startTime(LocalDateTime.of(2022, 2, 1, 12, 0))
                .endTime(LocalDateTime.of(2022, 2, 1, 13, 0))
                .totalCost(30.0)
                .build();
    }

    @Test
    void validateChargeDetailsRecord_shouldThrowException_whenEndTimeIsSmallerThanStartTime() {
        chargeDetailRecord1.setEndTime(LocalDateTime.of(2022, 2, 1, 9, 0));

        InvalidChangeDetailsRecordException exception = assertThrows(InvalidChangeDetailsRecordException.class,
                () -> underTest.validateChargeDetailsRecord(chargeDetailRecord1));
        assertEquals("End time can not be smaller than start time", exception.getMessage());
    }

    @Test
    void validateChargeDetailsRecord_shouldThrowException_whenLastUpdatedVehicleEndTimeIsGreaterThanCurrentStartTime() {
        List<ChargeDetailRecord> cdrList = new ArrayList<>();
        cdrList.add(chargeDetailRecord2);
        when(cdrRepository.findAllByVehicleId(chargeDetailRecord1.getVehicleId(), Sort.by(Sort.Direction.DESC, "endTime")))
                .thenReturn(cdrList);

        chargeDetailRecord1.setStartTime(LocalDateTime.of(2022, 2, 1, 12, 0));
        chargeDetailRecord1.setEndTime(LocalDateTime.of(2022, 2, 1, 12, 0));

        InvalidChangeDetailsRecordException exception = assertThrows(InvalidChangeDetailsRecordException.class,
                () -> underTest.validateChargeDetailsRecord(chargeDetailRecord1));
        assertEquals("End time of last updated vehicle can not be greater than current start time", exception.getMessage());
    }

    @Test
    void validateChargeDetailsRecord_shouldReturnTrue_whenValidChargeDetailRecord() {
        List<ChargeDetailRecord> cdrList = new ArrayList<>();
        when(cdrRepository.findAllByVehicleId(chargeDetailRecord1.getVehicleId(), Sort.by(Sort.Direction.DESC, "endTime")))
                .thenReturn(cdrList);

        boolean result = underTest.validateChargeDetailsRecord(chargeDetailRecord1);
        assertTrue(result);
    }
}
