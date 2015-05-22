package echoNestCache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.echonest.api.v4.Song;

import crawler.NaverMusicCrawler;



public class cacheSystem {
	
	private static String KEY1 = "ZZKPLNLJYHUVPSMXD";
	private static String KEY2 = "ZSOHHB9QC21T7M2HK";
	private static String KEY3 = "TTJSBGR5LDSK4GRMD";
	private static String KEY4 = "VEPIYKAP3UMG0H15M";
	
	public static int keyChangeCount = 0;
	public static String[] ECHONSET_KEYS = {KEY1, KEY2, KEY3, KEY4};
	
	public static int COUNT = 403726;
	public static int MAX_QUERY = 120;
	public static int GOMLOG_SIZE = 868806;
	static ArrayList<String> titleList, artistList;
	
	static DBHandler dbHandler;
	static EchoNest echonest;
	static BillboardXML billboard;
	static NaverMusicCrawler naverCrawler;

	public static void main(String[] args) {
		
		dbHandler = new DBHandler();
		//echonest = new EchoNest();
		
		int offset = Integer.parseInt(args[0]);
		  for(int i = 18597; i < 18597+30; i++) 
    		  dbHandler.insertDummyData2(i, offset);
		
		//try {
		//      BufferedReader in = new BufferedReader(new FileReader("kor_artist.txt"));
		//      String s;

		 //     while ((s = in.readLine()) != null) {
		   // 	  dbHandler.updateArtistCountry(s);
		   //   }
		    //  in.close();
		      ////////////////////////////////////////////////////////////////
		 //   } catch (IOException e) {
		  //  	e.printStackTrace();
		   // }
		
		//insertArtistData();
		
		
		
//		getBBGenreTop();
		
		//dbHandler.insertAlbumsTrack();
		
		String urlDomestic = "http://music.naver.com/listen/newTrack.nhn?domain=DOMESTIC&target=all&page=";
		String urlOversea = "http://music.naver.com/listen/newTrack.nhn?domain=OVERSEA&target=all&page=";
		int modeDomestic = 0;
		int modeOversea = 1;
		
		crawleringNaverMusic(urlDomestic, modeDomestic);
		crawleringNaverMusic(urlOversea, modeOversea);
	}
	
	public static void crawleringNaverMusic(String url, int mode) {
		
		int pageNum = 1;
		int count = 0;
		
		for(int i = pageNum; i >= 1; i--) {
			String[] song_array = crawleringNaverMusic(url+i);
			
			System.out.println("page : " + i);
			for(String song : song_array) {
				String title = "", artist = "";
				if(song != null) {
					String[] title_artist = song.split("##");
					if(title_artist.length == 2) {
						title = title_artist[0];
						artist = title_artist[1];
					} else {
						continue;
					}
					
					System.out.println("title : " + title + " artist : " + artist);
					if(mode == 0)
						cacheKorSong(title, artist);
					else if(mode == 1)
						cacheSong(title, artist);
					count++;
				}
				
				if(count %120 == 0) {
					keyChangeCount++;
					echonest.changeKey(ECHONSET_KEYS[keyChangeCount%4]);
				}
			}
		}
	}
	
	public static void gomlogInsert() {
		for(int start = 403726; start < GOMLOG_SIZE; start = start + MAX_QUERY) {
			
			GomLog gomlog = dbHandler.getSeqSongDeatil(start, MAX_QUERY);
			
			titleList = gomlog.getTitleList();
			artistList = gomlog.getArtistList();
			
			for(int i = 0; i < titleList.size(); i++) {
				COUNT++;
				if(dbHandler.getSongId(titleList.get(i), artistList.get(i)) == null ) {
					System.out.println("No." + COUNT + " There is no Data in Local song DB!!");
					cacheSong(titleList.get(i), artistList.get(i));
				} else {
					System.out.println("No." + COUNT + "Exist in Local DB!");
				}
			}
			try {
				Thread.sleep(1000*45);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dbHandler.close();
	}
	
	public static void getBBGenreTop() {
		try {
			billboard = new BillboardXML();
//			dbHandler.createBBGenreTopTable();
			
			insertBBGenreTopSong(billboard.getPopXML(), "pop");
			insertBBGenreTopSong(billboard.getHiphopXML(), "hiphop");
			insertBBGenreTopSong(billboard.getRnBXML(), "rnb");
			insertBBGenreTopSong(billboard.getRockXML(), "rock");
			
			echonest.changeKey(KEY2);
			
			insertBBGenreTopSong(billboard.getClubXML(), "club");
			insertBBGenreTopSong(billboard.getCountryXML(), "country");
			insertBBGenreTopSong(billboard.getElectronicXML(), "electronic");
			
			echonest.changeKey(KEY3);
			
			dbHandler.createTEMPBBHot100Table();
			insertBBHot100Song(billboard.getHot100XML());
			dbHandler.deleteBBHot100Table();
			dbHandler.renameBBHot100Table();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("Duplicate");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertBBGenreTopSong(NodeList items, String genre) {
		for( int idx=0; idx<items.getLength(); idx++ ){
			Node item = items.item(idx);
			NodeList children = item.getChildNodes();
			
			Node titleNode = children.item(1);
			Node artistNode = children.item(5);
//			Node rankNode = children.item(9);
			
			String title;
			if(idx < 10)
				title = titleNode.getFirstChild().getNodeValue().substring(3);
			else 
				title = titleNode.getFirstChild().getNodeValue().substring(4);
			String artist = artistNode.getFirstChild().getNodeValue();
			int featuringIndex = artist.indexOf("Featuring");
			if(featuringIndex != -1)
				artist = artist.substring(0, featuringIndex);
//			int rank = Integer.parseInt(rankNode.getFirstChild().getNodeValue());
			
			String songId = cacheKorSong(title, artist);
			if(songId != null) 
				dbHandler.insertBBGenreTopSong(songId, genre);
		}
	}
	
	public static void insertBBHot100Song(NodeList items) {
		for( int idx=0; idx<items.getLength(); idx++ ){
			Node item = items.item(idx);
			NodeList children = item.getChildNodes();
			
			Node titleNode = children.item(1);
			Node artistNode = children.item(5);
			Node rankNode = children.item(9);
			
			String title;
			if(idx < 10)
				title = titleNode.getFirstChild().getNodeValue().substring(3);
			else if(idx == 100)
				title = titleNode.getFirstChild().getNodeValue().substring(5);
			else 
				title = titleNode.getFirstChild().getNodeValue().substring(4);
			String artist = artistNode.getFirstChild().getNodeValue();
			int featuringIndex = artist.indexOf("Featuring");
			if(featuringIndex != -1)
				artist = artist.substring(0, featuringIndex);
			int rank = Integer.parseInt(rankNode.getFirstChild().getNodeValue());
			
			String songId = cacheSong(title, artist);
			if(songId != null) 
				dbHandler.insertBBHot100Song(songId, rank);
		}
	}
	
	public static void insertArtistData() {
		dbHandler.getArtistNInsert();
	}
	
	public static void insertGenreData() {
		try {
			dbHandler.createGenreTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertDummyGomData() {
		dbHandler.getDummyUser();
		dbHandler.getDummyData();
	}
	
	public static String[] crawleringNaverMusic(String url) {
		naverCrawler = new NaverMusicCrawler(url);
		String[] song_array = naverCrawler.crawleringNaverMusic();
		return song_array;
	}
	
	public static String cacheTrack(String track_id, String album_id) {
		
		Song song = echonest.searchSongWithTrackId(track_id);
		String songId = null;
		
		if(song == null){
	        System.out.println("-> There is no Data in EchoNest!!!");
	    } else {
	    	songId = song.getID();
	    	
	    	Object audio_summary;
			audio_summary = song.getObject("audio_summary");
            JSONParser jsonParser = new JSONParser();
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject;
            
			try {
				String track_spotify_id = "", album_spotify_id = "", artist_spotify_id = "";
				JSONArray spotify_tracks, spotify_artist;
				JSONObject trackJSON, artistJSON;
				if(song.getObject("tracks") != null) {
					spotify_tracks = (JSONArray) jsonParser.parse( song.getObject("tracks").toString() );
					trackJSON = new JSONObject();
					if(spotify_tracks.size() != 0) {
						trackJSON = (JSONObject) jsonParser.parse(spotify_tracks.get(0).toString());
						track_spotify_id = splitOnlyID(trackJSON.get("foreign_id").toString());
						album_spotify_id = splitOnlyID(trackJSON.get("foreign_release_id").toString());
					}
				}
				
				if(song.getObject("artist_foreign_ids") != null) {
					spotify_artist = (JSONArray) jsonParser.parse( song.getObject("artist_foreign_ids").toString() );
					System.out.println(spotify_artist);
					artistJSON = new JSONObject();
					if(spotify_artist.size() != 0) {
						artistJSON = (JSONObject) jsonParser.parse(spotify_artist.get(0).toString());
						artist_spotify_id = splitOnlyID(artistJSON.get("foreign_id").toString());
					}
				}
				
				List songTypeList = (List) song.getObject("song_type");
				String songType = "";
				if(songTypeList.size() != 0) {
		            for(int j = 0; j < songTypeList.size()-1; j++)
		            	songType += songTypeList.get(j) + ",";
		            songType += songTypeList.get(songTypeList.size()-1);
				}
				jsonObject = (JSONObject) jsonParser.parse(audio_summary.toString());
				
				System.out.println("track_id : " + track_id + " album_id : " + album_id);
				
				dbHandler.InsertSong(song.getID(), song.getArtistID(),
						track_id, album_id, artist_spotify_id, 
						song.getTitle(), song.getArtistName(),
						songType, jsonObject);
	            
	            System.out.println("--> Insert Local DB");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
	    }
		return songId;
	}
	
	public static String cacheSong(String title, String artist) {
		
		Song song = echonest.searchSong(title, artist);
		String songId = null;
		
		if(song == null){
	        System.out.println("-> There is no Data in EchoNest!!!");
	    } else {
	    	songId = song.getID();
	    	
	    	Object audio_summary;
			audio_summary = song.getObject("audio_summary");
            JSONParser jsonParser = new JSONParser();
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject;
            
			try {
				String track_spotify_id = "", album_spotify_id = "", artist_spotify_id = "";
				JSONArray spotify_tracks, spotify_artist;
				JSONObject trackJSON, artistJSON;
				if(song.getObject("tracks") != null) {
					spotify_tracks = (JSONArray) jsonParser.parse( song.getObject("tracks").toString() );
					trackJSON = new JSONObject();
					if(spotify_tracks.size() != 0) {
						trackJSON = (JSONObject) jsonParser.parse(spotify_tracks.get(0).toString());
						track_spotify_id = splitOnlyID(trackJSON.get("foreign_id").toString());
						album_spotify_id = splitOnlyID(trackJSON.get("foreign_release_id").toString());
					}
				}
				
				if(song.getObject("artist_foreign_ids") != null) {
					spotify_artist = (JSONArray) jsonParser.parse( song.getObject("artist_foreign_ids").toString() );
					System.out.println(spotify_artist);
					artistJSON = new JSONObject();
					if(spotify_artist.size() != 0) {
						artistJSON = (JSONObject) jsonParser.parse(spotify_artist.get(0).toString());
						artist_spotify_id = splitOnlyID(artistJSON.get("foreign_id").toString());
					}
				}
				
				List songTypeList = (List) song.getObject("song_type");
				String songType = "";
				if(songTypeList.size() != 0) {
		            for(int j = 0; j < songTypeList.size()-1; j++)
		            	songType += songTypeList.get(j) + ",";
		            songType += songTypeList.get(songTypeList.size()-1);
				}
				jsonObject = (JSONObject) jsonParser.parse(audio_summary.toString());
				
				dbHandler.InsertSong(song.getID(), song.getArtistID(),
						track_spotify_id, album_spotify_id, artist_spotify_id, 
						song.getTitle(), song.getArtistName(),
						songType, jsonObject);
	            
	            System.out.println("--> Insert Local DB");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
	    }
		return songId;
	}
	
public static String cacheKorSong(String title, String artist) {
		
		Song song = echonest.searchSong(title, artist);
		String songId = null;
		
		if(song == null){
	        System.out.println("-> There is no Data in EchoNest!!!");
	    } else {
	    	songId = song.getID();
	    	
	    	Object audio_summary;
			audio_summary = song.getObject("audio_summary");
            JSONParser jsonParser = new JSONParser();
            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject;
            
			try {
				String track_spotify_id = "", album_spotify_id = "", artist_spotify_id = "";
				JSONArray spotify_tracks, spotify_artist;
				JSONObject trackJSON, artistJSON;
				if(song.getObject("tracks") != null) {
					spotify_tracks = (JSONArray) jsonParser.parse( song.getObject("tracks").toString() );
					trackJSON = new JSONObject();
					if(spotify_tracks.size() != 0) {
						trackJSON = (JSONObject) jsonParser.parse(spotify_tracks.get(0).toString());
						track_spotify_id = splitOnlyID(trackJSON.get("foreign_id").toString());
						album_spotify_id = splitOnlyID(trackJSON.get("foreign_release_id").toString());
					}
				}
				
				if(song.getObject("artist_foreign_ids") != null) {
					spotify_artist = (JSONArray) jsonParser.parse( song.getObject("artist_foreign_ids").toString() );
					System.out.println(spotify_artist);
					artistJSON = new JSONObject();
					if(spotify_artist.size() != 0) {
						artistJSON = (JSONObject) jsonParser.parse(spotify_artist.get(0).toString());
						artist_spotify_id = splitOnlyID(artistJSON.get("foreign_id").toString());
					}
				}
				
				List songTypeList = (List) song.getObject("song_type");
				String songType = "";
				if(songTypeList.size() != 0) {
		            for(int j = 0; j < songTypeList.size()-1; j++)
		            	songType += songTypeList.get(j) + ",";
		            songType += songTypeList.get(songTypeList.size()-1);
				}
				jsonObject = (JSONObject) jsonParser.parse(audio_summary.toString());
				
				dbHandler.InsertKorSong(song.getID(), song.getArtistID(),
						track_spotify_id, album_spotify_id, artist_spotify_id, 
						song.getTitle(), song.getArtistName(),
						songType, jsonObject);
	            
	            System.out.println("--> Insert Local DB");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
	    }
		return songId;
	}
	
	public static String splitOnlyID(String spotifyID) {
		String[] spotifyKeys = spotifyID.split(":");
		return spotifyKeys[2];
	}

}
