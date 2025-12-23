package com.gea.app.vehicle.photo;

import com.gea.app.shared.model.dto.ApiResponse;
import com.gea.app.vehicle.photo.dto.VehiclePhotoResponse;
import com.gea.app.vehicle.photo.dto.VehiclePhotoUpdateRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Vehicle photo endpoints for Admin/Staff.
 */
@RestController
@RequestMapping("/api/vehicles/{id}/photos")
@RequiredArgsConstructor
public class VehiclePhotoController {

    private final VehiclePhotoService vehiclePhotoService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<VehiclePhotoResponse>> uploadPhoto(
            @PathVariable("id") UUID vehicleId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "caption", required = false) String caption) {
        var data = vehiclePhotoService.uploadPhoto(vehicleId, file, caption);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VehiclePhotoResponse>>> listPhotos(@PathVariable("id") UUID vehicleId) {
        var data = vehiclePhotoService.listPhotos(vehicleId);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @PatchMapping("/{photoId}")
    public ResponseEntity<ApiResponse<VehiclePhotoResponse>> updatePhoto(
            @PathVariable("id") UUID vehicleId,
            @PathVariable UUID photoId,
            @Valid @RequestBody VehiclePhotoUpdateRequest request) {
        var data = vehiclePhotoService.updatePhoto(vehicleId, photoId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> deletePhoto(
            @PathVariable("id") UUID vehicleId,
            @PathVariable UUID photoId) {
        var data = vehiclePhotoService.deletePhoto(vehicleId, photoId);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }
}
