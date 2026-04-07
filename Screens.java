import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

// Unit 6: JPanel container, layout, event handling
class HomePanel extends JPanel {

    HomePanel() {
        setBackground(new Color(20, 20, 30));
        setLayout(new BorderLayout(0, 16));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Georgia", Font.BOLD, 22));
        title.setForeground(new Color(212, 175, 95));
        add(title, BorderLayout.NORTH);

        // Unit 2: for loop + if to count room statuses
        int freeCount = 0;
        int busyCount = 0;
        for (int i = 0; i < HotelData.rooms.size(); i++) {
            if (HotelData.rooms.get(i).status.equals("FREE")) {
                freeCount++;
            } else {
                busyCount++;
            }
        }

        JPanel statsRow = new JPanel(new GridLayout(1, 3, 14, 0));
        statsRow.setBackground(new Color(20, 20, 30));
        statsRow.add(makeStatCard("Free Rooms", "" + freeCount, new Color(80, 180, 100)));
        statsRow.add(makeStatCard("Occupied", "" + busyCount, new Color(212, 175, 95)));
        statsRow.add(makeStatCard("Total Guests", "" + HotelData.bookings.size(), new Color(100, 160, 220)));

        JPanel middle = new JPanel(new BorderLayout(0, 14));
        middle.setBackground(new Color(20, 20, 30));
        middle.add(statsRow, BorderLayout.NORTH);
        middle.add(makeBookingsTable(), BorderLayout.CENTER);
        add(middle, BorderLayout.CENTER);
    }

    JPanel makeStatCard(String label, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(24, 24, 34));
        card.setBorder(BorderFactory.createLineBorder(new Color(50, 48, 40)));
        card.setPreferredSize(new Dimension(0, 90));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Georgia", Font.BOLD, 34));
        valueLabel.setForeground(color);

        JLabel keyLabel = new JLabel(label, SwingConstants.CENTER);
        keyLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        keyLabel.setForeground(Color.GRAY);

        JPanel inner = new JPanel(new GridLayout(2, 1));
        inner.setOpaque(false);
        inner.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inner.add(valueLabel);
        inner.add(keyLabel);
        card.add(inner);
        return card;
    }

    JPanel makeBookingsTable() {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(new Color(24, 24, 34));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 48, 40)),
            BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));

        JLabel tableTitle = new JLabel("Recent Bookings");
        tableTitle.setFont(new Font("Georgia", Font.BOLD, 14));
        tableTitle.setForeground(new Color(212, 175, 95));
        card.add(tableTitle, BorderLayout.NORTH);

        // Unit 1: String array for column headers
        String[] columns = {"ID", "Guest", "Room", "Check In", "Check Out", "Total", "Status"};
        Object[][] rows;

        // Unit 2: if-else to handle empty list
        if (HotelData.bookings.isEmpty()) {
            rows = new Object[][]{{"—", "No bookings yet", "—", "—", "—", "—", "—"}};
        } else {
            int count = HotelData.bookings.size();
            int showMax = 8;
            // Unit 2: nested if inside for (iteration + selection)
            int limit = count < showMax ? count : showMax;
            rows = new Object[limit][7];
            for (int i = 0; i < limit; i++) {
                Booking b = HotelData.bookings.get(count - 1 - i);
                rows[i] = new Object[]{b.bookingId, b.guestName, b.roomNumber, b.checkIn, b.checkOut, "Rs." + b.totalAmount, b.status};
            }
        }

        // Unit 6: JTable inside JScrollPane
        JTable table = new JTable(rows, columns) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table.setBackground(new Color(24, 24, 34));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Monospaced", Font.PLAIN, 12));
        table.setRowHeight(26);
        table.setGridColor(new Color(50, 48, 40));
        table.getTableHeader().setBackground(new Color(20, 20, 30));
        table.getTableHeader().setForeground(new Color(212, 175, 95));
        table.getTableHeader().setFont(new Font("Georgia", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(24, 24, 34));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }
}

// Unit 6: JPanel, Event Handling
class CheckInPanel extends JPanel {

    CheckInPanel() {
        setBackground(new Color(20, 20, 30));
        setLayout(new BorderLayout(0, 14));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buildPanel();
    }

    void buildPanel() {
        JLabel title = new JLabel("Check In");
        title.setFont(new Font("Georgia", Font.BOLD, 22));
        title.setForeground(new Color(212, 175, 95));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 4, 12, 12));
        grid.setBackground(new Color(20, 20, 30));

        // Unit 2: for loop with if to show only free rooms
        for (int i = 0; i < HotelData.rooms.size(); i++) {
            Room room = HotelData.rooms.get(i);
            if (room.status.equals("FREE")) {
                grid.add(makeRoomCard(room));
            }
        }

        // Unit 2: if to handle empty case
        if (grid.getComponentCount() == 0) {
            JLabel empty = new JLabel("No rooms available", SwingConstants.CENTER);
            empty.setForeground(Color.GRAY);
            grid.add(empty);
        }

        add(grid, BorderLayout.CENTER);
    }

    JPanel makeRoomCard(Room room) {
        JPanel card = new JPanel(new GridLayout(4, 1, 0, 4));
        card.setBackground(new Color(24, 24, 34));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 48, 40)),
            BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));
        card.setPreferredSize(new Dimension(160, 140));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel roomNum = new JLabel("Room " + room.number, SwingConstants.CENTER);
        roomNum.setFont(new Font("Georgia", Font.BOLD, 15));
        roomNum.setForeground(new Color(212, 175, 95));

        JLabel roomType = new JLabel(room.type, SwingConstants.CENTER);
        roomType.setFont(new Font("Serif", Font.ITALIC, 12));
        roomType.setForeground(Color.GRAY);

        JLabel roomRate = new JLabel("Rs." + room.ratePerNight + "/night", SwingConstants.CENTER);
        roomRate.setFont(new Font("Monospaced", Font.BOLD, 12));
        roomRate.setForeground(new Color(80, 180, 100));

        JLabel clickHint = new JLabel("Click to Check In", SwingConstants.CENTER);
        clickHint.setFont(new Font("Serif", Font.PLAIN, 10));
        clickHint.setForeground(Color.GRAY);

        card.add(roomNum);
        card.add(roomType);
        card.add(roomRate);
        card.add(clickHint);

        // Unit 6: MouseListener for event handling
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                openCheckInDialog(room);
            }
        });
        return card;
    }

    // Unit 6: JDialog, Event Handling, Unit 5: Exception Handling
    void openCheckInDialog(Room room) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Check In - Room " + room.number, true);
        dialog.setSize(400, 380);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(20, 20, 30));
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBackground(new Color(20, 20, 30));
        form.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JTextField nameField = makeTextField();
        JTextField phoneField = makeTextField();
        JTextField checkInField = makeTextField();
        JTextField checkOutField = makeTextField();

        checkInField.setText(LocalDate.now().toString());
        checkOutField.setText(LocalDate.now().plusDays(2).toString());

        form.add(makeLabel("Guest Name *"));
        form.add(nameField);
        form.add(makeLabel("Phone *"));
        form.add(phoneField);
        form.add(makeLabel("Check-In *"));
        form.add(checkInField);
        form.add(makeLabel("Check-Out *"));
        form.add(checkOutField);
        form.add(makeLabel("Room"));
        form.add(makeLabel("Room " + room.number + " - Rs." + room.ratePerNight));
        form.add(makeLabel("Type"));
        form.add(makeLabel(room.type));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        buttonRow.setBackground(new Color(20, 20, 30));
        JButton confirmBtn = makeButton("Confirm");
        JButton cancelBtn = makeButton("Cancel");
        cancelBtn.setBackground(new Color(60, 60, 80));
        buttonRow.add(confirmBtn);
        buttonRow.add(cancelBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(buttonRow, BorderLayout.SOUTH);

        cancelBtn.addActionListener(e -> dialog.dispose());

        confirmBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String ci = checkInField.getText().trim();
                String co = checkOutField.getText().trim();

                // Unit 2: if-else for input validation
                if (name.isEmpty() || ci.isEmpty() || co.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Fill all fields!", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Unit 5: try-catch exception handling
                try {
                    LocalDate checkInDate = LocalDate.parse(ci);
                    LocalDate checkOutDate = LocalDate.parse(co);

                    if (!checkOutDate.isAfter(checkInDate)) {
                        JOptionPane.showMessageDialog(dialog, "Check-out must be after check-in!", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int nights = (int)(checkOutDate.toEpochDay() - checkInDate.toEpochDay());
                    int total = nights * room.ratePerNight;

                    HotelData.bookings.add(new Booking(HotelData.generateBookingId(), name, room.number, ci, co, nights, total));
                    room.status = "BUSY";
                    dialog.dispose();

                    JOptionPane.showMessageDialog(null,
                        "Check In Confirmed!\n\nGuest: " + name + "\nRoom: " + room.number + "\nNights: " + nights + "\nTotal: Rs." + total,
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                    removeAll();
                    buildPanel();
                    revalidate();
                    repaint();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Use YYYY-MM-DD format", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.setVisible(true);
    }

    JTextField makeTextField() {
        JTextField field = new JTextField();
        field.setBackground(new Color(35, 35, 50));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Monospaced", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 58, 50)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }

    JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.PLAIN, 12));
        label.setForeground(Color.GRAY);
        return label;
    }

    JButton makeButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(212, 175, 95));
        button.setForeground(new Color(20, 20, 30));
        button.setFont(new Font("Georgia", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(130, 36));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
}

// Unit 6: JPanel, JDialog, Event Handling
class CheckOutPanel extends JPanel {

    CheckOutPanel() {
        setBackground(new Color(20, 20, 30));
        setLayout(new BorderLayout(0, 14));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buildPanel();
    }

    void buildPanel() {
        JLabel title = new JLabel("Check Out");
        title.setFont(new Font("Georgia", Font.BOLD, 22));
        title.setForeground(new Color(212, 175, 95));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 3, 12, 12));
        grid.setBackground(new Color(20, 20, 30));

        // Unit 2: for loop + if-else
        for (int i = 0; i < HotelData.rooms.size(); i++) {
            Room room = HotelData.rooms.get(i);
            if (room.status.equals("BUSY")) {
                Booking booking = HotelData.getActiveBooking(room.number);
                if (booking != null) {
                    grid.add(makeRoomCard(room, booking));
                }
            }
        }

        if (grid.getComponentCount() == 0) {
            JLabel empty = new JLabel("No occupied rooms", SwingConstants.CENTER);
            empty.setForeground(Color.GRAY);
            grid.add(empty);
        }

        add(grid, BorderLayout.CENTER);
    }

    JPanel makeRoomCard(Room room, Booking booking) {
        JPanel card = new JPanel(new GridLayout(5, 1, 0, 4));
        card.setBackground(new Color(24, 24, 34));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 48, 40)),
            BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));
        card.setPreferredSize(new Dimension(200, 160));

        JLabel roomNum = new JLabel("Room " + room.number);
        roomNum.setFont(new Font("Georgia", Font.BOLD, 14));
        roomNum.setForeground(new Color(212, 175, 95));

        JLabel guestName = new JLabel("Guest: " + booking.guestName);
        guestName.setFont(new Font("Serif", Font.PLAIN, 12));
        guestName.setForeground(Color.WHITE);

        JLabel dates = new JLabel("In: " + booking.checkIn + "  Out: " + booking.checkOut);
        dates.setFont(new Font("Monospaced", Font.PLAIN, 10));
        dates.setForeground(Color.GRAY);

        JLabel amount = new JLabel("Total: Rs." + booking.totalAmount);
        amount.setFont(new Font("Monospaced", Font.BOLD, 12));
        amount.setForeground(new Color(80, 180, 100));

        JButton checkOutBtn = new JButton("Check Out");
        checkOutBtn.setBackground(new Color(212, 175, 95));
        checkOutBtn.setForeground(new Color(20, 20, 30));
        checkOutBtn.setFont(new Font("Georgia", Font.BOLD, 12));
        checkOutBtn.setFocusPainted(false);
        checkOutBtn.setBorderPainted(false);
        checkOutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        card.add(roomNum);
        card.add(guestName);
        card.add(dates);
        card.add(amount);
        card.add(checkOutBtn);

        checkOutBtn.addActionListener(e -> openCheckOutDialog(room, booking));
        return card;
    }

    void openCheckOutDialog(Room room, Booking booking) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Check Out", true);
        dialog.setSize(360, 340);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(20, 20, 30));
        dialog.setLayout(new BorderLayout());

        JPanel body = new JPanel(new GridLayout(0, 2, 8, 8));
        body.setBackground(new Color(20, 20, 30));
        body.setBorder(BorderFactory.createEmptyBorder(20, 24, 10, 24));

        // Unit 1: operators - arithmetic operators to calculate GST
        int gst = (int)(booking.totalAmount * 0.12);
        int payable = booking.totalAmount + gst;

        // Unit 1: 2D String array (array of arrays)
        String[][] billRows = {
            {"Guest",        booking.guestName},
            {"Room",         booking.roomNumber + " (" + room.type + ")"},
            {"Check-In",     booking.checkIn},
            {"Check-Out",    booking.checkOut},
            {"Nights",       "" + booking.nights},
            {"Room Charge",  "Rs." + booking.totalAmount},
            {"GST (12%)",    "Rs." + gst},
            {"TOTAL",        "Rs." + payable}
        };

        // Unit 2: for loop to build bill rows
        for (int i = 0; i < billRows.length; i++) {
            JLabel keyLabel = new JLabel(billRows[i][0]);
            keyLabel.setFont(new Font("Serif", Font.PLAIN, 12));
            keyLabel.setForeground(Color.GRAY);

            JLabel valLabel = new JLabel(billRows[i][1]);
            valLabel.setFont(new Font("Monospaced", Font.BOLD, 12));

            // Unit 2: if-else inside loop
            if (billRows[i][0].equals("TOTAL")) {
                valLabel.setForeground(new Color(212, 175, 95));
            } else {
                valLabel.setForeground(Color.WHITE);
            }

            body.add(keyLabel);
            body.add(valLabel);
        }

        JLabel payLabel = new JLabel("Payment:");
        payLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        payLabel.setForeground(Color.GRAY);

        String[] paymentOptions = {"Cash", "Card", "UPI"};
        JComboBox<String> paymentMethod = new JComboBox<>(paymentOptions);
        paymentMethod.setBackground(new Color(35, 35, 50));
        paymentMethod.setForeground(Color.WHITE);
        body.add(payLabel);
        body.add(paymentMethod);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        buttonRow.setBackground(new Color(20, 20, 30));
        JButton confirmBtn = makeButton("Confirm");
        JButton cancelBtn = makeButton("Cancel");
        cancelBtn.setBackground(new Color(60, 60, 80));
        buttonRow.add(confirmBtn);
        buttonRow.add(cancelBtn);

        dialog.add(body, BorderLayout.CENTER);
        dialog.add(buttonRow, BorderLayout.SOUTH);

        cancelBtn.addActionListener(e -> dialog.dispose());

        confirmBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                booking.status = "OUT";
                room.status = "FREE";
                dialog.dispose();
                JOptionPane.showMessageDialog(null,
                    "Check-out done for " + booking.guestName + "\nPayment via " + paymentMethod.getSelectedItem(),
                    "Done", JOptionPane.INFORMATION_MESSAGE);
                removeAll();
                buildPanel();
                revalidate();
                repaint();
            }
        });

        dialog.setVisible(true);
    }

    JButton makeButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(212, 175, 95));
        button.setForeground(new Color(20, 20, 30));
        button.setFont(new Font("Georgia", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 36));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
}
