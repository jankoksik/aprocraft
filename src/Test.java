import static org.lwjgl.glfw.GLFW.*;
import  static  org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.json.simple.parser.ParseException;
import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test {

    Chunk stefan;

    public Test() {
        if (glfwInit() != true){
            System.err.println("GLFW sie zepsul");
            System.exit(1);
        }

        final int WIDTH = 640*2;
        final int HEIGHT = 480*2;

        long win = glfwCreateWindow(WIDTH, HEIGHT, "APROCraft v" + APROCraft.VERSION, 0,0);

        /* DEFAULT SETTINGS
        //MOUSE
        Controls.setAttack(GLFW_MOUSE_BUTTON_1);
        Controls.setPlace(GLFW_MOUSE_BUTTON_3);
        //FLY
        Controls.setDown(GLFW_KEY_DOWN);
        Controls.setUp(GLFW_KEY_UP);
        //W S A D
        Controls.setForward(GLFW_KEY_W);
        Controls.setBackward(GLFW_KEY_S);
        Controls.setLeft(GLFW_KEY_A);
        Controls.setRight(GLFW_KEY_D);
        //JUMP
        Controls.setJump(GLFW_KEY_SPACE);
        // OTHER
        Controls.setDrop(GLFW_KEY_Q);
        Controls.setUse(GLFW_KEY_E);
        Controls.setCrouch(GLFW_KEY_LEFT_CONTROL);


        SAVE SETTINGS
        try {
            SaveNReadJson.SaveControls("Controls");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

         */
        try {
            HashMap<String, Integer>  cntrl = SaveNReadJson.readControls("Controls");
            SaveNReadJson.applyCOntrols(cntrl);
            for(Map.Entry<String, Integer> entry : cntrl.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println(key + " : " + value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        glfwShowWindow(win);
        glfwMakeContextCurrent(win);
        GL.createCapabilities();

        Camera camera = new Camera(WIDTH, HEIGHT);

        //glEnable(GL_TEXTURE_2D);
        // tworzyc obiekty i tekstury pod tym

        //Texture dirt = new Texture("./resources/DirtGrassSide.png");

        stefan = new Chunk(0,0);

        float[] vertices =  new float[]{
                -0.5f, 0.5f, 0, // gora lewo 0
                0.5f, 0.5f, 0,  //gora prawo 1
                0.5f, -0.5f, 0, //dol prawo  2
                -0.5f, -0.5f, 0 //dol  lewo  3

        };
        float[] texture = new float[]{
          0,0,
          1,0,
          1,1,
          0, 1
        };
        int[] indices = new int[] {
                0,1,2,
                2,3,0
        };

        //Model model = new Model(vertices, texture, indices);
        //Shader shader = new Shader("shaderTest");
        Matrix4f scale = new Matrix4f().scale(64);

        Matrix4f target = new Matrix4f();

       // glClearColor(0,0,0,0);
        float Xcam = 0;
        float Ycam = 0;
        float Zcam = -5;
        float rotatey = 0;
        float yRot = 0;


        long timePrev = System.nanoTime();
        long timer = System.currentTimeMillis();
        double ns = 1_000_000_000 / 60.0;

        int frames = 0;
        int ticks = 0;

        glEnable(GL_DEPTH_TEST);

        while(glfwWindowShouldClose(win) != true)
        {
            glfwPollEvents();

            long timeNow = System.nanoTime();
            double elapsed = timeNow - timePrev;

            if (elapsed > ns) {
                if(glfwGetKey(win, GLFW_KEY_ESCAPE)==GL_TRUE)
                {
                    glfwSetWindowShouldClose(win, true);
                }
                if(glfwGetKey(win, Controls.getUp())==GL_TRUE)
                {
                    Ycam -= 0.01f;
                }
                if(glfwGetKey(win, Controls.getDown())==GL_TRUE)
                {
                    Ycam += 0.01f;
                }
                if(glfwGetKey(win, Controls.getRight())==GL_TRUE)
                {
                    Xcam -= 0.01f;
                    //yRot -= 0.01f;
                }
                if(glfwGetKey(win,Controls.getLeft())==GL_TRUE)
                {
                    Xcam += 0.01f;
                    //yRot -= 0.01f;
                }
                if(glfwGetKey(win, Controls.getBackward())==GL_TRUE)
                {
                    Zcam -= 0.01f;
                }
                if(glfwGetKey(win, Controls.getForward())==GL_TRUE)
                {
                    Zcam += 0.01f;
                }
                if(glfwGetKey(win, GLFW_KEY_LEFT)==GL_TRUE)
                {
                    camera.addRotation(0,-0.001f, 0);
                    yRot += 0.5f;
                }
                if(glfwGetKey(win, GLFW_KEY_RIGHT)==GL_TRUE)
                {
                    camera.addRotation(0,0.001f, 0);
                    yRot -= 0.5f;
                }
                if(glfwGetKey(win, GLFW_KEY_R)==GL_TRUE)
                {
                    camera.setPosition(new Vector3f(Xcam, Ycam, Zcam));
                    camera.setRotation(new Vector3f(0,rotatey + 90,0));
                }

                camera.setPosition(new Vector3f(Xcam, Ycam, Zcam));
                camera.setRotation(new Vector3f(0,rotatey,0));

                ticks ++;
                timePrev += ns;
            } else {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glClearColor(0.1f, 0.2f, 0.7f, 1);

                glMatrixMode(GL_PROJECTION);
                glLoadIdentity();
                gluPerspective(70.0f, ((float)WIDTH/(float)HEIGHT), 0.1f, 1000.0f);
                glMatrixMode(GL_MODELVIEW);
                glLoadIdentity();

                glTranslatef(Xcam, Ycam, Zcam);
                glRotatef(yRot, 0, 1, 0);

                //shader.bind();
                //shader.setUniform("sampler", 0);
                //shader.setUniform("projection", camera.getViewMatrix());
                //dirt.bind(0);
                //model.render();
                stefan.render();
                frames++;
                glfwSwapBuffers(win);
            }

            if (System.currentTimeMillis() - timer > 1000) {
                System.out.println("FPS: " + frames + " UPS: " + ticks);
                frames = 0;
                ticks = 0;
                timer += 1000;
            }


            /*glColor3f(0.5f,0.5f,1.0f);

            glBegin(GL_QUADS);
            glColor3f(1.0f,1.0f,0.0f);
            glVertex3f( 1.0f, 1.0f,-1.0f);
            glVertex3f(-1.0f, 1.0f,-1.0f);
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glVertex3f( 1.0f, 1.0f, 1.0f);
            glColor3f(1.0f,0.5f,0.0f);
            glVertex3f( 1.0f,-1.0f, 1.0f);
            glVertex3f(-1.0f,-1.0f, 1.0f);
            glVertex3f(-1.0f,-1.0f,-1.0f);
            glVertex3f( 1.0f,-1.0f,-1.0f);
            glColor3f(1.0f,0.0f,0.0f);
            glVertex3f( 1.0f, 1.0f, 1.0f);
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glVertex3f(-1.0f,-1.0f, 1.0f);
            glVertex3f( 1.0f,-1.0f, 1.0f);
            glColor3f(1.0f,1.0f,0.0f);
            glVertex3f( 1.0f,-1.0f,-1.0f);
            glVertex3f(-1.0f,-1.0f,-1.0f);
            glVertex3f(-1.0f, 1.0f,-1.0f);
            glVertex3f( 1.0f, 1.0f,-1.0f);
            glColor3f(0.0f,0.0f,1.0f);
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glVertex3f(-1.0f, 1.0f,-1.0f);
            glVertex3f(-1.0f,-1.0f,-1.0f);
            glVertex3f(-1.0f,-1.0f, 1.0f);
            glColor3f(1.0f,0.0f,1.0f);
            glVertex3f( 1.0f, 1.0f,-1.0f);
            glVertex3f( 1.0f, 1.0f, 1.0f);
            glVertex3f( 1.0f,-1.0f, 1.0f);
            glVertex3f( 1.0f,-1.0f,-1.0f);
            glEnd();*/

            //glfwSwapBuffers(win);
        }
        glfwTerminate();

    }

    public static void gluPerspective(float fovy, float aspect, float near, float far) {
        float bottom = -near * (float) Math.tan(fovy / 2);
        float top = -bottom;
        float left = aspect * bottom;
        float right = -left;
        glFrustum(left, right, bottom, top, near, far);
    }

    public static void main(String [] args){
        new Test();
    }



}
