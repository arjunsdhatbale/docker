package com.main.service;

import com.main.dto.DriverDto;
import com.main.entity.DriverEntity;
import com.main.exception.DriverNotFoundException;
import com.main.exception.DuplicateDriverException;
 import com.main.repo.DriverRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverService {

    private final DriverRepo driverRepo;
    private final ModelMapper modelMapper;

    @Transactional
    public DriverEntity addDriver(DriverEntity driverEntity) {
        log.info("Service request received to save driver...");

        // Check if driver already exists
        Optional<DriverEntity> isExistDriver = driverRepo.findByContactNo(driverEntity.getContactNo());

        if (isExistDriver.isPresent()) {
            throw new DuplicateDriverException("Driver already exists with contact number: "
                    + driverEntity.getContactNo());
        }


        // Save driver
        DriverEntity driver = driverRepo.save(driverEntity);
        log.info("Driver saved successfully with ID: {}", driver.getDriverId());
        return driver;
    }

    public DriverDto getDriver(Long driverId) {
        log.info("Service request received to get Driver.");

        DriverEntity driver = driverRepo.findById(driverId)
                .orElseThrow(() ->
                        new DriverNotFoundException("Driver not found with ID: " + driverId));

        return modelMapper.map(driver, DriverDto.class);
    }

    public List<DriverDto> getAllDrivers() {
        log.info("Service request received to fetch all drivers...");

        List<DriverEntity> drivers = driverRepo.findAll(Sort.by(Sort.Direction.DESC, "driverId"));


        return drivers.stream()
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .toList();
    }

    public DriverDto updateDriverPartial(Long driverId, DriverDto driverDto) {
        log.info("Service request received to partially update Driver with ID: {}", driverId);

        // Fetch existing driver or throw exception
        DriverEntity existingDriver = driverRepo.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException(
                        "Driver not found with ID: " + driverId
                ));

        // Update only NON-NULL fields from DTO to Entity
        modelMapper.map(driverDto, existingDriver);

        // Save updated entity
        DriverEntity updatedDriver = driverRepo.save(existingDriver);

        // Convert back to DTO
        return modelMapper.map(updatedDriver, DriverDto.class);
    }

    public String deleteDriver(Long driverId) {
        log.info("Service request received to delete Driver with ID: {}", driverId);

        DriverEntity driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException(
                        "Driver not found with ID: " + driverId
                ));

        driverRepo.delete(driver);

        return "Driver deleted successfully with ID: " + driverId;
    }
}
