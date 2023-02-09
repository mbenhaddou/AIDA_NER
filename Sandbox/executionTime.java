

public class executionTime {

	
	double startTime;
	double endTime;
	boolean timer = false; 
	public void setTimer()
	{
		startTime = System.currentTimeMillis();
		timer = true;
	}

	public void getExecutionTime()
	{
		if(!timer)
		{
			System.out.println("Set the timer first by calling setTimer().");
		}
		else
		{
			endTime = System.currentTimeMillis();
			double totTime = (endTime-startTime)/1000;
			int hr = (int)(totTime / 3600);
			double remainder = totTime % 3600;
			int min = (int)(remainder/60);
			int sec = (int)(remainder %60);
			System.out.println("Time of execution is "+hr +" hrs "+ min +" minutes and "+sec +" secs");
		}
	}
}
