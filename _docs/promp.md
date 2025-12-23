# Prompt - Development Guides (Small Scope)

Dokumen ini memecah scope pekerjaan menjadi prompt kecil per modul agar pengerjaan bertahap.

## Konteks Umum (Berlaku untuk Semua Prompt)
- Backend: Spring Boot + Spring Data JPA
- Struktur proyek: feature-driven
- Response: wajib menggunakan pola `ApiResponse`
- Error: gunakan `code/message/field/details` sesuai `_docs/backend_development_standards_guidelines.md`
- Ikuti checklist `_docs/todo.md` dan spesifikasi `_docs/api_specification_endpoints.md`
- Sertakan kebutuhan pre-request/post-request untuk testing Postman di `_docs/postman_testing.md` bila diperlukan.
- Sertakan minimal testing di setiap prompt:
  - Check if the response status.
  - Validate response time is less than `{{acceptable_limits_time}}`.
  - Validate that the response body is correct.

## Referensi
- `_docs/api_specification_endpoints.md`
- `_docs/todo.md`
- `_docs/project_overview.md`
- `_docs/requirements_use_case.md`
- `_docs/backend_development_standards_guidelines.md`
---

## Prompt 1 - Auth
**Tujuan:** Implementasikan endpoint auth.

**Scope:**
- `POST /auth/register`
- `POST /auth/login`

**Validasi Khusus:**
- Register: email format, password min length.
- Login: email dan password wajib.

**Output:**
- Endpoint berjalan + error code sesuai standar.

---

## Prompt 2 - Users
**Tujuan:** Implementasikan endpoint user.

**Scope:**
- `GET /users`
- `GET /users/me`

**Validasi Khusus:**
- Auth required

**Output:**
- Endpoint berjalan + response standar.

---

## Prompt 3 - Vehicles (Admin/Staff)
**Tujuan:** Implementasikan CRUD kendaraan dan status.

**Scope:**
- `POST /vehicles`
- `GET /vehicles`
- `GET /vehicles/{id}`
- `PUT /vehicles/{id}`
- `DELETE /vehicles/{id}`
- `PATCH /vehicles/{id}/status`

**Validasi Khusus:**
- Required fields, status transition.
- `maintenance` hanya dari `available`.

**Output:**
- Endpoint berjalan + status rules enforced.

---

## Prompt 4 - Vehicle Photos (Admin/Staff)
**Tujuan:** Upload dan kelola foto kendaraan.

**Scope:**
- `POST /vehicles/{id}/photos`
- `GET /vehicles/{id}/photos`
- `PATCH /vehicles/{id}/photos/{photoId}`
- `DELETE /vehicles/{id}/photos/{photoId}`

**Validasi Khusus:**
- File type/size.

**Output:**
- Upload + list foto bekerja.

---

## Prompt 5 - Rentals (Admin/Staff)
**Tujuan:** Implementasikan flow rental dan pengembalian.

**Scope:**
- `POST /rentals`
- `GET /rentals`
- `GET /rentals/{id}`
- `PATCH /rentals/{id}`
- `POST /rentals/{id}/return`

**Validasi Khusus:**
- startDate <= endDate.
- overlap rental ditolak.
- kendaraan tidak boleh di maintenance.

**Output:**
- Status kendaraan otomatis mengikuti rental aktif.

---

## Prompt 6 - Rental History (Admin/Staff)
**Tujuan:** Riwayat rental kendaraan.

**Scope:**
- `GET /rentals/history`

**Validasi Khusus:**
- Filter tanggal/vehicleId.

**Output:**
- Data history tampil sesuai filter.

---

## Prompt 7 - Availability Calendar (Admin/Staff)
**Tujuan:** Kalender ketersediaan.

**Scope:**
- `GET /availability/calendar`

**Validasi Khusus:**
- vehicleId dan date range valid.

**Output:**
- Kalender status per tanggal.

---

## Prompt 8 - Public Landing
**Tujuan:** Endpoint publik untuk landing page.

**Scope:**
- `GET /public/vehicles`
- `GET /public/vehicles/{id}`

**Validasi Khusus:**
- Date range valid jika diisi.

**Output:**
- Data kendaraan + availability tampil sesuai filter.

---

## Referensi
- `_docs/api_specification_endpoints.md`
- `_docs/todo.md`
- `_docs/project_overview.md`
- `_docs/requirements_use_case.md`
- `_docs/backend_development_standards_guidelines.md`


temukan apa yang salah di error berikut lalu rekomendasi perbaikan lalu documentasikan di _docs/errors.md, lalu perbaiki code nya, 
pastikan tidak error lagi dengan coba jalankan kembali jika berhasil jangan lupa kill kembali: 
