package play.util;

import java.util.Random;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huang
 */
@Slf4j
public class Ipv4Util {

//    private static final Logger log = Logger.getLogger(Ipv4Util.class.getName());

    /**
     * ipv4最大值，255.255.255.255，2^32 - 1
     */
    private static final long IPV4_MAX = (long) (Math.pow(2, 32) - 1);

    /**
     * ipv4字符串的段数
     */
    private static final int SEG_NUM = 4;

    /**
     * 处理失败的返回值
     */
    public static final int FAIL = -1;

    /**
     * 解析int字符串，失败时返回null
     */
    private static Integer parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * ipv4字符串转换为long
     * @param ipStr ipv4字符串
     * @return 对应的数值；无效的ip返回-1
     */
    public static long ipv4ToLong(String ipStr) {
        String[] parts = ipStr.split("\\.");
        if (parts.length != SEG_NUM) {
            return FAIL;
        }

        long ipVal = 0L;
        for (int i = 0; i < SEG_NUM; i++) {
            Integer tmpInt = parseInt(parts[ i ]);
            if (tmpInt == null || tmpInt > 255 || tmpInt < 0) {
                return FAIL;
            }
            ipVal += Math.pow(256, SEG_NUM - i - 1) * tmpInt;
        }

        return ipVal;
    }

    /**
     * ipv4掩码转换为long
     * @param maskStr ipv4掩码
     * @return 对应的数值；无效的ip返回-1
     */
    public static long maskToLong(String maskStr) {
        long val = ipv4ToLong(maskStr);
        long bak = val;
        // 0.0.0.0、255.255.255.255为无效的掩码
        if (val <= 0 || val >= IPV4_MAX) {
            return FAIL;
        }

        // ipv4掩码转换为二进制之后，为连续的1和0组成，0-1的转换只有1次
        int count = 32;
        int lastBit = 0;
        do {
            int thisBit = (int) (val % 2);
            if (thisBit == 0 && lastBit == 1) {
                return FAIL;
            }
            lastBit = thisBit;

            val = val >> 1;
            count--;
        } while (count > 0);

        return bak;
    }

    /**
     * 将ipv4数值转换为ipv4字符串
     * @param ipVal 数值表示的ip
     * @return ipv4字符串
     */
    public static String fmtIpv4Str(long ipVal) {
        if (ipVal < 0 || ipVal > IPV4_MAX) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SEG_NUM; i++) {
            sb.insert(0, "." + ipVal % 256);
            ipVal = ipVal / 256;
        }

        return sb.substring(1);
    }

    private static long generateMask(int length) {
        if (length <= 0) {
            return FAIL;
        }
        long val = 0;
        for (int i = 0; i < 32; i++) {
            if (i < length) {
                val = (val << 1) + 1;
            } else {
                val = val << 1;
            }
        }
        return val;
    }

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            long val = (long) (random.nextDouble() * IPV4_MAX);
            String ip = fmtIpv4Str(val);
            log.info("ipVal = " + val);
            log.info("ipStr = " + ip);
            // add jvm arg -ea to enable assert
            assert ipv4ToLong(ip) == val;
        }

        // 随机生成一个掩码
        int maskLen = random.nextInt(32);
        long maskVal = generateMask(maskLen);
        String maskStr = fmtIpv4Str(maskVal);
        log.info("maskLen = " + maskLen);
        log.info("maskVal = " + maskVal + " " + Long.toBinaryString(maskVal));
        log.info("maskStr = " + fmtIpv4Str(maskVal));
        assert maskToLong(maskStr) == maskVal;
    }
}
