/**
 * Scripting Door Rick Meegens & Michiel Dondorp
 * ICA Arnhem IC1C
 */
package towerdefense;

public class FrostProjectile extends BaseProjectile
{
	public FrostProjectile(TowerDefense game, Mob pointer, BaseTower p)
	{
		super(game, pointer, p);
		
		setImage("/images/FrostProjectile.png", 5, 5);
	}
}