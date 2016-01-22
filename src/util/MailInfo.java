package util;

public class MailInfo {
   String profile;
   String[] addressto;
   String subjuct;
   String cont;
   String pngfile;
   String[] files;
   /**
    * 邮件详情对象MailInfo
    * 
    * @param profile 配置文件的位置
    * @param addressto 发送人地址（多人）
    * @param subjuct 邮件标题
    * @param cont  邮件内容
    * @param pngfile  正文图片
    * @param files 邮件附件
    */
public MailInfo(String profile, String[] addressto, String subjuct, String cont, String pngfile, String[] files) {
	super();
	this.profile = profile;
	this.addressto = addressto;
	this.subjuct = subjuct;
	this.cont = cont;
	this.pngfile = pngfile;
	this.files = files;
}
public String getProfile() {
	return profile;
}
public void setProfile(String profile) {
	this.profile = profile;
}
public String[] getAddressto() {
	return addressto;
}
public void setAddressto(String[] addressto) {
	this.addressto = addressto;
}
public String getSubjuct() {
	return subjuct;
}
public void setSubjuct(String subjuct) {
	this.subjuct = subjuct;
}
public String getCont() {
	return cont;
}
public void setCont(String cont) {
	this.cont = cont;
}
public String getPngfile() {
	return pngfile;
}
public void setPngfile(String  pngfile) {
	this.pngfile = pngfile;
}
public String[] getFiles() {
	return files;
}
public void setFiles(String[] files) {
	this.files = files;
}
   
   
}
