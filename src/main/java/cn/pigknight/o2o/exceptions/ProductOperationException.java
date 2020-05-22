package cn.pigknight.o2o.exceptions;

public class ProductOperationException extends RuntimeException {

    private static final long serialVersionUID = -1240003671249259074L;

    public ProductOperationException(String msg) {
        super(msg);
    }
}
