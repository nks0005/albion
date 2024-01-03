package com.web.albion.Service;


import com.web.albion.Thread.DataProcessManager;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminBoardService {

    @Autowired
    DataProcessManager data_process_manager;

    public void runThread(){
        data_process_manager.startProcess();
    }

    public void stopThread(){
        data_process_manager.stopProcess();
    }

    public boolean getIsRunning(){
        return data_process_manager.getIsRunning();
    }


}
