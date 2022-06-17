package com.lutong.interfaces;

/**工模右上角弹窗回调 用于fragment和activity传递
 * @author: 小杨同志
 * @date: 2022/5/18
 */
public interface SetGmConfigSettings {
//    void setGmConfig();

    void setGmConfig(boolean isYd, boolean isLt, boolean isDx, boolean isJzBj, int jzMessage);
}
