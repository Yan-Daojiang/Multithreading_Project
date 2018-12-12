import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * @author: Yan_Daojiang
 * @date: 2018/12/03
 * @description:管理人员类，主要负管理人员的的相关的操作
 ****对文件下载和文件列表部分进行优化
 **/

public class Administrator extends User {
    private String name;
    private String password;
    private String role;


    private JFrame jFrame=new JFrame("管理员系统");
    private Container contentPane = jFrame.getContentPane();
    //管理员系统基本组件
    private JButton bt1=new JButton("修改用户");
    private JButton bt2=new JButton("删除用户");
    private JButton bt3=new JButton("增加用户");
    private JButton bt4=new JButton("用户列表");
    private JButton bt5=new JButton("文件列表");
    private JButton bt6=new JButton("退出系统");
    //用户显示基本量组件
    private JDialog diag1=new JDialog();
    private JTextArea textArea1=new JTextArea();
    //删除用户所需组件
    private JPanel del_pan=new JPanel();
    private JLabel del_lab=new JLabel("用户姓名");
    private JTextField del_text=new JTextField(10);
    private JButton del_bt=new JButton("确认删除");
    //修改用户所需的组件
    private JPanel ch_pan1=new JPanel();
    private JPanel ch_pan2=new JPanel();
    private JPanel ch_pan3=new JPanel();
    private JPanel ch_pan4=new JPanel();
    private JLabel ch_name=new JLabel("姓名");
    private JLabel ch_pw=new JLabel("新密码");
    private JLabel ch_role=new JLabel("新身份");
    private JTextField ch_tname=new JTextField(24);
    private JTextField ch_tpw=new JTextField(23);
    private JTextField ch_trole=new JTextField(23);
    private JButton ch_but=new JButton("确认修改");
    //增加用户所需组件
    private JPanel add_pan1=new JPanel();
    private JPanel add_pan2=new JPanel();
    private JPanel add_pan3=new JPanel();
    private JPanel add_pan4=new JPanel();
    private JLabel add_name=new JLabel("姓名");
    private JLabel add_pw=new JLabel("密码");
    private JLabel add_role=new JLabel("身份");
    private JTextField add_tname=new JTextField(25);
    private JTextField add_tpw=new JTextField(25);
    private JTextField add_trole=new JTextField(25);
    private JButton add_but=new JButton("确认增加");

    MyListener wlist=new MyListener();

    public Administrator(String name, String password, String role) {
        super(name, password, role);
        this.name=name;
        this.password=password;
        this.role=role;
    }

    Scanner sc = new Scanner(System.in);//创建用于一个输入的对象

    //用户列表显示，利用对话框显示系统中的用户的信息
    public void listUser(){
        String message1="用户姓名\t用户密码            用户角色"+"\n";
        //用于用户显示的对话框的基本设置
        diag1.setTitle("用户列表");
        diag1.setSize(330,200);

        //初始化一个对话框
        textArea1=new JTextArea(message1);
        textArea1.setLineWrap(true);//文本区换行处理
        diag1.add(textArea1);//文本区添加到对话框
        diag1.setLocationRelativeTo(null);
        textArea1.setEditable(false);//文本区设置为不可编辑状态

        //枚举哈希表中的值，用来输出系统中的人员信息
        try {
            for (Enumeration<User> e = DataProcessing.getAllUser(); e.hasMoreElements(); ) {
                User user = e.nextElement();
                textArea1.setWrapStyleWord(true);
                textArea1.append(user.getName() +"   \t     " + user.getPassword()+"\t"
                        + user.getRole()+"\n");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        textArea1.paintImmediately(textArea1.getX(), textArea1.getY(), textArea1.getWidth(), textArea1.getHeight());

        diag1.setResizable(false);//设置对话框大小不可改变
        diag1.validate();
        diag1.setVisible(true);
    }

    //删除用户
    public boolean delUser(String name) {
        try {
            if (DataProcessing.deleteUser(name))
                return true;
            else
                return false;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    //增加用户
    public boolean addUser(String name,String pwd,String role){

        try {
            if (DataProcessing.insertUser(name, pwd, role)) {
                return true;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    //修改用户信息
    public boolean changeInfo(String name,String pwd,String role){

        //更新用户的信息
        try {
            if (DataProcessing.updateUser(name, pwd, role))
                return true;
            else
                return false;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } catch(IllegalStateException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    //管理人员操作菜单
    public void showMenu() {
        //管理员窗口的基本设置
        //操作员窗口设置
        jFrame.setSize(400,400);
        jFrame.setLocationRelativeTo(null);//窗口显在屏幕中央
        jFrame.setResizable(false); //窗口大小不可变
        jFrame.getContentPane().setLayout(new FlowLayout());//设置布局管理器
        jFrame.setDefaultCloseOperation(3);//设置关闭程序
        //向窗口中添加基本组件
        contentPane.add(bt1);
        contentPane.add(bt2);
        contentPane.add(bt3);
        contentPane.add(bt4);
        contentPane.add(bt5);
        contentPane.add(bt6);
        //设置窗口的可见性
        jFrame.setVisible(true);

        //删除用户组件设置
        del_pan.add(del_lab);
        del_pan.add(del_text);
        del_pan.add(del_bt);
        del_pan.setVisible(false);//设置初始可见性
        contentPane.add(del_pan);

        //修改用户组件设置
        ch_pan1.add(ch_name);
        ch_pan1.add(ch_tname);
        ch_pan2.add(ch_pw);
        ch_pan2.add(ch_tpw);
        ch_pan3.add(ch_role);
        ch_pan3.add(ch_trole);
        ch_pan4.add(ch_but);
        contentPane.add(ch_pan1);
        contentPane.add(ch_pan2);
        contentPane.add(ch_pan3);
        contentPane.add(ch_pan4);
        ch_pan1.setVisible(false);
        ch_pan2.setVisible(false);
        ch_pan3.setVisible(false);
        ch_pan4.setVisible(false);

        //增加用户组件设置
        add_pan1.add(add_name);
        add_pan1.add(add_tname);
        add_pan2.add(add_pw);
        add_pan2.add(add_tpw);
        add_pan3.add(add_role);
        add_pan3.add(add_trole);
        add_pan4.add(add_but);
        contentPane.add(add_pan1);
        contentPane.add(add_pan2);
        contentPane.add(add_pan3);
        contentPane.add(add_pan4);
        add_pan1.setVisible(false);
        add_pan2.setVisible(false);
        add_pan3.setVisible(false);
        add_pan4.setVisible(false);

        //组件注册监听
        bt1.addActionListener(wlist);
        bt2.addActionListener(wlist);
        bt3.addActionListener(wlist);
        bt4.addActionListener(wlist);
        bt5.addActionListener(wlist);
        bt6.addActionListener(wlist);
        del_bt.addActionListener(wlist);//删除用户按钮注册监听
        ch_but.addActionListener(wlist);//修改用户注册监听
        add_but.addActionListener(wlist);//增加用户组件监听
    }

    //定义内部类处理监听
    class MyListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton bt=(JButton)e.getSource();//获取被单击的按钮

            //修改用户
            if(bt==bt1){
                add_pan1.setVisible(false);
                add_pan2.setVisible(false);
                add_pan3.setVisible(false);
                add_pan4.setVisible(false);
                del_pan.setVisible(false);
                ch_pan1.setVisible(true);
                ch_pan2.setVisible(true);
                ch_pan3.setVisible(true);
                ch_pan4.setVisible(true);
            }
            if(bt==ch_but){
                String ch_name=ch_tname.getText();
                String ch_pwd=ch_tpw.getText();
                String ch_role=ch_trole.getText();
                if(changeInfo(ch_name,ch_pwd,ch_role)){//删除用户成功
                    ch_pan1.setVisible(false);
                    ch_pan2.setVisible(false);
                    ch_pan3.setVisible(false);
                    ch_pan4.setVisible(false);
                }
            }
            //删除用户
            if(bt==bt2){add_pan1.setVisible(true);
                add_pan1.setVisible(false);
                add_pan2.setVisible(false);
                add_pan3.setVisible(false);
                add_pan4.setVisible(false);
                ch_pan1.setVisible(false);
                ch_pan2.setVisible(false);
                ch_pan3.setVisible(false);
                ch_pan4.setVisible(false);
                del_pan.setVisible(true);
            }
            if(bt==del_bt){
                String del_name=del_text.getText();
                if(delUser(del_name)){//删除用户成功后的操作
                    del_pan.setVisible(false);
                }
            }
            //增加用户
            if(bt==bt3){
                del_pan.setVisible(false);
                ch_pan1.setVisible(false);
                ch_pan2.setVisible(false);
                ch_pan3.setVisible(false);
                ch_pan4.setVisible(false);
                add_pan1.setVisible(true);
                add_pan2.setVisible(true);
                add_pan3.setVisible(true);
                add_pan4.setVisible(true);
            }
            if(bt==add_but){
                String add_name=add_tname.getText();
                String add_pwd=add_tpw.getText();
                String add_role=add_trole.getText();
                if(addUser(add_name,add_pwd,add_role)) {//删除用户成功
                    add_pan1.setVisible(false);
                    add_pan2.setVisible(false);
                    add_pan3.setVisible(false);
                    add_pan4.setVisible(false);
                }
            }

            //用户列表显示
            if(bt==bt4){
                listUser();
            }

            //文件列表显示
            if(bt==bt5){
                try{
                    showFileList();
                }catch (SQLException ex){
                    System.out.println(ex.getMessage());
            }
            }

            //退出系统
            if(bt==bt6){
                System.exit(0);
            }
        }
    }
}