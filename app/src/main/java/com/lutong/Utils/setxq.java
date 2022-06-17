package com.lutong.Utils;

public class setxq {

    //小区去激活指令
    String Str = "aa aa 55 55 0d f0 10 00 00 00 00 ff 00 00 00 00";

    /**
     * 建立小区
     *
     * @param ulEarfcn 上行频点
     * @param dlEarfcn 下行频点
     * @param PLMN     移动联通电信
     * @param band     band号
     * @param PCI      PCI
     * @param TAC
     * @return
     */
    public static String setXq(int ulEarfcn, int dlEarfcn, String PLMN, int band, int PCI, int TAC, int cellld) {

        //消息头
        StringBuffer buffer = new StringBuffer("aaaa555503f02c00000000ff");
        //上行频点
        StringBuffer buffer1 = StringPin(StringAdd(Integer.toString(ulEarfcn, 16)));
        //下行频点
        StringBuffer buffer2 = StringPin(StringAdd(Integer.toString(dlEarfcn, 16)));
        System.out.println(buffer2);
        //PLMN:移动联通电信
        StringBuffer buffer3 = str2HexStr(PLMN);
        StringBuffer buffer4 = buffer3.append("0000");
        //系统带宽
        //StringBuffer buffer4 = new StringBuffer("64");
        //Band号
        StringBuffer buffer6 = StringPin(StringBand(Integer.toString(band, 16)));
        //PCI
        StringBuffer buffer7 = StringPin(StringPCI(Integer.toString(PCI, 16)));
        //TAC
        StringBuffer buffer8 = StringPin(StringPCI(Integer.toString(TAC, 16)));
        //CellId
        StringBuffer buffer9 = StringPin(StringBand(Integer.toString(cellld, 16)));
        /*//UePMax（默认23）
//		StringBuffer buffer10 = StringPin(StringPCI(Integer.toString(UePMax, 16)));
		//EnodeBPMax（默认20）
		StringBuffer buffer11 = StringPin(StringPCI(Integer.toString(EnodeBPMax, 16)));*/


        /**
         * 2020 -3-19 改
         */
//        String str = buffer.append(buffer1).append(buffer2).append(buffer4).append("19").append(buffer6).append(buffer7).append(buffer8).append(buffer9).append("1700").append("1400").toString();//带宽 5兆
        String str = buffer.append(buffer1).append(buffer2).append(buffer4).append("32").append(buffer6).append(buffer7).append(buffer8).append(buffer9).append("1700").append("1400").toString();//带宽 10兆

        return str;
    }

    //16进制上行频点、下行频点
    public static StringBuffer StringAdd(String str) {
        StringBuffer buffer = new StringBuffer(str);
        for (int i = buffer.length(); i < 8; i++) {
            buffer.insert(0, "0");
        }
        return buffer;
    }

    //字符串分割成两个字符一组，倒序拼接到一起
    public static StringBuffer StringPin(StringBuffer str) {

        String[] bands = new String[str.length() / 2];
        for (int i = 0; i < str.length(); i += 2) {
            bands[i / 2] = str.substring(i, i + 2);
        }

        StringBuffer str1 = new StringBuffer();
        for (int i = bands.length - 1; i >= 0; i--) {
            str1.append(bands[i]);
        }

        return str1;
    }

    //移动联通电信转换成对应
    public static StringBuffer str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuffer sb = new StringBuffer("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb;
    }

    //Band号
    public static StringBuffer StringBand(String str) {

        StringBuffer buffer = new StringBuffer(str);
        for (int i = buffer.length(); i < 8; i++) {
            buffer.insert(0, "0");
        }
        return buffer;
    }

    //PCI
    public static StringBuffer StringPCI(String str) {

        StringBuffer buffer = new StringBuffer(str);
        for (int i = buffer.length(); i < 4; i++) {
            buffer.insert(0, "0");
        }
        return buffer;
    }


}
