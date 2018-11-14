import java.awt.*;
import java.util.Scanner;

/**
 * @author: Yan_Daojiang
 * @date: 2018/11/13
 * @description:
 **/
public class Operator extends User {
    public Operator(String name, String password, String role) {
        super(name, password, role);
    }

    //上传文件
    public void updatefile(){
        System.out.println("请输入文件名称：");
        Scanner scanner=new Scanner(System.in);
        String nothing=scanner.nextLine();
        String file=scanner.nextLine();
        System.out.println("上传文件·····");
    }

    public void showMenu(){
        System.out.println("************您已进入档案录入人员系统*****************");
        String in="abc";
        do {
            //对菜单选项进行输出
            Scanner sc = new Scanner(System.in);
            System.out.println("1.上传文件\n2.下载文件\n3.文件列表\n4.修改密码\n5.退出\n");
            System.out.printf("请输入您的选择：");
            in = sc.next();
            //根据输入进行相关的操作
            switch (in) {
                case "1":{//上传文件
                    updatefile();
                    break;
                }
                case "2": {//下载文件
                    String nothing=sc.nextLine();
                    System.out.println("请输入您要下载的文件的名称:");
                    String filename=sc.nextLine();
                    downloadFile(filename);
                    break;
                }
                case "3": {//文件列表的显示
                    showFileList();
                    break;
                }
                case "4":{//修改密码

                    break;
                }
                case "5":{
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
