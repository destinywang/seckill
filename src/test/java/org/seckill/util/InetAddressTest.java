package org.seckill.util;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * designed by destiny
 *
 * @author destiny
 *         e-mail destinywk@163.com
 *         github https://github.com/DestinyWang
 *         oschina https://git.oschina.net/destinywk
 * @version JDK 1.8.0_101
 * @since 2017/3/11 10:52
 */
public class InetAddressTest {
    @Test
    public void testInetAddress() {
        InetAddress address = null;
        // 获取本机的InetAddress实例
        try{
            address = InetAddress.getLocalHost();
        } catch(UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("计算机名：" + address.getHostName());
        System.out.println("IP地址：" + address.getHostAddress());
        // 获取字节数组形式的IP地址
        byte[] bytes = address.getAddress();
        System.out.println("字节数组形式的IP：" + Arrays.toString(bytes));
        // 直接输出address对象
        System.out.println("直接输出IP地址：" + address);
    }
}
