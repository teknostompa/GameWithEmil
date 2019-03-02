package FileManagers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Game.Main;

public class Save {
	public static void save() {
		FileWriter fstream;
		try {
			fstream = new FileWriter("maps/"+Main.world, false);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.flush();
		    for(int x=0; x<Main.map.length; x++) {
		    	for(int y=0; y<Main.map[0].length; y++) {
		    		String a =String.valueOf(Main.map[x][y]);
				    out.write(a+" ");
		    	}
		    	out.write("\n");
		    }
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
