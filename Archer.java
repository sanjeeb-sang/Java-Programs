
/**
 * 
 * @author Sanjeeb Sangraula
 * This class represents a Archer in a game
 */

public class Archer extends GameCharacter {
	
	/**
	 * 
	 * @param name the name of the Archer
	 * @param x the initial x-coordinate of the Archer
	 * @param y the initial y-coordinate of the Archer
	 */
	public Archer(String name, int x, int y) {
		super(name, x, y, "Archer");
	}
   
	/**
	 * Overriding the abstract method from the GameCharacter class.
	 * @param target the GameCharacter that this Archer is to attack
	 */
   @Override
	public boolean attack(GameCharacter target) {
		
		if (this.isInActive()) {
			return false;
		}
				
		double distance = this.getDistanceFrom(target);
		
		if (distance <= 30) {
			target.decreaseHealthPoints(10);
			return true;
		}
		
		return false;
	}

	
   /**
	 * Causes the Archer to move in { @code direction } for { @code distance }.
	 * If the distance is greater than 5, it's reduced to 5 and if it's negative then its converted to zero.
	 * @param direction the direction to move. Could be 'N', 'S', 'E', 'W' for North, South, East and West
	 * @param distance the distance for the Archer to move
	 */
	@Override
	public void move(char direction, int distance) {
   
      if (distance > 5) {
         distance = 5;
      }
      if (distance < 0) {
         distance = 0;
      }
      
      super.move(direction, distance);
   
   }

}


