package com.dcs.cdr.service;

import com.dcs.cdr.exception.InvalidChangeDetailsRecordException;
import com.dcs.cdr.model.ChargeDetailRecord;
import com.dcs.cdr.repository.ChargeDetailRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ChargeDetailsRecordValidationService {

    @Autowired
    private ChargeDetailRecordRepository cdrRepository;

    public boolean validateChargeDetailsRecord(ChargeDetailRecord cdr) {
        if (cdr.getEndTime().isBefore(cdr.getStartTime())) {
            throw new InvalidChangeDetailsRecordException("End time can not be smaller than start time");
        }

        var lastUpdatedVehicle = cdrRepository.findAllByVehicleId(cdr.getVehicleId(), Sort.by(Sort.Direction.DESC, "endTime"));
        if (null != lastUpdatedVehicle && !lastUpdatedVehicle.isEmpty() && lastUpdatedVehicle.get(0).getEndTime().isAfter(cdr.getStartTime())) {
            throw new InvalidChangeDetailsRecordException("End time of last updated vehicle can not be greater than " +
                    "current start time");
        }
        return true;
    }
}

