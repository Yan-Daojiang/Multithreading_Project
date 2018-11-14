import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author: Yan_Daojiang
 * @date: 2018/11/13
 * @description:
 **/

public class Browser extends User {
    public Browser(String name, String password, String role) {
        super(name, password, role);
    }
    public void showMenu(){
        System.out.println("************您已进入浏览员系统*****************");
        String in="abc";
        do {
            //对菜单选项进行输出
            Scanner sc = new Scanner(System.in);
            System.out.println("1.下载文件\n2.文件列表\n3.修改密码\n4.退出");
            System.out.printf("请输入您的选择：");

            //根据输入进行相关的操作
            in = sc.next();
            switch (in) {
                case "1": {//下载文件
                    String nothing=sc.nextLine();
                    System.out.println("请输入您要下载的文件的名称:");
                    String filename=sc.nextLine();
                    downloadFile(filename);
                    break;
                }
                case "2": {//文件列表的显示
                    showFileList();
                    break;
                }
                case "3":{//修改密码

                    break;
                }
                case "4":{
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
