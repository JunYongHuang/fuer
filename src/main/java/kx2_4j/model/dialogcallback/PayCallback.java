package kx2_4j.model.dialogcallback;

import java.util.Map;

public class PayCallback extends DialogCallback{
	private String orderid;	//订单号
	private String from;	//来源
	private int status;	//订单支付状态。1为支付成功
	private long pid;	//流水号，用于对账
	private int test;	//test值为非空或非零值，说明使用测试开心币进行的支付
	private int amount; //支付的开心币

	public PayCallback(Map<String,String[]> params)
	{
		super(params);
		orderid = params.get("orderid")[0];
		from = params.get("from")[0];
		status = Integer.parseInt(params.get("status")[0]);
		pid = Long.parseLong(params.get("pid")[0]);
		test = Integer.parseInt(params.get("test")[0]);
	}
	
	@Override
	public String toString()
	{
		return '{'
				+ "uid=" + uid
				+ ", ctime=" + ctime
				+ ", sig=" + sig
				+ ", callbackkey=" + callbackkey
				+ ", orderid=" + orderid
				+ ", from=" + from
				+ ", status=" + status
				+ ", pid=" + pid
				+ ", test=" + test
				+ ", amount=" + amount
				+ '}';
	}

	public String getOrderid()
	{
		return orderid;
	}
	public String getFrom()
	{
		return from;
	}
	public int getStatus()
	{
		return status;
	}
	public long getPid()
	{
		return pid;
	}
	public int getTest()
	{
		return test;
	}
	public int getAmount()
	{
		return amount;
	}
}