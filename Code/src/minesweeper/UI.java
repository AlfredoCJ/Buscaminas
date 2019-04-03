package minesweeper;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * Clase que se encarga de la ventana del juego, es decir, del Interfaz gráfico de Usuario.
 * 
 * @author Haris Muneer.
 */
public class UI extends JFrame
{
    /**
     * Botones que se usarán para pulsar sobre ellos.
     */
    private JButton[][] buttons;
    
    /**
     * Número de filas del tablero.
     */
    private int rows;
    /**
     * Número de columnas del tablero.
     */
    private int cols;
    
    /**
     * Texto para las minas restantes.
     */
    private JLabel minesLabel;
    /**
     * Número de minas restantes.
     */
    private int mines;
    
    /**
     * Texto para el tiempo transcurrido.
     */
    private JLabel timePassedLabel;    
    /**
     * Temporizador usado para controlar el tiempo.
     */
    private Thread timer;
    /**
     * Tiempo transcurrido (en segundos).
     */
    private int timePassed;
    /**
     * Booleando que indica si el tiempo está parado (TRUE) o no (FALSE).
     */
    private boolean stopTimer;
    
    /**
     * Título.
     */
    private final String FRAME_TITLE = "Buscaminas ~ Desarrollado por Haris Muneer - Modificado por Alfredo C.J.";
    
    /**
     * Ancho de la ventana.
     */
    private int FRAME_WIDTH = 520;
    /**
     * Alto de la ventana.
     */
    private int FRAME_HEIGHT = 550;
    /**
     * Posición horizontal de la ventana en la pantalla.
     */
    private int FRAME_LOC_X = 430;
    /**
     * Posición vertical de la ventana en la pantalla.
     */
    private int FRAME_LOC_Y = 50;

    /**
     * Icono de una mina explotada.
     */
    private Icon redMine;
    /**
     * Icono de una mina no explotada.
     */
    private Icon mine;
    /**
     * Icono de una bandera.
     */
    private Icon flag;
    /**
     * Icono de un valor textual.
     */
    private Icon tile;
    
    
    /**
     * Barra de menú.
     */
    private JMenuBar menuBar;
    /**
     * Menú del juego.
     */
    private JMenu gameMenu;
    /**
     * Opción de menú para jugar una nueva partida.
     */
    private JMenuItem newGame;
    /**
     * Opción de menú para mostrar las estadísticas.
     */
    private JMenuItem statistics;
    /**
     * Opción de menú para salir.
     */
    private JMenuItem exit;

    
    //---------------------------------------------------------------//
    /**
     * Constructor.
     * 
     * @param r Numero de filas.
     * @param c Numero de columnas.
     * @param m Numero de minas. 
     */
    public UI(int r, int c, int m)
    {                
        this.rows = r;
        this.cols = c;
        
        buttons = new JButton [rows][cols];

        // Set frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(FRAME_TITLE);
        setLocation(FRAME_LOC_X, FRAME_LOC_Y);
               
        // The layout of the frame:

        JPanel gameBoard;        
        JPanel tmPanel;        
        JPanel scorePanel;
        
        //----------------GAME BOARD---------------------//
        // Build the "gameBoard".
        gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(rows,cols,0,0));
        
        for( int y=0 ; y<rows ; y++ ) 
        {
            for( int x=0 ; x<cols ; x++ ) 
            {
                // Set button text.
                buttons[x][y] = new JButton("");

                // Set button name (x,y).
                buttons[x][y].setName(Integer.toString(x) + "," + Integer.toString(y));
                buttons[x][y].setFont(new Font("Serif", Font.BOLD, 24));
                
                buttons[x][y].setBorder(BorderFactory.createLineBorder(Color.black, 1, true));

                // Add this button to the gameboard.
                gameBoard.add(buttons[x][y]);
            }
        }
        //-----------------------------------------------//
                
                
        //-------------TIME AND MINE------------------------//
        
        JPanel timePassedPanel = new JPanel();
        timePassedPanel.setLayout(new BorderLayout(10,0));
        
        // Initialize the time passed label.
        this.timePassedLabel = new JLabel ("  0  " , SwingConstants.CENTER);
        timePassedLabel.setFont(new Font("Serif", Font.BOLD, 20));
                
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        
        timePassedLabel.setBorder(loweredetched);
        timePassedLabel.setBackground(new Color(110,110,255));
        timePassedLabel.setForeground(Color.white);
        timePassedLabel.setOpaque(true);
        
        JLabel iT = new JLabel("",SwingConstants.CENTER);
        iT.setIcon(new ImageIcon(getClass().getResource("/resources/clock.png"))); 

        timePassedPanel.add(iT, BorderLayout.WEST);
        timePassedPanel.add(timePassedLabel, BorderLayout.CENTER);
        timePassedPanel.setOpaque(false);
        
        this.timePassed = 0;
        this.stopTimer = true;

        
        JPanel minesPanel = new JPanel();
        minesPanel.setLayout(new BorderLayout(10,0));
        
        
        // Initialize mines label.
        this.minesLabel = new JLabel ("  0  " , SwingConstants.CENTER);
        minesLabel.setFont(new Font("Serif", Font.BOLD, 20));
        minesLabel.setBorder(loweredetched);
        minesLabel.setBackground(new Color(110,110,255));
        minesLabel.setForeground(Color.white);
        
        minesLabel.setOpaque(true);
        setMines(m);
        
        JLabel mT = new JLabel("", SwingConstants.CENTER);
        mT.setIcon(new ImageIcon(getClass().getResource("/resources/mine.png")));

        minesPanel.add(minesLabel, BorderLayout.WEST);
        minesPanel.add(mT, BorderLayout.CENTER);
        minesPanel.setOpaque(false);
        
        // Build the "tmPanel".
        tmPanel = new JPanel();
        tmPanel.setLayout(new BorderLayout(0,20));
        
        tmPanel.add(timePassedPanel, BorderLayout.WEST);
        tmPanel.add(minesPanel, BorderLayout.EAST);
        tmPanel.setOpaque(false);
        
        //--------------------------------------------//
                        
        
        //------------------Menu--------------------------//
        menuBar = new JMenuBar();
        
        gameMenu = new JMenu("Juego");
         
        newGame = new JMenuItem("   Nuevo juego");
        statistics = new JMenuItem("   Estadisticas");
        exit = new JMenuItem("   Salir");

        newGame.setName("Nuevo juego");
        statistics.setName("Estadisticas");
        exit.setName("Salir");

        gameMenu.add(newGame);
        gameMenu.add(statistics);
        gameMenu.add(exit);
        
        menuBar.add(gameMenu);                        
        //----------------------------------------------------//
               
        
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout(0,10));
        p.add(gameBoard, BorderLayout.CENTER);
        p.add(tmPanel, BorderLayout.SOUTH);
    
 
        p.setBorder(BorderFactory.createEmptyBorder(60, 60, 14, 60));        
        p.setOpaque(false);
      
        
        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/resources/2.jpg")));
        
        add(background);        
        
        background.setLayout(new BorderLayout(0,0));
        
        background.add(menuBar,BorderLayout.NORTH);
        background.add(p, BorderLayout.CENTER);        
        
        
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/mine.png")));
               
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
	
    //-----------------------------------------------------------------//

    //-----------------------Related to Timer------------------------//
    
    /**
     * Método que activa el temporizador de tiempo.
     */
    public void startTimer()
    {        
        stopTimer = false;
        
        timer = new Thread() {
                @Override
                public void run()
                {
                    while(!stopTimer)
                    {
                        timePassed++;

                        // Update the time passed label.
                        timePassedLabel.setText("  " + timePassed + "  ");

                        // Wait 1 second.
                        try{
                            sleep(1000); 
                        }
                        catch(InterruptedException ex){}
                    }
                }
        };                

       timer.start();
    }

    /**
     * Método que para el temporizador de tiempo.
     */
    public void interruptTimer()
    {
        stopTimer = true;
                
        try 
        {
            if (timer!= null)
                timer.join();
        } 
        catch (InterruptedException ex) 
        {

        }        
    }
    
    /**
     * Método que resetea el temporizador de tiempo.
     */
    public void resetTimer()
    {
        timePassed = 0;
        timePassedLabel.setText("  " + timePassed + "  ");        
    }

    /**
     * Método para mostrar el tiempo pasado en la ventana.
     * 
     * @param t Entero con el tiempo pasado.
     */
    public void setTimePassed(int t)
    {
        timePassed = t;
        timePassedLabel.setText("  " + timePassed + "  ");                
    }
    
    //-----------------------------------------------------------//
    /**
     * Método que inicia el juego gráficamente, es decir: pone las casillas en
     * modo oculto y las activa todas.
     */
    public void initGame()
    {
        hideAll();
        enableAll();
    }
    
    //------------------HELPER FUNCTIONS-----------------------//
    /**
     * Método que activa las casillas para que se les pueda pulsar en ellas.
     */
    public void enableAll()
    {
        for( int x=0 ; x<cols ; x++ ) 
        {
            for( int y=0 ; y<rows ; y++ ) 
            {
                buttons[x][y].setEnabled(true);
            }
        }
    }

    /**
     * Método que desactiva las casillas para que NO se les pueda pulsar en ellas.
     */
    public void disableAll()
    {
        for( int x=0 ; x<cols ; x++ ) 
        {
            for( int y=0 ; y<rows ; y++ ) 
            {
                buttons[x][y].setEnabled(false);
            }
        }
    }


    /**
     * Método que oculta todas las casillas, mostrandolas en modo inicial.
     */
    public void hideAll()
    {
        for( int x=0 ; x<cols ; x++ ) 
        {
            for( int y=0 ; y<rows ; y++ ) 
            {
                buttons[x][y].setText("");                
                buttons[x][y].setBackground(new Color(0,103,200));
                buttons[x][y].setIcon(tile);                
            }
        }
    }

    
    //---------------SET LISTENERS--------------------------//
    /**
     * Método que prepara los botones diciendoles que controlador los va ha atender.
     * 
     * @param game Es el controlador que los atenderá.
     */
    public void setButtonListeners(Game game)
    {
        addWindowListener(game);
    
        // Set listeners for all buttons in the grid in gameBoard
        for( int x=0 ; x<cols ; x++ ) 
        {
            for( int y=0 ; y<rows ; y++ ) 
            {
                buttons[x][y].addMouseListener(game);
            }
        }
        
        // Set listeners for menu items in menu bar
       newGame.addActionListener(game);
       statistics.addActionListener(game);
       exit.addActionListener(game);

       newGame.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
       exit.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
       statistics.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));       
    }
    
    
    //-----------------GETTERS AND SETTERS--------------------//
    /**
     * Getter para pedir el array de botones.
     * 
     * @return El array de botones.
     */
    public JButton[][] getButtons()
    {
        return buttons;
    }
    
    /**
     * Getter para saber el tiempo que ha pasado.
     * 
     * @return El tiempo que ha pasado.
     */
    public int getTimePassed()
    {
        return timePassed;
    }    


    //----------------------SET LOOK------------------------------//
    /**
     * Método para suavizar el mostrado de la ventana.
     * 
     * @param look Nombre de la ventana a suavizar.
     */
    public static void setLook(String look)
    {
        try {

            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (look.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
        } catch (Exception ex) { }            
    }

    //-------------------------------------------------------------//
    /**
     * Setter para mostrar el número de minas restantes.
     * 
     * @param m El número de minas restantes.
     */
    public void setMines(int m)
    {
        mines = m;
        minesLabel.setText("  " + Integer.toString(m) + "  ");
    }
    
    /**
     * Método para incrementar el número de minas y mostrarlo.
     */
    public void incMines()
    {
        mines++;
        setMines(mines);
    }
    
    /**
     * Método para decrementar el número de minas y mostrarlo.
     */
    public void decMines()
    {
        mines--;
        setMines(mines);
    }
    
    /**
     * Getter para pedir el número de minas restante.
     * 
     * @return El número de minas restante.
     */
    public int getMines()
    {
        return mines;
    }
            
    //--------------------Related to Icons----------------------------//
    /**
     * Método para redimensionar una imágen de un icono.
     * 
     * @param icon La imágen del icono a redimensionar.
     * @param resizedWidth Ancho de la imagen del icono a redimensionar.
     * @param resizedHeight Alto de la imagen del icono a redimensionar.
     * @return La imagen del icono redimensionado.
     */
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) 
    {
        Image img = icon.getImage();  
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        return new ImageIcon(resizedImage);
    }    
    
    /**
     * Método para cargar los iconos desde los archivos del proyecto.
     */
    public void setIcons()
    {
       //---------------------Set Icons-----------------------------//

        int bOffset = buttons[0][1].getInsets().left;
        int bWidth = buttons[0][1].getWidth();
        int bHeight = buttons[0][1].getHeight();
        
        ImageIcon d;
        
        d = new ImageIcon(getClass().getResource("/resources/redmine.png"));                
        redMine =   resizeIcon(d, bWidth - bOffset, bHeight - bOffset);        

        d = new ImageIcon(getClass().getResource("/resources/mine.png"));                
        mine =   resizeIcon(d, bWidth - bOffset, bHeight - bOffset);        
        
        d = new ImageIcon(getClass().getResource("/resources/flag.png"));                
        flag =   resizeIcon(d, bWidth - bOffset, bHeight - bOffset);        
        
        d = new ImageIcon(getClass().getResource("/resources/tile.png"));                
        tile =   resizeIcon(d, bWidth - bOffset, bHeight - bOffset);        
                
        //-------------------------------------------------------//
        
    }
    
    /**
     * Getter para pedir el icono de una mina.
     * 
     * @return El icono de una mina.
     */
    public Icon getIconMine()
    {
        return mine;
    }

    /**
     * Getter para pedir el icono de una mina explotada.
     * 
     * @return El icono de una mina explotada.
     */
    public Icon getIconRedMine()
    {
        return redMine;
    }
    
    /**
     * Getter para pedir el icono de una bandera.
     * 
     * @return El icono de una bandera.
     */
    public Icon getIconFlag()
    {
        return flag;
    }
    
    /**
     * Getter para pedir el icono de una casilla sin pulsar.
     * 
     * @return El icono de una casilla sin pulsar.
     */
    public Icon getIconTile()
    {
        return tile;       
    }        
    
    
    //---------------------------------------------------------------------//
    /**
     * Método que asigna los colores de fondo según sea el valor del texto.
     * 
     * @param b JButton a asignar.
     */
    public void setTextColor(JButton b)
    {
        if (b.getText().equals("1"))
            b.setForeground(Color.blue);
        else if (b.getText().equals("2"))
            b.setForeground(new Color(76,153,0));
        else if (b.getText().equals("3"))
            b.setForeground(Color.red);
        else if (b.getText().equals("4"))
            b.setForeground(new Color(153,0,0));
        else if (b.getText().equals("5"))
            b.setForeground(new Color(153,0,153));
        else if (b.getText().equals("6"))
            b.setForeground(new Color(96,96,96));
        else if (b.getText().equals("7"))
            b.setForeground(new Color(0,0,102));
        else if (b.getText().equals("8"))
            b.setForeground(new Color(153,0,76));        
    }
    //------------------------------------------------------------------------//
    
    
}
