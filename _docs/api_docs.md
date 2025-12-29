{
    "openapi": "3.1.0",
    "info": {
        "title": "API",
        "description": "Dokumentasi API",
        "version": "v1"
    },
    "servers": [
        {
            "url": "http://localhost:8080",
            "description": "Generated server url"
        }
    ],
    "security": [
        {
            "bearerAuth": []
        }
    ],
    "paths": {
        "/api/vehicles/{id}": {
            "get": {
                "tags": [
                    "vehicle-controller"
                ],
                "operationId": "getVehicle",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseVehicleResponse"
                                }
                            }
                        }
                    }
                }
            },
            "put": {
                "tags": [
                    "vehicle-controller"
                ],
                "operationId": "updateVehicle",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/VehicleRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseVehicleResponse"
                                }
                            }
                        }
                    }
                }
            },
            "delete": {
                "tags": [
                    "vehicle-controller"
                ],
                "operationId": "deleteVehicle",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseMapStringObject"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/vehicle-types/{id}": {
            "get": {
                "tags": [
                    "vehicle-type-controller"
                ],
                "operationId": "getVehicleType",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseVehicleTypeResponse"
                                }
                            }
                        }
                    }
                }
            },
            "put": {
                "tags": [
                    "vehicle-type-controller"
                ],
                "operationId": "updateVehicleType",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/VehicleTypeRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseVehicleTypeResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/brands/{id}": {
            "get": {
                "tags": [
                    "brand-controller"
                ],
                "operationId": "getBrand",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseBrandResponse"
                                }
                            }
                        }
                    }
                }
            },
            "put": {
                "tags": [
                    "brand-controller"
                ],
                "operationId": "updateBrand",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/BrandRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseBrandResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/vehicles": {
            "get": {
                "tags": [
                    "vehicle-controller"
                ],
                "operationId": "getVehicles",
                "parameters": [
                    {
                        "name": "status",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string"
                        }
                    },
                    {
                        "name": "categoryId",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    },
                    {
                        "name": "q",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseListVehicleResponse"
                                }
                            }
                        }
                    }
                }
            },
            "post": {
                "tags": [
                    "vehicle-controller"
                ],
                "operationId": "createVehicle",
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/VehicleRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseVehicleResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/vehicles/{id}/photos": {
            "get": {
                "tags": [
                    "vehicle-photo-controller"
                ],
                "operationId": "listPhotos",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseListVehiclePhotoResponse"
                                }
                            }
                        }
                    }
                }
            },
            "post": {
                "tags": [
                    "vehicle-photo-controller"
                ],
                "operationId": "uploadPhoto",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    },
                    {
                        "name": "caption",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string"
                        }
                    }
                ],
                "requestBody": {
                    "content": {
                        "multipart/form-data": {
                            "schema": {
                                "type": "object",
                                "properties": {
                                    "file": {
                                        "type": "string",
                                        "format": "binary"
                                    }
                                },
                                "required": [
                                    "file"
                                ]
                            }
                        }
                    }
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseVehiclePhotoResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/vehicle-types": {
            "get": {
                "tags": [
                    "vehicle-type-controller"
                ],
                "operationId": "getVehicleTypes",
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseListVehicleTypeResponse"
                                }
                            }
                        }
                    }
                }
            },
            "post": {
                "tags": [
                    "vehicle-type-controller"
                ],
                "operationId": "createVehicleType",
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/VehicleTypeRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseVehicleTypeResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/rentals": {
            "get": {
                "tags": [
                    "rental-controller"
                ],
                "operationId": "getRentals",
                "parameters": [
                    {
                        "name": "vehicleId",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    },
                    {
                        "name": "status",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string"
                        }
                    },
                    {
                        "name": "startDate",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string",
                            "format": "date"
                        }
                    },
                    {
                        "name": "endDate",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string",
                            "format": "date"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseListRentalResponse"
                                }
                            }
                        }
                    }
                }
            },
            "post": {
                "tags": [
                    "rental-controller"
                ],
                "operationId": "createRental",
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/RentalRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseRentalResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/rentals/{id}/return": {
            "post": {
                "tags": [
                    "rental-controller"
                ],
                "operationId": "returnRental",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/RentalReturnRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseRentalResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/brands": {
            "get": {
                "tags": [
                    "brand-controller"
                ],
                "operationId": "getBrands",
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseListBrandResponse"
                                }
                            }
                        }
                    }
                }
            },
            "post": {
                "tags": [
                    "brand-controller"
                ],
                "operationId": "createBrand",
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/BrandRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseBrandResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/auth/register": {
            "post": {
                "tags": [
                    "auth-controller"
                ],
                "operationId": "register",
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/RegisterRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseAuthResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/auth/login": {
            "post": {
                "tags": [
                    "auth-controller"
                ],
                "operationId": "login",
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/LoginRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseAuthResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/vehicles/{id}/status": {
            "patch": {
                "tags": [
                    "vehicle-controller"
                ],
                "operationId": "updateStatus",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/VehicleStatusRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseVehicleResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/vehicles/{id}/photos/{photoId}": {
            "delete": {
                "tags": [
                    "vehicle-photo-controller"
                ],
                "operationId": "deletePhoto",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    },
                    {
                        "name": "photoId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseMapStringObject"
                                }
                            }
                        }
                    }
                }
            },
            "patch": {
                "tags": [
                    "vehicle-photo-controller"
                ],
                "operationId": "updatePhoto",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    },
                    {
                        "name": "photoId",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/VehiclePhotoUpdateRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseVehiclePhotoResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/rentals/{id}": {
            "get": {
                "tags": [
                    "rental-controller"
                ],
                "operationId": "getRental",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseRentalResponse"
                                }
                            }
                        }
                    }
                }
            },
            "patch": {
                "tags": [
                    "rental-controller"
                ],
                "operationId": "updateRental",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "requestBody": {
                    "content": {
                        "application/json": {
                            "schema": {
                                "$ref": "#/components/schemas/RentalUpdateRequest"
                            }
                        }
                    },
                    "required": true
                },
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseRentalResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/users": {
            "get": {
                "tags": [
                    "user-controller"
                ],
                "operationId": "getUsers",
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseListUserResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/users/me": {
            "get": {
                "tags": [
                    "user-controller"
                ],
                "operationId": "me",
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseUserResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/rentals/history": {
            "get": {
                "tags": [
                    "rental-controller"
                ],
                "operationId": "getRentalHistory",
                "parameters": [
                    {
                        "name": "vehicleId",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    },
                    {
                        "name": "startDate",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string",
                            "format": "date"
                        }
                    },
                    {
                        "name": "endDate",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string",
                            "format": "date"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseListRentalResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/public/vehicles": {
            "get": {
                "tags": [
                    "public-vehicle-controller"
                ],
                "operationId": "getVehicles_1",
                "parameters": [
                    {
                        "name": "startDate",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string",
                            "format": "date"
                        }
                    },
                    {
                        "name": "endDate",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string",
                            "format": "date"
                        }
                    },
                    {
                        "name": "q",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string"
                        }
                    },
                    {
                        "name": "categoryId",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponsePublicVehiclesResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/public/vehicles/{id}": {
            "get": {
                "tags": [
                    "public-vehicle-controller"
                ],
                "operationId": "getVehicle_1",
                "parameters": [
                    {
                        "name": "id",
                        "in": "path",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    },
                    {
                        "name": "startDate",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string",
                            "format": "date"
                        }
                    },
                    {
                        "name": "endDate",
                        "in": "query",
                        "required": false,
                        "schema": {
                            "type": "string",
                            "format": "date"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponsePublicVehicleDetailResponse"
                                }
                            }
                        }
                    }
                }
            }
        },
        "/api/availability/calendar": {
            "get": {
                "tags": [
                    "availability-controller"
                ],
                "operationId": "getCalendar",
                "parameters": [
                    {
                        "name": "vehicleId",
                        "in": "query",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "uuid"
                        }
                    },
                    {
                        "name": "startDate",
                        "in": "query",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "date"
                        }
                    },
                    {
                        "name": "endDate",
                        "in": "query",
                        "required": true,
                        "schema": {
                            "type": "string",
                            "format": "date"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "content": {
                            "*/*": {
                                "schema": {
                                    "$ref": "#/components/schemas/ApiResponseAvailabilityCalendarResponse"
                                }
                            }
                        }
                    }
                }
            }
        }
    },
    "components": {
        "schemas": {
            "VehicleRequest": {
                "type": "object",
                "properties": {
                    "name": {
                        "type": "string",
                        "minLength": 1
                    },
                    "brandId": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "vehicleTypeId": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "year": {
                        "type": "integer",
                        "format": "int32",
                        "minimum": 1
                    },
                    "transmission": {
                        "type": "string",
                        "minLength": 1
                    },
                    "capacity": {
                        "type": "integer",
                        "format": "int32",
                        "minimum": 1
                    },
                    "pricePerDay": {
                        "type": "integer",
                        "format": "int64",
                        "minimum": 0
                    },
                    "description": {
                        "type": "string"
                    },
                    "status": {
                        "type": "string",
                        "minLength": 1
                    },
                    "categoryId": {
                        "type": "string",
                        "format": "uuid"
                    }
                },
                "required": [
                    "brandId",
                    "capacity",
                    "name",
                    "pricePerDay",
                    "status",
                    "transmission",
                    "vehicleTypeId",
                    "year"
                ]
            },
            "ApiResponseVehicleResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "$ref": "#/components/schemas/VehicleResponse"
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "VehicleResponse": {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "name": {
                        "type": "string"
                    },
                    "brand": {
                        "type": "string"
                    },
                    "type": {
                        "type": "string"
                    },
                    "year": {
                        "type": "integer",
                        "format": "int32"
                    },
                    "transmission": {
                        "type": "string"
                    },
                    "capacity": {
                        "type": "integer",
                        "format": "int32"
                    },
                    "pricePerDay": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "description": {
                        "type": "string"
                    },
                    "status": {
                        "type": "string"
                    },
                    "categoryId": {
                        "type": "string",
                        "format": "uuid"
                    }
                }
            },
            "VehicleTypeRequest": {
                "type": "object",
                "properties": {
                    "name": {
                        "type": "string",
                        "minLength": 1
                    }
                },
                "required": [
                    "name"
                ]
            },
            "ApiResponseVehicleTypeResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "$ref": "#/components/schemas/VehicleTypeResponse"
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "VehicleTypeResponse": {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "name": {
                        "type": "string"
                    }
                }
            },
            "BrandRequest": {
                "type": "object",
                "properties": {
                    "name": {
                        "type": "string",
                        "minLength": 1
                    }
                },
                "required": [
                    "name"
                ]
            },
            "ApiResponseBrandResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "$ref": "#/components/schemas/BrandResponse"
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "BrandResponse": {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "name": {
                        "type": "string"
                    }
                }
            },
            "ApiResponseVehiclePhotoResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "$ref": "#/components/schemas/VehiclePhotoResponse"
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "VehiclePhotoResponse": {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "vehicleId": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "url": {
                        "type": "string"
                    },
                    "caption": {
                        "type": "string"
                    },
                    "order": {
                        "type": "integer",
                        "format": "int32"
                    }
                }
            },
            "RentalRequest": {
                "type": "object",
                "properties": {
                    "vehicleId": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "renterName": {
                        "type": "string",
                        "minLength": 1
                    },
                    "renterPhone": {
                        "type": "string",
                        "minLength": 1
                    },
                    "renterAddress": {
                        "type": "string",
                        "minLength": 1
                    },
                    "renterIdNumber": {
                        "type": "string",
                        "minLength": 1
                    },
                    "startDate": {
                        "type": "string",
                        "format": "date"
                    },
                    "endDate": {
                        "type": "string",
                        "format": "date"
                    },
                    "pickupLocation": {
                        "type": "string"
                    },
                    "returnLocation": {
                        "type": "string"
                    },
                    "priceTotal": {
                        "type": "integer",
                        "format": "int64",
                        "minimum": 0
                    },
                    "notes": {
                        "type": "string"
                    }
                },
                "required": [
                    "endDate",
                    "priceTotal",
                    "renterAddress",
                    "renterIdNumber",
                    "renterName",
                    "renterPhone",
                    "startDate",
                    "vehicleId"
                ]
            },
            "ApiResponseRentalResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "$ref": "#/components/schemas/RentalResponse"
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "RentalResponse": {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "vehicleId": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "renterName": {
                        "type": "string"
                    },
                    "renterPhone": {
                        "type": "string"
                    },
                    "renterAddress": {
                        "type": "string"
                    },
                    "renterIdNumber": {
                        "type": "string"
                    },
                    "startDate": {
                        "type": "string",
                        "format": "date"
                    },
                    "endDate": {
                        "type": "string",
                        "format": "date"
                    },
                    "returnDate": {
                        "type": "string",
                        "format": "date"
                    },
                    "pickupLocation": {
                        "type": "string"
                    },
                    "returnLocation": {
                        "type": "string"
                    },
                    "priceTotal": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "status": {
                        "type": "string"
                    },
                    "notes": {
                        "type": "string"
                    },
                    "conditionNotes": {
                        "type": "string"
                    }
                }
            },
            "RentalReturnRequest": {
                "type": "object",
                "properties": {
                    "returnDate": {
                        "type": "string",
                        "format": "date"
                    },
                    "conditionNotes": {
                        "type": "string"
                    }
                },
                "required": [
                    "returnDate"
                ]
            },
            "RegisterRequest": {
                "type": "object",
                "properties": {
                    "email": {
                        "type": "string",
                        "minLength": 1
                    },
                    "password": {
                        "type": "string",
                        "maxLength": 2147483647,
                        "minLength": 8
                    }
                },
                "required": [
                    "email",
                    "password"
                ]
            },
            "ApiResponseAuthResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "$ref": "#/components/schemas/AuthResponse"
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "AuthResponse": {
                "type": "object",
                "properties": {
                    "accessToken": {
                        "type": "string"
                    },
                    "tokenType": {
                        "type": "string"
                    }
                }
            },
            "LoginRequest": {
                "type": "object",
                "properties": {
                    "email": {
                        "type": "string",
                        "minLength": 1
                    },
                    "password": {
                        "type": "string",
                        "minLength": 1
                    }
                },
                "required": [
                    "email",
                    "password"
                ]
            },
            "VehicleStatusRequest": {
                "type": "object",
                "properties": {
                    "status": {
                        "type": "string",
                        "minLength": 1
                    }
                },
                "required": [
                    "status"
                ]
            },
            "VehiclePhotoUpdateRequest": {
                "type": "object",
                "properties": {
                    "order": {
                        "type": "integer",
                        "format": "int32",
                        "minimum": 1
                    },
                    "caption": {
                        "type": "string"
                    }
                }
            },
            "RentalUpdateRequest": {
                "type": "object",
                "properties": {
                    "renterName": {
                        "type": "string"
                    },
                    "renterPhone": {
                        "type": "string"
                    },
                    "renterAddress": {
                        "type": "string"
                    },
                    "renterIdNumber": {
                        "type": "string"
                    },
                    "startDate": {
                        "type": "string",
                        "format": "date"
                    },
                    "endDate": {
                        "type": "string",
                        "format": "date"
                    },
                    "pickupLocation": {
                        "type": "string"
                    },
                    "returnLocation": {
                        "type": "string"
                    },
                    "priceTotal": {
                        "type": "integer",
                        "format": "int64",
                        "minimum": 0
                    },
                    "notes": {
                        "type": "string"
                    }
                }
            },
            "ApiResponseListVehicleResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "type": "array",
                        "items": {
                            "$ref": "#/components/schemas/VehicleResponse"
                        }
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "ApiResponseListVehiclePhotoResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "type": "array",
                        "items": {
                            "$ref": "#/components/schemas/VehiclePhotoResponse"
                        }
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "ApiResponseListVehicleTypeResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "type": "array",
                        "items": {
                            "$ref": "#/components/schemas/VehicleTypeResponse"
                        }
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "ApiResponseListUserResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "type": "array",
                        "items": {
                            "$ref": "#/components/schemas/UserResponse"
                        }
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "UserResponse": {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "email": {
                        "type": "string"
                    },
                    "role": {
                        "type": "string"
                    }
                }
            },
            "ApiResponseUserResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "$ref": "#/components/schemas/UserResponse"
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "ApiResponseListRentalResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "type": "array",
                        "items": {
                            "$ref": "#/components/schemas/RentalResponse"
                        }
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "ApiResponsePublicVehiclesResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "$ref": "#/components/schemas/PublicVehiclesResponse"
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "PublicVehicleItemResponse": {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "name": {
                        "type": "string"
                    },
                    "pricePerDay": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "status": {
                        "type": "string"
                    },
                    "nextAvailableDate": {
                        "type": "string",
                        "format": "date"
                    },
                    "thumbnailUrl": {
                        "type": "string"
                    }
                }
            },
            "PublicVehiclesResponse": {
                "type": "object",
                "properties": {
                    "items": {
                        "type": "array",
                        "items": {
                            "$ref": "#/components/schemas/PublicVehicleItemResponse"
                        }
                    }
                }
            },
            "ApiResponsePublicVehicleDetailResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "$ref": "#/components/schemas/PublicVehicleDetailResponse"
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "PublicVehicleDetailResponse": {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "name": {
                        "type": "string"
                    },
                    "brand": {
                        "type": "string"
                    },
                    "type": {
                        "type": "string"
                    },
                    "year": {
                        "type": "integer",
                        "format": "int32"
                    },
                    "transmission": {
                        "type": "string"
                    },
                    "capacity": {
                        "type": "integer",
                        "format": "int32"
                    },
                    "pricePerDay": {
                        "type": "integer",
                        "format": "int64"
                    },
                    "status": {
                        "type": "string"
                    },
                    "availableDates": {
                        "type": "array",
                        "items": {
                            "type": "string",
                            "format": "date"
                        }
                    },
                    "images": {
                        "type": "array",
                        "items": {
                            "type": "string"
                        }
                    },
                    "whatsAppLink": {
                        "type": "string"
                    }
                }
            },
            "ApiResponseListBrandResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "type": "array",
                        "items": {
                            "$ref": "#/components/schemas/BrandResponse"
                        }
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "ApiResponseAvailabilityCalendarResponse": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "$ref": "#/components/schemas/AvailabilityCalendarResponse"
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            },
            "AvailabilityCalendarResponse": {
                "type": "object",
                "properties": {
                    "vehicleId": {
                        "type": "string",
                        "format": "uuid"
                    },
                    "dates": {
                        "type": "array",
                        "items": {
                            "$ref": "#/components/schemas/AvailabilityDateStatus"
                        }
                    }
                }
            },
            "AvailabilityDateStatus": {
                "type": "object",
                "properties": {
                    "date": {
                        "type": "string",
                        "format": "date"
                    },
                    "status": {
                        "type": "string"
                    }
                }
            },
            "ApiResponseMapStringObject": {
                "type": "object",
                "properties": {
                    "errors": {
                        "type": "array",
                        "items": {}
                    },
                    "data": {
                        "type": "object",
                        "additionalProperties": {}
                    },
                    "timestamp": {
                        "type": "string",
                        "format": "date-time"
                    },
                    "isSuccess": {
                        "type": "boolean"
                    }
                }
            }
        },
        "securitySchemes": {
            "bearerAuth": {
                "type": "http",
                "scheme": "bearer",
                "bearerFormat": "JWT"
            }
        }
    }
}