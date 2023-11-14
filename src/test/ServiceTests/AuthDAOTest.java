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
    AuthToken authToCreate = new AuthToken();
    userDao.findUser("Alilah");
    authToCreate.setUsername("Alilah");
    authDao.insertAuth(authToCreate);
    assertEquals(authToCreate.getUsername(), "Alilah");
    assertNotNull(authToCreate.getAuthToken());
    System.out.println(authToCreate.getAuthToken());
  }
  @Test
  public void falseAuthToken() throws DataAccessException{
    AuthToken AlilahAuth = authDao.getAuthTokenByUsername("Alilah");
    assertThrows(DataAccessException.class, ()-> authDao.insertAuth(AlilahAuth));
  }


}
