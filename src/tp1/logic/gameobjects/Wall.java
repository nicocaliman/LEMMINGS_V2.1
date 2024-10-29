package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.view.Messages;

public class Wall extends GameObject
{

	private static final String ICON_WALL = Messages.WALL;

	public Wall(Position pos)
	{
		super(pos);
	}

	@Override
	public String getIcon() 
	{
		return ICON_WALL;
	}

	@Override
	public boolean isSolid() 
	{
		return true;	//is a wall
	}

	@Override
	public boolean isExit() 
	{
		return false;	//wall is not an exit door
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}
}