package scripts.F2PProgressiveLobsters.tasks.initial;

import static scripts.F2PProgressiveLobsters.data.Constants.*;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Players;
import org.tribot.api2007.Trading;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSPlayer;

import scripts.F2PProgressiveLobsters.data.Vars;
import scripts.F2PProgressiveLobsters.framework.AbstractTask;
import scripts.F2PProgressiveLobsters.utility.Failsafes;

/**
 * 
 * @author Einstein
 *
 *
 */
public class GetGold implements AbstractTask {

	@Override
	public String info() {
		return "Getting gold";
	}

	@Override
	public boolean shouldExecute() {
		return !Vars.get().hasGold;
	}

	@Override
	public void execute() {
		// Task specific fail-safe
		Failsafes.newPlayerInterface();

		// Update inventory
		if (Vars.get().hasTools || Inventory.getCount(COINS) >= 5000) {
			Vars.get().hasGold = true;
			Vars.get().shouldDisplayGoldWarning = false;
		}

		// Accept the gold
		if (Trading.getWindowState() != null)
			if (Trading.getCount(true, 995) > 0)
				if (!Trading.hasAccepted(false))
					Trading.accept();
	}

	// Note: This method is powered by a concurrent thread.
	//       It doesn't matter, as the main thread is guaranteed to be idle at this point.
	public static void getInTrade(String name) {
		// If no player was found: return
		final RSPlayer[] playerToTrade = Players.findNearest(Filters.Players.nameEquals(name));
		if (playerToTrade.length == 0)
			return;

		// Wait for other player to stop moving. Avoids missclicking.
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(300);
				return (!playerToTrade[0].isMoving());
			}
		}, General.random(3000, 5000));

		// Accept request and wait to get in trade
		if (playerToTrade[0].click("Trade with " + playerToTrade[0].getName())) {
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(300);
					return (Trading.getWindowState() != null);
				}
			}, General.random(3000, 5000));
		}
	}

}