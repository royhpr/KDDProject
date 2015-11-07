package MainFolder;

public class TestController {
	private static TestController instance;
	
	private TestController(){
		
	}
	
	public static TestController initInstance(){
		if(instance == null){
			instance = new TestController();
		}
		
		return instance;
	}
	
	//Test process will happen here
	public void TestDataset(){
		try{
			MessageController.logToConsole("Hey, I'm from test button");
			
			//All test login go here...
			
			
			
		}
		catch(Exception ex){
			throw ex;
		}
		finally{
			
		}
	}
}
