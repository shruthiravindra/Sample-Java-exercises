package receiver.result;

import java.io.Serializable;

// Result DataType
public class ResultMessage  implements Serializable{
    private static final long serialVersionUID = -1622448588567287171L;
    private int number ;
    private boolean isPrime;

    public ResultMessage(int number, boolean isPrime){
        this.number = number;
        this.isPrime = isPrime;
    }

    public int getNumber() {
        return number;
    }


    public boolean isPrime() {
        return isPrime;
    }

    @Override
    public String toString()
    {
        return this.number + " : " + this.isPrime;
    }
}
