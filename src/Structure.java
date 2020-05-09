public class Structure {
    public static final int MAX_SIZE = 8;

    private Block[][][] blocks;

    public Structure(String filename) {
        blocks = new Block[MAX_SIZE][MAX_SIZE][MAX_SIZE];

        //TODO: wczytywanie do tablicy z pliku
    }
}
