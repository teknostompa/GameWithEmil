package Game;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Button;





public class Main  extends JPanel  implements ActionListener, KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	int x = -750, y=-100, velX = 0, velY = 0;
    int posx=0, negx=0, posy=0, negy=0;
    int title=1;
    static int h = 600;
	static int w = 600;
	int grav;
	int height, width;
	BufferedImage spritesheet = null;
	BufferedImage image = null;
	TileLayer layer =null;
    static JFrame frame = new JFrame();
    Button b = null;
    JButton button = new JButton("Play");
    int health = 20;
    String[] saves;
    String world;
	String prevworld = null;
    ArrayList<ArrayList<Integer>> tempLayout = new ArrayList<>();

    public Main() throws IOException {
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);
    }

    @SuppressWarnings("unused")
	public void paint(Graphics g) {
    	if(title==0||title==2) {
	    	if(!world.equals(prevworld)) {
	    		System.out.println("yes");
	    		new textures();
	    	    //ArrayList<ArrayList<Integer>> tempLayout = new ArrayList<>();
	    		tempLayout.clear();
	    		try(BufferedReader br = new BufferedReader(new FileReader("maps/"+world))){

		    		System.out.println(world);
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
					}
				}
				x = -750; 
				y=-100;
				prevworld=world;
	    	}
	        super.paintComponent(g);
			
			int width = tempLayout.get(0).size();
			int height = tempLayout.size();
		    new graphics(height, width, layer, x, y, g, w, h);
			int xpos;
			int ypos;
			velX=posx-negx;
	        velY=posy-negy;
	        int movex = 1;
	        int movey = 1;
	        if(title==0) {
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
	        }
	        g.setColor(Color.black);
	        g.fillRect(w/2-25, h/2-25, 50, 50);
	        requestFocusInWindow();
	        button.setVisible(false);
    		g.drawString("Version : 1.0.8", 10, 20);
    		g.setColor(Color.RED);
    		g.fillRoundRect(w-20-(health*10), 20, health*10, 30, 10, 10);
    		if(health==0) {
    	        button.setVisible(true);
    			title=1;
    			health=20;
    		}
    		if(title==2) {
    			Color c = new Color(0, 0, 0, 100);
    			g.setColor(c);
    			g.fillRect(7, h-65, w-20, 30);
    		}
	        repaint();
    	}else if(title == 1) {
    		int cf=1;
			File dir = new File("maps");
			for(final File f : dir.listFiles()) {
    			cf+=1;
			}
			saves = new String[cf];
			cf=1;
    		for (final File f : dir.listFiles()) {
    			saves[cf]=f.getName();
    			cf+=1;
    		}
    		//frame.getContentPane().setLayout(new FlowLayout());
            //addComponentsToPane(frame.getContentPane());
            g.fillRect(0, 0, w, h);
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.PLAIN, 25));
    		cf=1;
    		dir = new File("maps");
    		for (final File f : dir.listFiles()) {
    			g.drawString(f.getName(), 100, 10+(cf*50));
    			cf+=1;
    		}
            repaint();
    	}
	}


    public void actionPerformed(ActionEvent e) {
    	Graphics g = getGraphics();
    	String action = e.getActionCommand();
	    if (action.equals("Geeks")) {
	        title=0;
            g.fillRect(0, 0, 200, h);
            g.fillRect(0, 0, w, 220);
            g.fillRect(w-200, 0, 200, h);
            g.fillRect(0, h-250, w, 250);
	        repaint();
	    }
    }
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        
        if(c == KeyEvent.VK_T && title == 0) {
        	title = 2;
        	System.out.println(title);
        }else if(c == KeyEvent.VK_T && title == 2) {
        	title = 0;
        }
        
        if(c == KeyEvent.VK_H && title==0) {
        	health -=1;
        }
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
    @Override
    public void mouseClicked(MouseEvent e) {
        e.getX();
        int y=e.getY();
        if(title == 1) {
        	y = (y+10)/50;
        	System.out.println(y);
        	world = saves[y];
        	spritesheet=null;
        	title=0;
        }
    }

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}