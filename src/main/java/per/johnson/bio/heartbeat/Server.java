package per.johnson.bio.singlethread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Johnson on 2018/7/21.
 */
public class Server {
    private ServerSocket serverSocket;
    private int port;
    private ThreadPoolExecutor executor;
    private int coreThreads;
    private boolean isInit;
    public Server(int port,int coreThreads){
        this.port = port;
        this.coreThreads = coreThreads;
    }
    public void init(int keepAliveTime){
        try {
            serverSocket = new ServerSocket(port);
            executor = new ThreadPoolExecutor(coreThreads,Integer.MAX_VALUE,keepAliveTime,TimeUnit.MINUTES,new LinkedBlockingDeque<>(5));
            isInit = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("服务器开启成功---");
        System.out.println("监听端口: "+ this.port);
        System.out.println("Core Threads number: "+ coreThreads);
    }
    public void start(){
        init(5);
        while(isInit){
            try {
                Socket socket = serverSocket.accept();
                executor.execute(new MessageHandler(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
