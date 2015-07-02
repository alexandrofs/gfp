package com.alexandrofs.gfp.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class TesteHttpClient {
 
  private String cookies;
  private HttpClient client = HttpClientBuilder.create().build();
  private final String USER_AGENT = "Mozilla/5.0";
 
  public static void main(String[] args) throws Exception {
 
	String url = "http://bankline.sorocred.com.br/faces/login.jsp";
	String gmail = "http://bankline.sorocred.com.br/faces/nb_sidemenu_investimentos_06376.jsp";
 
	// make sure cookies is turn on
	CookieHandler.setDefault(new CookieManager());
 
	TesteHttpClient http = new TesteHttpClient();
 
	String page = http.GetPageContent(url);
 
	List<NameValuePair> postParams = new ArrayList<NameValuePair>();
	
	postParams.add(new BasicNameValuePair("body:frmnb_login:codcliente", "000018830"));
	postParams.add(new BasicNameValuePair("body:frmnb_login:cpfusuario", "291.503.158-46"));
	postParams.add(new BasicNameValuePair("body:frmnb_login:senhausuario", "PRI301107"));
 
	http.sendPost(url, postParams);
	
	List<NameValuePair> postParams2 = new ArrayList<NameValuePair>();

	postParams2.add(new BasicNameValuePair("body:frm_menu:_id50", "Extrato"));
	postParams2.add(new BasicNameValuePair("body:frm_menu", "body:frm_menu"));

	http.sendPost(gmail, postParams2);

	System.out.println("Done");
  }
 
  private void sendPost(String url, List<NameValuePair> postParams) 
        throws Exception {
 
	HttpPost post = new HttpPost(url);
 
	// add header
	post.setHeader("Host", "bankline.sorocred.com.br");
	post.setHeader("User-Agent", USER_AGENT);
	post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	post.setHeader("Accept-Language", "pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4");
	post.setHeader("Accept-Encoding","gzip, deflate");
	post.setHeader("Cache-Control","max-age=0");
	post.setHeader("Cookie", getCookies());
	post.setHeader("Connection", "keep-alive");
	post.setHeader("Referer", "http://bankline.sorocred.com.br/faces/nb_inicial.jsp");
	post.setHeader("Content-Type", "application/x-www-form-urlencoded");
	post.setHeader("Origin", "http://bankline.sorocred.com.br");
 
	post.setEntity(new UrlEncodedFormEntity(postParams));
 
	HttpResponse response = client.execute(post);
 
	int responseCode = response.getStatusLine().getStatusCode();
 
	System.out.println("\nSending 'POST' request to URL : " + url);
	System.out.println("Post parameters : " + postParams);
	System.out.println("Response Code : " + responseCode);
 
	BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
 
	StringBuffer result = new StringBuffer();
	String line = "";
	while ((line = rd.readLine()) != null) {
		result.append(line);
	}
 
	System.out.println(result.toString());
 
  }
 
  private String GetPageContent(String url) throws Exception {
 
	HttpGet request = new HttpGet(url);
 
	request.setHeader("User-Agent", USER_AGENT);
	request.setHeader("Accept",
		"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	request.setHeader("Accept-Language", "en-US,en;q=0.5");
 
	HttpResponse response = client.execute(request);
	int responseCode = response.getStatusLine().getStatusCode();
 
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);
 
	BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
 
	StringBuffer result = new StringBuffer();
	String line = "";
	while ((line = rd.readLine()) != null) {
		result.append(line);
	}
 
	// set cookies
	setCookies(response.getFirstHeader("Set-Cookie") == null ? "" : 
                     response.getFirstHeader("Set-Cookie").toString());
 
	return result.toString();
 
  }
 
  public List<NameValuePair> getFormParams(
             String html, String username, String password)
			throws UnsupportedEncodingException {
 
	System.out.println("Extracting form's data...");
 
	Document doc = Jsoup.parse(html);
 
	// Google form id
	Element loginform = doc.getElementById("gaia_loginform");
	Elements inputElements = loginform.getElementsByTag("input");
 
	List<NameValuePair> paramList = new ArrayList<NameValuePair>();
 
	for (Element inputElement : inputElements) {
		String key = inputElement.attr("name");
		String value = inputElement.attr("value");
 
		if (key.equals("Email"))
			value = username;
		else if (key.equals("Passwd"))
			value = password;
 
		paramList.add(new BasicNameValuePair(key, value));
 
	}
 
	return paramList;
  }
 
  public String getCookies() {
	return cookies;
  }
 
  public void setCookies(String cookies) {
	this.cookies = cookies;
  }
 
}