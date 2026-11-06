package game;

public class Sequence {
	int start;
	int end;
	char ch;
	int length;
	
	public Sequence() {}
	
	public Sequence(int start, int end, char ch) {
		this.start=start;
		this.end=end;
		this.ch=ch;
		this.length=this.end-start + 1;
		
	}
}