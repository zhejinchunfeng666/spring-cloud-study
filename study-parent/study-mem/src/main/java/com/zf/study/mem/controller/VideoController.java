package com.zf.study.mem.controller;

import it.sauronsoftware.jave.*;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class VideoController {

    public  static  void videoHandler(String source,String target) throws EncoderException {
        File sourceFile = new File(source);
        File targetFile = new File(target);
        AudioAttributes audio=new AudioAttributes();
        audio.setCodec("libfaac");
        audio.setBitRate(new Integer(128000));
        audio.setSamplingRate(new Integer(44100));
        audio.setChannels(new Integer(2));
        VideoAttributes video = new VideoAttributes();
        video.setCodec("mpeg4");
        video.setBitRate(new Integer(160000));
        video.setFrameRate(new Integer(15));
        video.setSize(new VideoSize(176,144));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("3gp");
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        Encoder encoder = new Encoder();
        encoder.encode(sourceFile,targetFile,attrs);
        //获取源文件信息
        MultimediaInfo info = encoder.getInfo(sourceFile);
        // 获取源文件的视频信息
        VideoInfo video1 = info.getVideo();
        System.out.println("*********源文件视频信息**********");
        System.out.println("bit rate:"+video1.getBitRate());
        System.out.println("decoder:"+video1.getDecoder());
        System.out.println("frame rate:"+video1.getFrameRate());
        System.out.println("size:"+video1.getSize());
        // 获取源文件的音频信息
        AudioInfo audio1 = info.getAudio();
        System.out.println("*********源文件音频信息********");
        System.out.println("bit rate:"+audio1.getBitRate());
        System.out.println("channel:"+audio1.getChannels());
        System.out.println("decoder:"+audio1.getDecoder());
        System.out.println("samplingrate:"+audio1.getSamplingRate());
    }

    public static  void videoHandler2() throws IOException, InterruptedException {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();   //开始获取摄像头数据
        CanvasFrame canvas = new CanvasFrame("摄像头");//新建一个窗口
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);

        while(true)
        {
            File fileName = new File("D:\\ZF\\image\\"+System.currentTimeMillis()+".jpg");
            if(!canvas.isDisplayable())
            {//窗口是否关闭
                grabber.stop();//停止抓取
                System.exit(2);//退出
            }
            canvas.showImage(grabber.grab());//获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像
            Thread.sleep(50);//50毫秒刷新一次图像
            Frame frame = grabber.grab();
            ImageIO.write(FrameToBufferedImage(frame),"jpg",fileName);
        }
}

    public static BufferedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }


    /**
     * 获取视频封面
     */

    private static  String utilPath = "D:\\ZF\\video\\ffmpeg.exe";
    public static String processImg(String videoPath,String frame) {
        File file = new File(videoPath);
        if (!file.exists()) {
            System.err.println("路径[" + videoPath + "]对应的视频文件不存在!");
            return null;
        }
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(utilPath);
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-ss");
        commands.add(frame);	// 这个参数是设置截取视频多少秒时的画面
        commands.add("-t");
        commands.add("0.001");
        commands.add("-s");
        commands.add("640x368");// 宽X高
        String imgPath = videoPath.substring(0, videoPath.lastIndexOf(".")).replaceFirst("vedio", "file") + ".jpg";
        commands.add(imgPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            System.out.println("截取成功");
            return imgPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 提取视频中音频文件
     * @param videoPath
     * @return
     */
    public static String  getAudio(String videoPath){
        File file = new File(videoPath);
        if (!file.exists()) {
            System.err.println("路径[" + videoPath + "]对应的视频文件不存在!");
            return null;
        }
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(utilPath);
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-vn");
        commands.add("-c:a");
        commands.add("copy");
        String imgPath = videoPath.substring(0, videoPath.lastIndexOf(".")).replaceFirst("vedio", "file") + ".aac";
        commands.add(imgPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            System.out.println("截取成功");
            return imgPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 提取视频中的视频(无音频)
     * @param videoPath
     * @return
     */
    public static String  getVideo(String videoPath){
        File file = new File(videoPath);
        if (!file.exists()) {
            System.err.println("路径[" + videoPath + "]对应的视频文件不存在!");
            return null;
        }
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(utilPath);
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-an");
        commands.add("-c:v");
        commands.add("copy");
        String imgPath = videoPath.substring(0, videoPath.lastIndexOf(".")).replaceFirst("vedio", "file") + "22.mp4";
        commands.add(imgPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            System.out.println("截取成功");
            return imgPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 裁剪视频
     * @param videoPath
     */
    public static String  cutVideo(String videoPath){
        File file = new File(videoPath);
        if (!file.exists()) {
            System.err.println("路径[" + videoPath + "]对应的视频文件不存在!");
            return null;
        }
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(utilPath);
        commands.add("-ss");
        // 从此时间点开始裁剪视频
        commands.add("00:00:15");
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-t");
        // 持续时间：单位秒
        commands.add("10");
        commands.add("-c");
        commands.add("copy");
        String imgPath = videoPath.substring(0, videoPath.lastIndexOf(".")).replaceFirst("vedio", "file") + "new.mp4";
        commands.add(imgPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            System.out.println("截取成功");
            return imgPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String  cutVideo2(String videoPath){
        File file = new File(videoPath);
        if (!file.exists()) {
            System.err.println("路径[" + videoPath + "]对应的视频文件不存在!");
            return null;
        }
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(utilPath);
        commands.add("-ss");
        // 从此时间点开始裁剪视频
        commands.add("12");
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-to");
        // 持续时间：单位秒
        commands.add("10");
        commands.add("-c");
        commands.add("copy");
        String imgPath = videoPath.substring(0, videoPath.lastIndexOf(".")).replaceFirst("vedio", "file") + "new.mp4";
        commands.add(imgPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            System.out.println("截取成功");
            return imgPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 音视频合并
     * @param audioPath
     * @param videoPath
     * @return
     */
    public static String muxVideo(String audioPath,String videoPath){
        File videoFile = new File(videoPath);
        File audioFile = new File(audioPath);
        if (!videoFile.exists()){
            System.err.println("路径[" + videoPath + "]对应的视频文件不存在!");
            return null;
        }
        if (!audioFile.exists()){
            System.err.println("路径[" + audioPath + "]对应的音频文件不存在!");
            return null;
        }
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(utilPath);
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-i");
        commands.add(audioPath);
        commands.add("-vcodec");
        commands.add("copy");
        commands.add("-acodec");
        commands.add("copy");
        String imgPath = videoPath.substring(0, videoPath.lastIndexOf(".")).replaceFirst("vedio", "file") + "2.mp4";
        commands.add(imgPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            log.info("command:{}",commands.toString());
            builder.command(commands);
            builder.start();
            System.out.println("截取成功");
            return imgPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) throws Exception {
        String videoPath = "D:\\ZF\\video\\testnew.mp4";
        String audioPath = "D:\\ZF\\video\\testnew.aac";
//        videoHandler(source,target);
        videoHandler2();
//        String s = processImg(source, "1");
//        String audio = getAudio(source);
//        System.out.println(audio);
//        String s = muxVideo(audioPath,videoPath);
//        System.out.println(s);
    }
}
