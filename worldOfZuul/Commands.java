package worldOfZuul;

public enum Commands
{
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), CHOP("chop"), PLANT("plant");
    
    private String commandName;
    
    Commands(String commandString)
    {
        this.commandName = commandString;
    }
    
    public String toString()
    {
        return commandName;
    }
}
