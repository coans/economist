package com.economist.util;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.economist.service.AgencijaService;
import com.economist.service.PreduzeceService;

@Component
public class FakturaCron {

	@Autowired
	private AgencijaService agencijaService;
	@Autowired
	private PreduzeceService preduzeceService;
	
	@Scheduled(cron="0 * * ? * *")
	//"0 0 12 1 * *" means 12:00 PM on the first day of every month.
	public synchronized void generate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		System.out.println("qq.."/*sdf.format(new Date())*/);
	}
}
