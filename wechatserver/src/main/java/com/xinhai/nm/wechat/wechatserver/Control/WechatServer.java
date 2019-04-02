package com.xinhai.nm.wechat.wechatserver.Control;

import com.xinhai.nm.wechat.wechatserver.Common.AppConfig;
import com.xinhai.nm.wechat.wechatserver.Manual.HttpRequest;
import com.xinhai.nm.wechat.wechatserver.Manual.MessageServer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class WechatServer extends AppConfig {

    @RequestMapping("/customerTyping")
    public String customerTyping(Model model, HttpServletRequest request) throws IOException {
        MessageServer messageServer = new MessageServer();

        String openid = request.getParameter("openid");
        String command = request.getParameter("command");
        List list = new ArrayList();
        list.add(openid);
        list.add(command);

        Boolean verify = contentInspection(list);
        String result = "";
        if (verify == false) {
            result = returnMessage(4000, "");
        } else {
            result = messageServer.customerTyping(openid, command);
        }

        return result;
    }

    @RequestMapping("/sendMessage")
    public String sendMessage(Model model, HttpServletRequest request) throws IOException {
        MessageServer messageServer = new MessageServer();

        String openid = request.getParameter("openid");
        String msg = request.getParameter("message");
        List<String> list = new ArrayList<String>();
        list.add(openid);
        list.add(msg);

        Boolean aBoolean = contentInspection(list);
        String result = "";
        if (aBoolean == false) {
            result = returnMessage(4000, "");
        } else {
            result = messageServer.sendCustomerMessage(openid, msg, "");
        }
        return result;
    }
}
