package com.yy.stock.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * 列出FTP服务器上指定目录下面的所有文件
 */
@Slf4j
public class FtpUtil {
    public FTPClient ftp;
    public ArrayList<String> arFiles;
    public ArrayList<FTPFile> remoteFiles;

    /**
     * 重载构造函数
     *
     * @param isPrintCommmand 是否打印与FTPServer的交互命令
     */
    public FtpUtil(boolean isPrintCommmand) {
        ftp = new FTPClient();
        arFiles = new ArrayList<String>();
        remoteFiles = new ArrayList<>();
        if (isPrintCommmand) {
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        }
    }

    public static void main(String[] args) throws IOException {
        FtpUtil f = new FtpUtil(true);
        if (f.login("119.45.45.129", 21, "anonymous", null)) {
            f.List("/", "txt");
        }

        for (var arFile : f.remoteFiles) {
            var inputStream = f.ftp.retrieveFileStream(arFile.getName());

            var charset = Charset.forName("utf-8");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);

            BufferedReader br = new BufferedReader(inputStreamReader);
        }
        f.disConnection();
        for (String arFile : f.arFiles) {
            log.info(arFile);
        }

    }

    /**
     * 登陆FTP服务器
     *
     * @param host     FTPServer IP地址
     * @param port     FTPServer 端口
     * @param username FTPServer 登陆用户名
     * @param password FTPServer 登陆密码
     * @return 是否登录成功
     * @throws IOException
     */
    public boolean login(String host, int port, String username, String password) throws IOException {
        this.ftp.connect(host, port);
        if (FTPReply.isPositiveCompletion(this.ftp.getReplyCode())) {
            if (this.ftp.login(username, password)) {
                /**
                 需要注意这句代码，如果调用List()方法出现，文件的无线递归，与真实目录结构不一致的时候，可能就是因为转码后，读出来的文件夹与正式文件夹字符编码不一致所导致。
                 则需去掉转码，尽管递归是出现乱码，但读出的文件就是真实的文件，不会死掉。等读完之后再根据情况进行转码。
                 如果ftp部署在windows下，则：
                 for (String arFile : f.arFiles) {
                 arFile = new String(arFile.getBytes("iso-8859-1"), "GBK");
                 logger.info(arFile);
                 }
                 */
                this.ftp.setControlEncoding("GBK");
                return true;
            }
        }
        if (this.ftp.isConnected()) {
            this.ftp.disconnect();
        }
        return false;
    }

    /**
     * 关闭数据链接
     *
     * @throws IOException
     */
    public void disConnection() throws IOException {
        if (this.ftp.isConnected()) {
            this.ftp.disconnect();
        }
    }

    /**
     * - 下载文件
     *
     * @param ftpPath   -FTP服务器文件目录
     * @param filename  -文件名称
     * @param localpath -下载后的文件路径
     * @return
     */
    public boolean downloadFile(String ftpPath, String filename, String localpath) {
        boolean flag = false;
        OutputStream os = null;
        try {
            log.info("开始下载文件");

            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);// 传输的时候以二进制传输
//            ftpClient.enterLocalPassiveMode();
            // ftpClient.setRemoteVerificationEnabled(false);

            // ftpClient.configure(new
            // FTPClientConfig("com.zznode.tnms.ra.c11n.nj.resource.ftp.UnixFTPEntryParser"));
            // 由于apache不支持中文语言环境，通过定制类解析中文日期类型
//            ftpClient.configure(new FTPClientConfig("com.zznode.tnms.ra.c11n.nj.resource.ftp.UnixFTPEntryParser"));

            //切换FTP目录
            ftp.changeWorkingDirectory(ftpPath);

            // 查看有哪些文件夹 以确定切换的ftp路径正确
            String[] a = ftp.listNames();
            log.info(a[0]);

            FTPFile[] ftpFiles = ftp.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localpath + "/" + file.getName());
                    os = new FileOutputStream(localFile);
                    ftp.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            flag = true;
            log.info("下载文件成功");
        } catch (Exception e) {
            log.info("下载文件失败");
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 递归遍历出目录下面所有文件
     *
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @throws IOException
     */
    public void List(String pathName) throws IOException {
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            //更换目录到当前目录
            this.ftp.changeWorkingDirectory(pathName);
            FTPFile[] files = this.ftp.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    arFiles.add(pathName + file.getName());
                    remoteFiles.add(file);
                } else if (file.isDirectory()) {
                    // 需要加此判断。否则，ftp默认将‘项目文件所在目录之下的目录（./）’与‘项目文件所在目录向上一级目录下的目录（../）’都纳入递归，这样下去就陷入一个死循环了。需将其过滤掉。
                    if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
                        List(pathName + file.getName() + "/");
                    }
                }
            }
        }
    }

    /**
     * 递归遍历目录下面指定的文件名
     *
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @param ext      文件的扩展名
     * @throws IOException
     */
    public void List(String pathName, String ext) throws IOException {
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            //更换目录到当前目录
            this.ftp.changeWorkingDirectory(pathName);
            this.ftp.enterLocalPassiveMode();
            FTPFile[] files = this.ftp.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(ext)) {
                        arFiles.add(pathName + file.getName());
                        remoteFiles.add(file);
                    }
                } else if (file.isDirectory()) {
                    if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
                        List(pathName + file.getName() + "/", ext);
                    }
                }
            }
        }
    }
}
