package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import co.syscoop.soberano.domain.tracked.Worker;
import co.syscoop.soberano.domain.untracked.Authority;
import co.syscoop.soberano.domain.untracked.Responsibility;
import co.syscoop.soberano.util.SpringUtility;

/**
* @author Josue Portal
*
* The system needs logged in users to function securely.
*
* A set of workers, from the test cases, are recorded as preconditions. The remainders are used as input of cases.
* Notice, a worker is defined by its assignments.
*/
@Order(2)

//TODO: enable test
//@Disabled

class O2_WorkerTest_populate {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		try {
			/////////////////////////////////
			// add workers as precondition //
			/////////////////////////////////
			
			//clean LDAP of precondition users
			Worker workerToDelete = null;
			for (Integer i = 1; i <= 21; i++) {
				workerToDelete = new Worker("user" + i.toString() + "@soberano.syscoop.co");
				try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			}
			
			//clean other LDAP test users
			workerToDelete = new Worker("accounter@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			workerToDelete = new Worker("auditor@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			workerToDelete = new Worker("catalogMaintainer@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			workerToDelete = new Worker("checker@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			workerToDelete = new Worker("communityManager@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			workerToDelete = new Worker("manager@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			workerToDelete = new Worker("procurementWorker@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			workerToDelete = new Worker("salesclerk@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			workerToDelete = new Worker("shiftManager@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			workerToDelete = new Worker("storekeeper@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			workerToDelete = new Worker("systemadmin@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			workerToDelete = new Worker("workshop1Worker@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
			workerToDelete = new Worker("workshop2Worker@soberano.syscoop.co");
			try{workerToDelete.deleteUserFromLDAP();}catch(Exception ex){ex.printStackTrace();}
									
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();
			
			//user are recorded under Soberano's instance top user.
			SpringUtility.setLoggedUserForTesting("soberano.user.top");
			
			//user1
			responsibilities.add(new Responsibility(2, "Manager"));
			responsibilities.add(new Responsibility(3, "Accounter"));
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			responsibilities.add(new Responsibility(5, "Shift manager"));
			responsibilities.add(new Responsibility(6, "Checker"));
			responsibilities.add(new Responsibility(7, "Auditor"));
			responsibilities.add(new Responsibility(8, "Workshop 1 worker"));
			responsibilities.add(new Responsibility(9, "Workshop 2 worker"));
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			responsibilities.add(new Responsibility(11, "Catalog maintainer"));
			responsibilities.add(new Responsibility(12, "Community manager"));
			responsibilities.add(new Responsibility(13, "Procurement worker"));
			responsibilities.add(new Responsibility(14, "System admin"));
			responsibilities.add(new Responsibility(15, "Technologist"));
			for (int i = 1; i <= 14; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorker = new Worker(0,
										0,
										"user1@soberano.syscoop.co",
										"user1fn",
										"user1ln",
										"12345",
										"5355555551",
										"CU",
										"Calle user1, entre Y y Z",
										"10401",
										"Vedado1",
										1001,
										"La Habana1",
										2,
										0.0,
										0.0,
										responsibilities,
										authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}			
			
			//user2
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(2, "Manager"));
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			responsibilities.add(new Responsibility(6, "Checker"));
			responsibilities.add(new Responsibility(7, "Auditor"));
			responsibilities.add(new Responsibility(8, "Workshop 1 worker"));
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			responsibilities.add(new Responsibility(11, "Catalog maintainer"));
			responsibilities.add(new Responsibility(14, "System admin"));
			for (int i = 1; i <= 8; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user2@soberano.syscoop.co",
									"user2fn",
									"user2ln",
									"12345",
									"5355555552",
									"CU",
									"Calle user2, entre Y y Z",
									"10402",
									"Vedado2",
									1002,
									"La Habana2",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user3
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(2, "Manager"));
			responsibilities.add(new Responsibility(3, "Accounter"));
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			responsibilities.add(new Responsibility(5, "Shift manager"));
			responsibilities.add(new Responsibility(7, "Auditor"));
			responsibilities.add(new Responsibility(9, "Workshop 2 worker"));
			responsibilities.add(new Responsibility(12, "Community manager"));
			responsibilities.add(new Responsibility(13, "Procurement worker"));
			responsibilities.add(new Responsibility(14, "System admin"));
			responsibilities.add(new Responsibility(15, "Technologist"));
			for (int i = 1; i <= 10; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user3@soberano.syscoop.co",
									"user3fn",
									"user3ln",
									"12345",
									"5355555553",
									"CU",
									"Calle user3, entre Y y Z",
									"10403",
									"Vedado3",
									1003,
									"La Habana3",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user4
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(3, "Accounter"));
			responsibilities.add(new Responsibility(8, "Workshop 1 worker"));
			responsibilities.add(new Responsibility(9, "Workshop 2 worker"));
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			responsibilities.add(new Responsibility(11, "Catalog maintainer"));
			responsibilities.add(new Responsibility(12, "Community manager"));
			responsibilities.add(new Responsibility(13, "Procurement worker"));
			responsibilities.add(new Responsibility(14, "System admin"));
			for (int i = 1; i <= 8; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user4@soberano.syscoop.co",
									"user4fn",
									"user4ln",
									"12345",
									"5355555554",
									"CU",
									"Calle user4, entre Y y Z",
									"10404",
									"Vedado4",
									1004,
									"La Habana4",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user5
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(5, "Shift manager"));
			responsibilities.add(new Responsibility(6, "Checker"));
			responsibilities.add(new Responsibility(7, "Auditor"));
			responsibilities.add(new Responsibility(8, "Workshop 1 worker"));
			responsibilities.add(new Responsibility(9, "Workshop 2 worker"));
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			responsibilities.add(new Responsibility(12, "Community manager"));
			responsibilities.add(new Responsibility(14, "System admin"));
			responsibilities.add(new Responsibility(15, "Technologist"));
			for (int i = 1; i <= 9; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user5@soberano.syscoop.co",
									"user5fn",
									"user5ln",
									"12345",
									"5355555555",
									"CU",
									"Calle user5, entre Y y Z",
									"10405",
									"Vedado5",
									1005,
									"La Habana5",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user6
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(2, "Manager"));
			responsibilities.add(new Responsibility(3, "Accounter"));
			responsibilities.add(new Responsibility(5, "Shift manager"));
			responsibilities.add(new Responsibility(6, "Checker"));
			responsibilities.add(new Responsibility(7, "Auditor"));
			responsibilities.add(new Responsibility(8, "Workshop 1 worker"));
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			responsibilities.add(new Responsibility(11, "Catalog maintainer"));
			responsibilities.add(new Responsibility(12, "Community manager"));
			responsibilities.add(new Responsibility(13, "Procurement worker"));
			for (int i = 1; i <= 10; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user6@soberano.syscoop.co",
									"user6fn",
									"user6ln",
									"12345",
									"5355555556",
									"CU",
									"Calle user6, entre Y y Z",
									"10406",
									"Vedado6",
									1006,
									"La Habana6",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user7
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(2, "Manager"));
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			responsibilities.add(new Responsibility(5, "Shift manager"));
			responsibilities.add(new Responsibility(6, "Checker"));
			responsibilities.add(new Responsibility(8, "Workshop 1 worker"));
			responsibilities.add(new Responsibility(9, "Workshop 2 worker"));
			responsibilities.add(new Responsibility(11, "Catalog maintainer"));
			responsibilities.add(new Responsibility(13, "Procurement worker"));
			responsibilities.add(new Responsibility(14, "System admin"));
			responsibilities.add(new Responsibility(15, "Technologist"));
			for (int i = 1; i <= 10; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user7@soberano.syscoop.co",
									"user7fn",
									"user7ln",
									"12345",
									"5355555557",
									"CU",
									"Calle user7, entre Y y Z",
									"10407",
									"Vedado7",
									1007,
									"La Habana7",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user8
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(3, "Accounter"));
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			responsibilities.add(new Responsibility(5, "Shift manager"));
			responsibilities.add(new Responsibility(6, "Checker"));
			responsibilities.add(new Responsibility(7, "Auditor"));
			responsibilities.add(new Responsibility(11, "Catalog maintainer"));
			responsibilities.add(new Responsibility(13, "Procurement worker"));
			responsibilities.add(new Responsibility(14, "System admin"));
			for (int i = 1; i <= 8; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user8@soberano.syscoop.co",
									"user8fn",
									"user8ln",
									"12345",
									"5355555558",
									"CU",
									"Calle user8, entre Y y Z",
									"10408",
									"Vedado8",
									1008,
									"La Habana8",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user9
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(2, "Manager"));
			responsibilities.add(new Responsibility(14, "System admin"));
			responsibilities.add(new Responsibility(15, "Technologist"));
			for (int i = 1; i <= 3; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user9@soberano.syscoop.co",
									"user9fn",
									"user9ln",
									"12345",
									"5355555559",
									"CU",
									"Calle user9, entre Y y Z",
									"10409",
									"Vedado9",
									1009,
									"La Habana9",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user10
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(2, "Manager"));
			responsibilities.add(new Responsibility(3, "Accounter"));
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			responsibilities.add(new Responsibility(5, "Shift manager"));
			responsibilities.add(new Responsibility(6, "Checker"));
			responsibilities.add(new Responsibility(9, "Workshop 2 worker"));
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			responsibilities.add(new Responsibility(11, "Catalog maintainer"));
			responsibilities.add(new Responsibility(12, "Community manager"));
			for (int i = 1; i <= 9; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user10@soberano.syscoop.co",
									"user10fn",
									"user10ln",
									"12345",
									"5355555510",
									"CU",
									"Calle user10, entre Y y Z",
									"10410",
									"Vedado10",
									1010,
									"La Habana10",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user11
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(2, "Manager"));
			responsibilities.add(new Responsibility(3, "Accounter"));
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			responsibilities.add(new Responsibility(5, "Shift manager"));
			responsibilities.add(new Responsibility(7, "Auditor"));
			responsibilities.add(new Responsibility(9, "Workshop 2 worker"));
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			responsibilities.add(new Responsibility(11, "Catalog maintainer"));
			responsibilities.add(new Responsibility(14, "System admin"));
			responsibilities.add(new Responsibility(15, "Technologist"));
			for (int i = 1; i <= 10; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user11@soberano.syscoop.co",
									"user11fn",
									"user11ln",
									"12345",
									"5355555511",
									"CU",
									"Calle user11, entre Y y Z",
									"10411",
									"Vedado11",
									1011,
									"La Habana11",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user12
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(2, "Manager"));
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			responsibilities.add(new Responsibility(7, "Auditor"));
			responsibilities.add(new Responsibility(8, "Workshop 1 worker"));
			responsibilities.add(new Responsibility(9, "Workshop 2 worker"));
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			responsibilities.add(new Responsibility(12, "Community manager"));
			responsibilities.add(new Responsibility(13, "Procurement worker"));
			for (int i = 1; i <= 8; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user12@soberano.syscoop.co",
									"user12fn",
									"user12ln",
									"12345",
									"5355555512",
									"CU",
									"Calle user12, entre Y y Z",
									"10412",
									"Vedado12",
									1012,
									"La Habana12",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user13
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(3, "Accounter"));
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			responsibilities.add(new Responsibility(6, "Checker"));
			responsibilities.add(new Responsibility(7, "Auditor"));
			responsibilities.add(new Responsibility(9, "Workshop 2 worker"));
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			responsibilities.add(new Responsibility(13, "Procurement worker"));
			responsibilities.add(new Responsibility(15, "Technologist"));
			for (int i = 1; i <= 8; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user13@soberano.syscoop.co",
									"user13fn",
									"user13ln",
									"12345",
									"5355555513",
									"CU",
									"Calle user13, entre Y y Z",
									"10413",
									"Vedado13",
									1013,
									"La Habana13",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user14
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(3, "Accounter"));
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			responsibilities.add(new Responsibility(6, "Checker"));
			responsibilities.add(new Responsibility(7, "Auditor"));
			responsibilities.add(new Responsibility(8, "Workshop 1 worker"));
			responsibilities.add(new Responsibility(11, "Catalog maintainer"));
			responsibilities.add(new Responsibility(12, "Community manager"));
			for (int i = 1; i <= 7; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user14@soberano.syscoop.co",
									"user14fn",
									"user14ln",
									"12345",
									"5355555514",
									"CU",
									"Calle user14, entre Y y Z",
									"10414",
									"Vedado14",
									1014,
									"La Habana14",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user15
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(2, "Manager"));
			responsibilities.add(new Responsibility(3, "Accounter"));
			responsibilities.add(new Responsibility(6, "Checker"));
			responsibilities.add(new Responsibility(7, "Auditor"));
			responsibilities.add(new Responsibility(8, "Workshop 1 worker"));
			responsibilities.add(new Responsibility(9, "Workshop 2 worker"));
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			responsibilities.add(new Responsibility(14, "System admin"));
			responsibilities.add(new Responsibility(15, "Technologist"));
			for (int i = 1; i <= 9; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user15@soberano.syscoop.co",
									"user15fn",
									"user15ln",
									"12345",
									"5355555515",
									"CU",
									"Calle user15, entre Y y Z",
									"10415",
									"Vedado15",
									1015,
									"La Habana15",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user16
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(2, "Manager"));
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			responsibilities.add(new Responsibility(5, "Shift manager"));
			responsibilities.add(new Responsibility(8, "Workshop 1 worker"));
			responsibilities.add(new Responsibility(11, "Catalog maintainer"));
			responsibilities.add(new Responsibility(12, "Community manager"));
			responsibilities.add(new Responsibility(14, "System admin"));
			for (int i = 1; i <= 7; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user16@soberano.syscoop.co",
									"user16fn",
									"user16ln",
									"12345",
									"5355555516",
									"CU",
									"Calle user16, entre Y y Z",
									"10416",
									"Vedado16",
									1016,
									"La Habana16",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user17
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(3, "Accounter"));
			responsibilities.add(new Responsibility(15, "Technologist"));
			for (int i = 1; i <= 2; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user17@soberano.syscoop.co",
									"user17fn",
									"user17ln",
									"12345",
									"5355555517",
									"CU",
									"Calle user17, entre Y y Z",
									"10416",
									"Vedado17",
									1017,
									"La Habana17",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user18
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user18@soberano.syscoop.co",
									"user18fn",
									"user18ln",
									"12345",
									"5355555518",
									"CU",
									"Calle user18, entre Y y Z",
									"10418",
									"Vedado18",
									1018,
									"La Habana18",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user19
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(5, "Shift manager"));
			responsibilities.add(new Responsibility(15, "Technologist"));
			for (int i = 1; i <= 2; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user19@soberano.syscoop.co",
									"user19fn",
									"user19ln",
									"12345",
									"5355555519",
									"CU",
									"Calle user19, entre Y y Z",
									"10419",
									"Vedado19",
									1019,
									"La Habana19",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user20
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(6, "Checker"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user20@soberano.syscoop.co",
									"user20fn",
									"user20ln",
									"12345",
									"5355555520",
									"CU",
									"Calle user20, entre Y y Z",
									"10420",
									"Vedado20",
									1020,
									"La Habana20",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
			
			//user21
			responsibilities.clear();
			authorities.clear();
			responsibilities.add(new Responsibility(14, "System admin"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			newWorker = new Worker(0,
									0,
									"user21@soberano.syscoop.co",
									"user21fn",
									"user21ln",
									"12345",
									"5355555521",
									"CU",
									"Calle user21, entre Y y Z",
									"10421",
									"Vedado21",
									1021,
									"La Habana21",
									2,
									0.0,
									0.0,
									responsibilities,
									authorities);
			try{newWorker.record();}catch(Exception ex){ex.printStackTrace();}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail("Unpexpected exception.");
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void test() {
		//fail("Not yet implemented"); // TODO
	}
}
