package Helpers;

//import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
//import java.awt.font.*;
//import java.awt.image.*;
//import java.io.File;
//import java.io.IOException;

public class GraphicsUtil
{
  protected Graphics g;
  //protected ImageObserver observer;
  public GraphicsUtil(Graphics g)
  {
    this.g = g;
    //this.observer = observer;
  }

  public enum Align
  {
    North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest, Center
  }


  public void drawString(String text, RectangularShape bounds, Align align)
  {
    drawString(g, text, bounds, align, 0.0);
  }
  public void drawString(String text, RectangularShape bounds, Align align, double angle)
  {
    drawString(g, text, bounds, align, angle);
  }
  public static void drawString(Graphics g, String text, RectangularShape bounds, Align align)
  {
    drawString(g, text, bounds, align, 0.0);
  }
  public static void drawString(Graphics g, String text, RectangularShape bounds, Align align, double angle)
  {
    Graphics2D g2 = (Graphics2D)g;
    Font font = g2.getFont();
    if(angle != 0)
      g2.setFont(font.deriveFont(AffineTransform.getRotateInstance(Math.toRadians(angle))));

    Rectangle2D sSize = g2.getFontMetrics().getStringBounds(text, g2);
    Point2D pos = getPoint(bounds, align);
    double x = pos.getX();
    double y = pos.getY() + sSize.getHeight();

    switch(align)
    {
      case North:
      case South:
      case Center:
        x -= (sSize.getWidth() / 2);
        break;
      case NorthEast:
      case East:
      case SouthEast:
        x -= (sSize.getWidth());
        break;
      case SouthWest:
      case West:
      case NorthWest:
        break;
    }

    g2.drawString(text, (float)x, (float)y);
    g2.setFont(font);
  }
  public static Point2D getPoint(RectangularShape bounds, Align align)
  {
    double x = 0.0;
    double y = 0.0;

    switch(align)
    {
      case North:
        x = bounds.getCenterX();
        y = bounds.getMinY();
        break;
      case NorthEast:
        x = bounds.getMaxX();
        y = bounds.getMinY();
        break;
      case East:
        x = bounds.getMaxX();
        y = bounds.getCenterY();
        break;
      case SouthEast:
        x = bounds.getMaxX();
        y = bounds.getMaxY();
        break;
      case South:
        x = bounds.getCenterX();
        y = bounds.getMaxY();
        break;
      case SouthWest:
        x = bounds.getMinX();
        y = bounds.getMaxY();
        break;
      case West:
        x = bounds.getMinX();
        y = bounds.getCenterY();
        break;
      case NorthWest:
        x = bounds.getMinX();
        y = bounds.getMinY();
        break;
      case Center:
        x = bounds.getCenterX();
        y = bounds.getCenterY();
        break;
    }

    return new Point2D.Double(x,y);
  }

}
