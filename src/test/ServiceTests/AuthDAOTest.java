package ServiceTests;
import java.util.Collection;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

import chess.Model.User;
import dataAccess.DAO.UserDao;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import dataAccess.DAO.AuthDao;
import chess.Model.AuthToken;

public class AuthDAOTest {

 static AuthDao authDao = new AuthDao();
 static  UserDao userDao = new UserDao();

  @BeforeAll
  public static void setup() throws DataAccessException{
    authDao.clearAuthTokensInDB();
    userDao.clearUsersInDatabase();
    User testUser = new User("Alilah", "password", "alilah@gmail.com");
    userDao.insertUser(testUser);
  }

  @Test
  public void createAuthToken() throws DataAccessException{ //only works when taking off condition to check if user is in userDao
    userDao.findUser("Alilah");
    AuthToken authToCreate = authDao.insertAuth("Alilah");
    assertEquals(authToCreate.getUsername(), "Alilah");
    assertNotNull(authToCreate.getAuthToken());
    System.out.println(authToCreate.getAuthToken());
  }
  @Test
  public void falseAuthToken() throws DataAccessException{
    assertThrows(DataAccessException.class, ()-> authDao.insertAuth("fake"));
  }

//  @Test
//  public void duplicateAuthCheck()throws DataAccessException{
//    //TODO: create auth test yourself to see how they are getting duplicated
//    //start off loging the person in
//    //TODO: maybe start off checking this in the loginTest because login is already done
//  }
}
