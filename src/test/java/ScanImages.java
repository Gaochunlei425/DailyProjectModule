import javax.annotation.processing.FilerException;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: gcl
 * @create: 2020/12/8 14:51
 */

public class ScanImages {
    public static List<File> allFileName(String scanFlower) {
        ScanImages my = new ScanImages();
        //统计文件数量测试
        int i=0;
        try {
            List<File>f = my.showListFile(new File(scanFlower));
            for (File file : f) {
                i++;
                System.out.println(file.getPath());
            }
            System.out.printf("当前目录文件夹下面总数为%d个文件(不包含文件夹)",i);
            System.out.println(new Date());
            return f;
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

        return null;
    }

    /**
     *  返回当前目录所有文件(包含子目录)
     * @param dir
     * @return list
     * @throws Exception
     */
    public List<File> showListFile(File dir) throws Exception{
        List<File> list = new ArrayList<>();
        //查找参数文件是否存在,只检查第一个入参
        if(!dir.exists()) {
            throw new FilerException("找不到文件");
        }
        //如果是目录那么进行递归调用
        if(dir.isDirectory()) {
            //获取目录下的所有文件
            File[] f = dir.listFiles();
            //进行递归调用,最后总会返回一个list
            for (File file : f) {
                list.addAll(showListFile(file));
            }
        }else {//不是目录直接添加进去
            list.add(dir);
        }
        return list;
    }
}
