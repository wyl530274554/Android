package com.melon.subiao;

/**
 * 常量池
 *
 * @author wswangyl
 */
public interface Constant {
    /**
     * 终端型号 20个字节，位数不足，后补0x00
     */
    String TERMINAL_MODEL = "W2";

    /**
     * TODO 制造商ID
     */
    String MANUFACTURER_ID = "00000";

    /**
     * 公司名称
     */
    String COMPANY_NAME = "Wissen";

    /**
     * 产品代码
     */
    String PRODUCT_CODE = TERMINAL_MODEL;

    /**
     * 硬件版本号
     */
    String HARDWARE_VERSION = TERMINAL_MODEL;

    /**
     * 客户代码
     */
    String USER_CODE = "Wissen";

    /**
     * 标记位
     */
    byte[] MSG_FLAG = {0x7e};

    /**
     * 实时数据标记
     */
    byte[] RTP_FLAG = {0x30, 0x31, 0x63, 0x64};

    byte MSG_FLAG_BYTE = 0x7e;

    /**
     * 心跳间隔时间 秒
     */
    int HEART_BEAT_INTERVAL_IN_SECONDS = 15;

    /**
     * 默认位置汇报时间间隔 秒
     */
    int POSITION_REPORT_INTERVAL_IN_SECONDS = 15;

    /**
     * 断线重连时间 秒
     */
    int RECONNECT_INTERVAL_IN_SECONDS = 10;

    /**
     * 自定义消息ID 连接平台
     */
    int MSG_ID_TERMINAL_CONN = 0x5001;

    /**
     * 自定义消息ID 连接断开
     */
    int MSG_ID_CONN_FAILED = 0x5002;

    /**
     * 自定义消息ID 连接附件服务器失败
     */
    int MSG_ID_CONN_ATTACH_SERVER_FAILED = 0x5003;

    /**
     * 自定义消息ID 连接附件服务器失败
     */
    int MSG_ID_CONN_RTSP_SERVER_FAILED = 0x5004;

    /**
     * 删除N天前的数据
     */
    int DAY_OF_DEL_MESSAGE_RECORD = 7;

    /**
     * GPS保留后面6位小数
     */
    int GPS_COORDS_PRECISION = 1000000;

    /**
     * 报警标识号用的时间格式
     */
    String ALARM_TIMESTAMP_FORMAT = "yyMMddHHmmss";

    /**
     * 是否为主动上传（下发附件上传指令后，主动发附件到平台而不是在平台上点击）
     */
    boolean IS_AUTO_UPLOAD_ATTACHMENT = true;

    /**
     * 通信配置文件名称
     */
    String COMMUNICATION_PROPERTIES_FILE_NAME = "communication.properties";

    /**
     * 字符串编码
     */
    String STRING_CHARSET = "GBK";
}
