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

    public void show(){
        System.out.print("front");
        for (int count = 0; count < size-1; count++)
            System.out.print(" ");
        System.out.print("back");
        for (int count = 0; count < size; count++)
            System.out.print(" ");
        System.out.print("left");
        for (int count = 0; count < size; count++)
            System.out.print(" ");
        System.out.print("right");
        for (int count = 0; count < size-1; count++)
            System.out.print(" ");
        System.out.print("top");
        for (int count = 0; count < size+1; count++)
            System.out.print(" ");
        System.out.println("button");
        for (int row = 0; row < size; row++) {
            for (int sideID = 0; sideID < 6; sideID++) {
                for (int column = 0; column < size; column++)
                    System.out.print(cube[sideID][row][column]);
                for (int count = 0; count < size+1; count++)
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
}
