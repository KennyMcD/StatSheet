import java.awt.Desktop;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import java.util.stream.Collectors;
import java.io.File;
import javafx.stage.FileChooser;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Stack;
// This is the driver class RUN PROGRAM HERE
public class StatSheet extends Application
{
  
  private Desktop desktop = Desktop.getDesktop();
 
  private TableView table = new TableView();
  BST<String> tree = new BST<String>();
  //Example Players for user
  private ObservableList<Player> data =
        FXCollections.observableArrayList(
            new Player("Example", 1, 1, 1, 1),
            new Player("Player", 2, 2, 2, 2),
            new Player("Names", 3, 3, 3, 3)
            );
          
  public static void main(String[] args)
  {
    launch(args);
  }
  
  //Main stage
  private class StatSheetStats extends GridPane 
  {
   Label lblPlayer;
   ComboBox<String> cbPlayer;

   Label lblStat;
   ComboBox<String> cbStat;
   ToggleGroup group;
   String genderString;
   

   TableView chart;
  }
  @Override
  public void start(Stage stage)
  {
    //BST's that compares players to a statistic
    BST<IntPlayer> pointTree = new BST<IntPlayer>();
    BST<IntPlayer> assistTree = new BST<IntPlayer>();
    BST<IntPlayer> reboundTree = new BST<IntPlayer>();
    BST<IntPlayer> stealTree = new BST<IntPlayer>();
    BST<Player>  playerTree = new BST<Player>();
    
    //scene settings
    Scene scene = new Scene(new Group());
    stage.setTitle("Table View Sample");

    // Creates buttons, labels, text fields, and table
    final Label label = new Label("Stat Chart: ");
    final TextArea textArea = TextAreaBuilder.create()
                .prefWidth(200)
                .wrapText(true)
                .build();
    textArea.setEditable(false);
    final Label label1 = new Label("Status");
    final TextArea statusArea = TextAreaBuilder.create()
                .prefWidth(150)
                .wrapText(true)
                .build();
    final TextArea teamOneArea = TextAreaBuilder.create()
                .prefWidth(128)
                .wrapText(true)
                .build();
    final TextArea teamTwoArea = TextAreaBuilder.create()
                .prefWidth(128)
                .wrapText(true)
                .build();
    
    FileChooser fileChooser = new FileChooser();
  
    final Button openButton  = new Button("Open a file");
    final Button writeButton = new Button("Write to file");
    
    final Button teamOneButton = new Button("Add player to team 1");
    final Button teamTwoButton = new Button("Add player to team 2");
      
    table.setEditable(true);
    //Formats and labels table's cols and rows
    TableColumn playerCol = new TableColumn("Player Name");
    playerCol.setMinWidth(100);
    playerCol.setCellValueFactory(
    new PropertyValueFactory<Player, String>("firstName"));
   
    TableColumn pointCol = new TableColumn("Points");
    pointCol.setMinWidth(100);
    pointCol.setCellValueFactory(
    new PropertyValueFactory<Player, Integer>("points"));
                
    TableColumn assistCol = new TableColumn("Assists");
    assistCol.setMinWidth(100);
    assistCol.setCellValueFactory(
    new PropertyValueFactory<Player, Integer>("assists"));
                
    TableColumn rebCol = new TableColumn("Rebounds");
    rebCol.setMinWidth(100);
    rebCol.setCellValueFactory(
    new PropertyValueFactory<Player, Integer>("rebounds"));
                
    TableColumn stealCol = new TableColumn("Steals");
    stealCol.setMinWidth(100);
    stealCol.setCellValueFactory(
    new PropertyValueFactory<Player, Integer>("steals"));
    //Sets data being inserted in the cells
    table.setItems(data);     
    table.getColumns().addAll(playerCol, pointCol, assistCol, rebCol, stealCol);
    //Text fields and what col they interact with
    final TextField addPlayer = new TextField();
    addPlayer.setPromptText("First Name");
    addPlayer.setMaxWidth(playerCol.getPrefWidth());
        
    final TextField addPoints = new TextField();
    addPoints.setMaxWidth(pointCol.getPrefWidth());
    addPoints.setPromptText("Points");
       
    final TextField addAssists = new TextField();
    addAssists.setMaxWidth(assistCol.getPrefWidth());
    addAssists.setPromptText("Assists");

    final TextField addRebounds = new TextField();
    addRebounds.setMaxWidth(rebCol.getPrefWidth());
    addRebounds.setPromptText("Rebounds");
        
    final TextField addSteals = new TextField();
    addSteals.setMaxWidth(stealCol.getPrefWidth());
    addSteals.setPromptText("Steals");
    //Adds information from text cells into table
    final Button addButton = new Button("Add");
    //Button action event
    //Instruction panel
    textArea.setText("Instructions: \n1) Enter a players name and stats into the table " +
                     "by pressing the \"Add\" button you will transfer the data from the text boxes to the TableView." +
                     "\n\n2) After you've added players to the TableView press the \"In-Order\" button to print players ordered " +
                     "stats into the console \n\n3) After entering a players values into the textboxes (not into the table!)" +
                     " press the \"Write to file\" buton and it will send the text from the textboxes to a file" +
                     ", repeat this step with new text and it will at it in the next line of the file\n\n" +
                     "4) To add a player to a team type the players name and press the \"Add to team\" button");
    
    addButton.setOnAction(new EventHandler<ActionEvent>() 
    {
      @Override
      public void handle(ActionEvent e) 
      {
        //Sends the values entered in text boxs to Player p
        Player p = new Player(addPlayer.getText(),
                   Integer.parseInt(addPoints.getText()),
                   Integer.parseInt(addAssists.getText()),
                   Integer.parseInt(addRebounds.getText()),
                   Integer.parseInt(addSteals.getText()));
                   data.add(p);
        //Adds Player and stat to an IntPlayer object     
        IntPlayer pointPlayer = new IntPlayer(p, Integer.parseInt(addPoints.getText()));
        IntPlayer assistPlayer = new IntPlayer(p, Integer.parseInt(addAssists.getText()));
        IntPlayer reboundPlayer = new IntPlayer(p, Integer.parseInt(addRebounds.getText()));
        IntPlayer stealPlayer = new IntPlayer(p, Integer.parseInt(addSteals.getText()));
        //Insert the certain IntPlayer object to BST   
        pointTree.insert(pointPlayer);
        assistTree.insert(assistPlayer);
        reboundTree.insert(reboundPlayer);
        stealTree.insert(stealPlayer);
        playerTree.insert(p);
        //Clear text boxes after click and sets focus to the first box             
        addPlayer.clear();
        addPoints.clear();
        addAssists.clear();
        addRebounds.clear();
        addSteals.clear();
        addPlayer.requestFocus();
              
      }
    });
      //Prints the integer statistics in-order with player name next to it
      final Button inOrderButton = new Button("In-Order");
      inOrderButton.setOnAction(new EventHandler<ActionEvent>() 
      {
        @Override
        public void handle(ActionEvent e) 
        {
          System.out.print("\nPoints in order:");
          pointTree.inorder();
          
          System.out.print("\nAssists in order:");
          assistTree.inorder();
                
          System.out.print("\nRebounds in order:");
          reboundTree.inorder();
                
          System.out.print("\nSteals in order:");
          stealTree.inorder();
                
          System.out.print("\nPlayer Names in order:");
          playerTree.inorder();
        }
      });
      
      //Opens file and prints name of file into textArea
      statusArea.setText("Write data to text.txt in same folder as java file");
      //Validates file and prints if no exceptions
      openButton.setOnAction(new EventHandler<ActionEvent>()
      {
        @Override
        public void handle(ActionEvent arg0) 
        {
          File selectedFile = fileChooser.showOpenDialog(null);
          if (selectedFile != null)
          {
            statusArea.setText("File selected: " + selectedFile.getName());
          }
          try 
          {
            desktop.open(selectedFile);
          } 
          catch (IOException ex) 
          {
            System.out.print("broken file");
          }          
        }    
      });
      //Add player to team 1
      Stack<String> teamOne = new Stack<>();
      teamOneButton.setOnAction(new EventHandler<ActionEvent>() 
      {
        @Override
        public void handle(ActionEvent es) 
        {
          if(teamOne.size() < 12)
          { 
            teamOne.push(addPlayer.getText());
            statusArea.setText("Player added to team 1! (max 12 players)");
            teamOneArea.setText("Team 1 roster: " + teamOne);
          }
          else
          { 
            statusArea.setText("Team Full");
          } 
          addPlayer.clear();
          addPlayer.requestFocus();
       }
       });
       //Add player to team 2
       Stack<String> teamTwo = new Stack<>();
       teamTwoButton.setOnAction(new EventHandler<ActionEvent>() 
       {
         @Override
         public void handle(ActionEvent es) 
         {
           if(teamTwo.size() < 12)
           { 
             teamTwo.push(addPlayer.getText());
             statusArea.setText("Player added to team 2! (max 12 players)");
             teamTwoArea.setText("Team 2 roster: " + teamTwo);
           }
           else
           { 
             statusArea.setText("Team Full");
           } 
           addPlayer.clear();
           addPlayer.requestFocus();
         }
        });
      //Writes teams to file  
      final Button teamWrite = new Button("Write team to file"); 
      teamWrite.setOnAction(new EventHandler<ActionEvent>()
      {
        @Override
        public void handle(ActionEvent arg0) 
        {
          File teamFile = fileChooser.showOpenDialog(null);
          if (teamFile != null)
          {
           statusArea.setText("Team File Selected: " + teamFile.getName());
           try
           {
             java.io.PrintWriter teamput = new java.io.PrintWriter(new FileWriter(teamFile, true));
            
             teamput.println("Team 1: " + teamOne.toString());
             teamput.println("Team 2: " + teamTwo.toString());
             teamput.close();
           }
           catch(IOException ex)
           {
             System.out.println (ex.toString());
             System.out.println("Could not find file");
           }
          } 
        }       
      });
      //Writing the players into a file
      writeButton.setOnAction(new EventHandler<ActionEvent>()
      {
        @Override
        public void handle(ActionEvent arg0) 
        {
          File selectedFile = fileChooser.showOpenDialog(null);
          if (selectedFile != null)
          {
           statusArea.setText("File selected: " + selectedFile.getName());
           try
           {
             java.io.PrintWriter output = new java.io.PrintWriter(new FileWriter(selectedFile, true));
            
             output.print(addPlayer.getText() + ", ");
             output.print(addPoints.getText()   + ", ");
             output.print(addAssists.getText()  + ", ");
             output.print(addRebounds.getText() + ", ");
             output.println(addSteals.getText()   + "");
             output.close();
           }
           catch(IOException ex)
           {
             System.out.println (ex.toString());
             System.out.println("Could not find file");
           }
          } 
        }       
      });
      //Formatting of scene
      final HBox hb = new HBox();
      hb.getChildren().addAll(addPlayer, addPoints, addAssists, addRebounds, addSteals, addButton, inOrderButton, openButton, writeButton);
      hb.setSpacing(3);
      
      final HBox hbTwo = new HBox();
      hbTwo.getChildren().addAll(textArea, table);
      hbTwo.setSpacing(3);
      
      final HBox buttonsTwo = new HBox();
      buttonsTwo.getChildren().addAll(teamOneButton, teamTwoButton, label1, teamWrite);
      buttonsTwo.setSpacing(3);
      
      
      final HBox team = new HBox();
      team.getChildren().addAll(teamOneArea, teamTwoArea, statusArea);
      team.setSpacing(1);
      
            
      final VBox vp = new VBox();
       
      vp.getChildren().addAll(hb, hbTwo, buttonsTwo, team);
      vp.setSpacing(0);
      
      ((Group) scene.getRoot()).getChildren().add(vp);
      
      stage.setScene(scene);
      stage.show(); 
      
  }
}
