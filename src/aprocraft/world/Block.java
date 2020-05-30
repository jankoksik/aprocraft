package aprocraft.world;

public abstract class Block {
    public static final int NORMAL = 0;
    public static final int BOUNCY = 1;
    public static final int STICKY = 2;
    public static final int SLIPPY = 3;

    private int id;
    private String name;

    private RGB color;
    private int size;
    private int coordX, coordY;

    protected float durability;
    protected int material;

    private float[] colorData, lightData;

    public Block(String name, int id) {
        this.name = name;

        this.id = id;

        size = 1;
        durability = 1f;
        material = NORMAL;

        color = new RGB(1, 1, 1);

        coordX = (id-1) % 16;
        coordY = (id-1) / 16;

        float tx = coordX / 16.0f;
        float ty = coordY / 16.0f;
        float s = 1f / 16.0f;

        colorData = new float[]{
                tx, ty, tx + s, ty, tx + s, ty + s, tx, ty + s,
                tx, ty, tx + s, ty, tx + s, ty + s, tx, ty + s,
                tx, ty, tx, ty + s, tx + s, ty + s, tx + s, ty,
                tx, ty, tx, ty + s, tx + s, ty + s, tx + s, ty,
                tx, ty, tx + s, ty, tx + s, ty + s, tx, ty + s,
                tx, ty, tx + s, ty, tx + s, ty + s, tx, ty + s
        };

        lightData = new float[]{
                //back
                color.r * 0.65f, color.g * 0.65f, color.b * 0.65f, 1,
                color.r * 0.65f, color.g * 0.65f, color.b * 0.65f, 1,
                color.r * 0.65f, color.g * 0.65f, color.b * 0.65f, 1,
                color.r * 0.65f, color.g * 0.65f, color.b * 0.65f, 1,

                //front
                color.r * 0.75f, color.g * 0.75f, color.b * 0.75f, 1,
                color.r * 0.75f, color.g * 0.75f, color.b * 0.75f, 1,
                color.r * 0.75f, color.g * 0.75f, color.b * 0.75f, 1,
                color.r * 0.75f, color.g * 0.75f, color.b * 0.75f, 1,

                //left
                color.r * 0.85f, color.g * 0.85f, color.b * 0.85f, 1,
                color.r * 0.85f, color.g * 0.85f, color.b * 0.85f, 1,
                color.r * 0.85f, color.g * 0.85f, color.b * 0.85f, 1,
                color.r * 0.85f, color.g * 0.85f, color.b * 0.85f, 1,

                //right
                color.r * 0.5f, color.g * 0.5f, color.b * 0.5f, 1,
                color.r * 0.5f, color.g * 0.5f, color.b * 0.5f, 1,
                color.r * 0.5f, color.g * 0.5f, color.b * 0.5f, 1,
                color.r * 0.5f, color.g * 0.5f, color.b * 0.5f, 1,

                //bottom
                color.r * 0.4f, color.g * 0.4f, color.b * 0.4f, 1,
                color.r * 0.4f, color.g * 0.4f, color.b * 0.4f, 1,
                color.r * 0.4f, color.g * 0.4f, color.b * 0.4f, 1,
                color.r * 0.4f, color.g * 0.4f, color.b * 0.4f, 1,

                //top
                color.r * 0.95f, color.g * 0.95f, color.b * 0.95f, 1,
                color.r * 0.95f, color.g * 0.95f, color.b * 0.95f, 1,
                color.r * 0.95f, color.g * 0.95f, color.b * 0.95f, 1,
                color.r * 0.95f, color.g * 0.95f, color.b * 0.95f, 1
        };

        Blocks.registerBlock(this);
    }

    public float[] getData(float x, float y, float z) {
        return new float[]{
                x, y, z,
                x + size, y, z,
                x + size, y + size, z,
                x, y + size, z,

                x, y, z + size,
                x + size, y, z + size,
                x + size, y + size, z + size,
                x, y + size, z + size,

                x, y, z,
                x, y + size, z,
                x, y + size, z + size,
                x, y, z + size,

                x + size, y, z,
                x + size, y + size, z,
                x + size, y + size, z + size,
                x + size, y, z + size,

                x, y, z,
                x + size, y, z,
                x + size, y, z + size,
                x, y, z + size,

                x, y + size, z,
                x + size, y + size, z,
                x + size, y + size, z + size,
                x, y + size, z + size
        };
    }

    public float[] getColorData() {
        return colorData;
    }

    public float[] getLightData() {
        return lightData;
    }

    public RGB getColor() {
        return color;
    }

    public int getMaterial() {
        return material;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
