package com.ryw.server.common;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.ryw.server.util.Utils;

/**
 * restTemplate 扩展
 * @author ryw
 *
 */
@Component
@RefreshScope
public class RestTemplateExt {
	
	private static final Logger logger = LoggerFactory.getLogger(RestTemplateExt.class);
	
	@Autowired
	@Qualifier("loadBalanced")
	private RestTemplate restTemplate;
	
	public <T> T exchange(String url, String action, HttpMethod method, Object params, Class<T> responseType) {
		return exchange(url, action, method, params, responseType, InvocationHolder.getLogId());
	}
	
	public <T> T exchange(String url, String action, HttpMethod method, Object params, Class<T> responseType, String guiid) {
		//请求头
		HttpHeaders headers = new HttpHeaders();
		MimeType mimeType = MimeTypeUtils.parseMimeType("application/json");
		MediaType mediaType = new MediaType(mimeType.getType(), mimeType.getSubtype(), Charset.forName("UTF-8"));
		headers.setContentType(mediaType);
		
		if (params instanceof Map) {
			((Map) params).put("logId", guiid);
		}
		
		String paramStr = JSON.toJSONString(params);
		HttpEntity<String> entity = new HttpEntity<>(paramStr, headers);
		
		ResponseEntity<T> responseEntity = restTemplate.exchange(initUrl(url, action), method, entity, responseType);
		return responseEntity.getBody();
	}
	
	public String initUrl(String targetUrl, String requestAction) {
		String url = targetUrl + requestAction;
		logger.info("request url:-->" + url);
		return url;
	}
	
	/**
	 * POST 发送	请求
	 * @param targetUrl
	 * @param requestAction
	 * @param params
	 * @param responseType
	 * @return
	 */
	public <T> T postForObject(String targetUrl, String requestAction, Object params, Class<T> responseType) {
		return exchange(targetUrl, requestAction, HttpMethod.POST, params, responseType);
	}
	
	/**
	 * POST 发送请求
	 * @param targetUrl
	 * @param requestAction
	 * @param params
	 * @param responseType
	 * @param guiid
	 * @return
	 */
	public <T> T postForObject(String targetUrl, String requestAction, Object params, Class<T> responseType, String guiid) {
		return exchange(targetUrl, requestAction, HttpMethod.POST, params, responseType, guiid);
	}
	
	/**
	 * GET 发送请求
	 * @param targetUrl
	 * @param requestAction
	 * @param params
	 * @param responseType
	 * @return
	 */
	public <T> T getForObject(String targetUrl, String requestAction, Object params, Class<T> responseType) {
		return getForObject(targetUrl, requestAction, params, responseType, InvocationHolder.getLogId());
	}
	
	/**
	 * GET 发送请求
	 * @param targetUrl
	 * @param requestAction
	 * @param params
	 * @param responseType
	 * @param guiid
	 * @return
	 */
	public <T> T getForObject(String targetUrl, String requestAction, Object params, Class<T> responseType, String guiid) {
		
		boolean flag = false;
		StringBuffer sb = new StringBuffer(requestAction);
		sb.append("?");
		
		if (params instanceof Map) {
			for (Iterator iterator = ((Map) params).keySet().iterator(); iterator.hasNext();) {
				String paramStr = null;
				String key = (String) iterator.next();
				Object value = ((Map) params).get(key);
				
				//参数值为空判断
				if (ObjectUtils.isEmpty(value)) {
					continue;
				}
				//参数值类型判断
				if (value instanceof String) {
					paramStr = String.valueOf(value);
				}else if (value.getClass().isArray()) {
					paramStr = Utils.Array2String((Object[])value);
				}else if (value instanceof Collection) {
					paramStr = Utils.Array2String(((Collection) value).toArray());
				}else {
					continue;
				}
				
				if (flag) {
					sb.append("&");
				}
				sb.append(key);
				sb.append("=");
				sb.append(paramStr);
				flag = true;
			}
		}
		return exchange(targetUrl, sb.toString(), HttpMethod.GET, null, responseType, guiid);
	}
	
	/**
	 * PUT 发送请求
	 * @param targetUrl
	 * @param requestAction
	 * @param params
	 * @param responseType
	 * @return
	 */
	public <T> T putForObject(String targetUrl, String requestAction, Object params, Class<T> responseType) {
		return exchange(targetUrl, requestAction, HttpMethod.PUT, params, responseType, InvocationHolder.getLogId());
	}
	
	/**
	 * PUT 发送请求
	 * @param targetUrl
	 * @param requestAction
	 * @param params
	 * @param responseType
	 * @param guiid
	 * @return
	 */
	public <T> T putForObject(String targetUrl, String requestAction, Object params, Class<T> responseType, String guiid) {
		return exchange(targetUrl, requestAction, HttpMethod.PUT, params, responseType);
	}
	
	/**
	 * DELETE 发送请求
	 * @param targetUrl
	 * @param requestAction
	 * @param params
	 * @param responseType
	 * @return
	 */
	public <T> T deleteForObject(String targetUrl, String requestAction, Object params, Class<T> responseType) {
		return exchange(targetUrl, requestAction, HttpMethod.PUT, params, responseType,InvocationHolder.getLogId());
	}
	
	/**
	 * DELETE 发送请求
	 * @param targetUrl
	 * @param requestAction
	 * @param params
	 * @param responseType
	 * @param guiid
	 * @return
	 */
	public <T> T deleteForObject(String targetUrl, String requestAction, Object params, Class<T> responseType, String guiid) {
		return exchange(targetUrl, requestAction, HttpMethod.PUT, params, responseType);
	}
	
	
	/**
	 * SEND 发送请求，请求方式会自动根据 requestAction 获取
	 * @param targetUrl
	 * @param requestAction
	 * @param params
	 * @param responseType
	 * @return
	 */
	public <T> T send(String targetUrl, String requestAction, Object params, Class<T> responseType) {
		String methodStr = requestAction.substring(requestAction.lastIndexOf("_") + 1, requestAction.length());
		requestAction = requestAction.substring(0, requestAction.lastIndexOf("_"));
		
		RequestMethod method = RequestMethod.valueOf(methodStr);
		return send(targetUrl, requestAction, method, params, responseType, InvocationHolder.getLogId());
	}
	
	/**
	 * SEND 发送请求，需指定请求方式
	 * @param targetUrl
	 * @param requestAction
	 * @param method
	 * @param params
	 * @param responseType
	 * @return
	 */
	public <T> T send(String targetUrl, String requestAction, RequestMethod method, Object params, Class<T> responseType) {
		return send(targetUrl, requestAction, method, params, responseType, InvocationHolder.getLogId());
	}
	
	/**
	 * SEND 发送请求,需指定请求方式以及请求唯一ID
	 * @param targetUrl
	 * @param requestAction
	 * @param method
	 * @param params
	 * @param responseType
	 * @param guiid
	 * @return
	 */
	public <T> T send(String targetUrl, String requestAction, RequestMethod method, Object params, Class<T> responseType, String guiid) {
		T returnObj = null;
		
		switch (method) {
		case POST:
			returnObj = postForObject(targetUrl, requestAction, params, responseType, guiid);
			break;
		case GET:
			returnObj = getForObject(targetUrl, requestAction, params, responseType, guiid);
			break;
		case DELETE:
			returnObj = deleteForObject(targetUrl, requestAction, params, responseType, guiid);
			break;
		case PUT:
			returnObj = putForObject(targetUrl, requestAction, params, responseType, guiid);
			break;
		default:
			break;
		}
		return returnObj;
	}
}
