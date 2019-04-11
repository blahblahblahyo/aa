package pac_11.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.shape.*;
import javafx.animation.AnimationTimer;
import javafx.scene.image.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import pac_11.pacmanlogic.*;



/**
 * gui
 */
public class Gui extends Application {
    
    Brain gameBrain = new Brain(this);
    AnimationTimer timer;    
    BorderPane root = new BorderPane();
    Image image = new Image("File:pac_11/gui/Ghost.gif");
    ImageView pic = new ImageView(image);
    GridPane gameGridPane = new GridPane();
    HBox var = new HBox(pic);
    Image image1 = new Image("File:pac_11/gui/Gif.gif");
    ImageView pic1 = new ImageView(image1);
   
    HBox var1 = new HBox(pic1);
    String keyPressed = "d";
    Label scoreLabel = new Label("Score: " + gameBrain.getPlayer().getScore());
    
    //creating the visual board
    public void displayBoard() {
        pic.setFitHeight(40);
        pic.setFitWidth(40);
        pic1.setFitHeight(25);
        pic1.setFitWidth(25);
        
        
        for (int i = 0; i < 10; i++) {
            gameGridPane.getColumnConstraints().add(new ColumnConstraints(30));
            gameGridPane.getRowConstraints().add(new RowConstraints(30));
            
        }
            // updates the score and lives
            scoreLabel.setText("Score: " + gameBrain.getPlayer().getScore()+ "Lives: "+ gameBrain.getPlayer().getLives());
        
            gameGridPane.getChildren().clear();
            for (int i = 0; i < gameBrain.getDisplayArr().length; i++) {
                for (int j = 0; j < gameBrain.getDisplayArr()[0].length; j++) {
                    if(gameBrain.getDisplayArr()[i][j] == "P"){
                        gameGridPane.add(pic1,j,i);
                        if(keyPressed =="a"){
                            pic1.setRotate(180.00);
                        }
                        else if(keyPressed == "s"){
                            pic1.setRotate(90.00);
                        }

                        else if(keyPressed == "w"){
                            pic1.setRotate(270.00);
                        }

                        else if(keyPressed == "d"){
                            pic1.setRotate(0.00);
                        }
                        
                    }
                    else if(gameBrain.getDisplayArr()[i][j] == "G"){
                        gameGridPane.add(pic,j,i);
                    }

                    else if(gameBrain.getDisplayArr()[i][j] == "C"){
                        gameGridPane.add(new Circle(10, Color.YELLOW),j,i);
                    }
                    else if(gameBrain.getDisplayArr()[i][j] == "W"){
                        gameGridPane.add(new Rectangle(5, 30,Color.GRAY),j,i);
                    }
                    else if(gameBrain.getDisplayArr()[i][j] == "O"){
                        gameGridPane.add(new Circle (10,Color.BLUE),j,i);
                    }
                    else if(gameBrain.getDisplayArr()[i][j] == "g"){
                        gameGridPane.add(new Rectangle(5, 10,Color.BLACK),j,i);
                    }
                    
                }
            }
    }

    public boolean gameOver() {
        System.out.println("game over reached");

        if(gameBrain.isWin() == true) {
            root.getChildren().remove(gameGridPane);
            System.out.println("game over reached");
            root.setCenter(new Label("YOU WON"));
            //youLost.setTextFill(color.RED);
            timer.stop();
            return true;
        }
        else if(gameBrain.checkLoss() == true){
            timer.stop();
            return true;
        }
        else {return false;}

        
    }
    @Override
    public void start(Stage primaryStage) {
		//setting the texts
        //gameGridPane.setStyle("-fx-background-color: #C0C0C0;");
        Pacman player = gameBrain.getPlayer();
        Ghost ghost1 = gameBrain.getGhost();

        gameGridPane.setGridLinesVisible(true);
        Label youLost = new Label(" You Lost!!");
        root.setTop(scoreLabel);
        root.setCenter(gameGridPane);
        
        gameGridPane.setAlignment(Pos.CENTER);
       
        gameGridPane.setHgap(0);
	    gameGridPane.setVgap(0); 
        
        Scene gameScreen = new Scene(root, 400, 300);

		//Event handler
        gameScreen.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if(key.getCode() == javafx.scene.input.KeyCode.A ) {
                // var.setCenterX(var.getCenterX() + 10);
                keyPressed = "a";
            }
            else if (key.getCode() == javafx.scene.input.KeyCode.W) {
                keyPressed = "w";
                
            }
            else if (key.getCode() == javafx.scene.input.KeyCode.D) {
                keyPressed = "d";
                
            }
            else if (key.getCode() == javafx.scene.input.KeyCode.S) {
                keyPressed = "s";
            }
            else if (key.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                System.exit(0);
            }
            
        });

        long lastUpdate = System.nanoTime();
        
        timer = new AnimationTimer() {
            long lastUpdate = System.nanoTime();
            @Override
            public void handle(long now) {
                displayBoard(); 
                // System.out.println("in animation timer;");
                //from stackoverflow https://stackoverflow.com/questions/30146560/how-to-change-animationtimer-speed
                
                    if (now - lastUpdate >= 500000000l ) {
                    
                    gameBrain.validateMove(player.getPosition(), player.move(keyPressed));
                    gameBrain.checkLives();
                    gameBrain.validateMove(ghost1.getPosition(), ghost1.move("s")); 
                    gameBrain.checkCoins();

                    if(ghost1.getPowerStatus() && ghost1.getCounter() > 0){
                        ghost1.decreaseCounter();
                    }
                    else{
                        gameBrain.deactivatePowerUp();
                        gameBrain.getGhost().resetCounter();
                    }
                    gameBrain.checkLives();
                    //System.out.println(keyPressed);
                    lastUpdate = now;
                    
                }
                
            }
            
        };
        if (gameOver() == false){
        timer.start();}

        if(gameBrain.checkWin()== true) {
            root.getChildren().remove(gameGridPane);
            root.setCenter(youLost);
            //youLost.setTextFill(color.RED);
        }
       
            
        System.out.println(player.getScore());
        scoreLabel.setText("Score: " + player.getScore()+ "Lives: "+ player.getLives());
        primaryStage.setTitle("PACMAN Beta");
        primaryStage.setScene(gameScreen);
        primaryStage.show();

    }

}