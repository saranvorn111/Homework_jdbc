package controller;

import model.Topic;
import service.TopicServiceImp;

import java.util.List;

public class TopicController {
    private final TopicServiceImp topicServiceImp;

    public TopicController() {
        this.topicServiceImp = new TopicServiceImp();
    }
    public List<Topic> getAllTopic(){
        return topicServiceImp.selectAll();
    }
    public void addTopic(Topic topic){
        topicServiceImp.addTopic(topic);
    }
    public void updateTopic(Topic topic){
        topicServiceImp.updateTopic(topic);
    }
    public void deleteTopic(Integer id){
        topicServiceImp.deleteTopic(id);
    }
    public Topic searchById(Integer id){
       return topicServiceImp.searchById(id);
    }
    public List<Topic> searchByName(String name){
        return topicServiceImp.searchByName(name);
    }

}
