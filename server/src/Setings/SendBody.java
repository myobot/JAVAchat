package Setings;

import java.io.Serializable;

/**
 * Created by wzw on 2016/12/16.
 */
public class SendBody implements Serializable {
    private static final long serialVersionUID = 3674727123335529803L;
    private String order;
    private Object objMessage;      //User or HistoryChat类
    private String sourceID;
    private String destID;
    private String friendIP;

    public SendBody(){
    }
    public String getDestID() {
        return this.destID;
    }

    public void setDestID(String destID) {
        this.destID = destID;
    }
    public Object getObjMessage() {
        return this.objMessage;
    }

    public void setObjMessage(Object objMessage) {
        this.objMessage = objMessage;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSourceID() {
        return this.sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public void setfriendIP(String friendIP) {
        this.friendIP = friendIP;
    }

    public String getfriendIP() {
        return this.friendIP;
    }

}
