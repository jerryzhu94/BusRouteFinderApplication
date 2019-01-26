/** 
 * TextParser is a utility class that uses Regular expression(regex) 
 * to extract specific information from a given html text.
 * This class was created to seperate abstraction from its implementation to
 * promote maintainable and reusable code.
 * The core functionalities that this class provides are as follow:
 * 1) Get a List of community names 
 * 2) Get a HashMap of route numbers(Key) to route URL path(Value)
 * 3) Get a List of destinations of a route (Will contain 2 destinations)
 * 4) Get a HashMap of stop number(Key) to stop location(Value)
 * Please note that functions 1) and 2) are used for parsing html text from
 * the community transit site. Functions 3) and 4) are used for parsing html
 * text from the bus stop information site
*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {

	//Constructor
	public TextParser(String text) {
		TextParser.m_text = text;
	}

	//Returns a List of community names from the community transit html text
	public ArrayList<String> getCommunityNames()
	{
		ArrayList<String> communityNames = new ArrayList<String>();
		Pattern pattern = Pattern.compile("<h3>(.*)</h3>");
		m_matcher = pattern.matcher(m_text);
		while(m_matcher.find())
			communityNames.add(m_matcher.group(1));
		return communityNames;
	}
	
	//Returns a HashMap containing Route information on a specified community
	//Key: Route number
	//Value: URL path to used to access more information about Route
	public HashMap<String, String> getRouteInfo(String communityName)
	{
		HashMap<String, String> routeUrlPathByNumber = new HashMap<String,String>();
		//Get the child html element of community(Route information)
		String routeInfoText = "";
		communityName = communityName.replace("(", "\\(").replace(")", "\\)");;
		Pattern pattern = Pattern.compile("<h3>" + communityName + "</h3>(?s)(.+?)<(hr|p)");
		m_matcher = pattern.matcher(m_text);
		if(m_matcher.find())
			routeInfoText = m_matcher.group(1);
		//System.out.println(routeInfoText);
		
		//Get route urlPath and number
		pattern = Pattern.compile("schedules(.*)\".*>(.*)</a>");
		m_matcher = pattern.matcher(routeInfoText);
		while(m_matcher.find())
			routeUrlPathByNumber.put(m_matcher.group(2), m_matcher.group(1));
		
		return routeUrlPathByNumber;
	}
	
	//Returns a List of destinations of a route. 
	//In this project, two destinations will always get returned
	public ArrayList<String> getDestinations()
	{
		ArrayList<String> destinations = new ArrayList<String>();
		Pattern pattern = Pattern.compile("<h2>Weekday<small>(.*)</small>");
		m_matcher = pattern.matcher(m_text);
		while(m_matcher.find())
		{
			destinations.add(m_matcher.group(1));
		}
		return destinations;
	}
	
	//Returns a HashMap containing stop information on a route to a specified destination
	//Key: Stop number
	//Value: Stop location 
	public HashMap<String, String> getStopInfo(String destination)
	{
		HashMap<String, String> stopLocationByNumber = new HashMap<String, String>();
		String stopInfoText = "";
		//Get stopInfoText(child html element)
		Pattern pattern = Pattern.compile("<h2>Weekday<small>" + destination +"</small>(?s)(.+?)</thead>");
		m_matcher = pattern.matcher(m_text);
		if(m_matcher.find())
			stopInfoText = m_matcher.group(1);
		
		pattern = Pattern.compile("1x\">(.*)<(?s).+?<p>(.+?)</p>");
		m_matcher = pattern.matcher(stopInfoText);
		while(m_matcher.find())
		{
			stopLocationByNumber.put(m_matcher.group(1), m_matcher.group(2).replace("&amp;", "&"));
		}
		return stopLocationByNumber;
	}
	
	private static String m_text;
	private Matcher m_matcher;
}
