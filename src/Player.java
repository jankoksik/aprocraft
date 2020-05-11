import org.joml.Math;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Player {
    private float xCam, yCam, zCam;
    private float forward, left;
    private float xSpeed, ySpeed, zSpeed;
    private float friction;
    private float gravity;
    private float yRot, xRot;
    private float camSpeed, rotSpeed, jumpSpeed;
    private boolean flying;
    public Inventory eq = new Inventory(64, 32);
    private boolean mouseLocked;
    private boolean isStanding;

    private long window;
    private World world;

    public Player(long window, World world) {
        xCam = 10;
        yCam = 30;
        zCam = 10;

        forward = 0;
        left = 0;

        xSpeed = 0;
        ySpeed = 0;
        zSpeed = 0;

        friction = 0.88f;

        gravity = 0.04f;
        camSpeed = 0.2f;
        rotSpeed = 0.2f;
        jumpSpeed = 1.0f;

        yRot = 135;
        xRot = 0;

        flying = false;

        mouseLocked = false;

        isStanding = false;

        this.window = window;
        this.world = world;
    }

    public void update() {
        mouseUpdate();

        forward = 0;
        left = 0;

        if (glfwGetKey(window, Controls.getJump()) == GL_TRUE) {
            if (isStanding) {
                ySpeed = jumpSpeed;
                System.out.println("jump");
            }
        }

        if (flying) {
            if (glfwGetKey(window, Controls.getUp()) == GL_TRUE)
                yCam += camSpeed;

            if (glfwGetKey(window, Controls.getDown()) == GL_TRUE)
                yCam -= camSpeed;
        }

        if (glfwGetKey(window, Controls.getRight()) == GL_TRUE) {
            //xSpeed = camSpeed * Math.sin(Math.toRadians(yRot + 90));
            //zSpeed = -camSpeed * Math.cos(Math.toRadians(yRot + 90));
            left = -1;
        }

        if (glfwGetKey(window, Controls.getLeft()) == GL_TRUE) {
            //xSpeed = camSpeed * Math.sin(Math.toRadians(yRot - 90));
            //zSpeed = -camSpeed * Math.cos(Math.toRadians(yRot - 90));
            left = 1;
        }

        if (glfwGetKey(window, Controls.getForward()) == GL_TRUE) {
            //xCam -= camSpeed * Math.sin(Math.toRadians(yRot));
            //zCam += camSpeed * Math.cos(Math.toRadians(yRot));
            //xSpeed = camSpeed * Math.sin(Math.toRadians(yRot));
            //zSpeed = -camSpeed * Math.cos(Math.toRadians(yRot));
            forward = 1;
        }

        if (glfwGetKey(window, Controls.getBackward()) == GL_TRUE) {
            //xSpeed = -camSpeed * Math.sin(Math.toRadians(yRot));
            //zSpeed = camSpeed * Math.cos(Math.toRadians(yRot));
            forward = -1;
        }

        xSpeed = camSpeed * (forward * Math.sin(Math.toRadians(yRot)) - left * Math.cos(Math.toRadians(yRot)));
        zSpeed = camSpeed * (-forward * Math.cos(Math.toRadians(yRot)) - left * Math.sin(Math.toRadians(yRot)));

        //if(!isStanding)
        //ySpeed -= gravity;
        //else
        //ySpeed += 0.9f*gravity;

        //yCam += ySpeed;

        ySpeed -= gravity;

        xSpeed *= friction;
        ySpeed *= friction;
        zSpeed *= friction;

        move(xSpeed, ySpeed, zSpeed);


        //System.out.println("[" + (int)xCam + ", " + (int)yCam + ", " + (int)zCam + "] " + world.getBlock((int)xCam, (int)yCam+1, (int)zCam));
        //world.setBlock((int)-xCam, (int)-yCam, (int)-zCam, Blocks.AIR);
    }

    private void move(float x, float y, float z) {
        /*xCam += x;
        yCam += y;
        zCam += z;*/

        /*if (!checkCollision(x, 0, 0)) xCam += x;
        if (!checkCollision(0, y, 0)) yCam += y;
        if (!checkCollision(0, 0, z)) zCam += z;*/

        int xs = (int)Math.abs(x*100);
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
                z = 0;
    }

    private void mouseUpdate() {
        double newX = 0, newY = 0, prevX = 0, prevY = 0;

        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
            glfwSetCursorPos(window, APROCraft.WIDTH / 2, APROCraft.HEIGHT / 2);
            mouseLocked = !mouseLocked;
            if (mouseLocked)
                glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
            else
                glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

        }

        if (mouseLocked) {
            DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

            glfwGetCursorPos(window, x, y);
            x.rewind();
            y.rewind();

            newX = x.get();
            newY = y.get();

            double deltaX = newX - APROCraft.WIDTH / 2;
            double deltaY = newY - APROCraft.HEIGHT / 2;

            prevX = newX;
            prevY = newY;

            xRot += deltaY * 0.2f;
            yRot += deltaX * 0.2f;

            glfwSetCursorPos(window, APROCraft.WIDTH / 2, APROCraft.HEIGHT / 2);
        }
    }

    /*public boolean isStanding() {
        return world.getBlock((int)xCam, (int)yCam-1, (int)zCam) != null;
    }*/

    public boolean checkCollision(float x, float y, float z) {
        float radius = 0.4f;

        int x0 = (int) (xCam + x + radius);
        int x1 = (int) (xCam + x - radius);

        int y0 = (int) (yCam - 1 + y + radius);
        int y1 = (int) (yCam - 1 + y - radius);

        int z0 = (int) (zCam + z + radius);
        int z1 = (int) (zCam + z - radius);

        if (world.getBlock((int) (xCam + x), y1 - 1, (int) (zCam + z)) == null)
            isStanding = false;
        else
            isStanding = true;


        if (world.getBlock(x0, y0, z0) != null) return true;
        if (world.getBlock(x1, y0, z0) != null) return true;
        if (world.getBlock(x1, y1, z0) != null) return true;
        if (world.getBlock(x0, y1, z0) != null) return true;
        if (world.getBlock(x0, y0, z1) != null) return true;
        if (world.getBlock(x1, y0, z1) != null) return true;
        if (world.getBlock(x1, y1, z1) != null) return true;
        if (world.getBlock(x0, y1, z1) != null) return true;

        return false;
    }

    public void updateCamera() {
        glRotatef(xRot, 1, 0, 0);
        glRotatef(yRot, 0, 1, 0);
        glTranslatef(-xCam, -yCam, -zCam);
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
}
