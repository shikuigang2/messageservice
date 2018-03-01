package com.ztth.api.path.feignService;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("m-kafkaproducer")
public interface KafkaService {

    @RequestMapping("/addqueue")
    boolean addQueue(@RequestParam("message") String message);
}
