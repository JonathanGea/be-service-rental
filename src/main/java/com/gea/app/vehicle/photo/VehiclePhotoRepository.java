package com.gea.app.vehicle.photo;

import com.gea.app.vehicle.photo.entity.VehiclePhoto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for vehicle photos.
 */
public interface VehiclePhotoRepository extends JpaRepository<VehiclePhoto, UUID> {
    List<VehiclePhoto> findByVehicleIdOrderByOrderIndexAsc(UUID vehicleId);
    Optional<VehiclePhoto> findByIdAndVehicleId(UUID id, UUID vehicleId);
    long countByVehicleId(UUID vehicleId);
}
