package minesweeper;

import static java.lang.Math.ceil;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;

/**
 * Clase que almacena los datos de las partidas jugadas.
 * 
 * @author Haris Muneer.
 */
public class Score
{
    /**
     * ArrayList con los mejores tiempos.
     */
    ArrayList<Time> bestTimes;
    
    /**
     * Número de partidas jugadas.
     */
    int gamesPlayed;
    /**
     * Número de partidas ganadas.
     */
    int gamesWon;
       
    /**
     * Número máximo de partidas ganadas consecutivamente.
     */
    int longestWinningStreak;
    /**
     * Número máximo de partidas perdidas consecutivamente.
     */
    int longestLosingStreak;
    
    /**
     * Número de partidas consecutivas actual.
     */
    int currentStreak;

    /**
     * Número de partidas ganadas consecutivas actual.
     */
    int currentWinningStreak;
    /**
     * Número de partidas perdidas consecutivas actual.
     */
    int currentLosingStreak;
    
    /**
     * Constructor de la clase.
     */
    public Score()
    {
        gamesPlayed = gamesWon = currentStreak = longestLosingStreak = longestWinningStreak = currentWinningStreak = currentLosingStreak = 0;
        bestTimes = new ArrayList();
    }
    
    /**
     * Getter para saber las partidas jugadas.
     * 
     * @return Entero con las partidas jugadas.
     */
    public int getGamesPlayed()
    {
        return gamesPlayed;        
    }
    
    /**
     * Getter para saber las partidas ganadas.
     * 
     * @return Entero con las partidas ganadas.
     */
    public int getGamesWon()
    {        
        return gamesWon;
    }
    
    /**
     * Getter para saber el porcentaje de partidas ganadas.
     * 
     * @return Entero con el porcentaje de partidas ganadas.
     */
    public int getWinPercentage()
    {
        double gP = gamesPlayed;
        double gW = gamesWon;
        
        double percentage = ceil((gW/gP) * 100);
        
        return (int)percentage;
    }
    
    /**
     * Getter para saber el número máximo de partidas ganadas consecutivas.
     * 
     * @return Entero con el número máximo de partidas ganadas consecutivas.
     */
    public int getLongestWinningStreak()
    {
        return longestWinningStreak;
    }
    
    /**
     * Getter para saber el número máximo de partidas perdidas consecutivas.
     * 
     * @return Entero con el número máximo de partidas perdidas consecutivas.
     */
    public int getLongestLosingStreak()
    {
        return longestLosingStreak;
    }
    
    /**
     * Getter para saber el número actual de partidas jugadas consecutivas..
     * 
     * @return Entero con el número actual de partidas jugadas consecutivas.
     */
    public int getCurrentStreak()
    {
        return currentStreak;
    }
    
    /**
     * Getter para saber el número actual de partidas perdidas consecutivas.
     * 
     * @return Entero con el número actual de partidas perdidas consecutivas.
     */
    public int getCurrentLosingStreak()
    {
        return currentLosingStreak;
    }

    /**
     * Getter para saber el número actual de partidas ganadas consecutivas.
     * 
     * @return Entero con el número actual de partidas ganadas consecutivas.
     */
    public int getCurrentWinningStreak(){
        return currentWinningStreak;
    }
    
    /**
     * Método para incrementar el número de partidas ganadas.
     */
    public void incGamesWon()
    {
        gamesWon++;
    }
    
    /**
     * Método para incrementar el número de partidas jugadas.
     */
    public void incGamesPlayed()
    {
        gamesPlayed++;
    }
    
    /**
     * Método para incrementar el número de partidas consecutivas.
     */
    public void incCurrentStreak()
    {
        currentStreak++;
    }
    
    /**
     * Método para incrementar el número de partidas perdidas consecutivas.
     */
    public void incCurrentLosingStreak()
    {
        currentLosingStreak++;
        
        if (longestLosingStreak < currentLosingStreak)
        {
            longestLosingStreak = currentLosingStreak;
        }                
    }

    /**
     * Método para incrementar el número de partidas ganadas consecutivas.
     */
    public void incCurrentWinningStreak()
    {
        currentWinningStreak++;
        
        if (longestWinningStreak < currentWinningStreak)
        {
            longestWinningStreak = currentWinningStreak;
        }                
    }
    
    /**
     * Método para decrementar el número de partidas consecutivas.
     */
    public void decCurrentStreak()
    {        
        currentStreak--;
    }    
    
    /**
     * Método para reiniciar el contador de partidas.
     */
    public void resetScore()
    {
        gamesPlayed = gamesWon = currentStreak = longestLosingStreak = longestWinningStreak = currentWinningStreak = currentLosingStreak = 0;
    }
    
    /**
     * Getter para conocer los mejores tiempos.
     * 
     * @return ArrayList<Time> con los mejores tiempos.
     */
    public ArrayList<Time> getBestTimes()
    {
        return bestTimes;
    }
        
    /**
     * Método para añadir un tiempo. Al añadirlo, lo añade en orden y elimina
     * el peor si la lista tiene más de 5.
     * 
     * @param time Tiempo a añadir.
     * @param date Fecha de realización de ese tiempo.
     */
    public void addTime(int time, Date date)
    {
        bestTimes.add(new Time(time,date));
        Collections.sort(bestTimes,new TimeComparator()); 
        
        if(bestTimes.size() > 5)
            bestTimes.remove(bestTimes.size()-1);
    }
     
    //--------------------------------------------------------//

    
    //------------DATABASE--------------------------//
    
    //------------POPULATE FROM DATABASE------------//
    /**
     * Método que accede a la base de datos para solicitar los mejores tiempos y resultados.
     * 
     * @return TRUE en caso de que todo haya ido bien, FALSE si ha habido algún error.
     */
    public boolean populate()
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            String dbURL = Game.dbPath; 

            connection = DriverManager.getConnection(dbURL); 
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SCORE");

            while(resultSet.next()) 
            {
                gamesPlayed = resultSet.getInt("GAMES_PLAYED");
                gamesWon = resultSet.getInt("GAMES_WON");

                longestWinningStreak = resultSet.getInt("LWSTREAK");
                longestLosingStreak = resultSet.getInt("LLSTREAK");

                currentStreak = resultSet.getInt("CSTREAK");

                currentWinningStreak = resultSet.getInt("CWSTREAK");
                currentLosingStreak = resultSet.getInt("CLSTREAK");                                
            }
            
            // cleanup resources, once after processing
            resultSet.close();
            statement.close();

            
            //------------------------LOAD TIMES------------------//
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM TIME");
            
            
            while(resultSet.next())
            {
                int time = resultSet.getInt("TIME_VALUE");
                Date date = resultSet.getDate("DATE_VALUE");
                
                bestTimes.add(new Time(time,date));
            }
            
            
            // cleanup resources, once after processing
            resultSet.close();
            statement.close();
            
            
            // and then finally close connection
            connection.close();            
            
            return true;
        }
        catch(SQLException sqlex)
        {
            sqlex.printStackTrace();
            return false;
        }
    }

    /**
     * Método para guardar los datos en la base de datos.
     */
    public void save()
    {
        Connection connection = null;
        PreparedStatement statement = null;
        

        try {
            String dbURL = Game.dbPath; 
            
            connection = DriverManager.getConnection(dbURL); 

            
            //----------EMPTY SCORE TABLE------//
            String template = "DELETE FROM SCORE"; 
            statement = connection.prepareStatement(template);
            statement.executeUpdate();
            
            //----------EMPTY TIME TABLE------//
            template = "DELETE FROM TIME"; 
            statement = connection.prepareStatement(template);
            statement.executeUpdate();
            
            //--------------INSERT DATA INTO SCORE TABLE-----------//            
            template = "INSERT INTO SCORE (GAMES_PLAYED,GAMES_WON, LWSTREAK, LLSTREAK, CSTREAK, CWSTREAK, CLSTREAK) values (?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(template);
            
            statement.setInt(1, gamesPlayed);
            statement.setInt(2, gamesWon);
            statement.setInt(3, longestWinningStreak);
            statement.setInt(4, longestLosingStreak);
            statement.setInt(5, currentStreak);
            statement.setInt(6, currentWinningStreak);
            statement.setInt(7, currentLosingStreak);
            
            statement.executeUpdate();
            
            //-------------------INSERT DATA INTO TIME TABLE-----------//
            template = "INSERT INTO TIME (TIME_VALUE, DATE_VALUE) values (?,?)";
            statement = connection.prepareStatement(template);
            

            for (int i = 0; i < bestTimes.size(); i++)
            {
                statement.setInt(1, bestTimes.get(i).getTimeValue());
                statement.setDate(2, bestTimes.get(i).getDateValue());
                
                statement.executeUpdate();            
            }

            //---------------------------------------------------------//
            
            statement.close();
            
            // and then finally close connection
            connection.close();            
        }
        catch(SQLException sqlex)
        {
            sqlex.printStackTrace();
        }
        
    }

    //--------------------------------------------------//
    
    
    //---------------------------------------------------//
    /**
     * Clase interna para comparar tiempos.
     */
    public class TimeComparator implements Comparator<Time>
    {
        @Override
        public int compare(Time a, Time b) {
            if (a.getTimeValue() > b.getTimeValue())
                return 1;
            else if (a.getTimeValue() < b.getTimeValue())
                return -1;
            else
                return 0;
        }                        
    }

    //----------------------------------------------------------//
    /**
     * Clase interna para la gestión de tiempos en fecha.
     */
    public class Time{
        Date date;
        int time;
        
        /**
         * Constructor.
         * 
         * @param t Tiempo conseguido en el juego.
         * @param d Fecha de consecución.
         */
        public Time(int t, Date d)
        {
            time = t;
            date = d;
        }
        
        /**
         * Getter de la fecha.
         * 
         * @return La fecha.
         */
        public Date getDateValue()
        {
            return date;
        }
        
        /**
         * Getter del tiempo del juego.
         * 
         * @return El tiempo del juego.
         */
        public int getTimeValue()
        {
            return time;
        }        
    }    
}
