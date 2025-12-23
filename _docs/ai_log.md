# AI Log

## Konteks Proyek
- Aplikasi rental kendaraan dengan landing page publik, manajemen kendaraan, rental, dan integrasi WhatsApp.
- Status kendaraan otomatis mengikuti rental aktif; maintenance hanya boleh dari status available.
- Semua response mengikuti pola `ApiResponse` dengan struktur errors/data/timestamp/isSuccess.

## Dokumen Kunci
- `_docs/project_overview.md`: overview fitur dan entitas utama.
- `_docs/requirements_use_case.md`: daftar use case lengkap termasuk maintenance, foto, history, kalender.
- `_docs/api_specification_endpoints.md`: spesifikasi endpoint (Base URL: `/api`) dan contoh response standar.
- `_docs/todo.md`: checklist pengembangan endpoint + validasi khusus.
- `_docs/promp.md`: prompt kecil per modul (auth, users, vehicles, photos, rentals, history, calendar, public).
- `_docs/postman_testing.md`: pre/post-request dan testing minimal untuk Postman.
- `_docs/backend_development_standards_guidelines.md`: standar format response/error.

## Aturan Validasi Penting
- Register: email format, password min length.
- Login: email/password required.
- Vehicles: required fields, status transition; maintenance hanya dari available.
- Rentals: startDate <= endDate, overlap ditolak, kendaraan tidak boleh maintenance.
- Public/Calendar: date range valid bila diisi.

## Endpoint Utama (Ringkas)
- Auth: `POST /auth/register`, `POST /auth/login`.
- Users: `GET /users`, `GET /users/me`.
- Vehicles: CRUD + `PATCH /vehicles/{id}/status`.
- Photos: `POST/GET/PATCH/DELETE /vehicles/{id}/photos`.
- Rentals: `POST/GET/PATCH /rentals`, `GET /rentals/{id}`, `POST /rentals/{id}/return`.
- History: `GET /rentals/history`.
- Calendar: `GET /availability/calendar`.
- Public: `GET /public/vehicles`, `GET /public/vehicles/{id}`.

## Catatan Testing Postman
- Gunakan env `baseUrl` dan `accessToken`.
- Minimal tests: response status, response time < `{{acceptable_limits_time}}`, body sesuai `ApiResponse`.
