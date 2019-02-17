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
	
	private static void changeColor(
	        BufferedImage imgBuf,
	        int oldRed, int oldGreen, int oldBlue,
	        int newRed, int newGreen, int newBlue) {

	    int RGB_MASK = 0x00ffffff;
	    int ALPHA_MASK = 0xff000000;

	    int oldRGB = oldRed << 16 | oldGreen << 8 | oldBlue;
	    int toggleRGB = oldRGB ^ (newRed << 16 | newGreen << 8 | newBlue);

	    int w = imgBuf.getWidth();
	    int h = imgBuf.getHeight();

	    int[] rgb = imgBuf.getRGB(0, 0, w, h, null, 0, w);
	    for (int i = 0; i < rgb.length; i++) {
	        if ((rgb[i] & RGB_MASK) == oldRGB) {
	            rgb[i] ^= toggleRGB;
	        }
	    }
	    imgBuf.setRGB(0, 0, w, h, rgb, 0, w);
	}

    Timer tm = new Timer(100, this);
    int x = -800, y=-100, velX = 0, velY = 0;
    int posx=0, negx=0, posy=0, negy=0;
    static int h = 800;
	static int w = 800;
	int grav;
	BufferedImage spritesheet = null;
	BufferedImage image = null;
    ArrayList<ArrayList<Integer>> tempLayout = new ArrayList<>();
	TileLayer layer =null;
	

    public Main() throws IOException {
        tm.start();
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);
    }


    public void paint(Graphics g) {
    	tm.start();
    	if(spritesheet==null) {
    		textures textures = new textures();
    		try {
    			spritesheet = ImageIO.read(new File("ss.png"));
    		} catch (IOException e) {
    			e.printStackTrace();
    		}    		
    		
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
    		
    	}
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);
        g.setColor(Color.blue);
        g.fillRect(w/2-25, h/2-25, 50, 50);
        g.setColor(Color.black);

		
		int width = tempLayout.get(0).size();
		int height = tempLayout.size();
		layer = new TileLayer(width, height);
		for(int a = 0; a<height; a++) {
			for(int b = 0; b<width;b++) {
				layer.map[a][b] = tempLayout.get(a).get(b);
					if(layer.map[a][b] !=0) {
						int img= layer.map[a][b];
						String c = "bedrock";
						g.drawImage(textures.textures[layer.map[a][b]], x+(50*b), y+(50*a), 50, 50, null);
				    }
				}
			}
		int xpos=(x-h/2-25)/-50;
		int ypos=(y-w/2-25)/-50;
		velX=posx-negx;
        velY=posy-negy;
        int movex = 1;
        int movey = 1;
        for(int yp=-24; yp<25;yp++) {
        	xpos=(x-h/2-25)/-50;
			ypos=(y-w/2+yp)/-50;
            if(layer.map[ypos][xpos]!=0) {
    	    	if(velX<0) {
    	    		movex=0;
    	    	}
            	
    	    }
    		xpos=(x-h/2+25)/-50;
    		ypos=(y-w/2+yp)/-50;
            if(layer.map[ypos][xpos]!=0) {
    	    	if(velX>0) {
    	    		movex=0;
    	    	}
    	    }
        }
        if(movex == 1) {
	        x = x + velX;
        }
        for(int xp=-24; xp<25;xp++) {
        	xpos=(x-h/2+xp)/-50;
			ypos=(y-w/2-25)/-50;
            if(layer.map[ypos][xpos]!=0) {
    	    	if(velY<0) {
    	    		movey=0;
    	    	}
    	    }
    		xpos=(x-h/2+xp)/-50;
    		ypos=(y-w/2+25)/-50;
            if(layer.map[ypos][xpos]!=0) {
    	    	if(velY>0) {
    	    		movey=0;
    	    	}
    	    }
        }
        if(movey==1) {
        	y = y + velY;
        }
        setFocusable(true);
        requestFocusInWindow();
        repaint();
		}


    public void actionPerformed(ActionEvent e) {
        
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