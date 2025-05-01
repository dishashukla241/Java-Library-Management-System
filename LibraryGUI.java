import java.awt.*;
import java.util.List;
import javax.swing.*;

public class LibraryGUI extends JFrame {
    BookManager manager = new BookManager();

    JTextField idField = new JTextField(5);
    JTextField titleField = new JTextField(10);
    JTextField authorField = new JTextField(10);
    JTextField quantityField = new JTextField(5);
    JTextArea outputArea = new JTextArea(10, 30);

    public LibraryGUI() {
        setTitle("Library Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Book ID:")); inputPanel.add(idField);
        inputPanel.add(new JLabel("Title:")); inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:")); inputPanel.add(authorField);
        inputPanel.add(new JLabel("Quantity:")); inputPanel.add(quantityField);

        JButton addBtn = new JButton("Add Book");
        JButton viewBtn = new JButton("View All");
        JButton searchBtn = new JButton("Search by ID");
        JButton deleteBtn = new JButton("Delete");

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(viewBtn);
        btnPanel.add(searchBtn);
        btnPanel.add(deleteBtn);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            try {
                System.out.println("🔨 Add Book button clicked");

                Book b = new Book(
                    Integer.parseInt(idField.getText()),
                    titleField.getText(),
                    authorField.getText(),
                    Integer.parseInt(quantityField.getText())
                );

                System.out.println("📦 Book to be added: " + b);
                manager.addBook(b);  // Call to BookManager

                JOptionPane.showMessageDialog(this, "Book Added!");
                clearFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check the fields.");
            }
        });

        viewBtn.addActionListener(e -> {
            outputArea.setText("");
            List<Book> list = manager.getAllBooks();
            if (list.isEmpty()) {
                outputArea.setText("No books in the library.");
            } else {
                for (Book b : list) {
                    outputArea.append(b.toString() + "\n");
                }
            }
        });

        searchBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Book b = manager.getBookById(id);
                outputArea.setText(b != null ? b.toString() : "Book not found.");
            } catch (NumberFormatException ex) {
                outputArea.setText("Please enter a valid ID.");
            }
        });

        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                boolean removed = manager.deleteBook(id);
                JOptionPane.showMessageDialog(this, removed ? "Book Deleted!" : "Book not found.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid ID.");
            }
        });
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        quantityField.setText("");
    }
}
