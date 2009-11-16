package core.abstraction;

public interface Controller {
	
	public boolean sendMessage(String from, String message);
	public void removeDialog(String key);
}
