package com.cts.pas.policy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PolicyApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test 
	void testPolicyApplicationStarts() {
		PolicyApplication.main(new String[] {});
	}

}

