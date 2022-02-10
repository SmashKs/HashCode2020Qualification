import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
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
    public void testWithJavaSolution(String fileName) {
        Input input = parseFile(fileName);
        Answer answer = Solution1.getAnswer(input);
        createOutputFile(answer, fileName.replace("input", "javaOutput"));

        Long score = getScore(input, answer);
        System.out.println("Score - " + score);
    }

    public static Stream<Arguments> pythonOutput_dataSource() {
        return Stream.of(
                /*Arguments.of("input/a.txt", "pythonOutput/a.txt"),
                Arguments.of("input/b.txt", "pythonOutput/b.txt"),
                Arguments.of("input/c.txt", "pythonOutput/c.txt"),
                Arguments.of("input/d.txt", "pythonOutput/d.txt"),
                Arguments.of("input/e.txt", "pythonOutput/e.txt"),*/
                Arguments.of("input/f.txt", "pythonOutput/f.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("pythonOutput_dataSource")
    public void testWithPythonSolution(String inputFileName, String outputFileName) {
        Input input = parseFile(inputFileName);
        Answer answer = parsePythonOutputFile(outputFileName);
        Long score = getScore(input, answer);
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
                long numDaysSignUp = Long.parseLong(lineOneArray[1]);
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

            OptionalLong sumScoreOptional = library.getBookIdsToScan().stream()
                    .map(bookIdScoreMap::remove)
                    .filter(Objects::nonNull)
                    .limit(numberOfScannableBooks)
                    .mapToLong(j -> (long) j)
                    .reduce(Long::sum);

            long sumScore = sumScoreOptional.isPresent() ? sumScoreOptional.getAsLong() : 0L;

            currentScore += sumScore;
        }

        return currentScore;
    }

    void createOutputFile(Answer answer, String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");

            writer.println(answer.getNumberOfLibrariesToSignUp());
            for (int i = 0; i < answer.getNumberOfLibrariesToSignUp(); i++) {
                Answer.Library library = answer.getLibraryList().get(i);
                writer.println(library.getId() + " " + library.getNumberOfBooksToScan());
                writer.println(library.getBookIdsToScan().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" "))
                );
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    Answer parsePythonOutputFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String firstLine = reader.readLine();
            long numLibraries = Long.parseLong(firstLine);

            List<Answer.Library> libraryList = new ArrayList<>();
            for (int i = 0; i < numLibraries; i++) {

                String lineOne = reader.readLine();
                String[] lineOneArray = lineOne.split(" ");
                long id = Long.parseLong(lineOneArray[0]);
                long numBooksToScan = Long.parseLong(lineOneArray[1]);

                String lineTwo = reader.readLine();
                String[] bookIds = lineTwo.split(" ");
                List<Long> bookIdsList = Arrays.stream(bookIds)
                        .mapToLong(Long::parseLong)
                        .boxed()
                        .collect(Collectors.toList());

                libraryList.add(new Answer.Library(id, bookIdsList));
            }

            reader.close();

            return new Answer(libraryList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
