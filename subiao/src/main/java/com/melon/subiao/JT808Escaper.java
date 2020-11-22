package com.melon.subiao;

import java.io.ByteArrayOutputStream;

/**
 * 转义处理
 */
public class JT808Escaper {
    /**
     * 标识位转义码
     */
    public static final int CODE_MSG_FLAG_ESCAPER = 0x7d;
    public static final int CODE_MSG_FLAG_ESCAPER_02 = 0x02;
    public static final int CODE_MSG_FLAG_ESCAPER_01 = 0x01;

    /**
     * 发送消息时转义
     * 0x7e <====> 0x7d02
     *
     * @param bs    要转义的字节数组
     * @param start 起始索引
     * @param end   结束索引
     * @return 转义后的字节数组
     */
    public static byte[] escape(byte[] bs, int start, int end) throws Exception {
        if (start < 0 || end > bs.length)
            throw new ArrayIndexOutOfBoundsException("doEscape4Send error : index out of bounds(start=" + start
                    + ",end=" + end + ",bytes length=" + bs.length + ")");
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            for (int i = 0; i < start; i++) {
                baos.write(bs[i]);
            }
            for (int i = start; i < end; i++) {
                if (bs[i] == Constant.MSG_FLAG_BYTE) {
                    baos.write(CODE_MSG_FLAG_ESCAPER);
                    baos.write(CODE_MSG_FLAG_ESCAPER_02);
                } else if (bs[i] == CODE_MSG_FLAG_ESCAPER) {
                    baos.write(CODE_MSG_FLAG_ESCAPER);
                    baos.write(CODE_MSG_FLAG_ESCAPER_01);
                } else {
                    baos.write(bs[i]);
                }
            }
            for (int i = end; i < bs.length; i++) {
                baos.write(bs[i]);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            if (baos != null) {
                baos.close();
                baos = null;
            }
        }
    }

    /**
     * 反转义
     * @param rawBytes 原始字节
     */
    public static byte[] unescape(byte[] rawBytes) throws Exception {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            for (int i = 0; i < rawBytes.length; i++) {
                if (rawBytes[i] == CODE_MSG_FLAG_ESCAPER && rawBytes[i + 1] == CODE_MSG_FLAG_ESCAPER_01) {
                    //7d 01 反转为7d
                    baos.write(CODE_MSG_FLAG_ESCAPER);
                    i++;
                } else if (rawBytes[i] == CODE_MSG_FLAG_ESCAPER && rawBytes[i + 1] == CODE_MSG_FLAG_ESCAPER_02) {
                    //7d 02 反转为7e
                    baos.write(Constant.MSG_FLAG_BYTE);
                    i++;
                } else {
                    //正常写入
                    baos.write(rawBytes[i]);
                }
            }
            return baos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            if (baos != null) {
                baos.close();
                baos = null;
            }
        }
    }
}
