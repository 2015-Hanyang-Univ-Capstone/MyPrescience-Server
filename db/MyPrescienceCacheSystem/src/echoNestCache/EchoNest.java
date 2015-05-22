package echoNestCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Params;
import com.echonest.api.v4.Song;
import com.echonest.api.v4.SongParams;

public class EchoNest {
	
	
	private EchoNestAPI echoNest;
	
	EchoNest() {
		
	}
	
	public void changeKey(String key) {
		echoNest = new EchoNestAPI(key);
		echoNest.setTraceSends(true);
		echoNest.setTraceRecvs(true);
	}
	
	public Song searchSong(String title, String artistName) {
		SongParams p = new SongParams();
		p.setTitle(title);
		p.setArtist(artistName);
        p.add("bucket", "audio_summary");
        p.add("bucket", "song_type");
        p.includeTracks();
        p.addIDSpace("spotify");
        p.add("results", 1);
        
        List<Song> songs;
        Object audio_summary;
		try {
			songs = echoNest.searchSongs(p);
			if(songs.isEmpty()) {
				
			} else {
				Song song = songs.get(0);
				return song;
			}
			
		}  catch (EchoNestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Song searchSongWithTrackId(String trackId) {
		SongParams p = new SongParams();
        p.add("bucket", "audio_summary");
        p.add("bucket", "song_type");
        p.addIDSpace("spotify");
        p.add("results", 1);
        p.add("track_id", "spotify:track:"+trackId);
        
        List<Song> songs;
        Object audio_summary;
		try {
			songs = echoNest.getSongs(p);
			if(songs.isEmpty()) {
				
			} else {
				Song song = songs.get(0);
				return song;
			}
			
		}  catch (EchoNestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
