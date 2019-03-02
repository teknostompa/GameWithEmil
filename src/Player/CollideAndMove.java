package Player;

import java.awt.Graphics;

import Game.Main;
import Game.graphics;

public class CollideAndMove {
	public static void Collision(Graphics g){
		int width = Main.tempLayout.get(0).size();
		int height = Main.tempLayout.size();
	    new graphics(height, width, Main.x, Main.y, g, Main.w, Main.h);
		int xpos;
		int ypos;
		Main.velX=Main.posx-Main.negx;
		Main.velY=Main.posy-Main.negy;
        int movex = 1;
        int movey = 1;
        if(Main.title==0) {
        for(int yp=-24; yp<26;yp++) {
        	xpos=(Main.x-Main.h/2-25)/-50;
			ypos=(Main.y-Main.w/2+yp)/-50;
            if(Main.map[ypos][xpos]!=0 && Main.velX<0) {
    	    	if(Main.velX<0) {
    	    		movex=0;
    	    	}
            	
    	    }
    		xpos=(Main.x-Main.h/2+26)/-50;
    		ypos=(Main.y-Main.w/2+yp)/-50;
            if(Main.map[ypos][xpos]!=0 && Main.velX>0) {
    	    	if(Main.velX>0) {
    	    		movex=0;
    	    	}
    	    }
        }
        if(movex == 1 && !(Main.velX>0 && Main.x==375)) {
        	Main.x = Main.x + Main.velX;
        }
        for(int xp=-24; xp<26;xp++) {
        	xpos=(Main.x-Main.h/2+xp)/-50;
			ypos=(Main.y-Main.w/2-25)/-50;
            if(Main.map[ypos][xpos]!=0 && Main.velY<0) {
    	    	if(Main.velY<0) {
    	    		movey=0;
    	    	}
    	    }
    		xpos=(Main.x-Main.h/2+xp)/-50;
    		ypos=(Main.y-Main.w/2+26)/-50;
            if(Main.map[ypos][xpos]!=0 && Main.velY>0) {
    	    	if(Main.velY>0) {
    	    		movey=0;
    	    	}
    	    }
        }
        if(movey==1 && !(Main.velY>0 && Main.y==375)) {
        	Main.y = Main.y + Main.velY;
        }
        }
	}
}
