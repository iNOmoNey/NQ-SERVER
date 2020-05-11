package top.theanything.util;

import org.json.JSONObject;

public class JsonUtil {

    public static  String convert(Object object) {
        JSONObject jsonObject = new JSONObject(object);
        return jsonObject.toString();
    }

    public enum ResultGenerator {

        OK(200,"ok"),


        FORBIDDEN(403, "缺权限，操作被禁止"),


        ERROR(500, "服务器发生无法预期的错误，请联系管理员"),


        NOT_FOUND(404, "资源未找到"),


        TOKEN_NOT_FOUND(401,"token错误或已过期"),


        LACK_TOKEN(401, "缺乏token"),


        ERROR_URL(400, "请求url或相关参数出错，请参照文档进行请求")
        ;

        private int code;
        private String msg;

        ResultGenerator(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "ResultGenerator{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    '}';
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
