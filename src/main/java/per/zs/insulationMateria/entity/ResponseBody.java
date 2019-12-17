package per.zs.insulationMateria.entity;
/** 
* @author zs 
* @version 创建时间�?2019�?7�?5�? 上午11:15:32 
* @Description 返回信息实体�?
*/
public class ResponseBody {

    private int code;
    private String message;

    public ResponseBody(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "ResponseBody{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
