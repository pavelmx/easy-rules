package com.example.innowise.listener;

import com.example.innowise.service.RulesService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

public class MyFileChangeListener implements FileChangeListener {

    @Autowired
    private RulesService rulesService;

    @SneakyThrows
    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for (ChangedFiles changedFiles : changeSet) {
            for (ChangedFile changedFile : changedFiles.getFiles()) {
                if ((changedFile.getType().equals(ChangedFile.Type.MODIFY) || changedFile.getType().equals(ChangedFile.Type.ADD)) && !isLocked(changedFile.getFile().toPath())) {
                    System.out.println("Was changed ruleFile: " + changedFile.getFile().getName());
                    rewritingRules(changedFile.getFile().getName());
                }

                if (changedFile.getType().equals(ChangedFile.Type.DELETE)) {
                    System.out.println("Was deleted ruleFile: " + changedFile.getFile().getName());
                    deleteRules(changedFile.getFile().getName());
                }
            }
        }
    }

    private void rewritingRules(String fileName) throws Exception {
        rulesService.createNewYmlRules(fileName);
    }

    private void deleteRules(String fileName) {
        rulesService.deleteRules(fileName);
    }

    private boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }

}
