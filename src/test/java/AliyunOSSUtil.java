/*
package com;

@Component
public class AliyunOSSUtil {
 
    private static final Logger logger = LoggerFactory.getLogger(AliyunOSSUtil.class);
    private static String FILE_URL;
    private static String bucketName = AliyunOSSConfigConstant.BUCKE_NAME;
    private static String endpoint = AliyunOSSConfigConstant.END_POINT;
    private static String accessKeyId = AliyunOSSConfigConstant.AccessKey_ID;
    private static String accessKeySecret = AliyunOSSConfigConstant.AccessKey_Secret;
 
    */
/**
     * 上传文件。
     *
     * @param file 需要上传的文件路径
     * @return 如果上传的文件是图片的话，会返回图片的"URL"，如果非图片的话会返回"非图片，不可预览。文件路径为：+文件路径"
     *//*

    public static String upLoad(File file, String fileHost, String suffix) {
 
        // 默认值为：true
        boolean isImage = true;
        // 判断所要上传的图片是否是图片，图片可以预览，其他文件不提供通过URL预览
        try {
            Image image = ImageIO.read(file);
            isImage = image == null ? false : true;
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        logger.info("------OSS文件上传开始--------" + file.getName());
 
        // 文件名格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String dateString = sdf.format(new Date()) + "." + suffix; // 20180322010634.jpg
 
        // 判断文件
        if (file == null) {
            return null;
        }
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断容器是否存在,不存在就创建
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            // 设置文件路径和名称
//            String fileUrl = fileHost + "/" + (dateString + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
            String fileUrl = fileHost + "/" + dateString;
            if (isImage) {//如果是图片，则图片的URL为：....
                FILE_URL = "https://" + bucketName + "." + endpoint + "/" + fileUrl;
            } else {
                FILE_URL = fileUrl;
                logger.info("非图片,不可预览。文件路径为：" + fileUrl);
            }
 
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            // 设置权限(公开读)
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if (result != null) {
                logger.info("------OSS文件上传成功------" + fileUrl);
            }
        } catch (OSSException oe) {
            logger.error(oe.getMessage());
        } catch (ClientException ce) {
            logger.error(ce.getErrorMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return FILE_URL;
    }
 
    */
/**
     * 通过文件名下载文件
     *
     * @param objectName    要下载的文件名
     * @param localFileName 本地要创建的文件名
     *//*

    public static void downloadFile(String objectName, String localFileName) {
 
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFileName));
        // 关闭OSSClient。
        ossClient.shutdown();
    }
 
    */
/**
     * 删除文件
     * objectName key 地址
     *
     * @param filePath
     *//*

    public static Boolean delFile(String filePath) {
        logger.info("删除开始，objectName=" + filePath);
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
 
        // 删除Object.
        boolean exist = ossClient.doesObjectExist(bucketName, filePath);
        if (!exist) {
            logger.error("文件不存在,filePath={}", filePath);
            return false;
        }
        logger.info("删除文件,filePath={}", filePath);
        ossClient.deleteObject(bucketName, filePath);
        ossClient.shutdown();
        return true;
    }
 
    */
/**
     * 批量删除
     *
     * @param keys
     *//*

    public static Boolean delFileList(List<String> keys) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 删除文件。
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            ossClient.shutdown();
        }
        return true;
 
    }
 
    */
/**
     * 获取文件夹
     *
     * @param fileName
     * @return
     *//*

    public static List<String> fileFolder(String fileName) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        // 设置正斜线（/）为文件夹的分隔符。
        listObjectsRequest.setDelimiter("/");
        // 设置prefix参数来获取fun目录下的所有文件。
        if (StringUtils.isNotBlank(fileName)) {
            listObjectsRequest.setPrefix(fileName + "/");
        }
        // 列出文件
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有commonPrefix
        List<String> list = new ArrayList<>();
        for (String commonPrefix : listing.getCommonPrefixes()) {
            String newCommonPrefix = commonPrefix.substring(0, commonPrefix.length() - 1);
            String[] s = newCommonPrefix.split("/");
            list.add(s[1]);
        }
        // 关闭OSSClient
        ossClient.shutdown();
        return list;
    }
 
    */
/**
     * 列举文件下所有的文件url信息
     *//*

    public static List<String> listFile(String fileHost) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
 
        // 设置prefix参数来获取fun目录下的所有文件。
        listObjectsRequest.setPrefix(fileHost + "/");
        // 列出文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有文件。
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listing.getObjectSummaries().size(); i++) {
            if (i == 0) {
                continue;
            }
            FILE_URL = "https://" + bucketName + "." + endpoint + "/" + listing.getObjectSummaries().get(i).getKey();
            list.add(FILE_URL);
        }
        // 关闭OSSClient。
        ossClient.shutdown();
        return list;
    }
    */
/**
     * 获得url链接
     *
     * @param objectName
     * @return
     *//*

    public static String getUrl(String objectName) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 设置权限(公开读)
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        // 设置图片处理样式。
//        String style = "image/resize,m_fixed,w_100,h_100/rotate,90";
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 100);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
//        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        // 关闭OSSClient。
        logger.info("------OSS文件文件信息--------" + signedUrl.toString());
        ossClient.shutdown();
        if (signedUrl != null) {
            return signedUrl.toString();
        }
        return null;
    }
    // 获取文 MultipartFile 文件后缀名工具
    public static String getSuffix(MultipartFile fileupload) {
        String originalFilename = fileupload.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        System.out.println(suffix);
        return suffix;
    }
    */
/**
     * 创建文件夹
     *
     * @param folder
     * @return
     *//*

    public static String createFolder(String folder) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 文件夹名
        final String keySuffixWithSlash = folder;
        // 判断文件夹是否存在，不存在则创建
        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
            // 创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            logger.info("创建文件夹成功");
            // 得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            ossClient.shutdown();
            return fileDir;
        }
        return keySuffixWithSlash;
    }
    public static void main(String[] args) {
//        createFolder("test/");
//        getUrl("product/class/26065946787446951.jpg");
        listFile("document");
//        fileFolder(null);
//        fileFolder("document");
//        String bucketUrl = "https://jiangpin.oss-cn-shenzhen.aliyuncs.com/other/20190509023515.jpg";
//        String[] s = bucketUrl.split(".com");
//        String path = s[1];
//        path = path.substring(1, path.length());
//        System.out.println(path);
    }*/
