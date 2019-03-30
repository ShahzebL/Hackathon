public class Token
{
    private String value;
    private boolean isWord;
    private String type;

    private BufferedReader f;
    private PrintWriter outf;
    private String[] wordDictionary;

     public void readWords(String filename)
     {
         f = new BufferedReader(new FileReader(filename));
         StringTokenizer st = new StringTokenizer(f.readLine());
         int numWords = Integer.parseInt(st.nextToken());

         wordDictionary = new String[numWords];

         for(int i = 0; i < numWords; i++)
         {
             st = new StringTokenizer(f.readLine());
             wordDictionary[i] = st.nextToken();
         }
     }
    public Token(String value1, String type1)
    {
        this.value = value1;
        type = type1;
        
    }

    public boolean isWord()
    {
        return isWord;
    }

    public boolean inDictionary(String word, int low, int high)
    {
        if(low<high)
        {
            int mid = (low+high)/2;
            if(word.compareTo(wordDictionary[mid])>0)
            {
                return inDictionary(word, mid, high);
            }
            else if(word.compareTo(wordDictionary[])){
                
            }
        }
    }
}