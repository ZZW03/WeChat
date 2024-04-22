package com.zzw.tcp.fegin;


import com.zzw.common.model.req.friend.CheckStatusReq;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name = "Account" ,url = "http://localhost:8080")
public interface MessageClient {

    @RequestMapping(method = RequestMethod.POST, value = "/friend/CheckStatus")
    String CheckStatus( CheckStatusReq req);

}
