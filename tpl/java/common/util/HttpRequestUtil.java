package {{groupid}}.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;  
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

/**
 * HttpRequest 工具
 */
public class HttpRequestUtil {

	/**
	 * 
	 * @param url
	 * @param keyType
	 *            "PKCS12" 证书类型
	 * @param keyPath
	 *            证书地址
	 * @param keyPawd
	 *            证书密码
	 * @param xml
	 */
	public static String postSslXml(String url, String keyType, String keyPath,
			String keyPawd, String xml) {
		CloseableHttpClient httpclient = null;
		String r = null;
		try {
			KeyStore trustStore = KeyStore.getInstance(keyType);
			FileInputStream instream = new FileInputStream(new File(keyPath));
			try {

				trustStore.load(instream, keyPawd.toCharArray());
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					instream.close();
				} catch (Exception ignore) {
				}
			}
			// 相信自己的CA和所有自签名的证书
			SSLContext sslcontext = SSLContexts
					.custom()
					.loadTrustMaterial(trustStore,
							new TrustSelfSignedStrategy()).build();
			// 只允许使用TLSv1协议

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext, new String[] { "TLSv1" }, null,
					SSLConnectionSocketFactory.getDefaultHostnameVerifier());
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf)
					.build();
			// 创建http请求(postXml方式)
			HttpPost httppost = new HttpPost(url);
			StringEntity uefEntity;

			uefEntity = new StringEntity(xml, "UTF-8");
			uefEntity.setContentEncoding("UTF-8");
			uefEntity.setContentType("application/xml");
			httppost.setEntity(uefEntity);

			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					r = EntityUtils.toString(entity, "UTF-8");
					System.out
							.println("--------------------------------------");
					System.out.println("Response content: "
							+ r);
					System.out
							.println("--------------------------------------");
					
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String get(String url, Map<String, String> param) {
		StringBuffer sb = new StringBuffer();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String r = null;
		try {
			// 创建httpget.
			for (String key : param.keySet()) {
				if (sb.toString().length() > 0) {
					sb.append("&");
				}
				sb.append(key + "=" + param.get(key));
			}

			HttpGet httpget = new HttpGet(url + "?" + sb.toString());
			System.out.println("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					r = EntityUtils.toString(entity, "UTF-8");
					System.out
							.println("--------------------------------------");
					System.out.println("Response content: "
							+ r);
					System.out
							.println("--------------------------------------");
					
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return r;
	}

	/**
	 * post模拟form表单
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String postForm(String url, Map<String, String> param) {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String r = null;
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列
		List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		for (String key : param.keySet()) {
			formparams.add(new BasicNameValuePair(key, param.get(key)));
		}

		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					r = EntityUtils.toString(entity, "UTF-8");
					System.out
							.println("--------------------------------------");
					System.out.println("Response content: "
							+ r);
					System.out
							.println("--------------------------------------");
					
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

	/**
	 * post json
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String postJson(String url, Map<String, String> param) {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String r = null;
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列
//		JSON jsonParam = new JSONObject();
//		for (String key : param.keySet()) {
//			jsonParam.put(key, param.get(key));
//		}
		StringEntity uefEntity;
		try {
			uefEntity = new StringEntity(JSON.toJSONString(param), "UTF-8");
			uefEntity.setContentEncoding("UTF-8");
			uefEntity.setContentType("application/json");
			httppost.setEntity(uefEntity);

			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					r = EntityUtils.toString(entity, "UTF-8");
					System.out
							.println("--------------------------------------");
					System.out.println("Response content: "
							+ r);
					System.out
							.println("--------------------------------------");
					
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

	/**
	 * post xml
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String postXml(String url, String xml) {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String r = null;
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		StringEntity uefEntity;
		try {
			uefEntity = new StringEntity(xml, "UTF-8");
			uefEntity.setContentEncoding("UTF-8");
			uefEntity.setContentType("application/xml");
			httppost.setEntity(uefEntity);

			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					r = EntityUtils.toString(entity, "UTF-8");
					System.out
							.println("--------------------------------------");
					System.out.println("Response content: "
							+ r);
					System.out
							.println("--------------------------------------");
					
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

}
