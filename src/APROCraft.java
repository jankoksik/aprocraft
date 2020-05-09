import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class APROCraft {
    public static final String VERSION = "0.0.1";

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static final float FPS = 60.0f;

    //Game game;
    Chunk c;

    static long window;

    public APROCraft() {
        //game = new Game();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(WIDTH, HEIGHT, "APROCraft v" + VERSION, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);

        GL.createCapabilities();

        //c = new Chunk(0, 0);


    }

    public void update() {
        //game.update();

    }

    public static void setPerspective(float fovy, float aspect, float near, float far) {
        float bottom = -near * (float) Math.tan(fovy / 2);
        float top = -bottom;
        float left = aspect * bottom;
        float right = -left;
        glFrustum(left, right, bottom, top, near, far);
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.1f, 0.2f, 0.7f, 1);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        setPerspective(70.0f, ((float)WIDTH/(float)HEIGHT), 0.1f, 1000.0f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        c.render();

        //game.render();
    }

    public static void main(String[] args) {
        APROCraft aprocraft = new APROCraft();

        long timePrev = System.nanoTime();
        long timer = System.currentTimeMillis();
        double ns = 1_000_000_000 / FPS;

        int frames = 0;
        int ticks = 0;

        while(!glfwWindowShouldClose(window)) {
            long timeNow = System.nanoTime();
            double elapsed = timeNow - timePrev;

            if (elapsed > ns) {
                aprocraft.update();
                ticks ++;
                timePrev += ns;
            } else {
                aprocraft.render();
                frames++;
                glfwSwapBuffers(window);
            }

            if (System.currentTimeMillis() - timer > 1000) {
                System.out.println("FPS: " + frames + " UPS: " + ticks);
                frames = 0;
                ticks = 0;
                timer += 1000;
            }

            glfwPollEvents();
        }

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();

        System.exit(0);
    }
}
