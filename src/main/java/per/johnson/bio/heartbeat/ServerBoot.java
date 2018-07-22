package per.johnson.bio.singlethread;

/**
 * Created by Johnson on 2018/7/21.
 */
public class ServerBoot {
    public static void main(String[] args) {
        Server server = new Server(7777,8);
        server.start();
    }
}
