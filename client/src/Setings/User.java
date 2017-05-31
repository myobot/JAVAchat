package Setings;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedList;

/**
 * Created by wzw on 2016/12/15.
 */
public class User implements Serializable {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String passwd;

    /**
     * 问题
     */
    private String question;

    /**
     * 问题答案
     */
    private String answer;

    /**
     * 新密码
     */
    private String newpasswd;

    /**
     * 用户IP
     */
    private String IP;

    /**
     * 是否有消息
     */
    private boolean messagestatus;

    /**
     * 消息
     */
    private String message;

    /**
     * 用户在线的状态  false 不在线  true 在线
     */
    private boolean status=false;

    /**
     * 有无好友
     */
    private boolean friendstatus=false;

    /**
     * 好友列表
     */
    private LinkedList<User_friends> friendlist=null;

    /**
     * 时间
     */
    private Date date;
    private String d;

    public User(){

    }

    public User(String username, String IP, boolean status){
        this.username=username;
        this.IP=IP;
        this.status=status;
    }

    public String getUsername(){
        return this.username;
    }
    public String getPasswd(){
        return this.passwd;
    }
    public String getQuestion(){
        return this.question;
    }
    public String getAnswer(){
        return this.answer;
    }
    public String getNewpasswd(){
        return this.newpasswd;
    }
    public String getIP(){
        return this.IP;
    }
    //    public String getAddfriendname(){
//        return this.addfriendname;
//    }
//    public String getDelfriendname(){
//        return this.delfriendname;
//    }
//    public String getChatfriendname(){
//        return this.chatfriendname;
//    }
    public boolean getMessageStatus(){
        return this.messagestatus;
    }
    public boolean getStatus(){
        return this.status;
    }
    public String getMessage(){
        return this.message;
    }
    public boolean getFriendstatus(){
        return this.friendstatus;
    }
    public LinkedList<User_friends> getFriendlist(){
        return this.friendlist;
    }
    public Date getDate(){
        return this.date;
    }
    public String getStringDate(){
        return this.d;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setPasswd(String passwd){
        this.passwd=passwd;
    }
    public void setQuestion(String question){
        this.question=question;
    }
    public void setAnswer(String answer){
        this.answer=answer;
    }
    public void setNewpasswd(String newpasswd){
        this.newpasswd=newpasswd;
    }
    public void setIP(String IP){
        this.IP=IP;
    }
    //    public void setAddfriendname(String addfriendname){
//        this.addfriendname=addfriendname;
//    }
//    public void setDelfriendname(String delfriendname){
//        this.delfriendname=delfriendname;
//    }
//    public void setChatfriendname(String chatfriendname){
//        this.chatfriendname=chatfriendname;
//    }
    public void setMessagestatus(boolean messagestatus){
        this.messagestatus=messagestatus;
    }
    public void setMessage(String message){
        this.message=message;
    }
    public void setStatus(boolean status){
        this.status=status;
    }
    public void setFriendstatus(boolean friendstatus){
        this.friendstatus=friendstatus;
    }
    public void setFriendlist(LinkedList<User_friends> userlist){
        this.friendlist=userlist;
    }
    public void setDate(Date date){
        this.date=date;
    }
    public void setStringDate(String d){
        this.d=d;
    }
}
