package com.myproject;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = environment.getAvailableFontFamilyNames();//获得系统字体
        for (int i = 0; i < fonts.length; i++) {
            System.out.println(fonts[i]);
        }

        // 图片的宽度。
        int width = 102;
        // 图片的高度。
        int height = 42;
        int codeCount = 4;

        int codeX = 0;
        int fontHeight = 0;
        fontHeight = height - 17;// 字体的高度
        codeX = width / (codeCount+1);// 每个字符的宽度

        String rCode = "圣诞快乐";
        Random random = new Random();

//		// 获取系统字体
//		getEnvironmentFont();

        // 图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        //设置透明  start
        buffImg = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g=buffImg.createGraphics();
        //设置透明  end

        // 将图像填充为透明
        g.setColor(Color.WHITE);
        //g.fillRect(0, 0, width, height);

        // 设置字体颜色
        g.setColor(Color.BLACK);

        StringBuffer randomCode = new StringBuffer();
        //String strRand = CaptchaUtil.getRandomCode();
        char[] cs = rCode.toCharArray();
        // 随机产生验证码字符
        for (int i = 0; i < cs.length; i++) {
            String temp = String.valueOf(cs[i]);
            // 创建字体

            Font font = new Font("儿童卡通字体", Font.PLAIN, fontHeight);
            System.out.println(cs[i]+" "+ font.canDisplay(cs[i]));
            if(!font.canDisplay(cs[i])){
                font = new Font("宋体", Font.PLAIN, fontHeight);
            }
            g.setFont(font);
            // 设置字体位置
            g.drawString(temp, i* codeX + 8,
                    random.nextInt(height / 2) + 20);
            randomCode.append(temp);
        }
        String code = randomCode.toString();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(buffImg, "png", baos);
        byte [] bytes = baos.toByteArray();
        String img = Base64.encodeBase64String(bytes);

        System.out.println(img);
    }
}
