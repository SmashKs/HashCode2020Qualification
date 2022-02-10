import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sort each library by sign up process. Libraries with shorter sign up come first.
 * If two libraries take the same number of days to sign up, then the one with the higher score of books comes first.
 */
public class Solution1 {

    static Answer getAnswer(Input input) {
        List<Answer.Library> libraryList = input.getLibraryList().stream()
                .sorted(
                        Comparator.comparing(Library::getNumberOfDaysTakenToSignUp)
                                .thenComparing(library -> {
                                    return library.getBooks().stream()
                                            .mapToLong(Book::getScore)
                                            .sorted()
                                            .limit(input.getNumberOfDays())
                                            .reduce(Long::sum)
                                            .getAsLong();
                                })
                )
                .map(library -> new Answer.Library(
                        library.getId(),
                        library.getBooks().stream()
                                .sorted(Comparator.comparing(Book::getScore))
                                .limit(input.getNumberOfDays())
                                .mapToLong(Book::getId)
                                .boxed()
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return new Answer(libraryList);
    }
}
