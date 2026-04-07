import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

// Unit 6: JPanel, GridLayout, Event Handling
class RoomsPanel extends JPanel {

    RoomsPanel() {
        setBackground(new Color(20, 20, 30));
        setLayout(new BorderLayout(0, 14));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Room Status");
        title.setFont(new Font("Georgia", Font.BOLD, 22));
        title.setForeground(new Color(212, 175, 95));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 4, 12, 12));
        grid.setBackground(new Color(20, 20, 30));

        // Unit 2: for loop + if-else for status color
        for (int i = 0; i < HotelData.rooms.size(); i++) {
            Room room = HotelData.rooms.get(i);

            Color borderColor;
            if (room.status.equals("FREE")) {
                borderColor = new Color(80, 180, 100);
            } else {
                borderColor = new Color(212, 175, 95);
            }

            JPanel card = new JPanel(new GridLayout(4, 1, 0, 4));
            card.setBackground(new Color(24, 24, 34));
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
            ));

            JLabel numLabel = new JLabel("Room " + room.number, SwingConstants.CENTER);
            numLabel.setFont(new Font("Georgia", Font.BOLD, 14));
            numLabel.setForeground(borderColor);

            JLabel typeLabel = new JLabel(room.type, SwingConstants.CENTER);
            typeLabel.setFont(new Font("Serif", Font.ITALIC, 12));
            typeLabel.setForeground(Color.GRAY);

            JLabel rateLabel = new JLabel("Rs." + room.ratePerNight + "/night", SwingConstants.CENTER);
            rateLabel.setFont(new Font("Monospaced", Font.PLAIN, 11));
            rateLabel.setForeground(Color.WHITE);

            JLabel statusLabel = new JLabel(room.status, SwingConstants.CENTER);
            statusLabel.setFont(new Font("Monospaced", Font.BOLD, 11));
            statusLabel.setForeground(borderColor);

            card.add(numLabel);
            card.add(typeLabel);
            card.add(rateLabel);
            card.add(statusLabel);
            grid.add(card);
        }

        add(grid, BorderLayout.CENTER);
    }
}

// Unit 7: ArrayList, for-each loop, Unit 6: JTable, JScrollPane
class BookingsPanel extends JPanel {

    BookingsPanel() {
        setBackground(new Color(20, 20, 30));
        setLayout(new BorderLayout(0, 14));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("All Bookings");
        title.setFont(new Font("Georgia", Font.BOLD, 22));
        title.setForeground(new Color(212, 175, 95));
        add(title, BorderLayout.NORTH);

        // Unit 1: String array for column names
        String[] columns = {"ID", "Guest", "Room", "Check In", "Check Out", "Days", "Total", "Status"};
        Object[][] rows;

        // Unit 2: if-else for empty list
        if (HotelData.bookings.isEmpty()) {
            rows = new Object[][]{{"—", "No bookings", "—", "—", "—", "—", "—", "—"}};
        } else {
            // Unit 7: ArrayList access with index-based for loop
            rows = new Object[HotelData.bookings.size()][8];
            for (int i = 0; i < HotelData.bookings.size(); i++) {
                Booking b = HotelData.bookings.get(i);
                rows[i] = new Object[]{b.bookingId, b.guestName, b.roomNumber, b.checkIn, b.checkOut, b.nights, "Rs." + b.totalAmount, b.status};
            }
        }

        // Unit 6: JTable in JScrollPane
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
        add(scrollPane, BorderLayout.CENTER);
    }
}

// Unit 6: JPanel, JDialog, Unit 7: ArrayList
class FeedbackPanel extends JPanel {

    FeedbackPanel() {
        setBackground(new Color(20, 20, 30));
        setLayout(new BorderLayout(0, 14));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buildPanel();
    }

    void buildPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(20, 20, 30));

        JLabel title = new JLabel("Feedback");
        title.setFont(new Font("Georgia", Font.BOLD, 22));
        title.setForeground(new Color(212, 175, 95));

        JButton addButton = new JButton("+ Add");
        addButton.setBackground(new Color(212, 175, 95));
        addButton.setForeground(new Color(20, 20, 30));
        addButton.setFont(new Font("Georgia", Font.BOLD, 12));
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> openFeedbackDialog());

        header.add(title, BorderLayout.WEST);
        header.add(addButton, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBackground(new Color(20, 20, 30));

        // Unit 7: for-each loop over ArrayList
        for (Feedback f : HotelData.feedbacks) {
            JPanel card = new JPanel(new BorderLayout(8, 4));
            card.setBackground(new Color(24, 24, 34));
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 48, 40)),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
            ));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            // Unit 2: for loop to build star string (iteration)
            String starString = "";
            for (int i = 0; i < f.stars; i++) {
                starString = starString + "★";
            }
            for (int i = f.stars; i < 5; i++) {
                starString = starString + "☆";
            }

            JLabel starLabel = new JLabel(starString);
            starLabel.setFont(new Font("Serif", Font.PLAIN, 14));
            starLabel.setForeground(new Color(212, 175, 95));

            JLabel nameLabel = new JLabel(f.guestName + "  —  " + f.date);
            nameLabel.setFont(new Font("Serif", Font.BOLD, 12));
            nameLabel.setForeground(Color.WHITE);

            JPanel topRow = new JPanel(new BorderLayout());
            topRow.setOpaque(false);
            topRow.add(nameLabel, BorderLayout.WEST);
            topRow.add(starLabel, BorderLayout.EAST);

            JLabel msgLabel = new JLabel(f.message);
            msgLabel.setFont(new Font("Serif", Font.ITALIC, 12));
            msgLabel.setForeground(Color.GRAY);

            card.add(topRow, BorderLayout.NORTH);
            card.add(msgLabel, BorderLayout.CENTER);
            list.add(card);
            list.add(Box.createVerticalStrut(8));
        }

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.getViewport().setBackground(new Color(20, 20, 30));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Unit 6: JDialog, Event Handling, Unit 5: basic input validation
    void openFeedbackDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Feedback", true);
        dialog.setSize(380, 300);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(20, 20, 30));
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBackground(new Color(20, 20, 30));
        form.setBorder(BorderFactory.createEmptyBorder(20, 24, 10, 24));

        JTextField nameField = makeTextField();
        JTextArea commentArea = new JTextArea(3, 20);
        commentArea.setBackground(new Color(35, 35, 50));
        commentArea.setForeground(Color.WHITE);
        commentArea.setCaretColor(Color.WHITE);
        commentArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        commentArea.setLineWrap(true);

        // Unit 1: String array for combo box options
        String[] ratingOptions = {"5 - Excellent", "4 - Good", "3 - Average", "2 - Poor", "1 - Bad"};
        JComboBox<String> ratingCombo = new JComboBox<>(ratingOptions);
        ratingCombo.setBackground(new Color(35, 35, 50));
        ratingCombo.setForeground(Color.WHITE);

        form.add(makeLabel("Your Name *"));
        form.add(nameField);
        form.add(makeLabel("Rating"));
        form.add(ratingCombo);
        form.add(makeLabel("Comment *"));
        form.add(new JScrollPane(commentArea));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        buttonRow.setBackground(new Color(20, 20, 30));
        JButton submitBtn = makeButton("Submit");
        JButton cancelBtn = makeButton("Cancel");
        cancelBtn.setBackground(new Color(60, 60, 80));
        buttonRow.add(submitBtn);
        buttonRow.add(cancelBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(buttonRow, BorderLayout.SOUTH);

        cancelBtn.addActionListener(e -> dialog.dispose());

        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String comment = commentArea.getText().trim();

                // Unit 2: if-else for validation
                if (name.isEmpty() || comment.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Fill all fields!", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Unit 1: operator - subtraction to get star count from index
                int stars = 5 - ratingCombo.getSelectedIndex();

                // Unit 7: ArrayList.add() to insert into list
                HotelData.feedbacks.add(0, new Feedback(name, comment, stars, LocalDate.now().toString()));
                dialog.dispose();
                JOptionPane.showMessageDialog(null, "Thank you, " + name + "!", "Done", JOptionPane.INFORMATION_MESSAGE);

                removeAll();
                buildPanel();
                revalidate();
                repaint();
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
        button.setFont(new Font("Georgia", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 36));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
}

// Unit 6: JPanel, GridLayout, Event Handling, MouseListener
class AdminPanel extends JPanel {

    AdminPanel() {
        setBackground(new Color(20, 20, 30));
        setLayout(new BorderLayout(0, 14));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Admin Panel");
        title.setFont(new Font("Georgia", Font.BOLD, 22));
        title.setForeground(new Color(212, 175, 95));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 3, 14, 14));
        grid.setBackground(new Color(20, 20, 30));

        grid.add(makeCard("Add Room",       e -> addRoom()));
        grid.add(makeCard("Hotel Stats",    e -> showStats()));
        grid.add(makeCard("Add Room Type",  e -> addRoomType()));
        grid.add(makeCard("Clear Feedback", e -> clearFeedback()));
        grid.add(makeCard("View Guests",    e -> viewGuests()));
        grid.add(makeCard("About",          e -> showAbout()));

        add(grid, BorderLayout.CENTER);
    }

    // Unit 6: MouseListener for hover effect and click
    JPanel makeCard(String label, ActionListener action) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(24, 24, 34));
        card.setBorder(BorderFactory.createLineBorder(new Color(50, 48, 40)));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel cardLabel = new JLabel(label, SwingConstants.CENTER);
        cardLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        cardLabel.setForeground(new Color(212, 175, 95));
        cardLabel.setBorder(BorderFactory.createEmptyBorder(30, 14, 30, 14));
        card.add(cardLabel);

        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(new ActionEvent(card, 0, ""));
            }
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(36, 34, 20));
                card.repaint();
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(new Color(24, 24, 34));
                card.repaint();
            }
        });

        return card;
    }

    void addRoom() {
        JTextField numberField = makeTextField();
        JTextField priceField = makeTextField();
        JTextField amenitiesField = makeTextField();

        String[] typeOptions = {"Standard", "Deluxe", "Suite"};
        JComboBox<String> typeCombo = new JComboBox<>(typeOptions);
        typeCombo.setBackground(new Color(35, 35, 50));
        typeCombo.setForeground(Color.WHITE);

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBackground(new Color(20, 20, 30));
        form.add(makeLabel("Room Number")); form.add(numberField);
        form.add(makeLabel("Price/Night")); form.add(priceField);
        form.add(makeLabel("Amenities"));  form.add(amenitiesField);
        form.add(makeLabel("Type"));       form.add(typeCombo);

        int result = JOptionPane.showConfirmDialog(this, form, "Add Room", JOptionPane.OK_CANCEL_OPTION);

        // Unit 2: if with try-catch (Unit 5)
        if (result == JOptionPane.OK_OPTION) {
            try {
                String roomNumber = numberField.getText().trim();
                String roomType = (String) typeCombo.getSelectedItem();
                int price = Integer.parseInt(priceField.getText().trim());

                // Unit 7: ArrayList.add()
                HotelData.rooms.add(new Room(roomNumber, roomType, price));
                JOptionPane.showMessageDialog(this, "Room added!", "Done", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid price!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Unit 1: operators, Unit 2: for loop to calculate stats
    void showStats() {
        int freeCount = 0;
        int busyCount = 0;
        int totalRevenue = 0;

        // Unit 2: for loop
        for (int i = 0; i < HotelData.rooms.size(); i++) {
            if (HotelData.rooms.get(i).status.equals("FREE")) {
                freeCount++;
            } else {
                busyCount++;
            }
        }

        for (int i = 0; i < HotelData.bookings.size(); i++) {
            totalRevenue = totalRevenue + HotelData.bookings.get(i).totalAmount;
        }

        JOptionPane.showMessageDialog(this,
            "Total Rooms: " + HotelData.rooms.size() +
            "\nFree: " + freeCount + "  Occupied: " + busyCount +
            "\nTotal Bookings: " + HotelData.bookings.size() +
            "\nTotal Revenue: Rs." + totalRevenue,
            "Hotel Stats", JOptionPane.INFORMATION_MESSAGE);
    }

    void addRoomType() {
        JOptionPane.showMessageDialog(this, "Feature: Custom room types\nComing soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    void clearFeedback() {
        int choice = JOptionPane.showConfirmDialog(this, "Delete all feedback?", "Confirm", JOptionPane.YES_NO_OPTION);
        // Unit 2: if selection
        if (choice == JOptionPane.YES_OPTION) {
            HotelData.feedbacks.clear();
            JOptionPane.showMessageDialog(this, "Feedback cleared.", "Done", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Unit 3: String methods, Unit 2: for loop + if
    void viewGuests() {
        // Unit 3: String, StringBuffer (append pattern)
        StringBuffer guestList = new StringBuffer("Guest List\n\n");

        if (HotelData.bookings.isEmpty()) {
            guestList.append("No guests yet.");
        } else {
            for (int i = 0; i < HotelData.bookings.size(); i++) {
                Booking b = HotelData.bookings.get(i);
                guestList.append(b.guestName);
                guestList.append("  —  Room ");
                guestList.append(b.roomNumber);
                guestList.append("  (");
                guestList.append(b.status);
                guestList.append(")\n");
            }
        }

        JOptionPane.showMessageDialog(this, guestList.toString(), "Guests", JOptionPane.INFORMATION_MESSAGE);
    }

    void showAbout() {
        JOptionPane.showMessageDialog(this,
            "MPSTME Hotel Management System\n" +
            "Project by: Nishant Parwani\n" +
            "Subject: OOP using Java\n\n" +
            "Features: Login, Check In/Out,\nRooms, Bookings, Feedback, Admin",
            "About", JOptionPane.INFORMATION_MESSAGE);
    }

    JTextField makeTextField() {
        JTextField field = new JTextField();
        field.setBackground(new Color(35, 35, 50));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Monospaced", Font.PLAIN, 12));
        return field;
    }

    JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.PLAIN, 12));
        label.setForeground(Color.GRAY);
        return label;
    }
}
