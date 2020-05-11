package top.theanything.web.response;


import top.theanything.util.JsonUtil;

public class Result<T>{
    //TODO 通用返回
    private T  data;
    JsonUtil.ResultGenerator resultGenerator;

    public Result(T data, JsonUtil.ResultGenerator resultGenerator) {
        this.data = data;
        this.resultGenerator = resultGenerator;
    }

    public Result( ){
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public JsonUtil.ResultGenerator getResultGenerator() {
        return resultGenerator;
    }

    public void setResultGenerator(JsonUtil.ResultGenerator resultGenerator) {
        this.resultGenerator = resultGenerator;
    }

    public String toString() {
        return JsonUtil.convert(this);
    }
}