# TODO - API Development

## Foundation
- [ ] Review and align response/error format with `ApiResponse` standard.
- [ ] Define enums for status: vehicle (available/rented/unavailable/maintenance) and rental (pending/active/completed/cancelled).
- [ ] Add DTOs for request/response per endpoint.

## Auth
- [ ] Implement `POST /auth/register` (validation + USER_ALREADY_EXISTS error).
- [ ] Implement `POST /auth/login` (validation + UNAUTHORIZED_ACCESS error).

## Users
- [ ] Implement `GET /users` (admin-only if needed).
- [ ] Implement `GET /users/me` (current user profile).

## Vehicles (Admin/Staff)
- [ ] Implement `POST /vehicles`.
- [ ] Implement `GET /vehicles` with filters (status/categoryId/q).
- [ ] Implement `GET /vehicles/{id}`.
- [ ] Implement `PUT /vehicles/{id}`.
- [ ] Implement `DELETE /vehicles/{id}`.
- [ ] Implement `PATCH /vehicles/{id}/status` with rule: only available -> maintenance; rented is system-managed.

## Vehicle Photos (Admin/Staff)
- [ ] Implement `POST /vehicles/{id}/photos` (multipart upload).
- [ ] Implement `GET /vehicles/{id}/photos`.
- [ ] Implement `PATCH /vehicles/{id}/photos/{photoId}`.
- [ ] Implement `DELETE /vehicles/{id}/photos/{photoId}`.

## Rentals (Admin/Staff)
- [ ] Implement `POST /rentals` with date overlap validation.
- [ ] Implement `GET /rentals` with filters (vehicleId/status/startDate/endDate).
- [ ] Implement `GET /rentals/{id}`.
- [ ] Implement `PATCH /rentals/{id}` (policy-based update).
- [ ] Implement `POST /rentals/{id}/return` (close rental + vehicle available).

## Rental History (Admin/Staff)
- [ ] Implement `GET /rentals/history` (filters: vehicleId/startDate/endDate).

## Availability Calendar (Admin/Staff)
- [ ] Implement `GET /availability/calendar` (vehicleId/startDate/endDate).

## Public Landing
- [ ] Implement `GET /public/vehicles` (filters: startDate/endDate/q/categoryId).
- [ ] Implement `GET /public/vehicles/{id}`.

## Cross-Cutting
- [ ] Add feature-driven packages for vehicle, rental, photo, category.
- [ ] Add validation and standardized error codes for all endpoints.
- [ ] Add logging for key actions (create/update/rental/return).
- [ ] Add tests for core flows (auth, rental overlap, return updates status).
