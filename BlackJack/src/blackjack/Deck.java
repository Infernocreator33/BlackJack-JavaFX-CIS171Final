
package blackjack;

import blackjack.Card.Rank;
import blackjack.Card.Suit;

public class Deck 
{
    //create an array of cards from card class to fill 52 card deck
    private Card[] cards = new Card[52];
    
    public Deck()
    {
        refill();
    }
    //refill method that goes through each suit and each rank value
    public final void refill() 
    {
        int i = 0;
        for(Suit suit: Suit.values())
        {
            for(Rank rank : Rank.values())
            {
                cards[i++] = new Card(suit, rank);
            }
        }
    }
    //draw the card to the screen 
    public Card drawCard()
    {
        Card card = null;
        while(card == null)
        {
            int index = (int) (Math.random() * cards.length);
            card = cards[index];
            cards[index] = null;
        }
        return card;
    }
}