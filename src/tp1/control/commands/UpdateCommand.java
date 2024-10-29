package tp1.control.commands;

import tp1.logic.Game;
import tp1.view.GameView;
import tp1.view.Messages;

public class UpdateCommand extends NoParamsCommand
{
	//private constants
    private static final String NAME = Messages.COMMAND_UPDATE_NAME;
    private static final String SHORTCUT = Messages.COMMAND_UPDATE_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_UPDATE_DETAILS;
    private static final String HELP = Messages.COMMAND_UPDATE_HELP;

    //constructor
    public UpdateCommand()
    {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
    
    @Override
    protected boolean matchCommandName(String name) 
	{
		return getShortcut().equalsIgnoreCase(name) || 
				getName().equalsIgnoreCase(name) ||
				name.equals(Messages.EMPTY);
	}
    
	@Override
	public void execute(Game game, GameView view) 
	{		
		game.update();	//update game
		view.showGame();	//show updated board
	}
}
