package com.ztth.api.path.rest;

import com.ztth.api.path.biz.ApiPathBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiPathController {

    @Autowired
    private ApiPathBiz apiPathBiz;

    @RequestMapping(value = "/pathAll")
    @ResponseBody
    public ResponseEntity<?> getMenuListAll() throws Exception {
        return ResponseEntity.status(200).body(apiPathBiz.selectListAll());
    }



}
