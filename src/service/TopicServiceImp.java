package service;

import model.Topic;
import repository.TopicRepository;

import java.util.List;

public class TopicServiceImp implements TopicService{

    private final TopicRepository repository;

    public TopicServiceImp() {
        this.repository = new TopicRepository();
    }

    @Override
    public List<Topic> selectAll() {
        return repository.selectAll();
    }

    @Override
    public void addTopic(Topic topic) {
        repository.addTopic( topic);
    }

    @Override
    public void updateTopic(Topic topic) {
        repository.updateTopic(topic);
    }

    @Override
    public void deleteTopic(Integer id) {
        repository.deleteTopic(id);
    }

    @Override
    public Topic  searchById(Integer id) {
        return repository.searchById(id);
    }

    @Override
    public List<Topic> searchByName(String name) {
        return repository.searchByName(name);
    }
}
