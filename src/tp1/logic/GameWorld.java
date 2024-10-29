package tp1.logic;

public interface GameWorld
{
	public boolean isInAir(Position pos);
	public void lemmingArrived();
	public boolean isSolid(Position pos);
	public boolean isExit(Position pos);
	public void lemmingDead();
}
