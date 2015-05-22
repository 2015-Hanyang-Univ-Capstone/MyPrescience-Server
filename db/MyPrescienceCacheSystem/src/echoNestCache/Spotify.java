package echoNestCache;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Spotify {
	
	private DBHandler dbHandler;
	
	Spotify() {
		dbHandler = new DBHandler();
	}

	public String[] getTrack(String JSON) {
		JSONParser jsonParser = new JSONParser();
        JSONObject album = null;
        try {
            album = (JSONObject) jsonParser.parse(JSON);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        if(album != null) {
        
	        String id = (String) album.get("id");
	        String name = (String) album.get("name");
	        
	        String release = (String) album.get("release_date");
	        
	        JSONArray images = (JSONArray) album.get("images");
	        String image_600 = "", image_300 = "", image_64 = "";
	        if(images != null && images.size() == 3) {
		        JSONObject image1 = (JSONObject) images.get(0);
		        image_600 = (String) image1.get("url");
		        JSONObject image2 = (JSONObject) images.get(1);
		        image_300 = (String) image2.get("url");
		        JSONObject image3 = (JSONObject) images.get(2);
		        image_64 = (String) image3.get("url");
	        }
	        
	        JSONArray artists = (JSONArray) album.get("artists");
	        String artist_name = null;
	        if(artists != null && artists.size() != 0) {
		        JSONObject artsit = (JSONObject) artists.get(0);
		        artist_name = (String) artsit.get("name");
	        }
	
	        dbHandler.insertAlbum(id, name, artist_name, release, image_600, image_300, image_64);
	        
	        JSONObject tracks = (JSONObject) album.get("tracks");
	        String[] trackIds = null;
	        if(tracks != null) {
		        JSONArray items = (JSONArray) tracks.get("items");
		        if(items != null) {
			        trackIds = new String[items.size()];
			        if(items.size() != 0) {
			            for(int i = 0; i < items.size(); i++) {
			                JSONObject item = (JSONObject) items.get(i);
			                String track_id = (String) item.get("id");
			                trackIds[i] = track_id;
			            }
			        }
		        }
	        }
	        return trackIds; 
        }
        
        return null;
	}
	
}
