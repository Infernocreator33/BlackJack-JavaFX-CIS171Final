
package blackjack;

import static java.lang.Character.toLowerCase;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Card extends Parent
{
    String imageFiles = "";
    
    enum Suit 
    {
        DIAMONDS,HEARTS,SPADES,CLUBS
    };
    enum Rank
    {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10), ACE(11);
        
        final int value;
        private Rank(int value)
        {
            this.value = value;
        }
    };
    public final Suit suit;
   public final Rank rank;
   public final int value;
   
   
   public Card(Suit suit, Rank rank)
   {
       this.suit = suit;
       this.rank = rank;
       this.value = rank.value;
       
        Rectangle rectangle = new Rectangle(100,120);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.setFill(Color.WHITE);
        
    //Couldn't find an effiecient way to get the images to work with how I did it and didn't really have the time to fix it.
    
    
  //      String temp = Character.toString(suit.toString().charAt(0)).toLowerCase();
  //    imageFiles = "images\\" + temp + value + ".png";
  //    System.out.println(imageFiles);
  //    Image img = new Image(imageFiles);
  //    rectangle.setFill(new ImagePattern(img));
        Text text = new Text(toString());
        text.setWrappingWidth(95);
        getChildren().add(new StackPane(rectangle, text));
        
   }

   @Override
   public String toString()
   {
       return rank.toString() + " of " + suit.toString();
   }
}
