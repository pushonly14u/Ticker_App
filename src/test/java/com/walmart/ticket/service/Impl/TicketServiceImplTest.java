package com.walmart.ticket.service.Impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.walmart.ticket.model.SeatHold;
import com.walmart.ticket.model.Venue;

public class TicketServiceImplTest {
	private TicketServiceImpl Myservice;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Myservice = new TicketServiceImpl(new Venue(1,1), 2);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void numSeatsAvailable() throws InterruptedException{
		int no = Myservice.numSeatsAvailable();
		assert(no == 1);
		
	}
	
	@Test
	public void findAndHoldSeats() throws InterruptedException{
		SeatHold s1 = Myservice.findAndHoldSeats(1, "xyz@abc.com");
		assertNotNull(s1);
		assert(1 == s1.getSeatsHeld().size());
		s1 = Myservice.findAndHoldSeats(1, "test@test.com");
		assert(null == s1);
		Thread.sleep(3000);
		s1 = Myservice.findAndHoldSeats(1, "test@test.com");
		assertNotNull(s1);
		assert(1 == s1.getSeatsHeld().size());
		Thread.sleep(3000);
		s1 = Myservice.findAndHoldSeats(2, "test@test.com");
		assert(null == s1);
	}
	
	@Test
	public void reserveSeats() throws InterruptedException{
		SeatHold s1 = Myservice.findAndHoldSeats(1, "test@test.com");
		String conf = Myservice.reserveSeats(s1.getId(), "test@test.com");
		assertNotNull(conf);
		assertTrue(conf.contains("reserved!"));
		conf = Myservice.reserveSeats(0, "test@test.com");
		assert(null == conf);
	}
	
	
}
