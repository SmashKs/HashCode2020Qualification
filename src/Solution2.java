import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * This is solution is following
 * <p>
 * # 1. have the ratio for each library
 * #     score (before the deadline) / sign up days
 * # 2. signup the library with highest ratio
 * # 3. sort our books for each library with the score
 * # when you scan the book, remove it from the map
 * # 4. have a map for all books, map book id -> score
 * # 5. if a book is not in the map, just ignore
 * # [id1 -> 5], [id2 -> 7]
 * #
 * # map.remove(id1) -> return 5
 * # map.remove(id1) -> return null
 * # [[2, 1,  0, 1, 3], [1, 2, 2, 3]]
 * # scores {0: x, 1: x}
 */
class Solution2 implements Solution {
    PriorityQueue<Pair> heap = new PriorityQueue<>((a, b) -> (int) (b.rate - a.rate));

    @Override
    public Answer getAnswer(final Input input) {
        // sorting by the rank
        input.getLibraryList().forEach(library -> {
            long totalScoreOfBooks = library.getBooks().stream().mapToLong(Book::getScore).sum();
            double rank = (double) totalScoreOfBooks / library.getNumberOfDaysTakenToSignUp();
            heap.add(new Pair(rank, library));
        });

        // scan the books
        long deadline = input.getNumberOfDays(), day = 0;
        List<Answer.Library> res = new ArrayList<>();
        Set<Book> scanned = new HashSet<>();
        while (!heap.isEmpty() && day < deadline) {
            Library library = heap.remove().library;
            // make the day go to the next
            day += library.getNumberOfDaysTakenToSignUp();
            long remainedDays = deadline - day, scannedCount = 0;
            // sort the books by the score point
            List<Book> books = sortByScore(library.getBooks());
            List<Long> scannedBookOfLibrary = new ArrayList<>();
            // scan books
            for (final Book book : books) {
                if (remainedDays <= 0) {
                    break;
                }
                if (scanned.contains(book)) {
                    continue;
                }
                scanned.add(book);
                scannedBookOfLibrary.add(book.getId());
                scannedCount++;
                if (scannedCount % library.getNumberOfBooksScannablePerDay() == 0) {
                    remainedDays--;
                }
            }
            res.add(new Answer.Library(library.getId(), scannedBookOfLibrary));
        }
        return new Answer(res);
    }

    public List<Book> sortByScore(List<Book> books) {
        books.sort((book1, book2) -> (int) (book2.getScore() - book1.getScore()));
        return books;
    }

    class Pair {
        double rate;
        Library library;

        public Pair(double rate, Library library) {
            this.rate = rate;
            this.library = library;
        }
    }
}