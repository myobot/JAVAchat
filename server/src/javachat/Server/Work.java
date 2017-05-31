package javachat.Server;
import Setings.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by wzw on 2016/12/17.
 */
public class Work extends Thread {
    public int id;
    private ObjectInputStream netIn=null;
    private ObjectOutputStream netout=null;
    private Socket socket=null;
    private boolean runflag=true;
    private Server server=null;
    public Work(Server server,Socket socket)throws IOException{
        this.socket=socket;
        this.server=server;
        this.netIn=new ObjectInputStream(socket.getInputStream());
        this.netout=new ObjectOutputStream(socket.getOutputStream());
    }
    public void receive(){
        SendBody sendBody;
        try {
            sendBody=(SendBody)this.netIn.readObject();
            if((sendBody)!=null){
                System.out.print(server.getTime() + " 处理用户请求：" + sendBody.getOrder() + "\n");
                this.functions(sendBody);
            }
        }catch (IOException var3) {
            var3.printStackTrace();
            System.out.println("IO异常");
            this.runflag=false;
        } catch (ClassNotFoundException var4) {
            var4.printStackTrace();
            System.out.println("找不到这个类");
        }
    }
    public void run(){
        while(this.runflag){
            this.receive();
        }
    }
    public void send(SendBody sendBody){
        try{
            this.netout.writeObject(sendBody);
            this.netout.flush();
        }catch (IOException var3){
            System.out.println("发送异常"+var3.getMessage());
        }
    }

    public void login(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.login)){
            User user=(User)sendBody.getObjMessage();
            System.out.println("username:"+user.getUsername()+"\npasswd:"+user.getPasswd());
            if(!this.server.findUser(user.getUsername())){
                sendBody.setOrder(Command.loginfail);
                this.send(sendBody);
                System.out.println(1);
                return;
            }
            User userinfo=this.server.getUser(user.getUsername());
            if(userinfo!=null&&userinfo.getUsername().equals(user.getUsername())&&userinfo.getPasswd().equals(user.getPasswd())){
                if(this.server.isOnline(user.getUsername())){
                    sendBody.setOrder(Command.loginreapt);
                    this.send(sendBody);
                    System.out.println(2);
                    return;
                }
                User user1=this.server.getUser(user.getUsername());
                sendBody.setOrder(Command.loginsuccess);
                sendBody.setObjMessage(user1);
                this.send(sendBody);
                this.server.saveOnlineUser(user);
                sendBody.setOrder(Command.friendonline);
                sendBody.setSourceID(user.getUsername());
                sendBody.setObjMessage(null);
                this.server.sendToFriends(user.getUsername(),sendBody);
                System.out.println(3);
            }else{
                sendBody.setOrder(Command.loginfail);
                this.send(sendBody);
                System.out.println(4);
            }
        }
    }

    public void logout(SendBody sendBody){
        if(sendBody.getOrder().equals(Command.logout)){
            sendBody.setOrder(Command.friendoutline);
            this.server.sendToFriends(sendBody.getSourceID(),sendBody);
            this.runflag=false;
            this.server.logout(this);
        }

    }

    public void addFriend(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.addfriend)){
            User user=(User)sendBody.getObjMessage();
            if(!this.server.findUser(sendBody.getDestID())){
                sendBody.setOrder(Command.addfriendisnotexist);
                this.send(sendBody);
                return;
            }
            User userinfo=this.server.getUser(sendBody.getSourceID());
            if(this.server.checkUserFriend(userinfo,sendBody.getDestID())){
                sendBody.setOrder(Command.addfriendagin);
                this.send(sendBody);
                return;
            }
            sendBody.setOrder(Command.addfriendrequestconfirm);
            if(!this.server.sendtoUser(sendBody.getDestID(),sendBody)){
                sendBody.setOrder(Command.addfriendfail);
                this.send(sendBody);
                return;
            }
        }
        if(order.equals(Command.addfriendagree)){
            if(this.server.addFriend(sendBody.getSourceID(),sendBody.getDestID())){
                User userinfo=this.server.getUser(sendBody.getSourceID());
                sendBody.setObjMessage(userinfo);
                sendBody.setOrder(Command.addfriendconfirmagree);
                if(!this.server.sendtoUser(sendBody.getSourceID(),sendBody)){
                    sendBody.setOrder(Command.addfriendfail);
                    this.send(sendBody);
                    return;
                }
            }else{
                sendBody.setOrder(Command.addfriendfail);
                this.send(sendBody);
                return;
            }
        }
        if(order.equals(Command.addfriendrefuse)){
            User user=(User)sendBody.getObjMessage();
            sendBody.setOrder(Command.addfriendconfirmrefuse);
            if(!this.server.sendtoUser(sendBody.getSourceID(),sendBody)){
                sendBody.setOrder(Command.addfriendfail);
                this.send(sendBody);
                return;
            }
        }
    }

    public void delFriend(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.delfriend)){
            if(this.server.delfirend(sendBody.getSourceID(),sendBody.getDestID())){
                User userinfo=this.server.getUser(sendBody.getSourceID());
                sendBody.setOrder(Command.delfriendsuccess);
                sendBody.setObjMessage(userinfo);
                this.send(sendBody);
                sendBody.setOrder(Command.bedelete);
                this.server.sendtoUser(sendBody.getDestID(),sendBody);


            }else{
                sendBody.setOrder(Command.delfriendfail);
                this.send(sendBody);
            }
        }
    }

    public void sendMessage(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.personchat)){
            User user=(User)sendBody.getObjMessage();
            if(user.getMessageStatus()){
                if(!this.server.checkUserFriend(user,sendBody.getDestID())){
                    sendBody.setOrder(Command.personchatfail);
                    this.send(sendBody);
                    System.out.println("1");
                    return;
                }
                String message=user.getMessage();
                String d=user.getStringDate();
                try{


                    System.out.println(d+"\n"+message+"\n"+sendBody.getSourceID()+"\n"+sendBody.getDestID());
                    if(!this.server.isOnline(sendBody.getDestID())) {
                        this.server.inserthistory(user.getUsername(), sendBody.getDestID(), d, message, 0);
                        sendBody.setOrder(Command.presonchatisiffline);
                    }
                    else {
                        this.server.inserthistory(user.getUsername(), sendBody.getDestID(), d, message, 1);
                        sendBody.setOrder(Command.sendmessage);
                        if (this.server.sendtoUser(sendBody.getDestID(), sendBody)) {
                            User user1=this.server.getUser(sendBody.getSourceID());
                            sendBody.setObjMessage(user1);
                            sendBody.setOrder(Command.personchatsuccess);
                            this.send(sendBody);
                        }else{
                            sendBody.setOrder(Command.personchatfail);
                            this.send(sendBody);
                            System.out.println("2");
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void regist(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.register)){
            User user=(User)sendBody.getObjMessage();
            if(this.server.addnewuser(user)){
                sendBody.setOrder(Command.registersuccess);
                this.send(sendBody);
            }else{
                sendBody.setOrder(Command.registerfail);
                this.send(sendBody);
            }

        }
    }

    public void changePasswd(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.changepasswd)){
            User user=(User)sendBody.getObjMessage();
            if(this.server.changePasswd(user)){
                sendBody.setOrder(Command.changepasswdsuccess);
                this.send(sendBody);
            }else{
                sendBody.setOrder(Command.changepasswdfail);
                this.send(sendBody);
            }
        }
    }

    public void updateHistory(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.presonchatupdatehistory)){
            User user=this.server.getUser(sendBody.getSourceID());
            sendBody.setOrder(Command.presonchatupdatehistoryreturn);
            sendBody.setObjMessage(user);
            this.send(sendBody);
            this.server.updateStatus(sendBody.getSourceID(),sendBody.getDestID());
        }
    }
    public void updatemesagestatus(SendBody sendBody){
        String order=sendBody.getOrder();
        if(order.equals(Command.updatemessagestatus)){
            this.server.updateStatus(sendBody.getSourceID(),sendBody.getDestID());
        }
    }

    public void functions(SendBody sendBody){
        this.login(sendBody);
        this.logout(sendBody);
        this.addFriend(sendBody);
        this.delFriend(sendBody);
        this.sendMessage(sendBody);
        this.regist(sendBody);
        this.changePasswd(sendBody);
        this.updateHistory(sendBody);
        this.updatemesagestatus(sendBody);
    }
}
