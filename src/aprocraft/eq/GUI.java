package aprocraft.eq;

import aprocraft.APROCraft;
import aprocraft.io.Texture;
import aprocraft.world.Blocks;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import static org.lwjgl.opengl.GL11.*;

public class GUI {
    //Quick Action Bar
    Timer timer = new Timer();
    private int height = 0;
    private static Texture Grid = new Texture("./resources/ramkka.png");
    private Font awtFont;
    private static Texture hearts = new Texture("./resources/hearts.png");
    private static Texture crosshair = new Texture("./resources/crosshair.png");
    private static Texture back = new Texture("./resources/gui_back.png");
    public static final Texture NmbTex = new Texture("./resources/font.png");


    public static int magicNMBR = 15;
    public static float QABsize = APROCraft.HEIGHT / magicNMBR;
    private static int FontSize = (int) QABsize / 4;
    private static float CurrMul = 1.1f;
    public static int SizeOfQAB = 8;
    private int currChoosed = 0;
    Numbers numCord = new Numbers();

    /**
     * Metoda odpowiadająca za otworzenie GUI
     *
     * @return
     */
    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    private boolean opened = false;
    private int Hoff = 0;
    public static int QABsx = (int) (APROCraft.HEIGHT - (SizeOfQAB - 1) * QABsize + QABsize * CurrMul + (SizeOfQAB - 1) * 1);//(aprocraft.APROCraft.HEIGHT/2 + ((SizeOfQAB+1)*1 + SizeOfQAB*QABsize)/2);
    public static int QABsy = APROCraft.HEIGHT / magicNMBR;
    private List<Item> inv = new ArrayList<>();


    public int getYOffset() {
        int offsety = QABsy + (int) QABsize + 10;
        int size = (int) (0.4f * QABsize);
        int hoff = offsety + size + size / 2;
        return hoff + magicNMBR * 2 / 3;
    }

    public GUI(Inventory i) {
        SizeOfQAB = i.GetW();
        height = i.GetSpace() / i.GetW();
        inv = i.getEq();
        awtFont = new Font("Times New Roman", Font.BOLD, FontSize);
    }

    /**
     * Metoda ustawiająca które pole
     * * @param curr
     */
    public void setCurr(int curr) {
        currChoosed = curr;
        if (currChoosed < 0) {
            currChoosed = SizeOfQAB - 1;
        }
        if (currChoosed > SizeOfQAB - 1) {
            currChoosed = 0;
        }

    }

    public int GetCurr() {
        return currChoosed;
    }

    /**
     * Metoda odpowiedzialna za wyświetlenie paska szynkiego wybierania
     */
    public void RenderQAB() {
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        int cX = QABsx;
        int cY = QABsy;
        for (int i = 0; i < SizeOfQAB; i++) {
            Grid.bind(0);
            float si = QABsize;

            if (currChoosed == i)
                si *= CurrMul;
            glBegin(GL_QUADS);

            glTexCoord2f(0, 0);
            glVertex2f(cX, QABsy);

            glTexCoord2f(0, 1);
            glVertex2f(cX + si, QABsy);

            glTexCoord2f(1, 1);
            glVertex2f(cX + si, QABsy + si);

            glTexCoord2f(1, 0);
            glVertex2f(cX, QABsy + si);


            glEnd();
            int id = -1;
            if (!inv.isEmpty() && inv.size() > i)
                id = inv.get(i).getId();
            if (id != -1) {
                id -= 1;
                //blocks.bind(0);
                Blocks.TEXTURE_PACK.bind(0);
                DrawSquare(cX + (int) si / 4, QABsy + (int) si / 4, si / 2, GetTexById(id));
                DrawNumber(cX + (int) si / 2, QABsy + (int) si / 4, inv.get(i).getSize());
            }
            cX += (si + 1);
        }
        cX = 0;
        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
    }

    /**
     * Metoda odpowiadająca za wyśietlanie paskuy życia
     *
     * @param health aktualny poziom życia gracza
     */
    public void RenderHealth(int health) {
        int maxH = 20;
        if (maxH < health)
            health = maxH;
        if (health < 0)
            health = 0;

        glEnable(GL_TEXTURE_2D);
        hearts.bind(0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        int offsety = QABsy + (int) QABsize + 10;
        int size = (int) (0.4f * QABsize);
        Hoff = offsety + size + size / 2;
        int offsetx = 1;
        int cX = QABsx;
        int full = health / 2;
        int miss = (maxH - health) / 2;
        for (int i = 0; i < full; i++) {
            glBegin(GL_QUADS);
            glTexCoord2f((float) 0, 1);
            glVertex2f(cX, offsety);
            glTexCoord2f((float) 1 / 3, 1);
            glVertex2f(cX + size, offsety);
            glTexCoord2f((float) 1 / 3, 0);
            glVertex2f(cX + size, offsety + size);
            glTexCoord2f((float) 0, 0);
            glVertex2f(cX, offsety + size);

            cX += (size + offsetx);
            glEnd();
        }
        if (health % 2 != 0) {
            glBegin(GL_QUADS);
            glTexCoord2f((float) 1 / 3, 1);
            glVertex2f(cX, offsety);
            glTexCoord2f((float) 2 / 3, 1);
            glVertex2f(cX + size, offsety);
            glTexCoord2f((float) 2 / 3, 0);
            glVertex2f(cX + size, offsety + size);
            glTexCoord2f((float) 1 / 3, 0);
            glVertex2f(cX, offsety + size);

            cX += (size + offsetx);
            glEnd();
        }
        for (int i = 0; i < miss; i++) {
            glBegin(GL_QUADS);
            glTexCoord2f((float) 2 / 3, 1);
            glVertex2f(cX, offsety);
            glTexCoord2f(1, 1);
            glVertex2f(cX + size, offsety);
            glTexCoord2f(1, 0);
            glVertex2f(cX + size, offsety + size);
            glTexCoord2f((float) 2 / 3, 0);
            glVertex2f(cX, offsety + size);

            cX += (size + offsetx);
            glEnd();
        }


        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);


    }

    public void SetEq(List<Item> a) {
        inv = a;
    }

    /**
     * Metoda opdpowiadająca za odśiwrzanie stanu ekwipunku
     *
     * @param inventory ewkipunke wskazanego gracza
     */
    public void updateEq(List<Item> inventory) {
//        for(Item i : inv) {
//            if(!inventory.contains(i))
//
//        }
        for (Iterator<Item> it = inv.iterator(); it.hasNext(); ) {
            Item i = it.next();
            if (!inventory.contains(i))
                it.remove(); //metoda remove() iteratora
        }

        for (Item i : inventory) {
            if (!inv.contains(i))
                inv.add(i);
        }
    }

    /**
     * Metoda odpowiedzialan za wyświetlanie ewkipunku
     */
    public void RenderEq() {

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

        if (!opened) {

            crosshair.bind(0);
            glBegin(GL_QUADS);

            glTexCoord2f(0, 0);
            glVertex2f((W - CrossSize) / 2, (H - CrossSize) / 2);

            glTexCoord2f(0, 1);
            glVertex2f((W + CrossSize) / 2, (H - CrossSize) / 2);

            glTexCoord2f(1, 1);
            glVertex2f((W + CrossSize) / 2, (H + CrossSize) / 2);

            glTexCoord2f(1, 0);
            glVertex2f((W - CrossSize) / 2, (H + CrossSize) / 2);

            glEnd();

        } else {
            back.bind(0);


            glBegin(GL_QUADS);

            glTexCoord2f(1, 1);
            glVertex2f(QABsx - offset*0.7f, Hoff);

            glTexCoord2f(0, 1);
            glVertex2f(QABsx + offset*0.7f + SizeOfQAB * QABsize + (SizeOfQAB - 1) * 1, Hoff);

            glTexCoord2f(0, 0);
            glVertex2f(QABsx + offset*0.7f + SizeOfQAB * QABsize + (SizeOfQAB - 1) * 1, H - Hoff / 2);

            glTexCoord2f(1, 0);
            glVertex2f(QABsx - offset*0.7f, H - Hoff / 2);

            /*System.out.println("width: " + (QABsx + offset*0.7f + SizeOfQAB * QABsize + (SizeOfQAB - 1) * 1 - (QABsx - offset*0.7f)) +
                    "\nheight: " + (H - Hoff / 2 - Hoff));*/

            glEnd();

            for (int y = 0; y < height; y++) {
                //int Bx = QABsx;
                //int By = Hoff + offset;
                for (int x = 0; x < SizeOfQAB; x++) {
                    Grid.bind(0);


                    DrawSquare(cX, Hoff + magicNMBR * 2 / 3 + cY, QABsize, new float[]{0, 1, 0, 1});
                    int id = -1;
                    if (!inv.isEmpty() && inv.size() > y * SizeOfQAB + x)
                        id = inv.get(y * SizeOfQAB + x).getId();
                    if (id != -1) {
                        id -= 1;
                        //blocks.bind(0);
                        Blocks.TEXTURE_PACK.bind(0);
                        DrawSquare(cX + (int) QABsize / 4, Hoff + magicNMBR * 2 / 3 + cY + (int) QABsize / 4, QABsize / 2, GetTexById(id));
                        DrawNumber(cX + (int) QABsize / 2, Hoff + magicNMBR * 2 / 3 + cY + (int) QABsize / 4, inv.get(y * SizeOfQAB + x).getSize());
                    }
                    cX += QABsize + 1;


                }
                cY += QABsize + 1;
                cX = QABsx;

            }

            /*for(Item i : inv) {
                int id = i.getId();
                if (id != -1) {
                    id -= 1;
                    //blocks.bind(0);
                    Blocks.TEXTURE_PACK.bind(0);
                    System.out.println(i.pos);
                    DrawSquare(QABsx + i.pos*(int)QABsize + (int) QABsize / 4, Hoff + magicNMBR * 2 / 3 + (int)(i.pos/QABsize) + (int) QABsize / 4, QABsize / 2, GetTexById(id));
                    DrawNumber(QABsx + i.pos*(int)QABsize + (int) QABsize / 2, Hoff + magicNMBR * 2 / 3 + (int)(i.pos/QABsize) + (int) QABsize / 4, i.getSize() );
                }
            }*/

            /*Grid.bind(0);
            DrawSquare(QABsx + (int) QABsize * 5 + 4 + 4 * magicNMBR, QABsy + 4 * Hoff - (int) QABsize - 3 * magicNMBR - 1, QABsize, new float[]{0, 1, 0, 1});
            DrawSquare(QABsx + (int) QABsize * 5 + 4 + 4 * magicNMBR, QABsy + 4 * Hoff - 3 * magicNMBR, QABsize, new float[]{0, 1, 0, 1});
            DrawSquare(QABsx + (int) QABsize * 6 + 4 + 4 * magicNMBR + 1, QABsy + 4 * Hoff - 3 * magicNMBR, QABsize, new float[]{0, 1, 0, 1});
            DrawSquare(QABsx + (int) QABsize * 6 + 4 + 4 * magicNMBR + 1, QABsy + 4 * Hoff - (int) QABsize - 3 * magicNMBR - 1, QABsize, new float[]{0, 1, 0, 1});
            DrawSquare(QABsx + (int) QABsize * 8 + 4 + 4 * magicNMBR, QABsy + 4 * Hoff - 3 * magicNMBR - (int) QABsize / 2, QABsize, new float[]{0, 1, 0, 1});
            */

        }
        cX = 0;
        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
    }

    /**
     * Metoda odpowiadająca za wyświtlanie  przedmiotu obecnie trzymanego przez gracza
     *
     * @param x    współrzedna x trzymanego obiektu w ekwipunku
     * @param y    współrzedna y trzymanego obiektu w ekwipunku
     * @param item typ trzymanego biektu
     */
    public void renderGrabbedItem(int x, int y, Item item) {
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        Blocks.TEXTURE_PACK.bind(0);
        DrawSquare((int) (x - QABsize / 4), (int) (y - QABsize / 4), QABsize / 2, GetTexById(item.getId() - 1));
        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
    }

    /**
     * Metoda rusująca kwadraty (są ona później wykorzystywane do konstrukcji wizualnej części ekwipunku,
     * okna craftingu i obietków w ewkipunku
     *
     * @param x        współrzędna x kwadratu
     * @param y        współrzędna y kwadratu
     * @param size     rozmiary kwadrtu
     * @param texCords
     */
    private void DrawSquare(int x, int y, float size, float[] texCords) {
        glBegin(GL_QUADS);

        glTexCoord2f(texCords[1], texCords[3]);
        glVertex2f(x, y);

        glTexCoord2f(texCords[0], texCords[3]);
        glVertex2f(x + size, y);

        glTexCoord2f(texCords[0], texCords[2]);
        glVertex2f(x + size, y + size);

        glTexCoord2f(texCords[1], texCords[2]);
        glVertex2f(x, y + size);

        glEnd();
    }

    /**
     * Zwraca kordy textury po id
     *
     * @param id identyfikator wskazanej tekstury
     * @return
     */
    private float[] GetTexById(int id) {
        int x = (id) % 16;
        int y = (id) / 16;

        float[] cords = {
                (float) x * 1 / 16,
                (float) x * 1 / 16 + (float) 1 / 16,
                (float) y * 1 / 16,
                (float) y * 1 / 16 + (float) 1 / 16
        };
        return cords;
    }

    /**
     * Metoda wyświetlająca okno craftingu
     *
     * @param crafting
     */
    public void renderCrafting(Crafting crafting) {
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        int xS = QABsx + (int) QABsize * 5 + 4 + 4 * magicNMBR;
        int yS = QABsy + 4 * Hoff - (int) QABsize - 3 * magicNMBR - 1;
        int w = crafting.w;
        int h = crafting.craft.length / crafting.w;
        //int xS = APROCraft.WIDTH / 2 - (int) QABsize * w;
        //int yS = APROCraft.HEIGHT / 2 - (int) QABsize * h / 2;
        Grid.bind(0);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                DrawSquare(xS + (int) QABsize * x + x, yS + (int) QABsize * y + y, QABsize, new float[]{0, 1, 0, 1});
            }
        }
        DrawSquare(xS + (int) QABsize * (w + 2) + w, yS + (int) QABsize * h / 2 + h / 2, QABsize, new float[]{0, 1, 0, 1});

        Blocks.TEXTURE_PACK.bind(0);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int id = crafting.craft[x + y * w];
                if (id != 0)
                    DrawSquare(xS + (int) QABsize * x + x + (int) QABsize / 4, yS + (int) QABsize * y + y + (int) QABsize / 4, QABsize / 2, GetTexById(id - 1));
            }
        }

        int id = crafting.ShowPatternMatchinResult();
        if (id != -1) {
            DrawSquare(xS + (int) QABsize * (w + 2) + w + (int) QABsize / 4, yS + (int) QABsize * h / 2 + h / 2 + (int) QABsize / 4, QABsize / 2, GetTexById(id - 1));
            DrawNumber(xS + (int) QABsize * (w + 2) + w + (int) QABsize / 2, yS + (int) QABsize * h / 2 + h / 2 + (int) QABsize / 4, crafting.getNmbrOfItems());
        }

        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
    }

    /**
     * Metoda pozwalająca rysować numery
     *
     * @param x      współrzędna x rysowanego numeru
     * @param y      współrzędna y rysowanego numeru
     * @param number rysowany numer
     */
    public void DrawNumber(int x, int y, int number) {
        glColor3f(0, 0,0);
        drawNumber(x+1, y, number);
        drawNumber(x-1, y, number);
        drawNumber(x, y-1, number);
        drawNumber(x, y+1, number);
        drawNumber(x+1, y+1, number);
        drawNumber(x+1, y-1, number);
        drawNumber(x-1, y-1, number);
        drawNumber(x-1, y+1, number);
        glColor3f(1, 1,1);
        drawNumber(x, y, number);//
    }

    private void drawNumber(int x, int y, int number) {
        NmbTex.bind(0);
        int scnd = number % 10;
        int fst = number / 10;

        if (fst > 0) {
            int[] coord = Numbers.getNmbrs().get(fst);
            glBegin(GL_QUADS);

            glTexCoord2f((float) ((float) coord[0] / (float) Numbers.getW()), (float) ((float) coord[1] / (float) Numbers.getH()));
            glVertex2f(x, y + FontSize);

            glTexCoord2f((float) (((float) coord[0] + (float) coord[2]) / (float) Numbers.getW()), (float) ((float) coord[1] / (float) Numbers.getH()));
            glVertex2f(x + FontSize, y + FontSize);

            glTexCoord2f((float) (((float) coord[0] + (float) coord[2]) / (float) Numbers.getW()), (float) (((float) coord[1] + (float) coord[3]) / (float) Numbers.getH()));
            glVertex2f(x + FontSize, y);


            glTexCoord2f((float) ((float) coord[0] / (float) Numbers.getW()), (float) (((float) coord[1] + (float) coord[3]) / (float) Numbers.getH()));
            glVertex2f(x, y);

            glEnd();
        }
        int[] coord = Numbers.getNmbrs().get(scnd);
        glBegin(GL_QUADS);

        glTexCoord2f((float) ((float) coord[0] / (float) Numbers.getW()), (float) ((float) coord[1] / (float) Numbers.getH()));
        glVertex2f(x + FontSize + 1, y + FontSize);

        glTexCoord2f((float) (((float) coord[0] + (float) coord[2]) / (float) Numbers.getW()), (float) ((float) coord[1] / (float) Numbers.getH()));
        glVertex2f(x + FontSize + FontSize + 1, y + FontSize);


        glTexCoord2f((float) (((float) coord[0] + (float) coord[2]) / (float) Numbers.getW()), (float) (((float) coord[1] + (float) coord[3]) / (float) Numbers.getH()));
        glVertex2f(x + FontSize + FontSize + 1, y);


        glTexCoord2f((float) ((float) coord[0] / (float) Numbers.getW()), (float) (((float) coord[1] + (float) coord[3]) / (float) Numbers.getH()));
        glVertex2f(x + FontSize + 1, y);

        glEnd();


    }

    /**
     * Metoda odpowiadająca za zamianę oiektów miejscami w ekipunku
     *
     * @param i indeks obiektu w ekwipunku
     * @param j indeks obiektu w ekwipunku
     */
    public void swap(int i, int j) {
        Item tmp = inv.get(i);
        inv.set(i, inv.get(j));
        inv.set(j, tmp);
    }

    /**
     * Mrtoda zamieniająca obiekry miejscami w ekwipunu
     *
     * @param x1 współrzedna x pierwszego biektu
     * @param y1 współrzędan y pierwszego obiektu
     * @param x2 współrzedna x drugiego biektu
     * @param y2 współrzędan y drugiego obiektu
     */
    public void swap(int x1, int y1, int x2, int y2) {
        if (!inv.isEmpty() && inv.size() > y1 * SizeOfQAB + x1 && inv.size() > y2 * SizeOfQAB + x2) {
            int i = y1 * SizeOfQAB + x1;
            int j = y2 * SizeOfQAB + x2;
            swap(i, j);
        }
    }

    /**
     * Meotda zwracająca obiekt znajdujący się na podanej współrzędnej w ekwipunku
     *
     * @param x współrzedna x obiektu
     * @param y współrzedna y obiektu
     * @return obiekt typu Item
     */
    public Item GetItemXY(int x, int y) {
        if (!inv.isEmpty() && inv.size() > y * SizeOfQAB + x)
            return inv.get(y * SizeOfQAB + x);
        else
            return null;
    }

    /**
     * Metoda zwracająca które pole na pasku szybkiego wyboru jest obecnie zaznaczone
     *
     * @return numer indeksy wybranego pola
     */
    public int GetcurrQABid() {
        if (!inv.isEmpty() && currChoosed < inv.size())
            return inv.get(currChoosed).getId();
        else
            return 0;
    }
}
