package Client;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Window extends JFrame implements ActionListener,KeyListener{
	/**
	 * 对话框
	 */
	private static final long serialVersionUID = 1L;
	private String str;
	private JButton jb = new JButton("对话框");
	private JLabel jl = new JLabel();
	private int i = 0;
	public Window(String st,int i){
		this.str = st;
		this.i = i;
		jl.setBounds(35, 20, 150, 50);
		jl.setText(str);
		jb.setBounds(90, 70, 70, 30);
		this.add(jb);
		this.setLayout(null);
		jb.addActionListener(this);
		this.add(jl);
		jb.addKeyListener(this);
		this.setSize(250, 150);
		Toolkit tool = this.getToolkit();Image img = tool.getImage("Images/head.jpg");this.setIconImage(img);
		this.setLocation(500, 250);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jb){
			this.setVisible(false);
			//if(i == 1)
				//new Login();
			//if(i == 2)
				//new Typetitle();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyChar() == KeyEvent.VK_ENTER){
			if(e.getSource() == jb){
				this.setVisible(false);
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
