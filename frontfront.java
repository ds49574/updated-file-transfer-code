import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class frontfront extends JFrame
{
JPanel jp;
JButton modify;
public frontfront()
{
super("FILE TRANSFER");
setSize(500,150);
setResizable(false);
setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
jp=new JPanel();
jp.setLayout(new FlowLayout(FlowLayout.CENTER,10,25));
modify=new JButton("RECEIVE");
jp.add(modify);
add(jp);
setLocationRelativeTo(null);
setVisible(true);
addWindowListener(new WindowAdapter()
{
public void windowClosing(WindowEvent e)
{
int output=JOptionPane.showConfirmDialog(null,"Do You Want To Exit");
if(output==JOptionPane.YES_OPTION)
{
 System.exit(1);
}}});

modify.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ae)
{
front a=new front();
dispose();
}});
}
public static void main(String args[])
{
frontfront h=new frontfront();
}
}