package Client;
import Setings.*;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by wzw on 2016/12/25.
 */
public class Client {
    private ObjectInputStream netIn;
    private ObjectOutputStream netOut;
    private static Socket socket;
    private boolean runflag=true;
    public static Client client;
    private static Login loginUI;
    private static MainWindow mainUI;
    private static User User;
    private JFrame registerframe=null;
    private JFrame loginframe=null;

    public Client(){
    }
    public void init(){

            try {
//                socket = new Socket("123.206.13.201", i);
                String e="";
                e = JOptionPane.showInputDialog("服务器IP", "123.206.13.201");
                socket = new Socket(e, 8888);
                loginUI = new Login();
                loginframe = loginUI.getFrame();
                this.netOut = new ObjectOutputStream(socket.getOutputStream());
                this.netIn = new ObjectInputStream(socket.getInputStream());
                receive();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("端口"+8888+"不可用");
            }

    }
    public void receive(){
        SendBody sendBody=new SendBody();
        while(runflag) {
            if (!socket.isClosed()) {
                try {
                    if ((sendBody = (SendBody) netIn.readObject()) != null) {
                        function(sendBody);
                    }
                } catch (IOException e1) {
                    System.out.println("网络接收异常" + e1.getMessage());
                    if(!socket.isClosed())
                        this.logout();
                } catch (ClassNotFoundException e2) {
                    System.out.println("找不到这个类" + e2.getMessage());
                    if(!socket.isClosed())
                        this.logout();
                } catch (Exception e) {
                    System.out.println("未知异常" + e.getMessage());
                    if(!socket.isClosed())
                        this.logout();
                }
            }
        }
    }
    public void send(SendBody sendBody) {
        try {
            if(!socket.isClosed()) {
                this.netOut.writeObject(sendBody);
                this.netOut.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("网络输出异常" + e.getMessage());
            this.logout();
        }
    }
    public void loginreceiveorder(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.loginsuccess)){
            this.User=(User)sendBody.getObjMessage();
            this.loginframe.dispose();
            try {
                mainUI = new MainWindow(createFriendlist(this.User), this.User);
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
        if(order.equals(Command.loginreapt)){
            JOptionPane.showMessageDialog(this.loginUI.getFrame(),"重复登录","警告",2);
        }
        if(order.equals(Command.loginfail)){
            JOptionPane.showMessageDialog(this.loginUI.getFrame(),"登录失败","警告",2);
        }
    }
    public ArrayList<Friend> createFriendlist(User user){
        ArrayList<Friend> friendlist=new ArrayList<>();
        if(user.getFriendstatus()) {
            for (int i = 0; i < user.getFriendlist().size(); i++) {
                Friend friend = new Friend(user.getFriendlist().get(i).getFriendId(), user.getFriendlist().get(i).getStatus(), user.getFriendlist().get(i).getunreadmessagestatus());
                if (user.getFriendlist().get(i).getunreadmessagestatus()) {
                    for (int j = 0; j < user.getFriendlist().get(i).getUnreadmessagelist().size(); j++) {
                        if(friend.getMessage()!=null) {
                            friend.setMessage(friend.getMessage() + user.getFriendlist().get(i).getUnreadmessagelist().get(j).getDate() + "\n" + user.getFriendlist().get(i).getFriendId() + ": " + user.getFriendlist().get(i).getUnreadmessagelist().get(j).getMessage() + "\n");
                        }else{
                            friend.setMessage(user.getFriendlist().get(i).getUnreadmessagelist().get(j).getDate() + "\n" + user.getFriendlist().get(i).getFriendId() + ": " + user.getFriendlist().get(i).getUnreadmessagelist().get(j).getMessage() + "\n");

                        }
                    }
                }
                friendlist.add(friend);
            }
        }
        return friendlist;
    }
    public void registreceiveordre(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.registersuccess)){
            JOptionPane.showMessageDialog(this.registerframe,"注册成功");
            if(this.registerframe!=null){
                this.registerframe.dispose();
            }

        }
        if(order.equals(Command.registerfail)){
            JOptionPane.showMessageDialog(this.registerframe,"注册失败");

        }
    }
    public void addfriend(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.addfriendrequestconfirm)){
            Setings.User user=(Setings.User)sendBody.getObjMessage();
            int i= JOptionPane.showConfirmDialog(this.mainUI,"用户："+sendBody.getSourceID()+" 请求添加好友，是否同意？","提示",JOptionPane.YES_NO_OPTION);
            if(i!=0){
                sendBody.setOrder(Command.addfriendrefuse);
                this.send(sendBody);
                this.addFriend(sendBody.getSourceID());
            }else{
                sendBody.setOrder(Command.addfriendagree);
                this.send(sendBody);
                Friend friend=new Friend(sendBody.getSourceID(),true,false);
                try {
                    this.mainUI.addFriend(friend);
                    this.addfriendupdate(sendBody.getSourceID());
                }catch (Exception exc){
                    exc.printStackTrace();
                }
            }
        }
        if(order.equals(Command.addfriendconfirmagree)){
            Setings.User user=(Setings.User)sendBody.getObjMessage();
            JOptionPane.showMessageDialog(this.mainUI,"用户："+sendBody.getDestID()+" 同意了添加好友请求");
            this.User=user;
            Friend friend=new Friend(sendBody.getDestID(),true,false);
            try {
                this.mainUI.addFriend(friend);
            }catch (Exception exc){
                exc.printStackTrace();
            }


        }
        if(order.equals(Command.addfriendconfirmrefuse)){
            JOptionPane.showMessageDialog(this.mainUI,"用户："+sendBody.getDestID()+" 拒绝了添加好友请求");
        }
    }
    public void delfriend(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.delfriendsuccess)){
            Setings.User user=(Setings.User)sendBody.getObjMessage();
            JOptionPane.showMessageDialog(this.mainUI,"删除用户："+sendBody.getDestID()+" 成功");
            this.User=user;
            this.mainUI.delFriend(sendBody.getDestID());

        }
        if(order.equals(Command.delfriendfail)){
            JOptionPane.showMessageDialog(this.mainUI,"删除用户："+sendBody.getDestID()+" 失败");
        }
        if(order.equals(Command.bedelete)){
            JOptionPane.showMessageDialog(this.mainUI,"被用户："+sendBody.getSourceID()+" 删除");
            this.mainUI.delFriend(sendBody.getSourceID());
            this.delfriendupdate(sendBody.getSourceID());
        }
    }
    public void personchat(SendBody sendBody) {
        String order = sendBody.getOrder();
        if (order.equals(Command.sendmessage)) {
            Setings.User user = (Setings.User) sendBody.getObjMessage();
            try {
                this.mainUI.updateMessageComing(sendBody.getSourceID());
                this.mainUI.updateMessage(sendBody.getSourceID(), user.getMessage(), user.getStringDate());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        if(order.equals(Command.personchatsuccess)){
            Setings.User user=(Setings.User) sendBody.getObjMessage();
            this.User=user;
        }
        if(order.equals(Command.personchatfail)){
            JOptionPane.showMessageDialog(this.mainUI,"聊天失败！");
        }
        if(order.equals(Command.presonchatisiffline)){
            JOptionPane.showMessageDialog(this.mainUI,"用户已离线");

        }

    }
    public void updatehistory(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.presonchatupdatehistoryreturn)){
            Setings.User user=(Setings.User)sendBody.getObjMessage();
            this.User=user;

            for(int i=0;i<user.getFriendlist().size();i++){
                if(user.getFriendlist().get(i).getFriendId().equals(sendBody.getDestID())){
                    this.mainUI.updateHistory(user.getFriendlist().get(i).getMessage(),sendBody.getDestID());

                }
            }
        }
    }
    public void friendOnAndOff(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.friendonline)){
            this.mainUI.updateFriend(sendBody.getSourceID(),true);
        }
        if(order.equals(Command.friendoutline)){
            this.mainUI.updateFriend(sendBody.getSourceID(),false);
        }
    }

    public void login(String username,String passwd){
        User user=new User();
        user.setUsername(username);
        user.setPasswd(passwd);
        SendBody sendBody=new SendBody();
        sendBody.setOrder(Command.login);
        sendBody.setObjMessage(user);
        this.send(sendBody);
    }
    public void logout(){
        SendBody sendBody=new SendBody();
        sendBody.setOrder(Command.logout);
        sendBody.setSourceID(User.getUsername());

        this.send(sendBody);
        try{
            this.runflag=false;
            netIn.close();// 关闭网络对象输入流
            netOut.close();// 关闭网络对象输出流
            socket.close();//
            System.exit(0);
        }catch (Exception eclose) {
            System.out.println("客服端退出异常:" + eclose.getMessage());
        }
    }
    public void regist(JFrame jFrame,String username,String passwd,String question,String answer){
        this.registerframe=jFrame;
        SendBody sendBody=new SendBody();
        sendBody.setOrder(Command.register);
        User user=new User();
        user.setUsername(username);
        user.setPasswd(passwd);
        user.setQuestion(question);
        user.setAnswer(answer);
        sendBody.setObjMessage(user);
        this.send(sendBody);
    }
    public void addFriend(String friendname){
        if(friendname.equals(User.getUsername())){
            JOptionPane.showMessageDialog(this.mainUI,"不能添加自己","警告",2);
            return;
        }
        SendBody sendBody=new SendBody();
        sendBody.setOrder(Command.addfriend);
        sendBody.setSourceID(User.getUsername());
        sendBody.setDestID(friendname);
        this.send(sendBody);
    }
    public void delFriend(String friendname){
        SendBody sendBody=new SendBody();
        sendBody.setOrder(Command.delfriend);
        sendBody.setSourceID(User.getUsername());
        sendBody.setDestID(friendname);
        this.send(sendBody);
    }
    public void sendChatMessage(String s,String username,String friendname,String date){
        SendBody sendBody=new SendBody();
        sendBody.setOrder(Command.personchat);

        this.User.setMessagestatus(true);
        this.User.setMessage(s);

        this.User.setStringDate(date);
        sendBody.setObjMessage(this.User);

        sendBody.setSourceID(username);
        sendBody.setDestID(friendname);
        this.send(sendBody);
    }
    public void updateHistory(String username,String friendID){
        SendBody sendBody=new SendBody();
        sendBody.setOrder(Command.presonchatupdatehistory);
        sendBody.setSourceID(username);
        sendBody.setDestID(friendID);
        this.send(sendBody);
    }
    public void updatemessagestatus(String username,String friendID){
        SendBody sendBody=new SendBody();
        sendBody.setOrder(Command.updatemessagestatus);
        sendBody.setSourceID(username);
        sendBody.setDestID(friendID);
        this.send(sendBody);
    }
    private void addfriendupdate(String friendid){
        User_friends friend=new User_friends();
        friend.setFriendId(friendid);
        friend.setStatus(true);
        LinkedList<User_friends> u=this.User.getFriendlist();
        if(u==null){
            u=new LinkedList<>();
        }
        u.add(friend);
        this.User.setFriendlist(u);
    }
    private void delfriendupdate(String friendID){
        int num=-1;
        for(int i=0;i<this.User.getFriendlist().size();i++){
            if(this.User.getFriendlist().get(i).getFriendId().equals(friendID)){
                num=i;
            }
        }
        if(num!=-1){
            LinkedList<User_friends> u=this.User.getFriendlist();
            u.remove(num);
            this.User.setFriendlist(u);
        }
    }

    public void function(SendBody sendBody){
        this.loginreceiveorder(sendBody);
        this.registreceiveordre(sendBody);
        this.addfriend(sendBody);
        this.delfriend(sendBody);
        this.personchat(sendBody);
        this.updatehistory(sendBody);
        this.friendOnAndOff(sendBody);

    }

    public static void main(String args[]){
        client=new Client();
        client.init();
    }
}
