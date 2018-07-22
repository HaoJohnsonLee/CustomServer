package per.johnson.bio.singlethread;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Johnson on 2018/7/21.
 */
public class Client {
    private Socket socket;
    private String host;
    private int port;
    private boolean isInit;
    static final int BUF_SIZE = 1024;
    private static final long DEFAULT_HEARTBEAT_TIME = 2000;
    public Client(String host,int port){
        this.host = host;
        this.port = port;
    }
    public void init(){
        try {
            socket = new Socket(host,port);
            isInit = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void heartbeat(String msg,long time) throws IOException, InterruptedException {
        String t = msg;
        if(isInit) {
            while (true) {
                socket.getOutputStream().write(msg.getBytes());
                Thread.sleep(time);
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client("localhost", 7777);
        client.init();
        client.heartbeat("我是心跳包内容",DEFAULT_HEARTBEAT_TIME);
    }
}
