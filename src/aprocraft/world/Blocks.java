package aprocraft.world;

import java.util.ArrayList;
import java.util.List;

import aprocraft.io.*;

public abstract class Blocks {
    public static final Texture TEXTURE_PACK = new Texture("./resources/pack3.png");

    private static List<Block> blocks = new ArrayList<Block>();

    //podstawowe bloki
    public static final Block AIR = null;
    public static final Block GRASS = new GrassBlock();
    public static final Block DIRT = new DirtBlock();
    public static final Block STONE = new StoneBlock();
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
        super("Grass", new RGB(0.45f, 0.9f, 0.3f), 0, 0);
    }
    //new aprocraft.world.RGB(0.35f, 1f, 0.15f)
}

class DirtBlock extends Block {
    public DirtBlock() {
        super("Dirt", new RGB(0.4f, 0.3f, 0.1f), 2, 0);
    }
}

class StoneBlock extends Block {
    public StoneBlock() {
        super("Stone", new RGB(0.3f, 0.3f, 0.3f), 1, 0);
    }
}

class LogBlock extends Block {
    public LogBlock() {
        super("Oak Log", new RGB(0.3f, 0.2f, 0.0f), 4, 1);
    }
}

class PlanksBlock extends Block {
    public PlanksBlock() {
        super("Planks", new RGB(0.7f, 0.6f, 0.3f), 4, 0);
    }
}

class LeavesBlock extends Block {
    public LeavesBlock() {
        super("Oak Leaves", new RGB(0.3f, 0.7f, 0.3f), 5, 3);
        material = Block.STICKY;
    }
}

class DarkLeavesBlock extends Block {
    public DarkLeavesBlock() {
        super("Spruce Leaves", new RGB(0.15f, 0.5f, 0.25f), 5, 3);
        material = Block.STICKY;
    }
}

class BedrockBlock extends Block {
    public BedrockBlock() {
        super("Bedrock", new RGB(0.1f, 0.1f, 0.1f), 1, 1);
    }
}

class CloudBlock extends Block {
    public CloudBlock() {
        super("Cloud", new RGB(1f, 1f, 1f), 2, 4);
        material = Block.BOUNCY;
    }
}

class SandBlock extends Block {
    public SandBlock() {
        super("Sand", new RGB(1f, 0.9f, 0.3f), 2, 1);
        //material = aprocraft.world.Block.STICKY;
    }
}

class IceBlock extends Block {
    public IceBlock() {
        super("Ice", new RGB(0.6f, 0.7f, 1f), 1, 1);
        material = Block.SLIPPY;
    }
}

class RedSandstoneBlock extends Block {
    public RedSandstoneBlock() {
        super("Red Sandstone", new RGB(0.9f, 0.4f, 0.1f), 1, 0);
        //material = aprocraft.world.Block.STICKY;
    }
}

class CactusBlock extends Block {
    public CactusBlock() {
        super("Cactus", new RGB(0.1f, 0.6f, 0.1f), 5, 4);
        material = Block.STICKY;
    }
}

class DiamondOreBlock extends Block {
    public DiamondOreBlock() {
        super("Diamond Ore", new RGB(1, 1, 1), 2, 3);
    }
}
