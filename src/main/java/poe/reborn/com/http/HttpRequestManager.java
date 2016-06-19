package poe.reborn.com.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Exslims
 * 19.06.2016
 */
@Component
@Scope("singleton")
public class HttpRequestManager {
    @Autowired
    @Qualifier("poeApiHttpClient")
    private PoeHttpClient<String> httpClient;

    @PostConstruct
    public void init(){
        
    }
    public void setHttpClient(PoeHttpClient<String> httpClient) {
        this.httpClient = httpClient;
    }
}
