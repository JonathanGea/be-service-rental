backend_development_standards_guidelines

- **ORM:** Spring Data JPA dengan Hibernate
- **Javadoc:** Gunakan Javadoc untuk dokumentasi.
- **Logging:** Implementasikan logging yang efektif perhatikan level yang sesuai dengan enviroment development dan production
- Struktur Proyek menggunakan feature driven
    
    ```tsx
    ├── featureA
    │   │── FeatureAController.java
    │   │── FeatureAService.java
    │   │── FeatureARepository.java
    │   │── dto
    │       └── FeatureARequestDto.java
    │       └── FeatureAResponseDto.java
    │   │── entity
    │       └── FeatureA.java
    │   │── enum_
    │       └── FeatureA.java
    
    ```
    
- response
    - always use pattern @ApiResponse
        
        ```json
        {
          "errors": null,
          "data": {},
          "timestamp": "2025-10-29T15:03:24.230+07:00",
          "isSuccess": true
        }
        ```
        
    - Struktur Detail errors
        
        ```json
        {
          "code": "VALIDATION_BLANK", // Kode error unik, machine-readable
          "message": "Kolom 'username' tidak boleh kosong.", // Pesan error, human-readable
          "field": "username", // Opsional: Untuk error yang terkait dengan field input tertentu
          "details": {
            "expectedFormat": "alphanumeric",
            "minLength": 3
          }
        }
        ```
        
        code : 
        
        `USER_ALREADY_EXISTS`, `RESOURCE_NOT_FOUND`, `UNAUTHORIZED_ACCESS`, `INTERNAL_SERVER_ERROR` dst.
        
    
     **Error Validasi Input**
    
    ```json
    {
      "errors": [
        {
          "code": "VALIDATION_BLANK",
          "message": "Kolom 'username' tidak boleh kosong.",
          "field": "username"
        },
        {
          "code": "VALIDATION_MIN_LENGTH",
          "message": "Kolom 'password' minimal 8 karakter.",
          "field": "password",
          "details": {
            "minLength": 8
          }
        },
        {
          "code": "VALIDATION_INVALID_EMAIL",
          "message": "Format email tidak valid.",
          "field": "email"
        }
      ],
      "data": null,
      "timestamp": "2025-10-29T17:14:02.514+07:00",
      "isSuccess": false
    }
    ```
    
    **Error Bisnis atau Konflik**
    
    ```json
    {
      "errors": [
        {
          "code": "USER_ALREADY_EXISTS",
          "message": "Email 'john.doe@example.com' sudah terdaftar."
        }
      ],
      "data": null,
      "timestamp": "2025-10-29T17:16:05.456+07:00",
      "isSuccess": false
    }
    ```
    
    **Resource Tidak Ditemukan**
    
    ```json
    {
      "errors": [
        {
          "code": "RESOURCE_NOT_FOUND",
          "message": "Sumber daya pengguna dengan ID '123' tidak ditemukan.",
          "details": {
            "resourceType": "User",
            "identifier": "123"
          }
        }
      ],
      "data": null,
      "timestamp": "2025-10-29T17:15:30.123+07:00",
      "isSuccess": false
    }
    ```
    
    **Akses Tidak Sah / Tidak Memiliki Izin (401 Unauthorized / 403 Forbidden)**
    
    - **401 Unauthorized:** Klien gagal mengautentikasi (misal, token tidak valid atau tidak ada).
        
        ```json
        {
          "errors": [
            {
              "code": "UNAUTHORIZED_ACCESS",
              "message": "Anda tidak memiliki token otentikasi yang valid atau token telah kadaluarsa."
            }
          ],
          "data": null,
          "timestamp": "2025-10-29T17:20:00.000+07:00",
          "isSuccess": false
        }
        
        ```
        
    - **403 Forbidden:** Klien terautentikasi tetapi tidak memiliki izin untuk melakukan operasi tersebut.
        
        ```json
        {
          "errors": [
            {
              "code": "FORBIDDEN_ACCESS",
              "message": "Anda tidak memiliki izin untuk mengakses resource ini."
            }
          ],
          "data": null,
          "timestamp": "2025-10-29T17:21:00.000+07:00",
          "isSuccess": false
        }
        ```
        
    - **Error Internal Server**
        
        ```json
        {
          "errors": [
            {
              "code": "INTERNAL_SERVER_ERROR",
              "message": "Terjadi kesalahan internal yang tidak terduga. Silakan coba lagi nanti atau hubungi administrator.",
              "details": {
                 "suggestion": "Please provide the traceId to support staff for faster assistance."
              }
            }
          ],
          "data": null,
          "timestamp": "2025-10-29T17:17:10.789+07:00",
          "isSuccess": false
        }
        ```