package cn.pigknight.o2o.exceptions;

public class ShopOperationException extends RuntimeException {

    private static final long serialVersionUID = -9176506854922195181L;

    public ShopOperationException(String msg){
        super(msg);
    }
}
