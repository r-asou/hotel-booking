package exception;

/**
 * @author JC Liu
 * @date 2022/1/16$
 */
public class BusinessException extends Exception {

    public BusinessException() {
    }

    public BusinessException(String msg) {
        super(msg);
    }
}
