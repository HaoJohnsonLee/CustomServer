package per.johnson.bio.singlethread;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Johnson on 2018/7/21.
 */
public class MessageHandler implements Runnable{
    private Socket socket;
    private static final int BUF_SIZE = 1024;
    public MessageHandler(Socket socket){
        this.socket = socket;
    }

    private void doHandle(){
        try{
            System.out.println("当前处理线程: "+ Thread.currentThread().getName());
            InputStream is = socket.getInputStream();
            while (true) {
                byte[] data = new byte[BUF_SIZE];
                int len;
                while ((len = is.read(data)) != -1) {
                    String msg = new String(data, 0, len);
                    System.out.println("收到消息 " + msg+" From "+ Thread.currentThread().getName());
                    socket.getOutputStream().write(msg.getBytes());
                    if(msg.equals("bye")) return;
                }
            }
        }catch (IOException ignored) {
        }finally {
            System.out.println("-------------客户端退出---------------");
        }
    }

    @Override
    public void run() {
        doHandle();
    }
}
