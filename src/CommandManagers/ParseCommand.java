package CommandManagers;

import Game.Main;

public class ParseCommand {
	@SuppressWarnings("unused")
	public static void parseCommand(String CMD) {
		Commands CMDS = new Commands();
		
			
		if(CMD.charAt(0)=='/') {
			int f;
			String[] split = CMD.split("\\s+");
			String check = "";
			for(int i=0; i<CMDS.commands[0].length; i++) {
				split[0] = split[0].replace("/", "");
				String cmdCheck = CMDS.commands[0][i];
				if(split[0].length() >= cmdCheck.length()) {
					check =split[0].substring(0, cmdCheck.length());
					
					if(check.equals(cmdCheck)) {
						for(f = 0; f<CMDS.commands[1].length; f++) {
							if(CMDS.commands[i+1][f].equals("1")) {
								f+=1;
								break;
							}else if(CMDS.commands[i+1][f].equals("no")) {
								f=0;
								break;
							}
						}
						if(f==split.length-1) {
							CMDS.runCommand(split, cmdCheck);
						}else{
						}
					}
				}
			}
		}else {
			Main.print(CMD);
		}
	}
}
