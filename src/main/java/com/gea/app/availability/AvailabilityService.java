package com.gea.app.availability;

import com.gea.app.availability.dto.AvailabilityCalendarResponse;
import com.gea.app.availability.dto.AvailabilityDateStatus;
import com.gea.app.rental.RentalRepository;
import com.gea.app.rental.entity.Rental;
import com.gea.app.rental.enum_.RentalStatus;
import com.gea.app.shared.exception.ApiError;
import com.gea.app.shared.exception.ApiException;
import com.gea.app.vehicle.VehicleRepository;
import com.gea.app.vehicle.entity.Vehicle;
import com.gea.app.vehicle.enum_.VehicleStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Business logic for availability calendar.
 */
@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private static final Set<RentalStatus> BLOCKING_STATUSES = Set.of(
            RentalStatus.PENDING,
            RentalStatus.ACTIVE
    );

    private final VehicleRepository vehicleRepository;
    private final RentalRepository rentalRepository;

    public AvailabilityCalendarResponse getCalendar(UUID vehicleId, LocalDate startDate, LocalDate endDate) {
        if (vehicleId == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_BLANK")
                    .message("Kolom 'vehicleId' tidak boleh kosong.")
                    .field("vehicleId")
                    .build());
        }
        validateDateRange(startDate, endDate);
        Vehicle vehicle = getVehicleOrThrow(vehicleId);

        List<Rental> rentals = rentalRepository.findByVehicleIdAndStatusInAndDateRange(
                vehicleId, BLOCKING_STATUSES, startDate, endDate
        );

        List<AvailabilityDateStatus> dates = new ArrayList<>();
        LocalDate cursor = startDate;
        while (!cursor.isAfter(endDate)) {
            String status = resolveStatus(vehicle, rentals, cursor);
            dates.add(new AvailabilityDateStatus(cursor, status));
            cursor = cursor.plusDays(1);
        }

        return new AvailabilityCalendarResponse(vehicleId, dates);
    }

    private String resolveStatus(Vehicle vehicle, List<Rental> rentals, LocalDate date) {
        if (vehicle.getStatus() == VehicleStatus.MAINTENANCE) {
            return VehicleStatus.MAINTENANCE.getValue();
        }
        if (vehicle.getStatus() == VehicleStatus.UNAVAILABLE) {
            return VehicleStatus.UNAVAILABLE.getValue();
        }
        boolean rented = rentals.stream().anyMatch(rental ->
                !date.isBefore(rental.getStartDate()) && !date.isAfter(rental.getEndDate())
        );
        if (rented) {
            return VehicleStatus.RENTED.getValue();
        }
        return VehicleStatus.AVAILABLE.getValue();
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_BLANK")
                    .message("Tanggal mulai dan selesai wajib diisi.")
                    .field("startDate")
                    .details(Map.of("required", List.of("startDate", "endDate")))
                    .build());
        }
        if (startDate.isAfter(endDate)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_INVALID")
                    .message("Tanggal mulai tidak boleh lebih dari tanggal selesai.")
                    .field("startDate")
                    .details(Map.of("startDate", startDate.toString(), "endDate", endDate.toString()))
                    .build());
        }
    }

    private Vehicle getVehicleOrThrow(UUID vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiError.builder()
                        .code("RESOURCE_NOT_FOUND")
                        .message("Sumber daya kendaraan dengan ID '" + vehicleId + "' tidak ditemukan.")
                        .details(Map.of("resourceType", "Vehicle", "identifier", vehicleId.toString()))
                        .build()));
    }
}
