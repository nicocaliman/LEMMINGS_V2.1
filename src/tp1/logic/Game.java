package tp1.logic;

import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.Wall;

public class Game implements GameModel, GameStatus, GameWorld
{
	//public constants
	public static final int DIM_X = 10;
	public static final int DIM_Y = 10;
		
	//private constants
	private static final int LEMMINGS_LVL_1 = 4;
	private static final int LEMMINGS_LVL_0 = 3;
	private static final int WIN = 2;
		
	//attributes
	private int numberOfCycles;
	private int numberLemmingsInBoard;
	private int numberOfDeadLemmings;
	private int numberOfExitLemmings;
	private int numberOfLemmingsToWin;
	private boolean exit;	
	private int nLevel;		
	private GameObjectContainer container;
	
	public Game(int nLevel)
	{
		this.nLevel = nLevel;
		this.reset();
	}
	
// GameStatus methods
	@Override
	public int getCycle()
	{
		return this.numberOfCycles;	//return number of cycles played
	}

	@Override
	public int numLemmingsInBoard() 
	{
		return this.numberLemmingsInBoard;	//return number of lemmings in board
	}

	@Override
	public int numLemmingsDead() 
	{
		return this.numberOfDeadLemmings;	//return number of lemmings who died
	}

	@Override
	public int numLemmingsExit() 
	{
		return this.numberOfExitLemmings;	//return number of lemmings that crossed the door
	}

	@Override
	public int numLemmingsToWin()
	{
		return this.numberOfLemmingsToWin;	//return number of lemmings needed to win(they must cross the door)
	}

	@Override
	public String positionToString(int col, int row)
	{
		return this.container.positionToString(col, row);
	}

	@Override
	public boolean playerWins() 
	{
		return (this.numLemmingsToWin() == this.numLemmingsExit());
	}

	@Override
	public boolean playerLooses()
	{
		return this.numLemmingsInBoard() == 0;	//player looses if there is no lemmings left in board
	}

// GameModel methods
	 @Override
	public void update() 
	{
		this.updateNumberOfCycles();	//update number of cycles
		this.container.update();	//update game objects
	}
	
	 @Override
	public void exit()
	{
		this.exit = true;	//user quits the game		
	}
	
	 @Override
	public boolean isFinished() 
	{
		return this.exit || this.playerLooses();	//game finishes if user quits or the number of lemmings in board equals 0
	}
	
	public void reset()
	{
		this.numberOfCycles = 0;	//starts at 0
		this.numberOfDeadLemmings = 0;
		this.numberOfLemmingsToWin = WIN;
		
		this.exit = false;	//initially not finished
		
		this.container = new GameObjectContainer();	//instance of GameObjectContainer class
		
		if(this.nLevel == 0)	//if level = 0		
			initGame0();	//init game map 0
				
		else	//else		
			initGame1();	//init game map 1		
	}	
	
// GameWorld methods (callbacks)
	
	@Override
	public boolean isInAir(Position pos) 
	{
        return !this.container.isSolid(pos);	//true if lemming´s in the air
    }

		
	@Override
	public void lemmingArrived() 
	{
		this.numberOfExitLemmings++;
		this.numberLemmingsInBoard--;
	}
	

	@Override
	public boolean isSolid(Position pos)
	{
		return this.container.isSolid(pos);	//true if it is a wall
	}	

	@Override
	public boolean isExit(Position pos)
	{
		return this.container.isExit(pos);	//true if it is the exit door
	}
	
// Other methods
	private void initGame0()
	{		
		//add walls
		this.container.add(new Wall(new Position(8, 1)));
		this.container.add(new Wall(new Position(9, 1)));
				
		this.container.add(new Wall(new Position(2, 4)));
		this.container.add(new Wall(new Position(3, 4)));
		this.container.add(new Wall(new Position(4, 4)));
				
		this.container.add(new Wall(new Position(7, 5)));		

		this.container.add(new Wall(new Position(4, 6)));
		this.container.add(new Wall(new Position(5, 6)));
		this.container.add(new Wall(new Position(6, 6)));
		this.container.add(new Wall(new Position(7, 6)));		

		this.container.add(new Wall(new Position(8, 8)));		

		this.container.add(new Wall(new Position(0, 9)));
		this.container.add(new Wall(new Position(1, 9)));		
		this.container.add(new Wall(new Position(8, 9)));
		this.container.add(new Wall(new Position(9, 9)));
				
		//add lemmings
		this.container.add(new Lemming(this, new Position(9, 0)));
				
		this.container.add(new Lemming(this, new Position(2, 3)));
				
		this.container.add(new Lemming(this, new Position(0, 8)));		
				
		//add exit door
		this.container.add(new ExitDoor(new Position(4, 5)));
		

		this.numberLemmingsInBoard = LEMMINGS_LVL_0;	//number of lemmings in board LVL 0 = 3
	}
	
	private void initGame1()
	{
		this.initGame0();		
		
		this.container.add(new Lemming(this, new Position(3, 3)));
		
		this.numberLemmingsInBoard = LEMMINGS_LVL_1;
	}
	
	private void updateNumberOfCycles()
	{
		this.numberOfCycles++;
	}
	// TODO you should write a toString method to return the string that represents the object status
	// @Override
	// public String toString()

	@Override
	public void lemmingDead()
	{
		this.numberOfDeadLemmings++;
		this.numberLemmingsInBoard--;		
	}
}