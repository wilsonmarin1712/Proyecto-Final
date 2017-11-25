
package Juego;

import java.awt.Color;

/**
 *
 * @author wmarin, almartinez, wflorez
 */
public abstract class ObjetoJuegoMovimiento extends ObjetoJuego implements Movible{
    
    int xVel;
    int yVel;
    
    // Constructor for any non controllable object
    public ObjetoJuegoMovimiento(int xPosition, int yPosition, int xVelocity, int yVelocity, Color color)
    {
        super(xPosition, yPosition, color);
        this.xVel = xVelocity;
        this.yVel = yVelocity;
    
    }
    
    // Accessors and mutators for every part of the MovingGameObject constructor
    public int getXVelocity()
    {
        return xVel;
    }
    public int getYVelocity()
    {
        return yVel;
    }
    public void setXVelocity(int xVelocity)
    {
        this.xVel = xVelocity;
    }
    public void setYVelocity(int yVelocity)
    {
        this.yVel = yVelocity;
    }
    @Override
    
    // Used to move non controllable objects
    public void move()
    {
        this.xPos += xVel;
        this.yPos += yVel;
    }
    
}
