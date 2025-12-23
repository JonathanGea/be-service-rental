# Proposal Aplikasi Rental Kendaraan

## Ringkasan
Aplikasi ini adalah sistem rental kendaraan end-to-end dengan alur sederhana: user memilih kendaraan di landing page, memilih tanggal (opsional), lalu diarahkan ke WhatsApp untuk pemesanan. Admin/Staff mengelola data kendaraan, transaksi rental, foto, dan ketersediaan melalui backend API. Antarmuka pengguna telah siap digunakan dan terintegrasi dengan backend ini.

## Tujuan dan Manfaat
- Mempercepat proses pemesanan dengan alur langsung ke WhatsApp.
- Menjaga data kendaraan dan status ketersediaan selalu akurat.
- Memberikan kontrol penuh ke Admin/Staff atas data rental dan inventori.
- Menyediakan API yang konsisten untuk integrasi UI dan future expansion.

## Cakupan Fitur
### Public (Landing Page)
- List kendaraan dengan pencarian dan filter kategori.
- Detail kendaraan lengkap (spesifikasi, harga, foto, status).
- Penandaan ketersediaan berdasarkan tanggal pilihan.
- Link WhatsApp otomatis dengan template pesan.

### Admin/Staff
- CRUD kendaraan.
- Update status kendaraan (available/unavailable/maintenance).
- Manajemen foto kendaraan (upload, urutkan, hapus).
- Buat rental, update rental, dan proses pengembalian.
- Riwayat rental dan kalender ketersediaan.

## Modul Backend dan API Utama
- Auth: register, login (JWT).
- Users: list users dan profile.
- Vehicles: CRUD, status update.
- Vehicle Photos: upload/list/update/delete.
- Rentals: create/list/detail/update/return.
- Availability: kalender ketersediaan per kendaraan.
- Public Vehicles: list dan detail untuk landing page.

## Arsitektur dan Teknologi
- Framework: Spring Boot.
- ORM: Spring Data JPA (Hibernate).
- Database: PostgreSQL.
- Auth: JWT.
- Dokumentasi API: OpenAPI/Swagger.
- Standar response: ApiResponse (errors/data/timestamp/isSuccess).

## Keamanan
- Endpoint public hanya untuk landing page dan auth.
- Endpoint admin/staff membutuhkan JWT.
- Error response konsisten untuk validasi, bisnis, dan not found.

## Integrasi UI
- Endpoint public menyediakan data list/detail dan link WhatsApp siap pakai.
- Query ketersediaan mendukung startDate/endDate.
- Format data konsisten untuk kebutuhan tampilan dan interaksi.

## Konfigurasi dan Deployment
- Menggunakan environment variables untuk DB, JWT secret, dan WhatsApp phone.
- Logging dibedakan untuk environment dev/prod.
- Siap dijalankan di VPS/Cloud dengan Java 21 dan PostgreSQL.

## Deliverables
- Source code backend siap produksi.
- Dokumen API dan panduan testing (Postman).
- Kontrak API untuk integrasi UI.

## Out of Scope (Tahap Awal)
- Pembayaran online.
- Integrasi kalender pihak ketiga.
- Akun user publik dengan role kompleks.

## Rencana Implementasi
- Minggu 1: Setup environment, validasi API, dan data seeding.
- Minggu 2: Integrasi, UAT, dan perbaikan feedback.
- Minggu 3: Hardening, monitoring, dan deployment production.

## Next Steps
- Konfirmasi scope final dan lingkungan deployment.
- Penjadwalan UAT dan go-live.
