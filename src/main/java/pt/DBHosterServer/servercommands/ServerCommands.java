package pt.DBHosterServer.servercommands;

public enum ServerCommands {
	DISCONNECT_COMMAND("disconnect:"), COUNT_OF_ROWS_COMMAND("countOfRows:"), EXECUTE_SQL_COMMAND(
			"executeSql:"), PREV_COMMAND("prev:"), NEXT_COMMAND("next:");

	private String command;

	ServerCommands(String command) {
		this.command = command;
	}

	public String command() {
		return command;
	}
}
