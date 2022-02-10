import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Stream;

public class SolutionTest {

    public static Stream<Arguments> dataSource() {
        return Stream.of(
                Arguments.of("input/a.txt"),
                Arguments.of("input/b.txt"),
                Arguments.of("input/c.txt"),
                Arguments.of("input/d.txt"),
                Arguments.of("input/e.txt"),
                Arguments.of("input/f.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("dataSource")
    public void test(String fileName) {
        Input input = parseFile(fileName);
        Answer answer = Solution1.getAnswer(input);
        Long score = getScore(input, answer);
        createOutputFile(fileName.replace("input", "output"));
        System.out.println("Score - " + score);
    }

    Input parseFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String firstLine = reader.readLine();
            String[] firstLineArray = firstLine.split(" ");
            long numUniqueBooks = Long.parseLong(firstLineArray[0]);
            long numLibraries = Long.parseLong(firstLineArray[1]);
            long numDays = Long.parseLong(firstLineArray[2]);

            String bookScoreLine = reader.readLine();
            String[] bookScores = bookScoreLine.split(" ");
            Map<Long, Integer> bookIdScoreMap = new HashMap<>();
            for (int i = 0; i < numUniqueBooks; i++) {
                int score = Integer.parseInt(bookScores[i]);
                bookIdScoreMap.put((long) i, score);
            }

            List<Library> libraryList = new ArrayList<>();
            for (int i = 0; i < numLibraries; i++) {

                String lineOne = reader.readLine();
                String[] lineOneArray = lineOne.split(" ");
                long numBooksInLibrary = Long.parseLong(lineOneArray[0]);
                long numDaysSignUp = Long.parseLong(lineOneArray[2]);
                long numBooksScannablePerDay = Long.parseLong(lineOneArray[2]);

                String lineTwo = reader.readLine();
                String[] bookIds = lineTwo.split(" ");
                List<Book> books = new ArrayList<>();
                for (int j = 0; j < bookIds.length; j++) {
                    long bookId = Long.parseLong(bookIds[j]);
                    books.add(new Book(bookId, bookIdScoreMap.get(bookId)));
                }

                libraryList.add(new Library(
                        i,
                        numBooksInLibrary,
                        numDaysSignUp,
                        numBooksScannablePerDay,
                        books
                ));
            }

            reader.close();

            return new Input(
                    numUniqueBooks,
                    numDays,
                    bookIdScoreMap,
                    libraryList
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    Long getScore(Input input, Answer answer) {
        Map<Long, Integer> bookIdScoreMap = input.getBookIdScoreMap();
        List<Answer.Library> libraryList = answer.getLibraryList();

        Long currentScore = 0L;
        long currentDay = 0L;

        for (int i = 0; i < answer.getNumberOfLibrariesToSignUp(); i++) {
            Answer.Library library = libraryList.get(i);

            Library equivalentLibrary = input.getLibraryList().stream()
                    .filter(lib -> lib.getId() == library.getId())
                    .findFirst()
                    .get();

            currentDay += equivalentLibrary.getNumberOfDaysTakenToSignUp();

            if (currentDay >= input.getNumberOfDays()) {
                return currentScore;
            }

            long remainingDays = input.getNumberOfDays() - currentDay;
            long numberOfScannableBooks = remainingDays * equivalentLibrary.getNumberOfBooksScannablePerDay();

            long sumScore = library.getBookIdsToScan().stream()
                    .map(bookIdScoreMap::remove)
                    .filter(Objects::nonNull)
                    .limit(numberOfScannableBooks)
                    .mapToLong(j -> (long) j)
                    .reduce(Long::sum)
                    .getAsLong();

            currentScore += sumScore;
        }

        return currentScore;
    }

    void createOutputFile(String filename) {
        // ToDo
    }
}
