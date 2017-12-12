package scripts.F2PProgressiveLobsters.utility;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.tribot.api.General;

import scripts.F2PProgressiveLobsters.data.Constants;
import scripts.F2PProgressiveLobsters.data.Vars;
/**
 * 
 * @author Einstein
 *
 *
 */
public class DataUtil {

	/**
	 * Gets image from the web.
	 * 
	 * @param url of the image
	 * @return Image or null if failed.
	 */
	public static Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			General.println("Unable to load image!");
		}
		return null;
	}

	/**
	 * Gets the price of specified items. RSBuddy API.
	 * 
	 * @param id of item
	 * @return price of item
	 */
	public static int getPrice(int id) {
		try {
			URL url = new URL("http://api.rsbuddy.com/grandExchange?a=guidePrice&i=" + id);
			URLConnection con = url.openConnection();
			con.setUseCaches(true);
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String[] data = br.readLine().replace("{", "").replace("}", "").split(",");
			return Integer.parseInt(data[0].split(":")[1]);
		} catch (Exception e) {
			General.println("Unable to get lobsters price!");
		}
		return -1;
	}
	
	/**
	 * Decides what happened based on received string and updates variables accordingly.
	 * 
	 * @param string to interpret
	 */
	public static void interpretServerMessage(String s) {
		if (s.equals("You catch a lobster.")) {
			Vars.get().lobsters++;
			Vars.get().profit = Integer.toString((Vars.get().lobsters * Constants.LOBSTER_PRICE) / 1000) + "k";
		}
		if (s.contains("Congratulations, you've just advanced your Fishing level."))
			LevelUtil.update();
	}

}