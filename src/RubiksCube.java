import java.util.*;

public class RubiksCube {

    // дефолтовый размер
    public RubiksCube(){
        setDefaultColors();
    }

    // кастомный размер
    public RubiksCube(int size){
        if (size >= 2)
            this.size = size;
        else
            throw new IllegalArgumentException("size mustn't be less 2");
        cube = new int[6][size][size];
        setDefaultColors();
    }

    private int size = 3;
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
                for (int count = 0; count < 4; count++)
                    System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /* вращение куба в пространстве:
    *       - по/против час.стр.; меняют положение только боковые грани,
    *           верхняя и нижняя грань просто вращаются в определенном направлении;
    *       - перевернуть; основной акцент на смене положения верхней и нижней грани,
    *           ведь "визуального доступа" до нижней грани не было до совершения этого действия;
    *           впоследствии меняют положение передняя+задняя грани, вращаются левая+правая;
    */
    public void cubeRotation(String direction) throws IllegalArgumentException {
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
                throw new IllegalArgumentException("wrong direction");
        }
    }

    /* вращение слоев:
    *       - вращение грани взаимодействуя с другими гранями;
    *       - из-за принятой индексации и главного постулата "давайте без заморочек" визуальное
    *           вращение может не совпадать с формальным: например, вращая визуально против
    *           часовой стрелки формально выполняется вращение грани по часовой стрелке;
    *       итог: в параметрах указывается формальное направление по принятой индексации;
    */
    public void layerRotation(String side, String direction, int amount) throws IllegalArgumentException {

        if (!(direction.equals("clockwise")) && !(direction.equals("anticlockwise")))
            throw new IllegalArgumentException("wrong direction");
        if ((amount < 1) || (amount >= size))
            throw new IllegalArgumentException("1 <= amount < size");

        int sideID = getSideID(side);
        int extSize = size + 2 * amount;
        int[][] extendedSide = new int[extSize][extSize];
        int[] neighbours = nearSidesID(sideID);

        // выкладываем взаимодействующие элементы соседних граней в расширенную матрицу
        switch (sideID){
            case 0:
                for (int row = amount - 1, i = size-1; row >= 0; row--, i--)
                    for (int column = amount, j = 0; column < amount + size; column++, j++)
                        extendedSide[row][column] = cube[neighbours[0]][i][j];
                for (int column = amount + size, j = 0; column < extSize; column++, j++)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        extendedSide[row][column] = cube[neighbours[1]][i][j];
                for (int row = amount + size, i = 0; row < extSize; row++, i++)
                    for (int column = amount, j = 0; column < amount + size; column++, j++)
                        extendedSide[row][column] = cube[neighbours[2]][i][j];
                for (int column = amount - 1, j = size-1; column >= 0; column--, j--)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        extendedSide[row][column] = cube[neighbours[3]][i][j];
                break;
            case 1:
                for (int row = amount - 1, i = 0; row >= 0; row--, i++)
                    for (int column = amount, j = size-1; column < amount + size; column++, j--)
                        extendedSide[row][column] = cube[neighbours[0]][i][j];
                for (int column = amount + size, j = 0; column < extSize; column++, j++)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        extendedSide[row][column] = cube[neighbours[1]][i][j];
                for (int row = amount + size, i = size-1; row < extSize; row++, i--)
                    for (int column = amount, j = size-1; column < amount + size; column++, j--)
                        extendedSide[row][column] = cube[neighbours[2]][i][j];
                for (int column = amount - 1, j = size-1; column >= 0; column--, j--)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        extendedSide[row][column] = cube[neighbours[3]][i][j];
                break;
            case 2:
                for (int row = amount - 1, j = 0; row >= 0; row--, j++)
                    for (int column = amount, i = 0; column < amount + size; column++, i++)
                        extendedSide[row][column] = cube[neighbours[0]][i][j];
                for (int column = amount + size, j = 0; column < extSize; column++, j++)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        extendedSide[row][column] = cube[neighbours[1]][i][j];
                for (int row = amount + size, j = 0; row < extSize; row++, j++)
                    for (int column = amount, i = size-1; column < amount + size; column++, i--)
                        extendedSide[row][column] = cube[neighbours[2]][i][j];
                for (int column = amount - 1, j = size-1; column >= 0; column--, j--)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        extendedSide[row][column] = cube[neighbours[3]][i][j];
                break;
            case 3:
                for (int row = amount - 1, j = size-1; row >= 0; row--, j--)
                    for (int column = amount, i = size-1; column < amount + size; column++, i--)
                        extendedSide[row][column] = cube[neighbours[0]][i][j];
                for (int column = amount + size, j = 0; column < extSize; column++, j++)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        extendedSide[row][column] = cube[neighbours[1]][i][j];
                for (int row = amount + size, j = size-1; row < extSize; row++, j--)
                    for (int column = amount, i = 0; column < amount + size; column++, i++)
                        extendedSide[row][column] = cube[neighbours[2]][i][j];
                for (int column = amount - 1, j = size-1; column >= 0; column--, j--)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        extendedSide[row][column] = cube[neighbours[3]][i][j];
                break;
            case 4:
                for (int row = amount - 1, i = 0; row >= 0; row--, i++)
                    for (int column = amount, j = size-1; column < amount + size; column++, j--)
                        extendedSide[row][column] = cube[neighbours[0]][i][j];
                for (int column = amount + size, i = 0; column < extSize; column++, i++)
                    for (int row = amount, j = size-1; row < amount + size; row++, j--)
                        extendedSide[row][column] = cube[neighbours[1]][i][j];
                for (int row = amount + size, i = 0; row < extSize; row++, i++)
                    for (int column = amount, j = 0; column < amount + size; column++, j++)
                        extendedSide[row][column] = cube[neighbours[2]][i][j];
                for (int column = amount - 1, i = 0; column >= 0; column--, i++)
                    for (int row = amount, j = 0; row < amount + size; row++, j++)
                        extendedSide[row][column] = cube[neighbours[3]][i][j];
                break;
            case 5:
                for (int row = amount - 1, i = size-1; row >= 0; row--, i--)
                    for (int column = amount, j = 0; column < amount + size; column++, j++)
                        extendedSide[row][column] = cube[neighbours[0]][i][j];
                for (int column = amount + size, i = size-1; column < extSize; column++, i--)
                    for (int row = amount, j = 0; row < amount + size; row++, j++)
                        extendedSide[row][column] = cube[neighbours[1]][i][j];
                for (int row = amount + size, i = size-1; row < extSize; row++, i--)
                    for (int column = amount, j = size-1; column < amount + size; column++, j--)
                        extendedSide[row][column] = cube[neighbours[2]][i][j];
                for (int column = amount - 1, i = size-1; column >= 0; column--, i--)
                    for (int row = amount, j = size-1; row < amount + size; row++, j--)
                        extendedSide[row][column] = cube[neighbours[3]][i][j];
                break;
        }

        // вертим нужную грань без взаимодействия с другими гранями
        sideRotation(sideID, direction);

        // вертим расширенную матрицу
        int [][] oldExtendedSide = new int[extSize][extSize];
        for (int i=0; i < extSize; i++)
            for (int j=0; j < extSize; j++)
                oldExtendedSide[i][j] = extendedSide[i][j];
        switch (direction) {
            case "clockwise":
                for (int i = 0; i < extSize; i++)
                    for (int j = 0; j < extSize; j++)
                        extendedSide[i][j] = oldExtendedSide[extSize-1-j][i];
                break;
            case "anticlockwise":
                for (int i = 0; i < extSize; i++)
                    for (int j = 0; j < extSize; j++)
                        extendedSide[i][j] = oldExtendedSide[j][extSize-1-i];
                break;
        }

        // снимаем элементы с расширенной матрицы обратно на соседние грани
        switch (sideID){
            case 0:
                for (int row = amount - 1, i = size-1; row >= 0; row--, i--)
                    for (int column = amount, j = 0; column < amount + size; column++, j++)
                        cube[neighbours[0]][i][j] = extendedSide[row][column];
                for (int column = amount + size, j = 0; column < extSize; column++, j++)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        cube[neighbours[1]][i][j] = extendedSide[row][column];
                for (int row = amount + size, i = 0; row < extSize; row++, i++)
                    for (int column = amount, j = 0; column < amount + size; column++, j++)
                        cube[neighbours[2]][i][j] = extendedSide[row][column];
                for (int column = amount - 1, j = size-1; column >= 0; column--, j--)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        cube[neighbours[3]][i][j] = extendedSide[row][column];
                break;
            case 1:
                for (int row = amount - 1, i = 0; row >= 0; row--, i++)
                    for (int column = amount, j = size-1; column < amount + size; column++, j--)
                        cube[neighbours[0]][i][j] = extendedSide[row][column];
                for (int column = amount + size, j = 0; column < extSize; column++, j++)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        cube[neighbours[1]][i][j] = extendedSide[row][column];
                for (int row = amount + size, i = size-1; row < extSize; row++, i--)
                    for (int column = amount, j = size-1; column < amount + size; column++, j--)
                        cube[neighbours[2]][i][j] = extendedSide[row][column];
                for (int column = amount - 1, j = size-1; column >= 0; column--, j--)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        cube[neighbours[3]][i][j] = extendedSide[row][column];
                break;
            case 2:
                for (int row = amount - 1, j = 0; row >= 0; row--, j++)
                    for (int column = amount, i = 0; column < amount + size; column++, i++)
                        cube[neighbours[0]][i][j] = extendedSide[row][column];
                for (int column = amount + size, j = 0; column < extSize; column++, j++)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        cube[neighbours[1]][i][j] = extendedSide[row][column];
                for (int row = amount + size, j = 0; row < extSize; row++, j++)
                    for (int column = amount, i = size-1; column < amount + size; column++, i--)
                        cube[neighbours[2]][i][j] = extendedSide[row][column];
                for (int column = amount - 1, j = size-1; column >= 0; column--, j--)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        cube[neighbours[3]][i][j] = extendedSide[row][column];
                break;
            case 3:
                for (int row = amount - 1, j = size-1; row >= 0; row--, j--)
                    for (int column = amount, i = size-1; column < amount + size; column++, i--)
                        cube[neighbours[0]][i][j] = extendedSide[row][column];
                for (int column = amount + size, j = 0; column < extSize; column++, j++)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        cube[neighbours[1]][i][j] = extendedSide[row][column];
                for (int row = amount + size, j = size-1; row < extSize; row++, j--)
                    for (int column = amount, i = 0; column < amount + size; column++, i++)
                        cube[neighbours[2]][i][j] = extendedSide[row][column];
                for (int column = amount - 1, j = size-1; column >= 0; column--, j--)
                    for (int row = amount, i = 0; row < amount + size; row++, i++)
                        cube[neighbours[3]][i][j] = extendedSide[row][column];
                break;
            case 4:
                for (int row = amount - 1, i = 0; row >= 0; row--, i++)
                    for (int column = amount, j = size-1; column < amount + size; column++, j--)
                        cube[neighbours[0]][i][j] = extendedSide[row][column];
                for (int column = amount + size, i = 0; column < extSize; column++, i++)
                    for (int row = amount, j = size-1; row < amount + size; row++, j--)
                        cube[neighbours[1]][i][j] = extendedSide[row][column];
                for (int row = amount + size, i = 0; row < extSize; row++, i++)
                    for (int column = amount, j = 0; column < amount + size; column++, j++)
                        cube[neighbours[2]][i][j] = extendedSide[row][column];
                for (int column = amount - 1, i = 0; column >= 0; column--, i++)
                    for (int row = amount, j = 0; row < amount + size; row++, j++)
                        cube[neighbours[3]][i][j] = extendedSide[row][column];
                break;
            case 5:
                for (int row = amount - 1, i = size-1; row >= 0; row--, i--)
                    for (int column = amount, j = 0; column < amount + size; column++, j++)
                        cube[neighbours[0]][i][j] = extendedSide[row][column];
                for (int column = amount + size, i = size-1; column < extSize; column++, i--)
                    for (int row = amount, j = 0; row < amount + size; row++, j++)
                        cube[neighbours[1]][i][j] = extendedSide[row][column];
                for (int row = amount + size, i = size-1; row < extSize; row++, i--)
                    for (int column = amount, j = size-1; column < amount + size; column++, j--)
                        cube[neighbours[2]][i][j] = extendedSide[row][column];
                for (int column = amount - 1, i = size-1; column >= 0; column--, i--)
                    for (int row = amount, j = size-1; row < amount + size; row++, j--)
                        cube[neighbours[3]][i][j] = extendedSide[row][column];
                break;
        }
    }

    // установление соответствия между строковыми параметрами и индексами сторон
    private static int getSideID (String side) throws IllegalArgumentException{
        HashMap<String, Integer> map = new HashMap<>();
        map.put("front", 0);    // white
        map.put("back", 1);     // yellow
        map.put("left", 2);     // orange
        map.put("right", 3);    // red
        map.put("top", 4);      // blue
        map.put("bottom", 5);   // green
        try { return map.get(side); }
        catch (NullPointerException e){
            throw new IllegalArgumentException("wrong name of side");
        }
    }

    /* соседние стороны:
    *       - индексы массива ~ индексы сторон;
    *       - элемент массива ~ ряд индексов сторон; формально: представим сторону
    *           как находящуюся прямо перед глазами, тогда ряд индексов является индексами
    *           соседней верхней, правой, нижней и левой сторон;
    */
    private static int[] nearSidesID (int sideID) {
        int[][] nearSidesID = {{4,3,5,2}, {4,2,5,3},
                {4,0,5,1}, {4,1,5,0}, {1,3,0,2}, {1,2,0,3}};
        return nearSidesID[sideID];
    }

    /* вращение грани без взаимодействия с другими гранями:
    *       - сторона якобы находится перед глазами;
    *       - неважно, каково вращение с постороннего взгляда;
    */
    private void sideRotation(int sideID, String direction){
        int[][] oldSide = new int[size][size];
        for (int i=0; i < size; i++)
            for (int j=0; j < size; j++)
                oldSide[i][j] = cube[sideID][i][j];
        switch (direction){
            case "clockwise":
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size; j++)
                        cube[sideID][i][j] = oldSide[size-1-j][i];
                break;
            case "anticlockwise":
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size; j++)
                        cube[sideID][i][j] = oldSide[j][size-1-i];
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