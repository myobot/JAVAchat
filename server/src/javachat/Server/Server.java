package javachat.Server;

import Setings.*;
import sun.awt.image.ImageWatched;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wzw on 2016/12/17.
 */
public class Server {
    private ServerSocket serverSocket;
    private Socket socket=new Socket();
    static Server server=null;
    private Work work=null;
    private LinkedList<OnlineUserInfo> onlineuserlist=null;
    private boolean flag=true;
    Server(){

    }
    public static InetAddress getLocalHost() throws UnknownHostException {
        InetAddress IP = InetAddress.getLocalHost();
        return IP;
    }
    public void start(){
        try{
            this.serverSocket = new ServerSocket(8888);
            System.out.print(getTime()+"服务器已开启\n服务器IP"+getLocalHost().getHostAddress()+"\n");
            this.socket.close();
        }catch (IOException var2) {
            JOptionPane.showMessageDialog((Component)null, "此端口被占用,系统将退出");
            System.exit(0);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
    public void startThread(){
        while(true){
            try{
                this.socket=this.serverSocket.accept();
                this.work=new Work(this,socket);
                this.work.start();
                System.out.println("用户连接,IP: "+this.socket.getInetAddress());
            }catch (IOException var2) {
                var2.printStackTrace();
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }
    }
    public synchronized boolean findUser(String username){
        boolean f=true;
        try {
            if (!flag) {
                this.wait();
            }
            flag = false;
            Connection c = LoadandConnectSQL.getConn();
            String sql = "Select * From users Where username=?";

            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (!resultSet.next()) {
                f = false;
            }
        }catch (InterruptedException e1){
            e1.printStackTrace();
            f=false;
        }catch(Exception e2){
            e2.printStackTrace();
            f=false;
        } finally {
            flag=true;
            notifyAll();
            return f;
        }

    }
    public synchronized User getUser(String username) {

        try {
            if (!flag) {
                this.wait();
            }
            this.flag = false;
            Connection c = LoadandConnectSQL.getConn();
            String sql = "Select username,passwd From users Where username=?";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString(1));
                user.setPasswd(resultSet.getString(2));
                String sql1 = "Select `f_id`,`friend_id` from `friends` Where `my_id`=?";
                PreparedStatement pstmt1 = c.prepareStatement(sql1);
                pstmt1.setString(1, username);
                ResultSet resultSet1 = pstmt1.executeQuery();
                                LinkedList<User_friends> userFriendsLinkedList = new LinkedList<>();
                while (resultSet1.next()) {
                    User_friends user_friends = new User_friends();
                    user_friends.setFriendId(resultSet1.getString(2));
                    if (isOnline(user_friends.getFriendId())) {
                        user_friends.setStatus(true);
                    }
                    String sql3 = "Select `content`,`time`,`status` From `chathistoy` Where `f_id`=?";
                    PreparedStatement pstmt3 = c.prepareStatement(sql3);
                    pstmt3.setInt(1, resultSet1.getInt(1));
                    ResultSet resultSet3 = pstmt3.executeQuery();
                    LinkedList<MessageList> messageLists = new LinkedList<>();
                    LinkedList<MessageList> unreadmessageslist=new LinkedList<>();
                    while (resultSet3.next()) {
                        MessageList messageList = new MessageList();
                        messageList.setMessage(resultSet3.getString(1));
                        messageList.setDate(resultSet3.getString(2));
                        messageList.setSender(username);
                        if (resultSet3.getInt(3) == 0) {
                            messageList.setStatus(false);
                            unreadmessageslist.add(messageList);
                        } else {
                            messageList.setStatus(true);
                        }
                        messageLists.add(messageList);
                    }

                    String sql5="SELECT `f_id` FROM friends WHERE `my_id`=? and `friend_id`=?";
                    PreparedStatement pstmt5=c.prepareStatement(sql5);
                    pstmt5.setString(1,resultSet1.getString(2));
                    pstmt5.setString(2,username);
                    ResultSet resultSet4=pstmt5.executeQuery();
                    if(resultSet4.next()) {
                        pstmt3.setInt(1, resultSet4.getInt(1));
                        ResultSet resultSet5 = pstmt3.executeQuery();
                        while (resultSet5.next()) {
                            MessageList messageList = new MessageList();
                            messageList.setMessage(resultSet5.getString(1));
                            messageList.setDate(resultSet5.getString(2));
                            messageList.setSender(resultSet1.getString(2));
                            if (resultSet5.getInt(3) == 0) {
                                messageList.setStatus(false);
                                unreadmessageslist.add(messageList);
                            } else {
                                messageList.setStatus(true);
                            }
                            messageLists.add(messageList);
                        }
                    }
                    Collections.sort (messageLists);

                    if (messageLists.size()>=1) {
                        user_friends.setMessagestatus(true);
                        user_friends.setMessage(messageLists);
                    } else {
                        user_friends.setMessagestatus(false);
                        user_friends.setMessage(null);
                    }
                    if(unreadmessageslist.size()>=1){
                        user_friends.setUnreadmessagestatus(true);
                        user_friends.setUnreadmessagelist(unreadmessageslist);
                    }else{
                        user_friends.setUnreadmessagestatus(false);
                        user_friends.setUnreadmessagelist(null);
                    }
                    userFriendsLinkedList.add(user_friends);
                }
                if (isOnline(username)) {
                    user.setStatus(true);
                } else {
                    user.setStatus(false);
                }
                if (userFriendsLinkedList.size() >= 1) {
                    user.setFriendstatus(true);
                    user.setFriendlist(userFriendsLinkedList);
                } else {
                    user.setFriendstatus(false);
                    user.setFriendlist(null);
                }
                this.flag = true;
                notifyAll();
                return user;
            } else {
                this.flag = true;
                notifyAll();
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (InterruptedException e2) {
            e2.printStackTrace();

        }
        this.flag = true;
        notifyAll();
        return null;
    }
    public synchronized void updateStatus(String username,String friendname){
        try {
            if (!flag) {
                this.wait();
            }
            this.flag = false;
            Connection c=LoadandConnectSQL.getConn();
            String sql = "Select `f_id` From friends Where `my_id`=? and `friend_id`=?";
            PreparedStatement pstmt1 =c.prepareStatement(sql);
            pstmt1.setString(1,friendname);
            pstmt1.setString(2,username);
            ResultSet resultSet=pstmt1.executeQuery();
            if(resultSet.next()) {
                String sql1 = "Update chathistoy Set status=1 Where f_id=?";
                PreparedStatement pstmt2 = c.prepareStatement(sql1);
                pstmt2.setInt(1, resultSet.getInt(1));
                pstmt2.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        this.flag = true;
        notifyAll();
    }
    public boolean isOnline(String username){
        if (this.onlineuserlist == null) {
            return false;
        }
        for (int i = 0; i < this.onlineuserlist.size(); i++) {
            if (this.onlineuserlist.get(i).getUser().getUsername().equals(username)) {


                return true;
            }
        }

        return false;
    }
    public boolean checkUserFriend(User user,String friendname){
        if (!user.getFriendstatus()) {
            return false;
        } else {
            LinkedList<User_friends> friendslist = user.getFriendlist();
            for (int i = 0; i < friendslist.size(); i++) {
                if (friendname.equals(friendslist.get(i).getFriendId())) {

                    return true;
                }
            }
        }
        return false;
    }
    public synchronized LinkedList<String> getFriendList(String username){
        LinkedList<String> friendlist=new LinkedList<>();
        try {
            if (!flag) {
                this.wait();
            }
            this.flag = false;
            Connection c=LoadandConnectSQL.getConn();
            String sql="SELECT friend_id FROM friends WHERE my_id=?";
            PreparedStatement pstmt=c.prepareStatement(sql);
            pstmt.setString(1,username);
            ResultSet resultSet=pstmt.executeQuery();
            while(resultSet.next()){
                friendlist.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (InterruptedException e2) {
            e2.printStackTrace();

        } finally {
            this.flag = true;
            notifyAll();
            return friendlist;
        }
    }
    public void sendToFriends(String username,SendBody sendBody){
        LinkedList<String> frinedlist=getFriendList(username);
        for(int i=0;i<frinedlist.size();i++){
            sendtoUser(frinedlist.get(i),sendBody);
        }
    }
    public synchronized boolean addFriend(String username,String friendname){
        boolean f=true;
        try{
            if(!flag){
                this.wait();
            }
            this.flag=false;
            Connection c=LoadandConnectSQL.getConn();
            String sql="Insert into friends (my_id,friend_id) Values(?,?)";
            PreparedStatement pstmt=c.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,friendname);
            pstmt.addBatch();
            pstmt.setString(1,friendname);
            pstmt.setString(2,username);
            pstmt.addBatch();
            pstmt.executeBatch();
        }catch (SQLException e){
            e.printStackTrace();
            f=false;
        }catch(InterruptedException e2){
            e2.printStackTrace();
            f=false;
        }finally {
            this.flag=true;
            notifyAll();
            return f;
        }

    }
    public synchronized boolean inserthistory(String username,String friendname,String date,String message,int status){
        boolean f=true;
        try{
            if(!flag) {
                this.wait();
            }
                this.flag=false;
                Connection c=LoadandConnectSQL.getConn();
                String sql="Select f_id from friends Where my_id=? and friend_id=?";
                PreparedStatement pstmt=c.prepareStatement(sql);
                pstmt.setString(1,username);
                pstmt.setString(2,friendname);
                ResultSet resultSet=pstmt.executeQuery();
                if(resultSet.next()){
                    String sql1="Insert into chathistoy(`f_id`,`content`,`time`,`status`) Values(?,?,?,?)";
                    PreparedStatement pstmt1=c.prepareStatement(sql1);
                    pstmt1.setInt(1,resultSet.getInt(1));
                    pstmt1.setString(2,message);
                    pstmt1.setString(3, date);
                    pstmt1.setInt(4,status);
                    pstmt1.executeUpdate();
                }else{
                    f=false;
                }

        }catch (SQLException e){
            e.printStackTrace();
            f=false;
        }catch (InterruptedException e1){
            e1.printStackTrace();
            f=false;
        }finally {
            this.flag=true;
            notifyAll();
            return f;
        }

    }
    public synchronized boolean addnewuser(User user){
        boolean f=true;
        try{
            if(!flag){
                this.wait();
            }
            this.flag=false;
            Connection c=LoadandConnectSQL.getConn();
            String sql="Insert into users(username,passwd,question,answer) Values(?,?,?,?)";
            PreparedStatement pstmt=c.prepareStatement(sql);
            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPasswd());
            pstmt.setString(3,user.getQuestion());
            pstmt.setString(4,user.getAnswer());
            pstmt.executeUpdate();

        }catch (InterruptedException e){
            e.printStackTrace();
            f=false;
        }catch (SQLException e1){
            e1.printStackTrace();
            f=false;
        }finally {
            this.flag=true;
            notifyAll();
            return f;
        }


    }
    public synchronized boolean changePasswd(User user){
        boolean f=true;
        try{
            if(!flag) {
                this.wait();
            }
            this.flag=false;
            Connection c=LoadandConnectSQL.getConn();
            String sql="Select answer From users Where username=?";
            PreparedStatement pstmt=c.prepareStatement(sql);
            pstmt.setString(1,user.getUsername());
            ResultSet resultSet=pstmt.executeQuery();
            if(resultSet.next()){
                if(user.getAnswer().equals(resultSet.getString(1))){
                    String sql1="Update users Set passwd=? Where username=?";
                    PreparedStatement pstmt1=c.prepareStatement(sql1);
                    pstmt1.setString(1,user.getNewpasswd());
                    pstmt1.setString(2,user.getUsername());
                    pstmt1.executeUpdate();
                }else{
                    f=false;
                }
            }else{
                f=false;
            }
        }catch(InterruptedException e){
            e.printStackTrace();
            f=false;
        }catch(SQLException e1){
            e1.printStackTrace();
            f=false;
        }finally {
            this.flag=true;
            notifyAll();
            return f;
        }

    }
    public OnlineUserInfo getOnlineUserinfo(String username){
        for(int i=0;i<this.onlineuserlist.size();i++){
            if(onlineuserlist.get(i).getUser().getUsername().equals(username)){
                return onlineuserlist.get(i);
            }
        }
        return null;
    }
    public synchronized boolean sendtoUser(String username, SendBody sendBody){
        try{
            if(!flag){
                this.wait();
            }
            this.flag=false;
            OnlineUserInfo onlineUserInfo=getOnlineUserinfo(username);
            if(onlineUserInfo==null){
                this.flag=true;
                notifyAll();
                return false;
            }else{
                onlineUserInfo.getWorking().send(sendBody);
                this.flag=true;
                notifyAll();
                return true;
            }
        }catch (InterruptedException e){
            this.flag=true;
            notifyAll();
            e.printStackTrace();
            return false;
        }
    }
    public synchronized boolean delfirend(String username,String friendname){
        boolean f=true;
        try{
            if(!this.flag){
                this.wait();
            }
            this.flag=false;
            Connection c=LoadandConnectSQL.getConn();
            String sql="Delete From friends Where my_id=? and friend_id=?";
            PreparedStatement pstmt=c.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,friendname);
            pstmt.addBatch();
            pstmt.setString(1,friendname);
            pstmt.setString(2,username);
            pstmt.addBatch();
            pstmt.executeBatch();
        }catch(InterruptedException e){
            e.printStackTrace();
            f=false;
        }catch(SQLException e1){
            e1.printStackTrace();
            f=false;
        }finally {
            this.flag=true;
            notifyAll();
            return f;
        }

    }
    public synchronized boolean logout(String username){
        boolean f=true;
        try{
            if(!flag){
                this.wait();
            }
            this.flag=false;
            int num=0;
            for(int i=0;i<this.onlineuserlist.size();i++){
                if(onlineuserlist.get(i).getUser().getUsername().equals(username)){
                    num=i;
                    break;
                }
            }
            onlineuserlist.remove(num);
        }catch (Exception e){
            f=false;
        }
        this.flag=true;
        notifyAll();
        return f;
    }

    public synchronized boolean logout(Work work){
        boolean f=true;
        try{
            if(!flag){
                this.wait();
            }
            this.flag=false;
            int num=0;
            for(int i=0;i<this.onlineuserlist.size();i++){
                if(onlineuserlist.get(i).getWorking()==work){
                    num=i;
                    break;
                }
            }
            onlineuserlist.remove(num);
        }catch (Exception e){
            f=false;
        }
        this.flag=true;
        notifyAll();
        return f;
    }

    public synchronized void saveOnlineUser(User user){
        try {
            if (!flag) {
                this.wait();
            }
            this.flag = false;
            OnlineUserInfo onlineUserInfo = new OnlineUserInfo();
            onlineUserInfo.setWorking(this.work);
            onlineUserInfo.setUser(user);
            if (this.onlineuserlist == null) {
                this.onlineuserlist = new LinkedList<>();
            }
            this.onlineuserlist.add(onlineUserInfo);
            this.flag = true;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static String getTime() {
        Calendar calendar = GregorianCalendar.getInstance(Locale.getDefault());
        Date date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static void main(String args[]){
        server=new Server();
        server.start();
        server.startThread();
    }


}
