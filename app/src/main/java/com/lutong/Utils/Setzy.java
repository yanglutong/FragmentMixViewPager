package com.lutong.Utils;
/**
 * 设置增益
 * @author Administrator
 *
 */
public class Setzy {

	String str ="aa aa 55 55 13 f0 14 00 00 00 00 ff 34 00 00 00 01 01 00 00";
//	public static void main(String[] args) {
//		System.out.println(acceptGain(52));
//	}
	
	/**
	 * 设置增益
	 * @param Rxgain Rxgain取值范围(0-72),出厂默认值：FDD=40\TDD=52
	 * @return
	 */
	public static String acceptGain(int Rxgain){
			
		//消息头
		StringBuffer buffer = new StringBuffer("aaaa555513f01400000000ff");
		StringBuffer buffer1 = StringPin(StringAdd(Integer.toString(Rxgain, 16)));			
		StringBuffer buffer2 = new StringBuffer("00000000");
		String str = buffer.append(buffer1).append(buffer2).toString();
		
		return str;
	}
	
	//字符串前添加0，添加到字符串为8位为止
	public static StringBuffer StringAdd(String str){
		StringBuffer buffer = new StringBuffer(str);	
		for(int i=buffer.length();i<8;i++){
			buffer.insert(0,"0");
		}
		return buffer;
	}
	
	//字符串分割成两个字符一组，倒序拼接到一起
    public static StringBuffer StringPin(StringBuffer str){
    	
    	String [] bands = new String[str.length()/2]; 
		for(int i=0;i<str.length();i+=2){
			bands[i/2] = str.substring(i,i+2);
		}
		
		StringBuffer str1 = new StringBuffer();
		for(int i=bands.length-1;i>=0;i--){
			str1.append(bands[i]);
		}
		
    	return str1;
    }
    
}
