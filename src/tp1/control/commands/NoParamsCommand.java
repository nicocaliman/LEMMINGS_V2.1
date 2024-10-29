package tp1.control.commands;

public abstract class NoParamsCommand extends Command
{
	public NoParamsCommand(String name, String shortcut, String details, String help) 
	{
		super(name, shortcut, details, help);
	}

	@Override
	public Command parse(String[] commandWords)
	{
		Command c = null;
		
		if(this.matchCommandName(commandWords[0]) && commandWords.length == 1)	//if command from available commands list matches user input
		{
			c = this;	//instance of the current command
		}
		
		return c;	//return null if no match exists, else return the instance of the command
	}
}