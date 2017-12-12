package scripts.F2PProgressiveLobsters.tasks.initial;

import static scripts.F2PProgressiveLobsters.data.Constants.FEATHER;
import static scripts.F2PProgressiveLobsters.data.Constants.FLY_FISHING_ROD;
import static scripts.F2PProgressiveLobsters.data.Constants.LOBSTER_POT;
import static scripts.F2PProgressiveLobsters.data.Constants.SHOP_AREA;
import static scripts.F2PProgressiveLobsters.data.Constants.SMALL_FISHING_NET;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSInterfaceComponent;
import org.tribot.api2007.types.RSNPC;

import scripts.F2PProgressiveLobsters.data.Vars;
import scripts.F2PProgressiveLobsters.framework.AbstractTask;
import scripts.F2PProgressiveLobsters.utility.ScriptUtil;
/**
 * 
 * @author Einstein
 *
 *
 */
public class GetTools implements AbstractTask {

	@Override
	public String info() {
		return "Buying tools";
	}

	@Override
	public boolean shouldExecute() {
		return Vars.get().hasGold && !Vars.get().hasTools; 
	}

	@Override
	public void execute() {
		
		// Update inventory
		if (ScriptUtil.hasTools())
			Vars.get().hasTools = true;

		// Buy missing tools
		if (walkInShop())
			if (clickTradeShopOwner())
				if (waitForShopInterface())
					checkAndBuyTools();
	}

	private boolean walkInShop() {
		if(!SHOP_AREA.contains(Player.getPosition()))
			WebWalking.walkTo(SHOP_AREA.getRandomTile());
		return SHOP_AREA.contains(Player.getPosition());
	}

	private boolean clickTradeShopOwner() {
		// If shop is already open: return true
		if (Interfaces.isInterfaceValid(300))
			return true;

		// If no shop owner was found: return false
		RSNPC[] gerrant = NPCs.findNearest("Gerrant");
		if (gerrant.length == 0)
			return false;

		// If shop owner not on screen: move camera
		if (!gerrant[0].isOnScreen())
			Camera.turnToTile(gerrant[0].getPosition());

		// If succesfully clicked shop owner: wait for interface to appear
		if (Clicking.click("Trade", gerrant[0]))
			return true;
		
		return false;
	}
	
	private boolean waitForShopInterface() {
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(300);
				return (Interfaces.isInterfaceValid(300));
			}
		}, General.random(3000, 5000));
		return Interfaces.isInterfaceValid(300);
	}
	
	private void checkAndBuyTools() {
			if (Vars.get().equipment.contains(SMALL_FISHING_NET))
				buy(SMALL_FISHING_NET, 1, 1, 1);
			if (Vars.get().equipment.contains(FLY_FISHING_ROD))
				buy(FLY_FISHING_ROD, 1, 3, 1);
			if (Vars.get().equipment.contains(FEATHER))
				buy(FEATHER, 600, 8, 10);
			buy(LOBSTER_POT, 1, 5, 1); 
		}
	
	private void buy(int id, int reqAmount, int child, int buyAmount) {
		if (Inventory.getCount(id) >= reqAmount)
			return;
		RSInterfaceComponent tool = Interfaces.get(300, 2).getChild(child);
		if (tool != null)
			if (tool.getComponentStack() > 0)
				tool.click("Buy " + buyAmount);
	}

}