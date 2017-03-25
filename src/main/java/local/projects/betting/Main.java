package local.projects.betting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import local.projects.betting.model.Result;
import local.projects.betting.model.Team;

@Component
public class Main {
  
  public static void main(String[] args) {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("classpath:application-context.xml");
    
    Main p = context.getBean(Main.class);
    p.start(args);
  }
  
  @Autowired
  private MyBean myBean;
  
  @Autowired
  private DataSource db;
  
  private void start(String[] args) {
    System.out.println("my beans method: " + myBean.getStr());
    System.out.println(selectForum().toString());
  }
  
  public Result selectForum() {
    /**
     * Specify the statement
     */
    String query = "SELECT * FROM risultati";
    /**
     * Define the connection, preparedStatement and resultSet parameters
     */
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Result result = null;
    try {
      /**
       * Open the connection
       */
      connection = db.getConnection();
      /**
       * Prepare the statement
       */
      preparedStatement = connection.prepareStatement(query);
      /**
       * Bind the parameters to the PreparedStatement
       */
      // preparedStatement.setInt(1, forumId);
      /**
       * Execute the statement
       */
      resultSet = preparedStatement.executeQuery();
      /**
       * Extract data from the result set
       */
      if (resultSet.next()) {
        result = new Result(new Date(), new Team(resultSet.getString("Casa")),
            new Team(resultSet.getString("Trasferta")), 0, 0);
      }
    } catch (SQLException e) {
      /**
       * Handle any exception
       */
      e.printStackTrace();
    } finally {
      try {
        /**
         * Close the resultSet
         */
        if (resultSet != null) {
          resultSet.close();
        }
        /**
         * Close the preparedStatement
         */
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        /**
         * Close the connection
         */
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        /**
         * Handle any exception
         */
        e.printStackTrace();
      }
    }
    return result;
  }
}
