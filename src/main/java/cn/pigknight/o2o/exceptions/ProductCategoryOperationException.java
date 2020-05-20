package cn.pigknight.o2o.exceptions;

public class ProductCategoryOperationException extends RuntimeException {
    private static final long serialVersionUID = 4264996486390840545L;

    public ProductCategoryOperationException(String msg){
        super(msg);
    }
}
