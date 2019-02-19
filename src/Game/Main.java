package Game;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
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
import java.awt.Button;





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
    int x = -750, y=-100, velX = 0, velY = 0;
    int posx=0, negx=0, posy=0, negy=0;
    int title=1;
    static int h = 600;
	static int w = 600;
	int grav;
	BufferedImage spritesheet = null;
	BufferedImage image = null;
    ArrayList<ArrayList<Integer>> tempLayout = new ArrayList<>();
	TileLayer layer =null;
    static JFrame frame = new JFrame();
    Button b = null;
    JButton button = new JButton("Play");
	

    public Main() throws IOException {
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);
    }
    
    public void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        addAButton("Play", pane);
    }
    
    private void addAButton(String text, Container container) {
    	button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(this);
        button.setActionCommand("Geeks");
        Rectangle r = new Rectangle(200, 50);
        button.setBounds(r);
        container.add(button,BorderLayout.CENTER);
    }

    public void paint(Graphics g) {
    	if(title==0) {
	    
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
	        //g.setColor(Color.black);
			
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
	        for(int yp=-24; yp<26;yp++) {
	        	xpos=(x-h/2-25)/-50;
				ypos=(y-w/2+yp)/-50;
	            if(layer.map[ypos][xpos]!=0 && velX<0) {
	    	    	if(velX<0) {
	    	    		movex=0;
	    	    	}
	            	
	    	    }
	    		xpos=(x-h/2+26)/-50;
	    		ypos=(y-w/2+yp)/-50;
	            if(layer.map[ypos][xpos]!=0 && velX>0) {
	    	    	if(velX>0) {
	    	    		movex=0;
	    	    	}
	    	    }
	        }
	        if(movex == 1 && !(velX>0 && x==375)) {
		        x = x + velX;
	        }
	        for(int xp=-24; xp<26;xp++) {
	        	xpos=(x-h/2+xp)/-50;
				ypos=(y-w/2-25)/-50;
	            if(layer.map[ypos][xpos]!=0 && velY<0) {
	    	    	if(velY<0) {
	    	    		movey=0;
	    	    	}
	    	    }
	    		xpos=(x-h/2+xp)/-50;
	    		ypos=(y-w/2+26)/-50;
	            if(layer.map[ypos][xpos]!=0 && velY>0) {
	    	    	if(velY>0) {
	    	    		movey=0;
	    	    	}
	    	    }
	        }
	        if(movey==1 && !(velY>0 && y==375)) {
	        	y = y + velY;
	        }
	        g.setColor(Color.black);
	        g.fillRect(w/2-25, h/2-25, 50, 50);
	        requestFocusInWindow();
	        button.setVisible(false);
	        repaint();
    	}else if(title == 1) {
    		frame.getContentPane().setLayout(new FlowLayout());
            addComponentsToPane(frame.getContentPane());
            System.out.println("yes");
    	}
	}


    public void actionPerformed(ActionEvent e) {
    	String action = e.getActionCommand();
	    if (action.equals("Geeks")) {
	        title=0;
	        repaint();
	    }
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
        frame.setSize(w, h);
        frame.setTitle("Movement");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(t);
    }
    
}