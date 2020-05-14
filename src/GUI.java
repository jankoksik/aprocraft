import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    private  boolean opened=false;
    private int Hoff = 0;
    private int QABsx = (int) (APROCraft.HEIGHT - (SizeOfQAB - 1)*QABsize + QABsize*CurrMul + (SizeOfQAB - 1)*1 );//(APROCraft.HEIGHT/2 + ((SizeOfQAB+1)*1 + SizeOfQAB*QABsize)/2);
    private int QABsy = APROCraft.HEIGHT/ magicNMBR;
    private List<Item> inv = new ArrayList<>();

    public GUI(Inventory i) {
        SizeOfQAB = i.GetW();
        height = i.GetSpace()/i.GetW();
        inv = i.getEq();

    }

    public void updateEq(Inventory i){
        inv = i.getEq();
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
        Hoff = offsety + size + size/2;
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

        glEnable(GL_TEXTURE_2D);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        int cX = QABsx;
        int cY = 0;
        int offset = 300;
        int EqBEgX = QABsx - offset;
        float CrossSize = 30;
        int W = APROCraft.WIDTH;
        int H = APROCraft.HEIGHT;

        if(!opened){
            Texture Grid = new Texture("./resources/crosshair.png");
            Grid.bind(0);
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
            Texture back = new Texture("./resources/Background3.0.png");
            back.bind(0);


            glBegin(GL_QUADS);

            glTexCoord2f(1, 1);
            glVertex2f(QABsx-offset, Hoff);

            glTexCoord2f(0, 1);
            glVertex2f(QABsx+offset + SizeOfQAB *QABsize + (SizeOfQAB - 1)*1  , Hoff);

            glTexCoord2f(0, 0);
            glVertex2f(QABsx+offset + (SizeOfQAB - 1)*QABsize + QABsize*CurrMul + (SizeOfQAB - 1)*1  , H - Hoff/2 );

            glTexCoord2f(1, 0);
            glVertex2f(QABsx-offset, H - Hoff/2);

            glEnd();

            for(int y =0; y<height; y++)
            {
                int Bx = QABsx;
                int By = Hoff + offset;
                for(int x=0; x< SizeOfQAB; x++)
                {
                    Texture Grid = new Texture("./resources/ramkka.png");
                    Grid.bind(0);

                    DrawSquare(cX, Hoff +magicNMBR*2/3 + cY, QABsize, new float[] {0,1,0,1});
                    int id = 0;
                    if(!inv.isEmpty() && (y*SizeOfQAB + x) < inv.size())
                         id = inv.get(y*SizeOfQAB + x).getId();
                    if( id!= 0)
                    {
                        id-=1;
                        Texture blocks = new Texture("./resources/blocks.png");
                        blocks.bind(0);
                        DrawSquare(cX+(int)QABsize/4, Hoff +magicNMBR*2/3 + cY+(int)QABsize/4, QABsize/2,GetTexById(id) );
                    }

                    cX += QABsize +1;


                }
                cY += QABsize+1;
                cX = QABsx;

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

    private void DrawSquare(int x, int y, float size, float[] texCords){
        glBegin(GL_QUADS);

        glTexCoord2f(texCords[1],texCords[3]);
        glVertex2f(x, y);

        glTexCoord2f(texCords[0],texCords[3]);
        glVertex2f(x+size,y);

        glTexCoord2f(texCords[0],texCords[2]);
        glVertex2f(x+size, y+size);

        glTexCoord2f(texCords[1],texCords[2]);
        glVertex2f(x, y+size);

        glEnd();
    }
    private float[] GetTexById(int id){
        int x = id%8;
        int y = id/8;

        float [] cords = {
                (float)x*1/8,
                (float)x*1/8 + (float) 1/8,
                (float)y * 1/8,
                (float) y*1/8 +(float) 1/8
        };
        return  cords;
    }








}
