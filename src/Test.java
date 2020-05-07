import static org.lwjgl.glfw.GLFW.*;
import  static  org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

public class Test {

    public Test(){
        if (glfwInit() != true){
            System.err.println("GLFW sie zepsul");
            System.exit(1);
        }

        final int HEIGHT = 480;
        final int WIDTH = 640;
        long win = glfwCreateWindow(WIDTH, HEIGHT, "Minecraft v.0.0.1", 0,0);

        glfwShowWindow(win);
        glfwMakeContextCurrent(win);
        GL.createCapabilities();

        Camera camera = new Camera(WIDTH, HEIGHT);

        glEnable(GL_TEXTURE_2D);
        // tworzyc obiekty i tekstury pod tym

        Texture dirt = new Texture("./resources/DirtGrassSide.png");


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

        Model model = new Model(vertices, texture, indices);
        Shader shader = new Shader("shaderTest");
        Matrix4f scale = new Matrix4f().scale(64);

        Matrix4f target = new Matrix4f();

       // glClearColor(0,0,0,0);
        float Xcam = 0;
        float Ycam = 0;
        float Zcam = 0;
        while(glfwWindowShouldClose(win) != true)
        {
            target = scale;
            if(glfwGetKey(win, GLFW_KEY_ESCAPE)==GL_TRUE)
            {
                glfwSetWindowShouldClose(win, true);
            }
            if(glfwGetKey(win, GLFW_KEY_SPACE)==GL_TRUE)
            {
                Ycam -= 0.01f;
            }
            if(glfwGetKey(win, GLFW_KEY_LEFT_SHIFT)==GL_TRUE)
            {
                Ycam += 0.01f;
            }
            if(glfwGetKey(win, GLFW_KEY_D)==GL_TRUE)
            {
                Xcam -= 0.01f;
            }
            if(glfwGetKey(win, GLFW_KEY_A)==GL_TRUE)
            {
                Xcam += 0.01f;
            }
            if(glfwGetKey(win, GLFW_KEY_S)==GL_TRUE)
            {
               // Zcam -= 0.01f;
            }
            if(glfwGetKey(win, GLFW_KEY_W)==GL_TRUE)
            {
               // Zcam += 0.01f;
            }
            camera.setPosition(new Vector3f(Xcam, Ycam, Zcam));




            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);
            shader.bind();
            shader.setUniform("sampler", 0);
            shader.setUniform("projection", camera.getProjection().mul(target));
            dirt.bind(0);
            model.render();


            glfwSwapBuffers(win);
        }
        glfwTerminate();

    }
    public static void main(String [] args){
        new Test();
    }



}
