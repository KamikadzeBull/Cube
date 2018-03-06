public class Test {

    public static void main(String[] args) {
        RubiksCube cube3 = new RubiksCube();
        RubiksCube cube5 = new RubiksCube(5);

        cube5.cubeRotation("clockwise");
        cube5.cubeRotation("invert");
        cube5.show();
    }
}