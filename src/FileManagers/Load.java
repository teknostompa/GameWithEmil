package FileManagers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Game.Main;

public class Load {
	public static void load() {
		
		Main.tempLayout.clear();
		try(BufferedReader br = new BufferedReader(new FileReader("maps/"+Main.world))){

			String currentLine;
			while((currentLine = br.readLine()) != null) {
				if(currentLine.isEmpty()) {
					continue;
				}
				ArrayList<Integer> row = new ArrayList<>();
				
				String[] values = currentLine.trim().split(" ");
				
				for(String string : values) {
					if(!string.isEmpty()) {
						int id = Integer.parseInt(string);
						
						row.add(id);
					}
				}
				Main.tempLayout.add(row);
			}
		}
		catch(IOException e) {
			
		}
		int width = Main.tempLayout.get(0).size();
		int height = Main.tempLayout.size();
		Main.map = new int[height][width];
		for(int a = 0; a<height; a++) {
			for(int b = 0; b<width;b++) {
				Main.map[a][b] = Main.tempLayout.get(a).get(b);
			}
		}
		Main.x = -750; 
		Main.y=-100;
		Main.prevworld=Main.world;
	}
}
