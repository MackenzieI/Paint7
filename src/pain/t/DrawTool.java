package pain.t;

import java.util.Arrays;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javax.swing.undo.UndoManager;

public class DrawTool {    
    
    UndoManager undoM = new UndoManager();
    
    /**
    * Draw a straight line onto the canvas. Live draw not yet supported.
    * @param canvas takes a canvas
    * @param gc takes a graphics context
    */
     static public void straightLine(Canvas canvas, GraphicsContext gc) {  
         
        canvas.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
        double x1, y1, x2, y2;
        //GraphicsContext gc;
        @Override
        public void handle(MouseEvent event) {
            switch (event.getEventType().getName()) {
                case "MOUSE_PRESSED":
                    x1 = event.getX();
                    y1 = event.getY();
                    gc.beginPath();
                case "MOUSE_RELEASED":
                    x2 = event.getX();
                    y2 = event.getY();
                    gc.strokeLine(x1, y1, x2, y2); 
                }
            }
        });
     }
        
    /**
    * Draw a freehand/pencil tool line onto the canvas. 
    * @param canvas takes a canvas
    * @param gc takes a graphics context
    */
    static public void pencil(Canvas canvas, GraphicsContext gc) {            
        canvas.addEventHandler(MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch (event.getEventType().getName()) {
                    case "MOUSE_PRESSED":
                        gc.beginPath();
                        gc.moveTo(event.getX(), event.getY());
                        gc.stroke();
                    case "MOUSE_DRAGGED":
                        gc.lineTo(event.getX(), event.getY());
                    case "MOUSE_RELEASED":
                        gc.stroke();
                }         
                //canvas.setOnMousePressed(line);
            }
        });
    }                     
                
    /**
    * Draw a rectangle onto the canvas. 
     * @param canvas takes a canvas to draw on
     * @param gc takes a graphics context to draw
    */
    static public void rectangle(Canvas canvas, GraphicsContext gc) {
        //can only stroke to the right
        canvas.addEventHandler(MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
            double x1, y1, x2, y2, w, h;
            customDBox dbox = new customDBox(gc);
            
            @Override
            public void handle(MouseEvent event) {
            //save image to delete 
               // SnapshotParameters params = new SnapshotParameters();
                //WritableImage wi = canvas.snapshot(params, null);
                //gc.drawImage(wi, 0, 0);
                switch (event.getEventType().getName()) {
                    case "MOUSE_PRESSED":
                        x1 = event.getX();
                        y1 = event.getY();
                    case "MOUSE_DRAGGED":                       
                        x2 = event.getX();
                        y2 = event.getY();
                        w = x2 - x1;
                        h = y2 = y1;
                    case "MOUSE_RELEASED":
                        gc.strokeRect(x1, y1, w, h);
                        gc.fillRect(x1, y1, w, h);
                }
            }
        });
    }
            
    /**
    * Draw a square onto the canvas. 
     * @param canvas takes a canvas to draw on
     * @param gc takes a graphics context to draw
    */
    static public void square(Canvas canvas, GraphicsContext gc) {
        //can only stroke to the right
        canvas.addEventHandler(MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
            double x1, y1, x2, y2, w, h;
            customDBox dbox = new customDBox(gc);

            @Override
            public void handle(MouseEvent event) {
                switch (event.getEventType().getName()) {
                    case "MOUSE_PRESSED":
                        x1 = event.getX();
                        y1 = event.getY();
                    case "MOUSE_DRAGGED":
                        x2 = event.getX();
                        y2 = event.getY();
                        w = x2 - x1;
                        //h = y2 = y1;
                    case "MOUSE_RELEASED":
                        gc.strokeRect(x1, y1, w, w);
                        gc.fillRect(x1, y1, w, w);
                }
            }
        });
    }
                
    /**
    * Draw an ellipse onto the canvas. 
     * @param canvas takes a canvas to draw on
     * @param gc takes a graphics context to draw
    */
    static public void ellipse(Canvas canvas, GraphicsContext gc) {
        //can only stroke to the right
        //fills left like a square
        //canvas.removeEventHandler(MouseEvent.ANY, this); //need to store events as variables
        canvas.addEventHandler(MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
            double x1, y1, x2, y2, w, h;
            customDBox dbox = new customDBox(gc);

            @Override
            public void handle(MouseEvent event) {
                switch (event.getEventType().getName()) {
                    case "MOUSE_PRESSED":
                        x1 = event.getX();
                        y1 = event.getY();
                    case "MOUSE_DRAGGED":
                        x2 = event.getX();
                        y2 = event.getY();
                        //call an undo when it draws over it
                    case "MOUSE_RELEASED":
                        w = x2 - x1;
                        h = y2 = y1;
                        gc.strokeOval(x1, y1, w, h);
                        gc.fillOval(x1, y1, w, h);
                }                                 
                        //if doesn't work canvas.setOnAction
            }
        });
    }
            
    /**
    * Draw an circle onto the canvas. 
     * @param canvas takes a canvas to draw on
     * @param gc takes a graphics context to draw
    */
    static public void circle(Canvas canvas, GraphicsContext gc) {
        //try using strokearc
        //can only stroke to the right
        //fills left like a square
        canvas.addEventHandler(MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
            double x1, y1, x2, y2, w, h;
            customDBox dbox = new customDBox(gc);

            @Override
            public void handle(MouseEvent event) {
                switch (event.getEventType().getName()) {
                    case "MOUSE_PRESSED":
                        x1 = event.getX();
                        y1 = event.getY();
                    case "MOUSE_DRAGGED":
                        x2 = event.getX();
                         y2 = event.getY();
                    case "MOUSE_RELEASED":
                        w = x2 - x1;
                        //h = y2 = y1;
                        gc.strokeOval(x1, y1, w, w);
                        gc.fillOval(x1, y1, w, w);
                }
            }
        });
    }        
        
    /**
    * Draw a rounded rectangle onto the canvas. 
     * @param canvas takes a canvas to draw on
     * @param gc takes a graphics context to draw
    */
    static public void roundRectangle(Canvas canvas, GraphicsContext gc) {
            //can only stroke to the right
        canvas.addEventHandler(MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
            double x1, y1, x2, y2, w, h;
            customDBox dbox = new customDBox(gc);

            @Override
            public void handle(MouseEvent event) {
                switch (event.getEventType().getName()) {
                    case "MOUSE_PRESSED":
                        x1 = event.getX();
                        y1 = event.getY();
                    case "MOUSE_DRAGGED":
                        x2 = event.getX();
                        y2 = event.getY();
                        w = x2 - x1;
                        h = y2 = y1;
                    case "MOUSE_RELEASED":
                        gc.strokeRoundRect(x1, y1, w, h, 10, 10);
                        gc.fillRoundRect(x1, y1, w, h, 10, 10);
                }
            }
        });
    }        
                
    /**
    * Erase lines, shapes, and images on the canvas. 
     * @param canvas takes a canvas to draw on
     * @param gc takes a graphics context to draw
    */
    static void erase(Canvas canvas, GraphicsContext gc) { 
        canvas.addEventHandler(MouseEvent.ANY,
                new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(15);
                switch (event.getEventType().getName()) {
                    case "MOUSE_PRESSED":
                        gc.beginPath();
                        gc.moveTo(event.getX(), event.getY());
                        gc.stroke();
                    case "MOUSE_DRAGGED":
                        gc.lineTo(event.getX(), event.getY());
                        gc.stroke();
                    case "MOUSE_RELEASED":
                }
            }
        });
    }

    /**
    * Draw a polygon onto the canvas. 
     * @param canvas takes a canvas to draw on
     * @param gc takes a graphics context to draw
    */
    static public void polygon(Canvas canvas, GraphicsContext gc) {
        //can only stroke to the right
        canvas.addEventHandler(MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
            double x1, y1, x2, y2, x3, y3, x4, y4, x5, y5,
                    x6, y6, x7, y7, x8, y8, x9, y9, x10, y10;
            
            //in dialog box should ask for number of sides
            //get n from dbox
            PolygonBox dbox = new PolygonBox(gc, 0);
            int n = dbox.getVal(); //or issue here? // val is not changed by the function
            //int n = 10;
            double[] xarr = new double[n];
            double[] yarr = new double[n];

            @Override
            public void handle(MouseEvent event) {
                switch (event.getEventType().getName()) {
                    case "MOUSE_PRESSED":
                        x1 = event.getX();
                        y1 = event.getY();
                        gc.beginPath();
                    case "MOUSE_RELEASED":
                        x2 = event.getX();
                        y2 = event.getY();
                        
                        //calculate distance and angle
                        double distance = Math.hypot(x1-x2, y1-y2);
                        double angle = 360 / n; //maybe this is incorrect? 
                        
                        //four sides, but still triangle
                        
                        x3 = distance * Math.cos(angle) + x2;
                        y3 = distance * Math.cos(angle) + y2;
                        
                        double distance2 = Math.hypot(x2-x3, y2-y3);
                        x4 = distance2 * Math.cos(angle) + x3;
                        y4 = distance2 * Math.cos(angle) + y4;
                        
                        double distance3 = Math.hypot(x3-x4, y3-y4);
                        x5 = distance3 * Math.cos(angle) + x4;
                        y5 = distance3 * Math.cos(angle) + y4;
                        
                        double distance4 = Math.hypot(x4-x5, y4-y5);
                        x6 = distance4 * Math.cos(angle);
                        y6 = distance4 * Math.cos(angle);
                        
                        double distance5 = Math.hypot(x5-x6, y5-y6);
                        x7 = distance5 * Math.cos(angle);
                        y7 = distance5 * Math.cos(angle);
                        
                        double distance6 = Math.hypot(x6-x7, y6-y7);
                        x8 = distance6 * Math.cos(angle);
                        y8 = distance6 * Math.cos(angle);
                        
                        double distance7 = Math.hypot(x7-x8, y7-y8);
                        x9 = distance7 * Math.cos(angle);
                        y9 = distance7 * Math.cos(angle);
                        
                        double distance8 = Math.hypot(x8-x9, y8-y9);
                        x10 = distance8 * Math.cos(angle);
                        y10 = distance8 * Math.cos(angle);
                                
                        xarr[0] = x1;
                        xarr[1] = x2;
                        xarr[2] = x3;
                        switch (n) {
                            case 4:
                                xarr[3] = x4;
                                break;
                            case 5:
                                xarr[4] = x5;
                                break;
                            case 6:
                                xarr[5] = x6;
                                break;
                            case 7:
                                xarr[6] = x7;
                                break;
                            case 8:
                                xarr[7] = x8;
                                break;
                            case 9:
                                xarr[8] = x9;
                                break;
                            case 10:
                                xarr[9] = x10;
                            default:
                                break;
                        }
                        
                        yarr[0] = y1;
                        yarr[1] = y2;
                        yarr[2] = y3;
                        switch (n) {
                            case 4:
                                yarr[3] = y4;
                                break;
                            case 5:
                                yarr[4] = y5;
                                break;
                            case 6:
                                yarr[5] = y6;
                                break;
                            case 7:
                                yarr[6] = y7;
                                break;
                            case 8:
                                yarr[7] = y8;
                                break;
                            case 9:
                                yarr[8] = y9;
                                break;
                            case 10:
                                yarr[9] = y10;
                            default:
                                break;
                        }
                        
                        gc.fillPolygon(Arrays.copyOfRange(xarr, 0, n), Arrays.copyOfRange(yarr, 0, n), n);
                        gc.strokePolygon(Arrays.copyOfRange(xarr, 0, n), Arrays.copyOfRange(yarr, 0, n), n);

                }
            }
        });
    }
        
    /**
    * pick color and set graphic context's stroke color
    * @param canvas takes a canvas
    * @param gc takes a graphics context
    */
    static public void eyeDropper(Canvas canvas, GraphicsContext gc) {            
        canvas.setOnMousePressed((MouseEvent e) -> {
            double x, y;
            Color color;
            if (e.getButton() == MouseButton.PRIMARY) {
                x = e.getX();
                y = e.getY();
                color = readColor(canvas, x, y);
                    
                gc.setStroke(color);
            }
        });
    }
        
    /**
    * read color at the coordinates method used in eye dropper
    * @param canvas takes a canvas to read color from
    * @param x coordinate on the x-axis
    * @param y coordinate on the y-axis 
    * @return the color of the pixel at x, y coordinates
    */
    static private Color readColor(Canvas canvas, double x, double y) {
        //user clicks on canvas and return color clicked 
        WritableImage image = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
        SnapshotParameters sp = new SnapshotParameters();
        WritableImage snapshot = canvas.snapshot(sp, image);

        PixelReader pr = snapshot.getPixelReader();        
        return pr.getColor((int)x, (int)y);                
    };       
}