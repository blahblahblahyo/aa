package pac_11.pacmanlogic;

import java.util.Random;
/**
 * ghost
 */
public class Ghost extends Entity{

    private static final Random RANDOM = new Random();
    private boolean powerStatus = false; 
    private int powerPelletCount = 20;
    private int[] position;
    private double[][] diffusedArray;


    //Constructor
    public Ghost(int[] initialPosition, double[][] array){
        setPosition(initialPosition);
        setDiffusedArray(array);
    }

    public int[] move(String input) {
        // int rand = RANDOM.nextInt(4);
        int[][] directions = {{0, -1}, {0, 1}, {1,-1}, {1, 1}};
        // // Up, Down, Left, Right
        
        // return directions[rand];

        double [][] array = getDiffusedArray();
        int[] position = getPosition();
        double bestVal = array[position[0]][position[1]];
        int decision = 0;
        int[][] toCheck = {{position[0] - 1, position[1]}, {position[0] + 1, position[1]}, {position[0], position[1] - 1}, {position[0], position[1] + 1}};
        // double[] values = {0, 0, 0, 0};
        for (int i = 0; i < toCheck.length; i++) {
            if (toCheck[i][0] >= 0 && toCheck[i][0] <= array.length-1 && toCheck[i][1] >= 0 && toCheck[i][1] <= array.length-1) {
                if (array[toCheck[i][0]][toCheck[i][1]] > bestVal) {
                    bestVal = array[toCheck[i][0]][toCheck[i][1]];
                    decision = i;
                }
            }
        }

        return directions[decision];

    }

    /**
     * @return the diffusedArray
     */
    public double[][] getDiffusedArray() {
        return diffusedArray;
    }

    
    /**
     * @param diffusedArray the diffusedArray to set
     */
    public void setDiffusedArray(double[][] diffusedArray) {
        this.diffusedArray = diffusedArray;
    }
    
    public void setPosition(int[] position){
        this.position = position;
    }

    public int[] getPosition(){
        return position;
    }

    public boolean getPowerStatus(){
        return powerStatus;
    }
    public void setPowerStatus(boolean value){
        powerStatus = value;
    }

    public int getCounter(){
        return powerPelletCount;
    }
    public void decreaseCounter(){
        powerPelletCount--;
    }

    public void resetCounter(){
        if (powerPelletCount == 0){
            powerPelletCount = 50;
        }
    }
}
