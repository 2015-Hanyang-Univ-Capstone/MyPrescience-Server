package connectEchoNest;

import java.util.ArrayList;

public class GomLog {
	
	private ArrayList<String> ipList;
	private ArrayList<String> dateList;
	private ArrayList<String> timeList;
	private ArrayList<String> titleList;
	private ArrayList<String> artistList;
	private ArrayList<Integer> sizeList;
	private ArrayList<Integer> utcList;
	private ArrayList<String> versionList;
	
	public GomLog() {
		ipList = new ArrayList<String>();
		dateList= new ArrayList<String>();
		timeList = new ArrayList<String>();
		titleList = new ArrayList<String>();
		artistList = new ArrayList<String>();
		sizeList = new ArrayList<Integer>();
		utcList = new ArrayList<Integer>();
		versionList = new ArrayList<String>();
	}
	
	public ArrayList<String> getTitleList() {
		return titleList;
	}
	public void addTitle(String title) {
		titleList.add(title);
	}
	public ArrayList<String> getArtistList() {
		return artistList;
	}
	public void addArtist(String artist) {
		artistList.add(artist);
	}

	public ArrayList<String> getIpList() {
		return ipList;
	}

	public void addIp(String ip) {
		ipList.add(ip);
	}

	public ArrayList<String> getDateList() {
		return dateList;
	}

	public void addDate(String date) {
		dateList.add(date);
	}

	public ArrayList<String> getTimeList() {
		return timeList;
	}

	public void addTime(String time) {
		timeList.add(time);
	}

	public ArrayList<Integer> getSizeList() {
		return sizeList;
	}

	public void addSize(int size) {
		sizeList.add(size);
	}

	public ArrayList<Integer> getUtcList() {
		return utcList;
	}

	public void addUtc(int utc) {
		utcList.add(utc);
	}

	public ArrayList<String> getVersionList() {
		return versionList;
	}

	public void addVersion(String version) {
		versionList.add(version);
	}
}
