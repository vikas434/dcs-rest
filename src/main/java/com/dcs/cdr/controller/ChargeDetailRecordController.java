package com.dcs.cdr.controller;

import com.dcs.cdr.model.ChargeDetailRecord;
import com.dcs.cdr.service.ChargeDetailRecorderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/cdr")
public class ChargeDetailRecordController {

    @Autowired
    private ChargeDetailRecorderService cdrService;

    @GetMapping("/{cdrId}")
    public ResponseEntity<ChargeDetailRecord> getChargeDetailRecordById(@PathVariable Long cdrId) {
        ChargeDetailRecord cdr = cdrService.getChargeDetailRecordById(cdrId);
        if (cdr != null) {
            return ResponseEntity.ok(cdr);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<ChargeDetailRecord>> getAllChargeDetailRecordsByVehicleId(
            @PathVariable Long vehicleId,
            @RequestParam Optional<Sort.Direction> sortByStartTime,
            @RequestParam Optional<Sort.Direction> sortByEndTime) {
        List<ChargeDetailRecord> cdrList = cdrService.getAllChargeDetailRecordsByVehicleId(vehicleId, sortByStartTime, sortByEndTime);
        if (cdrList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(cdrList);
        }
    }

    @PostMapping
    public ResponseEntity<ChargeDetailRecord> createChargeDetailRecord(@Valid @RequestBody ChargeDetailRecord cdr) {
        ChargeDetailRecord savedCdr = cdrService.createChargeDetailRecord(cdr);
        return new ResponseEntity<>(savedCdr, HttpStatus.CREATED);
    }
}
