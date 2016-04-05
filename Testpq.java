import java.util.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;


import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.MutableAttributeSet;

public class Testpq 
     {

           public static void Connect(String s) throws Exception
        {
           //Set URL
           URL url = new URL(s);
           URLConnection spoof = url.openConnection();
           spoof.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)" );
           BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
           String strLine = "";
           FileWriter fstream = new FileWriter("tags.txt");
           BufferedWriter out = new BufferedWriter(fstream);
 
           while ((strLine = in.readLine()) != null)
                 {
                    out.write(strLine);
   
                 }
 
          out.write("End of page.");
          out.close();
        }

    public static List<String> extractText(Reader reader) throws IOException 
        {
              final ArrayList<String> list = new ArrayList<String>();
              ParserDelegator parserDelegator = new ParserDelegator();
              ParserCallback parserCallback = new ParserCallback() 
                  {
                      public void handleText(final char[] data, final int pos) 
                          {
                             list.add(new String(data));
                          }
      
                 };
             parserDelegator.parse(reader, parserCallback, true);
             return list;
        }

    public static void main(String[] args) 
        {

              PriorityQueue<Node> open = new PriorityQueue<Node>(100,
         	new Comparator<Node>() 
                      {
                          public int compare(Node n1, Node n2)
                              {
                                 if (n1.getscore() > n2.getscore())
                                       {
                                           return -1;
                                       }
                                 else if (n1.getscore() < n2.getscore())
                                       {
                                           return +1;
                                       }
                                 else 
                                       {  // equal
                                           return 0;
                                       }
                              }
                      });

	      try
                     {
	
	                DataInputStream input = new DataInputStream(System.in);
	                System.out.println("enter keyword");
	                String kw=input.readLine();
                        FileInputStream fstream = new FileInputStream("list2.txt");
                        DataInputStream in = new DataInputStream(fstream);
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        String str;
	
		        while((str=br.readLine())!=null)
                              {
	                           Connect(str);

	                           FileReader reader = new FileReader("tags.txt");	
                                   List<String> lines = Testpq.extractText(reader);
	                           FileWriter fstream1 = new FileWriter("text.txt");
 	                           BufferedWriter out = new BufferedWriter(fstream1);

                                   for (String line : lines) 
                                          {
                                               out.write(line);
	                                  }
                                   out.close();
	                           int c=0;
	                           Scanner s = new Scanner(new File("text.txt"));
                                   while (null != s.findWithinHorizon(kw, 0))
                                          {
            
		                                c=c+1;
		
                                          }
	                           s.close();
	                           open.add(new Node(str,c));
                              }	

                                   in.close();
                      }
	     catch(Exception e)
                     {
                               System.out.println(e);
                     }
             for(int i=0;i<10;i++)
                     {
		               Node t = (Node)(open.remove());
                               System.out.println(t.geturl());
		               System.out.println(t.getscore());
                     }
           }//main ends
     }

class Node 
          {
    	          String url;
	          int score;

	          Node(String a,int b)
		             {
		                url=a;
		                score=b;
		             }

	          int getscore()
		             {
		                return score;
		             }
	          String geturl()
		             {
		                return url;
		             }
	 }