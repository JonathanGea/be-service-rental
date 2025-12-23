package com.gea.app.rental;

import com.gea.app.rental.entity.Rental;
import com.gea.app.rental.enum_.RentalStatus;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for Rental entity.
 */
public interface RentalRepository extends JpaRepository<Rental, UUID>, JpaSpecificationExecutor<Rental> {

    @Query("""
            select count(r) > 0 from Rental r
            where r.vehicle.id = :vehicleId
              and r.status in :statuses
              and r.startDate <= :endDate
              and r.endDate >= :startDate
              and (:excludeId is null or r.id <> :excludeId)
            """)
    boolean existsOverlappingRental(
            @Param("vehicleId") UUID vehicleId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("statuses") Collection<RentalStatus> statuses,
            @Param("excludeId") UUID excludeId);

    @Query("""
            select r from Rental r
            where r.vehicle.id = :vehicleId
              and r.status in :statuses
              and r.startDate <= :endDate
              and r.endDate >= :startDate
            """)
    List<Rental> findByVehicleIdAndStatusInAndDateRange(
            @Param("vehicleId") UUID vehicleId,
            @Param("statuses") Collection<RentalStatus> statuses,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
