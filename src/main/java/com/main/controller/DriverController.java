package com.main.controller;

import com.main.dto.DriverDto;
import com.main.entity.DriverEntity;
import com.main.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
@Slf4j
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/add-driver")
    public ResponseEntity<DriverEntity> saveDriver(@RequestBody DriverEntity driverEntity){
        log.info("Request request received to save-driver...");
        DriverEntity driver = driverService.addDriver(driverEntity);
        return new ResponseEntity<>(driver, HttpStatus.CREATED);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverDto> getDriver(@PathVariable("driverId") Long driverId){
        log.info("Request received to fetch Driver by driverId:{}.", driverId);
        DriverDto driverDto = driverService.getDriver(driverId);
        return new ResponseEntity<>(driverDto, HttpStatus.OK);
    }

    @GetMapping("/get-all-Drivers")
    public ResponseEntity<List<DriverDto>> fetchAllDrivers(){
        log.info("Request received to fetch all drivers...");
        List<DriverDto> driverDtoList = driverService.getAllDrivers();
        return new ResponseEntity<>(driverDtoList, HttpStatus.OK);
    }

    @PatchMapping("/{driverId}")
    public ResponseEntity<DriverDto> updateDriverPartially(
            @PathVariable Long driverId,
            @RequestBody DriverDto driverDto) {

        log.info("Request received to patch driver with ID: {}", driverId);
        DriverDto updated = driverService.updateDriverPartial(driverId, driverDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{driverId}")
    public ResponseEntity<String> deleteDriver(@PathVariable Long driverId) {
        log.info("Request received to delete driver with ID: {}", driverId);

        String response = driverService.deleteDriver(driverId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
