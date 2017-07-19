/**
 * Created by Darpan Shah Jul 19, 2017
 */
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
/**
 * @author DARPAN
 *
 */
public class TicketServiceImplTest {
	private TicketServiceImpl service;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		service = new TicketServiceImpl(new Venue(1,1), 5);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void numSeatsAvailable() throws InterruptedException{
		int no = service.numSeatsAvailable();
		assert(no == 1);
		service = new TicketServiceImpl(new Venue(2,3), 5);
		no = service.numSeatsAvailable();
		assert(no == (2*3));
		service.findAndHoldSeats(2, "darpan@dp.com");
		no = service.numSeatsAvailable();
		assert(no == ((2*3)-2));
		Thread.sleep(6000); // default expire time is 5s. prior hold should be gone. (Timing is check on the scale of seconds)
		System.out.println("After waiting: " + service.numSeatsAvailable());
		assert((2*3) == service.numSeatsAvailable());
		service.findAndHoldSeats((2*3), "darpan@dp.com");
		assert(0 == service.numSeatsAvailable());
		Thread.sleep(6000); // default expire time is 5s. prior hold should be gone.
		assert((2*3) == service.numSeatsAvailable());
		SeatHold sh = service.findAndHoldSeats((2*3), "darpan@dp.com");
		no = service.numSeatsAvailable();
		service.reserveSeats(sh.getId(), "darpan@dp.com");
		assert(no == service.numSeatsAvailable()); // reserving seats should not change no of seats. (assuming we don`t pass expiry)
		
		service = new TicketServiceImpl(new Venue(2,3), 5);
		sh = service.findAndHoldSeats((2*3), "darpan@dp.com");
		no = service.numSeatsAvailable();
		Thread.sleep(6000);
		service.reserveSeats(sh.getId(), "darpan@dp.com");
		assert((no + sh.getSeatsHeld().size()) == service.numSeatsAvailable()); // After expiration, all held seats should be available.
	}
	
	@Test
	public void findAndHoldSeats() throws InterruptedException{
		SeatHold s1 = service.findAndHoldSeats(1, "xyz@abc.com");
		assertNotNull(s1);
		assert(1 == s1.getSeatsHeld().size());
		s1 = service.findAndHoldSeats(1, "xyz@abc.com");
		assert(null == s1);
		Thread.sleep(6000);
		s1 = service.findAndHoldSeats(1, "xyz@abc.com");
		assertNotNull(s1);
		assert(1 == s1.getSeatsHeld().size());
		Thread.sleep(6000);
		s1 = service.findAndHoldSeats(2, "xyz@abc.com");
		assert(null == s1);
	}
	
	@Test
	public void reserveSeats() throws InterruptedException{
		SeatHold s1 = service.findAndHoldSeats(1, "xyz@abc.com");
		String conf = service.reserveSeats(s1.getId(), "xyz@abc.com");
		assertNotNull(conf);
		assertTrue(conf.contains("reserved!"));
		conf = service.reserveSeats(0, "xyz@abc.com");
		assert(null == conf);
	}
	
	
}
