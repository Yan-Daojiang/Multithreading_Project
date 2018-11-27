import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.*;
import java.io.*;

/**
 * @author: Yan_Daojiang
 * @date: 2018/11/27
 * @description:程序的主类，负责登录的相关的信息
 **/

public class Main {
    public static void main(String[] args) {
        System.out.println("*************欢迎进入文件管理系统*****************");
        String in="abc";
        do {
            //对菜单选项进行输出
            Scanner sc = new Scanner(System.in);
            System.out.println("1.登录\n2.退出");
            System.out.printf("请输入您的选择:");

            //根据输入进行相关的操作
            in = sc.next();
            switch (in) {
                case "1": {//执行登录的相关操作

                    //从键盘输入登录人员的用户名和密码
                    sc.nextLine();
                    System.out.print("请输入您的姓名:");
                    String name=sc.nextLine();
                    System.out.print("请输入您的密码:");
                    String pwd=sc.nextLine();

                    //在系统中进行查找
                    //如果找到相关的用户，就执行后面的操作；否则提示用户检查输入的信息
                    User temp=null;

                    try{
                        temp=DataProcessing.searchUser(name,pwd);
                        if(temp!=null)
                            //找到就根据身份进行对象的创建
                            //根据temp的身份显示相关管理人员的菜单信息
                            temp.showMenu();
                        else
                            System.out.println("请检查您的输入是否有误！");
                    } catch(SQLException e){
                        System.out.println(e.getMessage());
                    } catch(IllegalStateException e){
                        System.out.println(e.getMessage());
                    }finally{
                        break;
                    }
                }

                case "2": {//退出系统
                    System.out.println("系统退出, 谢谢使用 ! ");
                    System.exit(0);
                }

                default: {//对密码错误等情况给出提示
                    System.out.println("您的输入有误，请检查你的输入 ");
                    break;
                }
            }

        }while (in != null) ;
    }
}