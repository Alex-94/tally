package com.alex.top.tally.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("alex_user")
public class AlexUserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private String userName;
	private String password;
	private String passwordsalt;
	private String openId;
	private Integer companyId;
	private Integer userType;
	private Date createTime;
	private Integer userState;

}
