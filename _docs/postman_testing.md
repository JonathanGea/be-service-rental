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

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("accessToken");
  pm.expect(json.data).to.have.property("tokenType");
});
```

### POST /auth/login
**Post-request:** simpan token untuk request berikutnya.

```javascript
const json = pm.response.json();
if (json && json.data && json.data.accessToken) {
  pm.environment.set("accessToken", json.data.accessToken);
}
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("accessToken");
  pm.expect(json.data).to.have.property("tokenType");
});
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

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.be.an("array");
  if (json.data.length > 0) {
    pm.expect(json.data[0]).to.have.property("id");
    pm.expect(json.data[0]).to.have.property("email");
    pm.expect(json.data[0]).to.have.property("role");
  }
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

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("id");
  pm.expect(json.data).to.have.property("email");
  pm.expect(json.data).to.have.property("role");
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

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("id");
  pm.expect(json.data).to.have.property("name");
  pm.expect(json.data).to.have.property("status");
});
```

### GET /vehicles
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.be.an("array");
});
```

### GET /vehicles/{id}
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("id");
  pm.expect(json.data).to.have.property("status");
});
```

### PUT /vehicles/{id}
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("id");
  pm.expect(json.data).to.have.property("status");
});
```

### PATCH /vehicles/{id}/status
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("id");
  pm.expect(json.data).to.have.property("status");
});
```

### DELETE /vehicles/{id}
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("deleted", true);
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

**Post-request:** simpan `photoId`.

```javascript
const json = pm.response.json();
if (json && json.data && json.data.id) {
  pm.environment.set("photoId", json.data.id);
}
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("id");
  pm.expect(json.data).to.have.property("url");
});
```

### GET /vehicles/{id}/photos
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.be.an("array");
});
```

### PATCH /vehicles/{id}/photos/{photoId}
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("id");
  pm.expect(json.data).to.have.property("caption");
});
```

### DELETE /vehicles/{id}/photos/{photoId}
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("deleted", true);
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

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("id");
  pm.expect(json.data).to.have.property("vehicleId");
});
```

### GET /rentals
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.be.an("array");
});
```

### GET /rentals/{id}
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("id");
  pm.expect(json.data).to.have.property("status");
});
```

### PATCH /rentals/{id}
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("id");
  pm.expect(json.data).to.have.property("status");
});
```

### POST /rentals/{id}/return
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("id");
  pm.expect(json.data).to.have.property("status");
});
```

### GET /rentals/history
**Pre-request:** set Authorization header.

```javascript
pm.request.headers.upsert({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("accessToken")
});
```

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.be.an("array");
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

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("vehicleId");
  pm.expect(json.data).to.have.property("dates");
  pm.expect(json.data.dates).to.be.an("array");
});
```
---

## Public Landing
### GET /public/vehicles
**Pre-request:** no auth required.

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("items");
  pm.expect(json.data.items).to.be.an("array");
});
```

### GET /public/vehicles/{id}
**Pre-request:** no auth required.

**Post-request (Tests):**

```javascript
pm.test("status is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("response time is acceptable", function () {
  const limit = parseInt(pm.environment.get("acceptable_limits_time"), 10) || 2000;
  pm.expect(pm.response.responseTime).to.be.below(limit);
});

pm.test("response body is correct", function () {
  const json = pm.response.json();
  pm.expect(json).to.have.property("isSuccess", true);
  pm.expect(json).to.have.property("data");
  pm.expect(json.data).to.have.property("id");
  pm.expect(json.data).to.have.property("name");
});
```
