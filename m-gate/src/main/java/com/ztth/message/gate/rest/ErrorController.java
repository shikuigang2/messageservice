package com.ztth.message.gate.rest;

import com.netflix.zuul.context.RequestContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class ErrorController {

    @RequestMapping(value = "/error")
    @ResponseBody
    public ResponseEntity<?> error() throws Exception {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ResponseEntity.status(200).body(ctx.getResponseBody());

    }

}
