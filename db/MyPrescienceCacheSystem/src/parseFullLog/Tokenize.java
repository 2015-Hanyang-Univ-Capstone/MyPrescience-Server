package parseFullLog;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.net.URLDecoder;

public class Tokenize {
	ArrayList<Table> table;

	public Tokenize(ArrayList<Table> t) {
		table = t;
		try {
			doTokenize();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void doTokenize() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream("access_log.newlyrics.gomtv.com"), "UTF8"));
		StringTokenizer strToken;
		String line, query;
		Table temp;
		int count, i, gc_count = 0;

		Connection conn = null;
		try {
			String url = "jdbc:mysql://"
					+ "127.0.0.1:8889/myprescience?useUnicode=true&characterEncoding=euckr";
			String id = "root";
			String pw = "root";

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("Mysql Connect Success!");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		while ((line = br.readLine()) != null) {
			// Only get /cgi-bin/lyrics...
			count = 0;
			for (i = 0; count != 3; i++)
				if (line.charAt(i) == '/')
					count++;
			if (line.charAt(i) != 'c' || line.charAt(i + 8) != 'l')
				continue;

			try {
				// System.out.println("Line: "+line);
				strToken = new StringTokenizer(line);
				temp = new Table();

				temp.ip = strToken.nextToken(" ");
				strToken.nextToken("[");
				temp.date = strToken.nextToken("[:");
				temp.time = strToken.nextToken(" ").substring(1, 6);
				temp.utc = Integer.parseInt(strToken.nextToken("+ ]"));
				strToken.nextToken("&");
				strToken.nextToken("&"); // hkey
				strToken.nextToken("&"); // filekey
				strToken.nextToken("&"); // file name
				temp.title = strToken.nextToken("&").substring(6);
				temp.artists = strToken.nextToken("&").substring(7);
				temp.album = strToken.nextToken("&").substring(6);
				temp.duration = Integer.parseInt(strToken.nextToken("&")
						.substring(9));
				temp.size = Integer.parseInt(strToken.nextToken("&").substring(
						5));
				strToken.nextToken("\"");
				strToken.nextToken("\"");
				strToken.nextToken("\"");
				strToken.nextToken("\"");
				temp.version = strToken.nextToken("\"");

				String t;
				if (!(temp.title.length() == 0 || temp.title.length() == 0 || temp.album
						.length() == 0)) {
					System.out.println("count: " + gc_count);
					// insert
					try {
						java.sql.Statement st;
						query = "INSERT INTO gomlog "
								+ "(ip, date, time, title, artist, album, duration, size, utc, version) "
								+ "values ('" + temp.ip + "', '" + temp.date
								+ "', '" + temp.time + "', '"
								+ decoding(temp.title) + "', '"
								+ decoding(temp.artists) + "', '"
								+ decoding(temp.album) + "', " + temp.duration
								+ ", " + temp.size + ", " + temp.utc + ", '"
								+ temp.version + "');";
						st = conn.createStatement();
						st.executeUpdate(query);
						query = null;
						st.close();
					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("ip: " + temp.ip);
						System.out.println("date: " + temp.date);
						System.out.println("time: " + temp.time);
						System.out.println("utc: " + temp.utc);
						System.out.println("title: " + decoding(temp.title));
						System.out.println("artist: " + decoding(temp.artists));
						System.out.println("album: " + decoding(temp.album));
						System.out.println("duration: " + temp.duration);
						System.out.println("size: " + temp.size);
						System.out.println("version: " + temp.version);
						System.out.println();
					}
				}
				strToken = null;
				line = null;
				temp = null;
				if (++gc_count % 10000 == 0)
					System.gc();
			} catch (Exception e) {
				temp = null;
				strToken = null;
				line = null;
				continue;
			}
		}
	}

	String decoding(String temp) {
		int i, j;
		String t = "";
		for (i = 0; i < temp.length(); i++) {
			if (temp.charAt(i) == '\\') {
				t += '%';
				i++;
			} else if (temp.charAt(i) == '\'') {
				t += "\\\'";
				i++;
			} else if (temp.charAt(i) == '&' && temp.charAt(i + 1) == '#') {
				for (j = 0; temp.charAt(i + j) != ';'; j++)
					;

				try {
					t += URLDecoder.decode(temp.substring(i, j + 1), "UTF-16");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				i += j;
			} else
				t += temp.charAt(i);
		}

		try {
			return URLDecoder.decode(t, "EUC-kr");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
