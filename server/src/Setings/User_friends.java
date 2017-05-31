package Setings;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by wzw on 2016/12/16.
 */
public class User_friends implements Serializable {
    /**
     * 朋友id
     */
    private String friendId;

    /**
     * 是否在线
     */

    private boolean status;

    /**
     * 是否有留言
     */
    private boolean messagestatus=false;

    private boolean unreadmessagestatus=false;
    private LinkedList<MessageList> unreadmessagelist;

    /**
     * 留言
     */

    private LinkedList<MessageList> messagelist;

    public User_friends(){

    }

    public void setFriendId(String friendId){
        this.friendId=friendId;
    }
    public void setStatus(boolean status){
        this.status=status;
    }
    public void setMessagestatus(boolean messagestatus){
        this.messagestatus=messagestatus;
    }

    public void setMessage(LinkedList messagelist){
        this.messagelist=messagelist;
    }
    public void setUnreadmessagestatus(boolean unreadmessagestatus){
        this.unreadmessagestatus=unreadmessagestatus;
    }
    public void setUnreadmessagelist(LinkedList<MessageList> unreadmessagelist){
        this.unreadmessagelist=unreadmessagelist;
    }
    public String getFriendId(){
        return this.friendId;
    }
    public boolean getStatus(){
        return this.status;
    }
    public boolean getMessageStatus(){
        return this.messagestatus;
    }
    public LinkedList<MessageList> getMessage(){
        return this.messagelist;
    }
    public boolean getunreadmessagestatus(){
        return this.unreadmessagestatus;
    }
    public LinkedList<MessageList> getUnreadmessagelist(){
        return this.unreadmessagelist;
    }
}
