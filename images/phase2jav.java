package images;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class phase2jav
{
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

   //BEGIN CardTable Class
   class CardTable extends JFrame
   {
      static int MAX_CARDS_PER_HAND = 57;
      static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games

      private int numCardsPerHand;
      private int numPlayers;

      public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

      public CardTable(String title, int numCardsPerHand, int numPlayers)/*The
      constructor filters input, adds any panels to the JFrame, and establishes
      layouts according to ----> We will use three Public JPanels, one for each
      hand (player-bottom and computer-top) and a middle "playing" JPanel. The
      client (below) will generate the human's cards at random and will be
      visible in the bottom JPanel, while the computer's cards will be chosen
      (again, by the client) to be all back-of-card images in the top JPanel.
      The middle JPanel will display cards that are "played" by the computer
      and human during the conflict.  Let's assume that each player plays one
      card per round, so for a 2-person game (computer + human) there will be
      exactly two cards played in the central region per round of battle.  My
      client chose a joker for the two central cards, just so we
      would have something to see in the playing region.*/
      {
         setLayout(new GridLayout(3, 1));
         add(pnlComputerHand, BorderLayout.NORTH);
         add(pnlHumanHand, BorderLayout.SOUTH);
         add(pnlPlayArea, BorderLayout.CENTER);
         /*
         pnlComputerHand.setLayout(new BorderLayout());
         pnlComputerHand.add(BorderLayout.NORTH);
         pnlHumanHand.setLayout(new BorderLayout());
         pnlHumanHand.add(new Button(title), BorderLayout.SOUTH);
         pnlPlayArea.setLayout(new BorderLayout());
         pnlPlayArea.add(new Button(title), BorderLayout.CENTER);*/




      }

      public int getNumCardsPerHand()
      {
         return numCardsPerHand;
      }

      public int getNumPlayers()
      {
         return numPlayers;
      }
   }
   //END CardTable Class

   //START GUICard Class
   class GUICard
   {
      // 14 = A thru K + joker
      private static Icon[][] iconCards = new ImageIcon[14][4];
      private static Icon iconBack;
      static boolean iconsLoaded = false;
      static final int NUM_CARD_IMAGES = 57;
      // 52 + 4 jokers + 1 back-of-card image
      static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];

      // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
      static String turnIntIntoCardValue(int k)
      {
         // an idea for a helper method (do it differently if you wish)
         String cardRank = null;
         String[] rankValues =
            { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K",
               "X" };
         if (k >= 0 && k < 14)
         {
            cardRank = rankValues[k];
         }
         else
         {
            return rankValues[0];
         }
         return cardRank;
      }

      // turns 0 - 3 into "C", "D", "H", "S"
      static String turnIntIntoCardSuit(int j)
      {
         // an idea for another helper method (do it differently if you wish)
         String cardSuit = null;
         String[] suitValues = { "C", "D", "H", "S" };
         if (j >= 0 && j < 4)
         {
            cardSuit = suitValues[j];
         }
         else
         {
            return suitValues[0];
         }
         return cardSuit;
      }

      static void loadCardIcon() /*the code for this was fundamentally done in
      Phase 1.  The difference here is that we are storing the Icons in a 2-D
      array.  Don't require the client to call this method.  Think about where
      you would need to call it and how can you avoid having the method reload
      the icons after it has already loaded them once.  Hint:  Call this method
      any time you might need an Icon, but make sure that it loads the entire
      array the first time it is called, and does nothing any later time.*/

      //From phase 1:
      // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
      // in a SHORT loop.  For each file name, read it in and use it to
      // instantiate each of the 57 Icons in the icon[] array.
      {
         int index = 0;

         for (int s = 0; s < 4; s++)
         {
            for (int r = 0; r < 14; r++)
            {
               icon[index++] = new ImageIcon(
                  "images/" + turnIntIntoCardValue(r) + turnIntIntoCardSuit(s) +
                     ".gif");
            }
         }
      }

      public static Icon getIcon(Card card) /*This method takes a Card object
      from the client, and returns the Icon for that card.  It would be used
      when the client needs to instantiate or change a JLabel. It can return
      something like: return iconCards[valueAsInt(card)][suitAsInt(card)];*/
      {

      }

      public static Icon getBackCardIcon()
      {
         return iconBack;
      }
   }

   class Card /*Adjust for the joker. (Even though there are 4 card icons, think
   of them as one type, X ) We need a way to know which card is Lower when we
   compare them for the game later.*/
   {
      public enum Suit
      {
         SPADES, HEARTS, DIAMONDS, CLUBS;
      }


      /*- put the order of the card values in here with the smallest first,
      include 'X' for a joker*/
      public static char[] valuRanks = new char[] { '2', '3', '4', '5', '6',
         '7', '8', '9', 'T', 'J', 'Q', 'K', 'A', 'X' };
      private char value;
      private Suit suit;
      private boolean cardError;

      public Card(char value, Suit suit)
      {
         set(value, suit);
      }

      public Card()//default constructor
      {
         set('A', Suit.SPADES);
      }

      public Card(Card origCard)
      {
         set(origCard.value, origCard.suit);
      }

      public String toString()
      {
         if (cardError == true)
         {
            return "** illegal **";
         }
         else
            return value + " of " + suit;//

      }

      public boolean set(char value, Suit suit)//mutator
      {
         if (isValid(value, suit) == true)
         {
            this.value = value;
            this.suit = suit;
            cardError = false;
            return true;
         }
         cardError = true;
         return false;
      }

      public char getValue()//accessors
      {
         return this.value;
      }

      public Suit getSuit()
      {
         return this.suit;
      }

      public boolean getCardError()
      {
         return cardError;
      }

      //a method to check
      public boolean equals(Card otherCard)
      {
         if (suit == otherCard.getSuit() && value == otherCard.getValue() &&
                cardError == otherCard.getCardError())
            return true;
         return false;
      }

      private boolean isValid(char value, Suit suit)
      {
         if ((value == '2') || (value == '3') || (value == '4') ||
                (value == '5') || (value == '6') || (value == '7') ||
                (value == '8') || (value == '9') || (value == 'T') ||
                (value == 'J') || (value == 'Q') || (value == 'K') ||
                (value == 'A'))
         {
            return true;
         }
         else
         {
            return false;
         }
      }

      static void arraySort(Card[] card, int arraySize) /*will sort the
      incoming array of cards using a bubble sort routine. You can break this
      up into smaller methods if it gets over 20 lines or so.*/
      {

      }
   }

   //END of Card Class

   //Start of Hand class
   class Hand extends Card/*

   Adjust for the joker by adding 4 empty spots to the Card[] array per pack.

   Adjust the playCard() method to account for possible empty spaces in the
   array.

   Do NOT add jokers to the masterPack[] because they will be added by the
   CardGameOutline class later on.*/

   {
      //series of variables used
      public final int MAX_CARDS = 75;  //range is from 50 - 100
      private Card[] myCards;
      private int numCards;

      //initializes integer numCards and array myCards
      public Hand()
      {
         this.myCards = new Card[MAX_CARDS];
         this.numCards = 0;

      }

      //resets numCards and myCards by overwriting with initial values
      public void resetHand()
      {
         this.myCards = new Card[MAX_CARDS];
         this.numCards = 0;

      }

      //adds a card to the array myCards
      public boolean takeCard(Card card)
      {
         if (numCards >= MAX_CARDS)
         {
            return false;
         }
         else
         {
            Card copy = new Card(card);
            myCards[numCards] = copy;
            numCards++;
            return true;
         }
      }

      //this method returns last card of array or returns bad card error
      public Card playCard()
      {
         Card holdCard = new Card();
         Card emptyCard = new Card();

         int index = getNumCards() - 1;
         if (numCards == 0)
         {
            return emptyCard;
         }
         
         if(myCards[index] == null)
         {
            playCard(index);
         }

         holdCard = myCards[index];
         numCards--;
         return holdCard;
      }

      //toString method to display hand
      public String toString()
      {
         String userHand = "Hand = ( ";
         for (int i = 0; i < numCards; i++)
         {
            userHand = userHand + myCards[i];
            userHand += ", ";
         }
         userHand += " )";
         return userHand;
      }

      //getter for array
      public Card[] getMyCards()
      {
         return myCards;
      }

      //getter for numCards
      public int getNumCards()
      {
         return numCards;
      }

      //takes card as parameter to check for out of bounds

      public Card inspectCard(int k)
      {
         Card invalidCard = new Card('H', Card.Suit.CLUBS);
         if (k > 312 || k < 0)
         {
            System.out.println("**illegal**");
            return invalidCard;
         }
         if (myCards[k] == null)
            return invalidCard;
         if (myCards[k].getCardError())
            return invalidCard;
         else
            invalidCard.set('3', Card.Suit.HEARTS);
         return invalidCard;
      }

      public Card playCard(int cardIndex)
      {
         if (numCards == 0) //error
         {
            //Creates a card that does not work
            return new Card('M', Card.Suit.spades);
         }
         //Decreases numCards.
         Card card = myCards[cardIndex];

         numCards--;
         for (int i = cardIndex; i < numCards; i++)
         {
            myCards[i] = myCards[i + 1];
         }

         myCards[numCards] = null;

         return card;
      }

      public void sort() //calls the new arraySort() in the Card class.
      {
         Card.arraySort(myCards, numCards);
      }

   }
   //END of Hand Class

   //START of Deck Class
   class Deck /*Adjust for the joker by adding 4 spots in the Card[] array.
   Add methods for adding and removing cards from the deck as well as a sort
   method. (these will be using in the CardGameOutline given in Phase 3)*/

   {
      public static final int MAX_CARDS = 312;
      private static Card[] masterPack = new Card[52];

      private Card[] cards;
      private int topCard;

      public Deck(int numPacks)
      {
         cards = new Card[numPacks * 52];
         allocateMasterPack();
         init(numPacks);
      }

      public Deck()
      {
         int numPacks = 1;
         cards = new Card[numPacks * 52];
         init(numPacks);
      }

      public void init(int numPacks)
      {
         topCard = (numPacks * 52) - 1;
         for (int i = 0; i <= topCard; i++)
         {
            cards[i] = masterPack[i % 52];
            System.out.println(cards[i]);
         }
      }

      public void shuffle()
      {
         int i = 0;
         Random pullCard = new Random();
         for (i = cards.length - 1; i > 0; i--)
         {
            int j = pullCard.nextInt(i + 1);
            Card temp = cards[i];
            cards[j] = cards[i];
            cards[i] = temp;

         }

      }

      public Card dealCard()
      {

         if (topCard == 0)
         {
            return new Card('X', Card.Suit.SPADES);
         }
         else
         {
            Card newCard = new Card(cards[topCard]);
            topCard--;
            return newCard;
         }
      }

      public int getTopCard()
      {
         return topCard;
      }

      //same as the inspecCard class from hand
      //but added the object copy if k is out of bounds
      //returns true and object copy
      public Card inspectCard(int k)
      {
         if (k < 0 || k > topCard)
         {
            System.out.println("out of bounds error");

            return new Card('X', Card.Suit.SPADES);
         }
         else
         {
            return cards[k];
         }
      }

      private void allocateMasterPack()
      {
         //some variables used in method
         //boolean cutRibbon = true;
         int r = 2; //r for rank
         Card.Suit suit;

         //if cutRibbon is true method exits
         if (masterPack[0] != null)
         {

            return;
         }
         // if masterPack is empty build it

         masterPack = new Card[52];
         // not empty do nothing
         // 2 nested loops to build the deck
         //s is for suit
         //NEED TO SEND CHAR THROUGH
         //THIS ONE IS NOT DONE
         for (int s = 0; s < 4; s++)
         {
            suit = Card.Suit.values()[s];
            r = 2; //use values() to pull enum values

            for (int i = 0; i < 8; i++)
            {
               masterPack[i + 13 * s] = new Card((char) (r + 48), suit);
               r++;
               //System.out.println(masterPack[i]);
            }
            masterPack[8 + 13 * s] = new Card('T', suit);
            masterPack[9 + 13 * s] = new Card('J', suit);
            masterPack[10 + 13 * s] = new Card('Q', suit);
            masterPack[11 + 13 * s] = new Card('K', suit);
            masterPack[12 + 13 * s] = new Card('A', suit);
         }
         //loop for char value & loop for Suit suit
      }

      boolean addCard(Card card) /*- make sure that there are not
      too many
      instances of the card in the deck if you add it.  Return false if
      there will be too many.  It should put the card on the top of the deck
      .*/
   {
      int count = 0;
      boolean cardAdded = false;
      while(cardAdded = false);
      {
         for(int i = 0; i < cards.length; i++)
         {
            if(cards[i] == card)
            {
               count++;
            }
            
            if(count > 3)
            {
               return cardAdded;
            }
            else
            {
               cardAdded = true;
            }
         }
         
         card = cards[cards.length + 1];
         return cardAdded;
      }
   }

   boolean removeCard(Card card)
   /*- you are looking to remove a specific
      card from the deck.  Put the current top card into its place.  Be sure
      the card you need is actually still in the deck, if not return false.
   */
   {
      boolean cardRemoved = false;
      for(int i = 0; i < cards.length; i++)
      {
         if(cards[i] == card)
         {
            Card temp = cards[getTopCard()];
            cards[getTopCard()] = card;
            card = temp;
            
            dealCard();
            cardRemoved = true;
            return cardRemoved;          
         }        
      }
      return cardRemoved;
   }

      public void sort() /* - put all of the cards in the deck back into the
         right order according to their values.  Is there another method
         somewhere that already does this that you could refer to?*/
      {

      }

      public int getNumCards() //return the number of cards remaining in the
      // deck
      {

      }

   }
   //END of Deck Class

   // a simple main to throw all the JLabels out there for the world to see
   public static void main(String[] args)
   {

      int k;
      Icon tempIcon;

      // establish main frame in which program will run
      CardTable myCardTable =
         new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // show everything to the user
      myCardTable.setVisible(true);

      // CREATE LABELS ----------------------------------------------------
      code goes here ...

      // ADD LABELS TO PANELS -----------------------------------------
      code goes here ...

      // and two random cards in the play region (simulating a computer/hum ply)
      code goes here ...

      // show everything to the user
      myCardTable.setVisible(true);
   }

}


