package MainFolder;

import javax.swing.SwingUtilities;

interface MessageInterface {	
	public void writeToConsole(String data);
}

public class MessageController {
	private static MessageInterface messageDel = MainScreen.initInstance();
	
	public static void logToConsole(String data){
		//Invoke GUI thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				messageDel.writeToConsole(data);
			}
		});
	}
}
