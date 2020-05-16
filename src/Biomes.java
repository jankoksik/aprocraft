import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Biomes {
    private static List<Biome> biomes = new ArrayList<Biome>();
    private static int totalSum = 0;

    public static final Biome DEFAULT = new DefaultBiome();
    public static final Biome FOREST = new ForestBiome();
    public static final Biome DESERT = new DesertBiome();
    public static final Biome PLAINS = new PlainsBiome();
    public static final Biome ICEBERG = new IcebergBiome();
    //public static final int MOUNTAINS = 4;

    public static void registerBiome(Biome b) {
        biomes.add(b);
        totalSum += b.getOccurrence();
    }

    public static Biome choose() {

        int index = new Random().nextInt(totalSum);
        int sum = 0;
        int i=0;
        while(sum < index ) {
            sum = sum + biomes.get(i++).getOccurrence();
        }

        return biomes.get(Math.max(0,i-1));
    }
}

class DefaultBiome extends Biome {
    public DefaultBiome() {
        super("Default", 10);
        addStructure(Structures.OAK_TREE, 3);
    }
}

class ForestBiome extends Biome {
    public ForestBiome() {
        super("Forest", 5);
        setAmplitude(4);
        setOctave(24);
        addStructure(Structures.OAK_TREE, 10);
        addStructure(Structures.SPRUCE_TREE, 15);
    }
}

class DesertBiome extends Biome {
    public DesertBiome() {
        super("Desert", 5);
        setAmplitude(8);
        setOctave(28);
        setLayers(Blocks.SAND, Blocks.SAND, Blocks.STONE);
        addStructure(Structures.CACTUS, 6);
    }
}

class PlainsBiome extends Biome {
    public PlainsBiome() {
        super("Plains", 10);
    }
}

class IcebergBiome extends Biome {
    public IcebergBiome() {
        super("Iceberg", 2);
        setLayers(Blocks.ICE, Blocks.STONE, Blocks.STONE);
    }
}
