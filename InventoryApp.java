package student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InventoryApp extends JFrame {
    // UI components
    JTextField tfName, tfQuantity, tfPrice;
    JButton btnAdd, btnUpdate, btnDelete, btnView;
    JTable table;
    DefaultTableModel model;

    // DB connection details
    final String URL = "jdbc:mysql://localhost:3306/inventory_db";
    final String USER = "root";
    final String PASS = "";

    public InventoryApp() {
        setTitle("Inventory Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tfName = new JTextField();
        tfQuantity = new JTextField();
        tfPrice = new JTextField();

        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(tfName);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(tfQuantity);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(tfPrice);

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        inputPanel.add(btnAdd);
        inputPanel.add(btnUpdate);

        add(inputPanel, BorderLayout.NORTH);

        // Table Panel
        model = new DefaultTableModel(new String[]{"ID", "Name", "Quantity", "Price"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        btnDelete = new JButton("Delete");
        btnView = new JButton("Refresh");
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnView.addActionListener(e -> loadProducts());

        // Load data at start
        loadProducts();
    }

    private void addProduct() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "INSERT INTO products (name, quantity, price) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tfName.getText());
            pst.setInt(2, Integer.parseInt(tfQuantity.getText()));
            pst.setDouble(3, Double.parseDouble(tfPrice.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product Added Successfully!");
            clearFields();
            loadProducts();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateProduct() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a product to update.");
            return;
        }
        int id = (int) model.getValueAt(selected, 0);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "UPDATE products SET name=?, quantity=?, price=? WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tfName.getText());
            pst.setInt(2, Integer.parseInt(tfQuantity.getText()));
            pst.setDouble(3, Double.parseDouble(tfPrice.getText()));
            pst.setInt(4, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product Updated Successfully!");
            clearFields();
            loadProducts();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteProduct() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a product to delete.");
            return;
        }
        int id = (int) model.getValueAt(selected, 0);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "DELETE FROM products WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product Deleted Successfully!");
            loadProducts();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadProducts() {
        model.setRowCount(0);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "SELECT * FROM products";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error Loading Data: " + ex.getMessage());
        }
    }

    private void clearFields() {
        tfName.setText("");
        tfQuantity.setText("");
        tfPrice.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InventoryApp().setVisible(true);
        });
    }
}
