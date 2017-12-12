package scripts.F2PProgressiveLobsters.tasks.disposal;

import org.tribot.api2007.Inventory;

import scripts.F2PProgressiveLobsters.data.Vars;
import scripts.F2PProgressiveLobsters.framework.AbstractTask;
import scripts.F2PProgressiveLobsters.utility.ScriptUtil;
/**
 * 
 * @author Einstein
 *
 *
 */
public class Drop implements AbstractTask {

	@Override
	public String info() {
		return "Dropping";
	}

	@Override
	public boolean shouldExecute() {
		return Vars.get().dropFish && Inventory.isFull();
	}

	@Override
	public void execute() {
		Inventory.dropAllExcept(ScriptUtil.currentEquipmentAsArray());
	}

}