
package ChopMaster.domain.commands;

public interface Command {

    Commands getCommandName();

    String getCommandValue();

    boolean hasCommandValue();

    boolean isUnknown();
    
}
