package poe.reborn.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import poe.reborn.com.http.HttpRequestManager;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Exslims
 * 19.06.2016
 */
@RestController
public class StatisticController {
    @Autowired
    private HttpRequestManager manager;

    @RequestMapping(value = "/nextFilesSize", method = RequestMethod.GET)
    public List<Integer> getNextFilesSize(){
        return manager.getListOfFileSize();
    }

    @RequestMapping(value = "/fullSize", method = RequestMethod.GET)
    public Long getFullSize(){
        return manager.getSizeOfAllReceiveData();
    }

    @PostConstruct
    public void init(){
        manager.start();
    }

    public void setManager(HttpRequestManager manager) {
        this.manager = manager;
    }
}
