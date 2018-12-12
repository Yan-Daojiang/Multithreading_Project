import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author: Yan_Daojiang
 * @date:2018/12/10
 * @description:浏览人员类
 * **添加浏览人员部分GUI
 **/

public class Browser extends User {
    private String name;
    private String password;
    private String role;

    private JFrame jFrame=new JFrame("浏览员系统");

    private JDialog diag=new JDialog();//创建隶属于jframe的对话框

    private JButton bt1=new JButton("下载文件");//负责是个菜单操作的按钮
    private JButton bt2=new JButton("文件列表");
    private JButton bt3=new JButton("修改密码");
    static JButton bt4=new JButton("退出系统");

    private JPanel pan1=new JPanel();//处理文件下载的和密码修改的panel
    private JPanel pan2=new JPanel();


    private JLabel diag_label1=new JLabel("文件下载成功");
    private JLabel diag_label2=new JLabel("文件下载失败");

    private JLabel diag_label3=new JLabel("密码修改成功");
    private JLabel diag_label4=new JLabel("密码修改失败");

    //设置panel中需要的按钮，标签，文本框
    private JLabel pan1_lable1=new JLabel("文件编号");
    private JLabel pan2_lable2=new JLabel("新密码");
    private JTextField pan1_text1=new JTextField(10);
    private JTextField pan2_text2=new JTextField(10);//接受新密码的文本框
    private JButton pan1_bt1=new JButton("下载");
    private JButton pan2_bt2=new JButton("确认修改");

    Container contentPane = jFrame.getContentPane();
    MyListener wlist=new MyListener();//创建监听者对象


    public Browser(String name, String password, String role) {
        super(name, password, role);
        this.name=name;
        this.password=password;
        this.role=role;
    }

    //档案浏览员菜单
    public void showMenu() {
        //浏览员窗口界面
        jFrame.setSize(400,200);
        jFrame.setLocationRelativeTo(null);//窗口显在屏幕中央
        jFrame.setResizable(false); //窗口大小不可变
        jFrame.getContentPane().setLayout(new FlowLayout());//设置布局管理器
        jFrame.setDefaultCloseOperation(3);//设置关闭程序

        //向窗口中添加菜单操作的按钮
        contentPane.add(bt1);
        contentPane.add(bt2);
        contentPane.add(bt3);
        contentPane.add(bt4);

        jFrame.setVisible(true);//设置出口可见性

        //文件下载
        pan1.setSize(400,120);
        pan1.add(pan1_lable1);
        pan1.add(pan1_text1);
        pan1.add(pan1_bt1);
        pan1.setVisible(false);
        contentPane.add(pan1);
        //密码修改部分组件
        pan2.setSize(400,120);
        pan2.add(pan2_lable2);
        pan2.add(pan2_text2);
        pan2.add(pan2_bt2);
        pan2.setVisible(false);
        contentPane.add(pan2);
        //对话框提示
        diag.setTitle("提示");
        diag.setSize(100,100);
        diag.setLocationRelativeTo(null);

        //注册监听
        bt1.addActionListener(wlist);
        bt2.addActionListener(wlist);
        bt3.addActionListener(wlist);
        bt4.addActionListener(wlist);
        pan1_bt1.addActionListener(wlist);
        pan2_bt2.addActionListener(wlist);
    }

    class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            JButton bt=(JButton)e.getSource();//获取被单击的按钮

            //点击文件下载按钮的事件
            if(bt==bt1) {
                pan2.setVisible(false);
                pan1.setVisible(true);
            }

            //点击确认下载的事件
            if(bt==pan1_bt1){
                String ID=pan1_text1.getText();
                try {
                    if (downloadFile(ID)) {
                        //文件下载成功就隐藏组件同时显示对话框
                        diag.remove(diag_label1);
                        diag.remove(diag_label2);
                        diag.remove(diag_label3);
                        diag.remove(diag_label4);
                        diag.add(diag_label1);//对话框中添加提示标签
                        diag.setVisible(true);
                        pan1.setVisible(false);
                        jFrame.remove(pan1);
                    }
                    else{
                        //文件下载失败对话框显示
                        diag.remove(diag_label1);
                        diag.remove(diag_label2);
                        diag.remove(diag_label3);
                        diag.remove(diag_label4);
                        diag.add(diag_label2);//对话框中添加提示标签
                        diag.setVisible(true);
                    }
                }catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }

            //点击文件列表显示的事件
            if(bt==bt2){
                try{
                    showFileList();
                }catch (SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }

            //点击修改密码的事件
            if(bt==bt3) {
                pan2.setVisible(true);
                pan1.setVisible(false);
            }

            //点击确认修改密码的事件
            if(bt==pan2_bt2){
                String pwd=pan2_text2.getText();
                try {
                    if (changeSelfInfo(pwd)) {
                        //修改密码成功就显示提示对话框同时移除修改框
                        diag.remove(diag_label1);
                        diag.remove(diag_label2);
                        diag.remove(diag_label3);
                        diag.remove(diag_label4);
                        diag.add(diag_label3);//对话框中添加提示标签
                        diag.setVisible(true);
                        pan2.setVisible(false);
                        jFrame.remove(pan2);
                    }
                    else{
                        //密码修改不成功的对话框的显示
                        diag.add(diag_label4);
                        diag.setVisible(true);
                    }
                }catch(SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }

            //点击退出系统的事件
            if(bt==bt4){
                System.exit(0);
            }
        }
    }

}