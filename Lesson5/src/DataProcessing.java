import java.util.Enumeration;
import java.util.Hashtable;
import java.sql.*;

/**
 * @author: Yan_Daojiang
 * @date: 2018/12/11
 * @description:通过数据库进行文件相关的操作
 * 主要涉及到的操作为sql语句的基本增删查改
 *
 **/

public  class DataProcessing {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    public static boolean connectedToDatabase=false;


    public static void connectedToDatabase(String driverName,String url,String user,String password){
        try{
            Class.forName(driverName);//加载驱动类
            connection=DriverManager.getConnection(url,user,password);//建立数据库连接
            connectedToDatabase=true;
        }catch(ClassNotFoundException ex){
            ex.getMessage();
        }catch (SQLException ex){
            ex.getMessage();
        }
    }


    //断开数据库连接
    public static void disConnectedToDatabase(){
        //数据库为谅解状态时
        if(connectedToDatabase){
            try{
                resultSet.close();
                statement.close();
                connection.close();
            }catch(SQLException ex){
                ex.getMessage();
            }finally{
                connectedToDatabase=false;
            }
        }
    }



    //档案查找
    public static Doc searchDoc(String DocID) throws SQLException{
        Doc temp=null;
        if(!connectedToDatabase)
            throw new SQLException("未连接到数据库");

        statement=connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );

        String sql="Select * from doc_info where Id='"+DocID+"'";
        resultSet=statement.executeQuery(sql);

        if(resultSet.next()){
            String ID=resultSet.getString("Id");
            String creator=resultSet.getString("creator");
            Timestamp timestamp=resultSet.getTimestamp("timestamp");
            String description=resultSet.getString("description");
            String filname=resultSet.getString("filename");
            temp=new Doc(ID,creator,timestamp,description,filname);
        }
        return temp;
    }



    //获取所有档案信息
    public static Enumeration<Doc> getAllDocs() throws SQLException{
        //使用哈希表保存数据库中取出的信息
        Hashtable<String,Doc> docs=new Hashtable<String,Doc>();
        Doc temp=null;
        Enumeration<Doc> e;
        if(!connectedToDatabase)
            throw new SQLException("未链接到数据库");

        statement=connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );

        String sql="select * from doc_info";
        resultSet=statement.executeQuery(sql);

        while(resultSet.next()) {
            String ID=resultSet.getString("ID");
            String creator=resultSet.getString("creator");
            Timestamp timestamp=resultSet.getTimestamp("timestamp");
            String description=resultSet.getString("description");
            String filename=resultSet.getString("filename");
            temp=new Doc(ID,creator,timestamp,description,filename);
            docs.put(ID, temp);
        }
        e=docs.elements();
        return e;
    }


    //数据库中插入新得档案信息
    public static boolean insertDoc(String ID, String creator, Timestamp timestamp,
                                    String description, String filename) throws SQLException{
        if(!connectedToDatabase)
            throw new SQLException("未连接到数据库");

        statement=connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE
        );

        //查询数据中是否已经存在改文件的编号
        String sql="select * from doc_info where Id='"+ID+"'";
        resultSet=statement.executeQuery(sql);

        if(resultSet.next())
            return false;

        //插入信息
        sql="insert into doc_info(Id,creator,timestamp,description,filename) " +
                "values "+"('"+ID+"','"+creator+"','"+timestamp+"','"+description+"','"+filename+"')";

        if(statement.executeUpdate(sql)>0)
            return true;
        else
            return false;
    }



    //根据用户名查找用户
    public static User searchUser(String sname) throws SQLException{
        User temp=null;
        if(!connectedToDatabase)
            throw new SQLException("未连接到数据库");

        statement=connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );

        String sql="Select * from doc_info where Id='"+sname+"'";
        resultSet=statement.executeQuery(sql);

        if(resultSet.next()){
            String username=resultSet.getString("username");
            String pwssword=resultSet.getString("password");
            String role=resultSet.getString("role");
            if(role.equals("browser")){
                temp=new Browser(username,pwssword,role);
            }
            if(role.equals("operator")) {
                temp = new Operator(username, pwssword, role);
            }
            if(role.equals("administrator")){
                temp=new Administrator(username,pwssword,role);
            }
        }
        return temp;
    }



    //  根据用户名和密码在数据库中查找用户
    public static User searchUser(String sname,String spassword) throws SQLException{
        User temp=null;
        if(!connectedToDatabase)
            throw new SQLException("未连接到数据库");

        statement=connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );

        String sql="select * from user_info where username='"+sname+"'and password='"+spassword+"'";
        resultSet=statement.executeQuery(sql);

        if(resultSet.next()){
            String username=resultSet.getString("username");
            String pwssword=resultSet.getString("password");
            String role=resultSet.getString("role");
            if(role.equals("browser")){
                temp=new Browser(username,pwssword,role);
            }
            if(role.equals("operator")) {
                temp = new Operator(username, pwssword, role);
            }
            if(role.equals("administrator")){
                temp=new Administrator(username,pwssword,role);
            }
        }
        return temp;
    }



    //获取档案管理系统中的所有用户的档案
    public static Enumeration<User> getAllUser() throws SQLException{
        Hashtable<String,User> users=new Hashtable<String,User>();
        User temp=null;
        Enumeration<User> e;
        if(!connectedToDatabase)
            throw new SQLException("未链接到数据库");

        statement=connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );
        String sql="select * from user_info";
        resultSet=statement.executeQuery(sql);

        while(resultSet.next()) {
            String username=resultSet.getString("username");
            String pwssword=resultSet.getString("password");
            String role=resultSet.getString("role");
            if(role.equals("browser")){
                temp=new Browser(username,pwssword,role);
                users.put(username, temp);
            }
            if(role.equals("operator")) {
                temp = new Operator(username, pwssword, role);
                users.put(username, temp);
            }
            if(role.equals("administrator")){
                temp=new Administrator(username,pwssword,role);
                users.put(username, temp);
            }
        }
        e=users.elements();
        return e;
    }



    //更新数据库中的用户信息
    public static boolean updateUser(String name, String password, String role) throws SQLException{
        if(!connectedToDatabase)
            throw new SQLException("未连接到数据库");

        statement=connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE
        );
        //查询数据库中是否已经存在要更新的用户
        String sql="select * from user_info where username='"+name+"'";
        resultSet=statement.executeQuery(sql);
        if(!resultSet.next())
            return false;

        sql="update user_info set password='"+password+"',role='"+role+"' where username='"+name+"'";

        if(statement.executeUpdate(sql)>0)
            return true;
        else
            return false;
    }


    //向数据库中插入新的用户
    public static boolean insertUser(String name, String password, String role) throws SQLException {
        if (!connectedToDatabase)
            throw new SQLException("未连接到数据库");

        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE
        );

        String sql = "select * from user_info where username='" + name + "'";
        resultSet = statement.executeQuery(sql);

        if (resultSet.next())
            return false;

        sql = "insert into user_info(username,password,role) values " +
                "" + "('" + name + "','" + password + "','" + role + "')";
        if (statement.executeUpdate(sql) > 0)
            return true;
        else
            return false;
    }


    //从数据库中删除一个存在的用户
    public static boolean deleteUser(String name) throws SQLException{
        if(!connectedToDatabase)
            throw new SQLException("未连接到数据库");

        statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE
        );
        String sql="select * from user_info where username='"+name+"'";
        resultSet=statement.executeQuery(sql);
        if(!resultSet.next())
            return false;

        sql="delete from user_info where username='"+name+"'";

        if(statement.executeUpdate(sql)>0)
            return true;
        else
            return false;
    }

}


