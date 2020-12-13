import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * This sample demonstrates how to upload/download an object to/from
 * Aliyun OSS using the OSS SDK for Java.
 */
public class SimpleGetObjectSample {

    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI4GJ8cD8WkhVxFXy6ptNg";
    private static String accessKeySecret = "******";
    private static String bucketName = "gcl-bucket-0001";

    // 扫描的文件夹
    private static String scanFlower = "C:\\Users\\gaoch\\AppData\\Roaming\\Typora\\typora-user-images";
    // 定时扫描时间   单位分钟
    private static Integer timing = 15;
    // 文件所在目录   Such as ：2020/12/08
    private static String bucketFlower = "noteImages";

    public static void main(String[] args) {
        SimpleGetObjectSample simpleGetObjectSample = new SimpleGetObjectSample();

        // 定时执行任务
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                System.out.println("任务开始，时间：" + new Date().toString());
                simpleGetObjectSample.uploadFileAvatar();
                System.out.println("任务结束，时间："+ new Date().toString());

            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(runnable, 0, timing, TimeUnit.MINUTES);
    }

    /**
     * 任务开始
     */
    public void uploadFileAvatar() {
        // 扫描文件夹下的所有文件
        List<File> files = ScanImages.allFileName(scanFlower);
        // 启动客户端
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        int i = 0;
        while (i == 0) {
            i++;
            List<String> fileNames = fileFolder(bucketFlower, ossClient);
            // 标记是否存在于OSS中  0：不在，1：在
            int[] flag = {0};
            // 遍历本地文件
            files.stream().forEach(file -> {
                //遍历远程文件名
                fileNames.stream().forEach(existFile -> {
                    if (StringUtils.equals(existFile,file.getName()))
                        flag[0] = 1;
                });

                try {
                    if (flag[0] == 1) {
                        System.out.println("文件已存在 : " + file.getName());
                        flag[0] = 0;
                    } else {
                        InputStream inputStream = new FileInputStream(file);
                        String fileName = bucketFlower + "/" + file.getName();
                        ossClient.putObject(bucketName, fileName, inputStream);
                        System.out.println("新增已上传：" + file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
             ossClient.shutdown();
        }
    }

    public static List<String> fileFolder(String fileName, OSS ossClient) {
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        // 设置正斜线（/）为文件夹的分隔符。
        listObjectsRequest.setDelimiter("/");
        // 设置扫描返回的文件
        listObjectsRequest.setMaxKeys(400);
        // 设置prefix参数来获取fun目录下的所有文件。
        if (StringUtils.isNotBlank(fileName)) {
            listObjectsRequest.setPrefix(fileName + "/");
        }
        // 列出文件
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有commonPrefix
        List<String> list = new ArrayList<>();
        // 获取所有文件
        List<OSSObjectSummary> commonPrefixes = listing.getObjectSummaries();
        for (OSSObjectSummary commonPrefix : commonPrefixes) {
            String[] s  = commonPrefix.getKey().split("/");
            if (s.length > 1) {
                list.add(s[1]);
            }
        }
        return list;
    }

}
