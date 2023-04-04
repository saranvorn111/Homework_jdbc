package view;

import controller.TopicController;
import model.Topic;

import java.util.List;

public class TopicView {
    private final TopicController controller;

    public TopicView() {
        this.controller = new TopicController();
    }


    public void showView(){
        controller.getAllTopic().forEach(topic -> System.out.println(topic));
    }
    public void showTopicView(Topic topic){
        System.out.println(topic);
    }
    public void showTopicView(List<Topic> topic){
        for(int i=0;i<topic.size();i++){
            System.out.println(topic.get(i));
        }
    }
}
