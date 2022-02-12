from InputData import InputData


class BaseSolution:
    def __init__(self):
        self._inputData = None
        self._outputData = None

    def run(self):
        pass

    def read(self, path):
        self._inputData = InputData()
        if not self._inputData.read(path):
            print('Error: could not read file ', path)

    def output(self, path):
        with open(path, 'w') as fd:
            fd.write(self._outputData.numLibrariesWillBeScanned)
            fd.write("\n")
            for library in self._outputData.libraries():
                fd.write(library.libraryId + ' ' + library.numBooks())
                fd.write("\n")
                fd.write(library.bookListToStr())
                fd.write("\n")