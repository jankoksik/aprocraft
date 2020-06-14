package aprocraft.io;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL13.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

/**
 * Klasa implementująca tekstury
 */

public class Texture {
    private int id;
    private int width, height;

    /**
     * Konstruktor klasy pobierający tekstury z przygotwaneo pliku
     *
     * @param filename nazwa pliku przechowującego informacje o teksturach
     */
    public Texture(String filename) {
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File(filename));
            width = bi.getWidth();
            height = bi.getHeight();
            int[] pixel_raw = new int[width * height];
            pixel_raw = bi.getRGB(0, 0, width, height, null, 0, width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

            for (int i = 0; i < height; i++) {
                for (int g = 0; g < width; g++) {
                    int pixel = pixel_raw[i * width + g];
                    pixels.put((byte) ((pixel >> 16) & 0xFF)); // czerwony
                    pixels.put((byte) ((pixel >> 8) & 0xFF)); // zielony
                    pixels.put((byte) (pixel & 0xFF)); // niebieski
                    pixels.put((byte) ((pixel >> 24) & 0xFF)); //alpha
                }

            }
            pixels.flip();
            id = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //this.id = id;
        //this.width = width;
        //this.height = height;
        /*
        IntBuffer width = BufferUtils.createIntBuffer(1) ;
        IntBuffer height = BufferUtils.createIntBuffer(1) ;
        IntBuffer comp = BufferUtils.createIntBuffer(1) ;
        ByteBuffer data = stbi_load("./resources/"+filename, width, height, comp, 4);
        id = glGenTextures();
        this.width = width.get();
        this.height = height.get();
        glBindTexture(GL_TEXTURE_2D,id);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        stbi_image_free(data);
        */

    }

    /**
     * Metoda przydzielająca teksturę
     *
     * @param sampler
     */
    public void bind(int sampler) {
        if (sampler >= 0 && sampler <= 31) {
            glActiveTexture(GL_TEXTURE0 + sampler);
            glBindTexture(GL_TEXTURE_2D, id);
        }
    }

    /**
     * Metoda zwracjąca szerokość tekstury
     *
     * @return szerokość
     */
    public int getWidth() {
        return width;
    }

    /**
     * Metoda zwracjąca wysokość tekstury
     *
     * @return wysokość
     */
    public int getHeight() {
        return height;
    }

    /*public void bind(Shader shader) {
        glBindTexture(GL_TEXTURE_2D, id);
        shader.setUseTexture(true);
    }

    public static void unbind(Shader shader) {
        glBindTexture(GL_TEXTURE_2D, 0);
        shader.setUseTexture(false);
    }*/
}
