package tp1.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tp1.logic.gameobjects.GameObject;
import tp1.view.Messages;

public class GameObjectContainer
{
	private List<GameObject> objects;

	public GameObjectContainer()
	{
		objects = new ArrayList<GameObject>();
	}
	
	// Only one add method (polymorphism)
	public void add(GameObject object) 
	{
		this.objects.add(object);	//add object to the list	
	}

	public void update()
	{
		for(GameObject o: objects)
		{
			o.update();	//update game object
		}
		
		this.removeDead(); //remove dead lemmings
	}
	
	public String positionToString(int col, int row)
	{
		StringBuilder elements = new StringBuilder();
		
		for(GameObject o: objects)
		{
			if(o.isInPosition(new Position(col, row)))
			{
				elements.append(o.getIcon());
			}
		}		
		
		return elements.length() > 0 ? elements.toString() : Messages.EMPTY;
	}
	
	private void removeDead()
	{
		Iterator<GameObject> iterator = this.objects.iterator();
		
		while (iterator.hasNext())
		{
	        GameObject o = iterator.next();
	        
	        if (!o.isAlive()) {     //if lemming´s dead
	            iterator.remove();   // remove lemming from the list
	        }
	    }
	}
	
	public boolean isSolid(Position position)
	{
		int i = 0;
		
		while(i < this.objects.size())
		{			
			if(this.objects.get(i).isSolid() && this.objects.get(i).isInPosition(position))
				return true;
			
			++i;					
		}
		
		return false;	//true if there is a wall in that position
	}
	
	public boolean isExit(Position position)
	{
		int i = 0;
		
		while(i < this.objects.size())
		{			
			if(this.objects.get(i).isExit() && this.objects.get(i).isInPosition(position))
				return true;
			
			++i;
		}
		
		return false;	//true if there is an exit door in that position
	}

	// TODO you should write a toString method to return the string that represents the object status
	// @Override
	// public String toString()
}
