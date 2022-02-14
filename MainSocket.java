import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;


public class MainSocket {

	private static int port;
	
	private static void execute() {

		BufferedReader reader, input;
		PrintStream writer;

		try (ServerSocket server = new ServerSocket(port)) {
			System.out.println("SERVER LISTENING ON PORT : " + port);
			
			while (true) {
				Socket socket = server.accept();
				System.out.println("CONNECTED TO  " + socket.getInetAddress() + " AT PORT " + socket.getPort());
			
				String messageFromClient = "";
				reader = new BufferedReader( new InputStreamReader(socket.getInputStream()));
				writer = new PrintStream(socket.getOutputStream());

				input = new BufferedReader( new InputStreamReader(
System.in));	
				
				messageFromClient = reader.readLine();
				
				while (!messageFromClient.equalsIgnoreCase("goodbye")) {	
					System.out.println("[ CLIENT ] : " + messageFromClient);
					
					String myMessage = input.readLine();
					writer.println(myMessage);
					writer.flush();

					messageFromClient = reader.readLine();

				}
			
				socket.close();
				reader.close();
				writer.close();
				input.close();
			}

		}

		catch (IOException ioException ) {
			System.out.println("CONNECTION ERROR WITH SERVER ");
			ioException.printStackTrace();
		}

	}

	public static void main(String [] args) {
		
		if (args.length < 1) {
			System.out.println("SERVER PORT NOT FOUND <PORT_NUMBER>");
			System.exit(0);

		}

		port  = Integer.parseInt(args[0]);
		execute();	
	}

}

			
