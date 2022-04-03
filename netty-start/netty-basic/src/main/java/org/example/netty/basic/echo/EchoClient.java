package org.example.netty.basic.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 类说明：netty的客户端
 */
public class EchoClient {

    private final int port;
    private final String host;


    public EchoClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void start() throws InterruptedException {
        /*线程组*/
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap(); /*客户端启动必备*/
        bootstrap.group(group)
                .channel(NioSocketChannel.class)/*指明使用NIO进行网络通讯*/
                .remoteAddress(new InetSocketAddress(host,port))/*配置远程服务器的地址*/
                .handler(new EchoClientHandler());
        ChannelFuture f = bootstrap.connect().sync();/*连接到远程节点，阻塞等待直到连接完成*/
        /*阻塞，直到channel关闭*/
        try {
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient(9999,"127.0.0.1").start();
    }
}
