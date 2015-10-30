package MainFolder;

public class FeatureFilterController {
	private static FeatureFilterController instance;
	
	private FeatureFilterController(){
		
	}
	
	public static FeatureFilterController initInstance(){
		if(instance == null){
			instance = new FeatureFilterController();
		}
		
		return instance;
	}
	
	//This will be called to filter irrelevant features
	public void filterFeatures(){
		try{
			MessageController.logToConsole("Hey, I'm from filter feature button");
		}
		catch(Exception ex){
			throw ex;
		}
		finally{
			
		}
	}
}
