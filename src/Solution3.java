import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Solution 3 is a copy of Solution 2, but re-calculates each library's rank after a library is selected.
 *
 * 1. Calculate the rank for each library (total score for all non-scanned books and divide by number of days to sign up)
 * 2. Choose library with best score
 * 3. Start from #1 again with remaining libraries
 */
public class Solution3 implements Solution {

    PriorityQueue<Solution3.Pair> heap = new PriorityQueue<>((a, b) -> (int) (b.rate - a.rate));

    @Override
    public Answer getAnswer(final Input input) {
        // sorting by the rank
        List<Library> libraryList = new ArrayList<>(input.getLibraryList());
        Map<Long, Integer> bookIdScoreMap = new HashMap<>(input.getBookIdScoreMap());
        long deadline = input.getNumberOfDays();
        heap = getNewValuesForHeap(libraryList, bookIdScoreMap);

        // scan the books
        long day = 0;
        List<Answer.Library> res = new ArrayList<>();
        Set<Book> scanned = new HashSet<>();
        while (!heap.isEmpty() && day < deadline) {
            Library library = heap.remove().library;
            libraryList.remove(library);
            // make the day go to the next
            day += library.getNumberOfDaysTakenToSignUp();
            long remainedDays = deadline - day;
            long scannedCount = 0;
            // sort the books by the score point
            List<Book> books = sortByScore(library.getBooks(), bookIdScoreMap);
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
                bookIdScoreMap.remove(book.getId());
                scannedBookOfLibrary.add(book.getId());
                scannedCount++;
                if (scannedCount % library.getNumberOfBooksScannablePerDay() == 0) {
                    remainedDays--;
                }
            }
            res.add(new Answer.Library(library.getId(), scannedBookOfLibrary));
            heap = getNewValuesForHeap(libraryList, bookIdScoreMap);
        }
        return new Answer(res);
    }

    public List<Book> sortByScore(List<Book> books, Map<Long, Integer> bookIdScoreMap) {
        books.sort((book1, book2) -> (int)
                (bookIdScoreMap.getOrDefault(book2.getId(), 0) -
                        bookIdScoreMap.getOrDefault(book1.getId(), 0)));
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

    private PriorityQueue<Solution3.Pair> getNewValuesForHeap(final List<Library> libraryList,
                                                              final Map<Long, Integer> bookIdScoreMap) {
        PriorityQueue<Solution3.Pair> heap = new PriorityQueue<>((a, b) -> (int) (b.rate - a.rate));
        libraryList.forEach(library -> {
            long totalScoreOfBooks = library.getBooks().stream()
                    .map(book -> bookIdScoreMap.get(book.getId()))
                    .filter(Objects::nonNull)
                    .mapToLong(l -> (long) l)
                    .sum();
            double rank = (double) totalScoreOfBooks / library.getNumberOfDaysTakenToSignUp();
            heap.add(new Solution3.Pair(rank, library));
        });
        return heap;
    }
}
