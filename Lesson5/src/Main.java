import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.*;
import java.io.*;

/**
 * @author: Yan_Daojiang
 * @date:2018/12/03
 * @description:程序的主类，负责登录的相关的信息
 * 添加登录部分的GUI
 **/

public class Main{
    static JFrame jFrame=new JFrame("文件管理系统1.0");
    static JLabel lab1=new JLabel("姓名");
    static JLabel lab2=new JLabel("口令");
    static JTextField text1,text2;//用于输入账号和口令的文本框
    static JDialog diag=new JDialog(jFrame);//创建对话框
    static JButton bt1=new JButton("登录");
    static JButton bt2=new JButton("退出");

    static MyWinListener wlist=new MyWinListener();//创建监听者对象


    public static void main(String[] args) {

        String driverName="com.mysql.jdbc.Driver";               // 加载数据库驱动类
        String url="jdbc:mysql://localhost:3306/document?useSSL=false";       // 声明数据库的URL
        String user="root";                                      // 数据库用户
        String password="123456";
        DataProcessing.connectedToDatabase(driverName, url, user, password);//数据库链接

        //主登录界面
        jFrame.setSize(300,150);
        jFrame.setLocationRelativeTo(null);//窗口显在屏幕中央
        jFrame.setResizable(false); //窗口大小不可变
        jFrame.getContentPane().setLayout(new FlowLayout());//设置布局管理器
        jFrame.setDefaultCloseOperation(3);//设置关闭程序

        //设置文本框
        text1=new JTextField(23);
        text2=new JTextField(23);

        //设置对话框
        diag.setTitle("提示");
        diag.setSize(100,90);
        diag.setLayout(new FlowLayout(FlowLayout.CENTER,5,20));
        JLabel label_tips=new JLabel("请检查你的输入");//设置对话框上的显示信息
        diag.add(label_tips);
        diag.setLocationRelativeTo(null);//对话框显示在屏幕中央

        Container contentPane = jFrame.getContentPane();
        contentPane.add(lab1);
        contentPane.add(text1);
        contentPane.add(lab2);
        contentPane.add(text2);
        contentPane.add(bt1);
        contentPane.add(bt2);

        //注册监听
        bt1.addActionListener(wlist);
        bt2.addActionListener(wlist);

        //设置窗口可见
        jFrame.setVisible(true);
        }


    static class MyWinListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton bt=(JButton)e.getSource();//获取被单击的按钮

            //点击登录的事件
            if(bt==bt1){
                //点击登录按钮就登录
                //获取文本框中的用户名和密码
                String name=text1.getText();
                String pwd=text2.getText();

                //在系统中进行查找
                    User temp=null;
                    try{
                        temp=DataProcessing.searchUser(name,pwd);
                        if(temp!=null) {
                            jFrame.setVisible(false);//登录成功后设置登录界面不可见
                            temp.showMenu();
                        }
                        else
                            diag.setVisible(true);//登录不成功就显示对话框
                    } catch(SQLException ex){
                        System.out.println(ex.getMessage());
                    } catch(IllegalStateException ex){
                        System.out.println(ex.getMessage());
                    }
            }

            //点击退出的响应
            if(bt==bt2){
                //点击退出就退出系统
                System.exit(0);
            }

        }
    }
}