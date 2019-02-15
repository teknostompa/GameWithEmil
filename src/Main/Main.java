package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel{
	public static int w = 600;
	public static int h = 600;
	public static int woff = w/2;
	public static int hoff = h/2;
	public static int cx = 0;
	public static int cy = 0;
	public void paint(Graphics g){
		ArrayList<ArrayList<Integer>> tempLayout = new ArrayList<>();

		TileLayer layer =null;
		try(BufferedReader br = new BufferedReader(new FileReader("World.txt"))){
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
				
				tempLayout.add(row);
			}
		}
		catch(IOException e) {
			
		}
		
		int width = tempLayout.get(0).size();
		int height = tempLayout.size();

		g.setColor(Color.white);
		g.fillRect(0,0,w,h);
		g.setColor(Color.black);
		g.fillRect(w/2-25, h/2-25, 50, 50);
		layer = new TileLayer(width, height);
		for(int y = 0; y<height; y++) {
			for(int x = 0; x<width;x++) {
				layer.map[y][x] = tempLayout.get(y).get(x);
				if(layer.map[y][x] != 0) {
					g.fillRect(x*50+cx+woff-100,y*50+cy+hoff+20,50,50);
				}
			}
			
		}
		repaint();
	}
	/*public void GameLoop(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0,0,w,h);
		g.setColor(Color.black);
		g.fillRect(w/2-25, h/2-25, 50, 50);
		this.paint(g);
	}*/
	static GraphicsConfiguration gc;
	public static void main(String[] args){
		Main f = new Main();
		JFrame frame = new JFrame(gc);
		frame.setTitle("2D Game");
		frame.setSize(w,h);
		frame.setLocation(0,0);
		frame.setVisible(true);
		frame.getContentPane().add(new Main());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		Graphics a=frame.getGraphics();
		boolean running = true;
		/*while(running == true) {
			f.GameLoop(a);
		}*/
	}

	
}

