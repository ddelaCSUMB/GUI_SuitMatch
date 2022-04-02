package images;
import javax.swing.*;
import java.awt.*;

public class phase1jav
{
   // static for the 57 icons and their corresponding labels
   // normally we would not have a separate label for each card, but
   // if we want to display all at once using labels, we need to.

   static final int NUM_CARD_IMAGES = 57; // 52 + 4 jokers + 1 back-of-card image
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];

   static void loadCardIcons()
   {
      // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
      // in a SHORT loop.  For each file name, read it in and use it to
      // instantiate each of the 57 Icons in the icon[] array.

      int index = 0;

      for(int s = 0; s < 4; s++)
      {
         for(int r = 0; r < 14; r++)
         {
            icon[index++] = new ImageIcon("images/" + turnIntIntoCardValue(r)
                  + turnIntIntoCardSuit(s) + ".gif");
         }
      }
   }

   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k)
   {
      // an idea for a helper method (do it differently if you wish)
      String cardRank = null;
      String[] rankValues = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J",
            "Q", "K", "X"};
      if(k >=0 && k < 14)
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
      String[] suitValues = {"C", "D", "H", "S"};
      if(j >= 0 && j < 4)
      {
         cardSuit = suitValues[j];
      }
      else
      {
         return suitValues[0];
      }
      return cardSuit;
   }

   // a simple main to throw all the JLabels out there for the world to see
   public static void main(String[] args)
   {
      int k;

      // prepare the image icon array
      loadCardIcons();

      // establish main frame in which program will run
      JFrame frmMyWindow = new JFrame("Card Room");
      frmMyWindow.setSize(1150, 650);
      frmMyWindow.setLocationRelativeTo(null);
      frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // set up layout which will control placement of buttons, etc.
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      frmMyWindow.setLayout(layout);

      // prepare the image label array
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         labels[k] = new JLabel(icon[k]);

      // place your 3 controls into frame
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         frmMyWindow.add(labels[k]);

      // show everything to the user
      frmMyWindow.setVisible(true);
   }
}
