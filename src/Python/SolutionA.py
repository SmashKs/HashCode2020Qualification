from BaseSolution import BaseSolution
from LibraryA import LibraryA
from OutputData import OutputData
from ScannedLibrary import ScannedLibrary


class SolutionA(BaseSolution):
    def __init__(self):
        super().__init__()
        print('Solution A')

    def run(self):
        if not self._inputData:
            print('Error: input data is empty')
            return

        libraries = list()
        for library in self._inputData.libraries:
            lib = LibraryA(library)
            lib.books = sorted(lib.books, key=lambda book: book.bookScore, reverse=True)
            libraries.append(lib)
        self._inputData.libraries = sorted(libraries, key=lambda lib: lib.ratio(), reverse=True)

        self._outputData = OutputData()
        scanned_books = set()
        start_day = 0
        for library in self._inputData.libraries:
            start_day += library.signUpDays
            if start_day >= self._inputData.taskDays(): break
            rest_day = self._inputData.taskDays() - start_day
            scanned_library = ScannedLibrary()
            scanned_library.libraryId = library.libraryId
            while rest_day > 0:
                for book in library.books:
                    if book in scanned_books: continue
                    scanned_books.add(book)
                    scanned_library.bookList().append(book.bookId)
                rest_day -= 1

            if scanned_library.numBooks() > 0:
                self._outputData.libraries().append(scanned_library)