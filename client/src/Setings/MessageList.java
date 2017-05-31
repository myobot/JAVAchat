package Setings;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by wzw on 2016/12/22.
 */
public class MessageList implements Serializable,Comparable<MessageList> {
    String message=null;
    String date=null;
    String sender=null;
    boolean status;
    public MessageList(){}
    public void setMessage(String message){
        this.message=message;
    }
    public void setDate(String date){
        this.date=date;
    }
    public void setSender(String sender){this.sender=sender;}
    public void setStatus(boolean status){
        this.status=status;
    }
    public String getMessage(){
        return this.message;
    }
    public String getDate(){
        return this.date;
    }
    public String getSender(){return this.sender;}
    public boolean getStatus(){return this.status;}
    @Override
    public int compareTo(MessageList o) {
        if(date.compareTo(o.getDate())>0){
            return 1;
        }else if(date.compareTo(o.getDate())<0){
            return -1;
        }else {
            return 0;
        }
    }
}
