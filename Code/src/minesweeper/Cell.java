package minesweeper;

/**
 * Clase que representa a la celda.
 * 
 * @author Haris Muneer.
 */
public class Cell 
{
    /**
     * Booleano que guarda si esta celda almacena una mina (TRUE) o no (FALSE).
     */
    private boolean mine;

    /**
     * Descripción de la celda en modo textual:
     *  "" - Indica un valor desconocido (es un estado erroneo o inicial).
     *  "F" - Hay una bandera.
     *  "M" - Hay una mina.
     *  Número de "0" a "8" - indica el número de minas de sus adyacentes.
     */
    private String content;

    /**
     * Número de minas de sus adyacentes (de 0 a 8).
     */
    private int surroundingMines;

    
    //----------------------------------------------------------//
    /**
     * Constructor de la clase.
     */
    public Cell()
    {
        mine = false;
        content = "";
        surroundingMines = 0;
    }


    //-------------GETTERS AND SETTERS----------------------------//
    /**
     * Getter para saber si hay mina dentro o no.
     * 
     * @return TRUE si tiene mina, FALSE si no la tiene.
     */
    public boolean getMine()
    {
        return mine;
    }

    /**
     * Setter para guardar si tiene o no una mina.
     * 
     * @param mine Boolean, TRUE si tiene mina, FALSE si no la tiene.
     */
    public void setMine(boolean mine)
    {
        this.mine = mine;
    }

    /**
     * Getter para saber que guarda en modo texto:
     *  "" - desconocido.
     *  "F" - una bandera.
     *  "M" - una mina.
     *  Número de "0" a "8" - indica el número de minas de sus adyacentes.
     * 
     * @return String con el código.
     */
    public String getContent()
    {
        return content;
    }

    /**
     * Setter para asignar el contenido textual:
     *  "" - desconocido.
     *  "F" - una bandera.
     *  "M" - una mina.
     *  Número de "0" a "8" - indica el número de minas de sus adyacentes.
     * 
     * @param content El contenido a asignar.
     */
    public void setContent(String content)
    {
        this.content = content;
    }

    /**
     * Getter para saber el número de minas de sus adyacentes.
     * 
     * @return El número de minas de sus adyacentes.
     */
    public int getSurroundingMines()
    {
        return surroundingMines;
    }

    /**
     * Setter para asignar el número de minas de sus adyacentes.
     * 
     * @param surroundingMines El número de minas de sus adyacentes.
     */
    public void setSurroundingMines(int surroundingMines)
    {
        this.surroundingMines = surroundingMines;
    }

    //-------------------------------------------------------------//
}
