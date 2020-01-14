package application;

import java.util.ArrayList;

public class RecordList {

	private ArrayList<Record> list;
	
	public RecordList() {
		list = new ArrayList<Record>();
	}
	
	
	
	public ArrayList<Record> getList() {
		return list;
	}
	public void setList2(ArrayList<Record> b) {
		
			this.list = b;
		
			
	}
	public void setList(Record b) {
		
			list.add(b);
			
		
			
	}







}
