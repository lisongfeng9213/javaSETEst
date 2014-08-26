import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import java.awt.Frame;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatClient
{

	/**
	 * @param args
	 */
	public static int staticNum = 1;
	private int clientNum;
	public ChatClient(){
		ChatClient.staticNum++;
		clientNum = ChatClient.staticNum;		
		System.out.println(clientNum);		
		new MyFrame(clientNum);
	}
	public static void main(String[] args) {
		new ChatClient();
	}
}

class MyFrame extends Frame{
	private int clientNum;
	TextArea taContent = new TextArea();
	TextField tfTxt = new TextField();
	
	Socket socket = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	
	Thread tRecv = new Thread(new RecvThread()); 
	public MyFrame(int clientNum)
	{	
		this.clientNum = clientNum;
		setTitle("CLIENT"+clientNum);
		setLocation(400, 300);
		setSize(500, 500);
		setVisible(true);
		setResizable(false);
		tfTxt.addActionListener(new MyActionListener());
		add(tfTxt,BorderLayout.SOUTH);
		add(taContent,BorderLayout.NORTH);
		pack();
		this.addWindowListener(new MywinClosing());
		connect();
		tRecv.start();
	}
	
	private class MywinClosing extends WindowAdapter
	{
		public void windowClosing(WindowEvent e){
			System.exit(0);
			disconnect();
		}
	}
	
	private class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();
			str = "CLIENT"+clientNum+"Ëµ:"+str;
			String strTa = taContent.getText().trim();
			taContent.setText(strTa+'\n'+str);
			tfTxt.setText("");
			
			try {
				dos.writeUTF(str);
				dos.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	public void connect(){
		try {
			socket = new Socket("127.0.0.1",8888);
			System.out.println("CLIENT IS ON");
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void disconnect(){
		try {
			dos.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class RecvThread implements Runnable {

		public void run() {
			try {
				while(true) {
					String str = dis.readUTF();
					System.out.println(str);
					//System.out.println(str);
					taContent.setText(taContent.getText() +'\n'+ str);
				}
			} catch (SocketException e) {
				System.out.println("ÍË³öÁË£¬bye!");
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		
	}


}


