package cn.edu.nju.software.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Component
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param params 请求参数
     * @return URL 所代表远程资源的响应结果
     */
    public String sendGet(String url, Map<String, Object> params) {
        String completeUrl = getCompleteUrl(url, params);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(responseErrorHandler);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(completeUrl, String.class);
        return responseEntity.getBody();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param body 请求参数
     * @return 所代表远程资源的响应结果
     */
    public String sendPost(String url, Map<String, Object> params, Map<String, Object> body) {
        String completeUrl = getCompleteUrl(url, params);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(responseErrorHandler);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(body), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(completeUrl, HttpMethod.POST, httpEntity, String.class);
        return responseEntity.getBody();
    }

    /**
     * 组合url和params
     *
     * @param url 基础访问路径
     * @param params 访问参数
     *
     * @return 组合好的访问路径
     * */
    private String getCompleteUrl(String url, Map<String, Object> params) {
        StringBuilder paramsStr = new StringBuilder();
        String completeUrl;
        if (params != null && params.keySet().size() != 0) {
            String[] keys = new String[params.keySet().size()];
            params.keySet().toArray(keys);
            for (int i = 0; i < keys.length; i++) {
                paramsStr.append(keys[i]).append('=').append(params.get(keys[i]));
                if (i != keys.length - 1) {
                    paramsStr.append('&');
                }
            }
            completeUrl = url + "?" + paramsStr.toString();
        } else {
            completeUrl = url;
        }
        return completeUrl;
    }

    private ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {

        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            logger.error(clientHttpResponse.getStatusText());
            return true;
        }

        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

        }
    };

}
