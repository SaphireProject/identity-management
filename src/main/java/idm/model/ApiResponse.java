package idm.model;


/**
 * ApiResponse class for generate answer on request
 *
 */
public class ApiResponse<T> {

    public ApiResponse() {
    }

    public ApiResponse(Object result) {
        this.result = result;
    }

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
