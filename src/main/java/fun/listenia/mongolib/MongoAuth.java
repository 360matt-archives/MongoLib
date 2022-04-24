package fun.listenia.mongolib;

public class MongoAuth {

    private String user;
    private String password;
    private String database;
    private String host;
    private int port;

    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }
    public String getDatabase() {
        return database;
    }
    public String getHost() {
        return host;
    }
    public int getPort() {
        return port;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setDatabase(String database) {
        this.database = database;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public String getConnectionString() {
        return String.format("mongodb://%s:%s@%s:%d/%s", user, password, host, port, database);
    }
}
