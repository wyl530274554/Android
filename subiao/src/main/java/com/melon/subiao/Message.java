package com.melon.subiao;

public class Message {
    public static String parse(String content) {
        if (content.length() < 9) {
            return "消息长度不正确";
        }

        byte[] msgBytes = null;
        try {
            msgBytes = HexStringUtils.hexString2Bytes(content);
        } catch (Exception e) {
            return "字节错误：" + e.getMessage();
        }

        if (msgBytes[0] != Constant.MSG_FLAG_BYTE || msgBytes[msgBytes.length - 1] != Constant.MSG_FLAG_BYTE) {
            return "首尾标识符不正确";
        }

        byte[] unescapedMsg;
        //反转义
        try {
            unescapedMsg = JT808Escaper.unescape(msgBytes);
        } catch (Exception e) {
            return "反转义失败：" + e.getMessage();
        }

        int countOf7e = countOf7e(unescapedMsg);
        if (countOf7e > 2) {
            return "标识符的数量有误：" + countOf7e;
        }

        int checkCode = checkCodeError(unescapedMsg);
        if (checkCode != 0) {
            return "校验码错误: " + HexStringUtils.toHexString(new byte[]{(byte) checkCode});
        }

        StringBuilder sb = new StringBuilder();
        sb.append("标识符").append(" : ").append("7E").append("\n");
        sb.append("校验码").append(" : ").append(HexStringUtils.toHexString(new byte[]{unescapedMsg[1]})).append("\n");
        sb.append("流水号").append(" : ").append(HexStringUtils.toHexString(new byte[]{unescapedMsg[2], unescapedMsg[3]})).append("\n");
        sb.append("厂商编号").append(" : ").append(HexStringUtils.toHexString(new byte[]{unescapedMsg[4], unescapedMsg[5]})).append("\n");
        sb.append("外设编号").append(" : ").append(HexStringUtils.toHexString(new byte[]{unescapedMsg[6]})).append("\n");
        sb.append("功能码").append(" : ").append(HexStringUtils.toHexString(new byte[]{unescapedMsg[7]})).append("\n");
        sb.append("数据内容").append(" : ").append(HexStringUtils.toHexString(BitOperator.splitBytes(msgBytes, 8, msgBytes.length - 2))).append("\n");
        sb.append("标识符").append(" : ").append("7E");
        return sb.toString();
    }

    private static int checkCodeError(byte[] contents) {
        int checkCode = BitOperator.oneByteToInteger(contents[1]);
        int jCheckCode = BitOperator.oneByteToInteger(generateCheckCode(BitOperator.splitBytes(contents, 4, contents.length - 2)));
        if (checkCode == jCheckCode) {
            return 0;
        }
        return jCheckCode;
    }

    /**
     * 生成校验码
     */
    private static byte generateCheckCode(byte[] needCheckData) {
        int sum = 0;
        for (byte d : needCheckData) {
            int i = BitOperator.oneByteToInteger(d);
            sum += i;
        }
        return BitOperator.numToByteArray(sum, BitOperator.DWORD)[3];
    }

    public static int countOf7e(byte[] contents) {
        int count = 0;
        for (byte content : contents) {
            if (content == 0x7e) {
                count++;
            }
        }
        return count;
    }
}
