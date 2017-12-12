package scripts.F2PProgressiveLobsters.tasks.disposal;

import static scripts.F2PProgressiveLobsters.data.Constants.*;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;

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
public class Bank implements AbstractTask {

	@Override
	public String info() {
		return "Banking";
	}

	@Override
	public boolean shouldExecute() {
		return !Vars.get().dropFish && Inventory.isFull();
	}

	@Override
	public void execute() {
		if(walkToDepositBox())
			if(openDepositBox())
				depositLobsters();
	}
	
	private boolean walkToDepositBox() {
		if (!Constants.DEPOSIT_BOX_AREA.contains(Player.getPosition()))
			WebWalking.walkTo(Constants.DEPOSIT_BOX_AREA.getRandomTile()); 
		return Constants.DEPOSIT_BOX_AREA.contains(Player.getPosition());
	}

	private boolean openDepositBox() {
		if (Banking.isDepositBoxOpen())
			return true;
		RSObject[] depositBox = Objects.find(3, 26254);
		if (depositBox.length == 0)
			return false;
		if (Clicking.click("Deposit", depositBox[0]))
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(300);
					return (Banking.isDepositBoxOpen());
				}
			}, General.random(3000, 5000));
		return Banking.isDepositBoxOpen();
	}
	
	private void depositLobsters() {
		Banking.depositAllExcept(ScriptUtil.currentEquipmentAsArray());
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(300);
				return (Inventory.getCount(LOBSTER_ID) == 0);
			}
		}, General.random(2000, 4000));
	}
	
}