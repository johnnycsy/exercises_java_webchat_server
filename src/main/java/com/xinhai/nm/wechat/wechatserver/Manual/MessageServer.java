package com.xinhai.nm.wechat.wechatserver.Manual;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinhai.nm.wechat.wechatserver.Common.AppConfig;
import com.xinhai.nm.wechat.wechatserver.Common.WechatCommon;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MessageServer extends AppConfig {

    /**
     * GET https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     * 获取小程序全局唯一后台接口调用凭据（access_token）。调调用绝大多数后台接口时都需使用 access_token，开发者需要进行妥善保存。
     * grant_type	string		是	填写 client_credential
     * appid	        string		是	小程序唯一凭证，即 AppID，可在「微信公众平台 - 设置 - 开发设置」页中获得。（需要已经成为开发者，且帐号没有异常状态）
     * secret	        string		是	小程序唯一凭证密钥，即 AppSecret，获取方式同 appid
     */
    private String getAccessToken() throws IOException {
        WechatCommon wechatCommon = new WechatCommon();
        String targetHttp = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wechatCommon.getAppid() + "&secret=" + wechatCommon.getAppSecret();
        /*accesstoken query*/
        HttpRequest httpRequest = new HttpRequest();
        String accessToken = httpRequest.get(targetHttp);
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(accessToken, Map.class);
        String result = "";
        if (map != null) {
            result = map.get("access_token").toString();
        }
        return result;
    }

    /**
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/typing?access_token=ACCESS_TOKEN
     * 下发客服当前输入状态给用户
     * access_token	string		是	接口调用凭证
     * touser	            string		是	用户的 OpenID
     * command	        Strign		是	命令
     * ---------command 的合法值
     * --------------Typing	                对用户下发"正在输入"状态
     * --------------CancelTyping	    取消对用户的"正在输入"状态
     */
    public String customerTyping(String openid, String command) throws IOException {
        String accessToken = getAccessToken();
        String httpSrc = "https://api.weixin.qq.com/cgi-bin/message/custom/typing?access_token=" + accessToken;
        Map<String, String> postData = new HashMap();
        postData.put("access_token", accessToken);
        postData.put("touser", openid);
        postData.put("command", command);
        HttpRequest httpRequest = new HttpRequest();
        String result = httpRequest.post(httpSrc, postData, "json");
        return result;
    }

    /**
     * GET https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
     * 获取客服消息内的临时素材。即下载临时的多媒体文件。目前小程序仅支持下载图片文件。
     * access_token	string		是	接口调用凭证
     * media_id         	string		是	媒体文件 ID
     * */

    /**
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     * 发送客服消息给用户。详细规则见 发送客服消息
     * access_token	     string		是	接口调用凭证
     * touser	                 string		是	用户的 OpenID
     * msgtype	             string		是	消息类型
     * content	             string		是	文本消息内容，msgtype="text" 时必填
     * image	                Object		是	图片消息，msgtype="image" 时必填
     * link	                    Object		是	图片消息，msgtype="link" 时必填
     * miniprogrampage	Object		是	小程序卡片，msgtype="miniprogrampage" 时必填
     */
    public String sendCustomerMessage(String openid, String content, String msgtype) throws IOException {
        String accessToken = getAccessToken();
        String httpUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken;
        Map<String, String> map = new HashMap<>();
        //map.put("access_token", accessToken);
        map.put("touser", openid);
        if (msgtype == null || msgtype == "") {
            map.put("msgtype", "text");
            map.put("text", "{content:" + content + "}");
        }
        HttpRequest httpRequest = new HttpRequest();
        String result = httpRequest.post(httpUrl, map, "json");
        return result;
    }

    /**
     * POST https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
     * 把媒体文件上传到微信服务器。目前仅支持图片。用于发送客服消息或被动回复用户消息。
     * access_token	string		是	接口调用凭证
     * type	                string		是	文件类型
     * media	            file		是	form-data 中媒体文件标识，有filename、filelength、content-type等信息
     * */


}
