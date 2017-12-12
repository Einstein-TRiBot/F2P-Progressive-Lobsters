package scripts.F2PProgressiveLobsters.data;

import java.awt.Image;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.F2PProgressiveLobsters.utility.DataUtil;

public interface Constants {
	
	// Lobster Info
	int LOBSTER_ID = 377;
	int LOBSTER_PRICE = DataUtil.getPrice(LOBSTER_ID);

	// Equipment
	int SMALL_FISHING_NET = 303;
	int FLY_FISHING_ROD = 309;
	int FEATHER = 314;
	int LOBSTER_POT = 301;
	int COINS = 995;

	// Fishing actions
	String NET_ACTION = "Net";
	String LURE_ACTION = "Lure";
	String CAGE_ACTION = "Cage";

	// Fishing spots
	int NET_FISHING_SPOT_ID = 1521;
	int FLY_FISHING_SPOT_ID = 1527;
	int CAGE_FISHING_SPOT_ID = 1522;

	// Areas
	RSArea FLY_FISHING_AREA = new RSArea(new RSTile(3239, 3256, 0), new RSTile(3242, 3240, 0));
	RSArea NET_CAGE_FISHING_AREA = new RSArea(new RSTile(2920, 3180, 0), new RSTile(2927, 3174, 0));
	RSArea DEPOSIT_BOX_AREA = new RSArea(new RSTile(3046, 3235, 0), new RSTile(3044, 3236, 0));
	RSArea SHOP_AREA = new RSArea(new RSTile[] { new RSTile(3018, 3230, 0), new RSTile(3018, 3223, 0), new RSTile(3014, 3219, 0), new RSTile(3011, 3220, 0), new RSTile(3011, 3230, 0) });
	RSArea COMBAT_SAFESPOT = new RSArea(new RSTile(3263, 3218, 0), 1);
	
	// Images
	Image BACKGROUND_PAINT = DataUtil.getImage("http://i.imgur.com/ImFVAvy.png");
	Image NET_FISHING = DataUtil.getImage("http://i.imgur.com/RdmJXDH.png");
	Image FLY_FISHING = DataUtil.getImage("http://i.imgur.com/Zf30Fi8.png");
	Image CAGE_FISHING = DataUtil.getImage("http://i.imgur.com/qZ2KSDv.png");
	
}