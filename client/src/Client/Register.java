package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wzw on 2016/11/22.
 */
public class Register {
    private JFrame title = new JFrame("用户注册");
    private JLabel usernamelab = new JLabel("用户名");
    private JLabel passwdlab = new JLabel("密码");
    private JLabel repasswdlab =new JLabel("确认密码");
    private JLabel questionlab = new JLabel("密码问题");
    private JLabel answerlab = new JLabel("答案");
    private JTextField usernametext = new JTextField();
    private JPasswordField passwdtext = new JPasswordField();
    private JPasswordField repasswdtext = new JPasswordField();
    private JTextField answertext = new JTextField();
    private String question[] = {"您目前的姓名是？", "您配偶的生日是？", "您的学号（或工号）是？", "您母亲的生日是？", "您高中班主任的名字是？", "您父亲的姓名是？", "您小学班主任的名字是？", "您父亲的生日是？", "您配偶的姓名是？", "您初中班主任的名字是？", "您最熟悉的童年好友名字是？", "您最熟悉的学校宿舍舍友名字是？", "对您影响最大的人名字是？"};
    private JComboBox questionlist = new JComboBox(question);
    private JButton submit = new JButton("注册");
    private JLabel inforlab = new JLabel("");
    private Dimension screamsize = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = (int) screamsize.getWidth();
    private int height = (int) screamsize.getHeight();

    public Register() {
        Font fnt1=new Font("新宋体",Font.BOLD,16);
        UIManager.put("OptionPane.font", fnt1);
        UIManager.put("OptionPane.messageFont", fnt1);
        UIManager.put("OptionPane.buttonFont", fnt1);
        Font fnt = new Font("新宋体", Font.BOLD, 14);
        title.setFont(fnt);
        usernamelab.setFont(fnt);
        passwdlab.setFont(fnt);
        questionlab.setFont(fnt);
        answerlab.setFont(fnt);
        submit.setFont(fnt);
        questionlist.setFont(fnt);
        questionlist.setBorder(BorderFactory.createTitledBorder("请选择问题"));
        questionlist.setMaximumRowCount(6);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent en) {
                if (en.getSource() == submit) {
                    String username = usernametext.getText();
                    String passwd = new String(passwdtext.getPassword());
                    String repasswd=new String(repasswdtext.getPassword());
                    String selectedquestion = (String) questionlist.getSelectedItem();
                    String answer = answertext.getText();
                    if (username.equals("")) {
//                        inforlab.setText("请输入用户名！");
                        JOptionPane.showMessageDialog(title,"请输入用户名!");
                        usernametext.requestFocus();
                    } else if (passwd.equals("")) {
//                        inforlab.setText("请先输入密码！");
                        JOptionPane.showMessageDialog(title,"请输入密码!");
                        passwdtext.requestFocus();
                    } else if (selectedquestion.equals("")) {
//                        inforlab.setText("请选择问题！");
                        JOptionPane.showMessageDialog(title,"请选择问题!");
                        questionlist.requestFocus();
                    } else if (answer.equals("")) {
//                        inforlab.setText("请输入问题答案");
                        JOptionPane.showMessageDialog(title,"请输入问题答案!");
                        answertext.requestFocus();
                    } else if(!passwd.equals(repasswd)) {
                        JOptionPane.showMessageDialog(title,"两次输入密码不一致！");
                        repasswdtext.requestFocus();
                    }else {
                        /*  username passwd question answer flag*/
                        RegisterCheck check = new RegisterCheck(title,username, passwd, selectedquestion, answer, true);
                        check.Regist();
                    }

                }
            }
        });

        KeyListener key_Listener = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    String username = usernametext.getText();
                    String passwd = new String(passwdtext.getPassword());
                    String repasswd = new String(repasswdtext.getPassword());
                    String selectedquestion = (String) questionlist.getSelectedItem();
                    String answer = answertext.getText();
                    if (username.equals("")) {
//                        inforlab.setText("请输入用户名！");
                        JOptionPane.showMessageDialog(title, "请输入用户名!");
                        usernametext.requestFocus();
                    } else if (passwd.equals("")) {
//                        inforlab.setText("请先输入密码！");
                        JOptionPane.showMessageDialog(title, "请输入密码!");
                        passwdtext.requestFocus();
                    } else if (selectedquestion.equals("")) {
//                        inforlab.setText("请选择问题！");
                        JOptionPane.showMessageDialog(title, "请选择问题!");
                        questionlist.requestFocus();
                    } else if (answer.equals("")) {
//                        inforlab.setText("请输入问题答案");
                        JOptionPane.showMessageDialog(title, "请输入问题答案!");
                        answertext.requestFocus();
                    } else if (!passwd.equals(repasswd)) {
                        JOptionPane.showMessageDialog(title, "两次输入密码不一致！");
                        repasswdtext.requestFocus();
                    } else {

                        RegisterCheck check = new RegisterCheck(title,username, passwd, selectedquestion, answer, true);
                        check.Regist();

                    }
                    if ((e.getKeyChar() == 'c' || e.getKeyChar() == 'C') && e.isControlDown()) {
                        System.exit(1);
                    }
                }
            }
        };
        submit.addKeyListener(key_Listener);
        usernametext.addKeyListener(key_Listener);
        passwdtext.addKeyListener(key_Listener);
        answertext.addKeyListener(key_Listener);

        title.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent en) {
                usernametext.requestFocus();
            }

            public void windowClosing(WindowEvent en) {
                title.dispose();
            }
        });
        title.setLayout(null);
        title.setLocation((this.width / 2 - 270), (this.height / 2 - 210));
        usernamelab.setBounds(50, 40, 60, 30);
        passwdlab.setBounds(50, 80, 60, 30);
        repasswdlab.setBounds(50,120,60,30);
        questionlab.setBounds(50, 175, 60, 30);
        answerlab.setBounds(50, 240, 60, 30);
        usernametext.setBounds(130, 40, 280, 30);
        passwdtext.setBounds(130, 80, 280, 30);
        repasswdtext.setBounds(130,120,280,30);
        questionlist.setBounds(130, 160, 280, 60);
        answertext.setBounds(130, 240, 280, 30);
        submit.setBounds(130, 310, 260, 50);
        title.add(usernamelab);
        title.add(passwdlab);
        title.add(repasswdlab);
        title.add(questionlab);
        title.add(answerlab);
        title.add(usernametext);
        title.add(passwdtext);
        title.add(repasswdtext);
        title.add(questionlist);
        title.add(answertext);
        title.add(submit);
        title.setSize(540, 420);
        title.setVisible(true);
        title.setResizable(false);

    }
}

/**
 * Created by wzw on 2016/11/25.
 */
class RegisterCheck {
    private String username;
    private String passwd;
    private String question;
    private String answer;
    private boolean flag;
    private JFrame jFrame;
    public RegisterCheck(JFrame jFrame,String username,String passwd,String question,String answer,boolean flag)
    {
        this.jFrame=jFrame;
        this.username=username;
        this.passwd=passwd;
        this.question=question;
        this.answer=answer;
        this.flag=flag;
    }
    public void Regist() {
        Client.client.regist(this.jFrame,this.username,this.passwd,this.question,this.answer);

    }
}


