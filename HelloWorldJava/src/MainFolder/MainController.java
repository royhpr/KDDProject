package MainFolder;

public class MainController {
	private static MainController instance;
	private FeatureFilterController filterCommandCenter;
	private TrainController trainCommandCenter;
	private TestController testCommandCenter;
	
	private MainController(){
		//Initialize all sub-controllers
		filterCommandCenter = FeatureFilterController.initInstance();
		trainCommandCenter = TrainController.initInstance();
		testCommandCenter = TestController.initInstance();
	}
	
	public static MainController initInstance(){
		if(instance == null){
			instance = new MainController();
		}
		
		return instance;
	}
	
	public void filterFeatures(int ratio){
		try{
			filterCommandCenter.filterFeatures(ratio);
		}
		catch(Exception ex){
			throw ex;
		}
		finally{
			
		}
	}
	
	public void TrainDataSet(){
		try{
			trainCommandCenter.TrainDataset();
		}
		catch(Exception ex){
			throw ex;
		}
		finally{
			
		}
	}
	
	public void TestDataSet(){
		try{
			testCommandCenter.TestDataset();
		}
		catch(Exception ex){
			throw ex;
		}
		finally{
			
		}
	}
}
