package com.lutong.Utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lutong.Constant.Constant;


public class ReceiveTask implements Runnable{
	@Override
	public void run(){	
		try {
			
			DatagramSocket ds = new DatagramSocket(3345);
			byte[] buf= new byte[1024];
			DatagramPacket dp = new DatagramPacket(buf, buf.length);
			while(true){
				//通过udp的socket服务将数据包接收到，通过receive方法
				ds.receive(dp);
				byte[] buf1 = dp.getData();
				//btye[]字节数组转换成16进制的字符串
				String str = ReceiveTask.toHexString1(buf1);
				//String str = new String(dp.getData(),0,dp.getLength());
						
				if(Constant.IP2.equals(dp.getAddress().getHostAddress())){
					System.out.println("123456");
					System.out.println("收到"+dp.getAddress().getHostAddress()+"发送数据："+str);
					
					//时间
					Date d = new Date();
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        System.out.println("时间："+sdf.format(d));
					//模式：FDD TDD
					if("00ff".equals(str.substring(16, 20))){
						//设置模式FDD
						System.err.println("FDD");
					}if("ff00".equals(str.substring(16, 20))){
						//设置模式TDD
						System.out.println("TDD");
					}
					//建立小区是否成功
					if("04f0".equals(str.substring(8, 12))){
						//成功0；不成功>0（16进制字符串转换成十进制）
						int row = Integer.parseInt(str.substring(24,32),16);
						if(row==0){
							System.out.println("设置成功!开始建立小区!");
						}else{
							System.err.println("设置失败!");
						}
					}
					//去激活小区是否成功
					if("a0f0".equals(str.substring(8, 12))){
						//成功0；不成功>0（16进制字符串转换成十进制）
						int row = Integer.parseInt(str.substring(24,32),16);
						if(row==0){
							System.out.println("公放设置成功!");
						}else{
							System.err.println("设置失败!");
						}
					}

					//去激活小区是否成功
					if("0ef0".equals(str.substring(8, 12))){
						//成功0；不成功>0（16进制字符串转换成十进制）
						int row = Integer.parseInt(str.substring(24,32),16);
						if(row==0){
							System.out.println("设置成功！去激活小区成功，停止定位！");
						}else{
							System.err.println("设置失败！");
						}
					}
					//基站执行状态
					if("19f0".equals(str.substring(8, 12))){
						String state = str.substring(24,32);
						System.out.println("state"+state);
						if("00000000".equals(state)){
							System.err.println("空口同步成功");
						}else if("01000000".equals(state)){
							System.err.println("空口同步失败");
						}else if("02000000".equals(state)){
							System.err.println("GPS同步成功");
						}else if("03000000".equals(state)){
							System.err.println("GPS同步失败");
						}else if("04000000".equals(state)){
							System.err.println("扫频成功");
						}else if("05000000".equals(state)){
							System.err.println("扫频失败");
						}else if("06000000".equals(state)){
							System.err.println("小区激活成功");
						}else if("07000000".equals(state)){
							System.err.println("小区激活失败");
						}else if("08000000".equals(state)){
							System.err.println("小区去激活");
						}else if("09000000".equals(state)){
							System.err.println("空口同步中");
						}else if("0a000000".equals(state)){
							System.err.println("GPS同步中");
						}else if("0b000000".equals(state)){
							System.err.println("扫频中");
						}else if("0c000000".equals(state)){
							System.err.println("小区激活中");
						}else if("0d000000".equals(state)){
							System.err.println("小区去激活中");
						}
						
					}
					if("08f0".equals(str.substring(8, 12))){
						
						//目标距离（16进制字符串转换成十进制）
						Integer.parseInt(str.substring(24, 26),16);
						System.out.println("距离："+Integer.parseInt(str.substring(24, 26),16));
						//IMSI号
						StringTOIMEI(str.substring(26, 56));
						System.out.println("IMSI号："+hexStringToString(str.substring(26, 56)));
																
						}
					if("10f0".equals(str.substring(8, 12))){
						//心跳解析
						//查看小区是否建立成功（0：小区 IDLE态；1：扫频/同步进行中；2：小区激活中；3：小区激活态；4：小区去激活中）
						if("0000".equals(str.substring(24, 28))){
							System.out.println("0：小区 IDLE态");
							
						}else if("0100".equals(str.substring(24, 28))){
							System.out.println("1：扫频/同步进行中");
						}else if("0200".equals(str.substring(24, 28))){
							System.out.println("2：小区激活中");
						}else if("0300".equals(str.substring(24, 28))){
							System.out.println("3：小区激活态");
							
							//Band号
							Integer.parseInt(StringPin(str.substring(28,32)),16);
							System.out.println("Band号："+Integer.parseInt(StringPin(str.substring(28,32)),16));
							//上行频点
							Integer.parseInt(StringPin(str.substring(32,40)),16);
							System.out.println("上行频点："+Integer.parseInt(StringPin(str.substring(32,40)),16));
							//下行频点
							Integer.parseInt(StringPin(str.substring(40,48)),16);
							System.out.println("下行频点："+Integer.parseInt(StringPin(str.substring(40,48)),16));
							//移动联通电信
							if("3436303030".equals(str.substring(48, 58))){
								//设置中国移动
							}if("3436303031".equals(str.substring(48, 58))){
								//设置中国联通
							}if("3436303033".equals(str.substring(48, 58))||"3436303131".equals(str.substring(48, 58))){
								//设置中国电信
							}
							
							//PCI
							Integer.parseInt(StringPin(str.substring(64,68)),16);
							System.out.println("PCI:"+Integer.parseInt(StringPin(str.substring(64,68)),16));
							//TAC
							Integer.parseInt(StringPin(str.substring(68,72)),16);
							System.out.println("TAC:"+Integer.parseInt(StringPin(str.substring(68,72)),16));
						
							}else if("0400".equals(str.substring(24, 28))){
								System.out.println("4：小区去激活中");
							}
				
						}
					//温度告警
					if("5bf0".equals(str.substring(8, 12))){
						if("00000000".equals(str.substring(32,40))){
							System.out.println("基带板温度超过70度");
						}
						if("01000000".equals(str.substring(32,40))){
							System.out.println("基带板温度降低到70度以下了");
						}
						
					}
					
				}
				if(Constant.IP2.equals(dp.getAddress().getHostAddress())){
					System.out.println("ABCD");
					System.out.println("收到"+dp.getAddress().getHostAddress()+"发送数据："+str);
					
					//时间
					Date d = new Date();
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        System.out.println("时间："+sdf.format(d));
					//设置模式：FDD TDD
					if("00ff".equals(str.substring(16, 20))){
						//设置模式FDD
						System.err.println("FDD");
					}if("ff00".equals(str.substring(16, 20))){
						//设置模式TDD
						System.out.println("TDD");
					}
					
					if("08f0".equals(str.substring(8, 12))){
						//目标距离（16进制字符串转换成十进制）
						Integer.parseInt(str.substring(24, 26),16);
						System.out.println("距离："+Integer.parseInt(str.substring(24, 26),16));
						//IMSI号
						StringTOIMEI(str.substring(26, 56));
						System.out.println("IMSI号："+hexStringToString(str.substring(26, 56)));
																
					}
					if("10f0".equals(str.substring(8, 12))){
						//心跳解析
						//查看小区是否建立成功（0：小区 IDLE态；1：扫频/同步进行中；2：小区激活中；3：小区激活态；4：小区去激活中）
						if("0000".equals(str.substring(24, 28))){
							System.out.println("0：小区 IDLE态");
							
						}else if("0100".equals(str.substring(24, 28))){
							System.out.println("1：扫频/同步进行中");
						}else if("0200".equals(str.substring(24, 28))){
							System.out.println("2：小区激活中");
						}else if("0300".equals(str.substring(24, 28))){
							System.out.println("3：小区激活态");
							//Band号
							Integer.parseInt(StringPin(str.substring(28,32)),16);
							System.out.println("Band号："+Integer.parseInt(StringPin(str.substring(28,32)),16));
							//上行频点
							Integer.parseInt(StringPin(str.substring(32,40)),16);
							System.out.println("上行频点："+Integer.parseInt(StringPin(str.substring(32,40)),16));
							//下行频点
							Integer.parseInt(StringPin(str.substring(40,48)),16);
							System.out.println("下行频点："+Integer.parseInt(StringPin(str.substring(40,48)),16));
							//移动联通电信
							if("3436303030".equals(str.substring(48, 58))){
								//设置中国移动
							}if("3436303031".equals(str.substring(48, 58))){
								//设置中国联通
							}if("3436303033".equals(str.substring(48, 58))||"3436303131".equals(str.substring(48, 58))){
								//设置中国电信
							}
							//PCI
							Integer.parseInt(StringPin(str.substring(64,68)),16);
							System.out.println("PCI:"+Integer.parseInt(StringPin(str.substring(64,68)),16));
							//TAC
							Integer.parseInt(StringPin(str.substring(68,72)),16);
							System.out.println("TAC:"+Integer.parseInt(StringPin(str.substring(68,72)),16));
							
						}else if("0400".equals(str.substring(24, 28))){
							System.out.println("4：小区去激活中");
						}
				
					}
					
				}
				/*System.out.println("截取字符串"+str.substring(8, 12));
				if(str.substring(8, 12).equals("08f0")){
					
					//目标距离（16进制字符串转换成十进制）
					Integer.parseInt(str.substring(24, 26),16);
					System.out.println("距离："+Integer.parseInt(str.substring(24, 26),16));
					//IMSI号
					StringTOIMEI(str.substring(26, 56));
					System.out.println("IMSI号："+hexStringToString(str.substring(26, 56)));
															
				}*//*if("10f0".equals(str.substring(8, 12))){
					//心跳解析
					//查看小区是否建立成功（0：小区 IDLE态；1：扫频/同步进行中；2：小区激活中；3：小区激活态；4：小区去激活中）
					if("0000".equals(str.substring(24, 28))){
						System.out.println("0：小区 IDLE态");
					}if("0100".equals(str.substring(24, 28))){
						System.out.println("1：扫频/同步进行中");
					}if("0200".equals(str.substring(24, 28))){
						System.out.println("2：小区激活中");
					}if("0300".equals(str.substring(24, 28))){
						System.out.println("3：小区激活态");
					}if("0400".equals(str.substring(24, 28))){
						System.out.println("4：小区去激活中");
					}
					//设置模式：FDD TDD
					if("00ff".equals(str.substring(16, 20))){
						//设置模式FDD
						System.err.println("FDD");
					}if("ff00".equals(str.substring(16, 20))){
						//设置模式TDD
						System.out.println("TDD");
					}
					//时间
					Date d = new Date();
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        System.out.println("时间："+sdf.format(d));
					//Band号
					Integer.parseInt(StringPin(str.substring(28,32)),16);
					System.out.println("Band号："+Integer.parseInt(StringPin(str.substring(28,32)),16));
					//上行频点
					Integer.parseInt(StringPin(str.substring(32,40)),16);
					System.out.println("上行频点："+Integer.parseInt(StringPin(str.substring(32,40)),16));
					//下行频点
					Integer.parseInt(StringPin(str.substring(40,48)),16);
					System.out.println("下行频点："+Integer.parseInt(StringPin(str.substring(40,48)),16));
					//移动联通电信
					if("3436303030".equals(str.substring(48, 58))){
						//设置中国移动
					}if("3436303031".equals(str.substring(48, 58))){
						//设置中国联通
					}if("3436303033".equals(str.substring(48, 58))||"3436303131".equals(str.substring(48, 58))){
						//设置中国电信
					}
					
					//PCI
					Integer.parseInt(StringPin(str.substring(64,68)),16);
					System.out.println("PCI:"+Integer.parseInt(StringPin(str.substring(64,68)),16));
					//TAC
					Integer.parseInt(StringPin(str.substring(68,72)),16);
					System.out.println("TAC:"+Integer.parseInt(StringPin(str.substring(68,72)),16));
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/*
	 * btye[]字节数组转换成16进制的字符串
	 */
	public static String toHexString1(byte[] b){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i){
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }
	
	 public static String toHexString1(byte b){
	        String s = Integer.toHexString(b & 0xFF);
	        if (s.length() == 1){
	            return "0" + s;
	        }else{
	            return s;
	        }
	 }
	 
	 /**
		 * 16进制转换成为string类型字符串
		 * @param s
		 * @return
		 */
	public static String hexStringToString(String s) {
	    if (s == null || s.equals("")) {
	        return null;
	    }
	    s = s.replace(" ", "");
	    byte[] baKeyword = new byte[s.length() / 2];
	    for (int i = 0; i < baKeyword.length; i++) {
	        try {
	            baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    try {
	        s = new String(baKeyword, "UTF-8");
	        new String();
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	    return s;
	}

	//取字符串偶数位字符拼接到一起
    public static String StringTOIMEI(String str){
    	StringBuffer buffer = new StringBuffer();
    	for(int i=1;i<=str.length();i+=2){
    		if(i%2 != 0){
    			buffer.append(str.charAt(i));
    		}
    	}
    	return buffer.toString();
    }
	
    //字符串分割成两个字符一组，倒序拼接到一起
    public static String StringPin(String str){
    	
    	String [] bands = new String[str.length()/2]; 
		for(int i=0;i<str.length();i+=2){
			bands[i/2] = str.substring(i,i+2);
			
		}
		
		String str1 = new String();
		for(int i=bands.length-1;i>=0;i--){
			str1 += bands[i];
		}
    	return str1;
    }
    
    
}
