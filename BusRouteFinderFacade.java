/** 
 * BusRouteFinderFacade is a class that utilizes the 
 * Community, Route, HtmlParser, and TextParser class to create 
 * the bus route finder API.
 * Because there are four independent classes being used in conjunction,
 * this class was created to hide complexity from the client code and
 * to promote loose coupling between subsystems. The design of this class
 * was based off the GoF facade pattern.
 * The core functionalities that this class provides are as follow:
 * 1) Get a List of community names 
 * 2) Get a HashMap of route numbers(Key) to route URL path(Value)
 * 3) Get a List of destinations of a route (Will contain 2 destinations)
 * 4) Get a HashMap of stop number(Key) to stop location(Value)
 * Please note that functions 1) and 2) are used for parsing html text from
 * the community transit site. Functions 3) and 4) are used for parsing html
 * text from the bus stop information site
*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BusRouteFinderFacade {

	public BusRouteFinderFacade() throws IOException {
		//Parse text to extract specific information
		parseCommunityTransitText();
	}
	
	//Prompts the user to enter an initial of the community that he/she is interested in
	public void promptUserForInitial()
	{
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		String input = "";
		char initial;
		while(true){
			System.out.print("Please enter a letter that your destinations start with: ");
			input = m_scanner.nextLine();
			//Validate user input
			if(input.length() == 1)
			{
				initial = input.charAt(0);
				break;
			}
			else
				System.out.println("Invalid entry");
		}
		//Prints all community names that start with the user-inputted initial 
		for(int i = 0; i < m_communities.size(); i++)
		{
			char communityInitial = m_communities.get(i).getName().charAt(0);
			if(Character.toUpperCase(communityInitial) == Character.toUpperCase(initial))
				m_communities.get(i).printInfo();
		}
	}
	
	//Prompts the user to enter a route number that he/she is interested in
	//Then prints the bus stop information of the route
	public void promptUserForRouteNumber() throws IOException
	{
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		String routeNumber = "";
		while(true){
			System.out.print("Please enter a route ID as a string: ");
			routeNumber = m_scanner.nextLine();
			//Validate user input
			if(m_routeByNumber.containsKey(routeNumber))
			{
				m_routeUrl = m_communityTransitUrl + m_routeByNumber.get(routeNumber).getUrlPath();
				break;
			}
			else
				System.out.println("Invalid route number");
		}
		
		System.out.println("The link for your route is: " + m_routeUrl);
		parseRouteStopText(routeNumber);
		m_routeByNumber.get(routeNumber).printStopInfo();
		
	}
	
	//Parses the html text of the community transit site
	private void parseCommunityTransitText() throws IOException
	{
		//Initialize htmlParser object
		m_htmlParser =  new HtmlParser(m_communityTransitUrl);
		//Initialize textParser object
		m_textParser = new TextParser(HtmlParser.getText());
		ArrayList<String> communityNames = m_textParser.getCommunityNames();
		for(int i = 0; i < communityNames.size(); i++)
		{
			//Create community object and add it to HashMap(m_communityByInitial)
			Community newCommunity = new Community(communityNames.get(i));
			m_communities.add(newCommunity);
			//Extract route information using TextParser object
			HashMap<String, String> routeInfo = m_textParser.getRouteInfo(communityNames.get(i));
			for(Map.Entry<String, String> routeEntry : routeInfo.entrySet())
			{
				String routeNumber = routeEntry.getKey();
				String routeUrlPath = routeEntry.getValue();
		
				//Check whether this route exists in the HashMap(m_routeByNumber)
				if(!m_routeByNumber.containsKey(routeNumber))
				{
					//Create route object and add to HashMap(m_routeByNumber)
					Route newRoute = new Route(routeNumber, routeUrlPath);
					m_routeByNumber.put(routeNumber, newRoute);
					newCommunity.addRoute(newRoute);
				}
				else
					newCommunity.addRoute(m_routeByNumber.get(routeNumber));
			}
		}
	}
	
	//Parses the html text of the stop information site
	private void parseRouteStopText(String routeNumber) throws IOException
	{
		Route chosenRoute = m_routeByNumber.get(routeNumber);
		m_htmlParser = new HtmlParser(m_routeUrl);
		m_textParser = new TextParser(HtmlParser.getText());
		String destination_1 = m_textParser.getDestinations().get(0);
		String destination_2 = m_textParser.getDestinations().get(1);
		chosenRoute.setDestinations(destination_1, destination_2);
		HashMap<String, String> stopInfo_1 = m_textParser.getStopInfo(destination_1);
		HashMap<String, String> stopInfo_2 = m_textParser.getStopInfo(destination_2);
		chosenRoute.setStopInfo(stopInfo_1, stopInfo_2);
	}

	//Variables
	private String m_communityTransitUrl = "https://www.communitytransit.org/busservice/schedules";
	private String m_routeUrl;
	//Containers
	private ArrayList<Community> m_communities= new ArrayList<Community>();
	private HashMap<String, Route> m_routeByNumber = new HashMap<String, Route>();
	//Parsers
	private HtmlParser m_htmlParser;
	private TextParser m_textParser;
	private Scanner m_scanner = new Scanner(System.in);
}
