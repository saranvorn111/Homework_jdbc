package service;

import model.Topic;
import java.util.List;

public interface TopicService {
    List<Topic> selectAll();
    void addTopic(Topic topic);
    void updateTopic(Topic topic);

    void deleteTopic(Integer id);

    Topic searchById(Integer id);

    List<Topic> searchByName(String name);
}
