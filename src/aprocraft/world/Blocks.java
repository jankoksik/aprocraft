package aprocraft.world;

import aprocraft.io.Texture;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa implementując poszczególne bloki
 */
public abstract class Blocks {
    public static final Texture TEXTURE_PACK = new Texture("./resources/pack.png");

    private static List<Block> blocks = new ArrayList<Block>();

    //podstawowe bloki
    public static final Block AIR = null;
    public static final Block GRASS = new GrassBlock();
    public static final Block DIRT = new DirtBlock();
    public static final Block STONE = new StoneBlock();
    public static final Block COBBLESTONE = new CobblestoneBlock();
    public static final Block BEDROCK = new BedrockBlock();
    public static final Block CLOUD = new CloudBlock();
    public static final Block SAND = new SandBlock();
    public static final Block ICE = new IceBlock();
    public static final Block RED_SANDSTONE = new RedSandstoneBlock();
    public static final Block CACTUS = new CactusBlock();
    public static final Block CONCRETE = new ConcreteBlock();

    //drewno, liscie itp
    public static final Block LOG = new LogBlock(0);
    public static final Block PLANKS = new PlanksBlock(0);
    public static final Block LEAVES = new LeavesBlock(0);

    public static final Block SPRUCE_LOG = new LogBlock(1);
    public static final Block SPRUCE_PLANKS = new PlanksBlock(1);
    public static final Block SPRUCE_LEAVES = new LeavesBlock(1);

    public static final Block BIRCH_LOG = new LogBlock(2);
    public static final Block BIRCH_PLANKS = new PlanksBlock(2);
    public static final Block BIRCH_LEAVES = new LeavesBlock(2);

    //rudy
    public static final Block DIAMOND_ORE = new DiamondOreBlock();
    public static final Block COAL_ORE = new CoalOreBlock();
    public static final Block GOLD_ORE = new GoldOreBlock();
    public static final Block IRON_ORE = new IronOreBlock();
    public static final Block COPPER_ORE = new CopperOreBlock();
    public static final Block RUBY_ORE = new RubyOreBlock();
    public static final Block SAPPHIRE_ORE = new SapphireOreBlock();
    public static final Block EMERALD_ORE = new EmeraldOreBlock();

    //kolorowa welna
    public static final Block WHITE_WOOL = new WoolBlock(0);
    public static final Block GRAY_WOOL = new WoolBlock(1);
    public static final Block BLACK_WOOL = new WoolBlock(2);
    public static final Block RED_WOOL = new WoolBlock(3);
    public static final Block ORANGE_WOOL = new WoolBlock(4);
    public static final Block YELLOW_WOOL = new WoolBlock(5);
    public static final Block GREEN_WOOL = new WoolBlock(6);
    public static final Block BLUE_WOOL = new WoolBlock(7);
    public static final Block PURPLE_WOOL = new WoolBlock(8);

    //specjalne
    public static final Block CRAFTINGBOX = new CraftingboxBlock();

    public static void registerBlock(Block block) {
        blocks.add(block);
    }

    /**
     * Metoda szukająca bloku po id
     *
     * @param id identyfikator bloku
     * @return blok o szukanym id
     */
    public static Block searchByID(int id) {
        for (Block b : blocks)
            if (b.getID() == id)
                return b;

        return null;
    }
}

/**
 * Implementacja bloku - trawa
 */
class GrassBlock extends Block {
    public GrassBlock() {
        super("Grass", 1);
        setDrop(2); //DIRT
    }
}

/**
 * Implementacja bloku - ziemia
 */
class DirtBlock extends Block {
    public DirtBlock() {
        super("Dirt", 2);
    }
}

/**
 * Implementacja bloku - kamień
 */
class StoneBlock extends Block {
    public StoneBlock() {
        super("Stone", 3);
        setDrop(4); //COBBLESTONE
        setDurability(40);
    }
}

/**
 * Implementacja bloku - stłuczony kamień
 */
class CobblestoneBlock extends Block {
    public CobblestoneBlock() {
        super("Cobblestone", 4);
        setDurability(40);
    }
}

/**
 * Implementacja bloku - kłoda
 */
class LogBlock extends Block {
    public LogBlock(int type) {
        super("Log", 17 + type);
        setDurability(30);
    }
}

/**
 * Implementacja bloku - drewnian deska
 */
class PlanksBlock extends Block {
    public PlanksBlock(int type) {
        super("Planks", 20 + type);
        setDurability(28);
    }
}

/**
 * Implementacja bloku - liście
 */
class LeavesBlock extends Block {
    public LeavesBlock(int type) {
        super("Leaves", 23 + type);
        setMaterial(Block.STICKY);
        setDurability(8);
    }
}

/**
 * Implementacja bloku - bedrock
 */
class BedrockBlock extends Block {
    public BedrockBlock() {
        super("Bedrock", 15);
        setDurability(1000);
    }
}

/**
 * Implementacja bloku - chmura
 */
class CloudBlock extends Block {
    public CloudBlock() {
        super("Cloud", 50);
        setMaterial(Block.BOUNCY);
        setDurability(12);
    }
}

/**
 * Implementacja bloku - piasek
 */
class SandBlock extends Block {
    public SandBlock() {
        super("Sand", 10);
    }
}

/**
 * Implementacja bloku - lód
 */
class IceBlock extends Block {
    public IceBlock() {
        super("Ice", 51);
        setMaterial(Block.SLIPPY);
        setDurability(12);
    }
}

/**
 * Implementacja bloku - redstone
 */
class RedSandstoneBlock extends Block {
    public RedSandstoneBlock() {
        super("Red Sandstone", 13);
        setDurability(40);
    }
}

/**
 * Implementacja bloku - kaktus
 */
class CactusBlock extends Block {
    public CactusBlock() {
        super("Cactus", 26);
        setMaterial(Block.HURTING);
        setDurability(15);
    }
}

/**
 * Implementacja bloku - ruda diamentu
 */
class DiamondOreBlock extends Block {
    public DiamondOreBlock() {
        super("Diamond Ore", 37);
        setDurability(80);
    }
}

/**
 * Implementacja bloku - ruda węgla
 */
class CoalOreBlock extends Block {
    public CoalOreBlock() {
        super("Coal Ore", 36);
        setDurability(80);
    }
}

/**
 * Implementacja bloku - ruda złota
 */
class GoldOreBlock extends Block {
    public GoldOreBlock() {
        super("Gold Ore", 33);
        setDurability(80);
    }
}

/**
 * Implementacja bloku - ruda żelaza
 */
class IronOreBlock extends Block {
    public IronOreBlock() {
        super("Iron Ore", 34);
        setDurability(80);
    }
}

/**
 * Implementacja bloku - ruda miedzi
 */
class CopperOreBlock extends Block {
    public CopperOreBlock() {
        super("Copper Ore", 35);
        setDurability(80);
    }
}

/**
 * Implementacja bloku - ruda rubinów
 */
class RubyOreBlock extends Block {
    public RubyOreBlock() {
        super("Ruby Ore", 38);
        setDurability(80);
    }
}

/**
 * Implementacja bloku - ruda szafirów
 */
class SapphireOreBlock extends Block {
    public SapphireOreBlock() {
        super("Sapphire Ore", 39);
        setDurability(80);
    }
}

/**
 * Implementacja bloku - ruda szmaragdu
 */
class EmeraldOreBlock extends Block {
    public EmeraldOreBlock() {
        super("Emerald Ore", 40);
        setDurability(80);
    }
}

/**
 * Implementacja bloku - welna
 */
class WoolBlock extends Block {
    public WoolBlock(int color) {
        super("Wool", 65 + color);
        setDurability(25);
    }
}

/**
 * Implementacja bloku - crafting block
 */
class CraftingboxBlock extends Block {
    public CraftingboxBlock() {
        super("Craftingbox", 81);
        setDurability(30);
    }
}

/**
 * Implementacja bloku - beton
 */
class ConcreteBlock extends Block {
    public ConcreteBlock() {
        super("Concrete", 52);
        setDurability(50);
    }
}
