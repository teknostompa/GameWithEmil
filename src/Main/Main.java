package Main;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;




@SuppressWarnings({ "serial", "unused" })
public class Main  extends JPanel  implements ActionListener, KeyListener {

    Timer tm = new Timer(5, this);
    int x = 0, y=0, velX = 0, velY = 0;
    int posx=0, negx=0, posy=0, negy=0;
    static int h = 600;
	static int w = 600;
	int grav;
	

    public Main() throws IOException {
        tm.start();
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);
    }


    public void paint(Graphics g) {
    	BufferedImage image = null;
		try {
			image = ImageIO.read(new File("a.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);
        g.setColor(Color.blue);
        g.fillOval(w/2-25, h/2-25, 50, 50);
        g.setColor(Color.black);
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
		layer = new TileLayer(width, height);
		for(int a = 0; a<height; a++) {
			for(int b = 0; b<width;b++) {
				layer.map[a][b] = tempLayout.get(a).get(b);
				if(layer.map[a][b] != 0) {
			        g.drawImage(image, x+(50*b), y+(50*a), 50, 50, null);
				}
			}
			
		}
    }


    public void actionPerformed(ActionEvent e) {  
        velX=posx-negx;
        velY=posy-negy;
        x = x + velX;
        y = y + velY;
        setFocusable(true);
        requestFocusInWindow();
        repaint();
    }
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();

        if (c == KeyEvent.VK_A) {
            posx = 1;
        }

        if (c == KeyEvent.VK_D) {
            negx = 1;
        }

        if (c == KeyEvent.VK_W) {
            posy = 1;
        }

        if (c == KeyEvent.VK_S) {
            negy = 1;
        }
    }
	public void keyReleased(KeyEvent e) {
	    int c = e.getKeyCode();
		if (c == KeyEvent.VK_A) {
	        posx = 0;
	    }
	
	    if (c == KeyEvent.VK_D) {
	        negx = 0;
	    }
	
	    if (c == KeyEvent.VK_W) {
	        posy = 0;
	    }
	
	    if (c == KeyEvent.VK_S) {
	        negy = 0;
	    }
	}
    @Override
    public void keyTyped(KeyEvent e) {

    }
    public static void main(String[] args) throws IOException {
        Main t = new Main();
        JFrame frame = new JFrame();
        frame.setSize(w, h);
        frame.setTitle("Movement");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(t);
    }
    
}