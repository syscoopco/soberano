package co.syscoop.soberano.domain.tracked.worker;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.opentest4j.AssertionFailedError;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.ActionTest;
import co.syscoop.soberano.test.helper.ChangePasswordForm;
import co.syscoop.soberano.test.helper.ConstrainedForm;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.StringIdCodeGenerator;

import org.springframework.context.*;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;

@Order(3)

//TODO: enable test
//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class O3_WorkerTest_password_change extends ActionTest {
	
	private static String oldPassword = "";
	private static String newPassword = "";
	private ApplicationContext applicationContext = SpringUtility.applicationContext();
	protected ChangePasswordForm changePasswordForm = null;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Zats.init("./src/main/webapp");	
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
		Zats.cleanup();
		Zats.end();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	@Order(0)
	final void test0() {
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/change_password.zul");
		changePasswordForm = new ChangePasswordForm(desktop,
													(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
													(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class));
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			changePasswordForm.testEachConstrainedObjectIsDeclared();
			changePasswordForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	@Order(1)
	final void test1() {
		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/change_password.zul");		
		oldPassword = new StringIdCodeGenerator().getTenCharsRandomString("");
		ConstrainedForm constrainedForm = new ConstrainedForm();
		constrainedForm.setComponentValue(desktop.query("textbox").query("#txtPassword").as(Textbox.class), oldPassword);
		constrainedForm.setComponentValue(desktop.query("textbox").query("#txtConfirmPassword").as(Textbox.class), oldPassword);
		clickOnApplyButton(desktop);
	}
	
	private Boolean userAuthenticate(String userName, String password) {
		Filter filter = new EqualsFilter("uid", userName);
		LdapTemplate ldapTemplate = (LdapTemplate) applicationContext.getBean("ldapTemplate");
        return ldapTemplate.authenticate(LdapUtils.emptyLdapName(), filter.encode(), password);
	}
	
	@Test
	@Order(2)
	final void test2() {		
		if (!userAuthenticate("user1@soberano.syscoop.co", oldPassword)) {
			fail("User should authenticate with a password changed without problem.");
		}
	}
	
	@Test
	@Order(3)
	final void test3() {
		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/change_password.zul");
		changePasswordForm = new ChangePasswordForm(desktop,
													(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
													(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class));
		try {			
			changePasswordForm.setComponentValue(desktop.query("textbox").query("#txtPassword").as(Textbox.class), "");
			changePasswordForm.setComponentValue(desktop.query("textbox").query("#txtConfirmPassword").as(Textbox.class), "");
			clickOnApplyButton(desktop);
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			changePasswordForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(4)
	final void test4() {
		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/change_password.zul");
		changePasswordForm = new ChangePasswordForm(desktop,
													(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
													(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class));
		try {			
			changePasswordForm.setComponentValue(desktop.query("textbox").query("#txtPassword").as(Textbox.class), "12345");
			changePasswordForm.setComponentValue(desktop.query("textbox").query("#txtConfirmPassword").as(Textbox.class), "");
			clickOnApplyButton(desktop);
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			changePasswordForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(5)
	final void test5() {
		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/change_password.zul");
		changePasswordForm = new ChangePasswordForm(desktop,
													(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
													(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class));
		try {			
			changePasswordForm.setComponentValue(desktop.query("textbox").query("#txtPassword").as(Textbox.class), "12345");
			changePasswordForm.setComponentValue(desktop.query("textbox").query("#txtConfirmPassword").as(Textbox.class), "1234");
			clickOnApplyButton(desktop);
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testPasswordsMustMatchException(ex);
		}
	}
	
	@Test
	@Order(6)
	final void test6() {
		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/change_password.zul");		
		newPassword = new StringIdCodeGenerator().getTenCharsRandomString("");
		ConstrainedForm constrainedForm = new ConstrainedForm();
		constrainedForm.setComponentValue(desktop.query("textbox").query("#txtPassword").as(Textbox.class), newPassword);
		constrainedForm.setComponentValue(desktop.query("textbox").query("#txtConfirmPassword").as(Textbox.class), newPassword);
		clickOnApplyButton(desktop);
	}
	
	@Test
	@Order(7)
	final void test7() {		
		if (userAuthenticate("user1@soberano.syscoop.co", oldPassword)) {
			fail("User should not authenticate with an old password.");
		}
	}
	
	@Test
	@Order(8)
	final void test8() {
		if (!userAuthenticate("user1@soberano.syscoop.co", newPassword)) {
			fail("User should authenticate with a password changed without problem.");
		}
	}
}
