package Client;

import Setings.User;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

public class delWindow extends JFrame implements ActionListener{
    /**
     * 对话框
     */
    private static final long serialVersionUID = 1L;
    private String str;
    private String id;
    private JButton jb = new JButton("是的");
    private JButton no = new JButton("再想想");
    private JLabel jl = new JLabel();
    private MainWindow mw;
    private User user;
    public delWindow(String str,MainWindow mw,User user){
        this.user=user;
        this.str = "真的要删除"+str+"吗？";
        this.id = str;
        this.mw = mw;
        jl.setBounds(35, 20, 150, 50);
        jl.setText(this.str);
        jb.setBounds(145, 70, 80, 30);
        no.setBounds(20, 70, 80, 30);
        this.add(jb);
        this.add(no);
        this.setLayout(null);
        no.addActionListener(this);
        jb.addActionListener(this);
        this.add(jl);
        this.setSize(250, 150);
        Toolkit tool = this.getToolkit();Image img = tool.getImage("Images/head.jpg");this.setIconImage(img);//设置图标
        this.setLocation(500, 250);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == jb){//确认删除

            //数据库更新
            Client.client.delFriend(id);
            this.dispose();
        }
        if(e.getSource() == no){
            this.dispose();
        }
    }
}