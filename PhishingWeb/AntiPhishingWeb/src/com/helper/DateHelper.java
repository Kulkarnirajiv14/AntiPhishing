package com.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
	public static void main(String[] args) {
		System.out.println(stringToDate("1999-07-15"));
	}

	public static Date stringToDate(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = formatter.parse(date);

			return d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date();

		// String strDate = formatter.format(date);
		// String returnDate = "";
		// String[] finalDate = date.split(":");
		// if (finalDate[1].equalsIgnoreCase("Jan")) {
		// returnDate = "01/" + finalDate[0] + "/" + "2019";
		// } else if (finalDate[1].equalsIgnoreCase("Feb")) {
		// returnDate = "02/" + finalDate[0] + "/" + "2019";
		// } else if (finalDate[1].equalsIgnoreCase("Mar")) {
		// returnDate = "03/" + finalDate[0] + "/" + "2019";
		// } else if (finalDate[1].equalsIgnoreCase("Apr")) {
		// returnDate = "04/" + finalDate[0] + "/" + "2019";
		// } else if (finalDate[1].equalsIgnoreCase("May")) {
		// returnDate = "05/" + finalDate[0] + "/" + "2019";
		// } else if (finalDate[1].equalsIgnoreCase("Jun")) {
		// returnDate = "06/" + finalDate[0] + "/" + "2019";
		// } else if (finalDate[1].equalsIgnoreCase("Jul")) {
		// returnDate = "07/" + finalDate[0] + "/" + "2019";
		// } else if (finalDate[1].equalsIgnoreCase("Aug")) {
		// returnDate = "08/" + finalDate[0] + "/" + "2019";
		// } else if (finalDate[1].equalsIgnoreCase("Sep")) {
		// returnDate = "09/" + finalDate[0] + "/" + "2019";
		// } else if (finalDate[1].equalsIgnoreCase("Oct")) {
		// returnDate = "10/" + finalDate[0] + "/" + "2019";
		// } else if (finalDate[1].equalsIgnoreCase("Nov")) {
		// returnDate = "11/" + finalDate[0] + "/" + "2019";
		// } else if (finalDate[1].equalsIgnoreCase("Dec")) {
		// returnDate = "12/" + finalDate[0] + "/" + "2019";
		// }
		// return returnDate;
	}
}
