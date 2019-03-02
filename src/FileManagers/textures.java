package FileManagers;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class textures {
	public static BufferedImage[] textures;
	public static String[] names;
	public textures() {
		final File dir = new File("textures");
		int a = 1;
		for (@SuppressWarnings("unused") final File f : dir.listFiles()) {
            a+=1;
        }
		names = new String[a];
		textures = new BufferedImage[a];
		int ci = 1;
		for (final File f : dir.listFiles()) {
            try {
            	names[ci] = f.getName();
                textures[ci] = ImageIO.read(f);
            } catch (final IOException e) {
            }
            ci+=1;
        }
	}
}
