import java.sql.*;
import java.util.*;

public class BookManager {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }
    private final String url = "jdbc:mysql://localhost:3306/library_db";
    private final String user = "root";
    private final String pass = "disha2005";

    public void addBook(Book book) {
        try {
            // Register the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
    
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                String sql = "INSERT INTO books (id, title, author, quantity) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, book.id);
                stmt.setString(2, book.title);
                stmt.setString(3, book.author);
                stmt.setInt(4, book.quantity);
    
                int rows = stmt.executeUpdate();  // Execute and get number of affected rows
                if (rows > 0) {
                    System.out.println("✅ Book inserted successfully into the database: " + book);
                } else {
                    System.out.println("⚠️ Book insertion failed for: " + book);
                }
    
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("🚫 Book with this ID already exists.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver not found. Add the connector JAR to your project.");
            e.printStackTrace();
        }
    }
    

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT * FROM books";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book b = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("quantity")
                );
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Book getBookById(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT * FROM books WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteBook(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "DELETE FROM books WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
