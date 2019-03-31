import java.io.IOException;

public class ReviseTester
{
    public static void main(String[] args) throws IOException
    {
        Revise r = new Revise("in.txt");
        r.outputErrors(r.spellingErrors());
    }
}
