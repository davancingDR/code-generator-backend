package com.rqh.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * 静态文件生成
 * @author rqh
 */
public class StaticGenerator {

    /**
     * 拷贝文件
     * @param inputFile
     * @param outputFile
     */
    public static void copyFile(String inputFile, String outputFile) {
        FileUtil.copy(inputFile, outputFile, false);
    }

    public static void main(String[] args) {
        // 获取整个项目的根路径
        String projectPath = System.getProperty("user.dir");
        File parentFile = new File(projectPath).getParentFile();
        // 输入路径：ACM 示例代码模板目录
        String inputPath = new File(parentFile, "code-generator-demo-projects/acm-template").getAbsolutePath();
        // 输出路径：生成的 ACM 示例代码目录
        String outputPath = projectPath;
        copyFile(inputPath, outputPath);
    }

    /**
     * 递归拷贝文件
     * @param inputPath
     * @param outputPath
     */
    public static void copyFileByRecursive (String inputPath, String outputPath) {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);
        try {
            copyFileByRecursive(inputPath, outputPath);
        } catch (Exception e) {
            System.out.println("拷贝文件失败");
            e.printStackTrace();
        }
    }

    public static void copyFileByRecursive (File inputFile, File outputFile) throws IOException {
        // 区分是文件是还是目录
        if (inputFile.isDirectory()) {
            System.out.println(inputFile.getName());
            File destOuputFile = new File(outputFile, inputFile.getName());
            // 如果是目录，首先创建目标目录。
            if (!destOuputFile.exists()) {
                destOuputFile.mkdirs();
            }
            // 获取目录下的所有文件或子目录
            File[] files = inputFile.listFiles();
            // 无子文件，直接结束
            if (ArrayUtil.isEmpty(files)) {
                return;
            }
            for (File file : files) {
                // 递归，拷贝下一层文件
                copyFileByRecursive(file, destOuputFile);
            }
        } else {
            // 是文件，直接复制到目标目录下
            Path destPath = outputFile.toPath().resolve(inputFile.getName());
            Files.copy(inputFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
