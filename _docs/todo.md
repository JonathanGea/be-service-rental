# TODO - API Development

## Foundation
- [ ] Review and align response/error format with `ApiResponse` standard.
- [ ] Define enums for status: vehicle (available/rented/unavailable/maintenance) and rental (pending/active/completed/cancelled).
- [ ] Add DTOs for request/response per endpoint.

## Auth
- [ ] Implement `POST /auth/register` (validation + USER_ALREADY_EXISTS error).
- [ ] Implement `POST /auth/login` (validation + UNAUTHORIZED_ACCESS error).

## Users
- [x] Implement `GET /users` (admin-only if needed).
- [x] Implement `GET /users/me` (current user profile).

## Vehicles (Admin/Staff)
- [x] Implement `POST /vehicles`.
- [x] Implement `GET /vehicles` with filters (status/categoryId/q).
- [x] Implement `GET /vehicles/{id}`.
- [x] Implement `PUT /vehicles/{id}`.
- [x] Implement `DELETE /vehicles/{id}`.
- [x] Implement `PATCH /vehicles/{id}/status` with rule: only available -> maintenance; rented is system-managed.

## Vehicle Photos (Admin/Staff)
- [x] Implement `POST /vehicles/{id}/photos` (multipart upload).
- [x] Implement `GET /vehicles/{id}/photos`.
- [x] Implement `PATCH /vehicles/{id}/photos/{photoId}`.
- [x] Implement `DELETE /vehicles/{id}/photos/{photoId}`.

## Rentals (Admin/Staff)
- [x] Implement `POST /rentals` with date overlap validation.
- [x] Implement `GET /rentals` with filters (vehicleId/status/startDate/endDate).
- [x] Implement `GET /rentals/{id}`.
- [x] Implement `PATCH /rentals/{id}` (policy-based update).
- [x] Implement `POST /rentals/{id}/return` (close rental + vehicle available).

## Rental History (Admin/Staff)
- [x] Implement `GET /rentals/history` (filters: vehicleId/startDate/endDate).

## Availability Calendar (Admin/Staff)
- [x] Implement `GET /availability/calendar` (vehicleId/startDate/endDate).

## Public Landing
- [x] Implement `GET /public/vehicles` (filters: startDate/endDate/q/categoryId).
- [x] Implement `GET /public/vehicles/{id}`.

## Cross-Cutting
- [x] Add feature-driven packages for vehicle, rental, photo, category.
- [x] Add validation and standardized error codes for all endpoints.
- [x] Add logging for key actions (create/update/rental/return).
- [ ] Add tests for core flows (auth, rental overlap, return updates status).

Temuan utama (urut berdasarkan severity) setelah membandingkan kode dengan _docs/project_overview.md, _docs/requirements_use_case.md, _docs/backend_development_standards_guidelines.md, dan _docs/
  api_specification_endpoints.md:

  - High: Endpoint pengembalian rental tidak menerima returnDate/conditionNotes dan tidak menyimpan data tersebut, padahal diwajibkan di use case & API spec. POST /rentals/{id}/return tidak punya request body dan
    Rental/RentalResponse juga tidak punya field terkait. Rujukan: src/main/java/com/gea/app/rental/RentalController.java:72, src/main/java/com/gea/app/rental/RentalService.java:176, src/main/java/com/gea/app/rental/
    entity/Rental.java:58, src/main/java/com/gea/app/rental/dto/RentalResponse.java:13
  - Medium: Landing page diminta menampilkan tanggal available pada daftar kendaraan, namun nextAvailableDate dikembalikan null saat tidak ada startDate/endDate (padahal daftar tetap ditampilkan). Rujukan: src/main/
    java/com/gea/app/public_/PublicVehicleService.java:163
  - Medium: Rental baru masih bisa dibuat untuk kendaraan berstatus unavailable, padahal status ini secara bisnis seharusnya tidak bisa dirental (sejalan dengan aturan status non-available di overview). Rujukan: src/
    main/java/com/gea/app/rental/RentalService.java:222
  - Low: Format timestamp di ApiResponse kemungkinan tidak match contoh +07:00 di docs karena memakai java.util.Date tanpa konfigurasi timezone Jackson. Rujukan: src/main/java/com/gea/app/shared/model/dto/
    ApiResponse.java:12, src/main/resources/application.yaml

  Catatan singkat

  - Struktur feature-driven, penggunaan ApiResponse, dan logging sudah konsisten dengan guideline.
  - Endpoint yang tercantum di API spec (auth/users/vehicles/photos/rentals/history/availability/public) sudah ada, tapi beberapa field/behavior masih belum sinkron seperti di poin di atas.

  Jika mau, saya bisa lanjutkan dengan perbaikan kode atau update dokumen agar konsisten. Next steps yang masuk akal:

  1. Tambahkan returnDate dan conditionNotes ke entity/DTO + request body POST /rentals/{id}/return.
  2. Hitung nextAvailableDate walau tanpa query date (pakai rental aktif/terdekat).
  3. Blok rental saat status kendaraan unavailable, atau klarifikasi aturan status di docs.