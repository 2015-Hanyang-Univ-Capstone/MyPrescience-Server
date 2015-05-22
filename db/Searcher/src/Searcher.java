
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
		
		String querystr = args[0];
		QueryParser queryParser = new QueryParser("song", analyzer);
		Query query = queryParser.parse(querystr);
		
		int mode = Integer.parseInt(args[1]);
		
		if(mode == 0) {

		    int hitsPerPage = 20;
		    IndexReader reader = DirectoryReader.open(index);
		    IndexSearcher searcher = new IndexSearcher(reader);
		    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, false);
		    searcher.search(query, collector);
		    
		    ScoreDoc[] hits = collector.topDocs().scoreDocs;
		    
		    for(int i=0;i<hits.length;++i) {
		      int docId = hits[i].doc;
		      Document d = searcher.doc(docId);
		      String song_id = d.get("song_id");
		      String[] combined = d.get("song").split("<>");
		      String title = combined[0];
		      String artist = combined[1];
		      System.out.println(song_id + "/" + title + "/" + artist);
		    }
		    reader.close();
		    
		} else if (mode == 1) {
			
			int hitsPerPage = 1;
		    IndexReader reader = DirectoryReader.open(index);
		    IndexSearcher searcher = new IndexSearcher(reader);
		    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, false);
		    searcher.search(query, collector);
		    ScoreDoc[] hits = collector.topDocs().scoreDocs;
		    
		    for(int i=0;i<hits.length;++i) {
		      int docId = hits[i].doc;
		      Document d = searcher.doc(docId);
		      String song_id = d.get("song_id");
		      String[] combined = d.get("song").split("<>");
		      String title = combined[0];
		      String artist = combined[1];
		      System.out.println(song_id);
		    }
		    reader.close();
			
		}
	}

}

