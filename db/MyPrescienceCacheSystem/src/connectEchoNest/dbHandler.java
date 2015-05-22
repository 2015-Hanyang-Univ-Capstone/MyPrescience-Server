package connectEchoNest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class dbHandler {
	
	static String URL = "jdbc:mysql://"
			+ "127.0.0.1:8889/exercise_myp?useUnicode=true&characterEncoding=euckr";
	static String ID = "root";
	static String PW = "root";
	static Connection conn = null;
	
	dbHandler() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(URL, ID, PW);
			System.out.println("Mysql Connect Success!");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	
	public static GomLog getSeqSongDeatil(int startIndex, int size) {
		
		GomLog gomlog = new GomLog();
		
		try{
			Statement stmt = null;
		    System.out.println("Creating statement...");
		    stmt = conn.createStatement();
		    String sql;
		    sql = "SELECT * FROM gomlog LIMIT " + startIndex + ", " + size;
		    ResultSet rs = stmt.executeQuery(sql);
	
	     //STEP 5: Extract data from result set
		    while(rs.next()){
		         //Retrieve by column name
		    	gomlog.addIp(rs.getString("ip"));
		    	gomlog.addDate(rs.getString("date"));
		    	gomlog.addTime(rs.getString("time"));
		    	gomlog.addTitle(rs.getString("title"));
		    	gomlog.addArtist(rs.getString("artist"));
		    	gomlog.addSize(rs.getInt("size"));
		    	gomlog.addUtc(rs.getInt("utc"));
		    	gomlog.addVersion(rs.getString("version"));
		    }
		      //STEP 6: Clean-up environment
		    rs.close();
		    stmt.close();
		    conn.close();
		    
		} catch(SQLException se) {
			se.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return gomlog;
	}
	
	public static void InsertSongId(String ip, String date, String time, String songid,
			List<String> genre, int size, int utc, String version) {
		
		Statement st = null;
		String genres = "";
		
		for(int i=0; i<genre.size(); i++)
			genres += genre.get(i) + ",";
		
		String query = "INSERT INTO gom_echo "
				+ " (ip, date, time, song_id, genre, size, utc, version) "
				+ " values ('" + ip + "', '" + date + "', '" + time + "', '" + songid + "',"
				+ "  '"+genres+"', " + size+", " + utc + ", '" + version + "')";
		
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
