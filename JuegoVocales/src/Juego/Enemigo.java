
package Juego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author wmarin, almartinez, wflorez
 */
public class Enemigo extends ObjetoJuegoMovimiento {

    ImageIcon alien1 = new ImageIcon("images/A.png");
    ImageIcon alien2 = new ImageIcon("images/e.png");
    ImageIcon alien3 = new ImageIcon("images/i.png");
    ImageIcon alienBoss = new ImageIcon("images/i2.png");
    ImageIcon alienBoss2 = new ImageIcon("images/ovocal.png");
    ImageIcon alienBoss3 = new ImageIcon("images/uvocal.png");
    
    private int enemytype, width, height;

    
    // Constructor para cualquier enemigo
    public Enemigo(int xPosition, int yPosition, int xVelocity, int yVelocity, int enemyType, Color color, int width, int height) {
        super(xPosition, yPosition, xVelocity, yVelocity, color);
        this.enemytype = enemyType;
        this.width = width;
        this.height = height;
    }
    
    @Override
    // Dibuja alien
    public void draw(Graphics g) {
        //Variante 1
        if (this.enemytype % 3 == 0) {
            alien1.paintIcon(null, g, this.getXPosition(), this.getYPosition());
        // Variante 2
        } else if (this.enemytype % 3 == 1 && this.enemytype != 100) {
            alien2.paintIcon(null, g, this.getXPosition(), this.getYPosition());
        // Variante 3
        } else if (this.enemytype % 3 == 2) {
            alien3.paintIcon(null, g, this.getXPosition(), this.getYPosition());
        // Jefe Enemigo
        } if (this.enemytype == 100)
        {
            if(PanelJuego.getBossHealth()>20){
                alienBoss.paintIcon(null, g, this.getXPosition(), this.getYPosition());
            }
            else if(PanelJuego.getBossHealth()>10){
                alienBoss2.paintIcon(null, g, this.getXPosition(), this.getYPosition());
            }
            else if(PanelJuego.getBossHealth()>0){
                alienBoss3.paintIcon(null, g, this.getXPosition(), this.getYPosition());
            }
        }
    }

    // Obtiene el hitbox para enemigos normales
    @Override
    public Rectangle getBounds() {
        Rectangle enemyHitBox = new Rectangle(this.getXPosition(), this.getYPosition(), width, height);
        return enemyHitBox;
    }

    // Usado para mover a todos los enemigos
    @Override
    public void move() {
        xPos += xVel;
    }

}
