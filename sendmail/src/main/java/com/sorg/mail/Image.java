package com.sorg.mail;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	public static void main(String[] args)throws InterruptedException, IOException {
		 String yourText = "java2s.com";
        File contextDir = new File(Image.class.getResource("/image2.jpg").getFile());

		    BufferedImage bufferedImage = ImageIO.read(contextDir);
		    Graphics graphics = bufferedImage.getGraphics();
		    graphics.setColor(Color.BLUE);
		    graphics.setFont(new Font("Blackadder ITC", Font.BOLD | Font.ITALIC, 80));
		    graphics.drawString("Happy Birthday", 400, 100);
		    graphics.setColor(Color.BLACK);
		    graphics.setFont(new Font("Script MT Bold", Font.BOLD, 40));
		    graphics.drawString("Dear,", 500, 350);
		    graphics.setColor(Color.ORANGE);
		    graphics.setFont(new Font("Jokerman", Font.BOLD, 60));
		    graphics.drawString("Swathi", 550,500);
		    graphics.setColor(Color.RED);
		    graphics.setFont(new Font("Script MT Bold", Font.BOLD, 40));
		    graphics.drawString("best wishes from HTL Ltd", 380, 990);
		    
		    try {
				ImageIO.write(bufferedImage, "jpg", new File(
				    "image8.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    System.out.println("Image Created");
		  }
}
