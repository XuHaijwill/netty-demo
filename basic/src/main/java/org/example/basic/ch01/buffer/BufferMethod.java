package org.example.basic.ch01.buffer;

import java.nio.ByteBuffer;

/**
 *
 * 类说明：
 */
public class BufferMethod {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(20);
        System.out.println("buffer = " + buffer);
        System.out.println("--------Test reset----------");


        System.out.println("--------Test rewind--------");


        System.out.println("--------Test compact--------");


        System.out.println("------Test get-------------");


        System.out.println("--------Test put-------");

    }
}
