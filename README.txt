For run server from console, you need to add JDBC library in CLASSPAT and execute Server.java with parameters PORT, dbUrl, jdbcDriver, login, pass.
For example: Server.java 6666 jdbc:mysql://localhost/library com.mysql.jdbc.Driver myLogin myPass.

For run client use the following command: Client.java.

If You want to write your client application, please use the next command:
1. countOfRows:countOfRows - count of rows that server will response for you. At first connect set count of rows. By default countOfRows = 0; 
2. disconnect: - disconnect from server;
3. executeSql: - execute your SQL and get response JSON.
4. prev: - get you the previous page with resultSet from Statment.
5. next: - get you the next page with resultSet from Statment.