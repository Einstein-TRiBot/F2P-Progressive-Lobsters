package scripts.F2PProgressiveLobsters.tasks.gathering;

import static scripts.F2PProgressiveLobsters.data.Constants.LOBSTER_ID;
import static scripts.F2PProgressiveLobsters.data.Constants.NET_CAGE_FISHING_AREA;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGroundItem;

import scripts.F2PProgressiveLobsters.data.Constants;
import scripts.F2PProgressiveLobsters.data.Vars;
import scripts.F2PProgressiveLobsters.framework.AbstractTask;
import scripts.F2PProgressiveLobsters.utility.ScriptUtil;

/**
 * 
 * @author Einstein
 *
 *
 */
public class PickUp implements AbstractTask {

	@Override
	public String info() {
		return "Picking up lobsters";
	}

	@Override
	public boolean shouldExecute() {
		// Note: a proper anti-lure has been implemented
		return ScriptUtil.lobstersFound();
	}

	@Override
	public void execute() {
		if (findAndClickLobster())
			if (waitToGetLobster())
				updateVariables();
	}

	private boolean findAndClickLobster() {
		RSGroundItem[] lobster = GroundItems.findNearest(LOBSTER_ID);
		if (lobster.length == 0)
			return false;
		if (NET_CAGE_FISHING_AREA.contains(lobster[0].getPosition()))
			if (lobster[0].click("Take Raw lobster"))
				return true;
		return false;
	}

	private boolean waitToGetLobster() {
		// Count lobsters
		int i = Inventory.getCount(LOBSTER_ID);

		// Wait to stop moving
		Timing.waitCondition(new Condition() {
			public boolean active() {
				General.sleep(300);
				return !Player.isMoving();
			}
		}, General.random(3000, 5000));

		// Wait for lobster to appear in inventory
		Timing.waitCondition(new Condition() {
			public boolean active() {
				General.sleep(300);
				return Inventory.getCount(LOBSTER_ID) > i;
			}
		}, General.random(3000, 5000));

		return Inventory.getCount(LOBSTER_ID) > i;
	}

	private void updateVariables() {
		Vars.get().lobsters++;
		Vars.get().profit = Integer.toString((Vars.get().lobsters * Constants.LOBSTER_PRICE) / 1000) + "k";
	}

}