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
@RequestMapping("/test")
public class TestController {

    @GetMapping("/systeminfo")
    public String getSystemInfo() {

        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        // CPU 사용량 구하기
        double cpuUsage = osBean.getProcessCpuLoad() * 100; // %로 표시하기 위해 100을 곱합니다.

        // 메모리 사용량 구하기
        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
        long usedMemory = heapMemoryUsage.getUsed();
        usedMemory = usedMemory / 1000 / 1000; // mb
        long maxMemory = heapMemoryUsage.getMax();
        maxMemory = maxMemory / 1000 / 1000; // mb

        // 디스크 사용량 구하기
        File diskPartition = new File("/");
        long totalSpace = diskPartition.getTotalSpace();
        long freeSpace = diskPartition.getFreeSpace();
        long usableSpace = diskPartition.getUsableSpace();
        usableSpace = usableSpace / 1000 / 1000; // kb, mb

        String systemInfo = "현재 CPU 사용량: " + cpuUsage + "%<br>"
                + "사용 중인 메모리: " + usedMemory + " bytes<br>"
                + "최대 사용 가능한 메모리: " + maxMemory + " bytes<br>"
                + "사용 가능한 디스크 공간: " + usableSpace + " MB<br>";

        System.out.println(systemInfo);

        return systemInfo;
    }
}
