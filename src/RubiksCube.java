public class RubiksCube {

    public RubiksCube(){
        size = 3;
        setDefaultColors();
    }

    public RubiksCube(int size){
        this.size = size;
        setDefaultColors();
    }

    private int size;
    private int[][][] cube = new int[6][size][size];

    public void setDefaultColors(){
        for (int sideID = 0; sideID < 6; sideID++)
            for (int row = 0; row < size; row++)
                for (int column = 0; column < size; column++)
                    cube[sideID][row][column] = sideID;
    }
}
