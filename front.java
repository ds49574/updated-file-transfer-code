import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class front extends JFrame
{
JPanel jp1,jp2;
JButton add;
JLabel l1;
JTextField t1;

public front()
{
super("Enter IP");
setSize(500,150);
setResizable(false);
jp1=new JPanel();
jp1.setLayout(new FlowLayout(FlowLayout.CENTER,10,25));
add=new JButton("CONNECT");
l1=new JLabel("IP:");
t1=new JTextField(20);
jp1.add(l1);
jp1.add(t1);
add(jp1);

jp2=new JPanel();
jp2.setLayout(new FlowLayout(FlowLayout.CENTER,10,25));
jp2.add(add);
add(jp2,BorderLayout.SOUTH);
setLocationRelativeTo(null);
setVisible(true);
addWindowListener(new WindowAdapter()
{
public void windowClosing(WindowEvent we)
{
dispose();
}
});

add.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ae)
{
String id=t1.getText();
TCPClient tcp = new TCPClient("D:\\bluetooth\\",id,3333);
dispose();
t1.setText("");
}
});
}
}