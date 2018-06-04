

/**
 * 
 * @author Sanjeeb Sangraula
 * This class represents a Knight in a game
 * 
 */

public class Knight extends GameCharacter {
	
	/**
	 * 
	 * @param name the name of the Knight
	 * @param x the initial x-coordinate of the Knight
	 * @param y the initial y-coordinate of the Knight
	 */
	public Knight(String name, int x, int y) {
		super(name, x, y, "Knight");
		Knight knight = 
	}
	
	
	/**
	 * Causes the Knight to attack the { @code target }
	 * @param target the GameCharacter target to attack
	 */
	@Override
	public boolean attack(GameCharacter target) {
		
		if (this.isInActive()) {
			return false;
		}
				
		double distance = this.getDistanceFrom(target);
		
		if (distance <= 5) {
			target.decreaseHealthPoints(20);
			return true;
		}
		
		return false;
	}
   
   
	/**
	 * Causes the Knight to move in { @code direction } for { @code distance }
	 * @param direction the direction to move. Could be 'N', 'S', 'E', 'W' for North, South, East and West
	 * @param distance the distance for the Knight to move
	 */
   @Override
	public void move(char direction, int distance) {
   
      if (distance > 2) {
         distance = 2;
      }
      if (distance < 0) {
         distance = 0;
      }
      super.move(direction, distance);
   }

}

