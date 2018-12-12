import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

/**
 * @author: Yan_Daojiang
 * @date: 2018/12/03
 * @description:操作人员类
 * ****本次实验实现的文件的上传下载和文件的查询
 **/

public class Operator extends User {
    private String name;
    private String password;
    private String role;

    private JFrame jFrame=new JFrame("操作员系统");
    private  Container contentPane = jFrame.getContentPane();

    private JPanel pan;
    private JLabel label;
    private JTextField text;
    private JButton down_bt;
    private JButton bt1=new JButton("上传文件");//负责这个菜单操作的按钮
    private JButton bt2=new JButton("下载文件");
    private JButton bt3=new JButton("文件列表");
    private JButton bt4=new JButton("退出系统");
    private JDialog dia;

    //用于上传的组件
    JPanel pan2=new JPanel();
    JPanel pan3=new JPanel();
    JPanel pan4=new JPanel();
    JPanel pan5=new JPanel();
    JLabel up_name=new JLabel("文件名");
    JLabel up_ID=new JLabel("文件编号");
    JLabel up_des=new JLabel("文件描述");
    JTextField up_tname=new JTextField(24);
    JTextField up_tID=new JTextField(23);
    JTextField up_tdes=new JTextField(23);
    JButton up_but=new JButton("确认上传");
    MyListener wlist=new MyListener();//创建监听者对象

    public Operator(String name, String password, String role) {
        super(name,password,role);
    }

    Scanner sc = new Scanner(System.in);//创建用于输入的一个对象

    //上传文件
    public boolean updatefile(String filename,String ID,String description){ //按照流的方式读取文件，并创建文件对象

        //构建要上传的文件
        File uploadfile=new File(filename);
        try {
            //按照流的方式读取文件的全部字节
            FileInputStream fin=new FileInputStream(uploadfile);
            int i,j=0;
            byte[] content=new byte[fin.available()];
            while((i=fin.read())!=-1){
                content[j]=(byte)i;
                j++;
            }
            fin.close();

            //获取要上传的文件的文件名，同时设定上传文件的保存位置
            File newfile=new File(uploadpath+uploadfile.getName());

            //上传文件
            if(!newfile.exists())//如果文件不存在就创建文件
                newfile.createNewFile();

            //按照流的方式写入文件
            OutputStream out=new FileOutputStream(newfile);
            out.write(content);

            //最后在系统中对上传的文件信息进行保存
            Date date=new Date();
            Timestamp timestamp=new Timestamp(date.getTime());
            DataProcessing.insertDoc(ID,super.getName() , timestamp, description, uploadfile.getName());

        }

        //对上传中可能产生的异常进行捕获处理
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return true;//文件上传成功的返回值
    }

    //显示档案录入人员菜单
    public void showMenu(){
        //操作员窗口设置
        jFrame.setSize(400,400);
        jFrame.setLocationRelativeTo(null);//窗口显在屏幕中央
        jFrame.setResizable(false); //窗口大小不可变
        jFrame.getContentPane().setLayout(new FlowLayout());//设置布局管理器
        jFrame.setDefaultCloseOperation(3);//设置关闭程序

        //向菜单窗口中添加按钮组件
        contentPane.add(bt1);
        contentPane.add(bt2);
        contentPane.add(bt3);
        contentPane.add(bt4);

        //设置窗口的可见性
        jFrame.setVisible(true);
        //文件下载组件
        label=new JLabel("文件编号");
        text=new JTextField(10);
        down_bt=new JButton("确认下载");
        //将组件添加到中间容器中
        pan=new JPanel();
        pan.add(label);
        pan.add(text);
        pan.add(down_bt);
        contentPane.add(pan);

        //初始设置中间容器不可见，点击相应的菜单按钮后显示
        pan.setVisible(false);

        //上传文件的信息
        pan2.add(up_name);
        pan2.add(up_tname);
        pan3.add(up_ID);
        pan3.add(up_tID);
        pan4.add(up_des);
        pan4.add(up_tdes);
        pan5.add(up_but);

        contentPane.add(pan2);
        contentPane.add(pan3);
        contentPane.add(pan4);
        contentPane.add(pan5);

        pan2.setVisible(false);
        pan3.setVisible(false);
        pan4.setVisible(false);
        pan5.setVisible(false);

        //设置按钮监听
        bt1.addActionListener(wlist);//上传文件监听
        bt2.addActionListener(wlist);//下载文件监听
        bt3.addActionListener(wlist);//文件列表监听
        bt4.addActionListener(wlist);//退出系统监听
        down_bt.addActionListener(wlist);//确认下载监听
        up_but.addActionListener(wlist);//上传监听
    }

    //内部类处理监听
    class MyListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton bt=(JButton)e.getSource();
            if(bt==bt1){
                //组件添加到对话框
                pan.setVisible(false);
                pan2.setVisible(true);
                pan3.setVisible(true);
                pan4.setVisible(true);
                pan5.setVisible(true);
            }
            if(bt==up_but){
                String filename=up_tname.getText();
                String ID=up_tID.getText();
                String des=up_tdes.getText();
                if(updatefile(filename,ID,des)){
                    pan2.setVisible(false);
                    pan3.setVisible(false);
                    pan4.setVisible(false);
                    pan5.setVisible(false);
                }
            }
            //下载文件
            if(bt==bt2){
                pan2.setVisible(false);
                pan3.setVisible(false);
                pan4.setVisible(false);
                pan5.setVisible(false);
                pan.setVisible(true);//点击下载文件后改为可见
            }
            if(bt==down_bt){
                String ID=text.getText();
                try{
                    if(downloadFile(ID)){
                        //文件下载成功
                       pan.setVisible(false);
                    }
                }catch (IOException ex){
                    System.out.println(ex.getMessage());
                }

            }

            //文件列表显示
            if(bt==bt3){
                try{
                    showFileList();
                }catch (SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }

            //退出系统
            if(bt==bt4){
                System.exit(0);
            }

        }
    }
}