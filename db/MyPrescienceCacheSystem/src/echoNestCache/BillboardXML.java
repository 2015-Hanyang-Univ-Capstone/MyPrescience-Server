package echoNestCache;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.echonest.api.v4.Song;

public class BillboardXML {
	
	String RSS_BILLBOARD = "http://www.billboard.com/rss/charts/";
	String POP = "pop-songs";
	String HIPHOP = "rap-song";
	String RnB = "r-and-b-songs";
	String ROCK = "rock-songs";
	String CLUB = "dance-club-play-songs";
	String COUNTRY = "country-songs";
	String ELECTRONIC = "dance-electronic-songs";
	String HOT100 = "hot-100";
	
	XPath xpath;
	private NodeList items;
	
	BillboardXML() {
		xpath = XPathFactory.newInstance().newXPath();
	}
	
	public void setXML(NodeList items) {
		this.items = items;
	}
	
	public NodeList getPopXML() throws Exception { 
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(RSS_BILLBOARD+POP);
		NodeList items = (NodeList)xpath.evaluate("//channel/item", document, XPathConstants.NODESET);
		return items;
	}
	
	public NodeList getHiphopXML() throws Exception { 
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(RSS_BILLBOARD+HIPHOP);
		NodeList items = (NodeList)xpath.evaluate("//channel/item", document, XPathConstants.NODESET);
		return items;
	}
	
	public NodeList getRnBXML() throws Exception { 
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(RSS_BILLBOARD+RnB);
		NodeList items = (NodeList)xpath.evaluate("//channel/item", document, XPathConstants.NODESET);
		return items;
	}
	
	public NodeList getRockXML() throws Exception { 
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(RSS_BILLBOARD+ROCK);
		NodeList items = (NodeList)xpath.evaluate("//channel/item", document, XPathConstants.NODESET);
		return items;
	}
	
	public NodeList getClubXML() throws Exception { 
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(RSS_BILLBOARD+CLUB);
		NodeList items = (NodeList)xpath.evaluate("//channel/item", document, XPathConstants.NODESET);
		return items;
	}
	
	public NodeList getCountryXML() throws Exception { 
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(RSS_BILLBOARD+COUNTRY);
		NodeList items = (NodeList)xpath.evaluate("//channel/item", document, XPathConstants.NODESET);
		return items;
	}
	
	public NodeList getElectronicXML() throws Exception { 
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(RSS_BILLBOARD+ELECTRONIC);
		NodeList items = (NodeList)xpath.evaluate("//channel/item", document, XPathConstants.NODESET);
		return items;
	}
	
	public NodeList getHot100XML() throws Exception { 
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(RSS_BILLBOARD+HOT100);
		NodeList items = (NodeList)xpath.evaluate("//channel/item", document, XPathConstants.NODESET);
		return items;
	}
}

