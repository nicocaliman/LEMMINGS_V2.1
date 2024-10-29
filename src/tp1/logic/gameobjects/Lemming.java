package tp1.logic.gameobjects;

import tp1.logic.Direction;
import tp1.logic.Game;
import tp1.logic.Position;
import tp1.logic.lemmingRoles.WalkerRole;

public class Lemming extends GameObject 
{
	//private constants
	private static final int FALL = 2;
	
	//attributes
	private WalkerRole role;
	private int fallForce;
	private Direction direction;	
	
	public Lemming(Game game, Position pos) 
	{
		super(game, pos);
		this.role = WalkerRole();
		this.fallForce = 0;	//initially 0
		this.direction = Direction.RIGHT;	//default movement
	}
	
	private WalkerRole WalkerRole() 
	{
		return new WalkerRole();
	}

	// Not mandatory but recommended
	public void walkOrFall()
	{
		if (this.game.isExit(this.pos))	//if lemming´s is in the exit door
		{
			this.game.lemmingArrived();	//lemming exit game
			this.dead();
		}
		
		else	//lemming can be walking or falling
		{	
			if (this.isInAir())	//if lemming is falling
			{					
				this.doFall();				//fall action					
			}
			
			else	//if lemming is walking
			{
				this.doWalk();		//walk action	
			}
		}
	}
	
	private void doWalk()
	{
		if (this.fallForce > FALL)
		{
			this.dead();
			this.game.lemmingDead();
		}
		
		else
		{
			this.fallForce = 0;
			
			if (this.getDirection() == Direction.RIGHT)	//if moving towards right
			{
				this.moveRightAction();					
			}
			
			else if (this.getDirection() == Direction.LEFT)	//if moving towards left
			{
				this.moveLeftAction();
			}
		}		
	}

	private void moveLeftAction()
	{
		Position newPosition = new Position(Direction.LEFT, this.pos);
		
		this.pos = newPosition;
		
		if (!newPosition.isInBoard()|| this.game.isSolid(newPosition)) //if new position touches right wall
		{
			this.direction = this.direction.opposite();	//set lemming´s direction
		}
		
		else	// if there is no obstacle
		{
			this.pos = newPosition;	//set new position
		}		
	}

	private void moveRightAction() 
	{
		Position newPosition = new Position(Direction.RIGHT, this.pos);
		
		if (!newPosition.isInBoard() || this.game.isSolid(newPosition)) //if new position touches right wall
		{
			this.direction = this.direction.opposite();	//set lemming´s direction
		}
		
		else	// if there is no obstacle
		{
			this.pos = newPosition;	//set new position
		}
		
	}

	private void doFall()
	{
		this.fallForce++;
		Position newPosition = new Position(Direction.DOWN, this.pos);
						
		if (!newPosition.isInBoard())	//if lemming jumps into the void
		{
			this.dead();	//lemming dies
			this.game.lemmingDead();
		}
		
		else if (this.game.isSolid(newPosition))	
		{
			if (this.fallForce > FALL)
			{
				this.dead();
				this.game.lemmingDead();
			}
			
			else
			{
				this.pos = newPosition;
			}
		}
		
		else
		{
			this.pos = newPosition;
		}
	}
	
	public void dead()
	{
		this.isAlive = false;	//set lemming´s life
	}
	
	/**
	 *  Implements the automatic update	
	 */
	
	@Override
	public void update() 
	{
		if (isAlive()) 
			role.play(this);
		//TODO fill your code
	}
	@Override
	public String getIcon() 
	{
		return this.role.getIcon(this);	//if lemming´s set to the right, show RIGHT icon, else, show LEFT icon
	}
	
	public Direction getDirection()
	{
		return this.direction;	//lemming´s direction
	}

	@Override
	public boolean isSolid()
	{
		return false;	//lemming is not a wall
	}

	@Override
	public boolean isExit() 
	{
		return false;	//lemming is not an exit
	}

	private boolean isInAir() 
	{		
		Position below = new Position(Direction.DOWN, this.pos);	//position below lemming
			
		//return true if position below lemming is not solid, else return false if there is a wall down below lemming´s position
		return game.isInAir(below);
	
	}
		
	// TODO you should write a toString method to return the string that represents the object status
	// @Override
	// public String toString()
}
