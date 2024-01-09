package com.web.albion.Controller;


import com.sun.management.OperatingSystemMXBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

@RestController
@RequestMapping("/systeminfo")
public class SysteminfoController {

    @GetMapping("/memory")
    public String getMemoryUsed() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
        long usedMemory = heapMemoryUsage.getUsed();
        usedMemory = usedMemory / 1000 / 1000; // mb
        long maxMemory = heapMemoryUsage.getMax();
        maxMemory = maxMemory / 1000 / 1000; // mb

        String result = "memory : [" + usedMemory + "MB / " + maxMemory + "MB]";
        return result;
    }

    @GetMapping("/cpu")
    public String getCpuUsed() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuUsage = osBean.getProcessCpuLoad() * 100;
        String result = "cpu : " + cpuUsage;
        return result;
    }
}
