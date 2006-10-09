package david.free.lottery;

public class NumberException extends Exception
	{
	int row=0;

	private NumberException()
		{
		super();
		}

	public NumberException(int row,String message)
		{
		super(message);
		this.row=row;
		}

	}
