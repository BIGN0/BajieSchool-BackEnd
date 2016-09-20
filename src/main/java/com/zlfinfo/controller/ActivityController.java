package com.zlfinfo.controller;

import com.alibaba.druid.sql.visitor.functions.If;
import com.zlfinfo.commons.base.BaseController;
import com.zlfinfo.model.*;
import com.zlfinfo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/8/22.
 */
@Controller
public class ActivityController extends BaseController {

    @Autowired
    private ActivityService actService;
    @Autowired
    private ActivityTypeService activityTypeService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private LoginStatusService loginStatusService;

    /**
     * 发布活动
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/addact", method = RequestMethod.POST)
    @ResponseBody
    public Object addact(@RequestParam(required = false) Integer actId, @RequestParam(required = false) Integer actType,
                         @RequestParam String actTitle, @RequestParam(required = false) String actContent, @RequestParam(required = false) String actImg,
                         @RequestParam(required = false) Date actTime, @RequestParam(required = false) String actPlace, @RequestParam String username, HttpServletResponse response) {
        Activity act = new Activity(actId, actType, actTitle, actContent, actImg, actTime, actPlace);
        Integer actid = actService.insert(act);
        if (actid == 0) {
            return renderError("保存失败", response);
        } else {
            UserActivity userActivity = new UserActivity();
            userActivity.setActId(actid);
            userActivity.setUsername(username);
            userActivity.setLaunFlag(0);
            actService.insertuserActivity(userActivity);
            return renderSuccess("保存成功", response);
        }
    }

    /**
     * 查询所有活动
     *
     * @param httpServletResponse
     * @return
     * @deprecated
     */
    @Deprecated
    @RequestMapping(value = "/allact", method = RequestMethod.GET)
    @ResponseBody
    public Object showAllActivity(HttpServletResponse httpServletResponse) {
        List<Activity> activityList = actService.selectAllActivity();
        if (null != activityList) {
            return renderSuccess(activityList, httpServletResponse);
        } else {
            return renderError("活动查询失败", httpServletResponse);
        }
    }

    @RequestMapping(value = "/activity", method = RequestMethod.POST)
    @ResponseBody
    public Object showActivity(@RequestParam String username, @RequestParam Integer type, HttpServletResponse
            httpServletResponse) {
        List<Activity> activityList = actService.selectActivityByUserNType(type);
        return null != activityList ? renderSuccess(activityList, httpServletResponse) : renderError("活动查询失败",
                httpServletResponse);
    }
    @RequestMapping(value = "/activityALL", method = RequestMethod.POST)
    @ResponseBody
    public Object showActivityALL(@RequestParam String username, @RequestParam Integer type, HttpServletResponse
            httpServletResponse) {
        Map resultMap=new HashMap();
        int ls  = loginStatusService.selectStatusByUsername(username);
            if(ls==0){
                List<Activity> activityList = actService.selectActivityByUserNType(type);
                List<ActivityType> activityTypeList = activityTypeService.selectAllActType();
                List<Banner> bannerList = bannerService.selectAllBanner();
                resultMap.put("Activity",activityList);
                resultMap.put("ActivityType",activityTypeList);
                resultMap.put("Banner",bannerList);
                return null != activityList ? renderSuccess(resultMap, httpServletResponse) : renderError("活动查询失败",
                        httpServletResponse);
            }else{
                return  renderError("用户状态为登出", httpServletResponse);
            }
    }

    @RequestMapping(value = "/activity/launchedact", method = RequestMethod.GET)
    @ResponseBody
    public Object showMineAct(String username, HttpServletResponse
            httpServletResponse) {
        List<Activity> activityList = actService.selectMineActivity(username, 0);
        return null != activityList ? renderSuccess(activityList, httpServletResponse) : renderError("发起活动列表查询失败",
                httpServletResponse);
    }
    @RequestMapping(value = "/activity/mine", method = RequestMethod.GET)
    @ResponseBody
    public Object selectMyActivity(@RequestParam String username, @RequestParam Integer type,  HttpServletResponse
            httpServletResponse) {
        List<Activity> publishedList = actService.selectMineActivity(username, type);
        return null != publishedList ? renderSuccess(publishedList, httpServletResponse) : renderError("发起活动列表查询失败",
                httpServletResponse);
    }
    @RequestMapping(value = "/activity/updlike", method = RequestMethod.POST)
    @ResponseBody
    public Object updlike(@RequestParam Integer id, HttpServletResponse
            httpServletResponse) {
        System.out.println(id);
       Activity act = new Activity();
        act = actService.selectByPrimaryKey(id);
        if(null!=act){
            act.setActId(id);
            act.setActLike(act.getActLike()+1);
            //已关注
            act.setReserve1("1");
            actService.updateByPrimaryKeySelective(act);
            return renderSuccess("更新成功", httpServletResponse);
        }else{
            return renderError("更新失败", httpServletResponse);
        }
    }
}
