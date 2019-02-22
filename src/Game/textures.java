package Game;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class textures {
	static BufferedImage[] textures = new BufferedImage[600];
	public textures() {
		
		int ci = 1;
		final File dir = new File("textures");
		for (final File f : dir.listFiles()) {
            try {
                textures[ci] = ImageIO.read(f);
            } catch (final IOException e) {
            }
            ci+=1;
        }

	}
}
