package com.zf.study.mem.controller;

import it.sauronsoftware.jave.*;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import javax.swing.*;
import java.io.File;
import java.util.List;

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

    public static  void videoHandler2() throws FrameGrabber.Exception, InterruptedException {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();   //开始获取摄像头数据
        CanvasFrame canvas = new CanvasFrame("摄像头");//新建一个窗口
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);

        while(true)
        {
            if(!canvas.isDisplayable())
            {//窗口是否关闭
                grabber.stop();//停止抓取
                System.exit(2);//退出
            }
            canvas.showImage(grabber.grab());//获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像

            Thread.sleep(50);//50毫秒刷新一次图像

    }}

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
//        commands.add("-f");
//        commands.add("-mp3");
//        commands.add("-acodec");
        commands.add("-vn");
        commands.add("-y");
        commands.add("-acodec");
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

    public static void main(String[] args) throws Exception {
        String source = "D:\\ZF\\video\\test.mp4";
        String target = "D:\\ZF\\video\\test.3gp";
//        videoHandler(source,target);
//        videoHandler2();
//        String s = processImg(source, "1");
        String audio = getAudio(source);
        System.out.println(audio);
    }
}
