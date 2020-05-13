import java.util.Random;

public abstract class Block {
    private static int CURRENT_ID = 0;

    public static final int NORMAL = 0;
    public static final int BOUNCY = 1;
    public static final int STICKY = 2;
    public static final int SLIPPY = 3;

    private int id;
    private String name;
    private RGB color;
    private float size;
    protected float durability;
    protected int material;

    private float[] colorData;

    public Block(String name, RGB color) {
        id = CURRENT_ID;
        CURRENT_ID ++;

        this.name = name;

        this.color = color;
        size = 1f;
        durability = 1f;
        material = NORMAL;

        colorData = new float[] {
                //back
                color.r*0.7f, color.g*0.7f, color.b*0.7f, 1,
                color.r*0.7f, color.g*0.7f, color.b*0.7f, 1,
                color.r*0.7f, color.g*0.7f, color.b*0.7f, 1,
                color.r*0.7f, color.g*0.7f, color.b*0.7f, 1,

                //right
                color.r*0.6f, color.g*0.6f, color.b*0.6f, 1,
                color.r*0.6f, color.g*0.6f, color.b*0.6f, 1,
                color.r*0.6f, color.g*0.6f, color.b*0.6f, 1,
                color.r*0.6f, color.g*0.6f, color.b*0.6f, 1,

                //left
                color.r*0.9f, color.g*0.9f, color.b*0.9f, 1,
                color.r*0.9f, color.g*0.9f, color.b*0.9f, 1,
                color.r*0.9f, color.g*0.9f, color.b*0.9f, 1,
                color.r*0.9f, color.g*0.9f, color.b*0.9f, 1,

                //front
                color.r*0.8f, color.g*0.8f, color.b*0.8f, 1,
                color.r*0.8f, color.g*0.8f, color.b*0.8f, 1,
                color.r*0.8f, color.g*0.8f, color.b*0.8f, 1,
                color.r*0.8f, color.g*0.8f, color.b*0.8f, 1,

                //bottom
                color.r*0.5f, color.g*0.5f, color.b*0.5f, 1,
                color.r*0.5f, color.g*0.5f, color.b*0.5f, 1,
                color.r*0.5f, color.g*0.5f, color.b*0.5f, 1,
                color.r*0.5f, color.g*0.5f, color.b*0.5f, 1,

                //top
                color.r, color.g, color.b, 1,
                color.r, color.g, color.b, 1,
                color.r, color.g, color.b, 1,
                color.r, color.g, color.b, 1
        };

        Blocks.registerBlock(this);

        /*Random r = new Random();

        for(int i = 0; i < colorData.length; i ++)
            colorData[i] *= (float)(r.nextInt(20)+80)/100.0f;*/
    }

    public float[] getData(float x, float y, float z) {
        return new float[] {
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
