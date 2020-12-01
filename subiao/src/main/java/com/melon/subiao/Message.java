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

        byte peripheralNumber = unescapedMsg[6];
        sb.append("外设编号").append(" : ").append(HexStringUtils.toHexString(new byte[]{peripheralNumber})).append("\n");
        byte functionCode = unescapedMsg[7];
        sb.append("功能码").append(" : ").append(HexStringUtils.toHexString(new byte[]{functionCode})).append("\n");

        byte[] dataBytes = BitOperator.splitBytes(msgBytes, 8, msgBytes.length - 2);
        sb.append("数据内容").append(" : ").append(HexStringUtils.toHexString(dataBytes)).append("\n");
        parseDataContent(sb, dataBytes, functionCode, peripheralNumber);
        sb.append("标识符").append(" : ").append("7E");
        return sb.toString();
    }

    private static void parseDataContent(StringBuilder sb, byte[] dataBytes, byte functionCode, byte peripheralNumber) {
        if (functionCode == 0x36 && peripheralNumber == 0x64) {
            //ADAS报警
            if (dataBytes.length != 31 + dataBytes[30] * 5) {
                sb.append("        ").append("内容长度不正确：").append(dataBytes.length).append("\n");
                return;
            }

            sb.append("        ").append("报警ID：").append(BitOperator.byteToInteger(BitOperator.splitBytes(dataBytes, 0, 3))).append("\n");
            sb.append("        ").append("标志状态：").append(dataBytes[4]).append("\n");
            sb.append("        ").append("报警类型：").append(dataBytes[5]).append("\n");
            sb.append("        ").append("前车车速：").append(dataBytes[6]).append("\n");
            sb.append("        ").append("前车距离：").append(dataBytes[7]).append("\n");
            sb.append("        ").append("偏离类型：").append(dataBytes[8]).append("\n");
            sb.append("        ").append("道路标志类型：").append(dataBytes[9]).append("\n");
            sb.append("        ").append("道路标志数据：").append(dataBytes[10]).append("\n");
            sb.append("        ").append("车速：").append(dataBytes[11]).append("\n");
            sb.append("        ").append("纬度：").append(BitOperator.byteToInteger(BitOperator.splitBytes(dataBytes, 14, 17))).append("\n");
            sb.append("        ").append("经度：").append(BitOperator.byteToInteger(BitOperator.splitBytes(dataBytes, 18, 21))).append("\n");
            sb.append("        ").append("时间：").append(HexStringUtils.toHexString(BitOperator.splitBytes(dataBytes, 22, 27))).append("\n");
            sb.append("        ").append("车辆状态：").append(BitOperator.byteToInteger(BitOperator.splitBytes(dataBytes, 28, 29))).append("\n");
            sb.append("        ").append("多媒体总数：").append(dataBytes[30]).append("\n");
            for (int i = 0; i < dataBytes[30]; i++) {
                sb.append("        ").append("多媒体类型：").append(dataBytes[31 + i * 5]).append(", ").append("多媒体ID: ").append(BitOperator.byteToInteger(BitOperator.splitBytes(dataBytes, 31 + i * 5 + 1, 31 + i * 5 + 4))).append("\n");
            }
            return;
        }
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
