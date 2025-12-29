package com.gea.app.vehicle;

import com.gea.app.vehicle.entity.Brand;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Brand entity.
 */
public interface BrandRepository extends JpaRepository<Brand, UUID> {
    Optional<Brand> findByNameIgnoreCase(String name);
}
