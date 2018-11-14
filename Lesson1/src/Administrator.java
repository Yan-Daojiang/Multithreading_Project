import java.util.Enumeration;
import java.util.Scanner;

/**
 * @author: Yan_Daojiang
 * @date: 2018/11/13
 * @description:
 **/
public class Administrator extends User {
    public Administrator(String name, String password, String role) {
        super(name, password, role);
    }
    Scanner sc = new Scanner(System.in);
    //用户列表显示
    public void listUser(){
        System.out.println("用户姓名\t用户密码\t用户角色");
        for(Enumeration<User> e=DataProcessing.getAllUser();e.hasMoreElements();) {
            User user=e.nextElement();
            System.out.println(user.getName()+"\t\t"+user.getPassword()+"\t\t\t"+user.getRole());
        }
    }

    //删除用户
    public void delUser() {
        sc.nextLine();
        System.out.print("输入您要删除的用户姓名:");
        String name = sc.nextLine();
        if (DataProcessing.delete(name))
            System.out.println("删除成功");
        else
            System.out.println("操作不成功，请重试");
    }


    public void showMenu() {
        System.out.println("************您已进入管理员系统*****************");
        String in="abc";
        do {
            //对菜单选项进行输出

            System.out.println("1.修改用户\n2.删除用户\n3.新增用户\n4.用户列表\n5.下载文件\n6.文件列表\n7.修改密码\n8.退出");
            System.out.printf("请输入您的选择：");
            in = sc.next();
            //根据输入进行相关的操作
            switch (in) {
                case "1":{//修改用户

                }
                case "2": {//删除用户
                    delUser();
                    break;
                }
                case "3": {

                }
                case "4":{//列表显示用户
                    listUser();
                    break;
                }
                case "5":{

                }
                case "6":{

                }
                case "7":{

                }
                case "8":{
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