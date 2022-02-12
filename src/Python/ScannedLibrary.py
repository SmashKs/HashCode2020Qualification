class ScannedLibrary:
    def __init__(self):
        self._libraryId = -1
        self._bookList = list()
        self._numBooks = 0

    @property
    def libraryId(self):
        return str(self._libraryId)

    @libraryId.setter
    def libraryId(self, _id):
        self._libraryId = _id

    def bookList(self):
        return self._bookList

    def numBooks(self):
        return str(len(self._bookList))

    def bookListToStr(self):
        return ' '.join(list(map(str, self._bookList)))