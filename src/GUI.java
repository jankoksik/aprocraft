import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class GUI {
    Vector2f position;
    Vector2f size;


     static void Test(){
        Texture tex = new Texture("./resources/Grid2.png");
        glEnable(GL_TEXTURE_2D);
        tex.bind(0);
         glEnable(GL_BLEND);
         glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
         glBegin(GL_QUADS);
         glTexCoord2f(0,0);
         glVertex2f(-50f, 50f);
         glTexCoord2f(0,1);
         glVertex2f(50f, 50f);
         glTexCoord2f(1,1);
         glVertex2f(50f, -50f);
         glTexCoord2f(1,0);
         glVertex2f(-50f, -50f);
         glEnd();
         glDisable(GL_BLEND);
         glDisable(GL_TEXTURE_2D);



    }







}
