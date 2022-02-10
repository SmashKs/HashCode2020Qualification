import java.util.List;

public class Answer {

    private final long numberOfLibrariesToSignUp;

    private final List<Library> libraryList;

    public Answer(List<Library> libraryList) {
        this.numberOfLibrariesToSignUp = libraryList.size();
        this.libraryList = libraryList;
    }

    public long getNumberOfLibrariesToSignUp() {
        return numberOfLibrariesToSignUp;
    }

    public List<Library> getLibraryList() {
        return libraryList;
    }

    static class Library {
        private final long id;
        private final long numberOfBooksToScan;
        private final List<Long> bookIdsToScan;

        public Library(long id, List<Long> bookIdsToScan) {
            this.id = id;
            this.numberOfBooksToScan = bookIdsToScan.size();
            this.bookIdsToScan = bookIdsToScan;
        }

        public long getId() {
            return id;
        }

        public long getNumberOfBooksToScan() {
            return numberOfBooksToScan;
        }

        public List<Long> getBookIdsToScan() {
            return bookIdsToScan;
        }
    }
}
