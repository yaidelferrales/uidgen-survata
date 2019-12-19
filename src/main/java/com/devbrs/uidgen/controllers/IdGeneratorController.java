package com.devbrs.uidgen.controllers;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Date;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newId")
public class IdGeneratorController {
	
	private String pad(String number, int length) {
		while (number.length() < length) {
			number = "0" + number;
		}
		
		return number;
	}
	
	private String getIpAddressWithoutDotsAndBase36() {
		String ipAddress = null;
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
		} catch (Exception e1) {}
		
		String [] ipParts = ipAddress.split("\\.");
		
		String result = "";
		for (int i = 0; i < ipParts.length; i++) {
			int tmp = Integer.parseInt(ipParts[i]);
			String tmpBase36 = Integer.toString(tmp, 36);
			
			//Add to the result the padded number
			result += pad(tmpBase36, 2);
		}
		
		return result;
	}
	
	private static Long getProcessId(final Long fallback) {
	    final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
	    final int index = jvmName.indexOf('@');

	    if (index < 1) {
	        return fallback;
	    }

	    try {
	    	return Long.parseLong(jvmName.substring(0, index));
	    } catch (NumberFormatException e) {
	        // ignore
	    }
	    
	    return fallback;
	}
	
	@RequestMapping("/{namespace}")
	public String generateId(@PathVariable String namespace) {		
		/*
		 * generate 4 random integers for the unique id generation and convert them into base 36
		 */
		String randomNumber1 = Integer.toString((int)(Math.random() * 1000), 36);
		String randomNumber2 = Integer.toString((int)(Math.random() * 1000), 36);
		String randomNumber3 = Integer.toString((int)(Math.random() * 1000), 36);
		String randomNumber4 = Integer.toString((int)(Math.random() * 1000), 36);
		
		/*
		 * Add pad to the generated numbers so all have the same length
		 */
		randomNumber1 = pad(randomNumber1, 2);
		randomNumber2 = pad(randomNumber2, 2);
		randomNumber3 = pad(randomNumber3, 2);
		randomNumber4 = pad(randomNumber4, 2);
		
		/*
		 * get current timestamp
		 */
		Date now = new Date();
		String timestamp = Long.toString(now.getTime(), 36);
		
		/*
		 * get the computer Ip Address
		 */
		String ipAddress = getIpAddressWithoutDotsAndBase36();
		
		/*
		 * get the process id
		 */		
		Long pId = getProcessId(null);
		
		if (pId == null) {
			pId = (long)(Math.random() * 100000);
		}
		
		String processId = pad(Long.toString(pId, 36), 3);
		
		String uniqueId = randomNumber1 + timestamp + randomNumber2 + ipAddress + randomNumber3 + processId + randomNumber4;
		
		return namespace + "-" + uniqueId;
	}
	
}
