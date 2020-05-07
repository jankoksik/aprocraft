import static org.lwjgl.glfw.GLFW.*;
import  static  org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;

public class Test {

    public Test(){
        if (glfwInit() != true){
            System.err.println("GLFW sie zepsul");
            System.exit(1);
        }

        int HEIGHT = 480;
         int WIDTH = 640;
        long win = glfwCreateWindow(WIDTH, HEIGHT, "Minecraft v.0.0.1", 0,0);

        glfwShowWindow(win);
        glfwMakeContextCurrent(win);
        GL.createCapabilities();

        glEnable(GL_TEXTURE_2D);
        // tworzyc obiekty i tekstury pod tym

        Texture dirt = new Texture("./resources/DirtGrassSide.png");


        float[] vertices =  new float[]{
                -0.5f, 0.5f, 0, // gora lewo
                0.5f, 0.5f, 0, //gora prawo
                0.5f, -0.5f, 0, //dol prawo
                //drugi trojkat
                0.5f, -0.5f, 0, // gora prawo
                -0.5f, -0.5f, 0, //dol  lewo
                -0.5f, 0.5f, 0 // gora lewo

        };
        float[] texture = new float[]{
          0,0,
          1,0,
          1,1,

          1,1,
          0, 1,
          0,0
        };
        float x=0.01f;

        Model model = new Model(vertices, texture);
       // glClearColor(0,0,0,0);
        while(glfwWindowShouldClose(win) != true)
        {
            if(glfwGetKey(win, GLFW_KEY_ESCAPE)==GL_TRUE)
            {
                glfwSetWindowShouldClose(win, true);
            }

            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);
            model.render();
            dirt.bind();


//            glBegin(GL_QUADS);
//                glTexCoord2f(0,0);
//                glVertex2f(-x, x);
//            glTexCoord2f(1,0);
//                glVertex2f(x, x);
//            glTexCoord2f(1,1);
//                glVertex2f(x, -x);
//            glTexCoord2f(0,1);
//                glVertex2f(-x, -x);
//
//                glEnd();

            glfwSwapBuffers(win);
        }
        glfwTerminate();

    }
    public static void main(String [] args){
        new Test();
    }



}
