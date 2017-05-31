package Client;

import Setings.MessageList;
import Setings.User;

import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow extends JFrame implements MouseListener, ActionListener, KeyListener, WindowListener{
	
	/**
	 * 添加好友界面
	 */
	private static final long serialVersionUID = 1L;
	private JPanel infor = new JPanel();	//信息面板,双击打开聊天面板
	//private JLabel inforPic;	//信息背景
	private JPanel chat = new JPanel();		//聊天面板,双击退出聊天面板
	//private JLabel chatPic;	//聊天背景
	private JPanel friend = new JPanel();	//在信息面板中的好友面板
	private JScrollPane FrJSP = new JScrollPane();//侧边进度条
	private JButton quit = new JButton("退出聊天");	//离开聊天面板
	private JButton send = new JButton("发送");		//发送消息
	private JButton history = new JButton("查看历史消息");	//历史消息
	private JLabel name,pic;	//信息面板控件
	private JButton search = new JButton("Search");	//搜索
	private JTextField searchFriend = new JTextField();
	private Toolkit tool;	//获取图片
	private JLabel toMessage = new JLabel();	//聊天面板发送给谁
	private ArrayList<FriendBox> Fb = new ArrayList<FriendBox>();	//好友信息面板
	private JButton add = new JButton("+ 添加好友");
	private int num = 0;//鼠标点击次数
	private int rnum = 0;	//鼠标右键点击次数
	private String chatObject;
	private JPopupMenu menu = new JPopupMenu();	//右键
	private Color c1 = new Color(255,255,255);
	private Color cs1 = new Color(220,220,220);
	private Color c2 = new Color(240,230,140);
	private Color cs2 = new Color(255,215,0);
	private Color c3 = new Color(250,128,114);
	private Color cs3 = new Color(255,69,0);
	private Color c4 = new Color(173,216,230);
	private Color c5 = new Color(152,251,152);
	private Color cs5 = new Color(60,179,113);
	private Color c6 = new Color(100,149,237);
	private Color color = c1;
	private JMenuItem color1 = new JMenuItem("纯洁白");		//菜单
	private JMenuItem color2 = new JMenuItem("卡其布");
	private JMenuItem color3 = new JMenuItem("可爱粉");
	private JMenuItem color4 = new JMenuItem("清新蓝");
	private JMenuItem color5 = new JMenuItem("光彩绿");
	private JTextArea text = new JTextArea(10,20);	//界面
	private JTextField input = new JTextField(50);	//输入框
	private JScrollPane chatArea;
	private JPanel setText = new JPanel();
	public  ArrayList<Friend> FriendList ;
	public User user;
	private ImageIcon background=new ImageIcon("Images/infor.jpg");;
	private JLabel jLabel=new JLabel(background);


	public MainWindow(ArrayList<Friend> FriendList,User user){

		this.FriendList=FriendList;
		this.user=user;
		createFriendBoxList(FriendList);		//加工好友面板

		setColor();

		infor.addMouseListener(new MouseAdapter(){	//显示右键菜
			public void mouseClicked(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON3){
					menu.show(e.getComponent(),e.getX(),e.getY());
				}
			}
		});
		infor.setBounds(0, 0, 250, 400);infor.addMouseListener(this);infor.setBackground(color);
		this.add(infor);infor.setLayout(null);//信息面板设置
		int ran = new Random().nextInt(5);
		pic = new JLabel(new ImageIcon("Images/tou"+ran+".jpg"));pic.setBounds(0, 0, 70, 70);infor.add(pic);pic.addMouseListener(this);
		Font fn = new Font("幼圆",Font.BOLD,20);name = new JLabel(this.user.getUsername());Color co = null;
		if(ran == 0)co = Color.PINK;else if(ran == 1)co = c6;else if(ran == 2) co = cs5;else if(ran == 3)co = cs2;else if(ran == 4)co = cs3;
		else if(ran == 5) co = Color.BLACK;
		name.setForeground(co);name.addMouseListener(this);name.setFont(fn);infor.add(name);name.setBounds(120, 20, 200, 30);
		add.setBounds(10, 330, 100, 30);infor.add(add);add.addActionListener(this);
		add.setBackground(color);add.addMouseListener(this);add.setBorderPainted(true);
		search.setBounds(160, 70, 80, 30);infor.add(search);search.addActionListener(this);search.addMouseListener(this);
		search.setBackground(color);search.setBorderPainted(true);searchFriend.addKeyListener(this);
		searchFriend.setBackground(c1);searchFriend.setBounds(0, 70, 160, 30);infor.add(searchFriend);searchFriend.addMouseListener(this);
		setFriend();

		chat.setBounds(253, 0, 350, 400);this.add(chat);chat.setBackground(new Color(173,216,230));
		chat.setLayout(null);chat.addMouseListener(this);//聊天面板设置
		quit.setBounds(42, 330, 100, 30);chat.add(quit);quit.addMouseListener(this);
		quit.addActionListener(this);quit.setBackground(new Color(173,216,230));
		send.setBounds(200, 330, 100, 30);chat.add(send);send.addMouseListener(this);
		send.setBorderPainted(true);send.setBackground(new Color(173,216,230));send.addActionListener(this);
		history.setBounds(200, 10, 125, 30);chat.add(history);history.addMouseListener(this);
		history.setBackground(new Color(173,216,230));history.setBorderPainted(true);//ȥ���߿�
		send.addActionListener(this);

		chatArea = new JScrollPane(text);
		text.setFont(new Font("幼圆",Font.PLAIN,15));
		text.setCaretPosition(text.getText().length());	//自动定位
		setText.setLayout(new GridLayout(1,1));
		setText.add("Center",chatArea);
		chat.add(setText);setText.setBounds(10, 50, 320, 200);
		text.setEditable(false);text.setLineWrap(true);//换行
		input.setBackground(c1);input.setBounds(10, 265, 200, 30);chat.add(input);input.addKeyListener(this);input.addMouseListener(this);

		tool = this.getToolkit();Image img = tool.getImage("Images/head.jpg");this.setIconImage(img);//设置图标

		jLabel.setBounds(0,0,250,400);

		this.getLayeredPane().add(jLabel, new Integer(Integer.MIN_VALUE));
		Container cp=this.getContentPane();
		((JPanel)cp).setOpaque(false);

		this.setTitle("Chat");
		this.setLayout(null);
		this.setSize(250, 400);
		this.setLocation(500, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(this);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void setColor() {
		// TODO Auto-generated method stub
		color1.setBackground(color);color2.setBackground(color);color3.setBackground(color);
		color4.setBackground(color);color5.setBackground(color);
		color1.addActionListener(new ActionListener(){		//右键事件监听
			public void actionPerformed(ActionEvent e) {color = c1;infor.setBackground(color);search.setBackground(color);add.setBackground(color);change();}

			private void change() {
				// TODO Auto-generated method stub
				for(int i = 0;i < Fb.size();i ++){
					Fb.get(i).setBackground(color);
				}
			}});
		color2.addActionListener(new ActionListener(){		//右键事件监听
			public void actionPerformed(ActionEvent e) {color = c2;infor.setBackground(color);search.setBackground(color);add.setBackground(color);change();}
			private void change() {
				// TODO Auto-generated method stub
				for(int i = 0;i < Fb.size();i ++){
					Fb.get(i).setBackground(color);
				}
			}});
		color3.addActionListener(new ActionListener(){		//右键事件监听
			public void actionPerformed(ActionEvent e) {color = c3;infor.setBackground(color);search.setBackground(color);add.setBackground(color);change();}
			private void change() {
				// TODO Auto-generated method stub
				for(int i = 0;i < Fb.size();i ++){
					Fb.get(i).setBackground(color);
				}
			}});
		color4.addActionListener(new ActionListener(){		//右键事件监听
			public void actionPerformed(ActionEvent e) {color = c4;infor.setBackground(color);search.setBackground(color);add.setBackground(color);change();}
			private void change() {
				// TODO Auto-generated method stub
				for(int i = 0;i < Fb.size();i ++){
					Fb.get(i).setBackground(color);
				}
			}});
		color5.addActionListener(new ActionListener(){		//右键事件监听
			public void actionPerformed(ActionEvent e) {color = c5;infor.setBackground(color);search.setBackground(color);add.setBackground(color);change();}
			private void change() {
				// TODO Auto-generated method stub
				for(int i = 0;i < Fb.size();i ++){
					Fb.get(i).setBackground(color);
				}
			}});
		menu.add(color1);menu.add(color2);menu.add(color3);menu.add(color4);menu.add(color5);
		menu.setBackground(color);
		infor.add(menu);
	}

	private void createFriendBoxList(ArrayList<Friend> FriendList) {	//将取得的好友信息加工为好友面板
		// TODO Auto-generated method stub
		for(int i = 0;i < FriendList.size();i ++){
			Fb.add(new FriendBox(FriendList.get(i).getId(),FriendList.get(i).isStat(),FriendList.get(i).isMessageStat(),this,this.user));
		}
	}
	public void addFriend(Friend friend){
		FriendBox friendBox=new FriendBox(friend.getId(),friend.isStat(),friend.isMessageStat(),this,this.user);
		friendBox.addMouseListener(this);
		friendBox.setBackground(color);
		this.FriendList.add(friend);
		this.Fb.add(friendBox);
		this.friend.setLayout(new GridLayout(this.Fb.size(),1));
		this.friend.add(friendBox);
		//Fb.get(i).setOpaque(false);

		this.validate();
	}
	public void delFriend(String friendid){
		int num=-1,num2=-1;
		FriendBox friendBox=null;
		for(int i=0;i<this.Fb.size();i++){
			if(this.Fb.get(i).getId().equals(friendid)){
				num=i;
				friendBox=this.Fb.get(i);
			}
		}
		for(int i=0;i<this.FriendList.size();i++){
			if(this.FriendList.get(i).getId().equals(friendid)){
				num2=i;
			}
		}
		if(num!=-1&&num2!=-1){
			this.Fb.remove(num);
			this.friend.setLayout(new GridLayout(this.Fb.size(),1));
			this.friend.remove(friendBox);
			this.FriendList.remove(num2);
			this.validate();
		}

	}
	private void setFriend() {
		// TODO Auto-generated method stub
		
		friend.setLayout(new GridLayout(Fb.size(),1));
		FrJSP = new JScrollPane(friend);
		FrJSP.getVerticalScrollBar().setUnitIncrement(20);
		FrJSP.setBounds(0, 100, 240, 225);
		//friend.setOpaque(false);
		
		for(int i = 0;i < Fb.size();i ++){
			if(Fb.get(i).message == true){
				friend.add(Fb.get(i));
				Fb.get(i).addMouseListener(this);
				Fb.get(i).setBackground(color);
				//Fb.get(i).setOpaque(false);
			}
		}
		for(int i = 0;i < Fb.size();i ++){
			if(Fb.get(i).message == false){
				friend.add(Fb.get(i));
				Fb.get(i).addMouseListener(this);
				Fb.get(i).setBackground(color);
				//Fb.get(i).setOpaque(false);
			}
		}

		FrJSP.setBackground(color);
		infor.add(FrJSP);
		this.validate();
	}
	
	private void create(MouseEvent e){	//打开聊天面板
		this.setSize(600,400 );
//		this.setLocation(400, 150);
		
		toMessage.setBounds(5, 5, 200, 40);
		chat.add(toMessage);
		if(e.getSource() == infor)
			for(int i = 0;i < FriendList.size();i ++){
				if(FriendList.get(i).isMessageStat() == true){
					chatObject = FriendList.get(i).getId();
					text.setText(FriendList.get(i).getMessage());
					FriendList.get(i).setMessageStat(false);
					break;
				}
			}
		else{
			for(int i = 0;i < FriendList.size();i ++){
				if(Fb.get(i) == e.getSource()){
					chatObject = FriendList.get(i).getId();
					text.setText(FriendList.get(i).getMessage());
					FriendList.get(i).setMessageStat(false);
					break;
				}
			}
		}
		for(int i=0;i<Fb.size();i++){
			if(chatObject.equals(Fb.get(i).getId())){
				if(color == c1)
					Fb.get(i).setBackground(cs1);
				else if(color == c2)
					Fb.get(i).setBackground(cs2);
				else if(color == c3)
					Fb.get(i).setBackground(cs3);
				else if(color == c4)
					Fb.get(i).setBackground(c6);
				else if(color == c5)
					Fb.get(i).setBackground(cs5);
			}
		}
		Font fn = new Font("宋体",Font.BOLD,15);
		toMessage.setText("Message to: "+ chatObject);
		toMessage.setFont(fn);toMessage.addMouseListener(this);
		//chatPic = new JLabel(new ImageIcon("Images/chat.jpg"));chatPic.setBounds(0, 0, 350, 400);chat.add(chatPic);
		
	}
    private void create(String friendid){	//打开聊天面板
        this.setSize(600,400 );
//		this.setLocation(400, 150);

        toMessage.setBounds(5, 5, 200, 40);
        chat.add(toMessage);
        chatObject=friendid;

        for(int i = 0;i < FriendList.size();i ++) {
            if (FriendList.get(i).getId().equals(friendid)) {
                text.setText(FriendList.get(i).getMessage());
                FriendList.get(i).setMessageStat(false);
                break;
            }
        }

        for(int i=0;i<Fb.size();i++){
            if(chatObject.equals(Fb.get(i).getId())){
                if(color == c1)
                    Fb.get(i).setBackground(cs1);
                else if(color == c2)
                    Fb.get(i).setBackground(cs2);
                else if(color == c3)
                    Fb.get(i).setBackground(cs3);
                else if(color == c4)
                    Fb.get(i).setBackground(c6);
                else if(color == c5)
                    Fb.get(i).setBackground(cs5);
            }
        }
        Font fn = new Font("宋体",Font.BOLD,15);
        toMessage.setText("Message to: "+ chatObject);
        toMessage.setFont(fn);toMessage.addMouseListener(this);
        //chatPic = new JLabel(new ImageIcon("Images/chat.jpg"));chatPic.setBounds(0, 0, 350, 400);chat.add(chatPic);

    }
	public void createRight(){
		this.setSize(250, 400);

		//this.repaint();
	}
	public void createSearch(){
		this.setSize(600,400 );

		
		toMessage.setBounds(5, 5, 200, 40);
		chat.add(toMessage);
		for(int i = 0;i < FriendList.size();i ++){
			if(FriendList.get(i).getId().equals(searchFriend.getText())){
				chatObject = FriendList.get(i).getId();
				text.setText(FriendList.get(i).getMessage());
				FriendList.get(i).setMessageStat(false);
				break;
			}
		}
		Font fn = new Font("宋体",Font.BOLD,15);
		toMessage.setText("Message to: "+ chatObject);
		toMessage.setFont(fn);toMessage.addMouseListener(this);
		//chatPic = new JLabel(new ImageIcon("Images/chat.jpg"));chatPic.setBounds(0, 0, 350, 400);chat.add(chatPic);
	}
	
	public void updateMessage(String id,String str,String date) {//更新好友的消息
        if(chatObject!=null) {
            if (chatObject.equals(id)) {
                text.setText(text.getText() + date + "\n" + id + ": " + str + "\n\n");
                text.setCaretPosition(text.getText().length());

            }
        }
		for (int i = 0; i < FriendList.size(); i++) {
			if (id.equals(FriendList.get(i).getId())) {
				FriendList.get(i).setMessage(FriendList.get(i).getMessage() + date + "\n" + id + ": " + str + "\n\n");
			}
		}

	}

	public void updateHistory(LinkedList<MessageList> messageLists,String friendname){
        if(chatObject!=null) {
            if (chatObject.equals(friendname)) {
                text.setText("");
				if(messageLists!=null) {
					for (int j = 0; j < messageLists.size(); j++) {
						if (messageLists.get(j).getSender().equals(user.getUsername())) {
							text.setText(text.getText() + messageLists.get(j).getDate() + "\n" + "我: " + messageLists.get(j).getMessage() + "\n\n");
						} else {
							if (messageLists.get(j).getStatus()) {
								text.setText(text.getText() + messageLists.get(j).getDate() + "\n" + friendname + ": " + messageLists.get(j).getMessage() + "\n\n");
							} else {
								text.setText(text.getText() + messageLists.get(j).getDate() + " 未读消息\n" + friendname + ": " + messageLists.get(j).getMessage() + "\n\n");
							}
						}
					}
				}
                text.setCaretPosition(text.getText().length());
            }
        }
		for(int i = 0;i < FriendList.size();i++){
			if(friendname.equals(FriendList.get(i).getId())){
				if(messageLists!=null) {
					FriendList.get(i).setMessage("");
					for (int j = 0; j < messageLists.size(); j++) {
						if (messageLists.get(j).getSender().equals(user.getUsername())) {
							FriendList.get(i).setMessage(FriendList.get(i).getMessage() + messageLists.get(j).getDate() + "\n" + "我: " + messageLists.get(j).getMessage() + "\n\n");
						} else {
							if (messageLists.get(j).getStatus()) {
								FriendList.get(i).setMessage(FriendList.get(i).getMessage() + messageLists.get(j).getDate() + "\n" + friendname + ": " + messageLists.get(j).getMessage() + "\n\n");
							} else {
								FriendList.get(i).setMessage(FriendList.get(i).getMessage() + messageLists.get(j).getDate() + " 未读消息\n" + friendname + ": " + messageLists.get(j).getMessage() + "\n\n");
							}
						}
					}
				}
			}
		}
	}
	
	public void updateFriend(String id,boolean is){		//上线、离线更新

//		Fb.clear();
//		friend.removeAll();
//		infor.remove(FrJSP);
//		for(int i = 0;i < FriendList.size();i ++){
//			if(FriendList.get(i).getId().equals(id)){
//				FriendList.get(i).setStat(is);
//			}
//		}
//		createFriendBoxList(FriendList);
//		setFriend();
		for(int i=0;i<Fb.size();i++){
			if(Fb.get(i).getId().equals(id)){
				Fb.get(i).setStat(is);
			}
		}
		this.validate();

	}
	
	public void updateMessageComing(String id){	//新消息来了
//		if(chatObject!=null) {
//            if(!chatObject.equals(id)) {
//                Fb.clear();
//                friend.removeAll();
//                infor.remove(FrJSP);
//                for (int i = 0; i < FriendList.size(); i++) {
//                    if (FriendList.get(i).getId().equals(id)) {
//                        FriendList.get(i).setMessageStat(true);
//						Fr.get(i).setMessage(true);
//                    }
//                }
//                createFriendBoxList(FriendList);
//                setFriend();
//            }
//        }else{
//            Fb.clear();
//            friend.removeAll();
//            infor.remove(FrJSP);
//            for (int i = 0; i < FriendList.size(); i++) {
//                if (FriendList.get(i).getId().equals(id)) {
//                    FriendList.get(i).setMessageStat(true);
//                }
//            }
//            createFriendBoxList(FriendList);
//            setFriend();
//        }

		if(chatObject!=null) {
			if(!chatObject.equals(id)) {
				for (int i = 0; i < FriendList.size(); i++) {
					if (FriendList.get(i).getId().equals(id)) {
						FriendList.get(i).setMessageStat(true);
						Fb.get(i).setMessage(true);
					}
				}
			}
		}
		else{
			for (int i = 0; i < FriendList.size(); i++) {
                if (FriendList.get(i).getId().equals(id)) {
                   	FriendList.get(i).setMessageStat(true);
					Fb.get(i).setMessage(true);
                }
            }

		}



	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
//		if(e.getButton() == MouseEvent.BUTTON1){
//			Timer timer = new Timer();
//			timer.schedule(new TimerTask(){
//				public void run(){
//					num = e.getClickCount();
//					if (num == 1) {
//						for (int i = 0; i < Fb.size(); i++) {
//							if (chatObject != null && chatObject.equals(Fb.get(i).getId())) {
//								Fb.get(i).setBackground(color);
//								Fb.get(i).setMessage(false);
//								Client.client.updatemessagestatus(user.getUsername(),chatObject);
//								validate();
//							}
//						}
//
//						create(e);
//						num = 0;
//						this.cancel();
//
//					}
//				}
//			}, new Date(), 200);
//		}
		if(e.getSource() == chat){
			if(e.getButton() == MouseEvent.BUTTON3){
					Timer timer = new Timer();
					timer.schedule(new TimerTask(){
						public void run(){
							rnum = e.getClickCount();
							if(rnum == 2){
								createRight();
								rnum = 0;
								chatObject=null;
								this.cancel();
							}
						}
					}, new Date(), 200);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == quit){
			this.setSize(250, 400);
			chatObject=null;
//			this.setLocation(500, 150);
			//this.repaint();
		}
		if(e.getSource() == send){
			if(input.getText()!=null){
				for(int i = 0;i < FriendList.size();i ++){
					if(chatObject.equals(FriendList.get(i).getId())){
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String d=sdf.format(new Date());
						text.setText(text.getText()+d+"\n我: "+input.getText()+"\n\n");
						FriendList.get(i).setMessage(text.getText());
						Client.client.sendChatMessage(input.getText(),user.getUsername(),chatObject,d);
						input.setText("");
					}
				}
			}
		}
		if(e.getSource() == add){
			new addFriend();
		}
		if(e.getSource() == history){
			//需要对象
			Client.client.updateHistory(user.getUsername(),chatObject);
		}
		if(e.getSource() == search){
			for(int i = 0;i < FriendList.size();i ++){
				if(FriendList.get(i).getId().equals(searchFriend.getText())){
					chatObject = FriendList.get(i).getId();
					createSearch();
					searchFriend.setText("");
					break;
				}
				else if(i +1 == FriendList.size()){
					new Window("查无此人",0);
					searchFriend.setText("");
				}
			}
		}
		for(int i=0;i<Fb.size();i++){
            if(e.getSource()==Fb.get(i)){
                for (int j = 0; j < Fb.size(); j++) {
                    if (chatObject != null && chatObject.equals(Fb.get(j).getId())) {
                        Fb.get(j).setBackground(color);
                        validate();
                    }
                }
                Client.client.updatemessagestatus(Fb.get(i).getId(), chatObject);
				create(Fb.get(i).getId());
                Fb.get(i).setMessage(false);
            }
        }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == history){
			history.setBackground(c6);
		}
		if(e.getSource() == quit){
			quit.setBackground(c6);
		}
		if(e.getSource() == send){
			send.setBackground(c6);
		}
		if(e.getSource() == name){
			name.setFont(new Font("幼圆",Font.ITALIC,20));
		}
		if(e.getSource() == searchFriend){
			searchFriend.setBackground(cs1);
		}
		if(e.getSource() == input){
			input.setBackground(cs1);
		}
		if(e.getSource() == toMessage){
			toMessage.setFont(new Font("幼圆",Font.ITALIC,15));
		}
		if(e.getSource() == pic){
			pic.setBounds(0, 0, 100, 100);
			searchFriend.setBounds(100, 70, 60, 30);
		}
		if(e.getSource() == search){
			if(color == c1)	search.setBackground(cs1);
			else if(color == c2) search.setBackground(cs2);
			else if(color == c3) search.setBackground(cs3);
			else if(color == c4) search.setBackground(c6);
			else if(color == c5) search.setBackground(cs5);
		}
		if(e.getSource() == add){
			if(color == c1)	add.setBackground(cs1);
			else if(color == c2) add.setBackground(cs2);
			else if(color == c3) add.setBackground(cs3);
			else if(color == c4) add.setBackground(c6);
			else if(color == c5) add.setBackground(cs5);
		}
		for(int i = 0;i < Fb.size();i ++){
			if(e.getSource() == Fb.get(i)){
				if(chatObject!=null&&chatObject.equals(Fb.get(i).getId())){
					continue;
				}
				if(color == c1)
					Fb.get(i).setBackground(cs1);
				else if(color == c2)
					Fb.get(i).setBackground(cs2);
				else if(color == c3)
					Fb.get(i).setBackground(cs3);
				else if(color == c4)
					Fb.get(i).setBackground(c6);
				else if(color == c5)
					Fb.get(i).setBackground(cs5);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == history){
			history.setBackground(c4);
		}
		if(e.getSource() == quit){
			quit.setBackground(c4);
		}
		if(e.getSource() == send){
			send.setBackground(c4);
		}
		if(e.getSource() == add){
			add.setBackground(color);
		}
		if(e.getSource() == searchFriend){
			searchFriend.setBackground(c1);
		}
		if(e.getSource() == input){
			input.setBackground(c1);
		}
		if(e.getSource() == pic){
			pic.setBounds(0, 0, 70, 70);
			searchFriend.setBounds(0, 70, 160, 30);
		}
		if(e.getSource() == toMessage){
			toMessage.setFont(new Font("宋体",Font.BOLD,15));
		}
		if(e.getSource() == search){
			search.setBackground(color);
		}
		if(e.getSource() == name){
			name.setFont(new Font("幼圆",Font.BOLD,20));
		}
		for(int i = 0;i < Fb.size();i ++){
			if(e.getSource() == Fb.get(i)){
				if(chatObject!=null&&chatObject.equals(Fb.get(i).getId())){
					continue;
				}
				Fb.get(i).setBackground(color);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub


	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyChar() == KeyEvent.VK_ENTER){
			if(e.getSource() == input){
				if(input.getText()!=null){
					for(int i = 0;i < FriendList.size();i ++){
						if(chatObject.equals(FriendList.get(i).getId())){
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String d=sdf.format(new Date());
							text.setText(text.getText()+d+"\n我: "+input.getText()+"\n\n");
							FriendList.get(i).setMessage(text.getText());
							Client.client.sendChatMessage(input.getText(),user.getUsername(),chatObject,d);
							input.setText("");
						}
					}
				}
			}
			if(e.getSource() == searchFriend){
				for(int i = 0;i < FriendList.size();i ++){
					if(FriendList.get(i).getId().equals(searchFriend.getText())){
						chatObject = FriendList.get(i).getId();
						createSearch();
						searchFriend.setText("");
						break;
					}
					else if(i +1 == FriendList.size()){
						new Window("查无此人",0);
						searchFriend.setText("");
					}
				}
				
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		Client.client.logout();
		this.dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}
}
