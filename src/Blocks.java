import java.util.ArrayList;
import java.util.List;

public abstract class Blocks {
    private static List<Block> blocks = new ArrayList<Block>();

    public static final Block AIR = null;
    public static final Block GRASS = new GrassBlock();
    public static final Block DIRT = new DirtBlock();
    public static final Block STONE = new StoneBlock();
    public static final Block LOG = new LogBlock();
    public static final Block LEAVES = new LeavesBlock();
    public static final Block DARK_LEAVES = new DarkLeavesBlock();
    public static final Block BEDROCK = new BedrockBlock();
    public static final Block CLOUD = new CloudBlock();
    public static final Block SAND = new SandBlock();
    public static final Block ICE = new IceBlock();
    public static final Block RED_SANDSTONE = new RedSandstoneBlock();
    public static final Block CACTUS = new CactusBlock();

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
        super("Grass", new RGB(0.35f, 1f, 0.15f), 1, 0);
    }
}

class DirtBlock extends Block {
    public DirtBlock() {
        super("Dirt", new RGB(0.4f, 0.3f, 0.1f), 3, 0);
    }
}

class StoneBlock extends Block {
    public StoneBlock() {
        super("Stone", new RGB(0.3f, 0.3f, 0.3f), 0, 0);
    }
}

class LogBlock extends Block {
    public LogBlock() {
        super("Oak Log", new RGB(0.3f, 0.2f, 0.0f), 4, 1);
    }
}

class LeavesBlock extends Block {
    public LeavesBlock() {
        super("Oak Leaves", new RGB(0.1f, 0.6f, 0.1f), 9, 1);
        material = Block.STICKY;
    }
}

class DarkLeavesBlock extends Block {
    public DarkLeavesBlock() {
        super("Spruce Leaves", new RGB(0.1f, 0.4f, 0.2f), 9, 1);
        material = Block.STICKY;
    }
}

class BedrockBlock extends Block {
    public BedrockBlock() {
        super("Bedrock", new RGB(0.1f, 0.1f, 0.1f), 13, 0);
    }
}

class CloudBlock extends Block {
    public CloudBlock() {
        super("Cloud", new RGB(1f, 1f, 1f), 1, 4);
        material = Block.BOUNCY;
    }
}

class SandBlock extends Block {
    public SandBlock() {
        super("Sand", new RGB(1f, 0.9f, 0.3f), 14, 0);
        //material = Block.STICKY;
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
        super("Red Sandstone", new RGB(0.9f, 0.4f, 0.1f), 0, 0);
        //material = Block.STICKY;
    }
}

class CactusBlock extends Block {
    public CactusBlock() {
        super("Cactus", new RGB(0.1f, 0.6f, 0.1f), 7, 8);
        material = Block.STICKY;
    }
}

