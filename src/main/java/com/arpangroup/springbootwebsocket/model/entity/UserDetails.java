package com.arpangroup.springbootwebsocket.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "UserDetails Model")
public class UserDetails {
    @Id
    @ApiModelProperty(notes = "ID of the user", name = "id", required = true, value = "1234")
    private Long id;
    @ApiModelProperty(notes = "Name of the user", name = "name", required = true, value = "John")
    private String name;
    @ApiModelProperty(notes = "ID of the user", name = "id", required = true, value = "http://jsonplaceholder/img/1")
    private String profileImage;
}
