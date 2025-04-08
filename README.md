# russian-software-catalog

# Russian Software Catalog

A web application that serves as a catalog of Russian software products with search, filtering and management capabilities.

## Features

- Browse software products with pagination
- Search by name or filter by category
- View detailed product information
- Add new software products via form
- Sort products by name or creation date
- Responsive and modern UI with animations
- Similar products recommendations

## Technologies

- **Backend**: 
  - Java 17
  - Spring Boot 3
  - Spring Data JPA
  - Hibernate

- **Frontend**:
  - Thymeleaf templates
  - Bootstrap 5
  - Custom CSS animations

- **Database**: 
  - H2 (embedded, can be configured for other databases)

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+

### Installation
1. Clone the repository
   ```bash
   git clone https://github.com/ynb4gang/russian-software-catalog.git
   ```
2. Navigate to project directory
   ```bash
   cd russian-software-catalog
   ```
4. Build the project
   ```bash
   mvn clean install
   ```
5. Run the application
   ```bash
   mvn spring-boot:run
   ```
The application will be available at `http://localhost:8080`
## API Endpoints
The application provides both web interface and REST API:

`GET /api/products` - Get all products

`GET /api/products/{id}` - Get product by ID

`POST /api/products` - Create new product

`PUT /api/products/{id}` - Update product

`DELETE /api/products/{id}` - Delete product
