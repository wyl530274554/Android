package com.melon.subiao;

import java.util.Arrays;
import java.util.List;

public class BitOperator {

    public static final int BYTE = 1;
    public static final int WORD = 2;
    public static final int DWORD = 4;
    public static final int BYTE_5 = 5;
    public static final int BYTE_7 = 7;
    public static final int BYTE_8 = 8;
    public static final int BYTE_20 = 20;

    public static byte[] numToByteArray(long num, int size) {
        byte[] result = new byte[size];
        for (int i = 0; i < size; i++) {
            result[i] = (byte) ((num >> (size - i - 1) * 8) & 0xFF);
        }
        return result;
    }

    /**
     * 把一个整形该为byte
     */
    public byte integerTo1Byte(int value) {
        return (byte) (value & 0xFF);
    }

    /**
     * 把一个整形该为1位的byte数组
     */
    public byte[] integerTo1Bytes(int value) {
        byte[] result = new byte[1];
        result[0] = (byte) (value & 0xFF);
        return result;
    }


    /**
     * 把byte[]转化位整形,通常为指令用
     */
    public static int byteToInteger(byte[] value) {
        int result;
        if (value.length == 1) {
            result = oneByteToInteger(value[0]);
        } else if (value.length == 2) {
            result = twoBytesToInteger(value);
        } else if (value.length == 3) {
            result = threeBytesToInteger(value);
        } else if (value.length == 4) {
            result = fourBytesToInteger(value);
        } else {
            result = fourBytesToInteger(value);
        }
        return result;
    }

    /**
     * 把一个byte转化位整形,通常为指令用
     */
    public static int oneByteToInteger(byte value) {
        return (int) value & 0xFF;
    }

    /**
     * 把一个2位的数组转化位整形
     */
    public static int twoBytesToInteger(byte[] value) {
        // if (value.length < 2) {
        // throw new Exception("Byte array too short!");
        // }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        return ((temp0 << 8) + temp1);
    }

    /**
     * 把一个3位的数组转化位整形
     */
    public static int threeBytesToInteger(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        return ((temp0 << 16) + (temp1 << 8) + temp2);
    }

    /**
     * 把一个4位的数组转化位整形,通常为指令用
     */
    public static int fourBytesToInteger(byte[] value) {
        // if (value.length < 4) {
        // throw new Exception("Byte array too short!");
        // }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return ((temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    /**
     * 把一个4位的数组转化位整形
     */
    public long fourBytesToLong(byte[] value) throws Exception {
        // if (value.length < 4) {
        // throw new Exception("Byte array too short!");
        // }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return (((long) temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    /**
     * 把一个数组转化长整形
     */
    public static long bytes2Long(byte[] value) {
        long result = 0;
        int len = value.length;
        int temp;
        for (int i = 0; i < len; i++) {
            temp = (len - 1 - i) * 8;
            if (temp == 0) {
                result += (value[i] & 0x0ff);
            } else {
                result += (value[i] & 0x0ff) << temp;
            }
        }
        return result;
    }

    /**
     * 把一个长整形改为byte数组
     */
    public byte[] longToBytes(long value) {
        return longToBytes(value, 8);
    }

    /**
     * 把一个长整形改为byte数组
     */
    public byte[] longToBytes(long value, int len) {
        byte[] result = new byte[len];
        int temp;
        for (int i = 0; i < len; i++) {
            temp = (len - 1 - i) * 8;
            if (temp == 0) {
                result[i] += (value & 0x0ff);
            } else {
                result[i] += (value >>> temp) & 0x0ff;
            }
        }
        return result;
    }


    /**
     * 把IP拆分位int数组
     */
    public int[] getIntIPValue(String ip) throws Exception {
        String[] sip = ip.split("[.]");
        // if (sip.length != 4) {
        // throw new Exception("error IPAddress");
        // }
        int[] intIP = {Integer.parseInt(sip[0]), Integer.parseInt(sip[1]), Integer.parseInt(sip[2]),
                Integer.parseInt(sip[3])};
        return intIP;
    }

    /**
     * 把byte类型IP地址转化位字符串
     */
    public String getStringIPValue(byte[] address) throws Exception {
        int first = this.oneByteToInteger(address[0]);
        int second = this.oneByteToInteger(address[1]);
        int third = this.oneByteToInteger(address[2]);
        int fourth = this.oneByteToInteger(address[3]);

        return first + "." + second + "." + third + "." + fourth;
    }

    /**
     * 合并字节数组
     */
    public static byte[] concatAll(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            if (array != null) {
                totalLength += array.length;
            }
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            if (array != null) {
                System.arraycopy(array, 0, result, offset, array.length);
                offset += array.length;
            }
        }
        return result;
    }

    /**
     * 合并字节数组
     */
    public byte[] concatAll(List<byte[]> rest) {
        int totalLength = 0;
        for (byte[] array : rest) {
            if (array != null) {
                totalLength += array.length;
            }
        }
        byte[] result = new byte[totalLength];
        int offset = 0;
        for (byte[] array : rest) {
            if (array != null) {
                System.arraycopy(array, 0, result, offset, array.length);
                offset += array.length;
            }
        }
        return result;
    }

    public float byte2Float(byte[] bs) {
        return Float.intBitsToFloat(
                (((bs[3] & 0xFF) << 24) + ((bs[2] & 0xFF) << 16) + ((bs[1] & 0xFF) << 8) + (bs[0] & 0xFF)));
    }

    public float byteBE2Float(byte[] bytes) {
        int l;
        l = bytes[0];
        l &= 0xff;
        l |= ((long) bytes[1] << 8);
        l &= 0xffff;
        l |= ((long) bytes[2] << 16);
        l &= 0xffffff;
        l |= ((long) bytes[3] << 24);
        return Float.intBitsToFloat(l);
    }

    public static int getCheckSum4JT808(byte[] bs, int start, int end) {
        if (start < 0 || end > bs.length)
            throw new ArrayIndexOutOfBoundsException("getCheckSum4JT808 error : index out of bounds(start=" + start
                    + ",end=" + end + ",bytes length=" + bs.length + ")");
        int cs = 0;
        for (int i = start; i < end; i++) {
            cs ^= bs[i];
        }
        return cs;
    }

    public static byte[] getCheckSum4JT808(byte[] bs) {
        byte[] cs = {0};
        for (int i = 0; i < bs.length; i++) {
            cs[0] ^= bs[i];
        }
        return cs;
    }

    public static int getBitRange(int number, int start, int end) {
        if (start < 0)
            throw new IndexOutOfBoundsException("min index is 0,but start = " + start);
        if (end >= Integer.SIZE)
            throw new IndexOutOfBoundsException("max index is " + (Integer.SIZE - 1) + ",but end = " + end);

        return (number << Integer.SIZE - (end + 1)) >>> Integer.SIZE - (end - start + 1);
    }

    public static int getBitAt(int number, int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("min index is 0,but " + index);
        if (index >= Integer.SIZE)
            throw new IndexOutOfBoundsException("max index is " + (Integer.SIZE - 1) + ",but " + index);

        return ((1 << index) & number) >> index;
    }

    /**
     * 分离字节数组
     *
     * @param bytes 全部字节组
     * @param start 从0开始 含头
     * @param end   含尾
     * @return 分离后的字节组
     */
    public static byte[] splitBytes(byte[] bytes, int start, int end) {
        byte[] b = new byte[end - start + 1];

        for (int i = start; i <= end; i++) {
            b[i - start] = bytes[i];
        }
        return b;
    }

    public byte[] getHeaderBytes(byte[] all) {
        return splitBytes(all, 1, 16);
    }
}
