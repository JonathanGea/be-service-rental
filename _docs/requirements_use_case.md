# Requirements Use Case - Rental App

## 1) Kelola Data Kendaraan
**Aktor:** Admin/Staff

**Description dan Tujuan:**
Memungkinkan Admin/Staff menambah, melihat, mengubah, dan menghapus data kendaraan agar daftar kendaraan selalu akurat.

**Kondisi Awal:**
Admin/Staff sudah memiliki akses ke sistem.

**Kondisi Akhir:**
Data kendaraan tersimpan dan dapat ditampilkan di landing page dengan status yang sesuai.

**Alur Sukses Utama:**
1) Admin/Staff membuka menu manajemen kendaraan.
2) Admin/Staff mengisi form data kendaraan.
3) Sistem memvalidasi dan menyimpan data.
4) Sistem menampilkan data kendaraan terbaru.

**Alur Alternatif:**
1) Admin/Staff mengubah data kendaraan yang sudah ada.
2) Sistem menyimpan perubahan dan memperbarui daftar.

**Pengecualian:**
- Data wajib tidak lengkap.
- Format data tidak valid.
- Perubahan status ke "maintenance" ditolak jika status kendaraan sedang "rented".

---

## 2) Lihat Daftar Kendaraan di Landing Page
**Aktor:** User Publik

**Description dan Tujuan:**
Memungkinkan user melihat semua kendaraan beserta status dan tanggal available untuk memilih kendaraan yang sesuai.

**Kondisi Awal:**
Landing page dapat diakses.

**Kondisi Akhir:**
User melihat daftar kendaraan dengan status dan info tanggal available.

**Alur Sukses Utama:**
1) User membuka landing page.
2) Sistem menampilkan semua kendaraan dengan flag status.
3) User melihat detail singkat dan tanggal available.

**Alur Alternatif:**
1) User menggunakan filter atau pencarian.
2) Sistem menampilkan kendaraan yang sesuai filter.

**Pengecualian:**
- Data kendaraan kosong.

---

## 3) Cek Ketersediaan Berdasarkan Tanggal
**Aktor:** User Publik

**Description dan Tujuan:**
Memungkinkan user memilih tanggal order untuk menandai ketersediaan kendaraan pada rentang tanggal tertentu.

**Kondisi Awal:**
User berada di landing page.

**Kondisi Akhir:**
Kendaraan ditandai available atau tidak available sesuai tanggal yang dipilih.

**Alur Sukses Utama:**
1) User memilih tanggal mulai dan tanggal selesai (opsional).
2) Sistem mengecek rental aktif pada periode tersebut.
3) Sistem menandai status ketersediaan kendaraan.

**Alur Alternatif:**
1) User tidak mengisi tanggal.
2) Sistem menampilkan status ketersediaan saat ini.

**Pengecualian:**
- Tanggal selesai lebih awal dari tanggal mulai.

---

## 4) Pemesanan via WhatsApp
**Aktor:** User Publik

**Description dan Tujuan:**
Memungkinkan user menghubungi Admin/Staff melalui WhatsApp dengan template pesan otomatis.

**Kondisi Awal:**
User memilih kendaraan pada landing page.

**Kondisi Akhir:**
WhatsApp terbuka dengan pesan berisi data kendaraan dan tanggal jika ada.

**Alur Sukses Utama:**
1) User klik tombol "Pesan via WhatsApp".
2) Sistem membuat template pesan.
3) Sistem redirect ke WhatsApp.

**Alur Alternatif:**
1) User belum memilih tanggal.
2) Sistem membuat pesan tanpa tanggal.

**Pengecualian:**
- Nomor WhatsApp tujuan tidak valid.

---

## 5) Buat Rental (Admin/Staff)
**Aktor:** Admin/Staff

**Description dan Tujuan:**
Memungkinkan Admin/Staff mencatat transaksi rental berdasarkan permintaan user.

**Kondisi Awal:**
User telah menghubungi Admin/Staff via WhatsApp.

**Kondisi Akhir:**
Rental tersimpan dan status kendaraan otomatis menjadi "rented" selama periode aktif.

**Alur Sukses Utama:**
1) Admin/Staff membuka form rental.
2) Admin/Staff mengisi data peminjam dan periode sewa.
3) Sistem memvalidasi dan menyimpan rental.
4) Sistem memperbarui status kendaraan sesuai rental aktif.

**Alur Alternatif:**
1) Admin/Staff mengubah detail rental sebelum disimpan.
2) Sistem menyimpan detail terbaru.

**Pengecualian:**
- Tanggal rental konflik dengan rental lain.
- Data peminjam tidak lengkap.

---

## 6) Pengembalian Kendaraan
**Aktor:** Admin/Staff

**Description dan Tujuan:**
Memungkinkan Admin/Staff menutup transaksi rental ketika kendaraan dikembalikan.

**Kondisi Awal:**
Ada rental aktif untuk kendaraan tersebut.

**Kondisi Akhir:**
Rental ditutup dan status kendaraan otomatis kembali "available".

**Alur Sukses Utama:**
1) Admin/Staff membuka detail rental aktif.
2) Admin/Staff mengisi data pengembalian.
3) Sistem menutup rental dan memperbarui status kendaraan.

**Alur Alternatif:**
1) Admin/Staff menambahkan catatan kondisi kendaraan.
2) Sistem menyimpan catatan tersebut.

**Pengecualian:**
- Rental aktif tidak ditemukan.

---

## 7) Ubah Status ke Maintenance
**Aktor:** Admin/Staff

**Description dan Tujuan:**
Memungkinkan Admin/Staff menandai kendaraan untuk perawatan agar tidak dapat disewa sementara waktu.

**Kondisi Awal:**
Kendaraan berstatus "available".

**Kondisi Akhir:**
Status kendaraan berubah menjadi "maintenance".

**Alur Sukses Utama:**
1) Admin/Staff membuka detail kendaraan.
2) Admin/Staff memilih aksi ubah status ke "maintenance".
3) Sistem memvalidasi status kendaraan saat ini.
4) Sistem menyimpan perubahan status.

**Alur Alternatif:**
1) Admin/Staff membatalkan aksi sebelum disimpan.
2) Sistem tidak mengubah status kendaraan.

**Pengecualian:**
- Kendaraan berstatus "rented" sehingga perubahan ke "maintenance" ditolak.

---

## 8) Upload/Kelola Foto Kendaraan
**Aktor:** Admin/Staff

**Description dan Tujuan:**
Memungkinkan Admin/Staff menambah, mengubah urutan, atau menghapus foto kendaraan agar informasi visual selalu terbaru.

**Kondisi Awal:**
Kendaraan sudah terdaftar di sistem.

**Kondisi Akhir:**
Foto kendaraan tersimpan dan ditampilkan pada detail kendaraan.

**Alur Sukses Utama:**
1) Admin/Staff membuka detail kendaraan.
2) Admin/Staff memilih aksi tambah/kelola foto.
3) Sistem memvalidasi file dan menyimpan foto.
4) Sistem menampilkan daftar foto terbaru.

**Alur Alternatif:**
1) Admin/Staff mengubah urutan foto.
2) Sistem menyimpan urutan baru.

**Pengecualian:**
- Format file tidak didukung.
- Ukuran file melebihi batas.

---

## 9) Lihat Riwayat Rental Kendaraan
**Aktor:** Admin/Staff

**Description dan Tujuan:**
Memungkinkan Admin/Staff melihat histori rental untuk audit dan pelacakan penggunaan kendaraan.

**Kondisi Awal:**
Ada data rental yang tersimpan.

**Kondisi Akhir:**
Riwayat rental kendaraan ditampilkan sesuai filter yang dipilih.

**Alur Sukses Utama:**
1) Admin/Staff membuka menu riwayat rental.
2) Admin/Staff memilih kendaraan atau filter periode.
3) Sistem menampilkan daftar rental terkait.

**Alur Alternatif:**
1) Admin/Staff mengekspor data riwayat (opsional).
2) Sistem menghasilkan file ekspor.

**Pengecualian:**
- Data riwayat tidak ditemukan.

---

## 10) Lihat Kalender Ketersediaan (Admin)
**Aktor:** Admin/Staff

**Description dan Tujuan:**
Memungkinkan Admin/Staff melihat kalender ketersediaan kendaraan berdasarkan rental aktif dan periode sewa.

**Kondisi Awal:**
Kendaraan sudah terdaftar dan memiliki data rental.

**Kondisi Akhir:**
Kalender ketersediaan tampil dengan status per tanggal.

**Alur Sukses Utama:**
1) Admin/Staff membuka menu kalender ketersediaan.
2) Admin/Staff memilih kendaraan atau rentang tanggal.
3) Sistem menampilkan kalender dengan status available/rented/maintenance.

**Alur Alternatif:**
1) Admin/Staff mengganti rentang tanggal.
2) Sistem memperbarui tampilan kalender.

**Pengecualian:**
- Data ketersediaan tidak ditemukan.
