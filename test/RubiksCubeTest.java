import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RubiksCubeTest {

    private RubiksCube rubiksCube = new RubiksCube();

    @Test
    void cubeRotation() {

        // 3-мерные массивы вручную забивались с реальной модели кубика :D
        int[][][] trueCube1 = {{{3,3,3},{3,3,3},{3,3,3}},
                               {{2,2,2},{2,2,2},{2,2,2}},
                               {{0,0,0},{0,0,0},{0,0,0}},
                               {{1,1,1},{1,1,1},{1,1,1}},
                               {{4,4,4},{4,4,4},{4,4,4}},
                               {{5,5,5},{5,5,5},{5,5,5}}};

        int[][][] trueCube2 = {{{2,2,2},{2,2,2},{2,2,2}},
                               {{3,3,3},{3,3,3},{3,3,3}},
                               {{1,1,1},{1,1,1},{1,1,1}},
                               {{0,0,0},{0,0,0},{0,0,0}},
                               {{4,4,4},{4,4,4},{4,4,4}},
                               {{5,5,5},{5,5,5},{5,5,5}}};

        int[][][] trueCube3 = {{{1,1,1},{1,1,1},{1,1,1}},
                               {{0,0,0},{0,0,0},{0,0,0}},
                               {{2,2,2},{2,2,2},{2,2,2}},
                               {{3,3,3},{3,3,3},{3,3,3}},
                               {{5,5,5},{5,5,5},{5,5,5}},
                               {{4,4,4},{4,4,4},{4,4,4}}};

        rubiksCube.cubeRotation(RubiksCube.Axis.VERTICAL, RubiksCube.Direction.CLOCKWISE);
        assertTrue(rubiksCube.equals(trueCube1));
        rubiksCube.setDefaultColors();

        rubiksCube.cubeRotation(RubiksCube.Axis.VERTICAL, RubiksCube.Direction.ANTICLOCKWISE);
        assertTrue(rubiksCube.equals(trueCube2));
        rubiksCube.setDefaultColors();

        rubiksCube.cubeRotation(RubiksCube.Axis.HORIZONTAL, RubiksCube.Direction.CLOCKWISE);
        rubiksCube.cubeRotation(RubiksCube.Axis.HORIZONTAL, RubiksCube.Direction.CLOCKWISE);
        assertTrue(rubiksCube.equals(trueCube3));
        rubiksCube.setDefaultColors();
    }

    @Test
    void layerRotation() {
        int[][][] defaultCube = rubiksCube.getCube(); // дефолтовый
        int[][][] trueCube1 = {{{2,3,0},{3,4,5},{1,1,1}},
                               {{0,0,2},{3,5,0},{1,4,0}},
                               {{1,5,0},{2,0,4},{3,2,4}},
                               {{3,5,2},{1,1,0},{4,3,3}},
                               {{5,4,4},{2,2,0},{5,1,4}},
                               {{3,2,2},{4,3,5},{5,1,5}}};

        // рандомный порядок действий
        rubiksCube.layerRotation(RubiksCube.Side.FRONT, RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.RIGHT, RubiksCube.Direction.ANTICLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.BACK, RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.LEFT,RubiksCube.Direction.ANTICLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.TOP,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.BOTTOM,RubiksCube.Direction.ANTICLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.FRONT,RubiksCube.Direction.CLOCKWISE,2);
        rubiksCube.layerRotation(RubiksCube.Side.RIGHT,RubiksCube.Direction.ANTICLOCKWISE,2);
        rubiksCube.layerRotation(RubiksCube.Side.BACK,RubiksCube.Direction.CLOCKWISE,2);
        rubiksCube.layerRotation(RubiksCube.Side.LEFT,RubiksCube.Direction.ANTICLOCKWISE,2);
        rubiksCube.layerRotation(RubiksCube.Side.TOP,RubiksCube.Direction.CLOCKWISE,2);
        rubiksCube.layerRotation(RubiksCube.Side.BOTTOM,RubiksCube.Direction.ANTICLOCKWISE,2);
        assertTrue(rubiksCube.equals(trueCube1));

        // то, что навертели прошлыми действиями, здесь собирается в дефолтовый куб по одному слою
        rubiksCube.layerRotation(RubiksCube.Side.FRONT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.TOP,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.TOP,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.BOTTOM,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.FRONT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.BOTTOM,RubiksCube.Direction.ANTICLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.RIGHT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.TOP,RubiksCube.Direction.ANTICLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.BACK,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.BACK,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.LEFT,RubiksCube.Direction.ANTICLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.FRONT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.BACK,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.BOTTOM,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.FRONT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.FRONT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.LEFT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.LEFT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.FRONT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.FRONT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.BOTTOM,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.RIGHT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.RIGHT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.TOP,RubiksCube.Direction.ANTICLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.FRONT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.FRONT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.RIGHT,RubiksCube.Direction.CLOCKWISE,1);
        rubiksCube.layerRotation(RubiksCube.Side.RIGHT,RubiksCube.Direction.CLOCKWISE,1);
        assertTrue(rubiksCube.equals(defaultCube));
    }
}