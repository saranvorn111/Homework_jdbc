import model.Topic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static JdbcImpl jdbcImpl;
    private static Scanner scanner;

    public static void main(String[] args) {
        //Class JDBC form JdbcImpl can call DataSource
        jdbcImpl = new JdbcImpl();
        scanner = new Scanner(System.in);
        Topic topic = new Topic();

        int opction;
        label:
        do{
            System.out.println("=====================|All list of Topic|==================");
            System.out.println(" 1. insert Topic");
            System.out.println(" 2. Update Topic");
            System.out.println(" 3. Delete Topic");
            System.out.println(" 4. Select By ID");
            System.out.println(" 5. Select By Name");
            System.out.println(" 6. show select ");
            System.out.println(" 0.Exit...");
            opction = getIntegerValue(" Choose one option 1->6 : ");
            switch (opction){
                case 1 ->{
                    System.out.println("--------------------|Insert|------------------------");
                    System.out.print(" Enter Name: ");
                    topic.setName(scanner.nextLine());
                    System.out.print(" Enter Description: ");
                    topic.setDescription(scanner.nextLine());
                    topic.setStatus(true);
                    insertTopic(topic);

                }
                case 2->{
                    System.out.println("---------------------|Update|---------------------");
                    System.out.print(" Input ID: ");
                    topic.setId(Integer.parseInt(scanner.nextLine()));
                    System.out.print(" Input Name: ");
                    topic.setName(scanner.nextLine());
                    System.out.print(" Input Description: ");
                    topic.setDescription(scanner.nextLine());
                    topic.setStatus(true);
                    updateTopic(topic);
                }
                case 3->{
                    System.out.println("--------------------------|Delete|--------------------");
                    System.out.print(" Input ID: ");
                    topic.setId(Integer.parseInt(scanner.nextLine()));
                    deleteTopic(topic.getId());

                }
                case 4->{
                    System.out.println("--------------------------|Select by ID|-----------------");
                    System.out.print(" Input ID to select:  ");
                    topic.setId(scanner.nextInt());
                    selectTopicById(topic.getId());
                }
                case 5->{
                    System.out.println("---------------------------|Select By Name|----------------");
                    System.out.print(" Input Name to select:  ");
                    topic.setName(scanner.nextLine());
                    selectTopicByName(topic.getName());
                }
                case 6->{
                    System.out.println("-------------------------|Show select|----------------------");
                    selectTopic();
                }
                case 0->{
                    break label;
                }
            }

        }while(true);
    }
    private static void selectTopic() {
        try (Connection conn = jdbcImpl.dataSource().getConnection()) {
            // 1.Create SQL Statement
            String selectSql = "SELECT * FROM  topics order by id asc";
            PreparedStatement statement = conn.prepareStatement(selectSql);
            // 2.Execute SQL Statment
            ResultSet resultSet = statement.executeQuery();

            // 3.Process Result with ResultSet
            List<Topic> topics = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Boolean status = resultSet.getBoolean("status");
                topics.add(new Topic(id, name, description, status));
            }
            for (Topic topic : topics) {
                System.out.println(topic);
            }
        } catch (Exception e) {

        }
    }
    public static Integer getIntegerValue(String label){
        while (true){
            try{
                System.out.print(label);
                return Integer.parseInt(scanner.nextLine());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    private static void insertTopic(Topic topic) {
        try (Connection conn = jdbcImpl.dataSource().getConnection()) {
            String insetSql = "INSERT INTO topics (name,description,status) VALUES(?,?,?)";
            PreparedStatement statement = conn.prepareStatement(insetSql);
            statement.setString(1, topic.getName());
            statement.setString(2, topic.getDescription());
            statement.setBoolean(3, topic.getStatus());

            int count = statement.executeUpdate();
            System.out.println(count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void updateTopic(Topic topic){
        try (Connection conn = jdbcImpl.dataSource().getConnection()){
            System.out.println(" Enter ID to search: ");
            Integer id= Integer.parseInt(scanner.nextLine());
            String updateSql = "UPDATE topics SET name=?, description=?,status=? WHERE id= "+ topic.getId();
            PreparedStatement statement = conn.prepareStatement(updateSql);
            statement.setString(1,topic.getName());
            statement.setString(2,topic.getDescription());
            statement.setBoolean(3,topic.getStatus());
            int count = statement.executeUpdate();
            if(count>0){
                System.out.println(" Update successful");
            }else{
                System.out.println(" search ID not found");
            }
//            System.out.println(count);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private static void deleteTopic(Integer id){
        try(Connection conn =jdbcImpl.dataSource().getConnection()){
            String deleteSql = "DELETE FROM topics WHERE id= "+ id;
            PreparedStatement statement = conn.prepareStatement(deleteSql);
            int count = statement.executeUpdate();
            if(count>0){
                System.out.println("Delete already ");
            }
            else{
                System.out.println("Delete not found");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static Topic selectTopicById(Integer id){
        try(Connection conn =jdbcImpl.dataSource().getConnection()) {
            String selectById = "SELECT * FROM topics WHERE id= ?";
            PreparedStatement statement = conn.prepareStatement(selectById);
            statement.setInt(1,id);
            ResultSet resultSet =  statement.executeQuery();
            Topic topic = new Topic();
            while(resultSet.next()){
                topic.setId(resultSet.getInt("id"));
                topic.setName(resultSet.getString("name"));
                topic.setDescription(resultSet.getString("description"));
                topic.setStatus(Boolean.getBoolean("status"));
            }
            System.out.println(topic);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    private static Topic selectTopicByName(String name){
        try(Connection conn =jdbcImpl.dataSource().getConnection()){
            String selectSqlName = "SELECT * FROM topics WHERE name ILIKE '%"+ name+"'";
            PreparedStatement statement = conn.prepareStatement(selectSqlName);
            ResultSet resultSet =  statement.executeQuery();
            Topic topic = new Topic();
            while(resultSet.next()){
                topic.setId(resultSet.getInt("id"));
                topic.setName(resultSet.getString("name"));
                topic.setDescription(resultSet.getString("description"));
                topic.setStatus(Boolean.getBoolean("status"));

            }
            System.out.println(topic);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


}

