package com.dcs.cdr.service;

import com.dcs.cdr.model.ChargeDetailRecord;
import com.dcs.cdr.repository.ChargeDetailRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChargeDetailRecorderService {

    @Autowired
    private ChargeDetailRecordRepository cdrRepository;

    @Autowired
    private ChargeDetailsRecordValidationService cdrValidationService;

    public ChargeDetailRecord createChargeDetailRecord(ChargeDetailRecord cdr) {
        cdrValidationService.validateChargeDetailsRecord(cdr);
        return cdrRepository.save(cdr);
    }

    public ChargeDetailRecord getChargeDetailRecordById(Long id) {
        return cdrRepository.findById(id).orElse(null);
    }

    public List<ChargeDetailRecord> getAllChargeDetailRecordsByVehicleId(Long vehicleId, Optional<Sort.Direction> sortByStartTime, Optional<Sort.Direction> sortByEndTime) {
        Sort sort = Sort.by(sortByStartTime.orElse(Sort.Direction.ASC), "startTime")
                .and(Sort.by(sortByEndTime.orElse(Sort.Direction.DESC), "endTime"));
        return cdrRepository.findAllByVehicleId(vehicleId, sort);
    }

}
