package com.xinhai.nm.wechat.wechatserver.Common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class AppConfig {

    /*统一报文内容*/
    private String messageSelect(Integer key) {
        String[] msg = new String[99999];
        /*公共错误码*/
        msg[0] = "成功";
        msg[1] = "系统繁忙";
        msg[404] = "当前访问无效/当前访问地址不存在";
        msg[401] = "HTTP请求参数不符合要求";
        msg[503] = "调用额度已超出限制";
        msg[504] = "服务故障";
        msg[4000] = "缺少必要参数，或者参数值格式不正确，具体错误信息请查看错误描述 data 字段。";
        msg[4100] = "签名鉴权失败。";
        msg[4200] = "请求已经过期。";
        msg[4300] = "帐号被封禁，或者不在接口针对的用户范围内等。";
        msg[4400] = "请求的次数超过了配额限制，请 提交工单 联系客服处理。";
        msg[4500] = "请求的访问与实际配置不想符。";
        msg[4600] = "协议不支持。";
        msg[5100] = "接口生成凭证错误，属于后端服务错误。";
        msg[6000] = "服务器内部错误。";
        msg[6100] = "版本暂不支持。";
        msg[6200] = "接口暂时无法访问。";
        msg[6300] = "数据为空。";
        msg[6086] = "手机号码错误。";
        msg[6404] = "当前信息不存在。";
        return msg[key].toString();
    }

    /*统一JSON报文*/
    public String returnMessage(Integer code, String data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String msg = messageSelect(code);
        Map<String, Object> message = new HashMap();
        message.put("code", code);
        message.put("msg", msg);
        message.put("data", data);
        return objectMapper.writeValueAsString(message);
    }

    /*判断参数是否为空*/
    public Boolean contentInspection(List str) {
        for (int i = 0; i < str.size(); i++) {
            if (str.get(i) == null || str.get(i) == "") {
                return false;
            }
        }
        return true;
    }

}
