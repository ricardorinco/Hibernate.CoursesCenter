package com.coursescenter.helper;

import java.time.LocalDate;
import java.util.Random;

public class Aleatory
{
	private static Random random = new Random();
	
	public Integer getInteger(Integer minimum, Integer maximum)
	{
		return random.nextInt((maximum - minimum) + 1) + minimum;
	}
	
	public LocalDate getDateTime(Integer minimum, Integer maximum)
	{
		int minDay = (int) LocalDate.of(minimum, 1, 1).toEpochDay();
		int maxDay = (int) LocalDate.of(maximum, 1, 1).toEpochDay();
		long randomDay = minDay + random.nextInt(maxDay - minDay);
		
		return LocalDate.ofEpochDay(randomDay);
	}
}