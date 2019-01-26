/** 
 * BusRouteFinderUI is the entry point of the bus route finder API.
 * The BusRouteFinderFacade was used to implement user-requested features
 * while hiding complexity and encapsulating details and implementations.
*/

import java.io.IOException;

public class BusRouteFinderUI {

	public BusRouteFinderUI() {

	}
	public static void main(String[] args) throws IOException{
		BusRouteFinderFacade routeFinder = new BusRouteFinderFacade();
		routeFinder.promptUserForInitial();
		routeFinder.promptUserForRouteNumber();
	}	
}
