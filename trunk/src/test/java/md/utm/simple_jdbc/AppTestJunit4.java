package md.utm.simple_jdbc;

import static org.junit.Assert.*;

import md.utm.simple_jdbc.App;

import org.junit.Test;

public class AppTestJunit4 {

	@Test
	public void testMain() {
		App app = new App();
		app.main(new String[]{"test"});
	}

}
