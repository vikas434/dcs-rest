package com.dcs.cdr.repository;

import com.dcs.cdr.model.ChargeDetailRecord;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeDetailRecordRepository extends CrudRepository<ChargeDetailRecord, Long> {
    List<ChargeDetailRecord> findAllByVehicleId(Long vehicleId, Sort sort);

    ChargeDetailRecord findTop1ByVehicleIdOrderByEndTimeDesc(Long vehicleId);

}

