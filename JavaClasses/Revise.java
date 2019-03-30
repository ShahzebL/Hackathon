import java.util.*;
import java.io.*;
import java.util.Arrays;

public class Revise {
    private BufferedReader f;
    private PrintWriter outf;
    private ArrayList<String> wordDictionary;
    private ArrayList<String> humanText;
    private Scanner inputScanner;
    private Scanner dictScanner;
    private Document inputDocument;
    
    public Revise(String filename)
    {
        readDictWords("words.txt");
        readInputFile(inFilefilename);

        
    }
    public void readDictWords(String filename)
     {
         
         f = new BufferedReader(new FileReader(filename));
         StringTokenizer st = new StringTokenizer(f.readLine());
         int numWords = Integer.parseInt(st.nextToken());

         wordDictionary = new ArrayList<String();

         for(int i = 0; i < numWords; i++)
         {
             st = new StringTokenizer(f.readLine());
             wordDictionary.add(st.nextToken());
         }
     }

     public void readInputFile(String filename)
     {
         f = new BufferedReader(new FileReader(filename));
         Scanner sc = new Scanner(f);
         inputDocument = new Document(sc);
         inputDocument.parseDocument();
     } 

     public ArrayList<String> spellingErrors()
     {
         ArrayList<String> errors = new ArrayList<String>();
    public Revise(Scanner dict, Scanner input) {
        readWords("words.txt");
        inputScanner = input;
        dictScanner = dict;
    }

    public void readWords(String filename) {

        f = new BufferedReader(new FileReader(filename));
        StringTokenizer st = new StringTokenizer(f.readLine());
        int numWords = Integer.parseInt(st.nextToken());

        wordDictionary = new String[numWords];

        for (int i = 0; i < numWords; i++) {
            st = new StringTokenizer(f.readLine());
            wordDictionary.add(st.nextToken());
        }
    }

    public void readInputFile(String filename) {
        f = new BufferedReader(new FileReader(filename));
        Scanner sc = new Scanner(f);
        inputDocument = new Document(sc);
        inputDocument.parseDocument();
    }

    public ArrayList<String> spellingErrors() {
        ArrayList<String> errors = new ArrayList<String>();
        return errors;
    }
}
        dictScanner = dict;
    }

    public void readWords(String filename) {

        f = new BufferedReader(new FileReader(filename));
        StringTokenizer st = new StringTokenizer(f.readLine());
        int numWords = Integer.parseInt(st.nextToken());

        wordDictionary = new String[numWords];

        for (int i = 0; i < numWords; i++) {
            st = new StringTokenizer(f.readLine());
            wordDictionary.add(st.nextToken());
        }
    }

    public void readInputFile(String filename) {
        f = new BufferedReader(new FileReader(filename));
        Scanner sc = new Scanner(f);
        inputDocument = new Document(sc);
        inputDocument.parseDocument();
    }

    public ArrayList<String> spellingErrors() {
        ArrayList<String> errors = new ArrayList<String>();
        return errors;
    }
}
