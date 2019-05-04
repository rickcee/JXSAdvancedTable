/**
 * 
 */
package net.rickcee.swingxs.demo.config;

import java.awt.Color;

import net.rickcee.swingxs.demo.model.Trade;
import net.rickcee.swingxs.model.meta.IFilterable;

/**
 * @author catalrc
 *
 */
public class TradeColorFilter implements IFilterable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rickcee.swingxs.model.meta.IFilterable#getColor(java.lang.Object)
	 */
	@Override
	public Color getColor(Object object) {
		Trade trade = (Trade) object;
		if ("USD".equals(trade.getTradeCurrency())) {
			return Color.GREEN;
		} else if ("ARS".equals(trade.getTradeCurrency())) {
			return Color.CYAN;
		} else if ("CAD".equals(trade.getTradeCurrency())) {
			return Color.RED;
		} else if ("MXP".equals(trade.getTradeCurrency())) {
			return Color.YELLOW;
		}
		return null;
	}

}
