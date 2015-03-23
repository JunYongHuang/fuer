package kx2_4j.model;

public class PagingWithTotal {
	public int total;
	public int prev;
	public int next;
	
	public String toString()
	{
		return "total:" + total
				+ ", prev:" + prev
				+ ", next:" + next
				;
	}
}
