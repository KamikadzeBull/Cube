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

        rubiksCube.cubeRotation("clockwise");
        assertArrayEquals(trueCube1, rubiksCube.getCube());
        rubiksCube.setDefaultColors();

        rubiksCube.cubeRotation("anticlockwise");
        assertArrayEquals(trueCube2, rubiksCube.getCube());
        rubiksCube.setDefaultColors();

        rubiksCube.cubeRotation("invert");
        assertArrayEquals(trueCube3, rubiksCube.getCube());
        rubiksCube.setDefaultColors();

        try{
            rubiksCube.cubeRotation("otherString");
            fail("expected IllegalArgumentException");
        }
        catch (IllegalArgumentException exception){
            assertEquals("wrong direction", exception.getMessage());
        }
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
        rubiksCube.layerRotation("front","clockwise",1);
        rubiksCube.layerRotation("right","anticlockwise",1);
        rubiksCube.layerRotation("back","clockwise",1);
        rubiksCube.layerRotation("left","anticlockwise",1);
        rubiksCube.layerRotation("top","clockwise",1);
        rubiksCube.layerRotation("bottom","anticlockwise",1);
        rubiksCube.layerRotation("front","clockwise",2);
        rubiksCube.layerRotation("right","anticlockwise",2);
        rubiksCube.layerRotation("back","clockwise",2);
        rubiksCube.layerRotation("left","anticlockwise",2);
        rubiksCube.layerRotation("top","clockwise",2);
        rubiksCube.layerRotation("bottom","anticlockwise",2);
        assertArrayEquals(trueCube1, rubiksCube.getCube());

        // то, что навертели прошлыми действиями, здесь собирается в дефолтовый куб по одному слою
        rubiksCube.layerRotation("front","clockwise",1);
        rubiksCube.layerRotation("top","clockwise",1);
        rubiksCube.layerRotation("top","clockwise",1);
        rubiksCube.layerRotation("bottom","clockwise",1);
        rubiksCube.layerRotation("front","clockwise",1);
        rubiksCube.layerRotation("bottom","anticlockwise",1);
        rubiksCube.layerRotation("right","clockwise",1);
        rubiksCube.layerRotation("top","anticlockwise",1);
        rubiksCube.layerRotation("back","clockwise",1);
        rubiksCube.layerRotation("back","clockwise",1);
        rubiksCube.layerRotation("left","anticlockwise",1);
        rubiksCube.layerRotation("front","clockwise",1);
        rubiksCube.layerRotation("back","clockwise",1);
        rubiksCube.layerRotation("bottom","clockwise",1);
        rubiksCube.layerRotation("front","clockwise",1);
        rubiksCube.layerRotation("front","clockwise",1);
        rubiksCube.layerRotation("left","clockwise",1);
        rubiksCube.layerRotation("left","clockwise",1);
        rubiksCube.layerRotation("front","clockwise",1);
        rubiksCube.layerRotation("front","clockwise",1);
        rubiksCube.layerRotation("bottom","clockwise",1);
        rubiksCube.layerRotation("right","clockwise",1);
        rubiksCube.layerRotation("right","clockwise",1);
        rubiksCube.layerRotation("top","anticlockwise",1);
        rubiksCube.layerRotation("front","clockwise",1);
        rubiksCube.layerRotation("front","clockwise",1);
        rubiksCube.layerRotation("right","clockwise",1);
        rubiksCube.layerRotation("right","clockwise",1);
        assertArrayEquals(defaultCube, rubiksCube.getCube());

        try{
            rubiksCube.layerRotation("otherString","clockwise",2);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException exception){
            assertEquals("wrong name of side", exception.getMessage());
        }

        try{
            rubiksCube.layerRotation("left","otherString",2);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException exception){
            assertEquals("wrong direction", exception.getMessage());
        }

        try{
            rubiksCube.layerRotation("left","clockwise",0);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException exception){
            assertEquals("1 <= amount < size", exception.getMessage());
        }
    }
}