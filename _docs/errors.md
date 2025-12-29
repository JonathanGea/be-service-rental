# Errors Log

## 2025-12-23 - mvn spring-boot:run

**Error**
- Compilation failure: `RentalRepository.java` tidak mengenal `List`.

**Root Cause**
- Missing import `java.util.List` di `RentalRepository`.

**Fix**
- Tambahkan `import java.util.List;` pada `src/main/java/com/gea/app/rental/RentalRepository.java`.

**Notes**
- Warning tambahan: Maven menampilkan peringatan root directory (tidak ada `.mvn`/`root="true"`) dan akses repo snapshot 503. Ini tidak memblokir build, tapi sebaiknya ditangani bila muncul berulang.

---

## 2025-12-23 - mvn spring-boot:run

**Error**
- Compilation failure: lambda memakai variabel `cursor` yang berubah di loop sehingga tidak efektif final.

**Root Cause**
- `cursor` direassign dalam `while`, tetapi direferensikan di lambda `anyMatch` pada `PublicVehicleService`.

**Fix**
- Gunakan variabel lokal `current` di dalam loop untuk dipakai pada lambda.

**Notes**
- Warning Maven root directory dan repo snapshot 503 masih muncul dan tidak memblokir kompilasi.

---

## 2025-12-29 - GET /brands

**Error**
- Response `data` kosong, list berada di `errors` meskipun `isSuccess=true`.

**Root Cause**
- Konstruktor `ApiResponse(boolean, List)` bentrok dengan `ApiResponse(boolean, T)` saat `T` adalah `List`,
  sehingga payload list dianggap sebagai error.

**Fix**
- Tambahkan factory `ApiResponse.success(data)`/`ApiResponse.failure(errors)` dan gunakan di seluruh controller/handler.

**Notes**
- Semua response list (users, vehicles, rentals, photos, brands, vehicle-types) ikut terdampak sebelum perbaikan.

---

## 2025-12-23 - mvn spring-boot:run

**Error**
- Maven gagal dijalankan karena membutuhkan Java 17+ (terdeteksi Java 11).

**Root Cause**
- Versi Maven 4.x di environment mewajibkan JDK 17+.

**Fix**
- Set `JAVA_HOME` ke JDK 17+ sebelum menjalankan Maven, contoh:
  `JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64 PATH=/usr/lib/jvm/java-21-openjdk-amd64/bin:$PATH mvn spring-boot:run`.
