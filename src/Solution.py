# 1. have the ratio for each library
#     score (before the deadline) / sign up days
# 2. signup the library with highest ratio
# 3. sort our books for each library with the score
# when you scan the book, remove it from the map
# 4. have a map for all books, map book id -> score
# 5. if a book is not in the map, just ignore
# [id1 -> 5], [id2 -> 7]
#
# map.remove(id1) -> return 5
# map.remove(id1) -> return null
# [[2, 1,  0, 1, 3], [1, 2, 2, 3]]
# scores {0: x, 1: x}

def count_ratio(books, scores, days):
    _sum = 0
    for book in books:
        _sum += scores[book]
    return _sum / days


def scan_library(libraries, scores):
    _map = dict()
    ratios = list()
    for i in range(len(libraries)):
        _scores = dict()
        for idx in libraries[i][2:]:
            _scores[scores[idx]] = idx
        s = sorted(_scores.keys(), reverse=True)
        j = 2
        for idx in s:
            libraries[i][j] = _scores[idx]
            j += 1

        ratio = count_ratio(libraries[i][2:], scores, libraries[i][0])
        ratios.append(ratio)
        _map[ratio] = i
    ratios.sort()
    return ratios, _map

# ratio : library (signup days, )
def signup_library(ratios: list, ratio_to_lib: dict, libraries: list, scores: list, task_days: int):
    start_day = 0
    scanned_book = set()
    for ratio in ratios:
        # put all books into the `global map` before the deadline
        # start_day += singup_process_daysstart
        library = libraries[ratio_to_lib[ratio]]
        start_day += library[0] # sign-up day
        rest_days = task_days - start_day # many days library can use

        idx = 2 # 0: sign-up, 1: how many boos can be sccanned for each day
        books = []
        for _ in range(rest_days):
            for _ in range(library[1]):
                while idx < len(library) and library[idx] in scanned_book:
                    idx += 1
                if idx >= len(library): break
                scanned_book.add(library[idx])
                books.append(library[idx])
        print(str(ratio_to_lib[ratio]), str(len(books)))
        print(books)


def main():
    libraries = [[2, 2, 0, 1, 2, 3, 4], [3, 1, 3, 2, 5, 0]]
    scores = [1, 2, 3, 6, 5, 4]
    scanning_day = 7
    ratios, ratio_to_lib = scan_library(libraries, scores)
    signup_library(ratios, ratio_to_lib, libraries, scores, scanning_day)


if __name__ == '__main__':
    main()
