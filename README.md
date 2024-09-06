# Chat Application Backend

This is the backend of a real-time chat application built using Spring Boot. The application uses WebSockets for real-time communication, JWT for authentication, OAuth2 for social logins, and Spring Security to ensure secure access to resources.

## Features

- **WebSocket Communication**: Real-time chat functionality using WebSocket.
- **JWT Authentication**: Secure token-based authentication using JSON Web Tokens.
- **OAuth2 Social Login**: Support for third-party authentication using OAuth2 (e.g., Google, GitHub).
- **Spring Security**: Robust security configuration to protect endpoints.
- **RESTful APIs**: Exposes RESTful APIs for managing chat messages, user authentication, and more.
- **User Management**: Register, login, and manage user accounts.
- **Role-Based Access Control**: Control access to endpoints based on user roles.
- **Java 8 Compatible**: Developed with compatibility in mind for Java 8 environments.

## Tech Stack

- **Java 8**: Main programming language.
- **Spring Boot**: Backend framework for building Java applications.
- **Spring WebSocket**: For handling WebSocket connections.
- **Spring Security**: Provides authentication and authorization features.
- **JWT**: For secure authentication using tokens.
- **OAuth2**: Enables third-party authentication for users.
- **Maven**: Build automation tool used to manage dependencies and build the project.
- **H2 Database** (or your chosen database): Lightweight, in-memory database for development and testing.
- **Docker**: For containerizing the application.

## Prerequisites

- **Java 8 or higher**: Ensure JDK 8 or above is installed.
- **Maven**: For building and managing the project.
- **Docker** (optional): If you wish to run the application in a containerized environment.

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/your-username/chat-application-backend.git
cd chat-application-backend
