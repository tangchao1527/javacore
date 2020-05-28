package com._520it.base.nio;


import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestBlockingNIO2 {

    /**
     * 客户端
     */
    @Test
    public void client() throws Exception{
        SocketChannel schannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));

        FileChannel fileChannel = FileChannel.open(Paths.get("G:\\个人工具\\linyuer3.jpg"), StandardOpenOption.READ);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (fileChannel.read(buffer) != -1){
            buffer.flip();
            schannel.write(buffer);
            buffer.clear();
        }

        schannel.shutdownOutput();

        //接收服务端的反馈
        int len = 0;
        while ((len = schannel.read(buffer)) != -1){
            buffer.flip();
            System.out.println(new String(buffer.array(),0,len));
            buffer.clear();
        }

        schannel.close();
        fileChannel.close();

    }

    /**
     * 服务端
     */
    @Test
    public void server() throws IOException {
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        FileChannel fileChannel = FileChannel.open(Paths.get("G:\\个人工具\\linyuer1.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        ssChannel.bind(new InetSocketAddress(9898));
        SocketChannel sc = ssChannel.accept();

        ByteBuffer buf = ByteBuffer.allocate(1024);

        while (sc.read(buf) != -1){
            buf.flip();
            fileChannel.write(buf);
            buf.clear();
        }

        //发送反馈给客户端
        buf.put("服务端收到消息".getBytes());

        buf.flip();
        sc.write(buf);

        sc.close();
        fileChannel.close();
        ssChannel.close();


    }
}
