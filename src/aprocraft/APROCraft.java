package aprocraft;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import aprocraft.eq.Crafting;
import aprocraft.eq.Inventory;
import aprocraft.eq.Item;
import aprocraft.io.SaveNReadJson;
import aprocraft.player.Player;
import org.joml.Math;
import org.json.simple.parser.ParseException;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import aprocraft.world.World;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class APROCraft {
    public static final String VERSION = "0.5.0 alpha";
    public static final String GAME_NAME = "APROCraft";
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;
    public static final float FPS = 60.0f;

    public APROCraft() {
        if (glfwInit() != true) {
            System.err.println("GLFW sie zepsul");
            System.exit(1);
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        long win = glfwCreateWindow(WIDTH, HEIGHT, GAME_NAME + " v" + VERSION, 0, 0);

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(win, (vidmode.width() - WIDTH) / 2, (vidmode.height() - HEIGHT) / 2);

        //Przykladowy ekwipunek z proba przeciazenia
        TestLogLabel("Eq add test");
        Inventory eqi = new Inventory(3, 10,1);
        if (eqi.addItem(0)) {
            System.out.println("dodano pomyslnie item");
        } else {
            System.out.println("blad");
        }
        // if(eqi.addItem(0)){System.out.println("dodano pomyslnie item");}else {System.out.println("blad");}
        // if(eqi.addItem(0)){System.out.println("dodano pomyslnie item");}else {System.out.println("blad");}
        //if(eqi.addItem(1)){System.out.println("dodano pomyslnie item");}else {System.out.println("blad");}
        // if(eqi.addItem(2)){System.out.println("dodano pomyslnie item");}else {System.out.println("blad");}
        // if(eqi.addItem(0)){System.out.println("dodano pomyslnie item");}else {System.out.println("blad");}
        // if(eqi.addItem(1)){System.out.println("dodano pomyslnie item");}else {System.out.println("blad");}
        // if(eqi.addItem(0)){System.out.println("dodano pomyslnie item");}else {System.out.println("blad");}
        // if(eqi.addItem(3)){System.out.println("dodano pomyslnie item");}else {System.out.println("blad");}

        TestLogLabel("Eq add many");
        if (eqi.addItem(1, 7, true)) {
            System.out.println("dodano pomyslnie item");
        } else {
            System.out.println("blad");
        }

        // testowe wypisanie eq

        TestLogLabel("Eq content");
        for (Item i : eqi.getEq()) {
            System.out.println(i.getId() + " : " + i.getSize());
        }
        TestLogLabel("Controls read");

        try {
            HashMap<String, Integer> cntrl = SaveNReadJson.readControls("Controls");
            SaveNReadJson.applyCOntrols(cntrl);
            for (Map.Entry<String, Integer> entry : cntrl.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println(key + " : " + value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TestLogLabel("recipe");
        Crafting craftingTable = new Crafting(3, 3, "TestRecip");
        craftingTable.LoadRecipes();
        craftingTable.PlaceItemInCrafting(0, 0, 1, eqi);
        craftingTable.PlaceItemInCrafting(0, 1, 1, eqi);
        int res = craftingTable.ShowPatternMatchinResult();
        if (res != -1) {
            craftingTable.Craft(eqi);
            System.out.println("zcraftowano : " + res);
            System.out.println("zcraftowales patyk z ziemi :-/ ");
        } else {
            System.out.println("nie znaleziono craftingu ");
        }


        glfwShowWindow(win);
        glfwMakeContextCurrent(win);

        glfwSwapInterval(1); //limit 60fps

        GL.createCapabilities();

        World stefan = new World();
        Player player = new Player(win, stefan);

        long timePrev = System.nanoTime();
        long timer = System.currentTimeMillis();
        double ns = 1_000_000_000 / FPS;

        int frames = 0;
        int ticks = 0;

        glEnable(GL_DEPTH_TEST);


        while (glfwWindowShouldClose(win) != true) {
            glfwPollEvents();

            long timeNow = System.nanoTime();
            double elapsed = timeNow - timePrev;

            if (elapsed > ns) {
                if (glfwGetKey(win, GLFW_KEY_ESCAPE) == GL_TRUE)
                    glfwSetWindowShouldClose(win, true);

                player.update();
                stefan.update(player);

                ticks++;
                timePrev += ns;
            } else {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


                //glClearColor(0.25f*stefan.getSkyColorMultiplier(), 0.4f*stefan.getSkyColorMultiplier(), 0.8f*stefan.getSkyColorMultiplier(), 1);
                glClearColor(0.25f, 0.4f, 0.8f, 1);


                glMatrixMode(GL_PROJECTION);
                glLoadIdentity();
                setPerspective(70.0f, ((float) WIDTH / (float) HEIGHT), 0.3f, 1000.0f);
                glMatrixMode(GL_MODELVIEW);
                glLoadIdentity();

                player.updateCamera();

                //shader.bind();
                stefan.render(player);

                glDisable(GL_DEPTH_TEST);
                glMatrixMode(GL_PROJECTION);
                glPushMatrix();
                glLoadIdentity();
                glOrtho(0, WIDTH, 0, HEIGHT, -1, 1);
                glMatrixMode(GL_MODELVIEW);
                glPushMatrix();
                glLoadIdentity();

                //player.addItem(6,256,true);

                player.getGui().RenderHealth(player.getHp());
                player.getGui().RenderQAB();
                player.getGui().RenderEq() ;

                //aprocraft.eq.GUI.Test();
                //glColor3f(1f, .5f, 1f);

                /*glBegin(GL_QUADS);
                glVertex2f(100, 100);
                glVertex2f(100 + 200, 100);
                glVertex2f(100 + 200, 100 + 200);
                glVertex2f(100, 100 + 200);
                glEnd();*/

                glMatrixMode(GL_PROJECTION);
                glPopMatrix();
                glMatrixMode(GL_MODELVIEW);
                glPopMatrix();
                glEnable(GL_DEPTH_TEST);

                frames++;
                glfwSwapBuffers(win);
            }

            if (System.currentTimeMillis() - timer > 1000) {
                //System.out.println("FPS: " + frames + " UPS: " + ticks);
                glfwSetWindowTitle(win, "aprocraft.APROCraft v" + VERSION + "     FPS: " + frames);
                frames = 0;
                ticks = 0;
                timer += 1000;
            }
        }

        glfwTerminate();
    }

    public static void setPerspective(float fovy, float aspect, float near, float far) {
        float bottom = -near * Math.tan(fovy / 2);
        float top = -bottom;
        float left = aspect * bottom;
        float right = -left;
        glFrustum(left, right, bottom, top, near, far);
    }

    public static void TestLogLabel(String Tested) {
        System.out.println();
        System.out.println("+---------------------------------------------");
        System.out.println("| Test : " + Tested);
        System.out.println("+---------------------------------------------");
        System.out.println();

    }

    public static void main(String[] args) {
        new APROCraft();
    }
}