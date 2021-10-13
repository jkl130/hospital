package com.anjsh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author sfturing
 * @date 2017年5月22日
 */
@Data
@TableName("order_records")
public class OrderRecords {
    //预约id
    @TableId(type = IdType.AUTO)
    private Integer id;
    //用户ID
    @TableField("userID")
    private int userID;
    //预约医院名称
    private String hospitalName;
    //预约科室名称
    private String officesName;
    //医生姓名
    private String doctorName;
    //预约日期
    private String transactDate;
    //预约时间段
    private String transactTime;
    //疾病信息
    private String diseaseInfo;
    //是否成功
    private int isSuccess;
    //是否发送邮件
    private int isSend;
    //是否取消
    private int isCancel;
    //是否完成订单
    private int isFinish;
    //预约识别码
    private int orderVer;
    //创建预约时间
    private Timestamp createTime;
}
