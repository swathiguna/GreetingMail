package com.sorg.mail;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

/**
 * Unit test for simple App.
 */
public class App {
	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		try (InputStream input = new FileInputStream("config/config.properties")) {
			Properties prop = new Properties();
			prop.load(input);

			final String username = prop.getProperty("mail.username");
			final String password = prop.getProperty("mail.password");
			logger.info("user mail id:" + username);

			int min = Integer.parseInt(prop.getProperty("image.min"));
			int max = Integer.parseInt(prop.getProperty("image.max"));

			prop.put("mail.smtp.host", prop.getProperty("mail.smtp.host"));
			prop.put("mail.smtp.port", prop.getProperty("mail.smtp.port"));
			prop.put("mail.smtp.auth", prop.getProperty("mail.smtp.auth"));
			prop.put("mail.smtp.starttls.enable", prop.getProperty("mail.smtp.starttls.enable")); // TLS

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			try {

				List<User> users = LoadExcel.readFromExcel();
				if (!CollectionUtils.isEmpty(users)) {
					logger.info("Size of user list:" + users.size());
					for (User user : users) {
						Random rand = new Random();
						int randomNum = rand.nextInt((max - min) + 1) + min;
						File contextDir = new File("config/images/image" + randomNum + ".jpg");

						BufferedImage bufferedImage = ImageIO.read(contextDir);

						Graphics graphics = bufferedImage.getGraphics();
						graphics.setColor(Color.BLUE);
					    graphics.setFont(new Font("Blackadder ITC", Font.BOLD | Font.ITALIC, 80));
						graphics.drawString(user.getEvent(), 400, 100);
						graphics.setColor(Color.BLACK);
						graphics.setFont(new Font("Script MT Bold", Font.BOLD, 40));
						graphics.drawString("Dear,", 500, 350);
						graphics.setColor(Color.ORANGE);
						graphics.setFont(new Font("Jokerman", Font.BOLD, 80));
						graphics.drawString(user.getName(), 550, 500);
						graphics.setColor(Color.ORANGE);
						graphics.setFont(new Font("Script MT Bold", Font.BOLD, 40));
						graphics.drawString("("+user.getMessage()+")", 600, 580);
						graphics.setColor(Color.RED);
						graphics.setFont(new Font("Script MT Bold", Font.BOLD, 60));
						graphics.drawString("best wishes from HTL Ltd", 380, 1000);

						try {
							ImageIO.write(bufferedImage, "jpg", new File("config/images/image.jpg"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Message message = new MimeMessage(session);
						message.setFrom(new InternetAddress(username));
						logger.info("Mail id:" + user.getMailId());
						message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getMailId()));
						message.setSubject("Greetings from HTL Ltd");
						//message.setText("Dear " + user.getName() + "," + "\n\n " + user.getMessage());
						// This mail has 2 part, the BODY and the embedded image
						MimeMultipart multipart = new MimeMultipart("related");

						// first part (the html)
						MimeBodyPart messageBodyPart = new MimeBodyPart();
						String htmlText = "<img src=\"cid:image\">";
						messageBodyPart.setContent(htmlText, "text/html");
						// add it
						multipart.addBodyPart(messageBodyPart);

						// second part (the image)
						messageBodyPart = new MimeBodyPart();

						contextDir = new File("config/images/image.jpg");
						DataSource fds = new FileDataSource(contextDir);
						messageBodyPart.setFileName(fds.getName());
						messageBodyPart.setDataHandler(new DataHandler(fds));
						messageBodyPart.setHeader("Content-ID", "<image>");
						multipart.addBodyPart(messageBodyPart);

						messageBodyPart = new MimeBodyPart();
						contextDir = new File("config/images/image.jpg");
						fds = new FileDataSource(contextDir);
						messageBodyPart.setFileName(fds.getName());
						messageBodyPart.setDataHandler(new DataHandler(fds));
						// messageBodyPart.setHeader("Content-ID", "<image>");
						// messageBodyPart.attachFile(contextDir.getAbsoluteFile());
						// add image to the multipart
						multipart.addBodyPart(messageBodyPart);
						message.setContent(multipart);
						// put everything together
						message.setContent(multipart);
						Transport.send(message);
						logger.info("Mail sent successfully");
					}

				}
				logger.info("Program completed successfully");
			} catch (MessagingException e) {
				logger.error("Error while seding email", e);
			}
		} catch (IOException ex) {
			logger.debug("Error while config properties loaded", ex);
		}
	}
}