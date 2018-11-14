import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author: Yan_Daojiang
 * @date: 2018/11/13
 * @description:浏览人员类，负责管理浏览人员的相关的操作
 **/

public class Browser extends User {
    public Browser(String name, String password, String role) {
        super(name, password, role);
    }

    //档案浏览员菜单
    public void showMenu(){
        System.out.println("************您已进入浏览员系统*****************");
        String in="abc";//先将输入初始化为一个无关的值

        do {
            //对菜单选项进行输出
            Scanner sc = new Scanner(System.in);
            System.out.println("1.下载文件\n2.文件列表\n3.修改密码\n4.退出");

            //根据输入进行相关的操作
            System.out.printf("请输入您的选择:");
            in = sc.next();

            //根据输入匹配操作
            switch (in) {
                case "1": {//下载文件
                    sc.nextLine();
                    System.out.print("请输入你要下载的文件名:");
                    String filename=sc.nextLine();
                    if(downloadFile(filename))
                        System.out.println("文件下载成功\n");
                    else
                        System.out.println("文件下载失败\n");
                    break;
                }
                case "2": {//文件列表的显示
                    showFileList();
                    break;
                }
                case "3":{//修改密码
                    //读取用户的新密码
                    sc.nextLine();
                    System.out.print("请输入你的新密码:");
                    String pwd = sc.nextLine();
                    //调用父类函数，修改密码
                    if (changeSelfInfo(pwd))
                        System.out.println("密码修改成功!\n");
                    else
                        System.out.println("密码修改不成功!\n");
                    break;
                }
                case "4":{//退出系统
                    exitSystem();
                }
                default: {//错误输入的提示信息
                    System.out.println("您的输入有误，请检查输入");
                    break;
                }
            }
        }while (in != null) ;
    }
}
