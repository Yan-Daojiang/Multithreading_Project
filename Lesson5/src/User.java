import javax.swing.*;
import java.awt.image.BufferedImageFilter;
import java.io.*;
import java.nio.Buffer;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @author: Yan_Daojiang
 * @date:2018/12/03
 * @description:父类
 ****添加文件文件列表等部分的GUI
 **/

public abstract class User {
    private String name;
    private String password;
    private String role;

    String uploadpath="D:\\Java_Project\\Lesson4\\File\\uploadfile\\";//上传后文件保存的位置
    String downpath="D:\\Java_Project\\Lesson4\\File\\downloadfile\\";//下载后文件保存的位置

    //GUI组件
    private JDialog diag1=new JDialog();//设置对话框显示文件列表
    private JTextArea textArea1;//设置文本区显示信息

    User(String name,String password,String role){
        this.name=name;
        this.password=password;
        this.role=role;
    }

    public boolean changeSelfInfo(String password) throws SQLException{
        //写用户信息到存储
        if (DataProcessing.updateUser(name, password, role)){
            this.password=password;
            return true;
        }else
            return false;
    }

    public boolean downloadFile(String ID) throws IOException{
        double ranValue=Math.random();
        if (ranValue>2)
            throw new IOException( "Error in accessing file" );

        //下载操作
        try {
            //在系统中查找档案是否存在
            Doc doc = DataProcessing.searchDoc(ID);

            //如果不存在文件就不能执行操作
            if(doc==null)
                return false;
            FileInputStream fin=new FileInputStream(uploadpath+doc.getFilename());
            int i,j=0;
            byte[] content=new byte[fin.available()];
            while((i=fin.read())!=-1){
                content[j]=(byte)i;
                j++;
            }
            fin.close();

            //获取下载下来的文件的文件名
            String filename=doc.getFilename();
            File newfile=new File(downpath+filename);

            if(!newfile.exists())//如果文件不存在就创建文件
                newfile.createNewFile();

            //根据文件创建输出流
            OutputStream out=new FileOutputStream(newfile);
            out.write(content);

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    public void showFileList() throws SQLException{
        double ranValue=Math.random();
        if (ranValue>2)
            throw new SQLException( "Error in accessing file DB" );

        String messageg1="档案编号\t档案名\t创建者\t档案描述\n";

        diag1.setTitle("文件列表");
        diag1.setSize(445,300);

        //初始化一个对话框
        textArea1=new JTextArea(messageg1);
        textArea1.setLineWrap(true);//文本区换行处理
        diag1.add(textArea1);//文本区添加到对话框
        diag1.setLocationRelativeTo(null);

        //对哈希表中的所有的文件进行信息输出
        Enumeration<Doc> e=DataProcessing.getAllDocs();
        Doc doc;
        //当哈希表不为空就进入循环，获取文件信息
        while(e.hasMoreElements()){//枚举哈希表中的元素
            doc=e.nextElement();

            //将哈希表中的元素添加到文本区
            textArea1.append(doc.getID()+"\t"+doc.getFilename()+"\t"
                    +doc.getCreator()+"\t"+doc.getDescription()+"\n");

        }

        diag1.setResizable(false);//设置对话框大小不可改变
        diag1.validate();
        diag1.setVisible(true);
    }

    public abstract void showMenu();

    public void exitSystem(){
        System.out.println("系统退出, 谢谢使用 ! ");
        System.exit(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}