package poe.reborn.com.http;

import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;

/**
 * Exslims
 * 15.05.2016
 */
public abstract class PoeHttpClient<P> {
    protected Logger log = Logger.getLogger(this.getClass());
    protected HttpClient httpClient;
    public abstract String getHtmlResponse(P arg);
    public abstract int getFileSize();
    public abstract String getContent();
}
