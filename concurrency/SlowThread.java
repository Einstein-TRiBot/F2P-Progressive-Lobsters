package scripts.F2PProgressiveLobsters.concurrency;

import static scripts.F2PProgressiveLobsters.data.Constants.FLY_FISHING_AREA;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Player;
import org.tribot.script.Script;

import scripts.F2PProgressiveLobsters.data.Vars;
import scripts.F2PProgressiveLobsters.utility.Failsafes;

/**
 * Updates certain variables and selectively performs fail-safes
 * at as slower rate,
 * in an attempt to reduce the number of computations per second.
 * 
 * @author Einstein
 * 
 */

public class SlowThread extends Thread {

	Script script;

	public SlowThread(Script script) {
		this.script = script;
	}

	@Override
	public void run() {
		while (true) {
			General.sleep(1000);

			// Update time
			Vars.get().runningTime = Timing.msToString(script.getRunningTime());
			
			// Lumbridge combat problem
			if (FLY_FISHING_AREA.contains(Player.getPosition()))
				Failsafes.combat();

			// If bot is fishing/moving it means it's doing fine
			if (Player.getAnimation() != -1 || Player.isMoving())
				continue;
			
			Failsafes.report();
			Failsafes.ship();
		}
	}
	
}