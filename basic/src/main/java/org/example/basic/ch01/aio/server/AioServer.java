package org.example.basic.ch01.aio.server;

import static org.example.basic.ch01.Ch01Const.DEFAULT_PORT;

/**
 *
 * 类说明：服务器主程序
 */
public class AioServer {
    private static AioServerHandler serverHandle;
    //统计客户端个数
    public volatile static long clientCount = 0;

    public static void start(){
        if(serverHandle!=null)
            return;
        serverHandle = new AioServerHandler(DEFAULT_PORT);
        new Thread(serverHandle,"Server").start();
    }
    public static void main(String[] args){
        AioServer.start();
    }
}
