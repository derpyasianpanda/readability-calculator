# Readibility Calculator

This program retrieves a desire file, parses the data, and calculates the readibility of the file using a multitude of readibility measures. To calculate readibility, the program keeps track of various things like Sentence, Word, and Syllable count.

This project was created with knowledge gained from Hyper Skill's Java track.

## Usage

1. Compile the Readability.java (using [Javac](https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javac.html))
2. Prepare some type of file (preferably .txt) to have the program parse and analyze
3. Run the program from the terminal (That is in the directory of the compiled bytecode) using the command `java Readability`
  - You can declare the file path in the command line arguments before running (ex. `java Readability tests\\tests.txt`)

## What I used/referenced

- [Java](https://www.java.com/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Hyper Skill](https://hyperskill.org/)
- Readability Calculations

  - [Automated Readibility Index](https://en.wikipedia.org/wiki/Automated_readability_index)
  - [Flesch–Kincaid Index](https://en.wikipedia.org/wiki/Flesch%E2%80%93Kincaid_readability_tests)
  - [Coleman–Liau Index](https://en.wikipedia.org/wiki/Coleman%E2%80%93Liau_index)
  - [Simple Measure of Gobbledygookx](https://en.wikipedia.org/wiki/SMOG)

## What I learned

- [RegEx](https://en.wikipedia.org/wiki/Regular_expression)
- Java Syntax
  
  - File IO
  - Design Patterns
  - Import/package structures
