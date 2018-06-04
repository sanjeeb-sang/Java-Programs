

/**
 * 
 * @author Sanjeeb Sangraula
 * This class represents a Wizard in a game
 * 
 */

public class Wizard extends GameCharacter {
	
	/**
	 * 
	 * @param name the name of the Wizard
	 * @param x the initial x-coordinate of the wizard
	 * @param y the initial y-coordinate of the wizard
	 */
	public Wizard(String name, int x, int y) {
		super(name, x, y, "Wizard");
	}
	

	/**
	 * Causes the Wizard to attack the { @code target }
	 * @param target the target to attack
	 */
	@Override
	public boolean attack(GameCharacter target) {
		
		if (this.isInActive()) {
			return false;
		}
				
		double distance = this.getDistanceFrom(target);
		
		if (distance <= 50) {
			target.decreaseHealthPoints(30);
			return true;
		}
		
		return false;
	}
   
	
	/**
	 * Causes the Wizard to move in { @code direction } for { @code distance }
	 * @param direction the direction to move. Could be 'N', 'S', 'E', 'W' for North, South, East and West
	 * @param distance the distance for the Wizard to move
	 */
   @Override
	public void move(char direction, int distance) {
      if (distance < 0) {
         distance = 0;
      }
      
      super.move(direction, distance);
      
   }

}
