
package pac_11.pacmanlogic;

/**
 * pacman
 */
public class Pacman extends Entity{
	
	private int score = 0;
	private int lives = 3;
	public int[] prevMove = new int[] {0, -1};
	private int[] position;

	//constructor
	public Pacman(int[] initialPosition){
        setPosition(initialPosition);
	}
	
	/**
	This method takes in user input and converts it into the correct format to be used in Brain.java
	*/
    public int[] move(String input){
        System.out.println(input);
		int[] direction;
			
		System.out.println(prevMove[0] + "  " + prevMove[1]);
		switch(input) { 
			case "w":
				direction = new int[] {0, -1};
				break;

			case "s":
				direction = new int[] {0, 1};
				break;

			case "a":
				direction = new int[] {1, -1};
				break;

			case "d":
				direction = new int[] {1, 1};
				break;

            default:
				// previous move
                return prevMove;
		}
		this.prevMove = direction;

        return direction;
		
	}

	public void setPosition(int[] position){
		this.position = position;
	}

	public int[] getPosition(){
		return position;
	}

	public int getLives(){
		return lives;
	}

	public int getScore(){
		return score;
	}

	public void loseLife(){
		lives--;
	}
	
	public void addScore(int aScore){
		score += aScore;
	}
}
