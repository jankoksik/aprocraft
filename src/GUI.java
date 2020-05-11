import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class GUI {
    //Quick Action Bar
    private static Texture Grid = new Texture("./resources/Grid2.png");
    private static float QABsize = 50;
    private static float CurrMul = 1.1f;
    private static int SizeOfQAB = 8;
    private static int currChoosed=0;
    private static int QABsx = (int) (APROCraft.HEIGHT - ((SizeOfQAB-1) * (QABsize)) -QABsize*CurrMul - SizeOfQAB+1);
    private static int QABsy = APROCraft.HEIGHT/ 30;


    public static void setCurr(int curr){
        currChoosed =  curr;
        if(currChoosed < 0)
        {
            currChoosed = SizeOfQAB-1;
        }
        if(currChoosed > SizeOfQAB-1){
            currChoosed = 0;
        }

    }
    public static int GetCurr(){
        return currChoosed;
    }

    public static void RenderQAB(){
        glEnable(GL_TEXTURE_2D);
        Grid.bind(0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        int cX = QABsx;
        int cY = QABsy;
        for(int i=0; i<SizeOfQAB; i++)
        {
            float si = QABsize;

            if(currChoosed == i)
                si *= CurrMul;
            glBegin(GL_QUADS);

            glTexCoord2f(0,0);
            glVertex2f(cX, QABsy);

            glTexCoord2f(0,1);
            glVertex2f(cX+si, QABsy);

            glTexCoord2f(1,1);
            glVertex2f(cX+si, QABsy+si);

            glTexCoord2f(1,0);
            glVertex2f(cX, QABsy+si);

            cX+=(si+1);
            glEnd();
        }
        cX = 0;
        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);


    }
     private void Test(){
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
