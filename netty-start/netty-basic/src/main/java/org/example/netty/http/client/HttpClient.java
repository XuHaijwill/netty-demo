package org.example.netty.http.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.example.netty.http.server.HttpServer;

import java.net.URI;

public class HttpClient {
    private static final boolean SSL = false;
    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                    ch.pipeline().addLast(new HttpClientCodec());
                    ch.pipeline().addLast("aggre",
                            new HttpObjectAggregator(10*1024*1024));
                    ch.pipeline().addLast("decompressor",new HttpContentDecompressor());
                    ch.pipeline().addLast("busi",new HttpClientInboundHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            URI uri = new URI("/test");
            String msg = "Hello";
            DefaultFullHttpRequest request =
                    new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
                    HttpMethod.GET,
                    uri.toASCIIString(),
                    Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

            // 构建http请求
            request.headers().set(HttpHeaderNames.HOST, host);
            request.headers()
                    .set(HttpHeaderNames.CONNECTION,
                            HttpHeaderValues.KEEP_ALIVE);
            request.headers()
                    .set(HttpHeaderNames.CONTENT_LENGTH,
                            request.content().readableBytes());
            // 发送http请求
            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();
        client.connect("127.0.0.1", HttpServer.port);
    }
}
