package com.caotinging.java8action.chap5;

import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @program: Java8Action
 * @description: 从文件生成流
 * @author: CaoTing
 * @create: 2019/11/16
 */
public class StreamFromFile {

    public static void main(String[] args) throws IOException {
        // 读取文件中的内容生成流 - 带资源的try语句块，自动关闭文件IO流
        try (Stream<String> lines = Files.lines(Paths.get(Resources.getResource("data2.txt").toURI()), StandardCharsets.UTF_8)) {
            lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .forEach(System.out::println);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
