package poe.reborn.com.http;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    private PrintWriter writer;
    private List<Integer> listOfFileSize;
    private long sizeOfAllReceiveData = 0;

    @Override
    public void run() {
        String nextChangeId = "";
        try {
            writer = new PrintWriter(new File("reports.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listOfFileSize = Collections.synchronizedList(new ArrayList<>());
        while (true) {
            String content = httpClient.getHtmlResponse(nextChangeId);

            int fileSize = httpClient.getFileSize();
            sizeOfAllReceiveData += fileSize;
            listOfFileSize.add(fileSize);

            nextChangeId = StringUtils.substringBetween(content, "next_change_id\":\"", "\",\"stashes\"");
            writer.println("Next change id = " + nextChangeId + "\n");
            writer.println("Chunk of content:" + "\n");
            writer.println(StringUtils.substringsBetween(content,"{","}")[0] + "\n");
            writer.flush();

            if(content.contains("exslims")){
                System.out.println("Found EXSLIMS");
            }
            try {
                Thread.sleep(new Random().nextInt(3)*1000);
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
