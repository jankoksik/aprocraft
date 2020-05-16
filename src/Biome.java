import java.util.List;

public abstract class Biome {
    public static final int DEFAULT = 0;
    public static final int FOREST = 1;
    public static final int DESERT = 2;
    public static final int PLAINS = 3;
    public static final int MOUNTAINS = 4;

    private List<Structures> vegetation;
    private List<Structures> buildings;
}
