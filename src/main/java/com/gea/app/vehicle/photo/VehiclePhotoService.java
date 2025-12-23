package com.gea.app.vehicle.photo;

import com.gea.app.shared.exception.ApiError;
import com.gea.app.shared.exception.ApiException;
import com.gea.app.shared.util.SupabaseStorageUploader;
import com.gea.app.vehicle.VehicleRepository;
import com.gea.app.vehicle.entity.Vehicle;
import com.gea.app.vehicle.photo.dto.VehiclePhotoResponse;
import com.gea.app.vehicle.photo.dto.VehiclePhotoUpdateRequest;
import com.gea.app.vehicle.photo.entity.VehiclePhoto;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Business logic for vehicle photo endpoints.
 */
@Service
@RequiredArgsConstructor
public class VehiclePhotoService {

    private static final Logger log = LoggerFactory.getLogger(VehiclePhotoService.class);

    private static final long MAX_FILE_SIZE_BYTES = 5L * 1024 * 1024;
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private final VehiclePhotoRepository vehiclePhotoRepository;
    private final VehicleRepository vehicleRepository;
    private final SupabaseStorageUploader storageUploader;

    public VehiclePhotoResponse uploadPhoto(UUID vehicleId, MultipartFile file, String caption) {
        Vehicle vehicle = getVehicleOrThrow(vehicleId);
        validateFile(file);

        String url;
        try {
            url = storageUploader.uploadFile("vehicle-photos", file);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, ApiError.builder()
                    .code("INTERNAL_SERVER_ERROR")
                    .message("Terjadi kesalahan internal yang tidak terduga. Silakan coba lagi nanti atau hubungi administrator.")
                    .details(Map.of("suggestion", "Please provide the traceId to support staff for faster assistance."))
                    .build());
        }

        int orderIndex = (int) (vehiclePhotoRepository.countByVehicleId(vehicleId) + 1);
        VehiclePhoto photo = VehiclePhoto.builder()
                .vehicle(vehicle)
                .url(url)
                .caption(caption)
                .orderIndex(orderIndex)
                .build();

        VehiclePhoto saved = vehiclePhotoRepository.save(photo);
        log.info("Vehicle photo uploaded id={} vehicleId={}", saved.getId(), vehicleId);
        return toResponse(saved);
    }

    public List<VehiclePhotoResponse> listPhotos(UUID vehicleId) {
        ensureVehicleExists(vehicleId);
        return vehiclePhotoRepository.findByVehicleIdOrderByOrderIndexAsc(vehicleId).stream()
                .map(this::toResponse)
                .toList();
    }

    public VehiclePhotoResponse updatePhoto(UUID vehicleId, UUID photoId, VehiclePhotoUpdateRequest request) {
        VehiclePhoto photo = getPhotoOrThrow(vehicleId, photoId);
        if (request.getOrder() != null) {
            photo.setOrderIndex(request.getOrder());
        }
        if (request.getCaption() != null) {
            photo.setCaption(request.getCaption());
        }
        VehiclePhoto saved = vehiclePhotoRepository.save(photo);
        log.info("Vehicle photo updated id={} vehicleId={}", saved.getId(), vehicleId);
        return toResponse(saved);
    }

    public Map<String, Object> deletePhoto(UUID vehicleId, UUID photoId) {
        VehiclePhoto photo = getPhotoOrThrow(vehicleId, photoId);
        vehiclePhotoRepository.delete(photo);
        log.info("Vehicle photo deleted id={} vehicleId={}", photoId, vehicleId);
        return Map.of("deleted", true);
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_BLANK")
                    .message("File tidak boleh kosong.")
                    .field("file")
                    .build());
        }
        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_INVALID")
                    .message("Ukuran file melebihi batas.")
                    .field("file")
                    .details(Map.of("maxSizeBytes", MAX_FILE_SIZE_BYTES))
                    .build());
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_INVALID")
                    .message("Tipe file tidak didukung.")
                    .field("file")
                    .details(Map.of("allowed", ALLOWED_CONTENT_TYPES))
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

    private void ensureVehicleExists(UUID vehicleId) {
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new ApiException(HttpStatus.NOT_FOUND, ApiError.builder()
                    .code("RESOURCE_NOT_FOUND")
                    .message("Sumber daya kendaraan dengan ID '" + vehicleId + "' tidak ditemukan.")
                    .details(Map.of("resourceType", "Vehicle", "identifier", vehicleId.toString()))
                    .build());
        }
    }

    private VehiclePhoto getPhotoOrThrow(UUID vehicleId, UUID photoId) {
        return vehiclePhotoRepository.findByIdAndVehicleId(photoId, vehicleId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiError.builder()
                        .code("RESOURCE_NOT_FOUND")
                        .message("Foto kendaraan dengan ID '" + photoId + "' tidak ditemukan.")
                        .details(Map.of("resourceType", "VehiclePhoto", "identifier", photoId.toString()))
                        .build()));
    }

    private VehiclePhotoResponse toResponse(VehiclePhoto photo) {
        return new VehiclePhotoResponse(
                photo.getId(),
                photo.getVehicle().getId(),
                photo.getUrl(),
                photo.getCaption(),
                photo.getOrderIndex()
        );
    }
}
