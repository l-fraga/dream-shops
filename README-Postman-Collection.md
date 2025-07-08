# Dream Shops API - Postman Collection

This document provides instructions for using the Dream Shops API Postman collection.

## Overview

The Dream Shops API is a comprehensive e-commerce REST API built with Spring Boot. The Postman collection includes all available endpoints organized by functionality.

## Setup Instructions

### 1. Import the Collection

1. Open Postman
2. Click "Import" button
3. Select the `Dream-Shops-API.postman_collection.json` file
4. The collection will be imported with all endpoints organized by controller

### 2. Configure Environment Variables

The collection uses the following environment variables that you can set in Postman:

| Variable | Default Value | Description |
|----------|---------------|-------------|
| `baseUrl` | `http://localhost:8080/api/v1` | Base URL for the API |
| `userId` | `1` | Default user ID for testing |
| `productId` | `1` | Default product ID for testing |
| `categoryId` | `1` | Default category ID for testing |
| `cartId` | `1` | Default cart ID for testing |
| `itemId` | `1` | Default cart item ID for testing |
| `orderId` | `1` | Default order ID for testing |
| `imageId` | `1` | Default image ID for testing |

### 3. Start the Application

Before testing, ensure the Dream Shops application is running:

```bash
# Navigate to the project directory
cd dream-shops

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Users (`/users`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/users/{userId}/user` | Get user by ID |
| POST | `/users/add` | Create a new user |
| PUT | `/users/{userId}/update` | Update user information |
| DELETE | `/users/{userId}/delete` | Delete a user |

**Sample Request Body for Create User:**
```json
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "password": "password123"
}
```

**Sample Request Body for Update User:**
```json
{
    "firstName": "Jane",
    "lastName": "Smith"
}
```

### Products (`/products`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/products/all` | Get all products |
| GET | `/products/product/{productId}/product` | Get product by ID |
| POST | `/products/add` | Add a new product |
| PUT | `/products/product/{productId}/update` | Update a product |
| DELETE | `/products/product/{productId}/delete` | Delete a product |
| GET | `/products/products/by/brand-and-name` | Get products by brand and name |
| GET | `/products/products/by/category-and-brand/{category}/{brand}` | Get products by category and brand |
| GET | `/products/products/{name}/products` | Get products by name |
| GET | `/products/product/by-brand/{brand}` | Get products by brand |
| GET | `/products/product/{category}/all/products` | Get products by category |
| GET | `/products/product/count/by-brand/and-name` | Count products by brand and name |

**Sample Request Body for Add Product:**
```json
{
    "name": "iPhone 15",
    "brand": "Apple",
    "price": 999.99,
    "inventory": 50,
    "description": "Latest iPhone model",
    "category": {
        "id": 1,
        "name": "Electronics"
    }
}
```

### Categories (`/categories`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/categories/all` | Get all categories |
| POST | `/categories/add` | Add a new category |
| GET | `/categories/category/{id}/category` | Get category by ID |
| GET | `/categories/category/{name}/category` | Get category by name |
| DELETE | `/categories/category/{id}/delete` | Delete a category |
| PUT | `/categories/cateogy/{id}/update` | Update a category |

**Sample Request Body for Add Category:**
```json
{
    "name": "Electronics"
}
```

### Carts (`/carts`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/carts/{cartId}/my-cart` | Get cart by ID |
| DELETE | `/carts/{cartId}/clear` | Clear all items from cart |
| GET | `/carts/{cartId}/cart/total-price` | Get cart total price |

### Cart Items (`/cartItems`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/cartItems/item/add` | Add item to cart |
| DELETE | `/cartItems/cart/{cartId}/item/{itemId}/remove` | Remove item from cart |
| PUT | `/cartItems/cart/{cartId}/item/{itemId}/update` | Update item quantity |

**Query Parameters for Add Item:**
- `cartId` (optional): Cart ID (will create new cart if not provided)
- `productId` (required): Product ID to add
- `quantity` (required): Quantity to add

**Query Parameters for Update Item:**
- `quantity` (required): New quantity

### Orders (`/orders`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/orders/order/create` | Create a new order |
| GET | `/orders/{orderId}/order` | Get order by ID |
| GET | `/orders/{userId}/orders` | Get all orders for a user |

**Query Parameters for Create Order:**
- `userId` (required): User ID to create order for

### Images (`/images`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/images/upload` | Upload images for a product |
| GET | `/images/image/download/{imageId}` | Download an image |
| PUT | `/images/updateImage/{imageId}` | Update an image |
| DELETE | `/images/delete/{imageId}` | Delete an image |

**Form Data for Upload Images:**
- `files` (required): Multiple image files
- `productId` (required): Product ID to associate images with

## Testing Workflow

### 1. Basic Setup
1. Start the application
2. Import the Postman collection
3. Set up environment variables

### 2. User Management
1. Create a user using `POST /users/add`
2. Note the user ID from the response
3. Update the `userId` environment variable
4. Test other user endpoints

### 3. Category Management
1. Create categories using `POST /categories/add`
2. Note the category ID from the response
3. Update the `categoryId` environment variable

### 4. Product Management
1. Create products using `POST /products/add`
2. Note the product ID from the response
3. Update the `productId` environment variable
4. Test product search and filtering endpoints

### 5. Shopping Cart
1. Add items to cart using `POST /cartItems/item/add`
2. Note the cart ID from the response
3. Update the `cartId` environment variable
4. Test cart management endpoints

### 6. Order Management
1. Create orders using `POST /orders/order/create`
2. Note the order ID from the response
3. Update the `orderId` environment variable
4. Test order retrieval endpoints

### 7. Image Management
1. Upload images using `POST /images/upload`
2. Note the image ID from the response
3. Update the `imageId` environment variable
4. Test image download and management endpoints

## Response Format

All API responses follow this format:

```json
{
    "message": "Success message",
    "data": {
        // Response data
    }
}
```

## Error Handling

The API returns appropriate HTTP status codes:

- `200 OK`: Success
- `404 Not Found`: Resource not found
- `409 Conflict`: Resource already exists
- `500 Internal Server Error`: Server error

Error responses include an error message in the response body.

## Database Setup

The application uses PostgreSQL. Ensure you have:

1. PostgreSQL installed and running
2. Database `dream_shops_db` created
3. User `root` with password `1234` (or update `application.properties`)

## Notes

- The API prefix is `/api/v1` as configured in `application.properties`
- File uploads are limited to 5MB per file
- All endpoints return JSON responses
- The application runs on port 8080 by default

## Troubleshooting

1. **Connection refused**: Ensure the application is running
2. **Database connection error**: Check PostgreSQL configuration
3. **File upload errors**: Check file size limits
4. **404 errors**: Verify the API prefix and endpoint paths

For more information, refer to the application logs or check the Spring Boot documentation. 