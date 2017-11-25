
package Juego;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author wmarin, almartinez, wflorez
 */
public class MarcoJuego extends JFrame
{
    private PanelJuego game;
    
    public MarcoJuego()
    {
        // Add text to title bar 
        super("Space Intruders");
        
        // Make sure the program exits when the close button is clicked
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        // Create an instance of the Game class and turn on double buffering
        //  to ensure smooth animation
        game = new PanelJuego();
        game.setDoubleBuffered(true);
        
        // Add the Breakout instance to this frame's content pane to display it
        this.getContentPane().add(game); 
        this.pack();
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        
        // Start the game
        game.start();  
    }
    
    public static void main(String[] args) 
    {
         java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MarcoJuego().setVisible(true);
            }
        });
        
    }
}
