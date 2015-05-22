package lucene;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher {

	public static void main(String[] args) throws IOException, ParseException  {
		
		StandardAnalyzer analyzer = new StandardAnalyzer();
		
		File file = new File("songIndexer");
		Directory index = FSDirectory.open(file);
		
		String querystr = "Linkin park";
	    Query q = new QueryParser("song", analyzer).parse(querystr);

	    int hitsPerPage = 15;
	    IndexReader reader = DirectoryReader.open(index);
	    IndexSearcher searcher = new IndexSearcher(reader);
	    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, false);
	    searcher.search(q, collector);
	    ScoreDoc[] hits = collector.topDocs().scoreDocs;
	    
	    int[] lineNumbers = new int[hits.length];
	    for(int i=0;i<hits.length;++i) {
	      int docId = hits[i].doc;
	      Document d = searcher.doc(docId);
	      String song_id = d.get("song_id");
	      String[] combined = d.get("song").split("<>");
//	      System.out.println(combined[0] + " " + combined.length );
	      String title = combined[0];
	      String artist = combined[1];
//	      
	      System.out.println(song_id + " / " + title + " / " + artist);
	    }
	    reader.close();
	}

}

//SOSAHVL137405DAD67, SOBLLEW137759334A5, SOBUHQV1315CD4A404, SODCSIR12AF72A1BD9, SOFMKWD13773EE0DFF