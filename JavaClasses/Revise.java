import java.util.*;
import java.io.*;


public class Revise
{
    private BufferedReader f;
    private PrintWriter outf;
    private ArrayList<String> wordDictionary;
    private Document inputDocument;
    private HashMap<Token, ArrayList<Integer>> wordsInSentences;

    public Revise(String filename) throws IOException
    {
        readDictWords("wordList.txt");
        readInputFile(filename);
        f.close();
        
    }
    public void readDictWords(String filename) throws IOException
    {

        f = new BufferedReader(new FileReader(filename));
        StringTokenizer st = new StringTokenizer(f.readLine());
        int numWords = Integer.parseInt(st.nextToken());

        wordDictionary = new ArrayList<String>();

        for(int i = 0; i < numWords; i++)
        {
            st = new StringTokenizer(f.readLine());
            wordDictionary.add(st.nextToken());
        }
    }

    public void readInputFile(String filename) throws IOException
    {
        f = new BufferedReader(new FileReader(filename));
        Scanner sc = new Scanner(f);
        inputDocument = new Document(sc);
        inputDocument.parseDocument();
        wordsInSentences = inputDocument.getWordsInSentences();
    }

    public ArrayList<Token> spellingErrors()
    {
        HashSet<Token> inWords = inputDocument.getWordSet();
        ArrayList<Token> errors = new ArrayList<Token>();
        for(Token t: inWords)
        {
            if(!wordDictionary.contains(t.getToken()))
            {
                errors.add(t);
            }
        }
        return errors;
    }

    public void outputErrors(ArrayList<Token> errors) throws IOException
    {
        outf = new PrintWriter(new File("errors.txt"));

        for(Token t: errors)
        {
            outf.println(t.getToken());
        }

        noteRepetition(outf);

        outf.close();
    }

    public void noteRepitition(PrintWriter outf)
    {
        for(Token k: wordsInSentences.keySet())
        {
            int consecutive = 0;
            ArrayList<Integer> sentences = wordsInSentences.get(k);

            for(int i = 0; i<sentences.size()-1; i++)
            {
                if(sentences.get(i)==sentences.get(i+1))
                {
                    consecutive++;

                }
            }
            if(consecutive>2)
            {
                addRepetition(outf, k, consecutive);
            }
        }
    }

    public void addRepetition(PrintWriter outf, Token k, int consecutive)
    {
        outf.println(k.getToken()+" is a repetitious word occuring in "+consecutive+" consecutive sentences.");
    }

}
