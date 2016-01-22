package util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

public class EmailSender {
	public static void sendemail(MailInfo info) {
		MailInfo mailinfo = info;
		String filename = mailinfo.getProfile();
		Properties properties = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filename));
			properties.load(in);
			in.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("username"),
						properties.getProperty("password"));
			}
		});
		session.setDebug(true);
		Message msg = new MimeMessage(session);
		try {
			msg.setText("what's up man");
			msg.setSubject(mailinfo.subjuct);
			msg.setFrom(new InternetAddress(properties.getProperty("fromAddress")));
			// 设置邮件内容
			// 将邮件中各个部分组合到一个"mixed"型的 MimeMultipart 对象
			MimeMultipart allPart = new MimeMultipart("mixed");
			// 创建邮件的各个 MimeBodyPart 部分
			MimeBodyPart content = null;
			MimeBodyPart attachment = null;
			content = createContent(mailinfo.cont, mailinfo.getPngfile());
			attachment = createAttachment(mailinfo.getFiles());
			allPart.addBodyPart(content);
			allPart.addBodyPart(attachment);
			// 将上面混合型的 MimeMultipart 对象作为邮件内容并保存
			msg.setContent(allPart);
			msg.setSubject(mailinfo.subjuct);
			System.out.println(properties.getProperty("fromAddress"));
			msg.saveChanges();
			String[] to = mailinfo.addressto;
			InternetAddress[] sendTo = new InternetAddress[to.length];

			for (int i = 0; i < to.length; i++) {
				InternetAddress address = null;
				try {
					address = new InternetAddress(to[i]);
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sendTo[i] = address;
			}

			msg.setRecipients(RecipientType.TO, sendTo);
			Transport.send(msg);
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static MimeBodyPart createAttachment(String[] fileName) {
		String[] files = fileName;
		System.out.println(files.toString());
		MimeBodyPart attachmentPart = new MimeBodyPart();
		for (int i = 0; i < files.length; i++) {
			FileDataSource fds = new FileDataSource(files[i]);
			try {
				attachmentPart.setDataHandler(new DataHandler(fds));
				attachmentPart.setFileName(fds.getName());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return attachmentPart;
	}

	/**
	 * 根据传入的邮件正文body和文件路径创建图文并茂的正文部分
	 */
	public static MimeBodyPart createContent(String body, String path) {
		MimeBodyPart contentBody = new MimeBodyPart();
		MimeMultipart multipart = new MimeMultipart("related");
		try {
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = "<font color=\"red\">" + body + "</font><br/><img src=\"cid:image\">";
			messageBodyPart.setContent(htmlText, "text/html;charset=GBK");
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource(path);
			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");
			multipart.addBodyPart(messageBodyPart);
			contentBody.setContent(multipart);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return contentBody;
	}
}
