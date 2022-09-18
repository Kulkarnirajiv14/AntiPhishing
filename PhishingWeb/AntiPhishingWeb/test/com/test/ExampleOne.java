package com.test;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Properties;

import javax.naming.*;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

/**
 */
public class ExampleOne {
	public static void main(String[] args) throws UnknownHostException,
			TextParseException {

		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.dns.DnsContextFactory");
		InitialDirContext idc;
		try {
			idc = new InitialDirContext(env);

			Attributes attrs = idc.getAttributes("www.technowingsinc.com",
					new String[] { "A","CNAME","" });
			Attribute attr = attrs.get("A");
			System.out.println(attr.get());
			return;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String ipAddress = "207.174.215.236";
		String ipAddress = "236.215.174.207";
		String dnsblDomain = "sbl-xbl.technowingsinc.com";

		Lookup lookup = new Lookup(ipAddress + "." + dnsblDomain, Type.ANY);
		Resolver resolver = new SimpleResolver();
		lookup.setResolver(resolver);
		lookup.setCache(null);
		Record[] records = lookup.run();
		if (lookup.getResult() == Lookup.SUCCESSFUL) {
			String responseMessage = null;
			String listingType = null;
			for (int i = 0; i < records.length; i++) {
				if (records[i] instanceof TXTRecord) {
					TXTRecord txt = (TXTRecord) records[i];
					for (Iterator j = txt.getStrings().iterator(); j.hasNext();) {
						responseMessage += (String) j.next();
					}
				} else if (records[i] instanceof ARecord) {
					listingType = ((ARecord) records[i]).getAddress()
							.getHostAddress();
				}
			}
			System.err.println("Found!");
			System.err.println("Response Message: " + responseMessage);
			System.err.println("Listing Type: " + listingType);
		} else if (lookup.getResult() == Lookup.HOST_NOT_FOUND) {
			System.err.println("Not found.");
		} else {
			System.err.println("Error!");
		}
	}
}