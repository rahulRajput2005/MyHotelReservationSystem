import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;

import static java.lang.System.exit;

public class HotelReservation {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "rahul@2005";
    public static void main(String[] args) throws SQLException {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            //Create connection and Scanner instances that are going to be used multiple in the code
            Connection connection = DriverManager.getConnection(url,username,password);
            Scanner scanner = new Scanner(System.in);
            while(true) {
                //Main menu
                System.out.println();
                System.out.println("Hotel Management System");
                System.out.println("1. Reserve a room.");
                System.out.println("2. View all Reservations.");
                System.out.println("3. Get room number.");
                System.out.println("4. Update reservation.");
                System.out.println("5. Delete reservation.");
                System.out.println("0. Exit.");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        reserveRoom(connection, scanner);
                        break;
                    case 2:
                        viewAllReservations(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection,scanner);
                        break;
                    case 5:
                        deleteReservation(connection, scanner);
                        break;
                    case 0:
                        exit();
                        return;
                    default:
                        System.out.println("Invalid Input.");
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }
        }

    private static void deleteReservation(Connection connection, Scanner scanner) {
        try{
            System.out.print("Enter reservation id to delete: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine();

            if(!reservationExists(connection, reservationId)){
                System.out.println("Reservation is not reserved !");
                return;
            }

            String query = "DELETE FROM reservations WHERE reservation_id = "+reservationId;
            try(Statement statement = connection.createStatement()){
                int affectedRows = statement.executeUpdate(query);
                if(affectedRows > 0){
                    System.out.println("Deletion Successful. "+affectedRows+" row(s) affected.");
                }else{
                    System.out.println("Deletion Failed");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void updateReservation(Connection connection, Scanner scanner) throws SQLException {
        try{
            System.out.print("Enter reservation id: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine();
            if(!reservationExists(connection, reservationId)){
                System.out.println("Reservation id not reserved !");
                return;
            }

            System.out.print("Enter new guest name: ");
            String newGuestName = scanner.next();
            System.out.println("Enter new room number: ");
            int newRoomNumber = scanner.nextInt();
            System.out.println("Enter new contact number: ");
            String newContactNumber = scanner.next();

            String query = "UPDATE reservations SET guest_name = '"+newGuestName+"',"+
                    "room_number = "+newRoomNumber+"," +
                    "contact_number ='"+newContactNumber+"'" +
                    " WHERE reservation_id = "+reservationId;

            try(Statement statement = connection.createStatement()){
                int affectedRows = statement.executeUpdate(query);

                if(affectedRows > 0){
                    System.out.println("Updation Successful . "+affectedRows+" row(s) affected");
                }else{
                    System.out.println("Updation Failed !");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static boolean reservationExists(Connection connection, int reservationId) {
        try{
            String query = "SELECT reservation_id FROM reservations WHERE reservation_id = "+reservationId;
            try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)){
                return resultSet.next();
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private static void getRoomNumber(Connection connection, Scanner scanner) {
        try{
            System.out.print("Enter reservation id: ");
            int reservationId = scanner.nextInt();
            System.out.print("Enter guest name: ");
            String guestName = scanner.next();

            String query =  "SELECT room_number FROM reservations " +
                    "WHERE reservation_id = " + reservationId +
                    " AND guest_name = '" + guestName + "'";


            try(Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)){
                if(resultSet.next()){
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("Room number for reservation id: "+reservationId+" and guest name "+guestName+" is: "+roomNumber);
                }else{
                    System.out.println("Guest not reserved!");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void viewAllReservations(Connection connection) throws SQLException{
        String query = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservations";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)){

            System.out.println("Current Reservations:");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                // Format and display the reservation data in a table-like format
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }

            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void reserveRoom(Connection connection, Scanner scanner) {
        try{
            System.out.print("Enter guest name: ");
            String guestName = scanner.next();
            scanner.nextLine();
            System.out.print("Enter room number: ");
            int roomNumber = scanner.nextInt();
            System.out.print("Enter contact number: ");
            String contactNumber = scanner.next();

            String query = "INSERT INTO reservations(guest_name, room_number, contact_number)" +
                    " VALUES('"+guestName+"',"+roomNumber+",'"+contactNumber+"');";
            try(Statement statement = connection.createStatement()){
                int affectedRows = statement.executeUpdate(query);
                if(affectedRows > 0){
                    System.out.println("Reservation Successful."+affectedRows+"  row(s) affected");
                }else{
                    System.out.println("Reservation failed");
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i=5;
        while(i!= 0){
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank you");
    }
}


