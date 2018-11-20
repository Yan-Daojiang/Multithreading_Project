import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author: Yan_Daojiang
 * @date: 2018/11/20
 * @description:操作人员类，主要负责操作人员的相关的操作
 * ***增加异常部分
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

    //上传文件
    public void updatefile(String filename,String num,String description){
        System.out.println("上传文件·····");
    }

    //显示档案录入人员菜单
    public void showMenu(){
        System.out.println("************您已进入档案录入人员系统*****************");
        String in="abc";
        do {
            //对菜单选项进行输出
            Scanner sc = new Scanner(System.in);
            System.out.println("1.上传文件\n2.下载文件\n3.文件列表\n4.修改密码\n5.退出\n");

            //根据输入进行相关的操作
            System.out.printf("请输入您的选择:");
            in = sc.next();
            switch (in) {
                case "1":{//上传文件
                    sc.nextLine();
                    System.out.print("请输入文件名称:");
                    String filename=sc.nextLine();
                    System.out.print("请输入档案编号:");
                    String num=sc.nextLine();
                    System.out.print("请输入档案描述:");
                    String description=sc.nextLine();
                    updatefile(filename,num,description);
                    break;
                }
                case "2": {//下载文件
                    sc.nextLine();
                    System.out.print("请输入要下载的文件名:");
                    String filename=sc.nextLine();
                    try {
                        if (downloadFile(filename))
                            System.out.println("文件下载成功\n");
                        else
                            System.out.println("文件下载失败\n");
                    }catch(IOException e){
                        System.out.println(e.getMessage());
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
