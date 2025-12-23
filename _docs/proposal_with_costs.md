# Proposal Aplikasi Rental Kendaraan (Dengan Biaya dan Timeline)

## Ringkasan
Aplikasi ini adalah sistem rental kendaraan end-to-end dengan alur sederhana: user memilih kendaraan di landing page, memilih tanggal (opsional), lalu diarahkan ke WhatsApp untuk pemesanan. Admin/Staff mengelola data kendaraan, transaksi rental, foto, dan ketersediaan melalui backend API. Antarmuka pengguna telah siap digunakan dan terintegrasi dengan backend ini.

## Profil Singkat
- Client: PT Client Rental (placeholder)
- Vendor: PT Penyedia Teknologi (placeholder)

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

## Integrasi UI
- Endpoint public menyediakan data list/detail dan link WhatsApp siap pakai.
- Query ketersediaan mendukung startDate/endDate.
- Format data konsisten untuk kebutuhan tampilan dan interaksi.

## Deliverables
- Source code backend siap produksi.
- Dokumen API dan panduan testing (Postman).
- Kontrak API untuk integrasi UI.
- Checklist deployment dan konfigurasi environment.

## Timeline Implementasi (Estimasi)
- Minggu 1: Setup environment, validasi API, data seeding, dan konfigurasi keamanan.
- Minggu 2: Integrasi UI, UAT, dan perbaikan feedback.
- Minggu 3: Hardening, monitoring, dan deployment production.

## Biaya Implementasi (Estimasi)
- Fase 1: Setup & Validasi API (Rp 8.000.000)
- Fase 2: Integrasi UI & UAT (Rp 12.000.000)
- Fase 3: Hardening & Deploy (Rp 10.000.000)
- Total: Rp 30.000.000

## Term Pembayaran
- 40 persen DP setelah PO dan kickoff.
- 40 persen setelah UAT selesai.
- 20 persen setelah go-live.

## Garansi dan Support
- Garansi bug fixing: 30 hari setelah go-live.
- Support: Senin-Jumat, 09.00-18.00 WIB.
- SLA respons awal: 1x24 jam kerja.

## Out of Scope
- Pembayaran online.
- Integrasi kalender pihak ketiga.
- Akun user publik dengan role kompleks.

## Next Steps
- Konfirmasi scope final dan lingkungan deployment.
- Penjadwalan UAT dan go-live.
