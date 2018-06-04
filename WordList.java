package cs2073;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordList {

	// Stores the list of words
	private ArrayList<String> wordsList = new ArrayList<>();

	/**
	 * No-Argument constructor for the WordList class
	 */
	public WordList() {
	}

	/**
	 * Argument constructor for the WordList class which takes the String[] of
	 * words as the argument
	 * 
	 * @param array
	 *            the String[] whose elements are to be added to the wordsList
	 *            ArrayList
	 */
	public WordList(String[] array) {
		saveArrayElements(array);
	}

	/**
	 * Helper method to save a list of String[] elements into the list of words
	 * in this class
	 * 
	 * @param array
	 *            The String[] from which the words are to be read.
	 */
	private void saveArrayElements(String[] array) {
		for (String item : array) {
			wordsList.add(item);
		}
	}

	/**
	 * Method to add all the words from the file into the arraylist of words
	 * 
	 * @param filename
	 *            The name of the file from which the words are to be read
	 * @return true if file is opened successfully and false if file isn't
	 *         opened successfully
	 */
	public boolean readWords(String filename) {

		Scanner fileScanner = null;

		try {

			fileScanner = new Scanner(new File(filename));

		} catch (FileNotFoundException e) {

			return false;
		}

		while (fileScanner.hasNext()) {
			wordsList.add(fileScanner.next());
		}

		return true;
	}

	/**
	 * Method to return the number of words in the ArrayList of words in the
	 * WordsList
	 * 
	 * @return the number of words stored in this WordsList class
	 */
	public int count() {
		return wordsList.size();
	}

	/**
	 * Method that returns the number of times the target string is stored in
	 * the ArrayList of words in this WordsList class
	 * 
	 * @param target
	 *            The String whose number is to be counted and returned
	 * @return the number of times target is stored in the WordsList
	 */
	public int count(String target) {
		int targetCount = 0;

		for (String item : wordsList) {
			if (checkIfEqual(target, item)) {
				targetCount++;
			}
		}
		return targetCount;
	}

	/**
	 * Helper method to compare two words first and second. While comparing the
	 * words, the case and punctuation symbols are ignored.
	 * 
	 * @return true if the words match, false if they don't match
	 */
	private boolean checkIfEqual(String first, String second) {

		if (first.equals(second)) {
			return true;
		}

		// remove punctuation from the two strings
		first = removePunctuation(first);
		second = removePunctuation(second);

		// bring both of the Strings to upper case so as to compare without any
		// regard to the case
		first = first.toUpperCase();
		second = second.toUpperCase();

		if (first.equals(second)) {
			return true;
		}

		return false;

	}

	/**
	 * Method to remove the punctuation from the String word. It removes the
	 * punctuation from only the last part of the word
	 * 
	 * @return the new String after removing punctuation from word
	 */
	private String removePunctuation(String word) {

		word = word.trim();
		
		// the punctuation will be at the last index
		char last = word.charAt(word.length() - 1);

		if (!Character.isAlphabetic(last)) {

			return word.substring(0, word.length() - 1);

		}

		return word;

	}

	/**
	 * Method to replace all the occurrences of the oldString word with the
	 * newString word. It returns the number of replacements made as a result.
	 * 
	 * @param oldString
	 *            The word to replace from the list
	 * @param newString
	 *            The word to replace the oldString word from the list with
	 * @param ignoreCase
	 *            Whether the case of the oldString should be considered while
	 *            replacing the oldString word
	 * @return The number of times the oldString word was replaced by the
	 *         newString word
	 */
	public int replace(String oldString, String newString, boolean ignoreCase) {

		int totalReplaced = 0;

		for (int i = 0; i < wordsList.size(); i++) {

			if (wordsList.get(i).equals(oldString)) {

				wordsList.set(i, newString);
				totalReplaced++;
				continue;

			}

			if (ignoreCase) {

				char char0 = oldString.charAt(0);
				String newChar0 = null;

				if (Character.isUpperCase(char0)) {

					newChar0 = ("" + char0).toLowerCase();

				} else {

					newChar0 = ("" + char0).toUpperCase();

				}

				String ignoredCaseOldString = newChar0 + oldString.substring(1);

				if (wordsList.get(i).equals(ignoredCaseOldString)) {

					wordsList.set(i, newString);
					totalReplaced++;

				}
			}
		}

		return totalReplaced;
	}

	/**
	 * This method displays all the words in the list in a line.
	 * 
	 * @param wordsPerLine
	 *            the number of words to display in a single line
	 * @return the total number of lines displayed
	 */
	public int display(int wordsPerLine) {

		int count = 0;
		int lines = 1;
		boolean first = true;

		for (String word : wordsList) {

			if (!first && (count % wordsPerLine) == 0) {

				System.out.println();
				lines++;

			}

			first = false;
			System.out.print(word + " ");
			count++;
		}

		return lines;

	}

	/**
	 * Method to return a String containing the contents of the list, surrounded
	 * by [ ] brackets. Individual words should be separated by single spaces.
	 * 
	 * @return The string with the content of the list
	 */
	public String toString() {

		String returnString = "[";

		for (int i = 0; i < wordsList.size(); i++) {

			returnString += wordsList.get(i);

			if (i != (wordsList.size() - 1)) {

				returnString += " ";

			}

		}

		return returnString + "]";

	}
}
