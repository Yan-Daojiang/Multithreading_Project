import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

/**
 * @author: Yan_Daojiang
 * @date: 2018/11/27
 * @description:操作人员类
 * ****本次实验实现的文件的上传下载和文件的查询
 **/
public class Operator extends User {
    private String name;
    private String password;
    private String role;


    public Operator(String name, String password, String role) {
        super(name, password, role);
        this.name=name;
        this.password=password;
        this.role=role;
    }

    Scanner sc = new Scanner(System.in);//创建用于输入的一个对象

    //上传文件
    public boolean updatefile(){ //按照流的方式读取文件，并创建文件对象

        //从键盘读取文件的相关的信息
        sc.nextLine();
        System.out.print("请输入要上传的文件名:");
        String filename=sc.nextLine();
        System.out.print("请输入文件编号:");
        String ID=sc.nextLine();
        System.out.print("请输入文件的描述:");
        String description=sc.nextLine();

        //构建要上传的文件
        File uploadfile=new File(filename);

        try {
            //按照流的方式读取文件
            InputStream in = new FileInputStream(uploadfile);

            byte[] buffer = new byte[10240];//创建字节数组
            in.read(buffer);//读取内容放到字节数组中

            //获取要上传的文件的文件名，同时设定上传文件的保存位置
            File newfile=new File(uploadpath+uploadfile.getName());

            //上传文件
            if(!newfile.exists())//如果文件不存在就创建文件
                newfile.createNewFile();

            //按照流的方式写入文件
            OutputStream out=new FileOutputStream(newfile);
            out.write(buffer);

            //最后在系统中对上传的文件信息进行保存
            Date date=new Date();
            Timestamp timestamp=new Timestamp(date.getTime());
            DataProcessing.insertDoc(ID,name,timestamp,description,uploadfile.getName());

        }

        //对上传中可能产生的异常进行捕获处理
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return true;//文件上传成功的返回值
    }

    //显示档案录入人员菜单
    public void showMenu(){
        System.out.println("************您已进入档案录入人员系统*****************");

        String in="abc";
        do {
            //对菜单选项进行输出
            System.out.println("1.上传文件\n2.下载文件\n3.文件列表\n4.修改密码\n5.退出\n");

            //根据输入进行相关的操作
            System.out.printf("请输入您的选择:");
            in = sc.next();
            switch (in) {
                case "1":{//上传文件
                    if(updatefile())
                        System.out.println("文件上传成功");
                    else
                        System.out.println("文件上传失败");
                    break;
                }

                case "2": {//下载文件
                    sc.nextLine();
                    System.out.print("请输入要下载的档案编号:");
                    String filename=sc.nextLine();
                    try {
                        if (downloadFile(filename))
                            System.out.println("文件下载成功\n");
                        else
                            System.out.println("文件下载失败\n");
                    }catch(IOException e){
                        System.out.println("捕获到了一个异常");
                    }
                    break;
                }

                case "3": {//文件列表的显示
                    try {
                        showFileList();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case "4":{//修改密码
                    sc.nextLine();
                    System.out.print("请输入你的新密码:");
                    String pwd = sc.nextLine();

                    try {
                        if (changeSelfInfo(pwd))
                            System.out.println("密码修改成功!");
                        else
                            System.out.println("密码修改不成功!");
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case "5":{//退出系统
                    exitSystem();
                }

                default: {
                    System.out.println("您的输入有误，请检查输入");
                    break;
                }
            }
        }while (in != null) ;
    }
}