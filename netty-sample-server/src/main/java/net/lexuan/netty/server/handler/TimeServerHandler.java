package net.lexuan.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        final ByteBuf time = ctx.alloc().buffer(17); // (2)
        time.writeBytes(new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()).getBytes());

        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        // f.addListener(new ChannelFutureListener() {
        // @Override
        // public void operationComplete(ChannelFuture future) {
        // ctx.close();
        // }
        // }); // (4)

        f.addListener(ChannelFutureListener.CLOSE);// 等效于上面注释的内容
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
