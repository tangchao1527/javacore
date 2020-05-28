package com._520it.base.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

/*
 * 一、通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 *
 * 二、通道的主要实现类
 * 	java.nio.channels.Channel 接口：
 * 		|--FileChannel
 * 		|--SocketChannel
 * 		|--ServerSocketChannel
 * 		|--DatagramChannel
 *
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 * 		本地 IO：
 * 		FileInputStream/FileOutputStream
 * 		RandomAccessFile
 *
 * 		网络IO：
 * 		Socket
 * 		ServerSocket
 * 		DatagramSocket
 *
 * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 *
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 *
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 *
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组  -> 字符串
 *
 */
public class TestChannel {

    public static void main(String[] args) {

       // testOne();
    }
    //字符集
    @Test
    public void test6() throws IOException{
        Charset cs1 = Charset.forName("GBK");

        //获取编码器
        CharsetEncoder ce = cs1.newEncoder();

        //获取解码器
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("尚硅谷威武！");
        cBuf.flip();

        //编码
        ByteBuffer bBuf = ce.encode(cBuf);

        for (int i = 0; i < 12; i++) {
            System.out.println(bBuf.get());
        }

        //解码
        bBuf.flip();
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());

        System.out.println("------------------------------------------------------");

        Charset cs2 = Charset.forName("GBK");
        bBuf.flip();
        CharBuffer cBuf3 = cs2.decode(bBuf);
        System.out.println(cBuf3.toString());
    }

    @Test
    public void test5(){
        Map<String, Charset> map = Charset.availableCharsets();

        Set<Map.Entry<String, Charset>> set = map.entrySet();

        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    /**
     * 分散与聚集
     */
    @Test
    public void testFour() throws Exception {

        long start = System.currentTimeMillis();
        RandomAccessFile raf = new RandomAccessFile("G:\\临时文件\\01.mp4","rw");

        FileChannel channel = raf.getChannel();

        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate((int)raf.length());
        ByteBuffer[] buffers = {buf1, buf2};
        channel.read(buffers);

        for (ByteBuffer buffer:buffers) {
            buffer.flip();
        }

        //聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("G:\\临时文件\\02.mp4", "rw");
        FileChannel channel2 = raf2.getChannel();
        channel2.write(buffers);
        System.out.println(System.currentTimeMillis() - start);
    }
    //通道之间的数据传输(直接缓冲区)
    @Test
    public void testThree() throws IOException{
        long start = System.currentTimeMillis();
        FileChannel inChannel = FileChannel.open(Paths.get("G:\\临时文件\\01.mp4"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("G:\\临时文件\\02.mp4"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

		inChannel.transferTo(0, inChannel.size(), outChannel);
      //  (inChannel, 0, inChannel.size());

        System.out.println(System.currentTimeMillis() - start);
        inChannel.close();
        outChannel.close();
    }

    /**
     * 使用直接缓冲区完成文件的复制(内存映射文件)
     */
    @Test
    public  void testTwo() throws Exception{
        long start = System.currentTimeMillis();

        FileChannel readChannel = FileChannel.open(Paths.get("G:\\临时文件\\01.mp4"), StandardOpenOption.READ);
        FileChannel writeChannel = FileChannel.open(Paths.get("G:\\临时文件\\03.mp4"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        MappedByteBuffer readMap = readChannel.map(FileChannel.MapMode.READ_ONLY, 0, readChannel.size());
        MappedByteBuffer writeMap = writeChannel.map(FileChannel.MapMode.READ_WRITE, 0, readChannel.size());
        byte[] dst = new byte[readMap.limit()];

        readMap.get(dst);
        writeMap.put(dst);
        writeChannel.close();
        readChannel.close();
        System.out.println(System.currentTimeMillis() - start);
    }
    /**
     * 利用通道完成文件的复制（非直接缓冲区）
     */
    @Test
    public  void testOne() {
        long start = System.currentTimeMillis();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fisChannel = null;
        FileChannel fosChannel = null;

        try {
             fis = new FileInputStream("G:\\临时文件\\01.mp4");
             fos = new FileOutputStream("G:\\临时文件\\02.mp4");

            //②分配指定大小的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            fisChannel = fis.getChannel();
            fosChannel = fos.getChannel();

            //③将通道中的数据存入缓冲区中
            while (fisChannel.read(buffer) != -1){
                //切换到读取模式
                buffer.flip();
                //④将缓冲区中的数据写入通道中
                fosChannel.write(buffer);
                //清空缓冲区
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fosChannel != null){
                try {
                    fosChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fisChannel != null) {
                try {
                    fisChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(System.currentTimeMillis() - start);

    }
}
