package com.justsy.https;
import java.io.BufferedReader;         
import java.io.BufferedWriter;         
import java.io.InputStreamReader;         
import java.io.OutputStreamWriter;         
import java.net.Socket;         
import java.security.cert.CertificateException;         
import java.security.cert.X509Certificate;         
import javax.net.ssl.SSLContext;         
import javax.net.ssl.SSLSocketFactory;         
import javax.net.ssl.TrustManager;         
import javax.net.ssl.X509TrustManager;         
/**      
 * JAVA操作SSL协议，通过Socket访问Https的程序代码例子。      
 *       
 * @author JAVA世纪网(java2000.net)      
 *       
 */        
public class ReadHttpsURL {         
  // 默认的HTTPS 端口         
  static final int HTTPS_PORT = 443;         
  public static void main(String argv[]) throws Exception {         
    // 受访主机         
    String host = "www.google.com";         
    // 受访的页面         
    String url = "/adsense/?sourceid=aso&subid=ZH_CN-ET-AS-ADSBY6&medium=link&hl=zh_CN";         
    // 自定义的管理器         
    X509TrustManager xtm = new Java2000TrustManager();         
    TrustManager mytm[] = { xtm };         
    // 得到上下文         
    SSLContext ctx = SSLContext.getInstance("SSL");         
    // 初始化         
    ctx.init(null, mytm, null);         
    // 获得工厂         
    SSLSocketFactory factory = ctx.getSocketFactory();         
    // 从工厂获得Socket连接         
    Socket socket = factory.createSocket(host, HTTPS_PORT);         
    // 剩下的就和普通的Socket操作一样了         
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));         
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));         
    out.write("GET " + url + " HTTP/1.0\n\n");         
    out.flush();         
    System.out.println("start   work!");         
    String line;         
    StringBuffer sb = new StringBuffer();         
    while ((line = in.readLine()) != null) {         
      sb.append(line + "\n");         
    }         
    out.close();         
    in.close();         
    System.out.println(sb.toString());         
  }         
}         
  
/**      
 * 自定义的认证管理类。      
 *       
 * @author JAVA世纪网(java2000.net)      
 *       
 */        
class Java2000TrustManager implements X509TrustManager {         
  Java2000TrustManager() {         
    // 这里可以进行证书的初始化操作         
  }         
  // 检查客户端的可信任状态         
  public void checkClientTrusted(X509Certificate chain[], String authType) throws CertificateException {         
    System.out.println("检查客户端的可信任状态...");         
  }         
  // 检查服务器的可信任状态         
  public void checkServerTrusted(X509Certificate chain[], String authType) throws CertificateException {         
    System.out.println("检查服务器的可信任状态");         
  }         
  // 返回接受的发行商数组         
  public X509Certificate[] getAcceptedIssuers() {         
    System.out.println("获取接受的发行商数组...");         
    return null;         
  }         
}      