import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * @author: Yan_Daojiang
 * @date: 2018/11/27
 * @description:管理人员类，主要负管理人员的的相关的操作
 ****对文件下载和文件列表部分进行优化
 **/

public class Administrator extends User {
    private String name;
    private String password;
    private String role;


    public Administrator(String name, String password, String role) {
        super(name, password, role);
        this.name=name;
        this.password=password;
        this.role=role;
    }

    Scanner sc = new Scanner(System.in);//创建用于一个输入的对象

    //用户列表显示
    public void listUser(){
        System.out.println("用户姓名\t用户密码\t用户角色");

        //枚举哈希表中的值，用来输出系统中的人员信息
        try {
            for (Enumeration<User> e = DataProcessing.getAllUser(); e.hasMoreElements(); ) {
                User user = e.nextElement();
                System.out.println(user.getName() + "\t\t" + user.getPassword() +
                        "\t\t\t" + user.getRole());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //删除用户
    public void delUser() {
        System.out.print("输入您要删除的用户姓名:");
        String name = sc.nextLine();
        try {
            if (DataProcessing.deleteUser(name))
                System.out.println("删除成功");
            else
                System.out.println("操作不成功，请重试");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //增加用户
    public void addUser(){
        System.out.print("请输入新用户的用户名:");
        String name=sc.nextLine();
        System.out.print("请输入新用户的用户密码:");
        String pwd=sc.nextLine();
        System.out.print("请输入新用户的用户身份:");
        String role=sc.nextLine();
        try {
            if (DataProcessing.insertUser(name, pwd, role)) {
                System.out.println("添加用户成功");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //修改用户信息
    public void changeInfo(){
        //获取要更改的相关的信息
        System.out.print("请输入需要修改的用户名:");
        String name=sc.nextLine();
        System.out.print("请输入修改后的密码:");
        String pwd=sc.nextLine();
        System.out.print("请输入修改后的身份:");
        String role=sc.nextLine();

        //更新用户的信息
        try {
            if (DataProcessing.updateUser(name, pwd, role))
                System.out.println("用户信息修改成功");
            else
                System.out.println("用户信息未能成功修改，请检查您的操作是否正确!");
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } catch(IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }

    //管理人员操作菜单
    public void showMenu() {
        System.out.println("************您已进入管理员系统*****************");
        String in="abc";

        do {
            //对菜单选项进行输出
            System.out.println("1.修改用户\n2.删除用户\n3.新增用户\n4.用户列表\n" +
                    "5.下载文件\n6.文件列表\n7.修改密码\n8.退出");

            //根据输入进行相关的操作
            System.out.printf("请输入您的选择:");
            in = sc.nextLine();

            switch (in) {
                case "1":{//修改用户
                    changeInfo();
                    break;
                }

                case "2": {//删除用户
                    delUser();
                    break;
                }

                case "3": {//新增用户
                    addUser();
                    System.out.println("新增用户成功!");
                    break;
                }

                case "4":{//列表显示用户
                    listUser();
                    break;
                }

                case "5":{//下载文件
                    System.out.print("请输入要下载的档案编号:");
                    String ID=sc.nextLine();

                    try {
                        if (downloadFile(ID))
                            System.out.println("文件下载成功\n");
                        else
                            System.out.println("文件下载失败\n");
                    } catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case "6":{//文件列表显示
                    try {
                        showFileList();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case "7": {//密码修改
                    System.out.print("请输入您的新密码:");
                    String pwd = sc.nextLine();
                    try {
                        if (changeSelfInfo(pwd))
                            System.out.println("密码修改成功!\n");
                        else
                            System.out.println("密码修改不成功!\n");
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case "8":{//退出系统
                    exitSystem();
                }

                default: {//错误选择提示信息
                    System.out.println("您的输入有误，请检查输入");
                    break;
                }
            }

        }while (in != null) ;
    }
}