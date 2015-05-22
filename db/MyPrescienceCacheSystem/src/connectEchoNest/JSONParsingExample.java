package connectEchoNest;

import java.net.*;
import java.io.*;

public class JSONParsingExample {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		URL yahoo = new URL("http://www.yahoo.com/");
		URLConnection yc = yahoo.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		
		String inputLine;

		while ((inputLine = in.readLine()) != null) 
			System.out.println(inputLine);
		in.close();
		
	}

}
