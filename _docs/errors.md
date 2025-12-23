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
