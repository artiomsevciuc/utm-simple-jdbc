package md.utm.simple_jdbc;

import java.sql.SQLException;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SQLAccessTest {
	private SqlExamlpleAccess testable;
	private LocalDate localDate;

	@Before
	public void before() throws SQLException {
		testable = new SqlExamlpleAccess();
		testable.getConnection().setAutoCommit(false);
		localDate = new LocalDate(1989, 5, 1);
	}

	@After
	public void after() {
		testable.close();
	}

	@Test
	public void testInsertRow() {
		Assert.assertTrue(testable.findPerson("Andrei", "Cojocari").isEmpty());
		int insertRow = testable.insertRow("Andrei", "Cojocari", localDate.toDate(), false);
		Assert.assertEquals(1, insertRow);
		Assert.assertFalse(testable.findPerson("Andrei", "Cojocari").isEmpty());
	}

	@Test
	public void testRemoveFromDataBase() {
		testable.insertRow("Andrei", "Cojocari", localDate.toDate(), false);
		testable.findPerson("Andrei", "Cojocari");
		testable.removeFromDataBase("Andrei", "Cojocari");
	}

	@Test
	public void testReadFromDataBase() {
		testable.insertRow("Andrei", "Cojocari", localDate.toDate(), false);
		List<Person> readFromDataBase = testable.readFromDataBase();
		Assert.assertFalse(readFromDataBase.isEmpty());
	}

}
