import java.io.IOException;

public class WordGameRunner
{
    public static void main(String[] args)  throws IOException
    {
        System.out.println("running");
        WordGameWithBot game = new WordGameWithBot();
        game.play();
    }
}