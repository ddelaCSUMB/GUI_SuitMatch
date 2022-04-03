package images;

import javax.swing.*;
import java.awt.*;

public class phase2jav
{
   // static for the 57 icons and their corresponding labels
   // normally we would not have a separate label for each card, but
   // if we want to display all at once using labels, we need to.

   static final int NUM_CARD_IMAGES = 57;
      // 52 + 4 jokers + 1 back-of-card image
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];

   static void loadCardIcons()
   {
      // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
      // in a SHORT loop.  For each file name, read it in and use it to
      // instantiate each of the 57 Icons in the icon[] array.

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

   public class CardTable extends JFrame
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

   public class GUICard
   {  // 14 = A thru K + joker
      private static Icon[][] iconCards = new ImageIcon[14][4];
      private static Icon iconBack;
      static boolean iconsLoaded = false;

      static void loadCardIcon() /*the code for this was fundamentally done in
      Phase 1.  The difference here is that we are storing the Icons in a 2-D
      array.  Don't require the client to call this method.  Think about where
      you would need to call it and how can you avoid having the method reload
      the icons after it has already loaded them once.  Hint:  Call this method
      any time you might need an Icon, but make sure that it loads the entire
      array the first time it is called, and does nothing any later time.*/
      {

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

   // a simple main to throw all the JLabels out there for the world to see
   public static void main(String[] args)
   {
      static int NUM_CARDS_PER_HAND = 7;
      static int  NUM_PLAYERS = 2;
      static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
      static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
      static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS];
      static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];

      public static void main(String[] args)
      {
         int k;
         Icon tempIcon;

         // establish main frame in which program will run
         CardTable myCardTable
            = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
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
