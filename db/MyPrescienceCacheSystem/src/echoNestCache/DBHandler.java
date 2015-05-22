package echoNestCache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mysql.jdbc.PreparedStatement;

public class DBHandler {
	
	String BBGenreTop_TABLE_NAME = "billboardGenreTop";
	String BBHOT100_TABLE_NAME = "billboardHot100";
	String TEMP_BBHOT100_TABLE_NAME = "tempbillboardHot100";
	String ARTIST_TABLE_NAME = "artist";
	String GENRE_TABLE_NAME = "genre";
	String RATING_TABLE_NAME = "rating";
	String USER_TABLE_NAME = "user";
	String ALBUM_TABLE_NAME = "album";
	
	String POP = "pop";
	String HIPHOP = "hiphop";
	String RnB = "rnd";
	String ROCK = "rock";
	String CLUB = "club";
	String COUNTRY = "country";
	String ELECTRONIC = "electronic";
	
	String URL = "jdbc:mysql://"
			+ "127.0.0.1:3306/exercise_myp?useUnicode=true&characterEncoding=euckr";
//	String URL = "jdbc:mysql://"
//			+ "172.200.152.155:8889/exercise_myp?useUnicode=true&characterEncoding=euckr";
	String ID = "root";
	String PW = "root";
	Connection conn = null;
	
	public DBHandler() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(URL, ID, PW);
			System.out.println("Mysql Connect Success!");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GomLog getSeqSongDeatil(int startIndex, int size) {
		
		GomLog gomlog = new GomLog();
		
		try{
			Statement stmt = null;
		    stmt = conn.createStatement();
		    String sql;
		    sql = "SELECT title, artist FROM gomlog LIMIT " + startIndex + ", " + size;
		    ResultSet rs = stmt.executeQuery(sql);
	
	     //STEP 5: Extract data from result set
		    while(rs.next()){
		         //Retrieve by column name
//		    	gomlog.addIp(rs.getString("ip"));
//		    	gomlog.addDate(rs.getString("date"));
//		    	gomlog.addTime(rs.getString("time"));
		    	gomlog.addTitle(rs.getString("title"));
		    	gomlog.addArtist(rs.getString("artist"));
//		    	gomlog.addSize(rs.getInt("size"));
//		    	gomlog.addUtc(rs.getInt("utc"));
//		    	gomlog.addVersion(rs.getString("version"));
		    }
		      //STEP 6: Clean-up environment
		    rs.close();
		    stmt.close();
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return gomlog;
	}
	
	public void getAllSongIntoIndex(IndexWriter w) {
		
		try{
			Statement stmt = null;
		    stmt = conn.createStatement();
		    String sql;
		    sql = "SELECT id, title, artist FROM song ";
		    ResultSet rs = stmt.executeQuery(sql);
	
	     //STEP 5: Extract data from result set
		    while(rs.next()){
		    	addDoc(w, rs.getString("id"), rs.getString("title"), rs.getString("artist"));
		    }
		      //STEP 6: Clean-up environment
		    rs.close();
		    stmt.close();
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getSongId(String title, String artistName) {
		
		String titleWithPattern = changeWithPattern(title.split(" "));
		String artistWithPattern = changeWithPattern(artistName.split(" "));
		String ret = null;
		try{
			Statement stmt = null;
		    stmt = conn.createStatement();
		    String sql;
		    sql = "SELECT id FROM song WHERE title LIKE '%" + titleWithPattern + "%'"
		    		+ " AND artist LIKE '%" + artistWithPattern + "%' ";
		    ResultSet rs = stmt.executeQuery(sql);
	
		    while(rs.next())
		    	ret = rs.getString("id");
		    rs.close();
		    stmt.close();
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public void getDummyUser() {
		
		try{
			Statement stmt = null;
		    stmt = conn.createStatement();
		    String sql;
		    sql = " SELECT ip"
		    		+ " FROM gom_echo ";
		    ResultSet rs = stmt.executeQuery(sql);
	
		    while(rs.next()) {
		    	insertDummyUser("dum"+rs.getString("ip"));
		    }
		    rs.close();
		    stmt.close();
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertDummyUser(String facebook_id) {

		Statement st = null;
		
		String query = "INSERT INTO "+ USER_TABLE_NAME
		+ " (facebook_id) "
		+ " values (\"" +facebook_id+ "\")";
		
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getDummyData() {
		
		try{
			Statement stmt = null;
		    stmt = conn.createStatement();
		    String sql;
		    sql = " SELECT user.id AS user_id, song.id AS song_id, COUNT(song_id) AS play_count " +
		    		" FROM gom_echo JOIN user " +
		    		" ON gom_echo.ip = user.facebook_id " +
		    		" JOIN song ON gom_echo.song_id = song.id " +
		    		" GROUP BY user.id, song.id ";
		    ResultSet rs = stmt.executeQuery(sql);
	
		    while(rs.next()) {
		    	insertDummyData(rs.getInt("user_id"), rs.getString("song_id"), rs.getInt("play_count"));
		    }
		    rs.close();
		    stmt.close();
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void insertDummyData(int user_id, String song_id, int rating) {
	
		int song_rating = 0;
		if(rating >= 20) {
			song_rating = 10;
		} else if(rating >= 15 && rating < 20) {
			song_rating = 9;
		} else if(rating >= 10 && rating < 15) {
			song_rating = 8;
		} else if(rating >= 5 && rating < 10) {
			song_rating = 7;
		} else if(rating < 5) {
			int min = 5, max =20;
	        song_rating = (int) (Math.random() * (max - min + 1)) + min;
	        song_rating = (song_rating > 10) ? 10 : song_rating;
		}
		
		System.out.println(user_id + " " + song_id + " " + song_rating);
		
		Statement st = null;
		String query = "INSERT INTO "+ RATING_TABLE_NAME
		+ " (user_id, song_id, rating) "
		+ " VALUES ( " + user_id + ",  \"" +song_id+ "\" , " +song_rating+ " )";
		
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String inSongId(String songId) {
		
		String ret = null;
		try{
			Statement stmt = null;
		    stmt = conn.createStatement();
		    String sql;
		    sql = "SELECT id FROM song WHERE id = '" + songId + "'";
		    ResultSet rs = stmt.executeQuery(sql);
	
		    while(rs.next())
		    	ret = rs.getString("id");
		    rs.close();
		    stmt.close();
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String changeWithPattern(String[] strArray) {
		String ret = "";
		for(int i = 0; i < strArray.length - 1; i++) {
			ret += strArray[i] + "%";
		}
		ret += "%";
		return ret;
	}
	
	
	public void InsertSong(String id, String artist_id,
								String track_spotify_id, String album_spotify_id, String artist_spotify_id,
								String title, String artist, String songType, JSONObject audio_summary) {
		
		
		Statement st = null;
		PreparedStatement stmt = null;
		
	    try {
	    	stmt = (PreparedStatement) conn.prepareStatement
	    			( " REPLACE INTO song "
					+ " (id, artist_id, track_spotify_id, album_spotify_id, artist_spotify_id, title, artist, song_type, tempo, time_signature, duration, valence, loudness,"
					+ "	 danceability, energy, liveness, speechiness, acousticness, instrumentalness, song_mode, song_key)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
	    			
			stmt.setString(1, id);
		    stmt.setString(2, artist_id);
		    stmt.setString(3, track_spotify_id);
		    stmt.setString(4, album_spotify_id);
		    stmt.setString(5, artist_spotify_id);
		    stmt.setString(6, title);
		    stmt.setString(7, artist);
		    stmt.setString(8, songType);
		    stmt.setDouble(9, (double) audio_summary.get("tempo"));
		    stmt.setLong(10, (long) audio_summary.get("time_signature"));
		    stmt.setDouble(11, (double) audio_summary.get("duration"));
		    if(audio_summary.get("valence") == null)
		    	stmt.setDouble(12, 0.0);
		    else 
		    	stmt.setDouble(12, (double) audio_summary.get("valence"));
		    if(audio_summary.get("loudness") == null)
		    	stmt.setDouble(13, 0.0);
		    else 
		    	stmt.setDouble(13, (double) audio_summary.get("loudness"));
		    if(audio_summary.get("danceability") == null)
		    	stmt.setDouble(14, 0.0);
		    else 
		    	stmt.setDouble(14, (double) audio_summary.get("danceability"));
		    if(audio_summary.get("energy") == null)
		    	stmt.setDouble(15, 0.0);
		    else 
		    	stmt.setDouble(15, (double) audio_summary.get("energy"));
		    if(audio_summary.get("liveness") == null)
		    	stmt.setDouble(16, 0.0);
		    else 
		    	stmt.setDouble(16, (double) audio_summary.get("liveness"));
		    if(audio_summary.get("speechiness") == null)
		    	stmt.setDouble(17, 0.0);
		    else 
		    	stmt.setDouble(17, (double) audio_summary.get("speechiness"));
		    if(audio_summary.get("acousticness") == null)
		    	stmt.setDouble(18, 0.0);
		    else 
		    	stmt.setDouble(18, (double) audio_summary.get("acousticness"));
		    if(audio_summary.get("instrumentalness") == null)
		    	stmt.setDouble(19, 0.0);
		    else 
		    	stmt.setDouble(19, (double) audio_summary.get("instrumentalness"));
		    stmt.setInt(20, Integer.parseInt(audio_summary.get("mode").toString()));
		    stmt.setInt(21, Integer.parseInt(audio_summary.get("key").toString()));
		    stmt.executeUpdate();
		    stmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void InsertKorSong(String id, String artist_id,
			String track_spotify_id, String album_spotify_id, String artist_spotify_id,
			String title, String artist, String songType, JSONObject audio_summary) {

		Statement st = null;
		PreparedStatement stmt = null;
		
	    try {
	    	stmt = (PreparedStatement) conn.prepareStatement
	    			( " REPLACE INTO song "
					+ " (id, artist_id, track_spotify_id, album_spotify_id, artist_spotify_id, title, artist, song_type, tempo, time_signature, duration, valence, loudness,"
					+ "	 danceability, energy, liveness, speechiness, acousticness, instrumentalness, song_mode, song_key, country)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " );
	    			
			stmt.setString(1, id);
		    stmt.setString(2, artist_id);
		    stmt.setString(3, track_spotify_id);
		    stmt.setString(4, album_spotify_id);
		    stmt.setString(5, artist_spotify_id);
		    stmt.setString(6, title);
		    stmt.setString(7, artist);
		    stmt.setString(8, songType);
		    stmt.setDouble(9, (double) audio_summary.get("tempo"));
		    stmt.setLong(10, (long) audio_summary.get("time_signature"));
		    stmt.setDouble(11, (double) audio_summary.get("duration"));
		    if(audio_summary.get("valence") == null)
		    	stmt.setDouble(12, 0.0);
		    else 
		    	stmt.setDouble(12, (double) audio_summary.get("valence"));
		    if(audio_summary.get("loudness") == null)
		    	stmt.setDouble(13, 0.0);
		    else 
		    	stmt.setDouble(13, (double) audio_summary.get("loudness"));
		    if(audio_summary.get("danceability") == null)
		    	stmt.setDouble(14, 0.0);
		    else 
		    	stmt.setDouble(14, (double) audio_summary.get("danceability"));
		    if(audio_summary.get("energy") == null)
		    	stmt.setDouble(15, 0.0);
		    else 
		    	stmt.setDouble(15, (double) audio_summary.get("energy"));
		    if(audio_summary.get("liveness") == null)
		    	stmt.setDouble(16, 0.0);
		    else 
		    	stmt.setDouble(16, (double) audio_summary.get("liveness"));
		    if(audio_summary.get("speechiness") == null)
		    	stmt.setDouble(17, 0.0);
		    else 
		    	stmt.setDouble(17, (double) audio_summary.get("speechiness"));
		    if(audio_summary.get("acousticness") == null)
		    	stmt.setDouble(18, 0.0);
		    else 
		    	stmt.setDouble(18, (double) audio_summary.get("acousticness"));
		    if(audio_summary.get("instrumentalness") == null)
		    	stmt.setDouble(19, 0.0);
		    else 
		    	stmt.setDouble(19, (double) audio_summary.get("instrumentalness"));
		    stmt.setInt(20, Integer.parseInt(audio_summary.get("mode").toString()));
		    stmt.setInt(21, Integer.parseInt(audio_summary.get("key").toString()));
		    stmt.setString(22, "kor");
		    stmt.executeUpdate();
		    stmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
	}
	
	public void InsertOverseaSong(String id, String artist_id,
			String track_spotify_id, String album_spotify_id, String artist_spotify_id,
			String title, String artist, String songType, JSONObject audio_summary) {

		Statement st = null;
		
		String query = "INSERT INTO song_oversea "
		+ " (id, artist_id, track_spotify_id, album_spotify_id, artist_spotify_id, title, artist, song_type, tempo, time_signature, duration, valence, loudness,"
		+ "	 danceability, energy, liveness, speechiness, acousticness, instrumentalness, song_mode, song_key) "
		+ " values (\"" +id+ "\", \"" +artist_id+ "\", \"" +track_spotify_id+ "\", \"" +album_spotify_id+ "\", \"" +artist_spotify_id
		+ "\", \"" +title+ "\", \"" +artist + "\",\"" +songType+ "\", "
		+  audio_summary.get("tempo") + ", " + audio_summary.get("time_signature") + ", "
		+ audio_summary.get("duration") + ", " + audio_summary.get("valence") + ", "
		+ audio_summary.get("loudness") + ", " + audio_summary.get("danceability") + ", "
		+ audio_summary.get("energy") + ", " + audio_summary.get("liveness") + ", "
		+ audio_summary.get("speechiness") + ", " + audio_summary.get("acousticness") + ", "
		+ audio_summary.get("instrumentalness") + ", " + Integer.parseInt(audio_summary.get("mode").toString()) + ", "
		+ Integer.parseInt(audio_summary.get("key").toString()) + " )";
		
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
	
	public void createBBGenreTopTable() throws SQLException {
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " + BBGenreTop_TABLE_NAME
	            + "  (song_id         VARCHAR(255),"
	            + "   genre VARCHAR(50))";

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	public void createTEMPBBHot100Table() throws SQLException {
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " + TEMP_BBHOT100_TABLE_NAME
	            + "  (song_id	VARCHAR(255),"
	            + "   rank		int )";

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	public void createGenreTable() throws SQLException {
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS " + GENRE_TABLE_NAME
	            + "  (genre	VARCHAR(100),"
	            + "   detail VARCHAR(150) )";

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	public void deleteBBHot100Table() throws SQLException {
	    String sqlCreate = "DROP TABLE " + BBHOT100_TABLE_NAME;

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	public void renameBBHot100Table() throws SQLException {
	    String sqlCreate = "RENAME TABLE " + TEMP_BBHOT100_TABLE_NAME 
	    		+ " to " + BBHOT100_TABLE_NAME;

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	public void insertBBGenreTopSong(String songId, String genre) {

		Statement st = null;
		
		String query = "INSERT INTO "+ BBGenreTop_TABLE_NAME
		+ " (song_id, genre) "
		+ " values (\"" +songId+ "\", \"" +genre+ "\" )";
		
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertBBHot100Song(String songId, int rank) {

		Statement st = null;
		
		String query = "INSERT INTO "+ TEMP_BBHOT100_TABLE_NAME
		+ " (song_id, rank) "
		+ " values (\"" +songId+ "\", " +rank+ " )";
		
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertGenreDetails(String genre, String detail) {

		Statement st = null;
		
		String query = "INSERT INTO "+ GENRE_TABLE_NAME
		+ " (song_id, rank) "
		+ " values (\"" +genre+ "\", \"" +detail+ "\" )";
		
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public void getArtistNInsert() {
		
		String spotify_artistAPI = "https://api.spotify.com/v1/artists/";
		
		try{
			Statement stmt = null;
		    stmt = conn.createStatement();
		    String sql;
		    sql = "SELECT DISTINCT artist_spotify_id "
		    		+ "FROM song "
		    		+ "WHERE artist_spotify_id NOT IN (SELECT id FROM artist) ";
		    ResultSet rs = stmt.executeQuery(sql);
	
	     //STEP 5: Extract data from result set
		    JSONParser jsonParser = new JSONParser();
            JSONObject artist = null;
            int count = 0;
            
		    while(rs.next()){
		    	count++;
		    	String artist_spotify_id = rs.getString("artist_spotify_id");
		    	if(!(artist_spotify_id.equals(""))) {
		            artist = (JSONObject) jsonParser.parse(getStringFromUrl(spotify_artistAPI+artist_spotify_id));
		            String name = (String) artist.get("name");
		            String genreStr = "";
		            System.out.println(count + " " + artist_spotify_id + " " + artist.toString());
		            
		            if (artist.get("genres") != null) {
			            JSONArray genresJSON = (JSONArray) artist.get("genres");
			            if (genresJSON != null && genresJSON.size() != 0) {
			            	for(int i = 0; i < genresJSON.size()-1; i++) 
			            		genreStr += (String)genresJSON.get(0) + ",";
			            	genreStr += (String)genresJSON.get(genresJSON.size()-1);
			            }
		            }
		            
		            String image_64 = "", image_300 = "", image_600 = "";
		            if ( artist.get("images") != null) {
			            JSONArray images = (JSONArray) artist.get("images");
			            if(images.size() >= 3 && images != null) {
			            	JSONObject image64 = (JSONObject) images.get(0);
			            	image_64 = (String) image64.get("url");
			            	JSONObject image300 = (JSONObject) images.get(1);
			            	image_300 = (String) image300.get("url");
			            	JSONObject image600 = (JSONObject) images.get(2);
			            	image_600 = (String) image600.get("url");
			            }
			    	}
		            
		            System.out.println("Insert Artist!");
		            
		            
		    		PreparedStatement stmt2 = null;
		    		
		    	    try {
		    	    	stmt2 = (PreparedStatement) conn.prepareStatement
		    					(" INSERT INTO " + ARTIST_TABLE_NAME + 
    							 " (id, name, genres, image_64, image_300, image_600) "
		    					+" VALUES (?, ?, ?, ?, ?, ?)");
		    	    	stmt2.setString(1, artist_spotify_id);
		    	    	stmt2.setString(2, name);
		    	    	stmt2.setString(3, genreStr);
		    	    	stmt2.setString(4, image_64);
		    	    	stmt2.setString(5, image_300);
		    	    	stmt2.setString(6, image_600);
		    	    	stmt2.executeUpdate();
		    	    	stmt2.close();
		    		} catch (SQLException e1) {
		    			// TODO Auto-generated catch block
		    			e1.printStackTrace();
		    		}
		    	}
		    }
		      //STEP 6: Clean-up environment
		    rs.close();
		    stmt.close();
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertAlbumsTrack() {
		
//		try{
//			Statement stmt = null;
//		    stmt = conn.createStatement();
//		    String sql;
//		    sql = "SELECT DISTINCT album_spotify_id FROM song WHERE album_spotify_id NOT IN (SELECT id FROM album)";
//		    ResultSet rs = stmt.executeQuery(sql);
	
	     //STEP 5: Extract data from result set
		    Spotify spotify = new Spotify();
		    cacheSystem cache = new cacheSystem();
		    int songCount = 0;
		    
		    String url = "https://api.spotify.com/v1/albums/";
//		    while(rs.next()){
//		    	String album_spotify_id = rs.getString("album_spotify_id");
		    	String album_spotify_id = "7DfjXzm6ULYG1swzuIITJi";
		    	System.out.println(album_spotify_id);
				String Json = getStringFromUrl(url+album_spotify_id);
				String[] track_ids = spotify.getTrack(Json);
				
				if(track_ids != null) {
					for(String track_id : track_ids) {
						cache.cacheTrack(track_id, album_spotify_id);
						songCount++;
						
						if(songCount % 120 == 0) {
							cache.keyChangeCount++;
							cache.echonest.changeKey(cache.ECHONSET_KEYS[cache.keyChangeCount%4]);
						}
					}
				}
//		    }
		      //STEP 6: Clean-up environment
//		    rs.close();
//		    stmt.close();
//		} catch(SQLException se) {
//			se.printStackTrace();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public boolean selectTrackDuplicate(String track_id) {

		String track = null;
		try {
			Statement stmt = null;
			stmt = conn.createStatement();
			
			String query = "SELECT track_spotify_id FROM song "
					+ " WHERE track_spotify_id = \""+ track_id +"\" ";
			
			ResultSet rs = stmt.executeQuery(query);
			
			
			while(rs.next()) {
				track = rs.getString("track_spotify_id");
				System.out.println(track);
			}
			
			stmt.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(track != null)
			return true;
		return false;
	}
	
	public void insertAlbum(String id, String name, String artist, String release_date,
			String image_600, String image_300, String image_64) {

		Statement st = null;
		PreparedStatement stmt = null;
		
	    try {
			stmt = (PreparedStatement) conn.prepareStatement
					(" REPLACE INTO album (id, name, artist, release_date, image_600, image_300, image_64) "
					+" VALUES (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, id);
		    stmt.setString(2, name);
		    stmt.setString(3, artist);
		    stmt.setString(4, release_date);
		    stmt.setString(5, image_600);
		    stmt.setString(6, image_300);
		    stmt.setString(7, image_64);
		    stmt.executeUpdate();
		    stmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		
//		String query = "REPLACE INTO "+ ALBUM_TABLE_NAME
//		+ " (id, name, artist, release_date, image_600, image_300, image_64) "
//		+ " values (\""+id+ "\", \""+name+"\", \""+artist+"\", \""+ release_date + "\"" 
//				+ ", \"" +image_600+ "\", \"" +image_300+ "\", \"" +image_64+ "\" )";
//		
//		try {
//			st = conn.createStatement();
//			st.executeUpdate(query);
//			
//			st.close();
//			} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public String getStringFromUrl(String url) {
        DefaultHttpClient httpclient = new DefaultHttpClient();

        HttpGet post = new HttpGet(url);
        HttpResponse response;
        StringBuilder total = new StringBuilder();
        try {
            response = httpclient.execute(post);
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            InputStream is = buf.getContent();

            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();
    }
	
	private void addDoc(IndexWriter w, String song_id, String title, String artist) throws IOException {
	    Document doc = new Document();
	    doc.add(new TextField("song", title + "<>" + artist, Field.Store.YES));
	    doc.add(new StringField("song_id", song_id, Field.Store.YES));
	    w.addDocument(doc);
	  }
	
public void writeKorArtist() {
		
		try {
			Statement stmt = null;
			stmt = conn.createStatement();
			
			String query = "SELECT DISTINCT artist FROM song "
					+ " WHERE country = 'kor' ";
			
			ResultSet rs = stmt.executeQuery(query);
			
			BufferedWriter out = new BufferedWriter(new FileWriter("kor_artist.txt"));
			
			while(rs.next()) {
				String artist = rs.getString("artist");
				out.write(artist); out.newLine();
			}
			out.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateArtistCountry(String artist) {
		
		try {
			Statement stmt = null;
			
			String query = "UPDATE song SET country = 'kor'"
					+ "	WHERE artist = '"+ artist +"'" ;
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkExist(String artist) {
		
		try {
			Statement stmt = null;
			
			String query = "SELECT name FROM artist WHERE name = '"+ artist +"'" ;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				System.out.println(rs.getString("name"));
			}
			
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertDummyData(int user_id, String artist) {
	
		try {
			Statement stmt = null;
			
			String query = "REPLACE INTO rating (user_id, song_id, rating) "
					+ " SELECT " + user_id + ", id, 10 FROM song WHERE artist = '"+ artist +"'" ;
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			
			System.out.println("Insert " + artist + " in user id " + user_id);
			
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertDummyData2(int user_id, int offset) {
		
		try {
			Statement stmt2 = null;
			String query2 = "SELECT artist, COUNT(id) "
						+ "	FROM song"
						+ "	GROUP BY artist"
						+ "	ORDER BY COUNT(id) DESC"
						+ "	LIMIT 100 OFFSET " + offset + ";" ;
			stmt2 = conn.createStatement();
			ResultSet rs = stmt2.executeQuery(query2);
			
			while(rs.next()) {
				String artist = rs.getString("artist");
				Statement stmt = null;
				
				String query = "REPLACE INTO rating (user_id, song_id, rating) "
						+ " SELECT " + user_id + ", id, 10 FROM song WHERE artist = '"+ artist +"'" ;
				stmt = conn.createStatement();
				stmt.executeUpdate(query);
				
				System.out.println("Insert " + artist + " in user id " + user_id);
				stmt.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
