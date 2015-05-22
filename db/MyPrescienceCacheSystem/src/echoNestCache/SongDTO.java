package echoNestCache;

import java.util.ArrayList;

public class SongDTO {
	
	private String id;
	private String artist_id;
	private String title;
	private String artist;
	private String songType;
	private float tempo;
	private float time_signature;
	private float duration;
	private float valence;
	private float loudness;
	private float danceability;
	private float energy;
	private float liveness;
	private float speechiness;
	private float acousticness;
	private float instrumentalness;
	private int mode;
	private int key;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArtist_id() {
		return artist_id;
	}

	public void setArtist_id(String artist_id) {
		this.artist_id = artist_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getSongType() {
		return songType;
	}

	public void setSongType(String songType) {
		this.songType = songType;
	}

	public float getTempo() {
		return tempo;
	}

	public void setTempo(float tempo) {
		this.tempo = tempo;
	}

	public float getTime_signature() {
		return time_signature;
	}

	public void setTime_signature(float time_signature) {
		this.time_signature = time_signature;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public float getValence() {
		return valence;
	}

	public void setValence(float valence) {
		this.valence = valence;
	}

	public float getLoudness() {
		return loudness;
	}

	public void setLoudness(float loudness) {
		this.loudness = loudness;
	}

	public float getDanceability() {
		return danceability;
	}

	public void setDanceability(float danceability) {
		this.danceability = danceability;
	}

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

	public float getLiveness() {
		return liveness;
	}

	public void setLiveness(float liveness) {
		this.liveness = liveness;
	}

	public float getSpeechiness() {
		return speechiness;
	}

	public void setSpeechiness(float speechiness) {
		this.speechiness = speechiness;
	}

	public float getAcousticness() {
		return acousticness;
	}

	public void setAcousticness(float acousticness) {
		this.acousticness = acousticness;
	}

	public float getInstrumentalness() {
		return instrumentalness;
	}

	public void setInstrumentalness(float instrumentalness) {
		this.instrumentalness = instrumentalness;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

}

