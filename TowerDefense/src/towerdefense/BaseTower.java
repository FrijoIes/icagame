package towerdefense;

import phonegame.*;
import phonegame.utils.*;

public abstract class BaseTower extends GameItem implements IAlarmListener
{
	protected TowerDefense mygame;
	protected boolean isActive;
	protected MoveableGameItem target;
	protected BaseProjectile projectile;
	
	// Upgrades
	protected int distancelevel;
	protected int fireratelevel;
	protected int powerlevel;
	
	protected int firerate;
	
	public BaseTower(TowerDefense game)
	{
		super();
		
		mygame = game;
		isActive = true;
		
		firerate = 25;
        setImage("/images/wall.png", 20, 20);
        
        mygame.setTimer(firerate, 0, this);
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
		BaseProjectile projectile = new BaseProjectile(mygame, target);
		projectile.setPosition(this.getX(), this.getY());
		
		mygame.addGameItem(projectile);
	}
	
	protected void lockTarget(MoveableGameItem pointer)
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
	
	public void animate()
	{}
	
	public void alarm(int id)
	{
		if(id == 0) // Fire
		{
			if(isActive)
			{
				drawFacing();
				fire();
				mygame.setTimer(firerate, 0, this);
			}
		}
	}
}
