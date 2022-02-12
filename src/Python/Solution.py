from SolutionA import SolutionA
import os


def main():
    input_files = ['a.txt', 'b.txt', 'c.txt', 'd.txt', 'e.txt', 'f.txt']
    input_folder = '/../../input/'
    output_folder = '/../../pythonOutput/'
    solution = SolutionA()
    for input_file in input_files:
        solution.read(os.getcwd() + input_folder + input_file)
        solution.run()
        solution.output(os.getcwd() + output_folder + input_file)


if __name__ == '__main__':
    main()
