import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class APROCraft {
    public static final String VERSION = "0.0.1";

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static final float FPS = 60.0f;

    public void update() {

    }

    public void render() {

    }

    public static void main(String[] args) {
        APROCraft game = new APROCraft();

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("APROCraft v" + VERSION);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        long timePrev = System.nanoTime();
        long timer = System.currentTimeMillis();
        double ns = 1_000_000_000 / FPS;

        int frames = 0;
        int ticks = 0;

        while(!Display.isCloseRequested()) {
            long timeNow = System.nanoTime();
            double elapsed = timeNow - timePrev;

            if (elapsed > ns) {
                game.update();
                ticks ++;
                timePrev += ns;
            } else {
                game.render();
                frames++;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                System.out.println("FPS: " + frames + " UPS: " + ticks);
                frames = 0;
                ticks = 0;
                timer += 1000;
            }

            Display.update();
        }

        Display.destroy();
        System.exit(0);
    }
}
