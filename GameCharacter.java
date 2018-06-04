

/**
 * 
 * @author Sanjeeb Sangraula
 * This abstract class represents a GameCharacter
 *
 */

public abstract class GameCharacter {

	private String mName;
	private int mHealthPoints;
	private int mX;
	private int mY;
	private boolean mIsInactive = false;
	private String mType;
	
	/**
	 * Creates a new GameCharacter with the name { @code name }, { @code x } as the x-coordinate and the { @code y } as the y-coordinate
	 * @param name the name of the GameCharacter
	 * @param x the initial x-coordinate of the GameCharacter
	 * @param y the initial y-coordinate of the GameCharacter
	 * @param type the type of the GameCharacter
	 */
	public GameCharacter (String name, int x, int y, String type) {
		mHealthPoints = 100;
		mName = name;
		mX = x;
		mY = y;
		mType = type;
	}
		
	/**
	 * Return the character's name
	 * @return the name of the GameCharacter
	 */
	public String getName() {
		return mName;		
	}
	
	/**
	 * Return the character's health points
	 * @return the health points of the GameCharacter
	 */
	public int getHealthPoints() {
		return mHealthPoints;
	}
	
	/**
	 * Return the character's x-coordinate
	 * @return the current x-coordinate of the GameCharacter
	 */
	public int getX() {
		return mX;
	}
	
	/**
	 * Return the character's y-coordinate
	 * @return the current y-coordinate of the GameCharacter
	 */
	public int getY() {
		return mY;
	}
	
	/**
	 * Return the distance from this character to the { @code character } 
	 * @param character the character whose distance from this GameCharacter is to be calculated
	 * @return the distance between this character and { @code character }
	 */
	public double getDistanceFrom(GameCharacter character) {

		double xDistance = Math.pow((character.getX() - this.getX()), 2);
		
		double yDistance = Math.pow((character.getY() - this.getY()), 2);
		
		double distance = Math.sqrt(xDistance + yDistance);
		
		return distance;
		
	}
	
	/**
	 * Move the GameCharacter in the { @code direction } for { @code distance }
	 * @param direction the direction where the GameCharacter is supposed to move
	 * @param distance the distance to move the GameCharacter
	 */
	public void move(char direction, int distance) {
		
		if (this.isInActive()) {
			return;
		}
		
		switch (direction) {
		
		case 'N':
			
			mY += distance;
			
			break;
			
		case 'S':
			
			mY -= distance;
			
			break;
			
		case 'E':
			
			mX += distance;
			
			break;
			
		case 'W':
			
			mX -= distance;
			
			break;		
		}
	}
	
	/**
	 * Abstract method to attack to be implemented in sub class
	 * @param target the GameCharacter which this GameCharacter is supposed to attack
	 * @return true if this GameCharacter attacked target, false otherwise
	 */
	public abstract boolean attack(GameCharacter target);
	
	/**
	 *  
	 * @param isInactive true if this GameCharacter is active, false otherwise
	 */
	public void setInactive(boolean isInactive){
		mIsInactive = isInactive;
	}
	
	/**
	 * Decreases the health points by { @code decreaseBy }
	 * @param decreaseBy the number by which we are supposed to decrease the health points of this GameCharacter
	 */
	public void decreaseHealthPoints(int decreaseBy) {
		mHealthPoints -= decreaseBy;
		if (mHealthPoints < 10) {
			this.setInactive(true);
		}
		if (mHealthPoints < 0) {
			mHealthPoints = 0;
		}
	}
	
	/**
	 * Return if the GameCharacter is active
	 * @return true if this GameCharacter is active, false if it is inactive
	 */
	public boolean isInActive() {
		return mIsInactive;
	}
	
	/**
	 * Return the type of the GameCharacter
	 * @return the type of the GameCharacter
	 */
	public String getType(){
		return mType;
	}
	
	/**
	 * Return a summary of the GameCharacter
	 * @return a summary of this GameCharacter in string representation
	 */
	public String toString() {
		String string = "Name : " + this.getName() + " Type : " + this.getType() + " Health Points : " + this.getHealthPoints() + " X Coordinate : " + this.getX() + " Y Coordinate : " + this.getY();
		return string;
	}
}

