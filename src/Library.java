import java.util.List;

public class Library {

    private final long id; // 0 ~ 10^5

    private final long numberOfBooks; // N, 1 ~ 10^5
    private final long numberOfDaysTakenToSignUp; // T, 1 ~ 10^5
    private final long numberOfBooksScannablePerDay; // M, 1 ~ 10^5

    private final List<Book> books;

    public Library(long id, long numberOfBooks, long numberOfDaysTakenToSignUp, long numberOfBooksScannablePerDay, List<Book> books) {
        this.id = id;
        this.numberOfBooks = numberOfBooks;
        this.numberOfDaysTakenToSignUp = numberOfDaysTakenToSignUp;
        this.numberOfBooksScannablePerDay = numberOfBooksScannablePerDay;
        this.books = books;
    }

    public long getId() {
        return id;
    }

    public long getNumberOfBooks() {
        return numberOfBooks;
    }

    public long getNumberOfDaysTakenToSignUp() {
        return numberOfDaysTakenToSignUp;
    }

    public long getNumberOfBooksScannablePerDay() {
        return numberOfBooksScannablePerDay;
    }

    public List<Book> getBooks() {
        return books;
    }
}
