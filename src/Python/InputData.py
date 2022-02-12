from Library import Library
from Book import Book
import os


class InputData(object):
    def __init__(self):
        self._libraries = list()
        self._numBooks = 0
        self._numLibraries = 0
        self._taskDays = 0
        self._listBooks = list()

    def read(self, path):
        if not os.path.exists(path): return False
        self._libraries.clear()
        self._listBooks.clear()

        with open(path, 'r') as fd:
            self._numBooks, self._numLibraries, self._taskDays = map(int, fd.readline().split())

            scores = fd.readline().split()
            for i in range(self._numBooks):
                book = Book()
                book.bookId = i
                book.bookScore = int(scores[i])
                self._listBooks.append(book)

            for i in range(self._numLibraries):
                library = Library()
                library.libraryId = i
                library.numBooks, library.signUpDays, library.scanningDays = map(int, fd.readline().split())
                book_list = fd.readline().split()
                for book in book_list:
                    library.books.append(self._listBooks[int(book)])
                self._libraries.append(library)

        return True

    def numLibraries(self):
        return self._numLibraries

    def numBooks(self):
        return self._numBooks

    @property
    def libraries(self):
        return self._libraries

    @libraries.setter
    def libraries(self, lib):
        self._libraries = lib

    def taskDays(self):
        return self._taskDays