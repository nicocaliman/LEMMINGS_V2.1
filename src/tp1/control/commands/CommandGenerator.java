package tp1.control.commands;

import java.util.Arrays;
import java.util.List;

public class CommandGenerator
{
	//store every instance of the commands
	private static final List<Command> availableCommands = Arrays.asList(
			new UpdateCommand(),
			new ResetCommand(),
			new HelpCommand(),
			new ExitCommand()
	);

	public static Command parse(String[] commandWords) 
	{			
		for (Command c: availableCommands) 
		{
			Command command = c.parse(commandWords);	//check if user input matches the command
			
			if (command != null)	//if it matches (parsing succeeded)
			{
	            return command;  //return command 
	        }
		}
		
		return null;	//if there were no matches, ERROR
	}
		
	public static String commandHelp() 
	{
		StringBuilder commands = new StringBuilder();
		
		for (Command c: availableCommands) 
		{
			commands.append(c.helpText());	//call helpText() method from every subclass
		}
		
		return commands.toString();
	}
}