# Postman Testing Notes

Dokumen ini berisi pre-request dan post-request yang diperlukan untuk testing endpoint.

## Global Environment
- `baseUrl` = `http://localhost:8080/api`
- `accessToken` = diisi dari response login/register

## Global Pre-request (Opsional)
Gunakan ini jika ingin menyusun URL dan header otomatis.

```javascript
pm.environment.set("baseUrl", pm.environment.get("baseUrl") || "http://localhost:8080/api");
```

## Global Post-request (Opsional)
Gunakan untuk validasi response standar.

```javascript
pm.test("response is ApiResponse", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess");
  pm.expect(json).to.have.property("timestamp");
});
```

---

## Auth
### POST /auth/register
**Post-request:** simpan token untuk request berikutnya.

```javascript
const json = pm.response.json();
if (json && json.data && json.data.accessToken) {
  pm.environment.set("accessToken", json.data.accessToken);
}
```

### POST /auth/login
**Post-request:** simpan token untuk request berikutnya.

```javascript
const json = pm.response.json();
if (json && json.data && json.data.accessToken) {
  pm.environment.set("accessToken", json.data.accessToken);
}
```

---

## Users
### GET /users
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

### GET /users/me
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

---

## Vehicles (Admin/Staff)
### POST /vehicles
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request:** simpan `vehicleId`.

```javascript
const json = pm.response.json();
if (json && json.data && json.data.id) {
  pm.environment.set("vehicleId", json.data.id);
}
```

### GET /vehicles, GET /vehicles/{id}, PUT /vehicles/{id}, DELETE /vehicles/{id}, PATCH /vehicles/{id}/status
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

---

## Vehicle Photos (Admin/Staff)
### POST /vehicles/{id}/photos
**Pre-request:** set Authorization header + pastikan `vehicleId` tersedia.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

### GET /vehicles/{id}/photos, PATCH /vehicles/{id}/photos/{photoId}, DELETE /vehicles/{id}/photos/{photoId}
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

---

## Rentals (Admin/Staff)
### POST /rentals
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request:** simpan `rentalId`.

```javascript
const json = pm.response.json();
if (json && json.data && json.data.id) {
  pm.environment.set("rentalId", json.data.id);
}
```

### GET /rentals, GET /rentals/{id}, PATCH /rentals/{id}, POST /rentals/{id}/return
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

---

## Rental History (Admin/Staff)
### GET /rentals/history
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

---

## Availability Calendar (Admin/Staff)
### GET /availability/calendar
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

---

## Public Landing
### GET /public/vehicles
Tidak memerlukan pre-request.

### GET /public/vehicles/{id}
Tidak memerlukan pre-request.
