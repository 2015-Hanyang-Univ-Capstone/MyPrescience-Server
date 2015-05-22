package crawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NaverMusicCrawler {
	
	private String naverMusicUrl;
	
	public NaverMusicCrawler(String url) {
		this.naverMusicUrl = url;
	}
	
	public String[] crawleringNaverMusic() {
		// TODO Auto-generated method stub
		URL url;
		String[] song_array = new String[20];

		try {
			url = new URL(naverMusicUrl);

			BufferedReader br;
			String line;
			br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
			
			while ((line = br.readLine()) != null) {
				Pattern pattern = Pattern
						.compile("a:track.*title.*\" |a:artist.*title.*\">|a:layerbtn,.*a");
				
//				a:artist.*title.*\" artist목
//				"a:track.*title.*\" "   , title목록!
				Matcher matcher = pattern.matcher(line);

				while (matcher.find()) {
					String crawleringStr = matcher.group();
					
					String num1 = crawleringStr.substring(crawleringStr.indexOf("r:")+2, crawleringStr.indexOf("r:")+3);
					String num2 = crawleringStr.substring(crawleringStr.indexOf("r:")+3, crawleringStr.indexOf("r:")+4);
					int num = -1;
					if(isStringInt(num2))
						num = Integer.parseInt(num1+num2);
					else
						num = Integer.parseInt(num1);
					
					String titleNartist = "";
					if(crawleringStr.indexOf("title=\"") != -1)
						titleNartist = crawleringStr.substring(crawleringStr.indexOf("title=\"")+7, crawleringStr.length()-2);
					else 
						titleNartist = crawleringStr.substring(crawleringStr.indexOf(">")+1, crawleringStr.length()-3);
					
					if(song_array[num-1] == null)
						song_array[num-1] = titleNartist + "##";
					else 
						song_array[num-1] += titleNartist;
				}
			}
			br.close();
		} catch (Exception e) {
			
		}
		return song_array;
	}
	
	public static boolean isStringInt(String s) {
	    try {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    } catch (Exception e) {
	    	return false;
	    }
	  }
}
