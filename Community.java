/** 
 * Community is a storage class with the capability of printing out 
 * information that the user may require.
 * The core functionalities that this class provides are as follow:
 * 1) Get the name of the community
 * 2) Add routes to its List of routes for future reference
 * 3) Print community name and its List of routes
*/

import java.util.ArrayList;

public class Community {

	public Community(String name) {
		this.m_name = name;
		this.m_routes = new ArrayList<Route>();
	}
	
	public String getName()
	{
		return m_name;
	}
	
	public void addRoute(Route route)
	{
		m_routes.add(route);
	}
	
	
	public void printInfo() {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Destination: " + m_name);
		for(int i = 0; i < m_routes.size(); i++)
			m_routes.get(i).printRouteNumber();

	}
	
	private String m_name;
	private ArrayList<Route> m_routes;
}
