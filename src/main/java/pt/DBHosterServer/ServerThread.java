package pt.DBHosterServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import pt.DBHosterServer.servercommands.DisconnectCommand;
import pt.DBHosterServer.servercommands.ExecuteSqlCommand;
import pt.DBHosterServer.servercommands.PrevSqlCommand;
import pt.DBHosterServer.servercommands.NextSqlCommand;
import pt.DBHosterServer.servercommands.ClientResponse;
import pt.DBHosterServer.servercommands.ClientResponseInvoker;
import pt.DBHosterServer.servercommands.CountOfRowsCommand;
import pt.DBHosterServer.servercommands.ClientResponseUtil;
import pt.DBHosterServer.servercommands.ServerCommands;

public class ServerThread extends Thread {
	private Socket clientSocket = null;
	private Connection conn = null;
	private Response serverResponse = null;
	private String sql = null;
	private BufferedReader in = null;
	private PrintWriter out = null;

	public ServerThread(String[] args, Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		createConnection(args);
		start();
	}

	private void createConnection(String[] args) {
		String dbUrl = args[1];
		String jdbcDriver = args[2];
		String login = args[3];
		String pass = args[4];

		System.out.println("dbUrl: " + dbUrl);
		System.out.println("jdbcDriver: " + jdbcDriver);
		System.out.println("login: " + login);
		System.out.println("pass: " + pass);

		DBConnection dbConn = new DBConnection(jdbcDriver, dbUrl, login, pass);
		System.out.println("DB connection started");
		this.conn = dbConn.createConnect();

	}

	public void run() {
		String inputLine = null;

		try {
			// Create Input stream for socket
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			// Create Output stream for socket
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
			// Create Server Response
			serverResponse = new Response(conn);
			ClientResponse clientResponse = ClientResponseUtil.getClientResponse();
			
			while (true) {
				// Whit input line from client
				inputLine = in.readLine();
				doCommand(clientResponse, inputLine);
			}
		} catch (IOException e) {
			System.err.println("IO Exception");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (ServerException e) {
			System.out.println("closing...");
			System.out.println(e.getMessage());
		} finally {
			try {
				serverResponse.closeStatment();
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (conn != null) {
					conn.close();
					System.out.println("Db connection is closed");
				}
				if (clientSocket != null) {
					clientSocket.close();
				}
			} catch (SQLException e1) {
				System.err.println("Sql exception: " + e1.getMessage());
			}
			catch (IOException e) {
				System.err.println("IO Exception" + e.getMessage());
			}
		}
		
	}
	
	// choose and do command from client
	private void doCommand(ClientResponse clientResponse, String inputLine)
			throws JsonGenerationException, JsonMappingException, IOException, ServerException {
		if (inputLine != null) {
			System.out.println("inputLine: " + inputLine);
			ClientResponseInvoker clientResponseInvoker = null;
	
			if (inputLine.trim().startsWith(ServerCommands.DISCONNECT_COMMAND.command())) {
				DisconnectCommand disconnectCommand = new DisconnectCommand(clientResponse, out);
				clientResponseInvoker = new ClientResponseInvoker(disconnectCommand);
				clientResponseInvoker.execute();
				throw new ServerException("disconnect");
			} else if (inputLine.trim().startsWith(ServerCommands.COUNT_OF_ROWS_COMMAND.command())) {
				CountOfRowsCommand countOfRowsCommand = new CountOfRowsCommand(clientResponse, inputLine);
				clientResponseInvoker = new ClientResponseInvoker(countOfRowsCommand);
				clientResponseInvoker.execute();
			} else if (inputLine.trim().startsWith(ServerCommands.EXECUTE_SQL_COMMAND.command())) {
				sql = inputLine.substring(inputLine.indexOf(":") + 1);
				ExecuteSqlCommand executeSqlCommand = new ExecuteSqlCommand(clientResponse, out, serverResponse, sql);
				clientResponseInvoker = new ClientResponseInvoker(executeSqlCommand);
				clientResponseInvoker.execute();
			} else if (inputLine.trim().startsWith(ServerCommands.PREV_COMMAND.command())) {
				PrevSqlCommand prevSqlCommand = new PrevSqlCommand(clientResponse, out, serverResponse/*, sql*/);
				clientResponseInvoker = new ClientResponseInvoker(prevSqlCommand);
				clientResponseInvoker.execute();
			} else if (inputLine.trim().startsWith(ServerCommands.NEXT_COMMAND.command())) {
				NextSqlCommand nextSqlCommand = new NextSqlCommand(clientResponse, out, serverResponse/*, sql*/);
				clientResponseInvoker = new ClientResponseInvoker(nextSqlCommand);
				clientResponseInvoker.execute();
			} else {
				out.println(serverResponse.getErrorResponse("Server have not with command: " + inputLine));
				System.out.println("Server have not with command: " + inputLine);
			}
		} else {
			// send error to client if input line is null
			out.println(
					serverResponse.getErrorResponse("Input line is null. Please check input line from client side"));
			System.out.println("Input line is null. Please check input line from client side");
		}
	}

}
