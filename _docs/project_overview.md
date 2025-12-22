# Project Overview - Rental App

## Tujuan
Membangun aplikasi rental kendaraan dengan fokus pada:
- Manajemen kendaraan yang disewakan (CRUD, status ketersediaan, harga, media).
- Landing page dengan dashboard sederhana agar user bisa:
  - Memilih kendaraan.
  - Memilih tanggal order (opsional) untuk melihat ketersediaan.
  - Diredirect ke WhatsApp untuk proses pemesanan.

## Lingkup Fitur
### 1) Manajemen Kendaraan
- Data kendaraan: nama, tipe, merek, tahun, transmisi, kapasitas, harga harian, deskripsi, status (available/rented/unavailable/maintenance), media (foto).
- CRUD kendaraan.
- Manajemen kendaraan saat di rental:
  - Status kendaraan otomatis mengikuti data rental.
  - Admin/Staff mengisi form saat rental dibuat dan saat kendaraan dikembalikan.
  - Sistem memperbarui status kendaraan berdasarkan rental aktif.
- Pengelompokan kategori (opsional): mobil, motor, premium, dll.
- Ketersediaan kendaraan ditentukan dari rental aktif dan periode sewa.

### 2) Landing Page / Dashboard
- Daftar kendaraan dengan filter dan pencarian dasar.
- Detail kendaraan (foto, spesifikasi, harga, status).
- Semua kendaraan tetap ditampilkan.
  - Kendaraan yang tidak available diberi flag/tanda agar user memahami statusnya.
  - User dapat melihat tanggal available kendaraan.
- Pemilihan tanggal order (opsional):
  - Jika diisi, gunakan untuk menandai ketersediaan kendaraan di rentang tanggal.
  - Jika kosong, tampilkan status ketersediaan saat ini.
- Tombol CTA “Pesan via WhatsApp”:
  - Redirect ke WA dengan template pesan otomatis (berisi nama kendaraan dan tanggal jika ada).

## Peran Pengguna
- Admin/Staff: mengelola data kendaraan dan ketersediaan.
- User publik: melihat daftar kendaraan, memilih tanggal, dan menghubungi via WhatsApp.

## Alur Utama
1) User membuka landing page.
2) User memilih tanggal (opsional).
3) Sistem menampilkan semua kendaraan dengan flag status dan info tanggal available.
4) User memilih kendaraan dan klik tombol WA.
5) Sistem membuka WhatsApp dengan pesan otomatis.

## Proses Rental (Detail)
1) User memilih kendaraan dan mengirim permintaan via WhatsApp.
2) Admin/Staff mencatat data peminjam dan detail rental:
   - Identitas peminjam (nama, nomor HP, alamat).
   - Identitas tambahan (no KTP/SIM, foto dokumen) jika diperlukan.
   - Tanggal mulai dan selesai.
   - Lokasi pickup/return (opsional).
   - Harga sewa, biaya tambahan, dan catatan.
3) Sistem menyimpan transaksi rental dan periode sewa aktif.
4) Status kendaraan otomatis menjadi "rented" selama rental aktif.
5) Saat kendaraan dikembalikan, Admin/Staff menutup rental:
   - Sistem otomatis mengubah status kendaraan ke "available".
   - Mencatat waktu kembali dan kondisi kendaraan (opsional).

## Struktur Respons API (Standar)
Mengikuti standar respons:

```json
{
  "errors": null,
  "data": {},
  "timestamp": "2025-10-29T15:03:24.230+07:00",
  "isSuccess": true
}
```

- Error validasi, bisnis, atau not found menggunakan format `errors` sesuai pedoman backend.

## Entitas Utama (Draft)
- Vehicle
  - id, name, brand, type, year, transmission, capacity, pricePerDay, description, status, images
- Rental
  - id, vehicleId, renterName, renterPhone, renterAddress, renterIdNumber, startDate, endDate, pickupLocation, returnLocation, priceTotal, status, notes
- Category (opsional)
  - id, name

## Integrasi WhatsApp
- Format pesan otomatis:
  - Nama kendaraan
  - Tanggal mulai & selesai (jika ada)
  - Info kontak user (opsional)

Contoh template URL:
```
https://wa.me/<PHONE_NUMBER>?text=Halo%2C%20saya%20ingin%20sewa%20<VEHICLE_NAME>%20tanggal%20<START_DATE>%20sampai%20<END_DATE>
```

## Catatan Teknis
- ORM: Spring Data JPA (Hibernate).
- Logging sesuai environment (dev vs prod).
- Struktur proyek feature-driven.
- Gunakan Javadoc untuk dokumentasi publik.

## Out of Scope (Tahap Awal)
- Pembayaran online.
- Integrasi kalender pihak ketiga.
- Akun user dan autentikasi.
