package poe.reborn.com.http;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * Exslims
 * 01.06.2016
 */
@Component
@Scope("prototype")
public class PoeApiHttpClient extends PoeHttpClient<String> {
    private int fileSize;

    @Override
    public String getHtmlResponse(String arg) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

        defaultHttpClient.addRequestInterceptor((request, context) -> {
            if (!request.containsHeader("Accept-Encoding")) {
                request.addHeader("Accept-Encoding", "gzip");
            }
        });

        defaultHttpClient.addResponseInterceptor((response, context) -> {
            response.setEntity(new GzipDecompressingEntity(response.getEntity()));
        });

        HttpResponse response;
        HttpGet httpGet;

        if(!arg.equals("")) {
            httpGet = new HttpGet("http://www.pathofexile.com/api/public-stash-tabs?id=" + arg);
        }else{
            httpGet = new HttpGet("http://www.pathofexile.com/api/public-stash-tabs");
        }
        try {
            response = defaultHttpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            String content = "";
            if (entity != null) {
                content = EntityUtils.toString(entity);
                fileSize = content.length();
            }
            defaultHttpClient.getConnectionManager().shutdown();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        defaultHttpClient.getConnectionManager().shutdown();
        return "";
    }

    @Override
    public int getFileSize() {
        return fileSize / 1024;
    }

    static class GzipDecompressingEntity extends HttpEntityWrapper {

        public GzipDecompressingEntity(final HttpEntity entity) {
            super(entity);
        }

        @Override
        public InputStream getContent() throws IOException, IllegalStateException {
            InputStream wrappedin = wrappedEntity.getContent();

            return new GZIPInputStream(wrappedin);
        }

        @Override
        public long getContentLength() {
            return -1;
        }

    }
}