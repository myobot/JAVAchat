package Client;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Friend {
	private String Id;	//friend id
    private boolean stat;	//is Alive	
    private boolean messageStat;
    private String message;
	
	public Friend(String id,boolean stat,boolean messageStat){
		this.setId(id);
		this.setStat(stat);
		this.setMessageStat(messageStat);
		this.message = null;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public boolean isStat() {
		return stat;
	}

	public void setStat(boolean stat) {
		this.stat = stat;
	}

	public boolean isMessageStat() {
		return messageStat;
	}

	public void setMessageStat(boolean messageStat) {
		this.messageStat = messageStat;
	}
	public void setMessage(String message){
		this.message = message;
	}
	public String getMessage(){
		return this.message;
	}
	
}
