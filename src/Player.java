import org.joml.Math;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Player {
    private float xCam, yCam, zCam;
    private float xSpeed, ySpeed, zSpeed;
    private float friction;
    private float gravity;
    private float yRot;
    private float camSpeed, rotSpeed, jumpSpeed;
    private boolean flying;

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
        rotSpeed = 1.0f;
        jumpSpeed = 1.0f;

        yRot = 135;

        flying = false;

        this.window = window;
        this.world = world;
    }

    public void update() {
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

        if (glfwGetKey(window, GLFW_KEY_LEFT) == GL_TRUE)
            yRot -= rotSpeed;

        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GL_TRUE)
            yRot += rotSpeed;

        if(!isStanding())
            ySpeed += gravity;
        else
            ySpeed -= 0.9f*gravity;

        ySpeed *= friction;

        yCam += ySpeed;
        //System.out.println("[" + (int)xCam + ", " + (int)yCam + ", " + (int)zCam + "] " + world.getBlock((int)xCam, (int)yCam+1, (int)zCam));
    }

    public boolean isStanding() {
        return world.getBlock((int)-xCam, (int)-yCam-2, (int)-zCam) != null;
    }

    public void updateCamera() {
        glRotatef(yRot, 0, 1, 0);
        glTranslatef(xCam, yCam, zCam);
    }
}
