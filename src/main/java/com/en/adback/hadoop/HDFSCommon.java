package com.en.adback.hadoop;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;

/**
 * @author Davidx
 * @email 75736409@qq.com
 * @date 2018/12/5
 */
public class HDFSCommon {
    public static final String APP_HDFS_PATH = "/xc/executor/";

    static Configuration conf = new Configuration(true);

    static {
        // 指定hadoop fs的地址
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        conf.set("fs.defaultFS", "hdfs://192.168.1.73:8020");
      //  conf.set("fs.hdfs.impl.disable.cache", "true");   多线程去掉注释
        //conf.set("fs.defaultFS", "hdfs://222.240.140.90:65095");
    }

    /**
     * 判断路径是否存在
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean exits(String path) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        return fs.exists(new Path(path));
    }

    /**
     * 创建文件
     *
     * @param filePath
     * @param contents
     * @throws IOException
     */
    public static void createFile(String filePath, byte[] contents)
            throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path path = new Path(filePath);
        FSDataOutputStream outputStream = fs.create(path);
        outputStream.write(contents);
        outputStream.close();
        fs.close();
    }

    /**
     * 创建文件
     *
     * @param filePath
     * @param fileContent
     * @throws IOException
     */
    public static void createFile(String filePath, String fileContent)
            throws IOException {
        createFile(filePath, fileContent.getBytes());
    }

    /**
     * @param localFilePath
     * @param remoteFilePath
     * @throws IOException
     */
    public static void copyFromLocalFile(String localFilePath,
                                         String remoteFilePath) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path localPath = new Path(localFilePath);
        Path remotePath = new Path(remoteFilePath);
        fs.copyFromLocalFile(false, true, localPath, remotePath);
        fs.close();
    }

    /**
     * 删除目录或文件
     *
     * @param remoteFilePath
     * @param recursive
     * @return
     * @throws IOException
     */
    public static boolean deleteFile(String remoteFilePath, boolean recursive)
            throws IOException {
        FileSystem fs = FileSystem.get(conf);
        boolean result = fs.delete(new Path(remoteFilePath), recursive);
        fs.close();
        return result;
    }

    /**
     * 删除目录或文件(如果有子目录,则级联删除)
     *
     * @param remoteFilePath
     * @return
     * @throws IOException
     */
    public static boolean deleteFile(String remoteFilePath) throws IOException {
        return deleteFile(remoteFilePath, true);
    }

    /**
     * 文件重命名
     *
     * @param oldFileName
     * @param newFileName
     * @return
     * @throws IOException
     */
    public static boolean renameFile(String oldFileName, String newFileName)
            throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path oldPath = new Path(oldFileName);
        Path newPath = new Path(newFileName);
        boolean result = fs.rename(oldPath, newPath);
        fs.close();
        return result;
    }

    /**
     * 创建目录
     *
     * @param dirName
     * @return
     * @throws IOException
     */
    public static boolean createDirectory(String dirName) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path dir = new Path(dirName);
        boolean result = false;
        if (!fs.exists(dir)) {
            result = fs.mkdirs(dir);
        }
        fs.close();
        return result;
    }

    /**
     * 列出指定路径下的所有文件(不包含目录)
     *
     * @param basePath
     * @param recursive
     */
    public static RemoteIterator<LocatedFileStatus> listFiles(String basePath,
                                                              boolean recursive) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        RemoteIterator<LocatedFileStatus> fileStatusRemoteIterator = fs
                .listFiles(new Path(basePath), recursive);

        return fileStatusRemoteIterator;
    }

    /**
     * 列出指定路径下的文件（非递归）
     *
     * @param basePath
     * @return
     * @throws IOException
     */
    public static RemoteIterator<LocatedFileStatus> listFiles(String basePath)
            throws IOException {
        FileSystem fs = FileSystem.get(conf);
        RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(
                new Path(basePath), false);
        fs.close();
        return remoteIterator;
    }

    /**
     * 列出指定目录下的文件\子目录信息（非递归）
     *
     * @param dirPath
     * @return
     * @throws IOException
     */
    public static FileStatus[] listStatus(String dirPath) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        FileStatus[] fileStatuses = fs.listStatus(new Path(dirPath));
        fs.close();
        return fileStatuses;
    }

    /**
     * 读取文件内容
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] readFile(String filePath) throws IOException {
        byte[] fileContent = null;
        FileSystem fs = FileSystem.get(conf);
        Path path = new Path(filePath);
        if (fs.exists(path)) {
            InputStream inputStream = null;
            ByteArrayOutputStream outputStream = null;
            try {
                inputStream = fs.open(path);
                outputStream = new ByteArrayOutputStream(
                        inputStream.available());
                IOUtils.copyBytes(inputStream, outputStream, conf);
                fileContent = outputStream.toByteArray();
            } finally {
                IOUtils.closeStream(inputStream);
                IOUtils.closeStream(outputStream);
                fs.close();
            }
        }
        return fileContent;
    }

    /**
     * 下载 hdfs上的文件
     *
     * @param remote
     * @param local
     * @throws IOException
     */
    public static void download(String remote, String local) throws IOException {
        Path path = new Path(remote);
        FileSystem fs = FileSystem.get(conf);
        fs.copyToLocalFile(path, new Path(local));
        System.out.println("download: from" + remote + " to " + local);
        fs.close();
    }

    public static void main(String[] args) {
//        long btime = System.currentTimeMillis();
//        ExecutorService executor = ThreadUtil.newExecutor(10);
//        for (int i = 0; i < 20; i++) {
//            int finalI = i;
//            executor.execute(() -> {
//                String fileName = "testExecutor" + finalI + ".txt";
//                System.out.println("当前线程===》" + Thread.currentThread().getName() + "===当前执行上传操作文件名=" + fileName);
//                try {
//                    HDFSCommon.createFile("/xc/executor/" + fileName, "testExecutor" + finalI);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    System.out.println("上传完成===》文件名=" + fileName);
//                }
//            });
//        }
//
//
//        //下载数据
//      /*  for (int i = 0; i < 50; i++) {
//            int finalI = i;
//            executor.execute(()->{
//                String fileName="testExecutor"+ finalI +".txt";
//                System.out.println("当前线程===》"+Thread.currentThread().getName()+"===当前执行下载操作文件名="+fileName);
//                try {
//                    HDFSCommon.download("/xc/executor/"+fileName,"E:\\executor\\"+fileName);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    System.out.println("下载完成===》文件名="+fileName);
//                }
//            });
//        }*/
//
//        executor.shutdown();
//        long etime = System.currentTimeMillis();
//        System.out.println("花费总时间时间====》" + (etime - btime));
//
    }
}