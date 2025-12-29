# API Specification - Endpoints

Base URL: `/api`

## Response Format
Semua response mengikuti pola `ApiResponse` sesuai standar backend.

Sukses:
```json
{
  "errors": null,
  "data": {},
  "timestamp": "2025-10-29T15:03:24.230+07:00",
  "isSuccess": true
}
```

Error (validasi):
```json
{
  "errors": [
    {
      "code": "VALIDATION_BLANK",
      "message": "Kolom 'email' tidak boleh kosong.",
      "field": "email",
      "details": {
        "expectedFormat": "email"
      }
    }
  ],
  "data": null,
  "timestamp": "2025-10-29T17:14:02.514+07:00",
  "isSuccess": false
}
```

Error (bisnis, not found, auth, internal) mengikuti pola yang sama dengan `code` seperti:
`USER_ALREADY_EXISTS`, `RESOURCE_NOT_FOUND`, `UNAUTHORIZED_ACCESS`, `FORBIDDEN_ACCESS`, `INTERNAL_SERVER_ERROR`, dll.

---

## Auth
### POST /auth/register
Mendaftarkan user baru.

Request:
```json
{
  "email": "john@example.com",
  "password": "secret123"
}
```

Response (ApiResponse):
```json
{
  "errors": null,
  "data": {
    "accessToken": "<JWT>",
    "tokenType": "Bearer"
  },
  "timestamp": "2025-10-29T15:03:24.230+07:00",
  "isSuccess": true
}
```

### POST /auth/login
Login user.

Request:
```json
{
  "email": "john@example.com",
  "password": "secret123"
}
```

Response (ApiResponse):
```json
{
  "errors": null,
  "data": {
    "accessToken": "<JWT>",
    "tokenType": "Bearer"
  },
  "timestamp": "2025-10-29T15:03:24.230+07:00",
  "isSuccess": true
}
```

---

## Users
### GET /users
List semua user. (auth required)

### GET /users/me
Profile user dari token. (auth required)

---

## Vehicles (Admin/Staff)
### POST /vehicles
Buat kendaraan.

Request:
```json
{
  "name": "Avanza",
  "brand": "Toyota",
  "type": "MPV",
  "year": 2022,
  "transmission": "AT",
  "capacity": 7,
  "pricePerDay": 350000,
  "description": "Unit bersih dan nyaman",
  "status": "available",
  "categoryId": "<UUID>"
}
```

### GET /vehicles
List kendaraan (admin), bisa filter.

Query params (opsional):
- `status` = available | rented | unavailable | maintenance
- `categoryId`
- `q` (search)

### GET /vehicles/{id}
Detail kendaraan.

### PUT /vehicles/{id}
Update data kendaraan.

### DELETE /vehicles/{id}
Hapus kendaraan.

### PATCH /vehicles/{id}/status
Ubah status kendaraan.

Rules:
- Hanya boleh set `maintenance` jika status saat ini `available`.
- Status `rented` diubah otomatis oleh sistem berdasarkan rental aktif.

Request:
```json
{
  "status": "maintenance"
}
```

---

## Vehicle Photos (Admin/Staff)
### POST /vehicles/{id}/photos
Upload foto kendaraan (multipart/form-data).

Form:
- `file` (required)
- `caption` (optional)

### GET /vehicles/{id}/photos
List foto kendaraan.

### PATCH /vehicles/{id}/photos/{photoId}
Ubah urutan atau metadata foto.

Request:
```json
{
  "order": 1,
  "caption": "Tampak depan"
}
```

### DELETE /vehicles/{id}/photos/{photoId}
Hapus foto kendaraan.

---

## Rentals (Admin/Staff)
### POST /rentals
Buat rental.

Request:
```json
{
  "vehicleId": "<UUID>",
  "renterName": "Budi",
  "renterPhone": "08123456789",
  "renterAddress": "Jakarta",
  "renterIdNumber": "1234567890",
  "startDate": "2025-11-01",
  "endDate": "2025-11-03",
  "pickupLocation": "Bandara",
  "returnLocation": "Bandara",
  "priceTotal": 1050000,
  "notes": "Tanpa sopir"
}
```

Rules:
- Tanggal tidak boleh overlap dengan rental aktif.
- Status kendaraan menjadi `rented` selama rental aktif.

### GET /rentals
List rental (filter opsional).

Query params (opsional):
- `vehicleId`
- `status` = pending | active | completed | cancelled
- `startDate` / `endDate`

### GET /rentals/{id}
Detail rental.

### PATCH /rentals/{id}
Update detail rental (sebelum aktif atau sesuai policy).

### POST /rentals/{id}/return
Menutup rental dan mengembalikan kendaraan.

Request:
```json
{
  "returnDate": "2025-11-03",
  "conditionNotes": "Baik"
}
```

---

## Rental History (Admin/Staff)
### GET /rentals/history
Riwayat rental kendaraan.

Query params (opsional):
- `vehicleId`
- `startDate` / `endDate`

---

## Availability Calendar (Admin/Staff)
### GET /availability/calendar
Kalender ketersediaan kendaraan.

Query params:
- `vehicleId`
- `startDate`
- `endDate`

Response data contoh (di dalam `data` ApiResponse):
```json
{
  "errors": null,
  "data": {
    "vehicleId": "<UUID>",
    "dates": [
      { "date": "2025-11-01", "status": "rented" },
      { "date": "2025-11-02", "status": "rented" },
      { "date": "2025-11-03", "status": "available" }
    ]
  },
  "timestamp": "2025-10-29T15:03:24.230+07:00",
  "isSuccess": true
}
```

---

## Public Landing
### GET /public/vehicles
List kendaraan untuk landing page.

Query params (opsional):
- `startDate` / `endDate` (untuk menandai availability)
- `q` (search)
- `categoryId`

Response data contoh (di dalam `data` ApiResponse):
```json
{
  "errors": null,
  "data": {
    "items": [
      {
        "id": "<UUID>",
        "name": "Avanza",
        "pricePerDay": 350000,
        "status": "available",
        "nextAvailableDate": "2025-11-03",
        "thumbnailUrl": "https://..."
      }
    ]
  },
  "timestamp": "2025-10-29T15:03:24.230+07:00",
  "isSuccess": true
}
```

### GET /public/vehicles/{id}
Detail kendaraan untuk landing page.

Response data contoh (di dalam `data` ApiResponse):
```json
{
  "errors": null,
  "data": {
    "id": "<UUID>",
    "name": "Avanza",
    "brand": "Toyota",
    "type": "MPV",
    "year": 2022,
    "transmission": "AT",
    "capacity": 7,
    "pricePerDay": 350000,
    "status": "available",
    "availableDates": ["2025-11-03", "2025-11-04"],
    "images": ["https://..."],
    "whatsAppLink": "https://wa.me/<PHONE_NUMBER>?text=..."
  },
  "timestamp": "2025-10-29T15:03:24.230+07:00",
  "isSuccess": true
}
```
