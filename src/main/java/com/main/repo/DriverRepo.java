package com.main.repo;

import com.main.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepo extends JpaRepository<DriverEntity,Long> {
    Optional<DriverEntity> findByContactNo(String contactNo);
}
