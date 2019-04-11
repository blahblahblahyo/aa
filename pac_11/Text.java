import pacmanlogic.*;
import java.util.Scanner;

public class Text {

        public static void main(String[] args) {
        // Gameloop
        Brain gameBrain = new Brain();
        Scanner keyboard = new Scanner(System.in);

        while (gameBrain.getPlayer().getScore() < 1000 && !gameBrain.checkGameOver()) {
            Pacman player = gameBrain.getPlayer();
            Ghost ghost1 = gameBrain.getGhost();

			gameBrain.checkLives();
            gameBrain.displayBoard();

            gameBrain.validateMove(player.getPosition(), player.move(keyboard.nextLine()));
            gameBrain.checkLives();
            
            gameBrain.validateMove(ghost1.getPosition(), ghost1.move(""));
            gameBrain.checkLives();
            
            gameBrain.checkCoins();
            if(ghost1.getPowerStatus() && ghost1.getCounter() > 0){
                ghost1.decreaseCounter();
            }
            else{
                gameBrain.deactivatePowerUp();
                gameBrain.getGhost().resetCounter();
            }

            System.out.println("Curent Score: " + gameBrain.getPlayer().getScore());
			System.out.println("Lives Left: " + gameBrain.getPlayer().getLives());
            System.out.println();
			
        }
        keyboard.close();
        System.out.println("Curent Score: " + gameBrain.getPlayer().getScore());
        gameBrain.displayBoard();

        // Display Win/Lose message
    	if (gameBrain.checkGameOver()) {
            System.out.println("You Lost!");
        } else {
	        System.out.println("You Won!");
        }
    }
}