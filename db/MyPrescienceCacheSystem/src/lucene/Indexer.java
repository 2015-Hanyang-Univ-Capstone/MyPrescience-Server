package lucene;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
import org.apache.lucene.util.Version;

import echoNestCache.DBHandler;


public class Indexer {
	
	public static void main(String[] args) throws IOException, ParseException  {
		
		StandardAnalyzer analyzer = new StandardAnalyzer();
		
		File file = new File("songIndexer");
		Directory dir = FSDirectory.open(file);
	    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
	    IndexWriter w = new IndexWriter(dir, config);
	    
	    DBHandler dbHandler = new DBHandler();
	    dbHandler.getAllSongIntoIndex(w);
        
	    System.out.println("make Index!");
	    w.close();
	}
}
