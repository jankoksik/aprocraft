import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class GUI {
    //Quick Action Bar



    private int height = 0;
    private static Texture Grid = new Texture("./resources/ramkka.png");
    private static int magicNMBR = 15;
    private static float QABsize = APROCraft.HEIGHT/magicNMBR;
    private static float CurrMul = 1.1f;
    private  int SizeOfQAB = 8;
    private  int currChoosed=0;
    private int QABsx = (int) (APROCraft.HEIGHT - (SizeOfQAB - 1)*QABsize + QABsize*CurrMul + (SizeOfQAB - 1)*1 );//(APROCraft.HEIGHT/2 + ((SizeOfQAB+1)*1 + SizeOfQAB*QABsize)/2);
    private int QABsy = APROCraft.HEIGHT/ magicNMBR;

    public GUI(Inventory i) {
        SizeOfQAB = i.GetW();
        height = i.GetSpace()/i.GetW();
    }

    public  void setCurr(int curr){
        currChoosed =  curr;
        if(currChoosed < 0)
        {
            currChoosed = SizeOfQAB-1;
        }
        if(currChoosed > SizeOfQAB-1){
            currChoosed = 0;
        }

    }
    public  int GetCurr(){
        return currChoosed;
    }

    public void RenderQAB(){
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
    public  void RenderHealth(int health){
        int maxH = 20;
        if(maxH < health)
            health = maxH;
        if(health < 0)
            health = 0;
        Texture Grid = new Texture("./resources/hearts.png");
        glEnable(GL_TEXTURE_2D);
        Grid.bind(0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        int offsety = QABsy + (int) QABsize + 10;
        int size = (int) (0.4f * QABsize);
        int offsetx = 1;
        int cX = QABsx;
        int full = health/2;
        int miss = (maxH-health)/2;
        for(int i=0; i<full; i++)
        {
            glBegin (GL_QUADS);
            glTexCoord2f ( (float)0,1); glVertex2f (cX, offsety);
            glTexCoord2f ((float)1/3, 1); glVertex2f (cX + size, offsety);
            glTexCoord2f ((float)1/3, 0); glVertex2f (cX + size, offsety+size);
            glTexCoord2f ((float)0, 0); glVertex2f (cX, offsety + size);

            cX+=(size+offsetx);
            glEnd();
        }
        if( health%2!=0){
            glBegin (GL_QUADS);
            glTexCoord2f ( (float)1/3,1); glVertex2f (cX, offsety);
            glTexCoord2f ((float)2/3, 1); glVertex2f (cX + size, offsety);
            glTexCoord2f ((float)2/3, 0); glVertex2f (cX + size, offsety+size);
            glTexCoord2f ((float)1/3, 0); glVertex2f (cX, offsety + size);

            cX+=(size+offsetx);
            glEnd();
        }
        for(int i=0; i<miss; i++)
        {
            glBegin (GL_QUADS);
            glTexCoord2f ( (float)2/3,1); glVertex2f (cX, offsety);
            glTexCoord2f (1, 1); glVertex2f (cX + size, offsety);
            glTexCoord2f (1, 0); glVertex2f (cX + size, offsety+size);
            glTexCoord2f ((float)2/3, 0); glVertex2f (cX, offsety + size);

            cX+=(size+offsetx);
            glEnd();
        }


        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);


    }


    public  void RenderEq(){
        Texture Grid = new Texture("./resources/crosshair.png");
        glEnable(GL_TEXTURE_2D);
        Grid.bind(0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        int cX = QABsx;
        int cY = QABsy;
        int offset = 100;
        int EqBEgX = QABsx - offset;
        float CrossSize = 30;
        boolean eqOpened = false;
        int W = APROCraft.WIDTH;
        int H = APROCraft.HEIGHT;

        if(!eqOpened){
            glBegin(GL_QUADS);

            glTexCoord2f(0, 0);
            glVertex2f((W-CrossSize)/2, (H-CrossSize)/2);

            glTexCoord2f(0, 1);
            glVertex2f((W+CrossSize)/2, (H-CrossSize)/2);

            glTexCoord2f(1, 1);
            glVertex2f((W+CrossSize)/2, (H+CrossSize)/2);

            glTexCoord2f(1, 0);
            glVertex2f((W-CrossSize)/2, (H+CrossSize)/2);

            glEnd();

        }else {
            for (int i = 0; i < SizeOfQAB; i++) {

            }
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
