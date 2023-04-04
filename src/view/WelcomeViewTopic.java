package view;

import controller.TopicController;
import model.Topic;

import java.util.Scanner;

public class WelcomeViewTopic {
    public static void showWelcome(){
        TopicController controller=new TopicController();
        TopicView topicView=new TopicView();
        Scanner scanner=new Scanner(System.in);
        do {
            System.out.println("=====================|All list of Topic|==================");
            System.out.println(" 1. insert Topic");
            System.out.println(" 2. Update Topic");
            System.out.println(" 3. Delete Topic");
            System.out.println(" 4. Select By ID");
            System.out.println(" 5. Select By Name");
            System.out.println(" 6. show select ");
            System.out.println(" 0.Exit...");
            System.out.println(" Choose one option 1->6 : ");
            Integer option = Integer.parseInt(scanner.nextLine());
            switch (option)
            {
                case 1:{
                    Topic topic=new Topic();
                    System.out.println("--------------------|Insert|------------------------");
                    System.out.print(" Enter Name: ");
                    topic.setName(scanner.nextLine());
                    System.out.print(" Enter Description: ");
                    topic.setDescription(scanner.nextLine());
                    topic.setStatus(true);
                    controller.addTopic(topic);
                }break;
                case 2:{
                    Topic topic=new Topic();
                    System.out.println("---------------------|Update|---------------------");
                    System.out.print(" Input ID: ");
                    topic.setId(Integer.parseInt(scanner.nextLine()));
                    System.out.print(" Input Name: ");
                    topic.setName(scanner.nextLine());
                    System.out.print(" Input Description: ");
                    topic.setDescription(scanner.nextLine());
                    topic.setStatus(true);
                    controller.updateTopic(topic);
                }break;
                case 3:{
                    System.out.print(" Input ID: ");
                    Integer id=Integer.parseInt(scanner.nextLine());
                    controller.deleteTopic(id);
                }break;
                case 4:{
                    System.out.print(" Input ID: ");
                    Integer id=Integer.parseInt(scanner.nextLine());
                    topicView.showTopicView(controller.searchById(id));
                }break;
                case 5:
                {
                    System.out.print("Enter Name to Search : ");
                    String name=scanner.nextLine();
                    topicView.showTopicView(controller.searchByName(name));
                }break;
                case 6:{
                    topicView.showTopicView(controller.getAllTopic());
                }break;

            }
        }while (true);
    }
}
