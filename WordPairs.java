import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A class to perform breadth-first search algorithm studied in class to perform
 * various operations on a graph of words. This class uses the DiGraph.java
 * class provided by the instructor
 * 
 * @author Sanjeeb Sangraula
 *
 */
public class WordPairs {

	// An instance variable to hold the DiGraph
	private DiGraph graph;
	// An instance variable to hold the WordChainFinder
	private WordChainFinder wordChainFinder;
	// An instance variable to hold the ReachableWordsFinder
	private ReachableWordsFinder reachableWordsFinder;

	/**
	 * A constructor that reads in the data from a text file which contains a
	 * series of lines. Each line has two words separated by a single space.
	 * 
	 * @param filename
	 *            the name of the file from which input is to be read
	 */
	public WordPairs(String filename) {
		graph = new DiGraph();
		wordChainFinder = new WordChainFinder(this.graph);
		reachableWordsFinder = new ReachableWordsFinder(this.graph);
		this.readFile(filename);
	}

	/**
	 * A method to read the input words and their pairs from a file
	 * 
	 * @param filename
	 *            the name of the file from which the input is to be read
	 */
	public void readFile(String filename) {

		File file = new File(filename);

		Scanner sc = null;

		try {

			sc = new Scanner(file);

			while (sc.hasNextLine()) {

				this.handleLineFromFile(sc.nextLine());
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	/**
	 * A method to handle the input from the input text file. It takes a line
	 * from the text file and adds that edge to the graph.
	 * 
	 * @param line
	 *            a line from the input file that contains two vertices and an
	 *            edge between the vertices.
	 */
	public void handleLineFromFile(String line) {

		Scanner sc = new Scanner(line);

		String first = null, second = null;

		if (sc.hasNext()) {
			first = sc.next().trim();
		}
		if (sc.hasNext()) {
			second = sc.next().trim();
		}

		if (!this.graph.validVertex(first)) {
			this.graph.addVertex(first);
		}

		if (!this.graph.validVertex(second)) {
			this.graph.addVertex(second);
		}
		this.graph.addEdge(first, second);
	}

	/**
	 * A method to return the shortest sequence of word pairs that begins with
	 * first and ends with last, using the format below [first word1, word1
	 * word2, word2 ... , wordn last]
	 * 
	 * @param first
	 *            the word or the vertex from which the chain begins
	 * @param last
	 *            the word or vertex where the chain ends
	 * @return a string containing the shortest sequence of word pairs that
	 *         begins with first and ends with last
	 */
	public String wordChain(String first, String last) {

		return this.wordChainFinder.wordChain(first, last);
	}

	/**
	 * A method to return the number of word pairs in the shortest chain that
	 * begins with first and ends with last. Return Integer.MAX_VALUE if none
	 * exists.
	 * 
	 * @param first
	 *            The word from which the shortest chain starts
	 * @param last
	 *            The word where the shortest chain is to end
	 * @return a integer representing the number of word pairs that begin with
	 *         first and ends with last
	 */
	public int chainLength(String first, String last) {

		return this.wordChainFinder.chainLength(first, last);
	}

	/**
	 * A method to return the number of distinct words that are part of all
	 * chains of maxLength that begin with word.
	 * 
	 * @param word
	 *            The word or vertex from which the chains start.
	 * @param maxLength
	 *            The maximum length of the chains that begin with word.
	 * @return The total number of distinct words that are part of all chains of
	 *         maxlength.
	 */
	public int reachableFrom(String word, int maxLength) {

		return this.reachableWordsFinder.reachableFrom(word, maxLength);
	}

	/**
	 * A method to return the number of distinct words that are part of all
	 * chains that begin with word. Assume that a word is always reachable from
	 * itself
	 * 
	 * @param word
	 *            The word or vertex from which the chains start
	 * @return The number of distinct words that are part of all chains that
	 *         begin with word
	 */
	public int reachableFrom(String word) {

		return this.reachableWordsFinder.reachableFrom(word);
	}

	/**
	 * A method to return a String containing the distinct words that are part
	 * all chains of maxLength that begin with word. The reachable words should
	 * be grouped by level, using the format [word] [word1, word2] [word3,
	 * word4, word5] ... ... [........., wordn]
	 * 
	 * @param word
	 *            The string from which the word chains begin
	 * @param maxLength
	 *            The maximum length of the chains of words that begin with word
	 * @return a string containing the distinct words that are parts of all
	 *         chains of maxLength that begin with word.
	 */
	public String reachableWords(String word, int maxLength) {

		return this.reachableWordsFinder.reachableWords(word, maxLength);
	}

	/**
	 * A method to returns the shortest sequence of word pairs that begin and
	 * ends with word, using the format below (returns [] if there is no such
	 * sequence).
	 * 
	 * @param word
	 *            The word or vertex from where the cyckle starts
	 * @return A string containing the sequence of words that begin and end with
	 *         word
	 */
	public String cycle(String word) {

		return this.wordChainFinder.wordChain(word, word);
	}

	/**
	 * A class to help in finding the reachableWords in this program.
	 * 
	 * @author Sanjeeb Sangraula
	 *
	 */
	class ReachableWordsFinder {

		// An instance variable to hold the graph
		private DiGraph graph;

		/**
		 * An argument constructor to create a new instance of
		 * ReachableWordsFinder class
		 * 
		 * @param graph
		 *            A DiGraph that holds the value
		 */
		public ReachableWordsFinder(DiGraph graph) {

			this.graph = graph;
		}

		/**
		 * A method to return the number of reachable words that begin from word
		 * 
		 * @param word
		 *            The word from which the cycle starts
		 * @return The number of words that can be reached from the first
		 *         word @param word
		 */
		public int reachableFrom(String word) {

			return this.getAllReachableWords(word, false, 0).reachedString().size();
		}

		/**
		 * A method to find the number of words reachable from word and are of
		 * maximum length maxLength
		 * 
		 * @param word
		 *            The word from which the reachable number of words are
		 *            calculated
		 * @param maxLength
		 *            The maximum length of the number of words that starts from
		 *            word
		 * @return The number of words that are reachable from String word and
		 *         up to maximum length maxLength
		 */
		public int reachableFrom(String word, int maxLength) {

			return this.getAllReachableWords(word, true, maxLength).reachedString().size();
		}

		/**
		 * A method to get a CustomQueue containing all the reachable Nodes from
		 * the word word
		 * 
		 * @param word
		 *            The String from which to find the reachable number of
		 *            words
		 * @param checkLength
		 *            True if to check the length of the reachable words, false
		 *            otherwise
		 * @param maxLength
		 *            The maximum length of the reachable words that start from
		 *            word
		 * @return A CustomQueue that contains all the reachable Strings from
		 *         word and are of maximum length maxxLength
		 */
		private CustomQueue getAllReachableWords(String word, boolean checkLength, int maxLength) {

			Map<String, Boolean> childVisited = new HashMap<>();

			CustomQueue queue = new CustomQueue(maxLength);

			Node firstNode = new Node(word, null, this.graph);

			queue.add(firstNode);

			Node node = null;
			while (queue.canContinue()) {

				node = queue.remove();
				if (this.isNewNode(childVisited, node)) {
					ArrayList<Node> childNodes = node.getChildren();
					childVisited.put(node.value, true);
					for (int i = 0; i < childNodes.size(); i++) {
						Node n = childNodes.get(i);
						queue.add(n);
					}
				}
			}
			return queue;
		}

		/**
		 * A method to return a String containing the reachable words that start
		 * with word and are of maximum length maxLength
		 * 
		 * @param word
		 *            The String from which the reachable words begin
		 * @param maxLength
		 *            The maximum length of the word that start with word
		 * @return A String containing the reachable words in a format [word1]
		 *         [word2, word3] .. .. [wordn]
		 */
		public String reachableWords(String word, int maxLength) {

			return this.getReachableWordsString(word, maxLength);
		}

		/**
		 * A method to get the reachable words that begin from String word and
		 * are of maximum length maxLength
		 * 
		 * @param word
		 *            The String from which the reachable words start
		 * @param maxLength
		 *            The maximum length of the reachable words
		 * @return The String containing the reachable words in a format
		 */
		private String getReachableWordsString(String word, int maxLength) {

			CustomQueue cq = this.getAllReachableWords(word, true, maxLength);
			Set<Node> queue = cq.reached();

			Map<String, Integer> map = this.getValueToLevelMap(queue);

			String s = "";

			boolean first = true;

			for (int i = 0; i <= maxLength; i++) {
				if (first) {
					first = false;
				} else {
					s += "\n";
				}
				s += "[";

				Iterator<String> iter = map.keySet().iterator();

				boolean first1 = true;
				while (iter.hasNext()) {
					String k = iter.next();
					if (map.get(k) == i) {
						if (first1) {
							first1 = false;
						} else {
							s += ", ";
						}
						s += k;
					}
				}
				s += "]";
			}
			return s + "\n";
		}

		/**
		 * A method to get a map that contains a map with keys of values and
		 * value of its level
		 * 
		 * @param set
		 *            A set containing all the nodes
		 * @return A map containing all the value to level mapping
		 */
		private Map<String, Integer> getValueToLevelMap(Set<Node> set) {

			Map<String, Integer> map = new TreeMap<>();
			Iterator<Node> it = set.iterator();
			while (it.hasNext()) {
				Node n = it.next();
				if (!map.containsKey(n.value)) {
					map.put(n.value, n.getHistory().size());
				}
			}
			return map;
		}

		/**
		 * A method to return True if the Node n is not in the Map visited,
		 * false if it does
		 * 
		 * @param visited
		 *            A map associating value with True if it is visited, False
		 *            otherwise
		 * @param n
		 *            A node which is to be determined if it is already visited
		 * @return True if the node is already visited, False otherwise
		 */
		private boolean isNewNode(Map<String, Boolean> visited, Node n) {
			return (visited.get(n.value) == null || visited.get(n.value) == false);
		}
	}

	/**
	 * A class to represent a Custom Queue. This is very similar to and uses a
	 * queue. It has methods that are very similar to a queue.
	 * 
	 * @author Sanjeeb Sangraula
	 *
	 */
	class CustomQueue {

		// An instance variable to keep track of current level
		private int level = 0;
		// An instance variable to keep track of whether maximum level has been
		// reached
		private boolean maxLevelReached = false;
		// An instance variable to keep track of maximum level
		private int maxLevel = 0;
		// An instance variable to keep track of reached nodes
		private Set<Node> reached;
		// An instance variable to keep track of a list of nodes using a queue
		private LinkedList<Node> queue;
		// An instance variable to keep track of whether to limit by level
		private boolean limitByLevel = false;

		/**
		 * An argument constructor to create a new instance of CustomQueue with
		 * a maximum level of maxLevel
		 * 
		 * @param maxLevel
		 *            The maximum level of the CustomQueue
		 */
		public CustomQueue(int maxLevel) {
			if (maxLevel == 0) {
				this.limitByLevel = false;
			} else {
				this.limitByLevel = true;
				this.maxLevel = maxLevel + 1;
			}
			this.queue = new LinkedList<>();
			this.reached = new HashSet<>();
		}

		/**
		 * A method to return a Set of Strings containing all the Strings that
		 * have been reached so far
		 * 
		 * @return A set containing all the reached Strings
		 */
		public Set<String> reachedStrings() {
			Set<String> set = new HashSet<String>();
			Iterator<Node> iter = this.reached.iterator();

			while (iter.hasNext()) {
				Node n = iter.next();
				set.add(n.value);
			}
			return set;
		}

		/**
		 * A method to get a Set of nodes that are reached
		 * 
		 * @return The Set of nodes that are reached
		 */
		public Set<Node> reached() {
			return this.reached;
		}

		/**
		 * A method to return a Set of Strings that are reached
		 * 
		 * @return A Set containing all the reached Strings
		 */
		public Set<String> reachedString() {
			Iterator<Node> iter = this.reached.iterator();
			Set<String> set = new TreeSet<String>();

			while (iter.hasNext()) {
				Node n = iter.next();
				set.add(n.value);
			}
			return set;
		}

		/**
		 * A method to determine if we can continue adding more to the queue
		 * 
		 * @return True if we can continue adding more to the queue, False
		 *         otherwise
		 */
		public boolean canContinue() {
			if (this.queue.isEmpty()) {
				return false;
			} else {
				if (this.limitByLevel) {
					return !this.maxLevelReached;
				} else {
					return true;
				}
			}
		}

		/**
		 * A method to add a new node to an instance of CustomQueue
		 * 
		 * @param n
		 *            A node to add to this CustomQueue
		 */
		public void add(Node n) {
			if (n.getLevel() > this.level) {
				this.level = n.getLevel();
			}

			this.checkLevel();
			if (this.limitByLevel) {
				if (this.maxLevelReached) {
					return;
				}
			}
			if (!this.reached.contains(n)) {
				this.reached.add(n);
				this.queue.add(n);
			}
		}

		/**
		 * A method to check the level of this CustomQueue.
		 */
		private void checkLevel() {
			if (this.level >= this.maxLevel) {
				this.maxLevelReached = true;
			}
		}

		/**
		 * A method to remove a Node from the queue and return the Node
		 * 
		 * @return The Node that was removed
		 */
		public Node remove() {
			return this.queue.remove();
		}
	}

	/**
	 * A class that helps in finding the word chains from the DiGraph.
	 * 
	 * @author Sanjeeb Sangraula
	 *
	 */
	class WordChainFinder {

		// An instance variable to hold the graph
		private DiGraph graph;

		/**
		 * An argument constructor to create a new instance of the
		 * WordChainFinder class
		 * 
		 * @param graph
		 *            A DiGraph contining the words
		 */
		public WordChainFinder(DiGraph graph) {
			this.graph = graph;
		}

		/**
		 * A method to find a String of Words that start with first and end with
		 * last
		 * 
		 * @param first
		 *            The String from which the word chain starts
		 * @param last
		 *            The String at where the word chain ends
		 * @return A String containing the Word chains that start with first and
		 *         end with last
		 */
		public String wordChain(String first, String last) {
			Node n = this.findShortestSequence(first, last);
			return this.getWordChainOutput(n);
		}

		/**
		 * A method to return the number of integers that are in the chain that
		 * start with first and end with last
		 * 
		 * @param first
		 *            The String from where the chain starts
		 * @param last
		 *            The String from where the chain ends
		 * @return The number of integers in the chain that starts with first
		 *         and ends with last
		 */
		public int chainLength(String first, String last) {
			Node n = this.findShortestSequence(first, last);
			if (n == null) {
				return Integer.MAX_VALUE;
			}
			int size = this.getWordPairsList(n).size();
			return size;
		}

		/**
		 * A method to find the Node that gives the shortest sequence from
		 * String first to String last
		 * 
		 * @param first
		 *            The String from where the word chain begins
		 * @param last
		 *            A String from where the word chain ends
		 * @return The node that is the shortest path from the String first to
		 *         the String last
		 */
		private Node findShortestSequence(String first, String last) {

			Map<String, Boolean> childrenVisited = new HashMap<>();

			LinkedList<Node> queue = new LinkedList<Node>();

			Node firstNode = new Node(first, null, this.graph);

			queue.add(firstNode);

			Node node = null;

			while (queue.size() != 0) {

				node = queue.poll();

				if (this.isNewNode(childrenVisited, node)) {

					ArrayList<Node> childNodes = node.getChildren();

					childrenVisited.put(node.value, true);

					for (int i = 0; i < childNodes.size(); i++) {

						Node n = childNodes.get(i);

						if (n.value.equals(last)) {
							return n;
						}
						queue.add(n);
					}
				}
			}
			return null;
		}

		/**
		 * A method to return the word chain output from a Node n
		 * 
		 * @param n
		 *            A node from which the String output is to be gotten
		 * @return A String containing the word chain that contains all the
		 *         history of Node n
		 */
		private String getWordChainOutput(Node n) {

			if (n == null) {
				return "[]";
			}

			ArrayList<String> wordPairs = this.getWordPairsList(n);
			String s = "[";

			for (int i = 0; i < wordPairs.size(); i++) {
				s += wordPairs.get(i);
				if ((i + 1) < wordPairs.size()) {
					s += ",";
				} else {
					// last iteration.
				}
			}
			return (s + "]");
		}

		/**
		 * A method to get word pairs from the Node n
		 * 
		 * @param n
		 *            The Node from which the word chains are found
		 * @return An ArrayList containing the word pairs
		 */
		private ArrayList<String> getWordPairsList(Node n) {

			ArrayList<String> parents = n.getHistory();
			ArrayList<String> wordPairs = new ArrayList<>();

			for (int i = 0; (i + 1) < parents.size(); i++) {
				wordPairs.add(parents.get(i) + " " + parents.get(i + 1));
			}

			wordPairs.add(parents.get(parents.size() - 1) + " " + n.value);

			return wordPairs;
		}

		/**
		 * A method to return True if the Node n is a new node, False otherwise
		 * 
		 * @param visited
		 *            A Map containing the Node values and if they have been
		 *            visited already
		 * @param node
		 *            A Node which is to be found to be visited
		 * @return True if the Node n has been visited, False otherwise
		 */
		private boolean isNewNode(Map<String, Boolean> visited, Node node) {

			return (visited.get(node.value) == null || visited.get(node.value) == false);
		}
	}

	/**
	 * A class to represent a Node in a graph
	 * 
	 * @author Sanjeeb Sangraula
	 *
	 */
	class Node {

		// An instance variable to contain the DiGraph
		private DiGraph graph;
		// An instance variable to hold the Node's value
		private String value = null;
		// An instance variable to hold the history of traversal of the Node n
		private ArrayList<String> history;

		/**
		 * An argument constructor to create a new instance of Node
		 * 
		 * @param value
		 *            The value of the Node
		 * @param parent
		 *            The Node that is the parent of this Node
		 * @param graph
		 *            The DiGraph that contains the list of words
		 */
		public Node(String value, Node parent, DiGraph graph) {

			this.graph = graph;
			this.history = new ArrayList<>();
			this.value = value;
			this.addHistory(parent);
		}

		/**
		 * A method to add a Node to the history of this Node
		 * 
		 * @param parent
		 *            The Node that is to be added to this Node
		 */
		public void addHistory(Node parent) {

			if (parent != null && parent.getHistory() != null) {

				this.history.addAll(parent.getHistory());
			}
			if (parent != null) {

				this.history.add(parent.value);
			}
		}

		/**
		 * A method to get the history of this Node
		 * 
		 * @return An ArrayList of Strings containing the history of this Node
		 */
		public ArrayList<String> getHistory() {

			return this.history;
		}

		/**
		 * A method to get the level of this Node
		 * 
		 * @return The current level of this Node
		 */
		public int getLevel() {

			return this.history.size();
		}

		/**
		 * A method to get the children of this Node
		 * 
		 * @return An ArrayList of Nodes that contains the children of this Node
		 */
		public ArrayList<Node> getChildren() {

			ArrayList<Node> childNodes = new ArrayList<>();
			Set<String> children = this.graph.getAdjacent(this.value);
			Iterator<String> it = children.iterator();
			while (it.hasNext()) {
				Node node = new Node(it.next(), this, this.graph);
				childNodes.add(node);
			}
			return childNodes;
		}
	}
}
