import java.util.*;

public class Document
{
    private BufferedReader f;
    private PrintWriter outf;
    private ArrayList<String> wordDictionary;
    private ArrayList<String> humanText;

    public Document()
    {
        readWords("words.txt")
    }
    public void readWords(String filename)
     {
         f = new BufferedReader(new FileReader(filename));
         StringTokenizer st = new StringTokenizer(f.readLine());
         int numWords = Integer.parseInt(st.nextToken());

         wordDictionary = new String[numWords];

         for(int i = 0; i < numWords; i++)
         {
             st = new StringTokenizer(f.readLine());
             wordDictionary.add(st.nextToken());
         }
     }

     public void readInputFile(String filename)
     {
         f = new BufferedReader(new FileReader(filename));
         
     } 

     public ArrayList<String> spellingErrors()
     {
         ArrayList<String> errors = new ArrayList<String>();

     }
    public void retrieveText(String filename)
     {
         f = new BufferedReader(new FileReader(filename));
         StringTokenizer st = new StringTokenizer(f.readLine());
         int numWords = Integer.parseInt(st.nextToken());

         humanText = new String[numWords];

         for(int i = 0; i < numWords; i++)
         {
             st = new StringTokenizer(f.readLine());
             humanText.add(st.nextToken());
         }
     }
}