import java.awt.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

	ArrayList<Client> clients = new ArrayList<Client>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChatServer().run();

	}

	public void run() {
		Socket s = null;
		DataInputStream dis = null;

		try {
			ServerSocket ss = new ServerSocket(8888);
			while (true) {
				s = ss.accept();
				System.out.println("A CONNETED IS BUILDED");
				Client c = new Client(s);
				clients.add(c);
				// System.out.println(clients.size());
				new Thread(c).start();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class Client implements Runnable {
		private Socket s;
		private DataInputStream dis;
		private DataOutputStream dos;

		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void send(String str) {
			try {
				dos.writeUTF(str);
				System.out.println(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			while(true){
				try {
					String str = dis.readUTF();
					System.out.println(str);
					for(int i=0; i<clients.size(); i++) {
						Client c = clients.get(i);
						if(c != this)
						{
							c.send(str);
						}
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					try {
						dis.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
			
		}
	}

}
