package com.qding.bigdata.ds.model;

import com.qding.bigdata.ds.util.BeanMapper;
import com.qding.bigdata.ds.util.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

import static org.springframework.beans.BeanUtils.*;

/**
 * @author: Yanxiaowei
 * @date: 2019/8/13 下午8:23
 */
@Data
public class GCTBaseV2Param {

    @ApiModelProperty(hidden = true)
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @ApiModelProperty(hidden = true)
    private final DateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

    @ApiModelProperty(value = "业务类型", example = "1丁管家|0千丁APP")
    protected String businessType;
    @ApiModelProperty(value = "时间类型", example = "day|month|year")
    protected String dateType;
    @ApiModelProperty(value = "开始日期", example = "2019-01-25")
    protected Date startDate;
    @ApiModelProperty(value = "结束日期", example = "2019-01-25")
    protected Date endDate;
    @ApiModelProperty(value = "iOS|Android", example = "all")
    protected String deviceType;
    @ApiModelProperty(value = "版本", example = "1.0")
    protected String version;
    @ApiModelProperty(value = "物业公司", example = "20190225185941043f4")
    protected String companyId;
    @ApiModelProperty(value = "分公司", example = "000001")
    public String QRYcompanyId;


    @ApiModelProperty(value = "页面ID", example = "000001")
    protected String pageId;

    @ApiModelProperty(value = "事件ID", example = "000001")
    protected String eventId;

    public String getStartDateStr() {
        return dateFormat.format(startDate);
    }

    public String getStartMonthStr() {
        return monthFormat.format(startDate);
    }

    public String getEndDateStr() {
        return dateFormat.format(endDate);
    }

    public String getEndMonthStr() {
        return monthFormat.format(endDate);
    }

    @ApiModelProperty(hidden = true)
    private String month;
    @ApiModelProperty(hidden = true)
    private String day;

    @ApiModelProperty(hidden = true)
    private String hour;

    public void setHour(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        this.hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        this.day = (String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
        this.month = (String.format("%02d", calendar.get(Calendar.MONTH) + 1));
    }

    public static <T extends GCTBaseV2Param> T getDayBefore(T param) {
        GCTBaseV2Param another = BeanMapper.map(param, param.getClass());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(param.getStartDate());
        calendar.add(Calendar.DATE, -1);
        another.setStartDate(calendar.getTime());
        another.setDay(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
        another.setMonth(String.format("%02d", calendar.get(Calendar.MONTH) + 1));
        return (T) another;
    }

    public static <T extends GCTBaseV2Param> T getPreviousDay(T param){
        GCTBaseV2Param newParam = BeanMapper.map(param, param.getClass());
        copyProperties(param,newParam);
        final long betweenDayNum = DateUtil.getBetweenDayNum(param.getStartDateStr(), param.getEndDateStr())+1;
        newParam.setStartDate(DateUtil.getDateByNDay(param.getStartDate(),-Integer.parseInt(String.valueOf(betweenDayNum))));
        newParam.setEndDate(DateUtil.getDateByNDay(param.getEndDate(),-Integer.parseInt(String.valueOf(betweenDayNum))));
        return (T)newParam;
    }
    public static <T extends GCTBaseV2Param> T getPreviousMonth(T param){
        GCTBaseV2Param newParam = BeanMapper.map(param, param.getClass());
        copyProperties(param,newParam);
        final long betweenMonthNum = DateUtil.getMonthDistance(param.getStartDate(), param.getEndDate())+1;
        newParam.setStartDate(DateUtil.getDateByNMonth(param.getStartDate(),-Integer.parseInt(String.valueOf(betweenMonthNum))));
        newParam.setEndDate(DateUtil.getDateByNMonth(param.getEndDate(),-Integer.parseInt(String.valueOf(betweenMonthNum))));
        return (T)newParam;
    }

    /**
     * 获取指定月 起始日期和结束日期
     * @param param
     * @param <T>
     * @return
     */
    public static <T extends GCTBaseV2Param> T getAvailableDayInMonth(T param){
        GCTBaseV2Param newParam = BeanMapper.map(param, param.getClass());
        copyProperties(param,newParam);
        //当月截止时间取到月末
        LocalDate startDate = LocalDate.parse(param.getStartDateStr());
        LocalDate endDate = LocalDate.parse(param.getEndDateStr());
        if(DateUtil.isThisTime(param.getStartDate().getTime(),"yyyy-MM")){
            startDate = startDate.with(TemporalAdjusters.firstDayOfMonth());
            endDate = LocalDate.now();
        }else{
            endDate = endDate.with(TemporalAdjusters.lastDayOfMonth());

        }
        newParam.setStartDate(DateUtil.localDateTime2Date(startDate));
        newParam.setEndDate(DateUtil.localDateTime2Date(endDate));
        return (T) newParam;
    }


}
