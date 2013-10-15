package md.utm.simple_jdbc;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MySQLAccessTest {
	private MySQLAccess testable;

	@Before
	public void before() throws SQLException {
		testable = new MySQLAccess();
		testable.getConnection().setAutoCommit(false);
	}

	@After
	public void after() {
		testable.close();
	}

	@Test
	public void testInsertRow() {
		Assert.assertTrue(testable.findPerson("Andrei", "Cojocari").isEmpty());
		int insertRow = testable.insertRow("Andrei", "Cojocari", 1985, false);
		Assert.assertEquals(1, insertRow);
		Assert.assertFalse(testable.findPerson("Andrei", "Cojocari").isEmpty());
	}

	@Test
	public void testRemoveFromDataBase() {
		testable.insertRow("Andrei", "Cojocari", 1985, false);
		testable.findPerson("Andrei", "Cojocari");
		testable.removeFromDataBase("Andrei", "Cojocari");
	}

	@Test
	public void testReadFromDataBase() {
		testable.insertRow("Andrei", "Cojocari", 1985, false);
		List<Person> readFromDataBase = testable.readFromDataBase();
		Assert.assertFalse(readFromDataBase.isEmpty());
	}

}
