package pt.DBHosterServer;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		try {
			int port = Integer.parseInt(args[0]);
			System.out.println("Port: " + port);

			ServerSocket s = new ServerSocket(port);
			System.out.println("Server Started");
			try {
				while (true) {
					Socket socket = s.accept();
					try {
						new ServerThread(args, socket);
					} catch (IOException e) {
						// Close socket if we have exception
						socket.close();
					}
				}
			} finally {
				s.close();
				System.out.println("Server socket connection is closed");
			}

		} catch (NumberFormatException e) {
			System.err.println(
					"Please, check your console parameters. " + "Can not parse String to int " + e.getMessage());
		} catch (BindException e) {
			System.err.println("This port is alredy used. " + e.getLocalizedMessage());
		}
	}

}
