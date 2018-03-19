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
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder("");
        s.append("front");
        for (int count = 0; count < size-1; count++)
            s.append(" ");
        s.append("back");
        for (int count = 0; count < size; count++)
            s.append(" ");
        s.append("left");
        for (int count = 0; count < size; count++)
            s.append(" ");
        s.append("right");
        for (int count = 0; count < size-1; count++)
            s.append(" ");
        s.append("top");
        for (int count = 0; count < size+1; count++)
            s.append(" ");
        s.append("button\n");
        for (int row = 0; row < size; row++) {
            for (int sideID = 0; sideID < 6; sideID++) {
                for (int column = 0; column < size; column++)
                    s.append(cube[sideID][row][column]);
                for (int count = 0; count < 4; count++)
                    s.append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    // проверяет на равенство кубы: в случае параметра в виде массива
    public boolean equals (int[][][] otherCube){
        int [][][] thisCube = this.getCube();
        boolean flag = true;
        try{
            for (int i = 0; i < 6; i++)
                for (int j = 0; j < size; j++)
                    for (int k = 0; k < size; k++)
                        if (thisCube[i][j][k] != otherCube[i][j][k])
                            throw new Exception();
        }
        catch (Exception e){
            flag = false;
        }
        return flag;
    }

    // ... в случае параметра в виде объекта класса RubiksCube
    public boolean equals (RubiksCube otherCube){
        int [][][] thisCube = this.getCube();
        int [][][] anotherCube = otherCube.getCube();
        boolean flag = true;
        try{
            for (int i = 0; i < 6; i++)
                for (int j = 0; j < size; j++)
                    for (int k = 0; k < size; k++)
                        if (thisCube[i][j][k] != anotherCube[i][j][k])
                            throw new Exception();
        }
        catch (Exception e){
            flag = false;
        }
        return flag;
    }

    // делает 50 поворотов рандомного количества слоев рандомных сторон за вас
    public void setRandomColors(){
        Random move = new Random();
        Random amount = new Random();
        List<Integer> randomMoves = new ArrayList<>();
        List<Integer> randomAmount = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            randomMoves.add(move.nextInt(12));
            randomAmount.add(amount.nextInt(size-1) + 1);
        }
        for (int i = 0; i < 50; i++)
            switch (randomMoves.get(i)){
                case 0:
                    this.layerRotation(Side.FRONT, Direction.CLOCKWISE, randomAmount.get(i));
                    break;
                case 1:
                    this.layerRotation(Side.FRONT, Direction.ANTICLOCKWISE, randomAmount.get(i));
                    break;
                case 2:
                    this.layerRotation(Side.BACK, Direction.CLOCKWISE, randomAmount.get(i));
                    break;
                case 3:
                    this.layerRotation(Side.BACK, Direction.ANTICLOCKWISE, randomAmount.get(i));
                    break;
                case 4:
                    this.layerRotation(Side.LEFT, Direction.CLOCKWISE, randomAmount.get(i));
                    break;
                case 5:
                    this.layerRotation(Side.LEFT, Direction.ANTICLOCKWISE, randomAmount.get(i));
                    break;
                case 6:
                    this.layerRotation(Side.RIGHT, Direction.CLOCKWISE, randomAmount.get(i));
                    break;
                case 7:
                    this.layerRotation(Side.RIGHT, Direction.ANTICLOCKWISE, randomAmount.get(i));
                    break;
                case 8:
                    this.layerRotation(Side.TOP, Direction.CLOCKWISE, randomAmount.get(i));
                    break;
                case 9:
                    this.layerRotation(Side.TOP, Direction.ANTICLOCKWISE, randomAmount.get(i));
                    break;
                case 10:
                    this.layerRotation(Side.BOTTOM, Direction.CLOCKWISE, randomAmount.get(i));
                    break;
                case 11:
                    this.layerRotation(Side.BOTTOM, Direction.ANTICLOCKWISE, randomAmount.get(i));
                    break;
            }
    }


    /* вращение куба в пространстве:
    *       - три оси: VERTICAL (напр. от верхней стороны), HORIZONTAL (от левой), STRAIGHT (от фронтальной);
    *       - два направления вращения: CLOCKWISE, ANTICLOCKWISE;
    */
    public void cubeRotation(Axis axis, Direction direction) throws IllegalArgumentException {
        int[][] tempSide;
        switch (direction){
            case CLOCKWISE:
                switch (axis){
                    case VERTICAL:
                        tempSide = cube[0];
                        cube[0] = cube[3];
                        cube[3] = cube[1];
                        cube[1] = cube[2];
                        cube[2] = tempSide;
                        sideRotation(Side.TOP, Direction.CLOCKWISE);
                        sideRotation(Side.BOTTOM, Direction.ANTICLOCKWISE);
                        break;
                    case HORIZONTAL:
                        tempSide = cube[0];
                        cube[0] = cube[4];
                        cube[4] = cube[1];
                        sideInversion(Side.TOP);
                        cube[1] = cube[5];
                        sideInversion(Side.BACK);
                        cube[5] = tempSide;
                        sideRotation(Side.LEFT, Direction.CLOCKWISE);
                        sideRotation(Side.RIGHT, Direction.ANTICLOCKWISE);
                        break;
                    case STRAIGHT:
                        tempSide = cube[4];
                        cube[4] = cube[2];
                        sideRotation(Side.TOP, Direction.CLOCKWISE);
                        cube[2] = cube[5];
                        sideRotation(Side.LEFT, Direction.CLOCKWISE);
                        cube[5] = cube[3];
                        sideRotation(Side.BOTTOM, Direction.CLOCKWISE);
                        cube[3] = tempSide;
                        sideRotation(Side.RIGHT, Direction.CLOCKWISE);
                        sideRotation(Side.FRONT, Direction.CLOCKWISE);
                        sideRotation(Side.BACK, Direction.ANTICLOCKWISE);
                        break;
                }
                break;
            case ANTICLOCKWISE:
                switch (axis){
                    case VERTICAL:
                        tempSide = cube[0];
                        cube[0] = cube[2];
                        cube[2] = cube[1];
                        cube[1] = cube[3];
                        cube[3] = tempSide;
                        sideRotation(Side.TOP, Direction.ANTICLOCKWISE);
                        sideRotation(Side.BOTTOM,Direction.CLOCKWISE);
                        break;
                    case HORIZONTAL:
                        tempSide = cube[0];
                        cube[0] = cube[5];
                        cube[5] = cube[1];
                        sideInversion(Side.BOTTOM);
                        cube[1] = cube[4];
                        sideInversion(Side.BACK);
                        cube[4] = tempSide;
                        sideRotation(Side.LEFT, Direction.ANTICLOCKWISE);
                        sideRotation(Side.RIGHT, Direction.CLOCKWISE);
                        break;
                    case STRAIGHT:
                        tempSide = cube[4];
                        cube[4] = cube[3];
                        sideRotation(Side.TOP, Direction.ANTICLOCKWISE);
                        cube[3] = cube[5];
                        sideRotation(Side.RIGHT, Direction.ANTICLOCKWISE);
                        cube[5] = cube[2];
                        sideRotation(Side.BOTTOM, Direction.ANTICLOCKWISE);
                        cube[2] = tempSide;
                        sideRotation(Side.LEFT, Direction.ANTICLOCKWISE);
                        sideRotation(Side.FRONT, Direction.ANTICLOCKWISE);
                        sideRotation(Side.BACK, Direction.CLOCKWISE);
                        break;
                }
                break;
        }
    }

    /* вращение слоев:
    *       - вращение грани взаимодействуя с другими гранями;
    *       - из-за принятой индексации и главного постулата "давайте без заморочек" визуальное
    *           вращение может не совпадать с формальным: например, вращая визуально против
    *           часовой стрелки формально выполняется вращение грани по часовой стрелке;
    *       итог: в параметрах указывается формальное направление по принятой индексации;
    */
    public void layerRotation(Side side, Direction direction, int amount) throws IllegalArgumentException {

        if ((amount < 1) || (amount >= size))
            throw new IllegalArgumentException("1 <= amount < size");

        int sideID = side.ID;
        int extSize = size + 2 * amount;
        int[][] extendedSide = new int[extSize][extSize];
        int[] neighbours = nearSidesID[sideID];

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
        sideRotation(side, direction);

        // вертим расширенную матрицу
        int [][] oldExtendedSide = new int[extSize][extSize];
        for (int i=0; i < extSize; i++)
            for (int j=0; j < extSize; j++)
                oldExtendedSide[i][j] = extendedSide[i][j];
        switch (direction) {
            case CLOCKWISE:
                for (int i = 0; i < extSize; i++)
                    for (int j = 0; j < extSize; j++)
                        extendedSide[i][j] = oldExtendedSide[extSize-1-j][i];
                break;
            case ANTICLOCKWISE:
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

    /* соседние стороны:
    *       - индексы массива ~ индексы сторон;
    *       - элемент массива ~ ряд индексов сторон; формально: представим сторону
    *           как находящуюся прямо перед глазами, тогда ряд индексов является индексами
    *           соседней верхней, правой, нижней и левой сторон;
    */

    private static int[][] nearSidesID = {{4,3,5,2}, {4,2,5,3},
            {4,0,5,1}, {4,1,5,0}, {1,3,0,2}, {1,2,0,3}};

    enum Side{
        FRONT(0), BACK(1), LEFT(2), RIGHT(3), TOP(4), BOTTOM(5);
        private int ID;
        Side(int ID){
            this.ID = ID;
        }
    }

    enum Direction{
        CLOCKWISE, ANTICLOCKWISE
    }

    enum Axis{
        VERTICAL, HORIZONTAL, STRAIGHT
    }

    /* вращение грани без взаимодействия с другими гранями:
    *       - сторона якобы находится перед глазами;
    *       - неважно, каково вращение с постороннего взгляда;
    */
    private void sideRotation(Side side, Direction direction){
        int sideID = side.ID;
        int[][] oldSide = new int[size][size];
        for (int i=0; i < size; i++)
            for (int j=0; j < size; j++)
                oldSide[i][j] = cube[sideID][i][j];
        switch (direction){
            case CLOCKWISE:
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size; j++)
                        cube[sideID][i][j] = oldSide[size-1-j][i];
                break;
            case ANTICLOCKWISE:
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size; j++)
                        cube[sideID][i][j] = oldSide[j][size-1-i];
                break;
        }
    }

    private void sideInversion(Side side){
        int sideID = side.ID;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                cube[sideID][size-1-i][size-1-i] = cube[sideID][i][j];
    }

    public int[][][] getCube(){
        return cube.clone();
    }

    public static void description(){
        System.out.println("цвета:");
        System.out.println("\t0 - white, 1 - yellow, 2 - orange");
        System.out.println("\t3 - red, 4 - blue, 5 - green");
        System.out.println("номера цветов связаны с нумерацией сторон");
        System.out.println("\tFRONT(0), BACK(1), LEFT(2)");
        System.out.println("\tRIGHT(3), TOP(4), BOTTOM(5)");
        System.out.println("оси вращения куба в пространстве:");
        System.out.println("\tVERTICAL, HORIZONTAL, STRAIGHT");
        System.out.println("возможные направления вращения:");
        System.out.println("\tCLOCKWISE, ANTICLOCKWISE");
        System.out.println("количество вращаемых слоев должно быть " +
                "не меньше 1, но меньше размерности куба");
    }
}