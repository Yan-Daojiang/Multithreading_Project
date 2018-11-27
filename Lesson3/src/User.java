import java.awt.image.BufferedImageFilter;
import java.io.*;
import java.nio.Buffer;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @author: Yan_Daojiang
 * @date: 2018/11/27
 * @description:父类
 ****本次实验将文件的下载和文件的查询功能进行完善
 **/

public abstract class User {
    private String name;
    private String password;
    private String role;


    String uploadpath="D:\\Java_Project\\Lesson3\\File\\uploadfile\\";//上传后文件保存的位置
    String downpath="D:\\Java_Project\\Lesson3\\File\\downloadfile\\";//下载后文件保存的位置

    User(String name,String password,String role){
        this.name=name;
        this.password=password;
        this.role=role;
    }

    public boolean changeSelfInfo(String password) throws SQLException{
        //写用户信息到存储
        if (DataProcessing.updateUser(name, password, role)){
            this.password=password;
            System.out.println("修改成功");
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

        System.out.println("档案编号\t档案名\t\t\t创建者\t\t\t档案描述");//输出格式的确定

        //对哈希表中的所有的文件进行信息输出
        Enumeration<Doc> e=DataProcessing.getAllDocs();
        Doc doc;

        //当哈希表不为空就进入循环，获取文件信息
        while(e.hasMoreElements()){//枚举哈希表中的元素
            doc=e.nextElement();
            System.out.println(doc.getID()+"\t\t"+doc.getFilename()+"\t\t"
                    +doc.getCreator()+"\t\t\t"+doc.getDescription()+"\t\t\t");
        }
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