
package blackjack;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GameMain extends Application
{
    //create deck, a hand with a dealer and a player and a message to show who won the game
    private Deck deck = new Deck();
    private Hand dealer, player;
    private Text message = new Text();
    
    //create a boolean to figure out if it is playable
    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
    
    //card spots
    HBox dealerCards = new HBox(20);
    HBox playerCards = new HBox(20);
    
    //create content for game
    private Parent createContent()
    {
        //create a hand for dealer and player
        dealer = new Hand(dealerCards.getChildren());
        player = new Hand(playerCards.getChildren());
        
        Pane pane = new Pane();
        pane.setPrefSize(1000, 600);
        
        //make black background with padding
        Region background = new Region();
        background.setPrefSize(1000, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");
        HBox paneLayout = new HBox(5);
        paneLayout.setPadding(new Insets(5,5,5,5));
        
        //left side
        Rectangle leftSide = new Rectangle(750, 550);
        leftSide.setArcWidth(50);
        leftSide.setArcHeight(50);
        leftSide.setFill(Color.GREEN);
        //right side
        Rectangle rightSide = new Rectangle(230, 550);
        rightSide.setArcWidth(50);
        rightSide.setArcHeight(50);
        rightSide.setFill(Color.DEEPPINK);
       
        //left stack
        VBox leftBox = new VBox(50);
        leftBox.setAlignment(Pos.TOP_CENTER);
        
        //dealer and player scores shown
        Text dealerScore = new Text("Dealer score: ");
        Text playerScore = new Text("Player score: ");
        leftBox.getChildren().addAll(dealerScore, dealerCards, message, playerCards, playerScore);
        
        //right stack
        
        VBox rightBox = new VBox(20);
        rightBox.setAlignment(Pos.CENTER);
        Button btnPlay = new Button("Play New Hand");
        Button btnHit = new Button("Hit");
        Button btnStay = new Button("Stay");
        HBox buttonHBox = new HBox(15);
        buttonHBox.setAlignment(Pos.CENTER);
        btnHit.setDisable(true);
        btnStay.setDisable(true);
        buttonHBox.getChildren().addAll(btnHit, btnStay);
        
        rightBox.getChildren().addAll(btnPlay, buttonHBox);
        
        paneLayout.getChildren().addAll(new StackPane(leftSide, leftBox),new StackPane(rightSide, rightBox));
        pane.getChildren().addAll(background, paneLayout);
        
        
        //binding properties
        btnPlay.disableProperty().bind(playable);
        btnHit.disableProperty().bind(playable.not());
        btnStay.disableProperty().bind(playable.not());
        
        playerScore.textProperty().bind(new SimpleStringProperty("Player : " ).concat(player.valueProperty().asString()));
        dealerScore.textProperty().bind(new SimpleStringProperty("Dealer : ").concat(dealer.valueProperty().asString()));

        
        //Event listener for the player from observable list value
        player.valueProperty().addListener((obs,old, intValue) -> 
        {
            //if 21 has been reached or gone over end game
             if(intValue.intValue() >= 21)
             {
                endGame();
             }
        });
        //Event listener for the dealer from observable list value
        dealer.valueProperty().addListener((obs, old, intValue) ->
       {
           //if 21 has been reached or gone over end game
           if(intValue.intValue() >= 21)
           {
               endGame();
           }
       });
        //set on action events for buttons
        btnPlay.setOnAction(event -> 
        {
            startNewGame(); 
        });
        btnHit.setOnAction(event -> 
       {
            player.takeCard(deck.drawCard()); 
       });
        btnStay.setOnAction(event ->
       {
           //while the dealer has a score of less than 17 draw a card if not then stay
         while(dealer.valueProperty().get() < 17)
         {
             dealer.takeCard(deck.drawCard());
         }
          endGame();
        });
         return pane;
        
    }
    private void startNewGame()
    {
        playable.set(true);
        message.setText("");
        
        deck.refill();
        //reset hands
        dealer.reset();
        player.reset();
        //draw cards
        player.takeCard(deck.drawCard());
        dealer.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
        dealer.takeCard(deck.drawCard());
        
        
    }
    //end game method needs redone 
    public void endGame()
    {
        playable.set(false);
        
        int dealerValue = dealer.valueProperty().get();
        int playerValue = player.valueProperty().get();

        String showWinner = "";
        
        //check for winner
        if(dealerValue == 21 || playerValue > 21 || (dealerValue < 21 && dealerValue > playerValue))
        {
            showWinner = "Dealer won";
        }
        else if(playerValue == 21 || dealerValue > 21 || (playerValue < 21 && playerValue > dealerValue))
        {
            showWinner = "Player won ";
        }
        else if(playerValue == dealerValue)
        {
            showWinner = "Draw";
        }
        message.setText(showWinner);
    }
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        //makes game field stay same size
        primaryStage.setResizable(false);
        primaryStage.setTitle("BlackJack Final  Zachary Sexton");
        primaryStage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
