package poe.reborn.com.http;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Exslims
 * 19.06.2016
 */
@Component
@Scope("singleton")
public class HttpRequestManager extends Thread {
    @Autowired
    @Qualifier("poeApiHttpClient")
    private PoeHttpClient<String> httpClient;

    private List<Integer> listOfFileSize;
    private long sizeOfAllReceiveData = 0;

    @Override
    public void run() {
        String nextChangeId = "";
        listOfFileSize = new ArrayList<>();
        while (true) {
            String content = httpClient.getHtmlResponse(nextChangeId);

            int fileSize = httpClient.getFileSize();
            sizeOfAllReceiveData += fileSize;
            listOfFileSize.add(fileSize);

            nextChangeId = StringUtils.substringBetween(content, "next_change_id\":\"", "\",\"stashes\"");
            if(content.contains("exslims")){
                System.out.println("Found EXSLIMS");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setHttpClient(PoeHttpClient<String> httpClient) {
        this.httpClient = httpClient;
    }

    public List<Integer> getListOfFileSize() {
        List<Integer> filesSize = new ArrayList<>(listOfFileSize);
        listOfFileSize.clear();
        return filesSize;
    }

    public long getSizeOfAllReceiveData() {
        return sizeOfAllReceiveData;
    }
}
