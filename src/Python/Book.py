class Book:
    def __init__(self):
        self._bookId = -1
        self._bookScore = -1

    @property
    def bookId(self):
        return self._bookId

    @bookId.setter
    def bookId(self, nId):
        self._bookId = nId

    @property
    def bookScore(self):
        return self._bookScore

    @bookScore.setter
    def bookScore(self, score):
        self._bookScore = score