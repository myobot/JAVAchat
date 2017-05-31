package Client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class addFriend extends JFrame implements ActionListener{

    /**
     * 添加好友界面
     */
    private static final long serialVersionUID = 1L;
    private JTextField id = new JTextField();
    private JButton ad = new JButton("添加");
    private JLabel ti = new JLabel("请输入ID号添加好友");
    public addFriend(){
        this.setTitle("添加好友");
        this.setLayout(null);
        ti.setBounds(65, 10, 120, 20);
        this.add(ti);
        id.setBounds(40, 40, 170, 30);
        this.add(id);
        ad.setBounds(85, 80, 80, 30);
        this.add(ad);
        ad.addActionListener(this);

        this.setLocation(500, 250);
        this.setSize(250, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == ad){
            Client.client.addFriend(id.getText());
            this.dispose();
        }
    }
}
