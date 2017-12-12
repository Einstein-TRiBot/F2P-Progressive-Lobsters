package scripts.F2PProgressiveLobsters.utility;

import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSGroundItem;

import scripts.F2PProgressiveLobsters.data.Vars;
import scripts.F2PProgressiveLobsters.tasks.initial.GetGold;
import static scripts.F2PProgressiveLobsters.data.Constants.*;
/**
 * 
 * @author Einstein
 *
 *
 */
public class ScriptUtil {

	/**
	 * @return true if bot has all required tools. The list dynamically changes based on fishing level.
	 */
	public static boolean hasTools() {
		boolean hasEnoughNet = Vars.get().equipment.contains(SMALL_FISHING_NET) ? Inventory.getCount(SMALL_FISHING_NET) >= 1   : true;
		boolean hasEnoughRod = Vars.get().equipment.contains(FLY_FISHING_ROD)   ? Inventory.getCount(FLY_FISHING_ROD)   >= 1   : true;
		boolean hasEnoughFeather = Vars.get().equipment.contains(FEATHER)       ? Inventory.getCount(FEATHER)           >= 600 : true;
		boolean hasEnoughCage = Vars.get().equipment.contains(LOBSTER_POT)      ? Inventory.getCount(LOBSTER_POT)       >= 1   : true;
		return hasEnoughNet && hasEnoughRod && hasEnoughFeather && hasEnoughCage;
	}

	/**
	 * @return true if bot has at least 5k gold, false otherwise
	 */
	public static boolean hasGold() {
		return Inventory.getCount(COINS) >= 5000;
	}

	/**
	 * Searches for dropped lobsters, notifies the bot if:
	 * 
	 * Only notifies bot if already in the banking stage (lvl 40 fishing +)
	 * Only notifies bot if lobsters are on the fishing dock (anti-lure).
	 * 
	 * @return true if lobsters found and the both of the above conditions are met
	 */
	public static boolean lobstersFound() {
		if (Vars.get().dropFish)
			return false;
		RSGroundItem[] lobster = GroundItems.findNearest(LOBSTER_ID);
		if (lobster.length > 0)
			return NET_CAGE_FISHING_AREA.contains(lobster[0].getPosition());
		return false;
	}

	/**
	 * Returns current equipment list as an array.
	 * 
	 * @return equipment list as array.
	 */
	public static int[] currentEquipmentAsArray() {
		int[] equipmentArray = new int[Vars.get().equipment.size()];
		for (int i = 0; i < Vars.get().equipment.size(); i++)
			equipmentArray[i] = Vars.get().equipment.get(i);
		return equipmentArray;
	}

	/**
	 * Decides if bot should be notified about trade request based on available gold.
	 * 
	 * @param name of the player attempting to trade with the bot
	 */
	public static void decideTradeReaction(String name) {
		if (!ScriptUtil.hasGold())
			GetGold.getInTrade(name);
	}
	
}