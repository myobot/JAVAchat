package Client;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static java.lang.Math.random;

class Login {
    private JFrame frame=new JFrame("登录界面");
    private JButton submit=new JButton("登录");
    private JButton zhuce=new JButton("注册");
    private JLabel usernamelab=new JLabel("用户名");
    private JLabel passwdlab=new JLabel("密码");
    private JLabel inforlab=new JLabel("");
    private JTextField usernametext=new JTextField();
    private JPasswordField passwdtext=new JPasswordField();
    private ImageIcon background=new ImageIcon("Images/login.jpg");;
    private JLabel jLabel=new JLabel(background);

    /*
    private Container cont=frame.getContentPane();
    private JPanel mainpanel =new JPanel();
    private JScrollPane scr;
    */
    private Dimension screamsize=Toolkit.getDefaultToolkit().getScreenSize();
    private int width=(int)screamsize.getWidth();
    private int height=(int)screamsize.getHeight();
    public Login()
    {
        Font fnt=new Font("新宋体",Font.BOLD,14);
//        UIManager.put("JOptionPane.messageFont", fnt);
        inforlab.setFont(fnt);
        frame.setFont(fnt);
        submit.setFont(fnt);
        zhuce.setFont(fnt);
        usernamelab.setFont(fnt);
        passwdlab.setFont(fnt);
        usernametext.setFont(fnt);
        passwdtext.setFont(fnt);

        usernamelab.setForeground(new Color(255,255,255,220));
        passwdlab.setForeground(new Color(255,255,255,220));

        submit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent en)
            {
                if(en.getSource()==submit) {
                    String username = usernametext.getText();
                    String passwd = new String(passwdtext.getPassword());
                    if (username.equals("")) {
                        JOptionPane.showMessageDialog(frame, "请输入用户名！");
                        usernametext.requestFocus();
                    }else if(passwd.equals("")){
                        JOptionPane.showMessageDialog(frame, "请输入密码！");
                        passwdtext.requestFocus();
                    }else {
                        LoginCheck l = new LoginCheck(frame,username, passwd);
                        l.Check();
                    }
                }
            }
        });

        /**
         * 输入面板键盘监听类
         */
        KeyListener key_Listener=new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    String username = usernametext.getText();
                    String passwd = new String(passwdtext.getPassword());
                    if (username.equals("")) {
                        JOptionPane.showMessageDialog(frame, "请输入用户名！");
                        usernametext.requestFocus();
                    }else if(passwd.equals("")){
                        JOptionPane.showMessageDialog(frame, "请输入密码！");
                        passwdtext.requestFocus();
                    }else {
                        LoginCheck l = new LoginCheck(frame,username, passwd);
                        l.Check();

                    }
                }
                if ((e.getKeyChar() == 'c' || e.getKeyChar() == 'C') && e.isControlDown()) {
                    System.exit(1);
                }
            }
        };
        usernametext.addKeyListener(key_Listener);
        passwdtext.addKeyListener(key_Listener);
        submit.addKeyListener(key_Listener);

        KeyListener key_Listenerzc=new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    Register r=new Register();


                }

                if ((e.getKeyChar() == 'c' || e.getKeyChar() == 'C') && e.isControlDown()) {
                    System.exit(1);
                }
            }
        };
        zhuce.addKeyListener(key_Listenerzc);

        KeyListener key_Listenerfp=new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {

                }

                if ((e.getKeyChar() == 'c' || e.getKeyChar() == 'C') && e.isControlDown()) {
                    frame.dispose();
                    System.exit(1);
                }
            }
        };


        zhuce.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==zhuce)
                {
                    Register r=new Register();
                }
            }
        });
        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent en)
            {
                usernametext.requestFocus();
            }
            public void windowClosing(WindowEvent en) {
                frame.dispose();
                Client.client.logout();
                System.exit(1);
            }
        });
        frame.setLayout(null);
        frame.setLocation((this.width/2-220),(this.height/2-136));
        usernamelab.setBounds(30,60,60,30);
        passwdlab.setBounds(30,100,60,30);
        usernametext.setBounds(110,60,180,30);
        passwdtext.setBounds(110,100,180,30);
        zhuce.setBounds(295,60,100,30);

        zhuce.setFocusable(false);

        submit.setBounds(110,180,180,50);
        inforlab.setBounds(130,230,200,30);

        frame.add(usernamelab);
        frame.add(passwdlab);
        frame.add(inforlab);
        frame.add(usernametext);
        frame.add(passwdtext);

        frame.add(zhuce);
        frame.add(submit);

        jLabel.setBounds(0,0,440,290);

        frame.getLayeredPane().add(jLabel, new Integer(Integer.MIN_VALUE));
        Container cp=frame.getContentPane();
        ((JPanel)cp).setOpaque(false);
        frame.setSize(440,290);
        frame.setVisible(true);
        frame.setResizable(false);

    }
    public JFrame getFrame(){
        return this.frame;
    }


}

/**
 * Created by wzw on 2016/11/24.
 */
class LoginCheck {
    private String username;
    private String passwd;
    private JFrame jFrame;
    public LoginCheck(JFrame jFrame,String username,String passwd)
    {
        this.jFrame=jFrame;
        this.username=username;
        this.passwd=passwd;

    }

    public void Check() {
        Client.client.login(this.username,this.passwd);
    }

}

