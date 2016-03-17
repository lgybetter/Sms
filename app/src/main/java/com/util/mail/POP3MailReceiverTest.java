package com.util.mail;

/**
 * Created by ttopi_000 on 2016/3/2.
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
/**
 * 邮件接受测试
 *
 */
public class POP3MailReceiverTest {
    public POP3MailReceiverTest( Message[] messages) {
        try {
            // 取出来邮件数
            int msgCount = messages.length;
            System.out.println("共有邮件: " + msgCount + "封");
            // FetchProfile fProfile = new FetchProfile();// 选择邮件的下载模式,
            // 根据网速选择不同的模式
            // fProfile.add(FetchProfile.Item.ENVELOPE);
            // folder.fetch(messages, fProfile);// 选择性的下载邮件
            // 5. 循环处理每个邮件并实现邮件转为新闻的功能
            for (int i = msgCount-5; i < msgCount-4; i++) {
                // 单个邮件
                System.out.println("第" + i + "邮件开始");
                mailReceiver(messages[i]);
                System.out.println("第" + i + "邮件结束");
                //邮件读取用来校验
                //messages[i].writeTo(new FileOutputStream("D:/pop3MailReceiver" + i + ".eml"));
            }
            // 7. 关闭 Folder 会真正删除邮件, false 不删除
            // 8. 关闭 store, 断开网络连接
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 解析邮件
     *
     * @param messages 邮件对象
     * @param i
     * @return
     * @throws IOException
     * @throws MessagingException
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    private void mailReceiver(Message msg) throws Exception {
        // 发件人信息
        Address[] froms = msg.getFrom();
        if (froms != null) {
            //System.out.println("发件人信息:" + froms[0]);
            InternetAddress addr = (InternetAddress) froms[0];
            System.out.println("发件人地址:" + addr.getAddress());
            System.out.println("发件人显示名:" + addr.getPersonal());
        }
        System.out.println("邮件主题:" + msg.getSubject());
        // getContent() 是获取包裹内容, Part相当于外包装
        Object o = msg.getContent();
        if (o instanceof Multipart) {
            Multipart multipart = (Multipart) o;
            reMultipart(multipart);
        } else if (o instanceof Part) {
            Part part = (Part) o;
            rePart(part);
        } else {
            System.out.println("类型" + msg.getContentType()+"类型结束");
            System.out.println("内容" + msg.getContent()+ "内容结束");
        }
    }

    /**
     * @param part 解析内容
     * @throws Exception
     */
    private void rePart(Part part) throws MessagingException,
            UnsupportedEncodingException, IOException, FileNotFoundException {
        if (part.getDisposition() != null) {

            String strFileNmae = MimeUtility.decodeText(part.getFileName()); //MimeUtility.decodeText解决附件名乱码问题
            System.out.println("发现附件: " + MimeUtility.decodeText(part.getFileName()));
            System.out.println("内容类型: " + MimeUtility.decodeText(part.getContentType()));
            System.out.println("附件内容:" + part.getContent());
            InputStream in = part.getInputStream();// 打开附件的输入流
            // 读取附件字节并存储到文件中
            java.io.FileOutputStream out = new FileOutputStream(strFileNmae);
            int data;
            while ((data = in.read()) != -1) {
                out.write(data);
            }
            in.close();
            out.close();
        } else {
            if (part.getContentType().startsWith("text/plain")) {
                System.out.println("文本内容：" + part.getContent());
            } else {
                System.out.println("HTML内容：" + part.getContent());
            }
        }
    }

    /**
     * @param multipart // 接卸包裹（含所有邮件内容(包裹+正文+附件)）
     * @throws Exception
     */
    private void reMultipart(Multipart multipart) throws Exception {
        //System.out.println("邮件共有" + multipart.getCount() + "部分组成");
        // 依次处理各个部分
        for (int j = 0, n = multipart.getCount(); j < n; j++) {
            //System.out.println("处理第" + j + "部分");
            Part part = multipart.getBodyPart(j);//解包, 取出 MultiPart的各个部分, 每部分可能是邮件内容,
            // 也可能是另一个小包裹(MultipPart)
            // 判断此包裹内容是不是一个小包裹, 一般这一部分是 正文 Content-Type: multipart/alternative
            if (part.getContent() instanceof Multipart) {
                Multipart p = (Multipart) part.getContent();// 转成小包裹
                //递归迭代
                reMultipart(p);
            } else {
                rePart(part);
            }
        }
    }
}