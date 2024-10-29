package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;

public abstract class GameObject 
{
	protected Position pos;
	protected boolean isAlive;
	protected Game game;
	
	public GameObject(Game game, Position pos) 
	{
		this.isAlive = true;
		this.pos = pos;
		this.game = game;
	}
	
	public GameObject(Position pos)
	{
		this.pos = pos;
		this.isAlive = true;
	}
	
	public boolean isInPosition(Position p) 
	{
		return this.pos.equals(p);
	}
 	
	public boolean isAlive() 
	{
		return isAlive;
	}
	
	public abstract boolean isSolid();
	public abstract boolean isExit();
	public abstract void update();
		
	public abstract String getIcon();
}
