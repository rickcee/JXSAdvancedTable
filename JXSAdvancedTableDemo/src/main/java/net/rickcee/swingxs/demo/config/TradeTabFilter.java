/**
 * 
 */
package net.rickcee.swingxs.demo.config;

import net.rickcee.swingxs.ITabFilter;
import net.rickcee.swingxs.demo.model.Trade;

/**
 * @author catalrc
 *
 */
public class TradeTabFilter implements ITabFilter {
	public static final String MODE_UNMATCHED = "Unmatched";
	public static final String MODE_MISMATCHED = "Mismatched";
	public static final String MODE_MATCHED = "Match Agreed";
	public static final String MODE_REJECTED = "Rejected";
	public static final String MODE_CANCELLED = "Cancelled";
	public static final String MODE_ERRORS = "Errors";
	
	/* (non-Javadoc)
	 * @see net.rickcee.swingxs.ITabFilter#getTabID(java.lang.Object)
	 */
	@Override
	public String getTabID(Object object) {	
		Trade trade = (Trade) object;
		if (trade.getQuantity() < 10) {
			return MODE_MISMATCHED;
		} else if (trade.getQuantity() < 100) {
			return MODE_MATCHED;
		} else if (trade.getQuantity() < 1000) {
			return MODE_REJECTED;
		} else if (trade.getQuantity() < 5000) {
			return MODE_CANCELLED;
		} else if (trade.getQuantity() < 7500) {
			return MODE_ERRORS;
		}
		return MODE_UNMATCHED;
	}

}
