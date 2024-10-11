# Online Fast Food Ordering System

This project is an online fast food ordering system built with Spring Boot and PostgreSQL. It allows users to browse food items, place orders, and make payments. The application also integrates with AWS S3 for managing images, such as food item photos.

## Features

- User authentication and authorization (JWT-based)
- Role-based access (Admin, User)
- Food item browsing
- Image management with AWS S3 (product, category, user images)
- Order management (view, create, update)
- Payment integration
- Admin dashboard to manage categories and products
- RESTful API with Spring Boot
- Database: PostgreSQL

## Technologies Used

- **Backend**: Spring Boot (JPA, Security, Validation)
- **Database**: PostgreSQL
- **Security**: JWT Authentication, BCrypt for password encoding
- **Cloud Storage**: AWS S3 for image storage
- **Deployment**: AWS EC2 instance
- **Other Libraries**:
    - Spring Data JPA
    - MapStruct (for mapping DTOs)
    - Lombok (for simplifying boilerplate code)
    - SpringDoc OpenAPI (for API documentation)
    - JJWT (for handling JWT tokens)
    - AWS SDK for S3 integration

## Setup Instructions

### Prerequisites

1. **Java 17** or higher
2. **PostgreSQL** installed and running
3. **AWS S3** bucket configured for image storage
4. **Maven** installed

### Steps to Run the Application

1. **Clone the repository**:
    ```bash
    git clone https://github.com/wexad/FoodDelivery.git
    cd FoodDelivery
    ```

2. **Configure PostgreSQL**:

   Update the `application.properties` with your PostgreSQL credentials:

3. **AWS S3 Configuration**:

   Add your AWS S3 credentials and bucket name in `application.properties`:
   ```properties
   aws.s3.bucket-name=your-s3-bucket
   aws.s3.access-key=your-aws-access-key
   aws.s3.secret-key=your-aws-secret-key

## Contact

For questions or suggestions, please contact [sherzodchoriyev747@example.com](mailto:sherzodchoriyev747@example.com).
