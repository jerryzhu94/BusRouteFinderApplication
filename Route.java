/** 
 * Route is a storage class with the capability of printing out 
 * information that the user may require.
 * The core functionalities that this class provides are as follow:
 * 1) Get the URL path of the route
 * 2) Set the destinations of the route
 * 3) Set the stop number and its corresponding stop location
 * 4) Print route number
 * 5) Print destinations, stop numbers, and stop locations
*/

import java.util.HashMap;
import java.util.Map;

public class Route {

	public Route(String number, String urlPath) {
		// TODO Auto-generated constructor stub
		this.m_number = number;
		this.m_urlPath = urlPath;
	}
	
	public String getUrlPath()
	{
		return this.m_urlPath;
	}
	
	public void setDestinations(String destination_1, String destination_2)
	{
		this.m_destination_1 = destination_1;
		this.m_destination_2 = destination_2;
	}
	
	public void setStopInfo(HashMap<String, String> stopLocationByNumber_1, HashMap<String, String> stopLocationByNumber_2 )
	{
		this.m_stopLocationByNumber_1 = stopLocationByNumber_1;
		this.m_stopLocationByNumber_2 = stopLocationByNumber_2;		
	}
	
	public void printRouteNumber()
	{
		System.out.println("Route number: " + m_number);
	}
	
	//Prints stop information at the user's request
	public void printStopInfo()
	{
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Destination: " + m_destination_1);
		for(Map.Entry<String, String> entry : m_stopLocationByNumber_1.entrySet())
			System.out.println("Stop number: " + entry.getKey() + " is " + entry.getValue());
		System.out.println("Destination: " + m_destination_2);
		for(Map.Entry<String, String> entry : m_stopLocationByNumber_2.entrySet())
			System.out.println("Stop number: " + entry.getKey() + " is " + entry.getValue());
	}
	
	private String m_number;
	private String m_urlPath;
	private String m_destination_1;
	private String m_destination_2;
	private HashMap<String, String> m_stopLocationByNumber_1;
	private HashMap<String, String> m_stopLocationByNumber_2;
}
