package movieReviews;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This program uses a collection of existing movie reviews from text files to
 * assign sentiment scores to additional reviews provided as input. This program
 * is an example of Machine Learning, a branch of computer science that studies
 * algorithms that build models from sample inputs and use those models to make
 * predictions or decisions.
 * 
 * @author Sanjeeb Sangraula
 *
 */

public class MovieReviews {

	// A map to store the keys of the words and the subsequent words
	private Map<String, Word> wordsList;

	/**
	 * A constructor that builds the word collection using the given
	 * number of lines from the input file
	 * 
	 * @param filename
	 *            the name of the file to input
	 * @param numberOfLines
	 *            the number of lines of the input file to read
	 */
	public MovieReviews(String filename, int numberOfLines) {

		this.wordsList = new HashMap<>();
		this.readFile(filename, numberOfLines);
	}

	/**
	 * A method to read the input file
	 * 
	 * @param fileName
	 *            the name of the text file to be read
	 * @param numberOfLines
	 *            the number of lines of the text file to read
	 * @return true if the file was opened successfully and false if the file
	 *         could not be opened
	 */
	private boolean readFile(String fileName, int numberOfLines) {

		Scanner sc = null;

		try {
			sc = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			return false;
		}

		int lineNumber = 0;

		while (sc.hasNextLine() && lineNumber < numberOfLines) {
			this.handleLineFromFile(sc.nextLine());
			lineNumber++;
		}
		return true;
	}

	/**
	 * A method to handle the input line from the file
	 * 
	 * @param line
	 *            a line from the file that is read by a scanner
	 */ 
	private void handleLineFromFile(String line) {

		// get the line score. The score is the first number in the line from
		// file
		int score = this.getScoreFromLine(line);

		// remove the score from the line
		line = this.removeScore(line);

		// get a list of valid words from the line
		ArrayList<String> validWords = this.getValidWords(line);

		// store the score of the valid words from the file
		this.handleValidWords(score, validWords);
	}

	/**
	 * A method to handle the valid words. It increases the word count and adds
	 * the score to the words.
	 * 
	 * @param score
	 *            the score of the words in the ArrayList
	 * @param validWords
	 *            an ArrayList of all the valid words from the line
	 */
	private void handleValidWords(int score, ArrayList<String> validWords) {

		for (int i = 0; i < validWords.size(); i++) {

			String word = validWords.get(i);

			if (!this.wordsList.containsKey(this.getKey(word))) {
				// save the word in the wordList
				this.wordsList.put(this.getKey(word), new Word());
			}

			Word currentWord = this.wordsList.get(this.getKey(word));

			// add the score to the word
			currentWord.addScore(score);
		}
	}

	/**
	 * A method to get the key from a word. In this case, the key is just the
	 * word itself
	 * 
	 * @param word
	 *            the word whose key is to be found
	 * @return the key associated with the word, which is the word itself in
	 *         this case
	 */
	private String getKey(String word) {
		return word;
	}

	/**
	 * A method to get a list of valid words
	 * 
	 * @param line
	 *            the line from which the list of valid words is to be found
	 * @return an ArrayList of Strings that contain valid words
	 */
	private ArrayList<String> getValidWords(String line) {

		String[] filteredArray = line.replaceAll("[^a-zA-Z 0-9]", "").toLowerCase().split("\\s+");

		return this.filterWordsArray(filteredArray);
	}

	/**
	 * A method to filter an array of words, removing unwanted words like
	 * Conjunctions.
	 * 
	 * @param array
	 *            the array of words which is to be filtered
	 * @return a new array of words that is filtered
	 */
	private ArrayList<String> filterWordsArray(String[] array) {

		ArrayList<String> words = new ArrayList<>();

		for (int i = 0; i < array.length; i++) {
			String word = array[i];
			if (word.equals("but") || word.equals("or") || word.equals("and") || word.equals("i") || word.equals("you")
					|| word.equals("it") || word.equals("he") || word.equals("she") || word.equals("we")
					|| word.equals("they") || word.equals("a") || word.equals("an") || word.equals("the")) {
				// don't add the words
			} else {
				// add the words
				if (word.length() > 2) {
					words.add(word);
				}
			}
		}
		return words;
	}

	/**
	 * A method to remove the score from a line. The score is the first
	 * character in a line
	 * 
	 * @param line
	 *            input string from which the score is to be removed
	 * @return result after removing the score from the line
	 */
	private String removeScore(String line) {
		return line.substring(1).trim();
	}

	/**
	 * A method to get the score from a line read from the input text file
	 * @param line the line from the input file that contains the score
	 * @return the score from the line
	 */
	private int getScoreFromLine(String line) {
		return Integer.parseInt(line.trim().substring(0, 1));
	}

	/**
	 * A method to return the score associated with a word in the collection. If
	 * the word is not in the collection, the neutral score (2.0) is returned.
	 * 
	 * @param word
	 *            the word whose score is to be returned
	 * @return the average score of the word
	 */
	public double wordScore(String word) {
		word = word.replaceAll("[^a-zA-Z 0-9]", "");
		word = word.toLowerCase();
		String key = this.getKey(word);
		return this.wordsList.containsKey(key) ? this.wordsList.get(key).getTotalScore() : 2.0;
	}

	/**
	 * A method to return the calculated score for a new review given as
	 * argument (the average score for the relevant valid words in the review).
	 * 
	 * @param review
	 *            the string whose components average is to be found
	 * 
	 * @return the average score of the words from the string review
	 */
	public double reviewScore(String review) {

		ArrayList<String> words = this.getValidWords(review);

		double score = 0;

		for (int i = 0; i < words.size(); i++) {
			score += this.wordScore(words.get(i));
		}
		return score / words.size();
	}

	/**
	 * A method to return the word with the highest score in the collection, provided the
	 * word occurs at least twice.
	 * 
	 * @return the word with the highest score
	 */
	public String mostPositive() {

		ArrayList<String> highestScoresList = new ArrayList<String>();

		ArrayList<String> frequentWordsList = new ArrayList<String>();

		double currentScore = 0.0;
		double highestScore = 0.0;
		String highestScoreKey = "";

		boolean first = true;

		for (String key : wordsList.keySet()) {

			if (wordsList.get(key).occursMoreThanTwice()) {

				frequentWordsList.add(key);

				currentScore = wordsList.get(key).getTotalScore();

				if (first) {
					highestScore = currentScore;
					highestScoreKey = key;
					first = false;
					continue;
				}

				if (currentScore > highestScore) {
					highestScore = currentScore;
					highestScoreKey = key;
				}
			}
		}

		for (String word : frequentWordsList) {
			if (this.wordsList.get(word).getTotalScore() == highestScore) {
				highestScoresList.add(word);
			}
		}

		if (highestScoresList.size() > 1) {
			int maxCount = 0;
			for (String word : highestScoresList) {
				if (this.wordsList.get(word).getCount() > maxCount) {
					maxCount = this.wordsList.get(word).getCount();
					highestScoreKey = word;
				}
			}
		} else {
			highestScoreKey = highestScoresList.get(0);
		}
		return highestScoreKey;
	}
	//
	// ArrayList<String> keys = this.getWordsOccuringTwice();
	//
	// Iterator<String> it = keys.iterator();
	// String key = "";
	//
	// while (it.hasNext()) {
	//
	// key = it.next();
	//
	// if (this.checkIfSmallest(key, keys)) {
	// return key;
	// }
	// }
	// return "";
	// }

	/**
	 * A method to return the word with the most negative or lowest score
	 * 
	 * @return the word that has the most negative score
	 */
	public String mostNegative() {

		ArrayList<String> lowestScoresList = new ArrayList<String>();

		ArrayList<String> frequentWordsList = new ArrayList<String>();

		double currentScore = 0.0;
		double lowestScore = 1000.0;
		String lowestScoreKey = "";

		boolean first = true;

		for (String key : wordsList.keySet()) {

			if (wordsList.get(key).occursMoreThanTwice()) {

				frequentWordsList.add(key);

				currentScore = wordsList.get(key).getTotalScore();

				if (first) {
					lowestScore = currentScore;
					lowestScoreKey = key;
					first = false;
					continue;
				}

				if (currentScore < lowestScore) {
					lowestScore = currentScore;
					lowestScoreKey = key;
				}
			}
		}

		for (String word : frequentWordsList) {
			if (this.wordsList.get(word).getTotalScore() == lowestScore) {
				lowestScoresList.add(word);
			}
		}

		if (lowestScoresList.size() > 1) {
			int maxCount = 0;
			for (String word : lowestScoresList) {
				if (this.wordsList.get(word).getCount() > maxCount) {
					maxCount = this.wordsList.get(word).getCount();
					lowestScoreKey = word;
				}
			}
		} else {
			lowestScoreKey = lowestScoresList.get(0);
		}
		return lowestScoreKey;
	}

	/**
	 * A class to store attributes related to a String word
	 * 
	 * @author Sanjeeb
	 *
	 */
	class Word {

		private double score;
		private int count;

		/**
		 * A no argument for the word
		 */
		public Word() {
			this.score = 0;
			this.count = 0;
		}

		/**
		 * Method to add score to this word
		 * 
		 * @param score
		 *            the score to be added
		 */
		public void addScore(double score) {
			this.count++;
			this.score += score;
		}

		/**
		 * Method to get the total score from the word
		 * 
		 * @return the total score of the word
		 */
		public double getTotalScore() {
			return score / count;
		}

		/**
		 * Method to return the number of times this word has occured in the
		 * program
		 * 
		 * @return count this word count in the file
		 */
		public int getCount() {
			return count;
		}

		/**
		 * Method to check if this word occurs more than twice
		 * 
		 * @return true if this word occurs more than twice, false otherwise
		 */
		public boolean occursMoreThanTwice() {
			return count >= 2;
		}

		/**
		 * Method to increase the count of this word
		 */
		public void increaseCount() {
			count++;
		}
	}
}