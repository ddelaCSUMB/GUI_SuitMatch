
import javax.swing.*;
import javax.swing.border.TitledBorder;
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

   static Card generateRandomCard()
   {
      Deck deck = new phase2jav().new Deck();
      Random randomGen = new Random();
      return deck.inspectCard(randomGen.nextInt(deck.getNumCards()));
   }


   //BEGIN CardTable Class
   static class CardTable extends JFrame
   {
      static int MAX_CARDS_PER_HAND = 56;
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
         this.numCardsPerHand = numCardsPerHand;
         setTitle(title); // look up jframe and how to instantiate it, and set layout,
         //if statement later
         //make sure greater than 0 and less than equal to 56
         // make sure max players of 2 and only 2, set things for the frame
         // add panels to frame from this constructor, set everything for the panel
         //add panels to the frame
         setLayout(new BorderLayout());
         //Sets values
         this.numCardsPerHand = numCardsPerHand;
         this.numPlayers = numPlayers;

         pnlComputerHand = new JPanel(new GridLayout(1, numCardsPerHand));
         pnlHumanHand = new JPanel(new GridLayout(1, numCardsPerHand));
         pnlPlayArea = new JPanel(new GridLayout(2, numPlayers));

         add(pnlComputerHand, BorderLayout.NORTH);
         add(pnlHumanHand, BorderLayout.SOUTH);
         add(pnlPlayArea, BorderLayout.CENTER);

         pnlComputerHand.setBorder(new TitledBorder("Computer Hand"));
         pnlHumanHand.setBorder(new TitledBorder("Playing Area"));
         pnlPlayArea.setBorder(new TitledBorder("Your Hand"));




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
      private static Icon[][] iconCards = new ImageIcon[14][4];
      private static Icon iconBack;

      static boolean iconsLoaded = false;

      public static void loadCardIcons()
      {
         if (iconsLoaded)
            return;
         for (int cardValue = 0; cardValue < iconCards.length; cardValue++)
         {
            for (int cardSuit = 0; cardSuit < iconCards[cardValue].length; cardSuit++)
            {
               String filename = numCard(cardValue) + numSuit(cardSuit) + ".gif";
               ImageIcon cardImage = new ImageIcon("images/" + filename);
               iconCards[cardValue][cardSuit] = cardImage;
            }
         }
         //create final back card
         iconBack = new ImageIcon("images/BK.gif");
         iconsLoaded = true;
      }

      //  Changes integer to the card value
      static String numCard(int cardNum)
      {
         String[] cardValues = {"A", "2", "3", "4", "5", "6",
            "7", "8", "9", "T", "J", "Q", "K", "X"};
         return cardValues[cardNum];
      }

      //Checks
      static String numSuit(int suitNum)
      {
         if (suitNum < 0 || suitNum > 3)
            return "invalid";
         return Card.Suit.values()[suitNum]
            .toString().toUpperCase().substring(0, 1);
      }

      //Checks
      private static int valueToInt(Card card)
      {
         return Card.valueOfCard(card);
      }

      //Converts suit to number
      private static int suitToNum(Card card)
      {
         Card.Suit cardSuit = card.getSuit();

         switch (cardSuit)
         {
         case SPADES:
            return 0;
         case HEARTS:
            return 1;
         case DIAMONDS:
            return 2;
         case CLUBS:
            return 3;
         default:
            return -1;
         }
      }

      public static Icon getIcon(Card card)
      {
         return iconCards[valueToInt(card)][suitToNum(card)];
      }

      public static Icon getBackcardIcon()
      {
         return iconBack;
      }
   }

   class Card
   {
      //String str = "A","2","3","4","5","6","7","8","9","10",
      public enum Suit
      {
         //clubs, diamonds, hearts, spades;
         //private char value;
         //private Suit suit;
         SPADES, HEARTS, DIAMONDS, CLUBS;
      }

      public static char[] cardPosition =
         { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K',
            'X' };

      //private static final int MIN_RANK = 1;
      //private static final int MAX_RANK = 13;

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

      public static void arraySort(Card[] cardArray, int arraySize)
      {

         Card temp;

         // Bubble sort algorithm
         for (int card = 0; card < arraySize; card++)
         {
            for (int nextCard = 1; nextCard < (arraySize - card); nextCard++)
            {
               int previousCard = valueOfCard(cardArray[nextCard - 1]);
               int currentCard = valueOfCard(cardArray[nextCard]);

               if (previousCard > currentCard)
               {
                  temp = cardArray[nextCard - 1];
                  cardArray[nextCard - 1] = cardArray[nextCard];
                  cardArray[nextCard] = temp;
               }

            }
         }
      }

      static int valueOfCard(Card card)
      {
         for (int value = 0; value < cardPosition.length; value++)
         {
            if (card.getValue() == cardPosition[value])
            {
               return value;
            }
         }
         return -1;
      }
   }
   //END of Card Class

   //Start of Hand class
   class Hand extends Card
   {

      /*Adjust for the joker by adding 4 empty spots to the Card[] array per
      pack.

   Adjust the playCard() method to account for possible empty spaces in the
   array.

   Do NOT add jokers to the masterPack[] because they will be added by the
   CardGameOutline class later on.*/

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

         if (myCards[index] == null)
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
            return new Card('M', Suit.SPADES);
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
   public class Deck /*Adjust for the joker by adding 4 spots in the Card[]
     array.
   Add methods for adding and removing cards from the deck as well as a sort
   method. (these will be using in the CardGameOutline given in Phase 3)*/

   {
      public static final int MAX_CARDS = 312;
      private static Card[] masterPack = new Card[56];

      private Card[] cards;
      private int topCard;

      public Deck(int numPacks)
      {
         cards = new Card[numPacks * 56];
         allocateMasterPack();
         init(numPacks);
      }

      public Deck()
      {
         int numPacks = 1;
         cards = new Card[numPacks * 56];
         init(numPacks);
      }

      public void init(int numPacks)
      {
         topCard = (numPacks * 56) - 1;
         for (int i = 0; i <= topCard; i++)
         {
            cards[i] = masterPack[i % 56];
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

         masterPack = new Card[56];
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
               masterPack[i + 14 * s] = new Card((char) (r + 48), suit);
               r++;
               //System.out.println(masterPack[i]);
            }
            masterPack[8 + 14 * s] = new Card('T', suit);
            masterPack[9 + 14 * s] = new Card('J', suit);
            masterPack[10 + 14 * s] = new Card('Q', suit);
            masterPack[11 + 14 * s] = new Card('K', suit);
            masterPack[12 + 14 * s] = new Card('A', suit);
            masterPack[13 + 14 * s] = new Card('X', suit);
         }
         //loop for char value & loop for Suit suit
      }

      boolean addCard(Card card) /*- make sure that there are not
         too many
         instances of the card in the deck if you add it.  Return false if
         there will be too many.  It should put the card on the top of the deck
         .*/
      {
         int DECK_SIZE = 56;
         int deckNum = topCard / DECK_SIZE;

         int cardInstances = 0;

         for (int cardPosition = 0; cardPosition < topCard; cardPosition++)
         {
            if (card.equals(cards[cardPosition]))
            {
               cardInstances++;
            }
         }

         System.out.println("Card instances is: " + cardInstances);

         if (cardInstances >= deckNum)
         {
            System.out.println("Did not add card");
            return false;
         }
         System.out.println("Added the card to the deck");

         cards[topCard] = card;

         topCard++;
         System.out.println("The topCard Value is: " + topCard);
         return true;


      }

      boolean removeCard(Card card) /*- you are looking to remove a specific
         card from the deck.  Put the current top card into its place.  Be sure
         the card you need is actually still in the deck, if not return false.*/
      {
         for (int cardsIndex = 0; cardsIndex < topCard; cardsIndex++)
         {
            if (cards[cardsIndex].equals(card))
            {
               System.out.println("Removed Card Successfully");


               cards[cardsIndex] = cards[topCard - 1];


               topCard--;
               return true;
            }
         }

         System.out.println("Did not remove card, none left");
         System.out.println(topCard);
         return false;


      }

      public void sort(Card[] cardArray, int arraySize) /* - put all of the cards in the deck back into the
         right order according to their values.  Is there another method
         somewhere that already does this that you could refer to?*/
      {
         Card temp;

         // Bubble sort algorithm
         for (int card = 0; card < arraySize; card++)
         {
            for (int nextCard = 1; nextCard < (arraySize - card); nextCard++)
            {
               int previousCard = Card.valueOfCard(cardArray[nextCard - 1]);
               int currentCard = Card.valueOfCard(cardArray[nextCard]);

               if (previousCard > currentCard)
               {
                  temp = cardArray[nextCard - 1];
                  cardArray[nextCard - 1] = cardArray[nextCard];
                  cardArray[nextCard] = temp;
               }

            }
         }


      }

      public int getNumCards() //return the number of cards remaining in the
      // deck
      {
         return cards.length;
      }

   }
   //END of Deck Class

   // a simple main to throw all the JLabels out there for the world to see
   public static void main(String[] args)
   {

      int k;
      Icon tempIcon;

      GUICard.loadCardIcons();
      // establish main frame in which program will run
      CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // show everything to the user
      myCardTable.setVisible(true);

      // CREATE LABELS ----------------------------------------------------
      //code goes here ...
      for (int card = 0; card < NUM_CARDS_PER_HAND; card++)
      {
         //give the Computer a back card Label
         computerLabels[card] = new JLabel(GUICard.getBackcardIcon());

         //give Human a random Card Label
         tempIcon = GUICard.getIcon(generateRandomCard());
         humanLabels[card] = new JLabel(tempIcon);
      }

      // ADD LABELS TO PANELS -----------------------------------------
      //code goes here ...
      for (int card = 0; card < NUM_CARDS_PER_HAND; card++)
      {
         //add indexed label to Computer panel
         myCardTable.pnlComputerHand.add(computerLabels[card]);

         //add indexed label to Human panel
         myCardTable.pnlHumanHand.add(humanLabels[card]);
      }
      // and two random cards in the play region (simulating a computer/hum ply)
      //code goes here ...

      // show everything to the user
      myCardTable.setVisible(true);

   }

}


