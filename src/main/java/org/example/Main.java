package org.example;
import javax.swing.*;
import java.awt.*;

public class Main {
    private static final int NumberOfSeats = 50;
    private static boolean[] seatAvailability = new boolean[NumberOfSeats];
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("E.Kul Cinema");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(4, 8, 4, 8);
        JButton displayButton = new JButton("Display all Seats");
        displayButton.addActionListener(e -> displaySeats());
        panel.add(displayButton, gbc);
        gbc.gridy++;
        JButton buyTicketsButton = new JButton("Buy Tickets");
        buyTicketsButton.addActionListener(e -> buyTickets());
        panel.add(buyTicketsButton, gbc);
        gbc.gridy++;
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exitApplication());
        panel.add(exitButton, gbc);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private static void displaySeats() {
        StringBuilder seatStatus = new StringBuilder("E.Kul Cinema Seats:\n");
        for (int i = 0; i < NumberOfSeats; i++) {
            seatStatus.append(seatAvailability[i] ? "X " : i + 1 + " ");
            if ((i + 1) % 10 == 0) {
                seatStatus.append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, seatStatus.toString(), "E.Kul Cinema - Seat Availability", JOptionPane.INFORMATION_MESSAGE);
    }
    private static int getNumberOfTickets() {
        int numberOfTickets;
        do {
            String input = JOptionPane.showInputDialog("Enter the number of tickets you want to purchase:");
            try {
                numberOfTickets = Integer.parseInt(input);
                if (numberOfTickets <= 0) {
                    JOptionPane.showMessageDialog(null, "Number of tickets must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (numberOfTickets > NumberOfSeats) {
                    JOptionPane.showMessageDialog(null, "Number of tickets exceeds available seats.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                numberOfTickets = -1;
            }
        } while (numberOfTickets <= 0 || numberOfTickets > NumberOfSeats);
        return numberOfTickets;
    }
    private static int getSeatNumber(String prompt) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(prompt);
                if (input == null) {
                    return -1;
                }
                int seatNumber = Integer.parseInt(input);
                if (seatNumber < 1 || seatNumber > NumberOfSeats) {
                    JOptionPane.showMessageDialog(null, "Invalid seat number. Please enter a valid seat.");
                } else {
                    return seatNumber;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private static void buyTickets() {
        int numberOfTickets = getNumberOfTickets();
        if (numberOfTickets == -1) {
            return;
        }
        StringBuilder purchaseResult = new StringBuilder("Your Tickets:\n");

        for (int i = 0; i < numberOfTickets; i++) {
            int seatNumber = getSeatNumber("Enter the seat number for ticket " + (i + 1) + ":");
            boolean seatAlreadyTaken = seatAvailability[seatNumber - 1];
            if (seatAlreadyTaken) {
                JOptionPane.showMessageDialog(null, "This seat is already taken. Please try again.");
                i--;
            } else {
                seatAvailability[seatNumber - 1] = true;
                purchaseResult.append("Ticket ").append(i + 1).append(": Successfully purchased seat number ").append(seatNumber).append(".\n");
            }
        }
        JOptionPane.showMessageDialog(null, purchaseResult.toString(), "Your Tickets", JOptionPane.INFORMATION_MESSAGE);
    }
    private static void exitApplication() {
        System.out.println("Exiting.. Thank you for using E.Kul cinema");
        System.exit(0);
    }
}