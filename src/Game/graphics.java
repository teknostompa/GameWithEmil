package Game;

import java.awt.Color;
import java.awt.Graphics;

import FileManagers.textures;

public class graphics {
	public graphics(int height, int width, int x, int y, Graphics g, int w, int h){
        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);
		for(int a = 0; a<height; a++) {
			for(int b = 0; b<width;b++) {
				if(Main.map[a][b] !=0) {
					g.drawImage(textures.textures[Main.map[a][b]], x+(50*b), y+(50*a), 50, 50, null);
			    }
			}
		}
	}
}
