package scripts.F2PProgressiveLobsters;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.api2007.util.ThreadSettings;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.F2PProgressiveLobsters.concurrency.SlowThread;
import scripts.F2PProgressiveLobsters.data.Vars;
import scripts.F2PProgressiveLobsters.framework.AbstractTask;
import scripts.F2PProgressiveLobsters.paint.Paint;
import scripts.F2PProgressiveLobsters.tasks.disposal.Bank;
import scripts.F2PProgressiveLobsters.tasks.disposal.Drop;
import scripts.F2PProgressiveLobsters.tasks.gathering.Fish;
import scripts.F2PProgressiveLobsters.tasks.gathering.PickUp;
import scripts.F2PProgressiveLobsters.tasks.initial.GetGold;
import scripts.F2PProgressiveLobsters.tasks.initial.GetTools;
import scripts.F2PProgressiveLobsters.utility.DataUtil;
import scripts.F2PProgressiveLobsters.utility.LevelUtil;
import scripts.F2PProgressiveLobsters.utility.ScriptUtil;

/**_________________________________________________________________________*/
                              @ScriptManifest                                (
		authors = "Einstein",
		category = "Fishing",
		name = "F2P Progressive Lobsters",
		gameMode = 1,
		version = 3,
		description = "Accepts gold, buys equipment, fishes progressively"   )
/**_________________________________________________________________________*/
        
public class F2PLobsters extends Script implements Starting, Ending, Painting, MessageListening07 {
	 
	@Override
	public void onStart() {
		ThreadSettings.get().setClickingAPIUseDynamic(true);		
	}
	
	@Override
	public void run() {
		
		// Wait to get in game; for correct variable initialization
		while(!Login.getLoginState().equals(Login.STATE.INGAME))
			General.sleep(300);
		
		 // Initializing variables based on current fishing level
		LevelUtil.initializeVariablesBasedOnLevel();
		
		// Initializing variables based on available items
		Vars.get().hasGold = ScriptUtil.hasGold();
		Vars.get().hasTools = ScriptUtil.hasTools();
	    Vars.get().shouldDisplayGoldWarning = !Vars.get().hasGold && !Vars.get().hasTools;
	    
		// Initialize the Task List
		List<AbstractTask> taskList = new ArrayList<AbstractTask>();
		Collections.addAll(taskList, new Bank(), new Drop(), new Fish(), new GetGold(), new GetTools(), new PickUp());
		
		// Fire up the "slow thread"
		new SlowThread(this).start();
		
		// Main loop
		while (true)
			for (AbstractTask task : taskList) {
				General.sleep(300);
				if (task.shouldExecute()) {
					Vars.get().info = task.info();
					task.execute();
				}
			}
	}
	
	@Override
	public void onEnd() {
        General.println("                                                              ");
		General.println("______________________________________________________________");
		General.println("Thank you for running Einstein's F2P Progressive Lobsters!    ");
		General.println("        Total running time: " + Vars.get().runningTime         );
		General.println("        Total profit: "       + Vars.get().profit              );
		General.println("______________________________________________________________");
	}

	@Override
	public void onPaint(Graphics g) {
		Paint.get().paint(g);
	}

	@Override
	public void serverMessageReceived(String message) {
		DataUtil.interpretServerMessage(message);
	}

	@Override
	public void tradeRequestReceived(String name) {
		ScriptUtil.decideTradeReaction(name);
	}

	// Not in use
	public void clanMessageReceived(String arg0, String arg1) {}
	public void duelRequestReceived(String arg0, String arg1) {}
	public void personalMessageReceived(String arg0, String arg1) {}
	public void playerMessageReceived(String arg0, String arg1) {}

}