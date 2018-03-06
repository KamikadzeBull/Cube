import java.util.*;

public class RubiksCube {

    // дефолтовый размер
    public RubiksCube(){
        size = 3;
        setDefaultColors();
    }

    // кастомный размер
    public RubiksCube(int size){
        this.size = size;
        setDefaultColors();
    }

    private int size;
    private int[][][] cube = new int[6][size][size];

    // возвращение куба в собранный вид
    public void setDefaultColors(){
        for (int sideID = 0; sideID < 6; sideID++)
            for (int row = 0; row < size; row++)
                for (int column = 0; column < size; column++)
                    cube[sideID][row][column] = sideID;
    }

    // отображение куба
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

    public void cubeRotation(String direction) throws IllegalAccessException {
        int[][] tempSide;
        switch (direction){
            case "clockwise":
                tempSide = cube[0];
                cube[0] = cube[3];
                cube[3] = cube[1];
                cube[1] = cube[2];
                cube[2] = tempSide;
                sideRotation(4,"clockwise");
                sideRotation(5,"anticlockwise");
                break;
            case "anticlockwise":
                tempSide = cube[0];
                cube[0] = cube[2];
                cube[2] = cube[1];
                cube[1] = cube[3];
                cube[3] = tempSide;
                sideRotation(4,"anticlockwise");
                sideRotation(5,"clockwise");
                break;
            case "invert":
                tempSide = cube[4];
                cube[4] = cube[5];
                cube[5] = tempSide;
                sideInversion(0);
                sideInversion(1);
                tempSide = cube[0];
                cube[0] = cube[1];
                cube[1] = tempSide;
                sideInversion(2);
                sideInversion(3);
                break;
            default:
                throw new IllegalAccessException();
        }
    }

    // установление соответствия между строковыми параметрами и индексами сторон
    private static int getSideID (String side){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("front", 0);    // white
        map.put("back", 1);     // yellow
        map.put("left", 2);     // orange
        map.put("right", 3);    // red
        map.put("top", 4);      // blue
        map.put("bottom", 5);   // green
        return map.get(side);
    }

    /* соседние стороны:
    *       - индексы массива ~ индексы сторон
    *       - элемент массива ~ ряд индексов сторон; формально: представим сторону
    *           как находящуюся прямо перед глазами, тогда ряд индексов является индексами
    *           соседней верхней, правой, нижней и левой сторон
    */
    private static int[] nearSidesID (int sideID) {
        int[][] nearSidesID = {{4,3,5,2}, {4,2,5,3},
                {4,0,5,1}, {4,1,5,0}, {1,3,0,2}, {1,2,0,3}};
        return nearSidesID[sideID];
    }

    /* вращение грани без взаимодействия с другими гранями:
    *       - сторона якобы находится перед глазами
    *       - неважно, каково вращение с постороннего взгляда
    */
    private void sideRotation(int sideID, String direction){
        int[][] oldSide = cube[sideID];
        switch (direction){
            case "clockwise":
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size; j++)
                        cube[sideID][j][(size - 1) - i] = oldSide[i][j];
                break;
            case "anticlockwise":
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size; j++)
                        cube[sideID][(size - 1) - j][i] = oldSide[i][j];
                break;
        }
    }

    // подобие двойного выполнения sideRotation() по одному направлению
    private void sideInversion(int sideID){
        int[][] oldSide = cube[sideID];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                cube[sideID][(size - 1) - i][(size - 1) - i] = oldSide[i][j];
    }
}