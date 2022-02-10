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
                                    long sum = library.getBooks().stream()
                                            .mapToLong(book -> input.getBookIdScoreMap().get(book.getId()))
                                            .sorted()
                                            .limit(input.getNumberOfDays())
                                            .reduce(Long::sum)
                                            .getAsLong();

                                    return sum;
                                })
                )
                .map(library -> new Answer.Library(
                        library.getId(),
                        library.getBooks().stream()
                                .sorted(Comparator.comparing(book -> input.getBookIdScoreMap().get(book.getId())))
                                .limit(input.getNumberOfDays())
                                .mapToLong(Book::getId)
                                .boxed()
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return new Answer(libraryList);
    }
}
