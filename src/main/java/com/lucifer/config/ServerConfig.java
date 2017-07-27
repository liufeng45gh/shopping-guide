package com.lucifer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by liufx on 17/3/16.
 */
@Component
public class ServerConfig {

    @Value("${server.resetCache}")
    public Boolean resetCache;

    @Value("${server.port}")
    public String port;
}
