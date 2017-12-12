package scripts.F2PProgressiveLobsters.data;

import java.awt.Image;
import java.util.ArrayList;

import org.tribot.api2007.types.RSArea;

public class Vars {
	
	// Instance manipulation
	private Vars() {}
	private static final Vars VARS = new Vars();
	public static Vars get() { return VARS; } 

	// Paint
	public String info = "";
	
	// Account data
	public boolean hasTools;
	public boolean hasGold;
	public boolean dropFish;
	public int lobsters;
	public String profit = "";
	
	// Script
	public String runningTime = "";
	public boolean shouldDisplayGoldWarning;

	// Current fishing data - dynamically changes as the bot levels up 
	public RSArea fishingArea;
	public int fishingSpot;
	public String fishingAction;
	public Image fishingImage;
	public ArrayList<Integer> equipment = new ArrayList<Integer>();

}