from Library import Library


class LibraryA(Library):
    def __init__(self, library):
        super().__init__()
        self._ratio = -1
        self._libraryId = library.libraryId
        self._signUpDays = library.signUpDays
        self._scanningDays = library.scanningDays
        self._numBooks = library.numBooks
        self._books = library.books
        self.ratio()

    def ratio(self):
        if self._ratio > -1:
            return self._ratio
        _sum = 0
        for book in self._books:
            _sum += book.bookScore
        self._ratio = _sum / self.signUpDays
        return self._ratio