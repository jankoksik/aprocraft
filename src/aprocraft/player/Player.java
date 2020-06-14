package aprocraft.player;

import aprocraft.APROCraft;
import aprocraft.eq.Crafting;
import aprocraft.eq.GUI;
import aprocraft.eq.Inventory;
import aprocraft.eq.Item;
import org.joml.Math;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWScrollCallback;

import aprocraft.world.Block;
import aprocraft.world.Blocks;
import aprocraft.world.World;

import java.nio.DoubleBuffer;
import java.util.Random;
import java.util.Scanner;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Klasa impelmentująca gracza. Obiekt gracz posiada następujące cechy:
 * położenie kamery, prędkość przemieszczania, działające tarcie, działająca grawitacja,raotacja, szybkośc kamery,
 * prękośc skoku, czy gracz ma możliwośc lotu, czas niszczenia bloku, czas stawiania bloku,
 * inicjalne punkty żywotności, przypisany ekwipunek i gui, raycast , trzymany przedmiot, zablokowanie myszki,
 *
 */

public class Player {
    private float xCam, yCam, zCam;
    private float forward, left;
    private float xSpeed, ySpeed, zSpeed;
    private float friction;
    private float gravity;
    private float yRot, xRot;
    private float camSpeed, jumpSpeed;
    private boolean flying;
    private int destroyTimer, placeTimer;
    private int mouseTimer;

    private int hp = 20;
    public Inventory eq;
    private GUI gui;
    private boolean invPrev;

    private boolean mouseLocked;
    private int step;
    private Raycast raycast;
    private Vector3f v;
    private Vector3f vPrev;

    private long window;

    double mouseX, mouseY;
    private boolean prevPressed = false;
    private boolean grabbed = false;
    private Item grabbedItem;

    private Crafting crafting;

    private World world;

    /**
     * Konstruktor klasy
     * @param window
     * @param world
     */
    public Player(long window, World world) {
        xCam = 512;
        yCam = 64;
        zCam = 512;

        forward = 0;
        left = 0;

        xSpeed = 0;
        ySpeed = 0;
        zSpeed = 0;

        friction = 0.96f;

        gravity = 0.03f;
        camSpeed = 0.2f;
        jumpSpeed = 0.3f;

        yRot = 135;
        xRot = 0;

        flying = false;

        mouseLocked = false;

        step = 0;

        destroyTimer = 0;
        placeTimer = 0;

        mouseTimer = 0;

        raycast = new Raycast(this);

        eq = new Inventory(64, 8, 5);
        gui = new GUI(eq);

        //eq.addItem(1, 10, false);

        crafting = new Crafting(2, 2, "TestRecip");
        crafting.LoadRecipes();
        //crafting.PlaceItemInCrafting(0, 0, 1, eq);
        //crafting.PlaceItemInCrafting(0, 1, 1, eq);
        /*int res = crafting.ShowPatternMatchinResult();
        if (res != -1) {
            crafting.Craft(eq);
            System.out.println("zcraftowano : " + res);
            System.out.println("zcraftowales patyk z ziemi :-/ ");
        } else {
            System.out.println("nie znaleziono craftingu ");
        }*/

        this.window = window;
        this.world = world;
    }

    /**
     * Metoda odpowiedzialna za odświerzanie informacji na temat gracza.
     */
    public void update() {
        mouseUpdate();

        raycast.update();
        v = raycast.getBlockPosition();
        Vector3f v2 = raycast.getNextBlockPosition();


        if (v != null && vPrev != null) {

            float x = (int) (v.x);
            float y = (int) (v.y);
            float z = (int) (v.z);

            float xp = (int) (vPrev.x);
            float yp = (int) (vPrev.y);
            float zp = (int) (vPrev.z);

            if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
                if (x == xp && y == yp && z == zp) {
                    destroyTimer++;
                    if (destroyTimer >= world.getBlock((int) x, (int) y, (int) z).getDurability()) {
                        //eq.addItem(4);
                        eq.addItem(world.getBlock((int) x, (int) y, (int) z).getDrop());
                        gui.updateEq(eq.getEq());
                        world.placeBlock((int) x, (int) y, (int) z, Blocks.AIR);
                        destroyTimer = 0;
                    }
                } else
                    destroyTimer = 0;
            }

            if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS) {
                placeTimer++;
                if (placeTimer % 8 == 0 && v2.distance(xCam - 0.5f, yCam, zCam - 0.5f) > 1.6f) {
                    world.placeBlock((int) v2.x, (int) v2.y, (int) v2.z, Blocks.searchByID(gui.GetcurrQABid()));//Blocks.PLANKS
                    eq.removeOne(gui.GetcurrQABid());
                    gui.updateEq(eq.getEq());
                }
            }
        }

        vPrev = v;

        forward = 0;
        left = 0;

        if (glfwGetKey(window, Controls.getJump()) == GL_TRUE) {
            if (isStanding()) {
                ySpeed = jumpSpeed;
            }
        }

        if (flying) {
            if (glfwGetKey(window, Controls.getUp()) == GL_TRUE)
                yCam += camSpeed;

            if (glfwGetKey(window, Controls.getDown()) == GL_TRUE)
                yCam -= camSpeed;
        }

        if (glfwGetKey(window, Controls.getRight()) == GL_TRUE)
            left = -1;

        if (glfwGetKey(window, Controls.getLeft()) == GL_TRUE)
            left = 1;

        if (glfwGetKey(window, Controls.getForward()) == GL_TRUE)
            forward = 1;

        if (glfwGetKey(window, Controls.getBackward()) == GL_TRUE)
            forward = -1;

        if (glfwGetKey(window, Controls.getInventory()) == GL_TRUE && !invPrev)
            gui.setOpened(!gui.isOpened());

        invPrev = glfwGetKey(window, Controls.getInventory()) == GL_TRUE;

        /*if (glfwGetKey(window, GLFW_KEY_O) == GL_TRUE)
            hp += -1;
        if (glfwGetKey(window, GLFW_KEY_P) == GL_TRUE)
            hp += 1;*/
//        if (glfwGetKey(window, GLFW_KEY_L) == GL_TRUE) {
//           eq.addItem(4);
//           //gui.SetEq(eq.getEq());
//           //System.out.println(eq.getNmbrOfItems(6));
//        }

        if (glfwGetKey(window, GLFW_KEY_T) == GL_TRUE) {
            /*Scanner keyboard = new Scanner(System.in);
            if (keyboard.hasNextInt()) {
                eq.addItem(keyboard.nextInt());
                gui.SetEq(eq.getEq());
            }*/

            /*Random r = new Random();
            for(int i = 0; i < 40; i++) {
                int nr = r.nextInt(127);
                class B extends Block {
                    public B() {
                        super("Block X", nr);
                    }
                }
                eq.addItem(new B().getID());
            }*/

            /*eq.addItem(1);
            eq.addItem(2);
            eq.addItem(3);
            eq.addItem(4);*/

            /*eq.addItem(81);
            eq.addItem(17);
            eq.addItem(18);
            eq.addItem(19);
            eq.addItem(20);
            eq.addItem(21);
            eq.addItem(22);*/

            eq.addItem(Blocks.PLANKS.getID());
            eq.addItem(Blocks.CONCRETE.getID());

            gui.updateEq(eq.getEq());
        }

        if (hp < 0)
            hp = 0;
        if (hp > 20)
            hp = 20;

        glfwSetScrollCallback(window, GLFWScrollCallback.create((window, xoffset, yoffset) -> {
            if (yoffset > 0) {
                gui.setCurr(gui.GetCurr() - 1);
            } else if (yoffset < 0) {
                gui.setCurr(gui.GetCurr() + 1);
            }
        }));

        xSpeed = camSpeed * (forward * Math.sin(Math.toRadians(yRot)) - left * Math.cos(Math.toRadians(yRot)));
        zSpeed = camSpeed * (-forward * Math.cos(Math.toRadians(yRot)) - left * Math.sin(Math.toRadians(yRot)));

        if (!isStanding()) {
            ySpeed -= gravity;
        } else {
            Block b = world.getBlock((int) xCam, (int) (yCam - 2f), (int) zCam);
            if (b != null)
                //ySpeed += gravity/2;

                if (b.getMaterial() == Block.BOUNCY) {
                    ySpeed = 0.8f;
                } else if (b.getMaterial() == Block.STICKY) {
                    xSpeed *= 0.5f;
                    zSpeed *= 0.5f;
                } else if (b.getMaterial() == Block.SLIPPY) {
                    xSpeed *= 1.5f;
                    zSpeed *= 1.5f;
                } else if(b.getMaterial() == Block.HURTING) {
                    if(world.getTime() % 20 == 0)
                        hp--;
                }

            xSpeed *= 0.8f;
            zSpeed *= 0.8f;

            step += forward;

            if(ySpeed <= -0.3f)
                hp += ySpeed*2;
        }

        xSpeed *= friction;
        ySpeed *= friction;
        zSpeed *= friction;

        move(xSpeed, ySpeed, zSpeed);

        if(world.getTime() % 200 == 0)
            hp++;

        //System.out.println("[" + (int)xCam + ", " + (int)yCam + ", " + (int)zCam + "] " + aprocraft.world.getBlock((int)xCam, (int)yCam+1, (int)zCam));
        //aprocraft.world.setBlock((int)-xCam, (int)-yCam, (int)-zCam, aprocraft.world.Blocks.AIR);
    }
/**
 * Metoda odpiwadająca za umożliwienie graczowi przemieszcanie się
 */
    private void move(float x, float y, float z) {
        if (!checkCollision(x, 0, 0)) xCam += x;
        if (!checkCollision(0, y, 0)) yCam += y; else ySpeed = 0;
        if (!checkCollision(0, 0, z)) zCam += z;

        /*if (!checkCollision(x, 0, 0) && !checkCollision(x, 0.6f, 0)) xCam += x;
        if (!checkCollision(0, y, 0) && !checkCollision(0, y+0.6f, 0)) yCam += y;
        if (!checkCollision(0, 0, z) && !checkCollision(0, 0.6f, z)) zCam += z;*/

        /*int xs = (int)Math.abs(x*100);
        int ys = (int)Math.abs(y*100);
        int zs = (int)Math.abs(z*100);

        for(int i = 0; i < xs; i ++)
            if(!checkCollision(x/xs, 0, 0))
                xCam += x/xs;
            else
                x = 0;

        for(int i = 0; i < ys; i ++)
            if(!checkCollision(0, y/ys, 0))
                yCam += y/ys;
            else
                y = 0;

        for(int i = 0; i < zs; i ++)
            if(!checkCollision(0, 0, z/zs))
                zCam += z/zs;
            else
                z = 0;*/
    }

    private int lsx, lsy;

    /**
     * Metoda odświerzająca położenie,ruch myszki ( kursora)
     */
    private void mouseUpdate() {

        //if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_3) == GLFW_PRESS) {
        //glfwSetCursorPos(window, aprocraft.APROCraft.WIDTH / 2, aprocraft.APROCraft.HEIGHT / 2);
        //mouseLocked = !mouseLocked;

        if (!gui.isOpened())
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        else
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

        //}

        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(window, x, y);
        x.rewind();
        y.rewind();

        mouseX = x.get();
        mouseY = y.get();

        if (!gui.isOpened()) {
            double deltaX = mouseX - APROCraft.WIDTH / 2;
            double deltaY = mouseY - APROCraft.HEIGHT / 2;

            xRot += deltaY * 0.2f;
            yRot += deltaX * 0.2f;

            if (xRot < -90)
                xRot = -90;
            if (xRot > 90)
                xRot = 90;

            if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
                mouseTimer++;
            } else {
                mouseTimer = 0;
            }

            glfwSetCursorPos(window, APROCraft.WIDTH / 2, APROCraft.HEIGHT / 2);
        } else {
            int selectionX = (int) ((mouseX - GUI.QABsx) / (GUI.QABsize + 1));
            int selectionY = 4 - (int) ((mouseY - gui.getYOffset() * 2 - 46) / (GUI.QABsize + 1));
            //System.out.println("Mouse(" + selectionX + ", " + selectionY + ") " + grabbed + ", " + grabbedItem);

            /*for(int i = 0; i < 8; i ++)
                for(int j = 0; j < 5; j ++)
                    if(gui.GetItemXY(i, j) != null)
                        System.out.println(i + ", " + j);*/

            if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS && !prevPressed) {
                if(selectionX == 10 && selectionY == 7) {
                    int res = crafting.ShowPatternMatchinResult();
                    if (res != -1) {
                        crafting.Craft(eq);
                        crafting.craft[0] = 0;
                        crafting.craft[1] = 0;
                        crafting.craft[2] = 0;
                        crafting.craft[3] = 0;
                        gui.updateEq(eq.getEq());
                    }
                }

                if (selectionX >= 6 && selectionY >= 6 && selectionX <= 7 && selectionY <= 7) {
                    crafting.GetItemFromCrafting(selectionX - 6, selectionY - 6, eq);
                    gui.updateEq(eq.getEq());
                }

                grabbed = true;
                grabbedItem = gui.GetItemXY(selectionX, selectionY);
                lsx = selectionX;
                lsy = selectionY;
            }

            if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_RELEASE && prevPressed) {
                if (grabbedItem != null)
                    if (selectionX >= 6 && selectionY >= 6 && selectionX <= 7 && selectionY <= 7) {
                        crafting.PlaceItemInCrafting(selectionX - 6, selectionY - 6, grabbedItem.getId(), eq);
                        gui.updateEq(eq.getEq());
                    } else
                        gui.swap(lsx, lsy, selectionX, selectionY);

                grabbed = false;
                grabbedItem = null;
            }
        }

        prevPressed = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS;
    }

    /**
     * Metoda wyświetlająca trzymany przedmiot
     */
    public void renderGrabbedItem() {
        if (grabbed && grabbedItem != null)
            gui.renderGrabbedItem((int) mouseX, (int) (APROCraft.HEIGHT - mouseY), grabbedItem);
    }

    /**
     * Metoda wywołująca wyświetlenie okna craftingu
     */
    public void renderCrafting() {
        if (gui.isOpened())
            gui.renderCrafting(crafting);
    }

    /**
     *
     * @return
     */
    public boolean isStanding() {
        return world.getBlock((int) xCam, (int) (yCam - 2f), (int) zCam) != null;//isStanding;//
    }

    /**
     * Metoda sprwdzająca kolizje gracza z obiektami
     * @param x współrzędna x gracza
     * @param y współrzędna y gracza
     * @param z współrzędna z gracza
     * @return wartośc boolean czy nastąpiła kolizja
     */
    public boolean checkCollision(float x, float y, float z) {
        float radius = 0.4f;

        int x0 = (int) (xCam + x + radius);
        int x1 = (int) (xCam + x - radius);

        int y0 = (int) (yCam - 0.9f + y + radius);
        int y1 = (int) (yCam - 0.9f + y - radius);

        int z0 = (int) (zCam + z + radius);
        int z1 = (int) (zCam + z - radius);

        /*if (aprocraft.world.getBlock((int) (xCam + x), (int) (yCam - 1 + y - radius - 1), (int) (zCam + z)) == null)
            isStanding = false;
        else
            isStanding = true;*/

        if (world.getBlock(x0, y0, z0) != null) return true;
        if (world.getBlock(x1, y0, z0) != null) return true;
        if (world.getBlock(x1, y1, z0) != null) return true;
        if (world.getBlock(x0, y1, z0) != null) return true;

        if (world.getBlock(x0, y0, z1) != null) return true;
        if (world.getBlock(x1, y0, z1) != null) return true;
        if (world.getBlock(x1, y1, z1) != null) return true;
        if (world.getBlock(x0, y1, z1) != null) return true;

        if (world.getBlock(x0, y0 + 1, z0) != null) return true;
        if (world.getBlock(x1, y0 + 1, z0) != null) return true;
        if (world.getBlock(x1, y1 + 1, z0) != null) return true;
        if (world.getBlock(x0, y1 + 1, z0) != null) return true;

        if (world.getBlock(x0, y0 + 1, z1) != null) return true;
        if (world.getBlock(x1, y0 + 1, z1) != null) return true;
        if (world.getBlock(x1, y1 + 1, z1) != null) return true;
        if (world.getBlock(x0, y1 + 1, z1) != null) return true;

        return false;
    }

    /**
     * Metoda odpwowiadająca za wyświetlanie ręki gracza i trzymanego w niej przemiotu
     */
    public void drawHand() {
        float x = -0.5f;
        float y = -0.4f;
        float z = 1f;

        float s = 0.125f;

        glColor3f(1f, 0.8f, 0.4f);

        glBegin(GL_QUADS);

        glVertex3f(x - s, y - s, z - s * 3);
        glVertex3f(x + s, y - s, z - s * 3);
        glVertex3f(x + s, y + s, z - s * 3);
        glVertex3f(x - s, y + s, z - s * 3);

        glVertex3f(x + s, y - s, z + s * 3);
        glVertex3f(x - s, y - s, z + s * 3);
        glVertex3f(x - s, y + s, z + s * 3);
        glVertex3f(x + s, y + s, z + s * 3);

        glVertex3f(x - s, y - s, z - s * 3);
        glVertex3f(x + s, y - s, z - s * 3);
        glVertex3f(x + s, y - s, z + s * 3);
        glVertex3f(x - s, y - s, z + s * 3);

        glVertex3f(x + s, y + s, z - s * 3);
        glVertex3f(x - s, y + s, z - s * 3);
        glVertex3f(x - s, y + s, z + s * 3);
        glVertex3f(x + s, y + s, z + s * 3);

        glVertex3f(x - s, y - s, z - s * 3);
        glVertex3f(x - s, y + s, z - s * 3);
        glVertex3f(x - s, y + s, z + s * 3);
        glVertex3f(x - s, y - s, z + s * 3);

        glVertex3f(x + s, y + s, z - s * 3);
        glVertex3f(x + s, y - s, z - s * 3);
        glVertex3f(x + s, y - s, z + s * 3);
        glVertex3f(x + s, y + s, z + s * 3);

        glEnd();

        glColor3f(1, 1, 1);

        int id = gui.GetcurrQABid() - 1;

        if (id != -1) {
            float cx = (id % 16) / 16.0f;
            float cy = (id / 16) / 16.0f;
            float cs = 1.0f / 16.0f;

            Blocks.TEXTURE_PACK.bind(0);

            glEnable(GL_TEXTURE_2D);
            glBegin(GL_QUADS);

            glTexCoord2f(cx, cy);
            glVertex3f(x - s + 0.1f, y - s + 0.1f, z - s + 0.3f);
            glTexCoord2f(cx + cs, cy);
            glVertex3f(x + s + 0.1f, y - s + 0.1f, z - s + 0.3f);
            glTexCoord2f(cx + cs, cy + cs);
            glVertex3f(x + s + 0.1f, y + s + 0.1f, z - s + 0.3f);
            glTexCoord2f(cx, cy + cs);
            glVertex3f(x - s + 0.1f, y + s + 0.1f, z - s + 0.3f);

            glEnd();
            glDisable(GL_TEXTURE_2D);
        }
    }

    /**
     * Metoda odpowiadająca za odśiwrzanie stanu kamery na podstawie ruchu myszki
     */
    public void updateCamera() {
        glLoadIdentity();
        glRotatef(Math.sin(step / 5.0f) / 2.0f, 0, 0, 1); //Math.sin(Math.sqrt(xCam*zCam)*3)
        glRotatef(xRot, 1, 0, 0);
        glRotatef(yRot, 0, 1, 0);
        glTranslatef(-xCam, -yCam, -zCam);

        glPushMatrix();
        glTranslatef(xCam, yCam, zCam);
        glRotatef(180 - yRot, 0, 1, 0);
        glRotatef(xRot + Math.sin(mouseTimer * 0.5f) * 8, 1, 0, 0);
        drawHand();
        glPopMatrix();

        /*float x = (int)(xCam + Math.sin(Math.toRadians(yRot)) * 3);
        float y = (int)(yCam - Math.sin(Math.toRadians(xRot)) * 3);
        float z = (int)(zCam - Math.cos(Math.toRadians(yRot)) * 3);*/

        //Vector3f v = raycast.getBlockPosition();

        if (v != null) {

            float x = (int) (v.x);
            float y = (int) (v.y);
            float z = (int) (v.z);

            float s = 1f;

            /*if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS)
                aprocraft.world.setBlock((int) x, (int) y, (int) z, aprocraft.world.Blocks.AIR);

            if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS)
                aprocraft.world.setBlock((int) x, (int) y+1, (int) z, aprocraft.world.Blocks.CLOUD);*/

            glLineWidth(4);
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

            glBegin(GL_QUADS);

            glVertex3f(x, y, z);
            glVertex3f(x + s, y, z);
            glVertex3f(x + s, y + s, z);
            glVertex3f(x, y + s, z);

            glVertex3f(x + s, y, z + s);
            glVertex3f(x, y, z + s);
            glVertex3f(x, y + s, z + s);
            glVertex3f(x + s, y + s, z + s);

            glVertex3f(x, y, z);
            glVertex3f(x + s, y, z);
            glVertex3f(x + s, y, z + s);
            glVertex3f(x, y, z + s);

            glVertex3f(x + s, y + s, z);
            glVertex3f(x, y + s, z);
            glVertex3f(x, y + s, z + s);
            glVertex3f(x + s, y + s, z + s);

            glVertex3f(x, y, z);
            glVertex3f(x, y + s, z);
            glVertex3f(x, y + s, z + s);
            glVertex3f(x, y, z + s);

            glVertex3f(x + s, y + s, z);
            glVertex3f(x + s, y, z);
            glVertex3f(x + s, y, z + s);
            glVertex3f(x + s, y + s, z + s);

            glEnd();

            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

            /*Blocks.TEXTURE_PACK.bind(0);
            glEnable(GL_TEXTURE_2D);

            glBegin(GL_QUADS);

            glVertex3f(x, y, z);
            glVertex3f(x + s, y, z);
            glVertex3f(x + s, y + s, z);
            glVertex3f(x, y + s, z);

            glVertex3f(x + s, y, z + s);
            glVertex3f(x, y, z + s);
            glVertex3f(x, y + s, z + s);
            glVertex3f(x + s, y + s, z + s);

            glVertex3f(x, y, z);
            glVertex3f(x + s, y, z);
            glVertex3f(x + s, y, z + s);
            glVertex3f(x, y, z + s);

            glVertex3f(x + s, y + s, z);
            glVertex3f(x, y + s, z);
            glVertex3f(x, y + s, z + s);
            glVertex3f(x + s, y + s, z + s);

            glVertex3f(x, y, z);
            glVertex3f(x, y + s, z);
            glVertex3f(x, y + s, z + s);
            glVertex3f(x, y, z + s);

            glVertex3f(x + s, y + s, z);
            glVertex3f(x + s, y, z);
            glVertex3f(x + s, y, z + s);
            glVertex3f(x + s, y + s, z + s);

            glEnd();

            glDisable(GL_TEXTURE_2D);*/
        }
    }

    public Crafting getCrafting() {
        return crafting;
    }

    /**
     * metoda odpowiadająca za parametr x kamery
     * @return wartość parametru x
     */
    public float getX() {
        return xCam;
    }
    /**
     * metoda odpowiadająca za parametr y kamery
     * @return wartość parametru y
     */
    public float getY() {
        return yCam;
    }
    /**
     * metoda odpowiadająca za parametr z kamery
     * @return wartość parametru z
     */
    public float getZ() {
        return zCam;
    }
    /**
     * Metoda pobierająca rotację ruchu myszki w płaszczyźnie x
     * @return wartość rotacji
     */
    public float getXRot() {
        return xRot;
    }
    /**
     * Metoda pobierająca rotację ruchu myszki w płaszczyźnie y
     * @return wartość rotacji
     */
    public float getYRot() {
        return yRot;
    }
    /**
     * Metoda pobierająca rotację ruchu myszki w płaszczyźnie z
     * @return wartość rotacji
     */
    public float getZRot() {
        return 0;
    }

    public World getWorld() {
        return world;
    }

    public boolean addItem(int id) {

        return eq.addItem(id);
    }

    /**
     * Metoda pobierająca obecne punkty żywotności gracza
     * @return wartość punktów życia
     */
    public int getHp() {
        return hp;
    }

    /**
     * Metoda ustawiająca obecne punkty żywotności gracza
     * @param hp wartość punktów życia
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    public GUI getGui() {
        return gui;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Metoda zwracająca ewkipunek gracza
     * @return
     */
    public Inventory getEq() {
        return eq;
    }

    public void setEq(Inventory eq) {
        this.eq = eq;
    }

    public void AutoSave() {

        Thread thread = new Thread(() -> {
            System.out.println("AutoSave_player");
            eq.saveEq(APROCraft.GAME_NAME + "_inv");
        });
        thread.setName("AutoSave_Player");
        thread.start();
    }
}
