# TODO - API Development

## Foundation
- [ ] Review and align response/error format with `ApiResponse` standard.
- [ ] Define enums for status: vehicle (available/rented/unavailable/maintenance) and rental (pending/active/completed/cancelled).
- [ ] Add DTOs for request/response per endpoint.

## Auth
- [ ] Implement `POST /auth/register` (validation + USER_ALREADY_EXISTS error).
- [ ] Implement `POST /auth/login` (validation + UNAUTHORIZED_ACCESS error).

## Users
- [x] Implement `GET /users` (admin-only if needed).
- [x] Implement `GET /users/me` (current user profile).

## Vehicles (Admin/Staff)
- [x] Implement `POST /vehicles`.
- [x] Implement `GET /vehicles` with filters (status/categoryId/q).
- [x] Implement `GET /vehicles/{id}`.
- [x] Implement `PUT /vehicles/{id}`.
- [x] Implement `DELETE /vehicles/{id}`.
- [x] Implement `PATCH /vehicles/{id}/status` with rule: only available -> maintenance; rented is system-managed.

## Vehicle Photos (Admin/Staff)
- [x] Implement `POST /vehicles/{id}/photos` (multipart upload).
- [x] Implement `GET /vehicles/{id}/photos`.
- [x] Implement `PATCH /vehicles/{id}/photos/{photoId}`.
- [x] Implement `DELETE /vehicles/{id}/photos/{photoId}`.

## Rentals (Admin/Staff)
- [x] Implement `POST /rentals` with date overlap validation.
- [x] Implement `GET /rentals` with filters (vehicleId/status/startDate/endDate).
- [x] Implement `GET /rentals/{id}`.
- [x] Implement `PATCH /rentals/{id}` (policy-based update).
- [x] Implement `POST /rentals/{id}/return` (close rental + vehicle available).

## Rental History (Admin/Staff)
- [x] Implement `GET /rentals/history` (filters: vehicleId/startDate/endDate).

## Availability Calendar (Admin/Staff)
- [x] Implement `GET /availability/calendar` (vehicleId/startDate/endDate).

## Public Landing
- [x] Implement `GET /public/vehicles` (filters: startDate/endDate/q/categoryId).
- [x] Implement `GET /public/vehicles/{id}`.

## Cross-Cutting
- [ ] Add feature-driven packages for vehicle, rental, photo, category.
- [ ] Add validation and standardized error codes for all endpoints.
- [ ] Add logging for key actions (create/update/rental/return).
- [ ] Add tests for core flows (auth, rental overlap, return updates status).
