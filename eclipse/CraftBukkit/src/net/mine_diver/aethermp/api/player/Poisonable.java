package net.mine_diver.aethermp.api.player;

public interface Poisonable extends PlayerApplicableInterface {
	
	public void updatePoison();
	public boolean afflictPoison();
	public boolean curePoison();
}
