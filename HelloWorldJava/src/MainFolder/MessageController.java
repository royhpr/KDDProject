package MainFolder;

public class MessageController {
	private static MessageController instance;
	
	private MessageController(){
		
	}
	
	public static MessageController initInstance(){
		if(instance == null){
			instance = new MessageController();
		}
		
		return instance;
	}
	
	public static void logToConsole(String message){
		
	}
}
