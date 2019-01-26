/** 
 * HtmlParser is a utility class that returns a html text given the URL.
 * This class in conjunction with the TextParser class allows for data extraction.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HtmlParser {

	public HtmlParser(String url) {
		HtmlParser.m_url = url;
	}

	//Returns a html text
	public static String getText() throws IOException
	{
		URLConnection connection = new URL(m_url).openConnection();
		connection.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine = "";
		String text = "";
		while ((inputLine = input.readLine()) != null) 
			text += inputLine + "\n";
		input.close();
		return text;
	}
	
	private static String m_url;
}
