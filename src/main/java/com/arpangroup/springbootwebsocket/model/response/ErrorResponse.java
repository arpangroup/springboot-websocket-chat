package com.arpangroup.springbootwebsocket.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Error Response Model")
public class ErrorResponse implements Serializable {
    private static final Long serialVersionUID = 1L;

    @ApiModelProperty(notes = "Error Code", name = "code", value = "200")
    private int code;
    @ApiModelProperty(notes = "Status", name = "message", value = "SUCCESS")
    private String status;
    @ApiModelProperty(notes = "message", name = "message", value = "Invalid field")
    private String message;
}
