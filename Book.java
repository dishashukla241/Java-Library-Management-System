public class Book {
    int id;
    String title;
    String author;
    int quantity;

    public Book(int id, String title, String author, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return id + " - " + title + " - " + author + " - Qty: " + quantity;
    }
}
