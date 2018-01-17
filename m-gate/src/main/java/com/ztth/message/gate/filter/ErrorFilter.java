package com.ztth.message.gate.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ErrorFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        //优先级，数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器，true代表需要过滤
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        if(ctx.getResponseBody() == null){
            Map <String,Object> map = new HashMap<String,Object>();
            map.put("code",404);
            map.put("msg","request not find!");

            ctx.setResponseBody(JSON.toJSONString(map));
            ctx.setSendZuulResponse(false);
        }
        System.out.println();

/*
        log.info("进入异常过滤器");
        System.out.println(ctx.getResponseBody());
        ctx.setResponseBody("出现异常");
*/
        return null;

    }

}