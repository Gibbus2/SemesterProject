package ChopMaster.domain.commands;

public enum Commands
{
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), CHOP("chop"), PLANT("plant"), SHOWSCORE("showscore");
    
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
