package com.aq.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase{
	
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
	
	@BeforeMethod
	public void setUP() throws ClientProtocolException, IOException{
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		url = serviceUrl+apiUrl;
	}
	
	@Test 
	public void getAPITest() throws ClientProtocolException, IOException{
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url);
	
		//Status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode(); 
		System.out.println("Staus Code: "+ statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status is not 200");

		//JSON String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
			
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("Response JSON from API:" + responseJSON);
			
		String perPageValue = TestUtil.getValueByJPath(responseJSON, "/per_page");
		System.out.println("total value of per_page: "+perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		//get values from JSON array
		String lastname = TestUtil.getValueByJPath(responseJSON, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJSON, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJSON, "/data[0]/avatar");
		String first_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/first_name");
		System.out.println( first_name+" "+lastname +", "+id+", "+avatar );
		
		//All Headers
		Header[] headerArray = closeableHttpResponse.getAllHeaders();  //Header type array
		HashMap<String, String> allHeaders = new HashMap<String, String>();  //Simple hasmap of String, String

		//Navigate within headerArray & put in single Header type variable
		//Then extract key, value pair from header variable & put in hashMap
		for(Header header : headerArray){
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Header Array: " + allHeaders);
	}
	
	@Test
	public void getAPITestWithHeadrs() throws ClientProtocolException, IOException{
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-type", "application/json");
		
		closeableHttpResponse = restClient.get(url, headerMap);
	
		//Status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode(); 
		System.out.println("Staus Code: "+ statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status is not 200");

		//JSON String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
			
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("Response JSON from API:" + responseJSON);
			
		String perPageValue = TestUtil.getValueByJPath(responseJSON, "/per_page");
		System.out.println("total value of per_page: "+perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		//get values from JSON array
		String lastname = TestUtil.getValueByJPath(responseJSON, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJSON, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJSON, "/data[0]/avatar");
		String first_name = TestUtil.getValueByJPath(responseJSON, "/data[0]/first_name");
		System.out.println( first_name+" "+lastname +", "+id+", "+avatar );
		
		//All Headers
		Header[] headerArray = closeableHttpResponse.getAllHeaders();  //Header type array
		HashMap<String, String> allHeaders = new HashMap<String, String>();  //Simple hasmap of String, String

		//Navigate within headerArray & put in single Header type variable
		//Then extract key, value pair from header variable & put in hashMap
		for(Header header : headerArray){
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Header Array: " + allHeaders);
	}
}
