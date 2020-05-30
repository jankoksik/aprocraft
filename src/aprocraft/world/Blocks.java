package aprocraft.world;

import java.util.ArrayList;
import java.util.List;

import aprocraft.io.*;

public abstract class Blocks {
    public static final Texture TEXTURE_PACK = new Texture("./resources/pack.png");

    private static List<Block> blocks = new ArrayList<Block>();

    //podstawowe bloki
    public static final Block AIR = null;
    public static final Block GRASS = new GrassBlock();
    public static final Block DIRT = new DirtBlock();
    public static final Block STONE = new StoneBlock();
    public static final Block COBBLESTONE = new CobblestoneBlock();
    public static final Block LOG = new LogBlock();
    public static final Block PLANKS = new PlanksBlock();
    public static final Block LEAVES = new LeavesBlock();
    public static final Block DARK_LEAVES = new DarkLeavesBlock();
    public static final Block BEDROCK = new BedrockBlock();
    public static final Block CLOUD = new CloudBlock();
    public static final Block SAND = new SandBlock();
    public static final Block ICE = new IceBlock();
    public static final Block RED_SANDSTONE = new RedSandstoneBlock();
    public static final Block CACTUS = new CactusBlock();

    //rudy
    public static final Block DIAMOND_ORE = new DiamondOreBlock();
    public static final Block COAL_ORE = new CoalOreBlock();
    public static final Block GOLD_ORE = new GoldOreBlock();
    public static final Block IRON_ORE = new IronOreBlock();

    public static void registerBlock(Block block) {
        blocks.add(block);
    }

    public static Block searchByID(int id) {
        for(Block b : blocks)
            if(b.getID() == id)
                return b;

        return null;
    }
}

class GrassBlock extends Block {
    public GrassBlock() {
        super("Grass", 1);
    }
}

class DirtBlock extends Block {
    public DirtBlock() {
        super("Dirt", 2);
    }
}

class StoneBlock extends Block {
    public StoneBlock() {
        super("Stone", 3);
    }
}

class CobblestoneBlock extends Block {
    public CobblestoneBlock() {
        super("Cobblestone", 4);
    }
}

class LogBlock extends Block {
    public LogBlock() {
        super("Oak Log", 17);
    }
}

class PlanksBlock extends Block {
    public PlanksBlock() {
        super("Planks", 20);
    }
}

class LeavesBlock extends Block {
    public LeavesBlock() {
        super("Oak Leaves", 23);
        material = Block.STICKY;
    }
}

class DarkLeavesBlock extends Block {
    public DarkLeavesBlock() {
        super("Spruce Leaves", 24);
        material = Block.STICKY;
    }
}

class BedrockBlock extends Block {
    public BedrockBlock() {
        super("Bedrock", 15);
    }
}

class CloudBlock extends Block {
    public CloudBlock() {
        super("Cloud", 50);
        material = Block.BOUNCY;
    }
}

class SandBlock extends Block {
    public SandBlock() {
        super("Sand", 10);
    }
}

class IceBlock extends Block {
    public IceBlock() {
        super("Ice", 51);
        material = Block.SLIPPY;
    }
}

class RedSandstoneBlock extends Block {
    public RedSandstoneBlock() {
        super("Red Sandstone", 13);
    }
}

class CactusBlock extends Block {
    public CactusBlock() {
        super("Cactus", 26);
        material = Block.STICKY;
    }
}

class DiamondOreBlock extends Block {
    public DiamondOreBlock() {
        super("Diamond Ore", 37);
    }
}

class CoalOreBlock extends Block {
    public CoalOreBlock() {
        super("Coal Ore", 36);
    }
}

class GoldOreBlock extends Block {
    public GoldOreBlock() {
        super("Gold Ore", 33);
    }
}

class IronOreBlock extends Block {
    public IronOreBlock() {
        super("Iron Ore", 34);
    }
}
