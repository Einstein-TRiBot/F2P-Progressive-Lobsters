package scripts.F2PProgressiveLobsters.framework;
/**
 * 
 * @author Einstein
 *
 *
 */
public interface AbstractTask {

	String info();

	boolean shouldExecute();

	void execute();

}