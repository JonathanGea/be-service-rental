package com.gea.app.vehicle;

import com.gea.app.shared.exception.ApiError;
import com.gea.app.shared.exception.ApiException;
import com.gea.app.vehicle.dto.BrandRequest;
import com.gea.app.vehicle.dto.BrandResponse;
import com.gea.app.vehicle.entity.Brand;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Business logic for brand endpoints.
 */
@Service
@RequiredArgsConstructor
public class BrandService {

    private static final Logger log = LoggerFactory.getLogger(BrandService.class);

    private final BrandRepository brandRepository;

    public BrandResponse createBrand(BrandRequest request) {
        String name = normalizeName(request.getName(), "name");
        if (brandRepository.findByNameIgnoreCase(name).isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT, ApiError.builder()
                    .code("RESOURCE_ALREADY_EXISTS")
                    .message("Brand '" + name + "' sudah terdaftar.")
                    .field("name")
                    .build());
        }
        Brand saved = brandRepository.save(Brand.builder().name(name).build());
        log.info("Brand created id={}", saved.getId());
        return new BrandResponse(saved.getId(), saved.getName());
    }

    public List<BrandResponse> getBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brand -> new BrandResponse(brand.getId(), brand.getName()))
                .toList();
    }

    public BrandResponse getBrand(UUID id) {
        Brand brand = getBrandOrThrow(id);
        return new BrandResponse(brand.getId(), brand.getName());
    }

    public BrandResponse updateBrand(UUID id, BrandRequest request) {
        Brand brand = getBrandOrThrow(id);
        String name = normalizeName(request.getName(), "name");
        brandRepository.findByNameIgnoreCase(name)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ApiException(HttpStatus.CONFLICT, ApiError.builder()
                            .code("RESOURCE_ALREADY_EXISTS")
                            .message("Brand '" + name + "' sudah terdaftar.")
                            .field("name")
                            .build());
                });
        brand.setName(name);
        Brand saved = brandRepository.save(brand);
        log.info("Brand updated id={}", saved.getId());
        return new BrandResponse(saved.getId(), saved.getName());
    }

    private Brand getBrandOrThrow(UUID id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiError.builder()
                        .code("RESOURCE_NOT_FOUND")
                        .message("Sumber daya brand dengan ID '" + id + "' tidak ditemukan.")
                        .details(Map.of("resourceType", "Brand", "identifier", id.toString()))
                        .build()));
    }

    private String normalizeName(String raw, String field) {
        String name = raw != null ? raw.trim() : "";
        if (!StringUtils.hasText(name)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_BLANK")
                    .message("Kolom '" + field + "' tidak boleh kosong.")
                    .field(field)
                    .build());
        }
        return name;
    }
}
