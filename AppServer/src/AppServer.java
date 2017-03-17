import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class AppServer {
	//private static Log log = LogFactory.getLog(HttpServer.class);  
    
    public static void main(String[] args) throws Exception {  
        AppServer server = new AppServer();  
        System.out.println("����������...");  
        server.start(8082);  
    }  
      
    public void start(int port) throws Exception {  
        //���÷���˵�NIO�߳���  
        EventLoopGroup bossGroup = new NioEventLoopGroup();  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap b = new ServerBootstrap();  
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)  
                    .childHandler(new ChannelInitializer<SocketChannel>() {  
                                @Override  
                                public void initChannel(SocketChannel ch) throws Exception {  
                                	// ����Ĵ���
                                    ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                                    // ��Ҫ�����Ŀ����
                                    ch.pipeline().addLast(new ProtobufDecoder(HotMessage.Message.getDefaultInstance()));

                                    ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());

                                    ch.pipeline().addLast(new ProtobufEncoder());
                                    
                                	ch.pipeline().addLast(new ServerHandler());  
                                }  
                            }).option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  
            //�󶨶˿ڣ�ͬ���ȴ��ɹ�  
            ChannelFuture f = b.bind(port).sync();  
            //�ȴ�����˼����˿ڹر�  
            f.channel().closeFuture().sync();  
        } finally {  
            //�����˳����ͷ��̳߳���Դ  
            workerGroup.shutdownGracefully();  
            bossGroup.shutdownGracefully();  
        }  
    }  
}
