import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 * The Readability program implements an application that
 * helps calculate a file's Readability Score
 *
 * @author  KV Le
 * @version 1.0
 * @since   6/13/2020
 */
public class Readability {
    /**
     * Gets file path and runs the Readability calculator
     * @param args - Contains a file name if the user wants to specify the file before entering the program
     */
    public static void main(String[] args) {
        try {
            String filePath;
            if (args.length > 0) {
                filePath = args[0];
            } else {
                System.out.print("Enter a File Path: ");
                filePath = new Scanner(System.in).next();
            }
            String text = readFileAsString(filePath).toLowerCase();
            runReadabilityCalculator(text);
        } catch (IOException exception) {
            System.out.println(exception.getMessage() + " was not found");
        } finally {
            System.out.println("\nThanks for using my Readability Calculator");
        }
    }

    /**
     * Runs Readability Calculator
     * @param text - The text of the desired file
     */
    public static void runReadabilityCalculator(String text) {
        try {
            // The lines below retrieve basic information about the text
            int sentenceCount = text.split("[.?!][\\s]+").length;
            int wordCount = text.split("[\\s]+").length;

            /*
            I calculated the amount of syllables using the following rules:
            1. Count the number of vowels in the word.
            2. Do not count double-vowels (for example, "rain" has 2 vowels but is only 1 syllable)
            3. If the last letter in the word is 'e' do not count it as a vowel (for example, "side" is 1 syllable)
            4. If at the end it turns out that the word contains 0 vowels, then consider this word as 1-syllable.
             */
            int charCount = text.replaceAll("[\\s]+", "").length();
            int syllableCount = 0;
            int polySyllableCount = 0;
            for (String word : text.split("[,:'\"]?[\\s][,:'\"]?")) {
                if (word.charAt(word.length() - 1) == 'e') {
                    word = word.substring(0, word.length() - 1);
                }
                int syllables = (int) Pattern.compile("[aeiouy]+").matcher(word).results().count();
                polySyllableCount += syllables >= 3 ? 1 : 0;
                syllableCount += syllables == 0 ? 1 : syllables;
            }

                /*
                The lines below retrieve readability score through the following methods:
                Automated Readability Index (ARI) -  https://en.wikipedia.org/wiki/Automated_readability_index
                Flesch–Kincaid Grade Level (FK) - https://en.wikipedia.org/wiki/Flesch%E2%80%93Kincaid_readability_tests
                Simple Measure of Gobbledygook (SMOG) - https://en.wikipedia.org/wiki/SMOG
                Coleman–Liau index (CL) - https://en.wikipedia.org/wiki/Coleman%E2%80%93Liau_index
                */
            double scoreARI = (4.71 * charCount / wordCount) +
                    (0.5 * wordCount / sentenceCount) - 21.43;
            double scoreFK = (0.39 * wordCount / sentenceCount) +
                    (11.8 * syllableCount / wordCount) - 15.59;
            double scoreSMOG = 1.043 * Math.sqrt(polySyllableCount * 30f / sentenceCount) + 3.1291;
            double scoreCL = 0.0588 * ((double) charCount / wordCount * 100) -
                    0.296 * ((double) sentenceCount / wordCount * 100) - 15.8;

            // The lines below retrieve the age based on the different readability scores
            int ageARI = getAge(scoreARI);
            int ageFK = getAge(scoreFK);
            int ageSMOG = getAge(scoreSMOG);
            int ageCL = getAge(scoreCL);

            // The lines below print out information about the calculations
            System.out.println("Words: " + wordCount);
            System.out.println("Sentences: " + sentenceCount);
            System.out.println("Characters: " + charCount);
            System.out.println("Syllables: " + syllableCount);
            System.out.println("Polysyllables: " + polySyllableCount);
            System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
            String desiredScore = new Scanner(System.in).next();
            System.out.println();

            switch (desiredScore.toLowerCase()) {
                case "all" -> {
                    System.out.printf("Automated Readability Index: %f (about %s year olds)",
                            scoreARI, ageARI);
                    System.out.printf("\nFlesch–Kincaid readability tests: %f (about %s year olds)",
                            scoreFK, ageFK);
                    System.out.printf("\nSimple Measure of Gobbledygook: %f (about %s year olds)",
                            scoreSMOG, ageSMOG);
                    System.out.printf("\nColeman–Liau index: %f (about %s year olds)",
                            scoreCL, ageCL);
                    double avg = (ageARI + ageARI + ageSMOG + ageCL) / 4f;
                    System.out.printf("\n\nThis text should be understood in average by %f year olds.", avg);
                }
                case "ari" -> System.out.printf("Automated Readability Index: %f (about %s year olds)",
                        scoreARI, ageARI);
                case "fk" -> System.out.printf("Flesch–Kincaid readability tests: %f (about %s year olds)",
                        scoreFK, ageFK);
                case "smog" -> System.out.printf("Simple Measure of Gobbledygook: %f (about %s year olds)",
                        scoreSMOG, ageSMOG);
                case "cl" -> System.out.printf("Coleman–Liau index: %f (about %s year olds)",
                        scoreCL, ageCL);
                default -> System.out.println("I can't calculate that :(");
            }
        } catch (ArithmeticException exception) {
            System.out.println("Seems like the file was empty or there was something wrong with the arithmetic");
        }
    }

    /**
     * Retrieves all text within a given file
     * @param filePath - The path to the file
     * @return - All the text within the given file
     * @throws IOException if the file doesn't exist
     */
    public static String readFileAsString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    /**
     * Returns the age given a grade level (Readability score in this case)
     * @param grade - Grade level
     * @return - The age estimated age of the given grade
     */
    public static int getAge(double grade) {
        int score = (int) Math.round(grade);
        if (score < 3) {
            return (score + 5);
        } else if (score < 13) {
            return score + 6;
        } else if (score < 14) {
            return score + 11;
        } else {
            return 30;
        }
    }
}
