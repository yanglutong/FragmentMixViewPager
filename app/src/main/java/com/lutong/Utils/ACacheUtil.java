package com.lutong.Utils;


import static android.provider.MediaStore.Video.VideoColumns.LATITUDE;
import static android.provider.MediaStore.Video.VideoColumns.LONGITUDE;


/**
 * 缓存工具包类
 */
public class ACacheUtil {
    public static String getNumberMax() {
        return getACache().getAsString("putNumberMax");
    }
    public static void putjguitime(String guitime) {//轨迹时间
        getACache().put("guitime", guitime);
    }
    public static void putNumberMax(String putNumberMax) {//一共查询次数
        getACache().put("putNumberMax", putNumberMax);
    }
    public static String getguitime() {
        return getACache().getAsString("guitime");
    }
    public static void putSID(String SID) {//覆盖范围开关
        getACache().put("SID", SID);
    }

    public static String getSID() {
        return getACache().getAsString("SID");
    }
    public static void putFugaiKG(String putFugaiKG) {//覆盖范围开关
        getACache().put("putFugaiKG", putFugaiKG);
    }

    public static String getFugaiKG() {
        return getACache().getAsString("putFugaiKG");
    }
    public static void putjbaojingtime(String baojingtime) {//报警的时间
        getACache().put("baojingtime", baojingtime);
    }

    public static String getbaojingtime() {
        return getACache().getAsString("baojingtime");
    }
    public static void putNumberremainder(String putNumberremainder) {//剩余查询次数
        getACache().put("putNumberremainder", putNumberremainder);
    }

    public static String getNumberremainder() {
        return getACache().getAsString("putNumberremainder");
    }
    public static ACache getACache() {
        return ACache.get();
    }

    public static void clearACache() {
        getACache().clear();
    }

//    public static boolean isLogin(){
//        return getACache().getAsString(USER_ID_KEY) == null ? false : true;
//    }
//
//    /**
//     * 将用户 id 添加至缓存
//     * @param userId
//     */
//    public static void putUserId(String userId){
//        getACache().put(USER_ID_KEY,userId);
//    }
//
//    /**
//     * 从缓存获取用户 id
//     * @return
//     */
//    public static int getUserId(){
//        String userId = getACache().getAsString(USER_ID_KEY);
//        return Integer.parseInt(userId);
//    }
//    /**
//     * 从缓存获取 guid
//     * @return
//     */
//    public static String getGuId(){
//        String guId = getACache().getAsString(GU_ID_KEY);
//        return guId;
//    }
//
//    /**
//     * 将用户 guid 添加至缓存
//     * @param guId
//     */
//    public static void putGuId(String guId){
//        getACache().put(GU_ID_KEY,guId);
//    }
//    /**
//     * 将用户 昵称 添加至缓存
//     * @param nickname
//     */
//    public static void putNickname(String nickname){
//        getACache().put(NICKNAME,nickname);
//    }
//
//    /**
//     * 从缓存获取用户 昵称
//     * @return
//     */
//    public static String getNickname(){
//        String nickname = getACache().getAsString(NICKNAME);
//        return nickname;
//    }
//    /**
//     * 将用户 头像 添加至缓存
//     * @param userhead
//     */
//    public static void putUserhead(String userhead){
//        getACache().put(USERHEAD,userhead);
//    }
//
//    /**
//     * 从缓存获取用户 头像
//     * @return
//     */
//    public static String getUserhead(){
//        String userhead = getACache().getAsString(USERHEAD);
//        return userhead;
//    }
//    /**
//     * 将手机号添加至缓存
//     * @param phone
//     */
//    public static void putPhone(String phone){
//        getACache().put(PHONE_KEY,phone);
//    }
//
//    /**
//     * 从缓存获取手机号
//     * @return
//     */
//    public static String getPhone(){
//        return getACache().getAsString(PHONE_KEY);
//    }
//    /**
//     * 将比例添加至缓存
//     * @param ratio
//     */
//    public static void putRatio(String ratio){
//        getACache().put(RATIO_KEY,ratio);
//    }
//
//    /**
//     * 从缓存获取比例
//     * @return
//     */
//    public static int getRatio(){
//        return Integer.parseInt(getACache().getAsString(RATIO_KEY));
//    }
//    /**
//     * 将余额添加至缓存
//     * @param yue
//     */
//    public static void putYue(String yue){
//        getACache().put(YUE_KEY,yue);
//    }
//
//    /**
//     * 从缓存获取余额
//     * @return
//     */
//    public static String getYue(){
//        return getACache().getAsString(YUE_KEY);
//    }
//    /**
//     * 将礼物添加至缓存
//     * @param giftList
//     */
//    public static void putGiftList(ArrayList<Gift> giftList){
//        getACache().put(GIFT_LIST,giftList);
//    }
//
//    /**
//     * 从缓存获取礼物
//     * @return
//     */
//    public static ArrayList<Gift> getGiftList(){
//        return (ArrayList<Gift>) getACache().getAsObject(GIFT_LIST);
//    }


    /**
     * 将起点
     * 0.经度添加至缓存
     *
     * @param latitude
     */
    public static void putStartLatitude(String latitude) {
        getACache().put(LATITUDE, latitude);
    }

    public static String getStartLatitude() {
        return getACache().getAsString(LATITUDE);
    }

    /**
     * 将经度添加至缓存
     *
     * @param longitude
     */
    public static void putStartLongitude(String longitude) {
        getACache().put(LATITUDE, longitude);
    }

    public static String getStartLongitude() {
        return getACache().getAsString(LATITUDE);
    }

    /**
     * 将经度添加至缓存
     *
     * @param latitude
     */
    public static void putLatitude(String latitude) {
        getACache().put(LATITUDE, latitude);
    }

    /**
     * 从缓存获取经度
     *
     * @return
     */
    public static String getLatitude() {
        return getACache().getAsString(LATITUDE);
    }

    /**
     * 将纬度添加至缓存
     *
     * @param longitude
     */
    public static void putLongitude(String longitude) {
        getACache().put(LONGITUDE, longitude);
    }

    /**
     * 从缓存获取纬度
     *
     * @return
     */
    public static String getLongitude() {
        return getACache().getAsString(LONGITUDE);
    }

    public static void putDW_ID(String DWID) {//单位ID
        getACache().put("DW_ID", DWID);
    }

    public static String getDW_ID() {
        return getACache().getAsString("DW_ID");
    }

    public static void putID(String id) {//用户id
        getACache().put("ID", id);
    }

    public static String getID() {
        return getACache().getAsString("ID");
    }

    public static void putPUR_CODE(String PUR_CODE) {//code
        getACache().put("PUR_CODE", PUR_CODE);
    }

    public static String getPUR_CODE() {
        return getACache().getAsString("PUR_CODE");
    }

    /**
     * 短信验证码
     *
     * @param shortCode
     */
    public static void putShortCode(String shortCode) {
        getACache().put("shortCode", shortCode, 60 * 5);
    }

    public static String getShortCode() {
        return getACache().getAsString("shortCode");
    }

    ////////////////////////////////////////
    public static void putTl(String tl) {//单位TL
        getACache().put("TL", tl);
    }

    public static String getTl() {
        return getACache().getAsString("TL");
    }

    public static void putEci(String eci) {//Eci
        getACache().put("Eci", eci);
    }

    public static String getEci() {
        return getACache().getAsString("Eci");
    }

    public static void putTa(String ta) {//Eci
        getACache().put("Ta", ta);
    }

    public static String getTa() {
        return getACache().getAsString("Ta");
    }

    public static void putjzType(String jzType) {//Eci
        getACache().put("jzType", jzType);
    }

    public static String getjzType() {
        return getACache().getAsString("jzType");
    }

    /**
     * ms  0为定位 1为侦码
     *
     * @param
     */
    public static void putMS1(String ms1) {//Eci
        getACache().put("ms1", ms1);
    }

    public static String getMS1() {
        return getACache().getAsString("ms1");
    }

    public static void putMS2(String ms2) {//Eci
        getACache().put("ms2", ms2);
    }

    public static String getMS2() {
        return getACache().getAsString("ms2");
    }

    public static void putZD(String ms2) {//Eci
        getACache().put("zd", ms2);
    }

    public static String getZD() {
        return getACache().getAsString("zd");
    }
    //设备1spinner 选中

    public static void putSpinner1(String ms2) {//Eci
        getACache().put("Spinner1", ms2);
    }

    public static String getSpinner1() {
        return getACache().getAsString("Spinner1");
    }

    //设备1spinner 选中

    public static void putSpinner2(String ms2) {//Eci
        getACache().put("Spinner2", ms2);
    }

    public static String getSpinner2() {
        return getACache().getAsString("Spinner2");
    }

    //
    public static void putsbSpTYPE(String ms2) {//Eci
        getACache().put("sbSpTYPE", ms2);
    }

    public static String getsbSpTYPE() {
        return getACache().getAsString("sbSpTYPE");
    }
}
