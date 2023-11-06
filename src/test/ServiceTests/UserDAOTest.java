package ServiceTests;
import dataAccess.DAO.UserDao;
import chess.Model.User;
import java.util.Collection;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class UserDAOTest { //ALL PASS

  static UserDao userDao = new UserDao();

  @BeforeAll
  public static void setup() throws DataAccessException{
    userDao.clearUsersInDatabase();
    User testUser = new User("Alilah", "password", "alilah@gmail.com");
    userDao.insertUser(testUser);
  }
  @Test
  public void insertNewUser()throws DataAccessException{
    User userToInsert = new User("Rebekah", "pass", "Rebekah@gmail.com");
    userDao.insertUser(userToInsert);
    assertEquals(userDao.findUser("Rebekah"), userToInsert);
  }

  @Test
  public void failDuplicateInsert() throws DataAccessException{
    User duplicateUser = new User("Alilah", "password", "alilah@gmail.com");
    assertThrows(DataAccessException.class, ()-> userDao.insertUser(duplicateUser));
  }

  @Test
  public void findFakeUser()throws DataAccessException{ //changed data exception
    User user = userDao.findUser("Hola");
    assertEquals(user, null);
  }

  @Test
  public void incorrectUser() throws DataAccessException{
    User fakeUser = new User();
    fakeUser.setUsername("fake");
    fakeUser.setEmail("fakeemail@gmail.com");
    assertThrows(DataAccessException.class, ()-> userDao.insertUser(fakeUser));
  }

}
