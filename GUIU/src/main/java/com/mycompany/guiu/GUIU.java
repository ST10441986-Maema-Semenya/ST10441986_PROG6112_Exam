package com.mycompany.guiu;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

public class GUIU extends JFrame {

    static final double VAT_RATE = 0.15;
    static final double COMMISSION_RATE = 0.05;
    static final String[] PRODUCTS = {"Cozy Nights Monarch", "Restonic Barbados", "Sealy phiucket"};
    static final Path DATA_FILE = Paths.get("data.txt").toAbsolutePath();

    private final CardLayout cards = new CardLayout();
    private final JPanel root = new JPanel(cards);

    // Input page
    private final JTextField nameField = new JTextField(20);
    private final JComboBox<String> productBox = new JComboBox<>(PRODUCTS);
    private final JTextField priceField = new JTextField(20);

    // Display page
    private final JLabel nameOut = new JLabel();
    private final JLabel productOut = new JLabel();
    private final JLabel priceOut = new JLabel();
    private final JLabel vatOut = new JLabel();
    private final JLabel commissionOut = new JLabel();
    private final JLabel totalOut = new JLabel();

    public GUIU() {
        super("Mattress Sales");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        root.add(buildInputPage(), "input");
        root.add(buildDisplayPage(), "display");
        add(root);
        pack();
        setMinimumSize(new Dimension(420, 300));
        setLocationRelativeTo(null);
    }

    private JPanel buildInputPage() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addRow(p, 0, "Name:", nameField);
        addRow(p, 1, "Product:", productBox);
        addRow(p, 2, "Price (R):", priceField);

        JButton process = new JButton("Process");
        process.addActionListener(e -> process());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        c.insets = new Insets(20, 0, 0, 0);
        p.add(process, c);

        getRootPane().setDefaultButton(process);
        return p;
    }

    private JPanel buildDisplayPage() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        totalOut.setFont(totalOut.getFont().deriveFont(Font.BOLD, 16f));

        addRow(p, 0, "Name:", nameOut);
        addRow(p, 1, "Product:", productOut);
        addRow(p, 2, "Price:", priceOut);
        addRow(p, 3, "VAT (15%):", vatOut);
        addRow(p, 4, "Commission (5%):", commissionOut);
        addRow(p, 5, "Total sale amount:", totalOut);

        JButton again = new JButton("New Sale");
        again.addActionListener(e -> {
            nameField.setText("");
            priceField.setText("");
            productBox.setSelectedIndex(0);
            cards.show(root, "input");
            getRootPane().setDefaultButton(null);
            nameField.requestFocusInWindow();
        });
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 6; c.gridwidth = 2;
        c.insets = new Insets(20, 0, 0, 0);
        p.add(again, c);
        return p;
    }

    private void addRow(JPanel p, int row, String label, JComponent field) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = row;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        p.add(new JLabel(label), c);

        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        p.add(field, c);
    }

    private void process() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            showError("Please enter a name.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceField.getText().trim().replace(',', '.'));
        } catch (NumberFormatException ex) {
            showError("Please enter a valid price, e.g. 4999.99");
            return;
        }
        if (price <= 0) {
            showError("Price must be greater than zero.");
            return;
        }

        String product = (String) productBox.getSelectedItem();
        double vat = price * VAT_RATE;
        double commission = price * COMMISSION_RATE;
        double total = price + vat + commission;

        try {
            save(name, product, price, vat, commission, total);
        } catch (IOException ex) {
            showError("Could not save to data.txt: " + ex.getMessage());
            return;
        }

        nameOut.setText(name);
        productOut.setText(product);
        priceOut.setText(money(price));
        vatOut.setText(money(vat));
        commissionOut.setText(money(commission));
        totalOut.setText(money(total));
        cards.show(root, "display");
    }

    private void save(String name, String product, double price, double vat,
                      double commission, double total) throws IOException {
        String line = String.format(Locale.US, "%s,%s,%s,%.2f,%.2f,%.2f,%.2f%n",
                LocalDateTime.now().withNano(0), name.replace(',', ' '), product,
                price, vat, commission, total);
        Files.writeString(DATA_FILE, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private static String money(double v) {
        return String.format(Locale.US, "R %,.2f", v);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Invalid input", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIU().setVisible(true));
    }
}