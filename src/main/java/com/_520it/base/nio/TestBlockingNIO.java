package com._520it.base.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
 * 一、使用 NIO 完成网络通信的三个核心：
 *
 * 1. 通道（Channel）：负责连接
 *
 * 	   java.nio.channels.Channel 接口：
 * 			|--SelectableChannel
 * 				|--SocketChannel
 * 				|--ServerSocketChannel
 * 				|--DatagramChannel
 *
 * 				|--Pipe.SinkChannel
 * 				|--Pipe.SourceChannel
 *
 * 2. 缓冲区（Buffer）：负责数据的存取
 *
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 *
 */
public class TestBlockingNIO {


    /**
     * 客户端
     */
    @Test
    public void client() throws Exception {

        // 1 获取通道
        SocketChannel cchanel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        FileChannel fileChannel = FileChannel.open(Paths.get("G:\\个人工具\\linyuer3.jpg"), StandardOpenOption.READ);

        // 2 指定分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //3. 读取本地文件，并发送到服务端
        while(fileChannel.read(buffer) != -1){
             buffer.flip();
             cchanel.write(buffer);
             buffer.clear();
        }

        //4. 关闭通道
        fileChannel.close();
        cchanel.close();

    }

    /**
     * 服务端
     */
    @Test
    public void server() throws Exception {
        // 1 获取通道
        ServerSocketChannel sschanel = ServerSocketChannel.open();

        FileChannel fileChanel = FileChannel.open(Paths.get("G:\\个人工具\\linyuer2.jpg"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        //2 绑定连接

        sschanel.bind(new InetSocketAddress(9898));

        //3 获取客户端连接通道
        SocketChannel sc = sschanel.accept();

        //4. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        while (sc.read(buf) != -1){
            buf.flip();
            fileChanel.write(buf);
            buf.clear();
        }

        //5. 接收客户端的数据，并保存到本地
        sc.close();
        fileChanel.close();
        sschanel.close();
    }

}
