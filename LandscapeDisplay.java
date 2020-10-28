import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LandscapeDisplay extends JPanel implements ActionListener, MouseListener, ChangeListener {

    private JFrame frame; //JFrame declaration
    private JButton bGridToggle, bScreenClear, bPlaceToggle, bAliveSet, bDeadSet, bGridSet, bRegen; //button declarations
    private JSlider redSlider, greenSlider, blueSlider, speedSlider; //slider declarations
    private boolean place = false; //place boolean, controls whether cells can be placed manually
    protected Landscape scape; //the landscape holds the cells
    private int gridScale; // width (and height) of each square in the grid

    public LandscapeDisplay(Landscape scape, int scale) {

        //set up scape and grid scale
        this.scape = scape;
        this.gridScale = scale;

        //creates new frame with header "Game of Life"
        frame = new JFrame("Game of Life");
        //sets x button operation to close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //adds this JPanel to frame
        frame.add(this);
        //packs frame with panel
        frame.pack();
        //sets frame to be visible
        frame.setVisible(true);
        //sets up mouse listener
        this.addMouseListener(this);

        //color background
        this.setBackground(Color.lightGray);

        //button declarations
        bGridToggle = new JButton("Grid: Off");
        bGridToggle.addActionListener(this);
        this.add(bGridToggle);

        bPlaceToggle = new JButton("Place: On");
        bPlaceToggle.addActionListener(this);
        this.add(bPlaceToggle);

        bScreenClear = new JButton("Clear");
        bScreenClear.addActionListener(this);
        this.add(bScreenClear);

        bRegen = new JButton("Regen Cells");
        bRegen.addActionListener(this);
        this.add(bRegen);

        bAliveSet = new JButton("Set Alive Color");
        bAliveSet.addActionListener(this);
        this.add(bAliveSet);

        bDeadSet = new JButton("Set Dead Color");
        bDeadSet.addActionListener(this);
        this.add(bDeadSet);

        bGridSet = new JButton("Set Grid Color");
        bGridSet.addActionListener(this);
        this.add(bGridSet);

        //slider declarations
        redSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        redSlider.addChangeListener(this);
        this.add(redSlider);

        greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        greenSlider.addChangeListener(this);
        this.add(greenSlider);

        blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        blueSlider.addChangeListener(this);
        this.add(blueSlider);

        speedSlider = new JSlider(JSlider.HORIZONTAL, 30, 500, 200);
        speedSlider.addChangeListener(this);
        this.add(speedSlider);

    }

    public Dimension getPreferredSize() {
        return new Dimension(this.scape.getCols() * this.gridScale + 130, this.scape.getRows() * this.gridScale);
    }

    public void paintComponent(Graphics g) {

        //call parent
        super.paintComponent(g);

        gridScale = Math.min((getWidth()-130)/scape.getCols(), getHeight()/scape.getRows()); //determine scale of a cell based on dimensions of JFrame

        scape.draw(g, gridScale); //draw the scape (grid of cells)

        //redefine boundaries of buttons based on dimensions of JFrame
        bGridToggle.setBounds(gridScale*scape.getCols()+5,5,120,20);
        bScreenClear.setBounds(gridScale*scape.getCols()+5,30,120,20);
        bPlaceToggle.setBounds(gridScale*scape.getCols()+5,55,120,20);
        bRegen.setBounds(gridScale*scape.getCols()+5,80,120,20);
        bAliveSet.setBounds(gridScale*scape.getCols()+5,205,120,20);
        bDeadSet.setBounds(gridScale*scape.getCols()+5,230,120,20);
        bGridSet.setBounds(gridScale*scape.getCols()+5,255,120,20);

        //redefine boundaries and draw color for red slider
        redSlider.setBounds(gridScale*scape.getCols()+5,105,95,20);
        g.setColor(new Color( 255, 0, 0 ));
        g.fillRect(gridScale*scape.getCols()+105,105,20,20);

        //redefine boundaries and draw color for green slider
        greenSlider.setBounds(gridScale*scape.getCols()+5,130,95,20);
        g.setColor(new Color( 0, 255, 0 ));
        g.fillRect(gridScale*scape.getCols()+105,130,20,20);

        //redefine boundaries and draw color for blue slider
        blueSlider.setBounds(gridScale*scape.getCols()+5,155,95,20);
        g.setColor(new Color( 0, 0, 255 ));
        g.fillRect(gridScale*scape.getCols()+105,155,20,20);

        //draw color with RGB of three sliders
        g.setColor(new Color( redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue() ));
        g.fillRect(gridScale*scape.getCols()+5,180,120,20);

        //redefine boundaries of speed slider
        speedSlider.setBounds(gridScale*scape.getCols()+5,280,120,20);


    } //end paint component

    public int getSpeed() {

        return speedSlider.getValue();

    }

    //handles button presses
    public void actionPerformed(ActionEvent e) {

        switch(e.getActionCommand()) {
            case "Grid: On":
                bGridToggle.setText("Grid: Off");
                scape.toggleGrid();
                break;
            case "Grid: Off":
                bGridToggle.setText("Grid: On");
                scape.toggleGrid();
                break;
            case "Clear":
                scape.reset();
                break;
            case "Place: On":
                place = true;
                bPlaceToggle.setText("Place: Off");
                scape.updateRunning(place);
                break;
            case "Place: Off":
                place = false;
                bPlaceToggle.setText("Place: On");
                scape.updateRunning(place);
                break;
            case "Regen Cells":
                for (int i = 0; i < scape.getRows(); i++) {
                    for (int j = 0; j < scape.getCols(); j++ ) {
                        scape.getCell( i, j ).setAlive( Math.random() <= .3 );
                    }
                }
                break;
            case "Set Alive Color":
                scape.setAliveColor(new Color( redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue() ));
                break;
            case "Set Dead Color":
                scape.setDeadColor(new Color( redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue() ));
                break;
            case "Set Grid Color":
                scape.setGridColor(new Color( redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue() ));
                break;

        }

    }

    public void mouseClicked(MouseEvent e) {
        if(!place) {
            Cell cell = scape.getCell(e.getY() / gridScale, e.getX() / gridScale);
            cell.setAlive(!cell.getAlive());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void stateChanged(ChangeEvent e) {}

}
