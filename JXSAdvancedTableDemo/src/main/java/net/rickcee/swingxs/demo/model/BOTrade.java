package net.rickcee.swingxs.demo.model;

import java.util.Calendar;

/**
 * Created on Jan 24, 2012
 */

/**
 * @author RickCeeNet
 * 
 */
public class BOTrade {

	static String[] customer = { "IBKRUS33XXX", "BLAKUS31XXX", "CITIUS33CHI", "RBOSUS3GXXX", "ROYCUS3XIBF", "ROALUS31XXX", "ACOLUS66XXX",
			"ALYIUS33INC", "BARCUS33BDE", "BEARUS33XXX" };
	static Integer[] entities = { 1, 2, 3, 4 };
	static String[] currency = { "USD", "CAD", "GBP", "EUR", "ARS", "BRL", "IRP", "CHP", "URP", "MXP" };
	static String[] cusip = { "14149YAR9", "126650BG4", "254709AC2", "437076AQ5", "441060AG5", "50075NAN4",
			"574599BE5", "617446B99", "637640AC7", "713291AL6", "852061AE0", "887317AA3", "925524BF6", "125509BG3",
			"125896AV2" };
	static String[] cusipDesc = { "Cardinal Health Inc", "CVS Caremark Corp", "Discover Finl Services",
			"Home Depot Inc", "Hospira Inc", "Kraft Foods Inc", "Masco Corp", "Morgan Stanley", "Natl Semicon Corp",
			"Pepco Hldgs Inc", "Sprint Nextel Corp", "Time Warner Inc", "Viacom", "Cigna Corp", "CMS Engy Corp" };

	public static Trade generate(Class<?> clazz) {
		Trade trade;
		trade = new Trade();

		// Random for Cusip
		Integer cusipIndex = Double.valueOf((Math.random() * 12)).intValue();
		trade.setCusip(cusip[cusipIndex]);
		trade.setIsin("US" + cusip[cusipIndex] + 0);
		trade.setNotes(cusipDesc[cusipIndex]);

		// Random for Customer
		Integer customerIndex = Double.valueOf((Math.random() * 10)).intValue();
		trade.setCustomerId(customer[customerIndex]);

		// Random for Qty, Price, Principal
		trade.setPrice(Math.random() * 100);
		trade.setQuantity(Double.valueOf(Math.random() * 10000).longValue());
		trade.setPrincipal(Double.valueOf(trade.getPrice() * trade.getQuantity()).longValue());

		// Random for Currency Trade
		Integer currencyTradeIndex = Double.valueOf((Math.random() * 10)).intValue();
		trade.setTradeCurrency(currency[currencyTradeIndex]);

		// Random for Currency Settle
		Integer currencySettleIndex = Double.valueOf((Math.random() * 10)).intValue();
		trade.setSettlementCurrency(currency[currencySettleIndex]);

		// Random value for B/S Code
		Integer bsIndex = Double.valueOf((Math.random() * 10)).intValue();
		trade.setBuySellCode(bsIndex >= 5 ? "B" : "S");

		// Random value for Trade Date
		Integer tradeDateIndex = Double.valueOf((Math.random() * 10)).intValue();
		Calendar td = Calendar.getInstance();
		td.add(Calendar.DATE, -tradeDateIndex);
		td.set(Calendar.HOUR, 0);
		td.set(Calendar.MINUTE, 0);
		td.set(Calendar.SECOND, 0);
		td.set(Calendar.MILLISECOND, 0);
		trade.setTradeDate(td.getTime());

		// Random value for Settle Date based on Trade Date + SD Index
		Integer sdIndex = new Double(3 + Math.random() * 10).intValue();
		Calendar sd = (Calendar) td.clone();
		sd.add(Calendar.DATE, sdIndex);
		trade.setSettlementDate(sd.getTime());

		// Random for Entity ID
		Integer entityIndex = Double.valueOf((Math.random() * 4)).intValue();
		trade.setEntityId(entities[entityIndex]);

		// Random for Transaction Number
		trade.setTransactionNumber("T" + currencySettleIndex + currencyTradeIndex + cusipIndex + customerIndex
				+ tradeDateIndex + entityIndex);

		// System.out.println(trade.getTradeCurrency());
		return trade;
	}

	public static void main(String argsp[]) {
		BOTrade.generate(null);
	}
	
}
