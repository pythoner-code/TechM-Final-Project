# E-Commerce Platform

## Overview

This project is a comprehensive e-commerce platform built using modern technologies. The front end is developed with Angular, while the backend is powered by Spring Boot. It incorporates JWT for secure authentication, integrates payment gateways for transaction processing, and uses MySQL as the database.

## Features

- **User Authentication:** Secure user registration and login using JWT.
- **Product Management:** Add, update, delete, and view products.
- **Shopping Cart:** Add products to the cart, view cart items, and adjust quantities.
- **Order Processing:** Place orders and view order history.
- **Payment Integration:** Secure payment processing.
- **Admin Dashboard:** Manage products, view orders, and handle user management.
- **Responsive Design:** Accessible on various devices and screen sizes.

## Technologies Used

### Frontend

- **Angular:** A platform for building mobile and desktop web applications.

### Backend

- **Spring Boot:** A framework for building production-ready web applications in Java.
- **JWT (JSON Web Token):** For secure authentication.
- **Payment Integration:** For processing payments.
- **MySQL:** A relational database management system.

## Getting Started

### Prerequisites

- **Java 8 or higher**
- **Node.js and npm**
- **Angular CLI**
- **MySQL**

### Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/pythoner-code/TechM-Final-Project.git
    cd TechM\ Final\ Project
    ```

2. **Backend Setup:**

    - Navigate to the backend directory:

        ```bash
        cd backend
        ```

    - Update the `application.properties` file with your MySQL database credentials:

        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/yourdbname
        spring.datasource.username=yourusername
        spring.datasource.password=yourpassword
        spring.jpa.hibernate.ddl-auto=update
        jwt.secret=yourJWTSecretKey
        ```

    - Install the required dependencies and run the Spring Boot application:

        ```bash
        mvn install
        mvn spring-boot:run
        ```

3. **Frontend Setup:**

    - Navigate to the frontend directory:

        ```bash
        cd ../frontend
        ```

    - Install the required dependencies:

        ```bash
        npm install
        ```

    - Update the `environment.ts` file with your backend API URL:

        ```typescript
        export const environment = {
          production: false,
          apiUrl: 'http://localhost:8080/api'
        };
        ```

    - Run the Angular application:

        ```bash
        ng serve
        ```

4. **Access the Application:**

    - Open your web browser and navigate to `http://localhost:4200`.

5. **Alternate way to run this application:**

    - Open the Frontend folder in Visual Studio Code and follow the steps mentioned at Point 3.
    - Run the Backend in IntelliJ or Eclipse IDE whichever you prefer and open the project for Maven, and select this particular folder.
    - Set up the database before running the backend application.

## Usage

### User Registration and Login

- Users can register by providing their details.
- After registration, users can log in using their credentials.

### Product Management

- Admin users can add new products, update existing products, or delete products.
- All users can view the list of products.

### Shopping Cart and Orders

- Users can add products to their shopping cart.
- Users can view and manage their cart.
- Users can place orders and view their order history.

### Payment Processing

- Users can securely process payments through the integrated payment gateway.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add your feature'`).
5. Push to the branch (`git push origin feature/your-feature`).
6. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.


---

Happy coding!
