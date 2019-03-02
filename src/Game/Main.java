package Game;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Player.CollideAndMove;
import CommandManagers.ParseCommand;
import FileManagers.Load;
import FileManagers.Save;
import FileManagers.textures;



public class Main  extends JPanel  implements ActionListener, KeyListener, MouseListener, MouseWheelListener {
	private static final long serialVersionUID = 1L;
	public static int spawnx = -750;
	public static int spawny = -100;
	public static int x;
	public static int y;
	public static int velX = 0;
	public static int velY = 0;
	public static int posx=0, negx=0, posy=0, negy=0;
	public static int title=1;
	public static int h = 600;
    public static int w = 600;
	public static int height, width;
	public static BufferedImage spritesheet = null;
	public static BufferedImage image = null;
	public static JFrame frame = new JFrame();
    public static Button b = null;
    public static JButton button = new JButton("Play");
    public static int health = 20;
    public static String[] saves;
    public static String world;
    public static String prevworld = null;
    public static ArrayList<ArrayList<Integer>> tempLayout = new ArrayList<>();
    public static int SelectedBlock = 1;
    public static int menu = 0;
    public static int[][] inventory = new int[4][9];
    public static String CMD ="";
    public static int[][] map;
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
	    	    Load.load();
	    	}
	        super.paintComponent(g);
			
			CollideAndMove.Collision(g);
    		Color c= new Color (0,0,0,255);
	        g.setColor(c);
	        g.fillRect(w/2-25, h/2-25, 50, 50);
	        requestFocusInWindow();
	        button.setVisible(false);
	        g.setColor(Color.black);
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
    			c = new Color(0, 0, 0, 100);
    			g.setColor(c);
    			g.fillRect(7, h-65, w-20, 30);
    			c= new Color(255,255,255);
    			g.setColor(c);
                g.setFont(new Font("arial", Font.PLAIN, 25));
    			String text=CMD;
    			g.drawString(text, 20, h-40);
    		}
    		if(title==3) {
    			c = new Color(0, 0, 0, 255);
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
    			c = new Color(0, 0, 0, 255);
    			g.setColor(c);
    			g.fillRect(w/2-150,	h/2-30, 300, 60);
    			g.fillRect(w/2-150,	h/2+60, 300, 60);
    			c= new Color(255,255,255);
    			g.setColor(c);
                g.setFont(new Font("arial", Font.PLAIN, 25));
    			String text="Return to game";
    			int wi =g.getFontMetrics().stringWidth(text);
    			g.drawString(text, w/2-(wi/2), h/2+5);
    			text="Save and back to menu";
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
        if(title==2) {
        	if(c==KeyEvent.VK_ESCAPE) {
        		title=0;
        	}else if(c==KeyEvent.VK_ENTER) {
        		ParseCommand.parseCommand(CMD);
        		title=0;
        		CMD="";
        	}else if(c==KeyEvent.VK_BACK_SPACE) {
        		CMD = CMD.substring(0, CMD.length() - 1);
        	}else if(c==KeyEvent.VK_SPACE) {
        		CMD += " ";
        	}else if(c==KeyEvent.VK_SHIFT) {
        	}else{
        		CMD+=e.getKeyChar();
        	}
        }else{
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

        if(c == KeyEvent.VK_E && title == 0) {
        	title=5;
        }
        
        if(c == KeyEvent.VK_T && title == 0) {
        	title = 2;
        }
        
        if(c == KeyEvent.VK_H && title==0) {
        	health -=1;
        }
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
        x = spawnx; y= spawny;
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
        		Save.save();
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
        		Save.save();
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
        	if(map[-tiley][-tilex] == 0) {        		
        		map[-tiley][-tilex] = SelectedBlock;
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
        	if(map[-tiley][-tilex] != 0) {
        		map[-tiley][-tilex] = 0;
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
	public static void print(String s) {
		System.out.println(s);
	}
	public void printint(int i) {
		System.out.println(i);
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