import java.util.List;
import java.util.Map;

public class Input {

    private final long numberOfUniqueBooks; // B, 1 ~ 10^5
    private final long numberOfLibraries; // L, 1 ~ 10^5
    private final long numberOfDays; // D, 1 ~ 10^5
    private final Map<Long, Integer> bookIdScoreMap; // Maps book id -> score, where score S is 0 ~ 10^3
    private final List<Library> libraryList;

    public Input(long numberOfUniqueBooks, long numberOfDays, Map<Long, Integer> bookIdScoreMap, List<Library> libraryList) {
        this.numberOfUniqueBooks = numberOfUniqueBooks;
        this.numberOfLibraries = libraryList.size();
        this.numberOfDays = numberOfDays;
        this.bookIdScoreMap = bookIdScoreMap;
        this.libraryList = libraryList;
    }

    public long getNumberOfUniqueBooks() {
        return numberOfUniqueBooks;
    }

    public long getNumberOfLibraries() {
        return numberOfLibraries;
    }

    public long getNumberOfDays() {
        return numberOfDays;
    }

    public Map<Long, Integer> getBookIdScoreMap() {
        return bookIdScoreMap;
    }

    public List<Library> getLibraryList() {
        return libraryList;
    }
}
