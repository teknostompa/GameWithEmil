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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Button;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;




public class Main  extends JPanel  implements ActionListener, KeyListener, MouseListener, MouseWheelListener {
	private static final long serialVersionUID = 1L;
	int spawnx = -750, spawny = -100;
	int x = spawnx, y= spawny, velX = 0, velY = 0;
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
    int SelectedBlock = 1;
    int menu = 0;

    public Main() throws IOException {
        addKeyListener(this);
        addMouseWheelListener(this);
        addMouseListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);
    }

    @SuppressWarnings("unused")
	public void paint(Graphics g) {
    	if(title==0||title==2||title==3) {
	    	if(!world.equals(prevworld)) {
	    		new textures();
	    	    //ArrayList<ArrayList<Integer>> tempLayout = new ArrayList<>();
	    		tempLayout.clear();
	    		try(BufferedReader br = new BufferedReader(new FileReader("maps/"+world))){

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
    		g.drawString("Version : 1.1.0", 10, 20);
    		g.setFont(new Font("arial", Font.PLAIN, 20));
    		g.drawString("Selected Block: #" + SelectedBlock + " " + textures.names[SelectedBlock], 10, h-50);
    		g.setColor(Color.RED);
    		g.fillRoundRect(w-20-(health*10), 20, health*10, 30, 10, 10);
    		if(health==0) {
    	        button.setVisible(true);
    			title=3;
    		}
    		if(menu==1) {
    	        button.setVisible(true);
    			title=4;
    			menu=0;
    		}
    		if(title==2) {
    			Color c = new Color(0, 0, 0, 100);
    			g.setColor(c);
    			g.fillRect(7, h-65, w-20, 30);
    		}
    		if(title==3) {
    			Color c = new Color(0, 0, 0, 255);
    			g.setColor(c);
    			g.fillRect(w/2-150,	h/2-30, 300, 60);
    			g.fillRect(w/2-150,	h/2+60, 300, 60);
    			c= new Color(255,255,255);
    			g.setColor(c);
                g.setFont(new Font("arial", Font.PLAIN, 25));
    			String text="Respawn";
    			int wi =g.getFontMetrics().stringWidth(text);
    			g.drawString(text, w/2-(wi/2), h/2+5);
    			text="Back to menu";
    			wi =g.getFontMetrics().stringWidth(text);
    			g.drawString(text, w/2-(wi/2), h/2+95);
    		}
    		if(title==4) {
    			Color c = new Color(0, 0, 0, 255);
    			g.setColor(c);
    			g.fillRect(w/2-150,	h/2-30, 300, 60);
    			g.fillRect(w/2-150,	h/2+60, 300, 60);
    			c= new Color(255,255,255);
    			g.setColor(c);
                g.setFont(new Font("arial", Font.PLAIN, 25));
    			String text="Return to game";
    			int wi =g.getFontMetrics().stringWidth(text);
    			g.drawString(text, w/2-(wi/2), h/2+5);
    			text="Back to menu";
    			wi =g.getFontMetrics().stringWidth(text);
    			g.drawString(text, w/2-(wi/2), h/2+95);
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
        if (c == KeyEvent.VK_ESCAPE) {
	        menu=1;
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
        int x1 = e.getX();
        int y1=e.getY();
        if(title == 1) {
        	y1 = (y1+10)/50;
        	world = saves[y1];
        	spritesheet=null;
        	title=0;
        }
        if(title == 3) {
        	if(y1>h/2-30 && y1<h/2+30 && x1>w/2-150 && x1<w/2+150) {
            	x=spawnx;
                y=spawny;
        		health=20;
        		title=0;
        		repaint();
        	}
        	if(y1>h/2+60 && y1<h/2+90 && x1>w/2-150 && x1<w/2+150) {
        		save();
        		health=20;
        		title=1;
        		repaint();
        	}
        }
        if(title == 4) {
        	if(y1>h/2-30 && y1<h/2+30 && x1>w/2-150 && x1<w/2+150) {
        		title=0;
        		repaint();
        	}
        	if(y1>h/2+60 && y1<h/2+90 && x1>w/2-150 && x1<w/2+150) {
        		save();
        		health=20;
        		title=1;
        		repaint();
        	}
        }
        if(title == 0) {
        	if(e.getButton()==MouseEvent.BUTTON3) {
        	int tilex=x/50;
        	int tiley=y/50;
        	int mx =(x1-x%50)/50;
        	int my =(y1-y%50)/50;
        	tilex-=mx;
        	tiley-=my;
        	if(layer.map[-tiley][-tilex] == 0) {        		
        		layer.map[-tiley][-tilex] = SelectedBlock;
        	}
        }
        }
        if(title == 0 && prevworld == world) {
        	if(e.getButton()==MouseEvent.BUTTON1) {
        	int tilex=x/50;
        	int tiley=y/50;
        	int mx =(x1-x%50)/50;
        	int my =(y1-y%50)/50;
        	tilex-=mx;
        	tiley-=my;
        	if(layer.map[-tiley][-tilex] != 0) {
        		layer.map[-tiley][-tilex] = 0;
        	}
        	
        }
        }
    }

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	public void print(String s) {
		System.out.print(s);
	}
	public void printint(int i) {
		System.out.println(i);
	}
	
	public void save() {
		FileWriter fstream;
		try {
			fstream = new FileWriter("maps/"+world, false);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.flush();
		    for(int x=0; x<layer.map.length; x++) {
		    	for(int y=0; y<layer.map[0].length; y++) {
		    		String a =String.valueOf(layer.map[x][y]);
				    out.write(a+" ");
		    	}
		    	out.write("\n");
		    }
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent m) {
		int range = m.getWheelRotation();
		SelectedBlock+=range;
		if(SelectedBlock<1) {
			SelectedBlock=textures.textures.length-1;
		}
		if(SelectedBlock>textures.textures.length-1) {
			SelectedBlock=1;
		}
	}

}