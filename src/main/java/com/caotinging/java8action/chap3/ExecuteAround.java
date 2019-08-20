package com.caotinging.java8action.chap3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 资源处理（例如处理文件或数据库）时一个常见的模式就是打开一个资源，做一些处理，然后关闭资源。
 * 这个设置和清理阶段总是很类似，并且会围绕着执行处理的那些重要代码。
 * 这就是所谓的环绕执行（execute around）模式
 *
 * @program: Java8Action
 * @description: 环绕执行模式
 * @author: CaoTing
 * @create: 2019/7/29
 */
public class ExecuteAround {

    // 1.定义一个函数式接口
    public interface BufferedReaderProcessor {
        String process(BufferedReader reader) throws IOException;
    }

    // 2.定义一个环绕式的方法
    public String processFile(BufferedReaderProcessor processor) throws IOException {
        // 2.1 带资源的try语句，会在方法结束后自动释放资源。简化了代码 实际上就是环绕式的方法
        try (BufferedReader br = new BufferedReader(new FileReader("lambdasinaction/chap3/data.txt"))) {
            // 2.2 真正的核心行为 --现在这个方法可以通过lambda自定义读取该文件的第一行或者最后一行等等
            return processor.process(br);
        }
    }

    // 3.调用
    public static void main(String[] args) {
        System.out.println("================== 环绕执行模式 ====================");

    }
}
