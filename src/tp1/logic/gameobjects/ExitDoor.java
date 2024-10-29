package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.view.Messages;

public class ExitDoor extends GameObject
{		
	//private constants
	private static final String ICON_EXIT_DOOR = Messages.EXIT_DOOR;
	
	//constructor
	public ExitDoor(Position position)
	{
		super(position);
	}

	public String getIcon()
	{
		return ICON_EXIT_DOOR;
	}
		
	@Override
	public String toString()
	{
		return null;
	}

	@Override
	public boolean isSolid()
	{
		return false;	//exit door is not a wall
	}

	@Override
	public boolean isExit() 
	{
		return true;	//is an exit door
	}

	@Override
	public void update()
	{
		
	}
}