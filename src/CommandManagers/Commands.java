package CommandManagers;

import Game.Main;

public class Commands {
	public String[][] commands = new String[][] {
		{"setblock", "goto", "heal"},
		{"0", "0", "1"},
		{"0","1"},
		{"no"}
	};
	public void runCommand(String[] a, String b) {
		try {
		if(b == commands[0][0]) {
		int c=Integer.parseInt(a[1]);
		int d=Integer.parseInt(a[2]);
		int e=Integer.parseInt(a[3]);
		if(c>=0 && d>=0 && e>=0)
			Main.map[c][d] = e;
		}else if(b==commands[0][1]) {
			int c=Integer.parseInt(a[1]);
			int d=Integer.parseInt(a[2]);
			Main.x=c*-50+Main.h/2-25;
			Main.y=d*-50+Main.w/2-25;
		}else if(b==commands[0][2]) {
			Main.health=20;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
