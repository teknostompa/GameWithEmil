package Main;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class textures {
	static BufferedImage[] textures;
	public textures() {
		textures = new BufferedImage[566];
		try {
			textures[1] = ImageIO.read(new File("textures/acacia_door_bottom.png"));
			textures[2] = ImageIO.read(new File("textures/acacia_door_top.png"));
			textures[3] = ImageIO.read(new File("textures/acacia_leaves.png"));
			textures[4] = ImageIO.read(new File("textures/acacia_log_top.png"));
			textures[5] = ImageIO.read(new File("textures/acacia_log.png"));
			textures[6] = ImageIO.read(new File("textures/acacia_planks.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
