package scripts.F2PProgressiveLobsters.utility;

import static scripts.F2PProgressiveLobsters.data.Constants.*;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.ext.Ships;

/**
 * 
 * @author Einstein
 *
 *
 */
public class Failsafes {

	/**
	 * Only called in Lumbridge.
	 * 
	 * Stops cyber bullying
	 */
	public static void combat() {
		int animation = Player.getAnimation();
		// defending-unarmed || defending-shield || defending-sword
		if (animation == 424 || animation == 1156 || animation == 388)
			if (WebWalking.walkTo(COMBAT_SAFESPOT.getRandomTile()))
				General.println("Bot got cyberbullied. Safespot reached.");
	}

	/**
	 * Gets the player off the ship.
	 */
	public static void ship() {
		if (Ships.isOnShip())
			if (Ships.crossGangplank())
				General.println("Bot stuck on ship. Ship fail-safe was enabled.");
	}

	/**
	 * Closes the report interface.
	 */
	public static void report() {
		if (Interfaces.isInterfaceValid(553))
			if (Clicking.click(Interfaces.get(553, 1).getChild(11)))
				General.println("Bot got stuck on report interface. Report failsafe was enabled.");
	}

	/**
	 * Only called in get gold stage.
	 * 
	 * Closes the new player interface that prevents bot to receive trade requests.
	 */
	public static void newPlayerInterface() {
		if (Interfaces.isInterfaceValid(193))
			if (Clicking.click(Interfaces.get(193, 2)))
				General.println("New player interface blocked trading. Interface failsafe was enabled.");
	}

}