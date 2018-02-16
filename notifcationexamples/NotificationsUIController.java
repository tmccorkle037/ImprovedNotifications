/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import static java.lang.Thread.State.TERMINATED;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static javax.lang.model.type.TypeKind.NULL;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button task1Button;
    
    @FXML
    private Button task2Button;
    
    @FXML
    private Button task3Button;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void controlTask1(ActionEvent event){
        if(task1 == null){
            startTask1();
            System.out.println("1st start task 1");
        }
        
        task1Button.setOnAction((ActionEvent event1) -> {
            if(task1.isAlive()){
                stopTask1();
                System.out.println("stopped task 1");
            }
            else if( task1.getState() == TERMINATED){
                task1 = null;
                startTask1();   
            }
        });
    }
   
    public void startTask1() {
        System.out.println("start task 1");
        if (task1 == null) {
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1Button.setText("Stop Task 1");
            task1.start();
        }
    }
    
    public void stopTask1(){
            task1Button.setText("Start Task 1");
            task1.end();
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            task1Button.setText("Start Task1");
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void controlTask2(ActionEvent event){
        if(task2 == null){
            startTask2();
            System.out.println("1st start task 2");
        }
        
        task2Button.setOnAction((ActionEvent event1) -> {
            if(task2.isAlive()){
                stopTask2();
                System.out.println("stopped task 2");
            }
            if( task2.getState() == TERMINATED){
                task2 = null;
                startTask2();   
            }
        });
    }
    
    @FXML
    public void startTask2() {
        System.out.println("start task 2");
        if (task2 == null) {
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
            });
            
            task2Button.setText("Stop Task 2");
            task2.start();
        }
    }
    
    public void stopTask2(){
            task2Button.setText("Start Task 2");
            task2.end();
    }
    
    @FXML
    public void controlTask3(ActionEvent event){
        if(task3 == null){
            startTask3();
            System.out.println("1st start task 3");
        }
        
        task3Button.setOnAction((ActionEvent event1) -> {
            if(task3.isAlive()){
                stopTask3();
                System.out.println("stopped task 3");
            }
            else if( task3.getState() == TERMINATED){
                task3 = null;
                startTask3();   
            }
        });
    }
//    @FXML
    public void startTask3() {
        System.out.println("start task 3");
        
            if (task3 == null) {
                task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
                task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
                });
            }
            task3Button.setText("Stop Task 3");
            task3.start();
    }
    
    
    public void stopTask3(){
            task3Button.setText("Start Task 3");
            task3.end();
    }
}

