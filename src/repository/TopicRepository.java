package repository;

import connection.JdbcImpl;
import model.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TopicRepository {
    private final JdbcImpl jdbc;

    public TopicRepository() {
        this.jdbc = new JdbcImpl();
    }

    public List<Topic> selectAll() {

        try ( Connection conn = jdbc.dataSource().getConnection()){

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
            return topics;
        } catch (Exception e) {

        }
        return null;
    }
    public void addTopic(Topic topic){
        try (Connection conn = jdbc.dataSource().getConnection()) {
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

    public void updateTopic(Topic topic) {
        try (Connection conn = jdbc.dataSource().getConnection()){
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

    public void deleteTopic(Integer id) {
        try(Connection conn =jdbc.dataSource().getConnection()){
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

    public Topic searchById(Integer id) {
        try(Connection conn =jdbc.dataSource().getConnection()) {
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

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Topic> searchByName(String name) {
        try(Connection conn =jdbc.dataSource().getConnection()){
            String selectSqlName = "SELECT * FROM topics WHERE name ILIKE '%"+ name+"'";
            PreparedStatement statement = conn.prepareStatement(selectSqlName);
            ResultSet resultSet =  statement.executeQuery();
            List<Topic> topic = new ArrayList<>();
            while(resultSet.next()){
                topic.add(
                        new Topic(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                Boolean.getBoolean("status")
                        )
                );
            }
            return topic;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
