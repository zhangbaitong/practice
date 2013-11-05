package po;

import java.math.BigDecimal;

public class CsvOrder {
	
	public String Date;
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public BigDecimal getIncome() {
		return Income;
	}
	public void setIncome(BigDecimal income) {
		Income = income;
	}
	public String getTradeNo() {
		return TradeNo;
	}
	public void setTradeNo(String tradeNo) {
		TradeNo = tradeNo;
	}
	public String getTrader() {
		return Trader;
	}
	public void setTrader(String trader) {
		Trader = trader;
	}
	public BigDecimal Income;
	public String TradeNo;
	public String Trader;

}
