import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.Scanner;
/**
 * @author: Yan_Daojiang
 * @date: 2018/11/13
 * @description:
 **/
import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args) {
        System.out.println("*************欢迎进入文件管理系统*****************");
        String in="abc";
        do {
            //对菜单选项进行输出
            Scanner sc = new Scanner(System.in);
            System.out.println("1.登录\n2.退出");
            System.out.printf("请输入您的选择：");
            in = sc.next();
            //根据输入进行相关的操作
            switch (in) {
                case "1": {//执行登录的相关操作
                    String nothing=sc.nextLine();
                    System.out.print("请输入您的姓名:");
                    //登录界面的相关信息的输入
                    String name=sc.nextLine();
                    System.out.print("请输入您的密码：");
                    String pwd=sc.nextLine();
                    //在系统中进行查找
                    //如果找到相关的用户，就执行后面的操作；否则提示用户检查输入的信息
                    User temp=DataProcessing.search(name,pwd);
                    if(temp!=null)
                        //找到就根据身份进行对象的创建
                       temp.showMenu();
                    else
                        System.out.println("请检查您的输入是否有误！");
                    break;
                }
                case "2": {//退出系统
                    System.out.println("系统退出, 谢谢使用 ! ");
                    System.exit(0);
                }
                default: {
                    System.out.println("您的输入有误，请检查你的输入 ");
                    break;
                }
            }
        }while (in != null) ;
    }
}