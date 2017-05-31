package Client;

import Setings.User;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class FriendBox extends JPanel{
	private JLabel Id = new JLabel();	//friend id
    private boolean stat;	//is Alive	
    private JLabel messageStat = new JLabel();
    public boolean message;
    private String id;
    private JPopupMenu jp = new JPopupMenu();	//右键
	private JMenuItem jm = new JMenuItem("删除好友");		//菜单
	private MainWindow mw;
	private User user;
	
	public FriendBox(String id,boolean stat,boolean messageStat,MainWindow mw,User user){
		this.user=user;
		this.message = messageStat;
		this.id = id;
		this.Id.setText(id);	//id
		if(stat == false)
			this.Id.setForeground(new Color(192,192,192));
		this.stat = stat;
		if(message == true)	//消息显示
			this.messageStat.setIcon(new ImageIcon("Images/message.png"));
		else
			this.messageStat.setIcon(new ImageIcon("Images/nomessage.png"));
		this.mw = mw;	//主窗体
		this.setLayout(new FlowLayout(200,10,20));
		this.add(this.messageStat);this.add(this.Id);
		
		jp.add(jm);
		jm.addActionListener(new ActionListener(){		//右键事件监听
			@Override
			public void actionPerformed(ActionEvent e) { new delWindow(Id.getText(),mw,user);}});
		this.add(jp);	//加入右键菜单
		this.addMouseListener(new MouseAdapter(){	//显示右键菜单
			public void mouseClicked(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON3){
					jp.show(e.getComponent(),e.getX(),e.getY());
				}
			}
		});
		
		this.setBorder(BorderFactory.createEtchedBorder());
		
	}

	public String getId() {
		return id;
	}
	public void setStat(boolean stat){
		this.stat=stat;
		if(!this.stat){
			this.Id.setForeground(new Color(192,192,192));
		}else{
			this.Id.setForeground(new Color(0,0,0));
		}
	}
	public void setMessage(boolean message){
		this.message=message;
		if(message == true)	//消息显示
			this.messageStat.setIcon(new ImageIcon("Images/message.png"));
		else
			this.messageStat.setIcon(new ImageIcon("Images/nomessage.png"));

	}
}
