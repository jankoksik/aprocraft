public class Biomes {
    public static final Biome DEFAULT = new DefaultBiome();
    public static final Biome FOREST = new ForestBiome();
    public static final Biome DESERT = new DesertBiome();
    //public static final int PLAINS = 3;
    //public static final int MOUNTAINS = 4;
}

class DefaultBiome extends Biome {
    public DefaultBiome() {
        super("Default");
        addStructure(Structures.OAK_TREE, 5);
    }
}

class ForestBiome extends Biome {
    public ForestBiome() {
        super("Forest");
        setAmplitude(4);
        setOctave(24);
        addStructure(Structures.OAK_TREE, 20);
    }
}

class DesertBiome extends Biome {
    public DesertBiome() {
        super("Desert");
        setAmplitude(8);
        setOctave(28);
        setLayers(Blocks.SAND, Blocks.SAND, Blocks.STONE);
        addStructure(Structures.CACTUS, 6);
    }
}
