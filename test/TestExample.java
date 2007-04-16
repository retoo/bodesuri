import junit.framework.*;

public class TestExample extends TestCase {

	
	public void testFoo() {
		assertTrue( "TestFoo", SuperDuper.foo() );
	}
	
	public void testBar() {
		assertTrue( "TestBar", SuperDuper.bar() );
	}
}