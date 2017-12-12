package scripts.F2PProgressiveLobsters.tasks.gathering;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
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
public class Fish implements AbstractTask {

	@Override
	public String info() {
		return "Fishing";
	}

	@Override
	public boolean shouldExecute() {
		return Vars.get().hasTools && !Inventory.isFull() && !ScriptUtil.lobstersFound();
	}

	@Override
	public void execute() {
		if (walkToFishingArea())
			if (findAndClickFishingSpot())
				waitToAnimate();
	}

	private boolean walkToFishingArea() {
		if (!Vars.get().fishingArea.contains(Player.getPosition()))
			WebWalking.walkTo(Vars.get().fishingArea.getRandomTile());
		return Vars.get().fishingArea.contains(Player.getPosition());
	}

	private boolean findAndClickFishingSpot() {
		// If player is busy fishing/walking: return false
		if (Player.getAnimation() != -1 || Player.isMoving())
			return false;

		// If no fishing spot was found: return false
		RSNPC[] fishingSpot = NPCs.findNearest(Vars.get().fishingSpot);
		if (fishingSpot.length == 0)
			return false;

		// If fishing spot is not on screen: move closer & move camera
		if (!fishingSpot[0].isOnScreen()) {
			Walking.blindWalkTo(fishingSpot[0].getPosition());
			Camera.turnToTile(fishingSpot[0]);
		}

		// If successfully clicked the fishing spot: return true
		if (fishingSpot[0].click(Vars.get().fishingAction + " Fishing spot"))
			return true;

		return false;
	}

	private void waitToAnimate() {
		// Wait until the player stops walking
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(300);
				return !Player.isMoving();
			}
		}, General.random(3000, 5000));

		// Wait until the player started animating
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(300);
				return Player.getAnimation() != -1;
			}
		}, General.random(2000, 4000));
	}

}