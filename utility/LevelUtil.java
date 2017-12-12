package scripts.F2PProgressiveLobsters.utility;

import static scripts.F2PProgressiveLobsters.data.Constants.*;
import java.util.Collections;
import org.tribot.api2007.Skills;
import scripts.F2PProgressiveLobsters.data.Vars;

/**
 * 
 * @author Einstein
 *
 *
 */
public class LevelUtil {

	// This method can't get any uglier, but it gets the job done.
	// Fortunately, it's only called once, upon starting the script.
	public static void initializeVariablesBasedOnLevel() {

		int level = Skills.getActualLevel(Skills.SKILLS.FISHING);
		Collections.addAll(Vars.get().equipment, SMALL_FISHING_NET, FLY_FISHING_ROD, FEATHER, LOBSTER_POT, COINS);

		if (level < 20) {
			Vars.get().dropFish = true;
			Vars.get().fishingArea = NET_CAGE_FISHING_AREA;
			Vars.get().fishingSpot = NET_FISHING_SPOT_ID;
			Vars.get().fishingAction = NET_ACTION;
			Vars.get().fishingImage = NET_FISHING;
			return;
		}
		if (level < 40) {
			Vars.get().dropFish = true;
			Vars.get().fishingArea = FLY_FISHING_AREA;
			Vars.get().fishingSpot = FLY_FISHING_SPOT_ID;
			Vars.get().fishingAction = LURE_ACTION;
			Vars.get().fishingImage = FLY_FISHING;
			if (Vars.get().equipment.contains(SMALL_FISHING_NET))
				Vars.get().equipment.remove((Integer) SMALL_FISHING_NET);
			return;
		}
		Vars.get().dropFish = false;
		Vars.get().fishingArea = NET_CAGE_FISHING_AREA;
		Vars.get().fishingSpot = CAGE_FISHING_SPOT_ID;
		Vars.get().fishingAction = CAGE_ACTION;
		Vars.get().fishingImage = CAGE_FISHING;
		if (Vars.get().equipment.contains(SMALL_FISHING_NET))
			Vars.get().equipment.remove((Integer) SMALL_FISHING_NET);
		if (Vars.get().equipment.contains(FEATHER))
			Vars.get().equipment.remove((Integer) FEATHER);
		if (Vars.get().equipment.contains(FLY_FISHING_ROD))
			Vars.get().equipment.remove((Integer) FLY_FISHING_ROD);
	}

	// Slightly better method. It's called upon leveling up.
	public static void update() {

		// Get level
		int level = Skills.getActualLevel(Skills.SKILLS.FISHING);

		// Switch to fly fishing
		if (level == 20) {
			Vars.get().dropFish = true;
			Vars.get().fishingArea = FLY_FISHING_AREA;
			Vars.get().fishingSpot = FLY_FISHING_SPOT_ID;
			Vars.get().fishingAction = LURE_ACTION;
			Vars.get().fishingImage = FLY_FISHING;
			if (Vars.get().equipment.contains(SMALL_FISHING_NET))
				Vars.get().equipment.remove((Integer) SMALL_FISHING_NET);
			return;
		}
		// Switch to lobsters
		if (level == 40) {
			Vars.get().dropFish = false;
			Vars.get().fishingArea = NET_CAGE_FISHING_AREA;
			Vars.get().fishingSpot = CAGE_FISHING_SPOT_ID;
			Vars.get().fishingAction = CAGE_ACTION;
			Vars.get().fishingImage = CAGE_FISHING;
			if (Vars.get().equipment.contains(FEATHER))
				Vars.get().equipment.remove((Integer) FEATHER);
			if (Vars.get().equipment.contains(FLY_FISHING_ROD))
				Vars.get().equipment.remove((Integer) FLY_FISHING_ROD);
			return;
		}
	}

}