package com.galaxy.demo.payloads;

import com.galaxy.demo.error.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object data;
    private List<String> datas;

    public Result(){
        this.datas = new ArrayList<String>();
    }

    public static Result success() {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.code());
        result.setMessage("OK");
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.code());
        result.setMessage("OK");
        result.setData(data);
        return result;
    }

    public static Result error(Object data) {
        Result result = new Result();
        result.setCode(ResultCode.INTERNAL_SERVER_ERROR.code());
        result.setMessage("error");
        result.setData(data);
        return result;
    }
}
