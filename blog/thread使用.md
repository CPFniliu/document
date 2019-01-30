# 线程使用

## 通过标记启动或停止一个线程

```java
package cn.cpf.exercise.printer.printer;

import cn.cpf.exercise.printer.common.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPrinter {

    /**
     * 打印机ID
     */
    private String id = null;

    /**
     * 效验机
     */
    private PrintVerifier printVerifier = null;

    /**
     * 是否去重复
     */
    protected boolean distinct = true;

    /**
     * 保存打印的行 (用于去重复)
     */
    private Set<String> set = null;

    /**
     * 打印总行
     */
    private int totalLineCount = -1;

    /**
     * 打印完成行
     */
    private int finishedLineCount = -1;

    /**
     * 打印机内线程
     */
    private PrintThread thread = null;

    protected AbstractPrinter(String id) {
        this.id = id;
    }

    /**
     * 打印文件
     * 解析 path 路径, 并根据文件路径创建打印后的文件,
     * 循环读取文件行, 进行解析, 写入打印后的文件中
     *
     * @param path 需读取的文件路径
     * @throws IOException
     */
    private void printFile(String path) throws IOException {
        // 对文件进行效验
        File file = new File(path);
        if (! file.exists()) {
            throw new FileNotFoundException("定位文件时发现文件不存在");
        }
        if (!file.isFile()) {
            throw new RuntimeException("未发现可用文件");
        }

        // 进行相关初始化
        // 获取文件中总行数
        totalLineCount = Utils.getFileLineCount(file);
        finishedLineCount = 0;
        if (distinct) {
            set = new HashSet<>();
        }

        PrintFileReader fileReader = new PrintFileReader();
        PrintFileWriter fileWriter = new PrintFileWriter();
        try {
            // 打开待读取文件
            fileReader.openFile(file);
            // 打开输出文件, 没有则新建
            fileWriter.openOrNewFile(Utils.appendSuffix(file.getPath(), "_out_" + id));
            String line;
            while (null != (line = fileReader.readLine())) {
                String tidyStr = disposeString(line);
                // 如果 不需要去重复 或 需要去重复但是所要打印字符串并不重复 则打印相应字符串
                if (!distinct || addString(tidyStr)) {
                    fileWriter.writeLine(tidyStr);
                    // 如果设置了效验机, 则将信息发送给效验机进行效验
                    if (printVerifier != null) {
                        printVerifier.addMessage(this, tidyStr);
                    }
                }
                // 行数 + 1
                finishedLineCount++;
            }
        } finally {
            fileReader.close();
            fileWriter.close();
            set = null;
        }
    }

    /**
     * 打印一行
     * @param str
     */
    public void print(String str) {
        boolean distinctTmp = distinct;
        distinct = false;
        printLine(str);
        distinct = distinctTmp;
    }

    /**
     * 获取打印的进度
     * @return [totalLineCount, finishedLineCount]
     */
    public int[] getProgress() {
        int[] rst = {totalLineCount, finishedLineCount};
        return rst;
    }


    /**
     * 打印一行
     *
     * @param str
     */
    protected void printLine(String str) {
        String tidyStr = disposeString(str);
        // 如果 不需要去重复 或 需要去重复但是所要打印字符串并不重复 则打印相应字符串
        if (!distinct || addString(tidyStr)) {
            printString(tidyStr);
            if (printVerifier != null) {
                printVerifier.addMessage(this, tidyStr);
            }
        }
    }

    /**
     * 对打印的行进行处理
     *
     * @param str
     * @return
     */
    protected abstract String disposeString(String str);

    /**
     * 将需要打印的字符串字符串添加进 set 集合
     *
     * @param str 需要打印的字符串
     * @return 如果添加成功 (集合中没有该字符串), 则返回 true
     */
    protected boolean addString(String str) {
        if (set == null) {
            System.out.println("line");
            return true;
        }
        return set.add(str);
    }

    /**
     * 打印机打印字符串
     *
     * @param string 需要打印的字符串
     */
    protected void printString(String string){
        System.out.println(string);
    }

    /**
     * 启动线程打印文件
     *
     * @param filePath
     */
    public void startPrint(String filePath) {
        if (thread != null && thread.isAlive()) {
            return;
        }
        thread = new PrintThread();
        thread.setPrint(filePath);
        thread.start();
    }

    private class PrintThread extends Thread {

        private String filePath = null;

        public void setPrint(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public void run() {
            try {
                printFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 进行效验
     *
     * @param string
     * @return
     */
    public abstract boolean verify(String string);

    /**
     * 设置打印机的效验器
     *
     * @param printVerifier
     */
    public void setPrintVerifier(PrintVerifier printVerifier) {
        this.printVerifier = printVerifier;
    }

    public String getId() {
        return id;
    }
}


```