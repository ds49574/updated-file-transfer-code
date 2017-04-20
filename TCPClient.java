import java.io.*;
import java.net.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;

public class TCPClient extends JFrame implements ActionListener, MouseListener {
	JPanel panel;
	JLabel title, subT, msg, error, servFiles;
	Font font,labelfont,font1;
	JTextField txt;
	JButton down,start;
	String dirName;
	Socket clientSocket;
	InputStream inFromServer;
	OutputStream outToServer;
	BufferedInputStream bis;
	PrintWriter pw;
	String name, file, path;
	String hostAddr;
	int portNumber;
	int c;
	int size = 9022386;
	JList<String> filelist;
	String[] names = new String[10000];
        public static String xv="";
	int len; // number of files on the server retrieved

	public TCPClient(String dir,String host,int port) {
		super("TCP CLIENT");
                setVisible(true);
                setSize(800,800);
                setResizable(false);
		// set dirName to the one that's entered by the user
		dirName = dir;
                xv=host;
		// set hostAddr to the one that's passed by the user
               
		hostAddr = host;

		// set portNumber to the one that's passed by the user
		portNumber = port;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel(null);

		font = new Font("Roboto", Font.BOLD, 60);
		title = new JLabel("TCP CLIENT");
		title.setFont(font);
		title.setBounds(300, 50, 400, 50);
		panel.add(title);

		labelfont = new Font("Roboto", Font.PLAIN, 20);
		subT = new JLabel("Enter File Name :");
		subT.setFont(labelfont);
		subT.setBounds(100, 450, 200, 50);
		panel.add(subT);
                
		txt = new JTextField();
		txt.setBounds(400, 450, 800, 40);
		panel.add(txt);

		down = new JButton("Download");
		down.setBounds(550, 550, 200, 50);
		panel.add(down);
              
                start = new JButton("Click here");
		start.setBounds(250, 550, 200, 50);
		panel.add(start);

		error = new JLabel("");
		error.setFont(labelfont);
		error.setBounds(200, 650, 600, 50);
		panel.add(error);


                down.addActionListener(this);
                start.addActionListener(this);

		try {
			clientSocket = new Socket(hostAddr, portNumber);
			inFromServer = clientSocket.getInputStream();
			pw = new PrintWriter(clientSocket.getOutputStream(), true);
			outToServer = clientSocket.getOutputStream();
			ObjectInputStream oin = new ObjectInputStream(inFromServer);
			String s = (String) oin.readObject();
			System.out.println(s+"hi");

			len = Integer.parseInt((String) oin.readObject());
			System.out.println(len);

			String[] temp_names = new String[len];

			for(int i = 0; i < len; i++) {
				String filename = (String) oin.readObject();
				System.out.println(filename);
				names[i] = filename;
				temp_names[i] = filename;
			}

			// sort the array of strings that's going to get displayed in the scrollpane
			Arrays.sort(temp_names);

			servFiles = new JLabel("Files in the Server Directory :");
			servFiles.setBounds(350, 125, 400, 50);
			panel.add(servFiles);

			filelist = new JList<>(temp_names);
			JScrollPane scroll = new JScrollPane(filelist);
			scroll.setBounds(300, 200, 400, 200);

			panel.add(scroll);
			filelist.addMouseListener(this);

		} 
		catch (Exception exc) {
			System.out.println("Exception: " + exc.getMessage());
			error.setText("Exception:" + exc.getMessage());
			error.setBounds(300,125,600,50);
			panel.revalidate();
		}

		getContentPane().add(panel);
	}

    public void mouseClicked(MouseEvent click) {
        if (click.getClickCount() == 2) {
           String selectedItem = (String) filelist.getSelectedValue();
           txt.setText(selectedItem);
           panel.revalidate();
         }
    }

    public void mousePressed(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

	public void actionPerformed(ActionEvent event) {
		 if (event.getSource() == down) {
			try {
String s="";
				File directory = new File(dirName);

				if (!directory.exists()) {
					directory.mkdir();
				}

				boolean complete = true;
				byte[] data = new byte[size];
				name = txt.getText();
				file = new String("*" + name + "*");
				pw.println(file); //lets the server know which file is to be downloaded

				ObjectInputStream oin = new ObjectInputStream(inFromServer);
				s = (String) oin.readObject();

				if(s.equals("Success")) {
					File f = new File(directory, name);
					FileOutputStream fileOut = new FileOutputStream(f);
					DataOutputStream dataOut = new DataOutputStream(fileOut);

					//empty file case
					while (complete) {
						c = inFromServer.read(data, 0, data.length);
						if (c == -1) {
							complete = false;
							System.out.println("Completed");
							error.setText("Completed");
							panel.revalidate();

						} else {
							dataOut.write(data, 0, c);
							dataOut.flush();
						}
					}
					fileOut.close();
				}
				else {
					System.out.println("Requested file not found on the server.");
					error.setText("Requested file not found on the server.");
					panel.revalidate();
				}
			} 
			catch (Exception exc) {
				System.out.println("Exception: " + exc.getMessage());
				error.setText("Exception:" + exc.getMessage());
				panel.revalidate();
			}
		}
            if (event.getSource() == start)
            {
              dispose();
              new TCPClient("D:\\bluetooth\\",xv,3333);
            }
	}

	private static void sendBytes(BufferedInputStream in , OutputStream out) throws Exception {
		int size = 9022386;
		byte[] data = new byte[size];
		int bytes = 0;
		int c = in.read(data, 0, data.length);
		out.write(data, 0, c);
		out.flush();
	}

}