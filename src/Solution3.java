import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Solution3 implements Solution {

    PriorityQueue<Solution3.Pair> heap = new PriorityQueue<>((a, b) -> (int) (b.rate - a.rate));

    @Override
    public Answer getAnswer(final Input input) {
        // sorting by the rank
        List<Library> libraryList = new ArrayList<>(input.getLibraryList());
        Map<Long, Integer> bookIdScoreMap = new HashMap<>(input.getBookIdScoreMap());
        heap = getNewValuesForHeap(libraryList, bookIdScoreMap);

        // scan the books
        long deadline = input.getNumberOfDays(), day = 0;
        List<Answer.Library> res = new ArrayList<>();
        Set<Book> scanned = new HashSet<>();
        while (!heap.isEmpty() && day < deadline) {
            Library library = heap.remove().library;
            libraryList.remove(library);
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

    private PriorityQueue<Solution3.Pair> getNewValuesForHeap(final List<Library> libraryList, Map<Long, Integer> bookIdScoreMap) {
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
