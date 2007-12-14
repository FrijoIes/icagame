package towerdefense;

import phonegame.*;
import phonegame.utils.*;
import java.util.Vector;

public abstract class BaseTower extends GameItem implements IAlarmListener
{
	protected TowerDefense mygame;
	protected boolean isActive;
	protected Mob target;
	protected BaseProjectile projectile;
	
	// Upgrades
	protected int distancelevel;
	protected int fireratelevel;
	protected int powerlevel;
	protected String towerType;
	
	protected int firerate;
	protected double maxdist;
	protected int damage;

	protected int cashvalue;
	
	public BaseTower(TowerDefense game)
	{
		super();
		
		mygame = game;
		isActive = true;
		
		distancelevel = fireratelevel = powerlevel = firerate = damage = 0;
		
		incFireRateLevel();
		incPowerLevel();
		incDistanceLevel();
		
		maxdist = 50f;
		
		cashvalue = 0;
		
        setImage("/images/wall.png", 20, 20);
        
        mygame.setTimer(firerate, 0, this);
        mygame.setTimer(10, 1, this);
	}
	
	private final double facing()
	{
		if(target == null)
			return 0f;
		
        int dx = target.getX() - getX();
        int dy = target.getY() - getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double sindir = dy/distance;
        double cosdir = dx/distance;
        double radians;

        if ( Math.abs(cosdir) < Math.abs(sindir) )
        {
            if ( sindir < 0 )
                radians = Math.PI/2 - Tools.arcsin(cosdir);
            else
            	radians = Math.PI + Math.PI/2 + Tools.arcsin(cosdir);
        } else
        {
            if ( cosdir > 0 )
            	radians = Tools.arcsin(-sindir);
            else
            	radians = Math.PI - Tools.arcsin(-sindir);
        }

        return (360+Tools.round(Math.toDegrees(radians)))%360;
	}
	
	private void drawFacing()
	{
		int index = (int)Math.floor(facing() / 45);
		this.setFrame( index  );
	}
	
	protected void fire()
	{
		/*
		BaseProjectile projectile = new BaseProjectile(mygame, target, this);
		projectile.setPosition(this.getX(), this.getY());
		
		mygame.addGameItem(projectile);
		*/
	}
	
	protected void incFireRateLevel()
	{
		fireratelevel++;
	}
	
	protected void incDistanceLevel()
	{
		distancelevel++;
	}
	
	protected void incPowerLevel()
	{
		powerlevel++;
	}
	
	public int getDistancelevel()
	{
		return distancelevel;
	}
	public int getFireratelevel()
	{
		return fireratelevel;
	}
	public int getPowerlevel()
	{
		return powerlevel;
	}
	
	public String getTowertyp()
	{
		return towerType;
	}
	
	protected void lockTarget(Mob pointer)
	{
		target = pointer;
	}
	
	public void setActive(boolean active)
	{
		isActive = active;
	}
	
	public boolean getActive()
	{
		return isActive;
	}
	
	public int getCashValue()
	{
		return cashvalue;
	}
	
	public void animate()
	{}
	
	public void alarm(int id)
	{
		if(id == 0) // Fire
		{
			if(target != null && target.getActive() == true)
			{
				drawFacing();
				fire();
			}
			
			mygame.setTimer(firerate, 0, this);
		}
		else if(id == 1)
		{
			mygame.setTimer(10, 1, this);
			
			if(target == null)
			{
				Vector items = mygame.getItemsOfType("Mob");
				
				for(int i=0; i < items.size(); i++)
				{
					Mob current = ((Mob) items.elementAt(i) );
						
					if(current != null)
					{
						int dx = (current.getX() + current.getFrameWidth() / 2) - (this.getX() + this.getFrameWidth() / 2);
						int dy = (current.getY() + current.getFrameHeight() / 2) - (this.getY() + this.getFrameHeight() / 2);
						double distance = Math.sqrt(dx * dx + dy * dy);
						
						if(distance <= maxdist)
							target = current;
					}
				}
			}
			else
			{
				if( target.getActive() == false  )
				{
					target = null;
					return;
				}
				
				int dx = (target.getX() + target.getFrameWidth() / 2) - (this.getX() + this.getFrameWidth() / 2);
				int dy = (target.getY() + target.getFrameHeight() / 2) - (this.getY() + this.getFrameHeight() / 2);
				double distance = Math.sqrt(dx * dx + dy * dy);
        
				if(distance > maxdist)
					target = null;
			}
		}
	}
}
