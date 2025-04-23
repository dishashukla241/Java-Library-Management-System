import java.util.*;

public class BookManager {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(int id) {
        for (Book b : books) {
            if (b.id == id) return b;
        }
        return null;
    }

    public boolean deleteBook(int id) {
        Iterator<Book> it = books.iterator();
        while (it.hasNext()) {
            if (it.next().id == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}
