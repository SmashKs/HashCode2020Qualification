class OutputData:
    def __init__(self):
        self._numLibrariesWillBeScanned = 0
        self._libraries = list()

    @property
    def numLibrariesWillBeScanned(self):
        return str(len(self._libraries))

    @numLibrariesWillBeScanned.setter
    def numLibrariesWillBeScanned(self, num):
        self._numLibrariesWillBeScanned = num

    def libraries(self):
        return self._libraries