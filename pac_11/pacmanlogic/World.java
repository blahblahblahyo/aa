package pac_11.pacmanlogic;


import java.util.Random;

/**
 * world
 */
public class World {

    private int width;
    private int height;
    private String[][] movingArr;
    private String[][] coinArr;

    private static final Random RANDOM = new Random();
    
    /**
     * @return the coinArr
     */
    public String[][] getCoinArr() {
        return coinArr;
    }

    /**
     * @param coinArr the coinArr to set
     */
    public void setCoinArr(String[][] coinArr) {
        this.coinArr = coinArr;
    }

    /**
     * @return the movingArr
     */
    public String[][] getMovingArr() {
        return movingArr;
    }

    /**
     * @param movingArr the movingArr to set
     */
    public void setMovingArr(String[][] movingArr) {
        this.movingArr = movingArr;
    }

    public String[][] copyArr(String[][] arr) {
        int w = arr.length;
        int h = arr[0].length;
        String[][] newArr = new String[w][h];
        for (int width = 0; width < w; width++) {
            for (int height = 0; height < h; height++) {
                newArr[width][height] = arr[width][height];
            }
        }

        return newArr;
    }
    
    public void placeCoins() {
        // calculate how many coins we need ( should be 1/3 or 1/2 of width * height)

        // randomly places coins on vacant blocks
        // while num of coins != coins we need
        // add a coin to an emty slot randomly, num of coins ++
        // will be used only once per round 
        int coinsToPlace = 5;
        // int coinsToPlace = (int) (1/3 * this.height * this.width);
        int coinsPlaced = 0;

        while (coinsPlaced < coinsToPlace) {
            for (int w = 0; w < this.width; w++) {
                for (int h = 0; h < this.height; h++) {
                    if (coinArr[w][h] == " ") {
                        if (RANDOM.nextDouble() > 0.8) {
                            coinArr[w][h] = "C";
                            coinsPlaced ++;
                        }
                    }
                }
            }
        }
    }

    private void placePowerup() {
        // put one power pellet on a vacant place (on coin array, not necessarily the moving array too)

        // will be reused periodically
        int powerupsPlaced = 0;
        while (powerupsPlaced == 0) {
            int w = RANDOM.nextInt(this.width);
            int h = RANDOM.nextInt(this.height);

            if (coinArr[w][h] == " ") {
                coinArr[w][h] = "O";
                powerupsPlaced ++;
            }
        }
    }
	
	//set power pellet array
	public void setPowerUpArr(){
		String[][] newGhostArr = copyArr(getMovingArr());
		
		for(int i =0; i< width;i++){
			for (int j =0;j < height; j++){
				if(newGhostArr[i][j] == "G"){
					newGhostArr[i][j] = "g";
				}
			}
		}
	
		setMovingArr(newGhostArr);
	}
	
	//resets array to original after power up has ended
	public void resetFromPowerUpArr(){
		String[][] newGhostArr = copyArr(getMovingArr());
		
		for(int i =0; i< width;i++){
			for (int j =0;j < height; j++){
				if(newGhostArr[i][j] == "g"){
					newGhostArr[i][j] = "G";
				}
			}
		}
	
		setMovingArr(newGhostArr);
	}
		

    public World(String[][] arr, int[] playerPos, int[] ghostPos) {       
        this.width = arr.length;
        this.height = arr[0].length;
        String[][] m = copyArr(arr);
        m[playerPos[0]][playerPos[1]] = "P";
        m[ghostPos[0]][ghostPos[1]] = "G";
        setMovingArr(m);
        setCoinArr(copyArr(arr));

        placeCoins();
        placePowerup();
    }
}
