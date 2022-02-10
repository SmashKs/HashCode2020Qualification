import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution1 {

    static Answer getAnswer(Input input) {

        Library library1 = input.getLibraryList().get(0);
        List<Answer.Library> libraryList = Stream.of(library1)
                .map(library ->
                        new Answer.Library(
                                library.getId(),
                                library1.getBooks().stream()
                                        .mapToLong(Book::getId)
                                        .boxed()
                                        .collect(Collectors.toList())
                        )
                ).collect(Collectors.toList());

        return new Answer(libraryList);
    }
}
