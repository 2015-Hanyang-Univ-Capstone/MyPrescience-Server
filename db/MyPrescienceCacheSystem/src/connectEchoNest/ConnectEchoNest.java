package connectEchoNest;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.ArtistParams;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Params;
import com.echonest.api.v4.Song;

public class ConnectEchoNest {
	
	static dbHandler dbHandler;
	static int SEARCH_COUNT;
	static int COUNT;
	static ArrayList<String> ipList, dateList, timeList, 
							titleList, artistList, versionList;
	static ArrayList<Integer> sizeList, utcList;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SEARCH_COUNT = 0;
		COUNT = 0;
		
		//144620 + 9795 + 49576 + 82548 + 53946 + 43536 + 8364 + 13430 부터 재시작.
		
		for(int start=144620+9795+49576+82548+53946+43536+8364
				; start<Const.MAX_SIZE; start=start+Const.PAGE_SIZE) {
			dbHandler = new dbHandler();
			GomLog song = dbHandler.getSeqSongDeatil(start, Const.PAGE_SIZE);
			
			ipList = song.getIpList();
			dateList = song.getDateList();
			timeList = song.getTimeList();
			titleList = song.getTitleList();
			artistList = song.getArtistList();
			sizeList = song.getSizeList();
			utcList = song.getUtcList();
			versionList = song.getVersionList();
			
			connectEchoNest(titleList, artistList);
			
			System.out.println("All Query Count : " + COUNT);
			System.out.println("SEARCH SUCCESS COUNT : " + SEARCH_COUNT);
			
			try {
				Thread.sleep(1000*50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void connectEchoNest(ArrayList<String> titleList, 
			ArrayList<String> artistList) {
		
		EchoNestAPI echoNest = new EchoNestAPI("ZZKPLNLJYHUVPSMXD");
		
		for(int i=0; i<titleList.size(); i++) {
	        
	        Params p = new Params();
	        p.add("title", titleList.get(i));
	        p.add("artist", artistList.get(i)); 
	        p.add("bucket", "audio_summary");
	        p.add("bucket", "song_type");
	        p.add("results", 1);
	        
	        List<Song> songs;
	        Object audio_summary;
			try {
				songs = echoNest.searchSongs(p);
				
				if(songs.isEmpty()) {
					COUNT++;
					System.out.println(p.getMap());
				}
				
				for (Song song : songs) {
					COUNT++;
					SEARCH_COUNT++;
					audio_summary = song.getObject("audio_summary");
					System.out.println();
					System.out.println("--------------   About Song   ----------------");
		            
		            JSONParser jsonParser = new JSONParser();
		            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
		            JSONObject jsonObject = (JSONObject) jsonParser.parse(audio_summary.toString());
		            
		            System.out.println("id : " + song.getID());
		            System.out.println("artist id : " + song.getArtistID());
		            System.out.println("title : " + song.getTitle());
		            System.out.println("artist : " + song.getArtistName());
		            
		            List songTypeList = (List) song.getObject("song_type");
		            String songType = "";
		            for(int j = 0; j < songTypeList.size()-1; j++)
		            	songType += songTypeList.get(j) + ",";
		            songType += songTypeList.get(songTypeList.size()-1);
		            System.out.println("songType : " + songType );
		            
		            System.out.println("tempo : " + jsonObject.get("tempo"));
		            System.out.println("time_signature : " + jsonObject.get("time_signature"));
		            System.out.println("duration : " + jsonObject.get("duration"));
		            System.out.println("valence : " + jsonObject.get("valence"));
		            
		            System.out.println("loudness : " + jsonObject.get("loudness"));
		            System.out.println("danceability : " + jsonObject.get("danceability"));
		            System.out.println("energy : " + jsonObject.get("energy"));
		           
		            System.out.println("liveness : " + jsonObject.get("liveness"));
		            System.out.println("speechiness : " + jsonObject.get("speechiness"));
		            System.out.println("acousticness : " + jsonObject.get("acousticness"));
		            System.out.println("instrumentalness : " + jsonObject.get("instrumentalness"));
		            
		            System.out.println("mode : " + jsonObject.get("mode"));
		            System.out.println("key : " + jsonObject.get("key"));
		            
		            System.out.println("--------------------------------------------");
		            
		            System.out.println("--------------   About Artist   ----------------");
		            
		            Artist artist = echoNest.newArtistByID("ARH6W4X1187B99274F");
		            artist.fetchBucket("genre");
		            artist.fetchBucket("images");
		            artist.fetchBucket("songs");
//		            ArtistParams artistParam = new ArtistParams();
//		            artistParam.setName(song.getArtistName());
//		            artistParam.add("bucket", "genre");
//		            artistParam.add("bucket", "images");
//		            artistParam.add("bucket", "songs");
//		            artistParam.add("results", "1");
		            
//		            List<Artist> artists = echoNest.searchArtists(artistParam);
//		            for(Artist artist : artists) {
		            	System.out.println("Id : " + artist.getID());
		            	System.out.println("Name : " + artist.getName() );
		            	System.out.println("genre : " + artist.getObject("genres") );
		            	System.out.println("images : " + artist.getObject("images") );
		            	System.out.println("songs : " + artist.getObject("songs") );
//		            }
//		            
		            System.out.println("--------------------------------------------");
		            
		            
//		            dbHandler = new dbHandler();
//		            dbHandler.InsertSongId(ipList.get(i), dateList.get(i), timeList.get(i),
//		            		song.getID(), genre, sizeList.get(i), utcList.get(i), versionList.get(i) );
				}
				
			}  catch (EchoNestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
