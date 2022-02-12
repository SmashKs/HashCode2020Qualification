class Library:
    def __init__(self):
        self._libraryId = -1
        self._signUpDays = 0
        self._scanningDays = 0
        self._numBooks = 0
        self._books = list()

    @property
    def libraryId(self):
        return self._libraryId

    @libraryId.setter
    def libraryId(self, _id):
        self._libraryId = _id

    @property
    def signUpDays(self):
        return self._signUpDays

    @signUpDays.setter
    def signUpDays(self, days):
        self._signUpDays = days

    @property
    def scanningDays(self):
        return self._scanningDays

    @scanningDays.setter
    def scanningDays(self, days):
        self._scanningDays = days

    @property
    def books(self):
        return self._books

    @books.setter
    def books(self, bookList):
        self._books = bookList

    @property
    def numBooks(self):
        return self._numBooks

    @numBooks.setter
    def numBooks(self, nBooks):
        self._numBooks = nBooks