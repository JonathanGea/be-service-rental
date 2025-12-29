package com.gea.app.vehicle;

import com.gea.app.vehicle.entity.VehicleType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for VehicleType entity.
 */
public interface VehicleTypeRepository extends JpaRepository<VehicleType, UUID> {
    Optional<VehicleType> findByNameIgnoreCase(String name);
}
