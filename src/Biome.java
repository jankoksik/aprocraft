import java.util.List;

public abstract class Biome {
    public static final int DEFAULT = 0;
    public static final int FOREST = 1;
    public static final int DESERT = 2;
    public static final int PLAINS = 3;
    public static final int MOUNTAINS = 4;

    private String name;

    private int amplitude;
    private int octave;

    private List<Block> layer;
    private List<Block> ores;

    private List<Structure> vegetation;
    private List<Structure> buildings;

    private float vegetationDestiny;
    private float buildingsDestiny;

    public Biome(String name) {
        this.name = name;
    }
}
