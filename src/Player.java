import org.joml.Math;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Player {
    private float xCam, yCam, zCam;
    private float xSpeed, ySpeed, zSpeed;
    private float friction;
    private float gravity;
    private float yRot, xRot;
    private float camSpeed, rotSpeed, jumpSpeed;
    private boolean flying;

    private boolean mouseLocked;

    private long window;
    private World world;

    public Player(long window, World world) {
        xCam = -10;
        yCam = -30;
        zCam = -10;

        xSpeed = 0;
        ySpeed = 0;
        zSpeed = 0;

        friction = 0.92f;

        gravity = 0.04f;
        camSpeed = 0.2f;
        rotSpeed = 0.2f;
        jumpSpeed = 1.0f;

        yRot = 135;
        xRot = 0;

        flying = false;

        mouseLocked = false;

        this.window = window;
        this.world = world;
    }

    public void update() {
        mouseUpdate();

        if (glfwGetKey(window, Controls.getJump()) == GL_TRUE) {
            if (isStanding())
                ySpeed = -jumpSpeed;
        }

        if (flying) {
            if (glfwGetKey(window, Controls.getUp()) == GL_TRUE)
                yCam -= camSpeed;

            if (glfwGetKey(window, Controls.getDown()) == GL_TRUE)
                yCam += camSpeed;
        }

        if (glfwGetKey(window, Controls.getRight()) == GL_TRUE) {
            xCam -= camSpeed * Math.sin(Math.toRadians(yRot + 90));
            zCam += camSpeed * Math.cos(Math.toRadians(yRot + 90));
        }

        if (glfwGetKey(window, Controls.getLeft()) == GL_TRUE) {
            xCam -= camSpeed * Math.sin(Math.toRadians(yRot - 90));
            zCam += camSpeed * Math.cos(Math.toRadians(yRot - 90));
        }

        if (glfwGetKey(window, Controls.getForward()) == GL_TRUE) {
            xCam -= camSpeed * Math.sin(Math.toRadians(yRot));
            zCam += camSpeed * Math.cos(Math.toRadians(yRot));
        }

        if (glfwGetKey(window, Controls.getBackward()) == GL_TRUE) {
            xCam += camSpeed * Math.sin(Math.toRadians(yRot));
            zCam -= camSpeed * Math.cos(Math.toRadians(yRot));
        }

        if(!isStanding())
            ySpeed += gravity;
        else
            ySpeed -= 0.9f*gravity;

        ySpeed *= friction;

        yCam += ySpeed;
        //System.out.println("[" + (int)xCam + ", " + (int)yCam + ", " + (int)zCam + "] " + world.getBlock((int)xCam, (int)yCam+1, (int)zCam));
        //world.setBlock((int)-xCam, (int)-yCam, (int)-zCam, Blocks.AIR);
    }

    private void mouseUpdate() {
        double newX = 0, newY = 0, prevX = 0, prevY = 0;

        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
            glfwSetCursorPos(window, APROCraft.WIDTH/2, APROCraft.HEIGHT/2);
            mouseLocked = !mouseLocked;
            if(mouseLocked)
                glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
            else
                glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

        }

        if (mouseLocked){
            DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

            glfwGetCursorPos(window, x, y);
            x.rewind();
            y.rewind();

            newX = x.get();
            newY = y.get();

            double deltaX = newX - APROCraft.WIDTH/2;
            double deltaY = newY - APROCraft.HEIGHT/2;

            prevX = newX;
            prevY = newY;

            xRot += deltaY*0.2f;
            yRot += deltaX*0.2f;

            glfwSetCursorPos(window, APROCraft.WIDTH/2, APROCraft.HEIGHT/2);
        }

    }

    public boolean isStanding() {
        return world.getBlock((int)-xCam, (int)-yCam-2, (int)-zCam) != null;
    }

    public void updateCamera() {
        glRotatef(xRot, 1, 0, 0);
        glRotatef(yRot, 0, 1, 0);
        glTranslatef(xCam, yCam, zCam);
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
