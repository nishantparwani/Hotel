import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}

// Unit 1: Class, variables, datatypes, literals, identifiers
class Room {
    String number;
    String type;
    String status;
    int ratePerNight;

    Room(String number, String type, int ratePerNight) {
        this.number = number;
        this.type = type;
        this.ratePerNight = ratePerNight;
        this.status = "FREE";
    }
}

class Booking {
    String bookingId;
    String guestName;
    String roomNumber;
    String checkIn;
    String checkOut;
    int nights;
    int totalAmount;
    String status;

    Booking(String bookingId, String guestName, String roomNumber, String checkIn, String checkOut, int nights, int totalAmount) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.nights = nights;
        this.totalAmount = totalAmount;
        this.status = "IN";
    }
}

class Feedback {
    String guestName;
    String message;
    String date;
    int stars;

    Feedback(String guestName, String message, int stars, String date) {
        this.guestName = guestName;
        this.message = message;
        this.stars = stars;
        this.date = date;
    }
}

// Unit 3: Class with static data, methods, objects
class HotelData {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static ArrayList<Feedback> feedbacks = new ArrayList<>();
    static int bookingCounter = 100;

    // Unit 2: static block uses selection + initialization
    static {
        rooms.add(new Room("101", "Standard", 1800));
        rooms.add(new Room("102", "Standard", 1800));
        rooms.add(new Room("201", "Deluxe", 3200));
        rooms.add(new Room("202", "Deluxe", 3200));
        rooms.add(new Room("301", "Suite", 6500));
        rooms.add(new Room("302", "Suite", 6500));
        feedbacks.add(new Feedback("Rahul V.", "Great stay!", 5, "2025-01-10"));
        feedbacks.add(new Feedback("Priya S.", "Very nice rooms.", 4, "2025-01-15"));
    }

    // Unit 3: methods in a class
    static Room findRoom(String number) {
        // Unit 2: for loop + if
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).number.equals(number)) {
                return rooms.get(i);
            }
        }
        return null;
    }

    static Booking getActiveBooking(String roomNumber) {
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            if (b.roomNumber.equals(roomNumber) && b.status.equals("IN")) {
                return b;
            }
        }
        return null;
    }

    static String generateBookingId() {
        bookingCounter++;
        return "BK" + bookingCounter;
    }
}
