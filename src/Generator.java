import java.util.Random;

public class Generator {
    private Random rand;

    private long seed;
    private int octave;
    private float amplitude;

    public Generator(long seed, int octave, float amplitude) {
        this.seed = seed;
        this.octave = octave;
        this.amplitude = amplitude;

        rand = new Random();
    }

    public float getHeight(int x, int z) {
        int xmin = (int)((double)x / octave);
        int xmax = xmin + 1;
        int zmin = (int)((double)z / octave);
        int zmax = zmin + 1;

        float tx = (x-xmin*octave)/octave;
        float tz = (z-zmin*octave)/octave;

        float tmp1 = interpolate((float)getNoise(xmin, zmin), (float)getNoise(xmax, zmin), tx);
        float tmp2 = interpolate((float)getNoise(xmin, zmax), (float)getNoise(xmax,zmax), tx);

        return interpolate(tmp1, tmp2, tz)*amplitude;
    }

    private float interpolate(float a, float b, float t) {
        float f = (float) (1.0f - Math.cos((float)(t * Math.PI))) * 0.5f;
        return a * (1.0f - f) + b * f;
    }

    private double getNoise(float x, float z) {
        rand.setSeed((long) ((Math.sin(x + Math.cos(z)) + Math.tan(seed)) * 10000));
        return rand.nextDouble();
    }
}
