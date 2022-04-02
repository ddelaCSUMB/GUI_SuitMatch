
import javax.swing.*;

import java.awt.*;

public class CardTable extends JFrame {
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games

   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

   //constructor filters input, adds any panels to the JFrame, and
   //establishs layouts
   CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      this.numCardsPerHand = numCardsPerHand;

      setTitle(title); // look up jframe and how to instantiate it, and set layout,
      //if statement later
      //make sure greater than 0 and less than equal to 56
      // make sure max players of 2 and only 2, set things for the frame
      // add panels to frame from this constructor, set everything for the panel
      //add panels to the frame

   }

}

//CardTable frmMyWindow = new CardTable("Card Room");
//icons into label -> labels onto a panel -> panels are on the frame
//access cardtable (to update Jpanels, possibly once) class from the main
//use myCardTable.pnlComputerHand
