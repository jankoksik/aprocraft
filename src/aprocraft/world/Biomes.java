package aprocraft.world;

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
    public static final Biome CANYON = new CanyonBiome();
    //MOUNTAINS, JUNGLE

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

        addStructure(Structures.OAK_TREE, 1);
        addStructure(Structures.BIRCH_TREE, 3);

        addOre(Blocks.COAL_ORE, 15);
        addOre(Blocks.COPPER_ORE, 10);
        addOre(Blocks.IRON_ORE, 7);
        addOre(Blocks.GOLD_ORE, 4);
        addOre(Blocks.RUBY_ORE, 3);
        addOre(Blocks.SAPPHIRE_ORE, 3);
        addOre(Blocks.EMERALD_ORE, 3);
        addOre(Blocks.DIAMOND_ORE, 3);

    }
}

class ForestBiome extends Biome {
    public ForestBiome() {
        super("Forest", 5);
        //setAmplitude(4);
        //setOctave(24);
        addStructure(Structures.OAK_TREE, 10);
        addStructure(Structures.SPRUCE_TREE, 15);

        addOre(Blocks.COAL_ORE, 15);
        addOre(Blocks.COPPER_ORE, 10);
        addOre(Blocks.IRON_ORE, 7);
        addOre(Blocks.GOLD_ORE, 4);
        addOre(Blocks.RUBY_ORE, 3);
        addOre(Blocks.SAPPHIRE_ORE, 3);
        addOre(Blocks.EMERALD_ORE, 3);
        addOre(Blocks.DIAMOND_ORE, 3);
    }
}

class DesertBiome extends Biome {
    public DesertBiome() {
        super("Desert", 5);
        //setAmplitude(8);
        //setOctave(28);
        setLayers(Blocks.SAND, Blocks.SAND, Blocks.STONE);
        addStructure(Structures.CACTUS, 6);

        addOre(Blocks.COAL_ORE, 15);
        addOre(Blocks.COPPER_ORE, 10);
        addOre(Blocks.IRON_ORE, 7);
        addOre(Blocks.GOLD_ORE, 4);
        addOre(Blocks.RUBY_ORE, 3);
        addOre(Blocks.SAPPHIRE_ORE, 3);
        addOre(Blocks.EMERALD_ORE, 3);
        addOre(Blocks.DIAMOND_ORE, 3);
    }
}

class PlainsBiome extends Biome {
    public PlainsBiome() {
        super("Plains", 10);

        addOre(Blocks.COAL_ORE, 15);
        addOre(Blocks.COPPER_ORE, 10);
        addOre(Blocks.IRON_ORE, 7);
        addOre(Blocks.GOLD_ORE, 4);
        addOre(Blocks.RUBY_ORE, 3);
        addOre(Blocks.SAPPHIRE_ORE, 3);
        addOre(Blocks.EMERALD_ORE, 3);
        addOre(Blocks.DIAMOND_ORE, 3);
    }

}

class IcebergBiome extends Biome {
    public IcebergBiome() {
        super("Iceberg", 2);
        setLayers(Blocks.ICE, Blocks.STONE, Blocks.STONE);

        addOre(Blocks.COAL_ORE, 15);
        addOre(Blocks.COPPER_ORE, 10);
        addOre(Blocks.IRON_ORE, 7);
        addOre(Blocks.GOLD_ORE, 4);
        addOre(Blocks.RUBY_ORE, 3);
        addOre(Blocks.SAPPHIRE_ORE, 3);
        addOre(Blocks.EMERALD_ORE, 3);
        addOre(Blocks.DIAMOND_ORE, 3);
    }
}

class CanyonBiome extends Biome {
    public CanyonBiome() {
        super("Canyon", 2);
        setLayers(Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE);
        setAmplitude(30);
        setOctave(40);
    }
}
