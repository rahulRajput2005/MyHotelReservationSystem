# MyHotelReservationSystem
Hotel Reservation Management System
This is a command-line Java application for managing hotel room reservations using a MySQL database. It allows users to perform standard CRUD (Create, Read, Update, Delete) operations on reservation records.

🔑 Key Features
Reserve a room (Create): Add a new reservation with guest details.

View all Reservations (Read): Display a tabular list of all current reservations.

Get room number (Read): Retrieve the room number for a specific reservation ID and guest name.

Update reservation (Update): Modify the guest name, room number, and contact number for an existing reservation.

Delete reservation (Delete): Remove a reservation record by its ID.

🛠️ Prerequisites
To run this application, you need the following installed:

Java Development Kit (JDK) (Version 8 or higher is recommended)

MySQL Server

MySQL JDBC Driver: You must include the MySQL Connector/J library in your project's classpath.

⚙️ Setup and Configuration
1. Database Configuration
The application connects to a MySQL database named hotel_db. Before running, you must create this database and the necessary table.

Database Name: hotel_db

User Credentials:

Username: root

Password: rahul@2005 (You should change this to a secure password in a real-world scenario.)

2. Database Schema
Execute the following SQL commands to create the reservations table:

CREATE DATABASE IF NOT EXISTS hotel_db;
USE hotel_db;

CREATE TABLE IF NOT EXISTS reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(100) NOT NULL,
    room_number INT NOT NULL,
    contact_number VARCHAR(15),
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


Here is a suggested README.md content for your HotelReservation.java project.

It follows standard structure including a description, setup instructions, usage, and database schema.

Hotel Reservation Management System
This is a command-line Java application for managing hotel room reservations using a MySQL database. It allows users to perform standard CRUD (Create, Read, Update, Delete) operations on reservation records.

🔑 Key Features
Reserve a room (Create): Add a new reservation with guest details.

View all Reservations (Read): Display a tabular list of all current reservations.

Get room number (Read): Retrieve the room number for a specific reservation ID and guest name.

Update reservation (Update): Modify the guest name, room number, and contact number for an existing reservation.

Delete reservation (Delete): Remove a reservation record by its ID.

🛠️ Prerequisites
To run this application, you need the following installed:

Java Development Kit (JDK) (Version 8 or higher is recommended)

MySQL Server

MySQL JDBC Driver: You must include the MySQL Connector/J library in your project's classpath.

⚙️ Setup and Configuration
1. Database Configuration
The application connects to a MySQL database named hotel_db. Before running, you must create this database and the necessary table.

Database Name: hotel_db

User Credentials:

Username: root

Password: rahul@2005 (You should change this to a secure password in a real-world scenario.)

2. Database Schema
Execute the following SQL commands to create the reservations table:

SQL
CREATE DATABASE IF NOT EXISTS hotel_db;
USE hotel_db;

CREATE TABLE IF NOT EXISTS reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(100) NOT NULL,
    room_number INT NOT NULL,
    contact_number VARCHAR(15),
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

3. Java Code Configuration
If your MySQL credentials are different, update the following fields in the HotelReservation.java file:

private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
private static final String username = "root";
private static final String password = "pass@123"; // <--- CHANGE THIS
