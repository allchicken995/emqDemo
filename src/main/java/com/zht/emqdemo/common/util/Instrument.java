package com.zht.emqdemo.common.util;

import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;

/**
 * 共通方法工具类
 *
 * @author zht
 */
@Configuration
public class Instrument {

    /**
     * 对字符串CRC加密
     *
     * @param str 加密字符串
     * @return String
     * @author fujisoft 2017/08/01
     */
    public static String crcMac(String str) {
        try {
            if (str.length() != 12) {
                return "";
            }
            long lFcrc = 0;
            int data1 = (new BigInteger(str.substring(0, 2), 16).intValue() + 10) % 256;
            int data2 = (new BigInteger(str.substring(2, 4), 16).intValue() + 20) % 256;
            int data3 = (new BigInteger(str.substring(4, 6), 16).intValue() + 30) % 256;
            int data4 = (new BigInteger(str.substring(6, 8), 16).intValue() + 40) % 256;
            int data5 = (new BigInteger(str.substring(8, 10), 16).intValue() + 50) % 256;
            int data6 = (new BigInteger(str.substring(10, 12), 16).intValue() + 60) % 256;
            lFcrc = crcCalc(lFcrc, data1);
            lFcrc = crcCalc(lFcrc, data2);
            lFcrc = crcCalc(lFcrc, data3);
            lFcrc = crcCalc(lFcrc, data4);
            lFcrc = crcCalc(lFcrc, data5);
            lFcrc = crcCalc(lFcrc, data6);

            // 算出结果如果为0的话，强制设置成1
            if (0 == lFcrc) {

                lFcrc = lFcrc + 1;
            }

            return conactLeftZero(4, Long.toHexString(lFcrc).toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("CRC加密失败！", e);
        }
    }

    /**
     * CRC算出
     *
     * @param lFcrc
     * @param dat
     * @return
     */
    protected static long crcCalc(long lFcrc, int dat) {
        long cal;
        cal = lFcrc ^ dat;
        for (int intI = 0; intI < 8; intI++) {
            if ((cal & 1) == 1) {
                cal = cal ^ 0x010811;
            }
            cal = (int) cal / 2;
        }
        return cal & 0xFFFF;
    }


    /**
     * 字符串前补0
     *
     * @param len   Integer
     * @param value String
     * @param
     * @return value String
     */
    protected static String conactLeftZero(int len, String value) {

        if (value == null) {
            return "";
        }

        while (value.length() < len) {
            value = "0".concat(value);
        }

        return value;
    }

}
