public class Book {

    private final long id;
    private final long score;

    Book (long id, long score) {
        this.id = id;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public long getScore() {
        return score;
    }
}
