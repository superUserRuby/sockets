import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;


public class Client {
		
	private static int port;
	
	private static void connect() {
		
		BufferedReader reader, input;
		PrintStream writer;

		try(Socket socket = new Socket("127.0.0.1", port)) {
			
			System.out.println("CONNECTED TO SERVER AT : " + socket.getPort() + " WITH IP : " + socket.getInetAddress());

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			input = new BufferedReader(new InputStreamReader(System.in));
			writer = new PrintStream(socket.getOutputStream());

			String sendMessage = input.readLine();
			while (!sendMessage.equalsIgnoreCase("goodbye")) {
				writer.println(sendMessage);
				writer.flush();

				String recvMessage = reader.readLine();

				System.out.println("[ SERVER ]: " + recvMessage);
				sendMessage = input.readLine();
			}

			input.close();
			reader.close();
			writer.close();

		}

		catch (IOException ioException ) {
			System.out.println("ERROR WITH IO OPERATION IN CLIENT SIDE");
			ioException.printStackTrace();
		}

	}


	public static void main(String [] args) {
		if (args.length < 1) {
			System.out.println("CANNOT CONNECT TO SERVER <PORT_NUMBER> MISSING");
			System.exit(0);

		}

		port = Integer.parseInt(args[0]);
		connect();
	}

}


