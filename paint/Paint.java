package scripts.F2PProgressiveLobsters.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.tribot.api2007.Login;

import static scripts.F2PProgressiveLobsters.data.Constants.BACKGROUND_PAINT;
import scripts.F2PProgressiveLobsters.data.Vars;
/**
 * 
 * @author Einstein
 *
 *
 */
public class Paint {
		  
	// Instance manipulation
	private Paint() {}
	private static final Paint PAINT = new Paint();
	public static Paint get() { return PAINT; }
	
	public void paint(Graphics g) {
		// Only paint if in game
		if (Login.getLoginState() != Login.STATE.INGAME)
			return;
		
		// Images
		g.drawImage(BACKGROUND_PAINT, 0, 337, null);
		g.drawImage(Vars.get().fishingImage, 570, 9, null);

		// "Needs gold" warning
		if (Vars.get().shouldDisplayGoldWarning) {
			g.setColor(Color.YELLOW);
			g.drawString("Needs at least 5k gold to start; 10k recommended.", 10, 330); 
			g.drawString("Auto-accept trades enabled.", 360, 330);
		}

		// Current task info
		g.setFont(new Font("Arial Black", Font.PLAIN, 25));
		g.setColor(Color.YELLOW);
		g.drawString(Vars.get().info, 230, 480);

		// Timer
		g.setFont(new Font("Arial Black", Font.PLAIN, 18));
		g.setColor(Color.WHITE);
		g.drawString(Vars.get().runningTime, 430, 360);

		// Lobster count & profit
		g.setFont(new Font("Arial Black", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString(": " + Vars.get().lobsters, 95, 400);
		g.drawString(": " + Vars.get().profit, 95, 480);	
	}
	
}