package aprocraft.player;

import aprocraft.APROCraft;
import aprocraft.eq.GUI;
import aprocraft.eq.Inventory;
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

    public Inventory getEq() {
        return eq;
    }

    public void setEq(Inventory eq) {
        this.eq = eq;
    }

    private int hp = 20;
    public Inventory eq;
    private GUI gui;
    private boolean mouseLocked;
    private int step;
    private Raycast raycast;
    private Vector3f v;

    private long window;

    public boolean addItem (int id){

         return eq.addItem(id);
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public GUI getGui() {
        return gui;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    private World world;

    public Player(long window, World world) {
        xCam = 512;
        yCam = 34;
        zCam = 512;

        forward = 0;
        left = 0;

        xSpeed = 0;
        ySpeed = 0;
        zSpeed = 0;

        friction = 0.96f;

        gravity = 0.02f;
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

        this.window = window;
        this.world = world;
    }

    public void update() {
        mouseUpdate();

        raycast.update();
        v = raycast.getBlockPosition();
        Vector3f v2 = raycast.getNextBlockPosition();


        if(v != null) {

            float x = (int) (v.x);
            float y = (int) (v.y);
            float z = (int) (v.z);

            if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
                destroyTimer ++;
                if(destroyTimer % 8 == 0) {
                    //eq.addItem(4);
                    eq.addItem(world.getBlock((int) x, (int) y, (int) z).getID());
                    gui.SetEq(eq.getEq());
                    world.placeBlock((int) x, (int) y, (int) z, Blocks.AIR);
                }
            }

            if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS) {
                placeTimer ++;
                if(placeTimer % 8 == 0 && v2.distance(xCam-0.5f, yCam, zCam-0.5f) > 1.6f) {
                    world.placeBlock((int) v2.x, (int) v2.y, (int) v2.z, Blocks.searchByID(gui.GetcurrQABid()));//Blocks.PLANKS
                    eq.removeOne(gui.GetcurrQABid());
                    gui.SetEq(eq.getEq());
                }
            }
        }

        forward = 0;
        left = 0;

        if (glfwGetKey(window, Controls.getJump()) == GL_TRUE) {
            if (isStanding()) {
                ySpeed = jumpSpeed;
                // System.out.println("jump");
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
        if (glfwGetKey(window, Controls.getInventory()) == GL_TRUE)
            gui.setOpened(!gui.isOpened());

        if (glfwGetKey(window, GLFW_KEY_O) == GL_TRUE)
            hp += -1;
        if (glfwGetKey(window, GLFW_KEY_P) == GL_TRUE)
            hp += 1;
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

            Random r = new Random();
            for(int i = 0; i < 40; i++) {
                int nr = r.nextInt(255);
                class B extends Block {
                    public B() {
                        super("Block X", null, nr%16, (int)nr/16);
                    }
                }
                eq.addItem(new B().getID());
            }

            gui.SetEq(eq.getEq());
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

        if (!isStanding())
            ySpeed -= gravity;
        else {
            Block b = world.getBlock((int) xCam, (int) (yCam - 2f), (int) zCam);
            if (b != null)
                if (b.getMaterial() == Block.BOUNCY) {
                    ySpeed = 0.8f;
                } else if (b.getMaterial() == Block.STICKY) {
                    xSpeed *= 0.5f;
                    zSpeed *= 0.5f;
                } else if (b.getMaterial() == Block.SLIPPY) {
                    xSpeed *= 1.5f;
                    zSpeed *= 1.5f;
                }

            xSpeed *= 0.8f;
            zSpeed *= 0.8f;

            step += forward;
        }

        xSpeed *= friction;
        ySpeed *= friction;
        zSpeed *= friction;

        move(xSpeed, ySpeed, zSpeed);


        //System.out.println("[" + (int)xCam + ", " + (int)yCam + ", " + (int)zCam + "] " + aprocraft.world.getBlock((int)xCam, (int)yCam+1, (int)zCam));
        //aprocraft.world.setBlock((int)-xCam, (int)-yCam, (int)-zCam, aprocraft.world.Blocks.AIR);
    }

    private void move(float x, float y, float z) {
        if (!checkCollision(x, 0, 0)) xCam += x;
        if (!checkCollision(0, y, 0)) yCam += y;
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

    private boolean prevPressed = false;

    private void mouseUpdate() {
        double newX = 0, newY = 0;

        //if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_3) == GLFW_PRESS) {
            //glfwSetCursorPos(window, aprocraft.APROCraft.WIDTH / 2, aprocraft.APROCraft.HEIGHT / 2);
            //mouseLocked = !mouseLocked;

            if(!gui.isOpened())
                glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
            else
                glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

        //}

        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(window, x, y);
        x.rewind();
        y.rewind();

        newX = x.get();
        newY = y.get();

        if (!gui.isOpened()) {
            double deltaX = newX - APROCraft.WIDTH / 2;
            double deltaY = newY - APROCraft.HEIGHT / 2;

            xRot += deltaY * 0.2f;
            yRot += deltaX * 0.2f;

            if (xRot < -90)
                xRot = -90;
            if (xRot > 90)
                xRot = 90;

            if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
                mouseTimer ++;
            } else {
                mouseTimer = 0;
            }

            glfwSetCursorPos(window, APROCraft.WIDTH / 2, APROCraft.HEIGHT / 2);
        } else {

        }

        prevPressed = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS;
    }

    public boolean isStanding() {
        return world.getBlock((int) xCam, (int) (yCam - 2f), (int) zCam) != null;//isStanding;//
    }

    public boolean checkCollision(float x, float y, float z) {
        float radius = 0.4f;

        int x0 = (int) (xCam + x + radius);
        int x1 = (int) (xCam + x - radius);

        int y0 = (int) (yCam - 1 + y + radius);
        int y1 = (int) (yCam - 1 + y - radius);

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

    public void drawHand() {
        float x = -0.5f;
        float y = -0.4f;
        float z = 1f;

        float s = 0.125f;

        glColor3f(1f, 0.8f, 0.4f);

        glBegin(GL_QUADS);

        glVertex3f(x - s, y - s, z - s*3);
        glVertex3f(x + s, y - s, z - s*3);
        glVertex3f(x + s, y + s, z - s*3);
        glVertex3f(x - s, y + s, z - s*3);

        glVertex3f(x + s, y - s, z + s*3);
        glVertex3f(x - s, y - s, z + s*3);
        glVertex3f(x - s, y + s, z + s*3);
        glVertex3f(x + s, y + s, z + s*3);

        glVertex3f(x - s, y - s, z - s*3);
        glVertex3f(x + s, y - s, z - s*3);
        glVertex3f(x + s, y - s, z + s*3);
        glVertex3f(x - s, y - s, z + s*3);

        glVertex3f(x + s, y + s, z - s*3);
        glVertex3f(x - s, y + s, z - s*3);
        glVertex3f(x - s, y + s, z + s*3);
        glVertex3f(x + s, y + s, z + s*3);

        glVertex3f(x - s, y - s, z - s*3);
        glVertex3f(x - s, y + s, z - s*3);
        glVertex3f(x - s, y + s, z + s*3);
        glVertex3f(x - s, y - s, z + s*3);

        glVertex3f(x + s, y + s, z - s*3);
        glVertex3f(x + s, y - s, z - s*3);
        glVertex3f(x + s, y - s, z + s*3);
        glVertex3f(x + s, y + s, z + s*3);

        glEnd();

        glColor3f(1, 1, 1);
    }

    public void updateCamera() {
        glLoadIdentity();
        glRotatef(Math.sin(step / 5.0f) / 2.0f, 0, 0, 1); //Math.sin(Math.sqrt(xCam*zCam)*3)
        glRotatef(xRot, 1, 0, 0);
        glRotatef(yRot, 0, 1, 0);
        glTranslatef(-xCam, -yCam, -zCam);

        glPushMatrix();
        glTranslatef(xCam, yCam, zCam);
        glRotatef(180-yRot, 0, 1, 0);
        glRotatef(xRot+Math.sin(mouseTimer*0.5f)*8, 1, 0, 0);
        drawHand();
        glPopMatrix();

        /*float x = (int)(xCam + Math.sin(Math.toRadians(yRot)) * 3);
        float y = (int)(yCam - Math.sin(Math.toRadians(xRot)) * 3);
        float z = (int)(zCam - Math.cos(Math.toRadians(yRot)) * 3);*/

        //Vector3f v = raycast.getBlockPosition();

        if(v != null) {

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
        }
    }

    public float getX() {
        return xCam;
    }

    public float getY() {
        return yCam;
    }

    public float getZ() {
        return zCam;
    }

    public float getXRot() {
        return xRot;
    }

    public float getYRot() {
        return yRot;
    }

    public float getZRot() {
        return 0;
    }

    public World getWorld() {
        return world;
    }


    public void AutoSave(){

        Thread thread = new Thread(() -> {
            System.out.println("AutoSave_player");
            eq.saveEq(APROCraft.GAME_NAME + "_inv");
        });
        thread.setName("AutoSave_Player");
        thread.start();
    }
}
