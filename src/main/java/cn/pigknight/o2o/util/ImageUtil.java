package cn.pigknight.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    private static String basePath =  Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 将CommonsMultipartFile转换成File类
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile){
        File newfile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newfile);
        }catch (IllegalStateException e){
            logger.error(e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newfile;
    }
    /**
     * 处理缩略图，并返回新生成图片的相对值路径
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnails(File thumbnail,String targetAddr){
        String realFilename = getRandomFilename();
        String extension = getFileExtension(thumbnail);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFilename + extension;
        logger.debug("current relativeAddr is:"+ relativeAddr);
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        logger.debug("current complete addr is:"+PathUtil.getImgBasePath()+relativeAddr);
        try {
            Thumbnails.of(thumbnail).size(200,200).watermark(Positions.BOTTOM_RIGHT,
                    ImageIO.read(new File(basePath+"/watermark.jpg")),0.5f).outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，即/home/work/lei/xxx.jpg,
     * 那么 home work lei 这三个文件夹都得自动创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     * @param cFile
     * @return
     */
    private static String getFileExtension(File cFile) {
        String originalFilename = cFile.getName();
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
     * @return
     */
    public static String getRandomFilename() {
        //获取随机的五位数
        int rannum = r.nextInt(89999)+10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("C://Users//PigKnight//Pictures//Saved Pictures//background.jpg"))
                .size(200, 200).watermark(Positions.BOTTOM_RIGHT,
                ImageIO.read(new File(basePath+"/watermark.jpg")),0.50f).outputQuality(0.8f)
                .toFile("C://Users//PigKnight//Pictures//Saved Pictures//backgroundnew.jpg");
    }
}
