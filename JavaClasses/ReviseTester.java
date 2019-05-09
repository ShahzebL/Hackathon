import java.io.IOException;

import javax.script.Invocable;

public class ReviseTester
{
    public static void main(String[] args) throws IOException
    {
        System.out.println("Yo we go to the revisetester");
        engine.eval(new FileReader("index.js"));
        Invocable invocable = (Invocable)engine;
        invocable.invokeFunction("processText");
        
        Revise r = new Revise("in.txt");
        r.outputErrors(r.spellingErrors());
    }
}
