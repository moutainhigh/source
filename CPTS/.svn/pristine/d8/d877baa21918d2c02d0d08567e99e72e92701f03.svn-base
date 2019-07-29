package com.dchip.cloudparking.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * Http连接工具类
 * 
 * @author phil
 * @date 2017年7月2日
 *
 */
public class HttpReqUtil {

	private static int DEFAULT_CONNTIME = 5000;
	private static int DEFAULT_READTIME = 5000;

	/**
	 * http请求
	 * 
	 * @param method
	 *            请求方法GET/POST
	 * @param path
	 *            请求路径
	 * @param timeout
	 *            连接超时时间 默认为5000
	 * @param readTimeout
	 *            读取超时时间 默认为5000
	 * @param data
	 *            数据
	 * @return
	 */
	private static String defaultConnection(String method, String path, int timeout, int readTimeout, String data, String encoding)
			throws Exception {
		String result = "";
		URL url = new URL(path);
		if (url != null) {
			HttpURLConnection conn = getConnection(method, url);
			conn.setConnectTimeout(timeout == 0 ? DEFAULT_CONNTIME : timeout);
			conn.setReadTimeout(readTimeout == 0 ? DEFAULT_READTIME : readTimeout);
			if (data != null && !"".equals(data)) {
				OutputStream output = conn.getOutputStream();
				output.write(data.getBytes(SystemConstant.DEFAULT_CHARACTER_ENCODING));
				output.flush();
			}
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream input = conn.getInputStream();
				result = IOUtil.inputStreamToString(input, encoding);
				conn.disconnect();
			}
		}
		return result;
	}

	/**
	 * 根据url的协议选择对应的请求方式
	 * 
	 * @param method
	 *            请求的方法
	 * @return
	 * @throws IOException
	 */
	private static HttpURLConnection getConnection(String method, URL url) throws IOException {
		HttpURLConnection conn = null;
		if ("https".equals(url.getProtocol())) {
			SSLContext context = null;
			try {
				context = SSLContext.getInstance("SSL", "SunJSSE");
				context.init(new KeyManager[0], new TrustManager[] { new MyX509TrustManager() },
						new java.security.SecureRandom());
			} catch (Exception e) {
				throw new IOException(e);
			}
			HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
			connHttps.setSSLSocketFactory(context.getSocketFactory());
			connHttps.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			conn = connHttps;
		} else {
			conn = (HttpURLConnection) url.openConnection();
		}
		conn.setRequestMethod(method);
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		return conn;
	}

	/**
	 * 设置参数
	 * 
	 * @param map
	 *            参数map
	 * @param path
	 *            需要赋值的path
	 * @param charset
	 *            编码格式 默认编码为utf-8(取消默认)
	 * @return 已经赋值好的url 只需要访问即可
	 */
	public static String setParmas(Map<String, String> map, String path, String charset) throws Exception {
		String result = "";
		boolean hasParams = false;
		if (path != null && !"".equals(path)) {
			if (MapUtils.isNotEmpty(map)) {
				StringBuilder builder = new StringBuilder();
				Set<Entry<String, String>> params = map.entrySet();
				for (Entry<String, String> entry : params) {
					String key = entry.getKey().trim();
					String value = entry.getValue().trim();
					if (hasParams) {
						builder.append("&");
					} else {
						hasParams = true;
					}
					if (charset != null && !"".equals(charset)) {
						// builder.append(key).append("=").append(URLDecoder.(value,charset));
						builder.append(key).append("=").append(IOUtil.urlEncode(value, charset));
					} else {
						builder.append(key).append("=").append(value);
					}
				}
				result = builder.toString();
			}
		}
		return doUrlPath(path, result).toString();
	}

	/**
	 * 设置连接参数
	 * 
	 * @param path
	 *            路径
	 * @return
	 */
	private static URL doUrlPath(String path, String query) throws Exception {
		URL url = new URL(path);
		if (StringUtils.isEmpty(path)) {
			return url;
		}
		if (StringUtils.isEmpty(url.getQuery())) {
			if (path.endsWith("?")) {
				path += query;
			} else {
				path = path + "?" + query;
			}
		} else {
			if (path.endsWith("&")) {
				path += query;
			} else {
				path = path + "&" + query;
			}
		}
		return new URL(path);
	}

	/**
	 * 默认的http请求执行方法,返回
	 * 
	 * @param method 请求的方法 POST/GET
	 * @param path 请求path 路径
	 * @param map 请求参数集合
	 * @param data 输入的数据 允许为空
	 * @return
	 */
	public static String HttpDefaultExecute(String method, String path, Map<String, String> map, String data, String encoding) {
		String result = "";
		try {
			String url = setParmas((TreeMap<String, String>) map, path, "");
			result = defaultConnection(method, url, DEFAULT_CONNTIME, DEFAULT_READTIME, data, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 默认的https执行方法,返回
	 * 
	 * @param method
	 *            请求的方法 POST/GET
	 * @param path
	 *            请求path 路径
	 * @param map
	 *            请求参数集合
	 * @param data
	 *            输入的数据 允许为空
	 * @return
	 */
	public static String HttpsDefaultExecute(String method, String path, Map<String, String> map, String data, String encoding) {
		String result = "";
		try {
			String url = setParmas((TreeMap<String, String>) map, path, "");
			result = defaultConnection(method, url, DEFAULT_CONNTIME, DEFAULT_READTIME, data, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取客户端ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		// squid的squid.conf 的配制文件中forwarded_for项改为off时
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		// 多级反向代理
		if (ip != null && ip.indexOf(",") > 0 && ip.split(",").length > 1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

}

/**
 * 自定义信任管理器
 * 
 * @author phil
 * @date 2017年7月2日
 */
class MyX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
