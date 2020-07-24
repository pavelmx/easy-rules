package com.example.innowise.config;

import com.example.innowise.listener.MyFileChangeListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.File;
import java.time.Duration;

@Configuration
public class FileWatcherConfig {

    @Value("${rules.location}")
    private String rulesLocation;

    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofSeconds(10L), Duration.ofSeconds(3L));
        fileSystemWatcher.addSourceDirectory(new File(rulesLocation));
        fileSystemWatcher.addListener(fileChangeListener());
        fileSystemWatcher.start();
        return fileSystemWatcher;
    }

    @Bean
    public MyFileChangeListener fileChangeListener() {
        return new MyFileChangeListener();
    }

    @PreDestroy
    public void onDestroy() {
        fileSystemWatcher().stop();
    }
}