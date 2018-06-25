package com.netty.webscoket.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class NioDemo {

    public static void main(String args[]) {
        try {
            //步骤一：打开ServerSocketChannel,用于监听客户端的连接，它是所有客户端连接的父管道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //步骤二：绑定监听端口，设置连接为非阻塞模式
            serverSocketChannel.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"), 8888));
            serverSocketChannel.configureBlocking(false);
            //步骤三：创建Reactor线程，创建多路复用器并启动线程
            Selector selector = Selector.open();
//            new Thread(new ReactorTask()).start();
            //步骤四：将erverSocketChannel注册到Reactor线程的多路复用器Selector上，监听accept事件
//            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT,)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
