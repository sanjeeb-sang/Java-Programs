package browser;

import java.net.URL;

/**
 * A Java class that simulates the navigational tools of an Internet browser,
 * using Stack object(s) to keep track of the sites visited.
 * 
 * @author Sanjeeb Sangraula
 *
 */
public class Browser {

	private String currentUrl;
	private StackInt<String> backStack, forwardStack, historyStack;

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// CONSTRUCTORS

	/**
	 * A no argument constructor for the Browser class. It initializes the
	 * browser with the url "http://www.ulm.edu".
	 */
	public Browser() {
		this("http://www.ulm.edu");
	}

	/**
	 * An argument constructor that initializes the Browser object with the url
	 * {@code url}
	 * 
	 * @param url
	 *            the url that is to be initially loaded.
	 */
	public Browser(String url) {

		// initialize backStack, forwardStack and historyStack
		backStack = new LinkedStack<String>();
		forwardStack = new LinkedStack<String>();
		historyStack = new LinkedStack<String>();

		// load ulm.edu
		this.load(url);

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// PUBLIC METHODS

	/**
	 * Method that attempts to load the web page with the URL passed as argument
	 * 
	 * @return true if the url is valid, false otherwise
	 */
	public boolean load(String url) {

		// add the old mCurrentUrl to the back stack
		if (this.currentUrl != null) {
			this.addToBackStack(this.currentUrl);
		}

		// clear the forward stack as new url is loaded
		this.clearForwardStack();

		// change current url to the new version
		this.currentUrl = url;

		if (isUrlValid(url)) {
			// url is valid

			// add the url to history as it's is valid
			this.addToHistory(url);

			return true;

		} else {
			// url is not valid

			return false;
		}
	}

	/**
	 * A method that returns a String object containing the URL for the current
	 * page. If the url for the page is invalid then it returns "ERROR: CANNOT
	 * FIND <url>"
	 * 
	 * @return string object containing the URL for the current page or error if
	 *         the url is incorrect
	 */
	public String currentPage() {
		return this.getContent();
	}

	/**
	 * A method that returns true if there is a previous page in the browsing
	 * history, false otherwise
	 * 
	 * @return true if previous page exists, false otherwise
	 */
	public boolean canGoBack() {
		return !this.backStack.empty();
	}

	/**
	 * A method that returns true if there is a next page in the browsing
	 * history, false otherwise
	 * 
	 * @return true if there's a next page, false otherwise
	 */
	public boolean canGoForward() {
		return !this.forwardStack.empty();
	}

	/**
	 * A method that makes the browser load the previous page. The previous page
	 * is stored in a stack is loaded when the goBack method is called.
	 * 
	 * @return the url of the page if the url exists, and error if the url
	 *         doesn't exists
	 */
	public String goBack() {

		// push the current url to the forward stack
		this.forwardStack.push(this.currentUrl);

		// set the previous as the new page
		this.setCurrentPage(this.backStack.pop());

		return this.getContent();
	}

	/**
	 * A method that makes the browser load the forward page. The forward page
	 * is stored in a stack is loaded when the gogoForward method is called.
	 * 
	 * @return the url of the page if the url exists, and if the url doesn't
	 *         exists then it displays the error
	 */
	public String goForward() {

		// add the current page to the back stack
		this.addToBackStack(this.currentUrl);

		// get the forward page url from the forwardStack and set it as current
		// page
		this.setCurrentPage(this.forwardStack.pop());

		return this.getContent();
	}

	/**
	 * Returns a list of the web pages visited as a vertical list in a single
	 * string. NOTE: There's a new line before the list and after the list.
	 * 
	 * @return the list of web sites visited
	 */
	public String history() {

		String historyString = "\nHISTORY:\n";

		StackInt<String> historyStackCopy = this.getCopyOfHistoryStack();

		while (!historyStackCopy.empty()) {

			historyString += historyStackCopy.pop() + "\n";

		}
		return historyString + "\n";
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// PRIVATE HELPER METHODS

	/**
	 * Sets the url of the current page
	 * 
	 * @param url
	 *            the url that will be set to the current page
	 */
	private void setCurrentPage(String url) {

		this.currentUrl = url;

	}

	/**
	 * Returns the content of the current page
	 * 
	 * @return the url if currentUrl is valid, else returns “ERROR: CANNOT FIND
	 *         ” + currentUrl
	 */
	private String getContent() {

		return this.isUrlValid(this.currentUrl) ? currentUrl : "ERROR: CANNOT FIND " + currentUrl;

	}

	/**
	 * Method to check if the url is valid.
	 * 
	 * @return true if url is valid, false if url is invalid
	 */
	private boolean isUrlValid(String url) {

		try {
			URL webpage = new URL(url);
			if (webpage.getContent() != null) {
				// url properly formatted and found
				return true;
			}
			return false;
		}

		catch (Exception e) {
			// url was not properly formatted
			return false;
		}

	}

	/**
	 * Clears all the URL's in the forward stack
	 */
	private void clearForwardStack() {

		while (!forwardStack.empty()) {

			forwardStack.pop();
		}
	}

	/**
	 * Adds the passed url string to the back stack
	 * 
	 * @param url
	 *            the url that is to be added to the backStack
	 */
	private void addToBackStack(String url) {

		this.backStack.push(url);

	}

	/**
	 * Adds the passed url to the historyStack
	 * 
	 * @param url
	 *            the url that is to be added to the history stack
	 */
	private void addToHistory(String url) {

		this.historyStack.push(url);
	}

	/**
	 * A method that builds and returns a copy of a historyStack
	 * 
	 * @return the copy of the passed stack
	 */
	private StackInt<String> getCopyOfHistoryStack() {

		StackInt<String> newStack = new LinkedStack<String>();
		StackInt<String> newStack2 = new LinkedStack<String>();

		while (!historyStack.empty()) {

			String stackItem = historyStack.pop();

			newStack.push(stackItem);
			newStack2.push(stackItem);
		}

		historyStack = this.getStackInReverse(newStack);

		return this.getStackInReverse(newStack2);
	}

	/**
	 * Method that reverses the content of a stack and then returns the new
	 * stack. The old stack will be empty after the method is completed.
	 * 
	 * @param stack
	 *            the stack that is to be reversed
	 * @return a stack whose contents are reverse of the old stack
	 */
	private StackInt<String> getStackInReverse(StackInt<String> stack) {

		StackInt<String> newStack = new LinkedStack<String>();

		while (!stack.empty()) {
			newStack.push(stack.pop());
		}

		return newStack;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
