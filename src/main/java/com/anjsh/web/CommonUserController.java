package com.anjsh.web;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.anjsh.entity.CommonUser;
import com.anjsh.entity.Favourite;
import com.anjsh.entity.Hospital;
import com.anjsh.entity.OrderRecords;
import com.anjsh.service.CommonUserService;
import com.anjsh.service.HospitalService;
import com.anjsh.service.OrderRecordsService;
import com.anjsh.exception.BizException;
import com.anjsh.utils.JwtTokenUtils;
import com.anjsh.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.anjsh.dao.FavouriteDao;


/**
 * @author sfturing
 * @date 2017年5月6日
 */
@RestController
@RequestMapping("user")
public class CommonUserController {
    @Autowired
    private CommonUserService commonUserService;
    @Autowired
    private OrderRecordsService orderRecordsService;
    @Autowired
    private FavouriteDao favouriteDao;
    @Autowired
    private HospitalService hospitalService;


    private static Logger log = LoggerFactory.getLogger(CommonUserController.class);


    /**
     * 用户登陆验证
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody Map<String, String> dataMap, HttpServletRequest request) {
        // 登录用户，并将登录后的状态码返回，如果是0用户不存在，如果是1那么密码错误，如果是2那么密码正确
        CommonUser user = commonUserService.login(dataMap.get("email").trim(), dataMap.get("password"), request);
        Map<String, Object> payload = new HashMap<>(4);
        payload.put("userInfo", user);
        return JwtTokenUtils.generateTokenUser(UUID.randomUUID().toString(), payload, 604800000L);
    }

    @GetMapping("/refreshToken")
    public String refreshToken() {
        CommonUser commonUser = UserContext.getCommonUser();
        Map<String, Object> payload = new HashMap<>(4);
        payload.put("userInfo", commonUserService.getById(commonUser.getUserId()));
        return JwtTokenUtils.generateTokenUser(UUID.randomUUID().toString(), payload, 604800000L);
    }


    /**
     * 用户注册
     *
     * @param commonUser
     * @param request
     * @return
     */
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public void sign(@RequestBody CommonUser commonUser, HttpServletRequest request) {
        // 注册用户: 0:用户身份证号已注册 1:用户邮箱已注册 2:用户手机号已注册 3:用户注册成功
        //将输入邮箱去除空格
        commonUser.setUserEmail(commonUser.getUserEmail().trim());
        commonUserService.sign(commonUser, request);
        sendMail(commonUser, 0);

    }

    /**
     * 用户修改手机
     */
    @RequestMapping(value = "/modifiPhone", method = RequestMethod.POST)
    public void modifiPhone(String userMobile) {
        commonUserService.modifyPhone(userMobile, UserContext.getCommonUser().getUserEmail());
    }

    private void sendMail(CommonUser commonUser, int i) {
        new Thread(() -> {
            boolean isSuccess = commonUserService.sendEmailCheck(commonUser);
            if (isSuccess) {
                log.info(commonUser.getUserEmail() + "发送成功" + commonUser.getUpdateTime());
                commonUser.setStatus(i);
                commonUserService.updateById(commonUser);
            } else {
                log.info(commonUser.getUserEmail() + "发送失败" + commonUser.getUpdateTime());
            }
        }).start();
    }

    /**
     * 用户找回密码
     *
     * @return
     */
    @RequestMapping(value = "/findPassword", method = RequestMethod.POST)
    public void findPassword(final String userEmail) {

        // 错误信息
        // 通过输入的身份证查找用户
        final CommonUser commonUser = commonUserService.findCommonUserByEmail(userEmail);
        // 用户不存在返回找回密码页面
        if (commonUser == null) {
            throw new BizException("用户不存在，请检查后输入");
        }

        if (commonUserService.findHeadway(commonUser.getUpdateTime()) < 300) {
            throw new BizException("5分钟内只能发送一封邮件");
        }

        // 单独开启线程发送邮件，防止用户等待时间过长，成功日志输出，失败也输出。
        sendMail(commonUser, 0);
    }

    /**
     * 检查验证码界面 0：验证码超时 1：验证码验证通过 2：验证码验证失败
     *
     * @return
     */
    @RequestMapping(value = "/checkVerification", method = RequestMethod.POST)
    public void checkVerification(int verificationCode) {
        CommonUser commonUser = UserContext.getCommonUser();
        commonUserService.checkVerification(verificationCode, commonUser);
        commonUser.setStatus(1);
        commonUserService.updateById(commonUser);
    }

    /**
     * 用户更改密码
     *
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public void updatePassword(String newPassWord) {
        CommonUser commonUser = UserContext.getCommonUser();
        if (!commonUserService.modifyPassWord(commonUser.getUserEmail(), newPassWord)) {
            throw new BizException("密码修改失败");
        }
    }


    /**
     * 用户完善信息
     */
    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST)
    public void addUserInfo(String userIdenf, String userName, String userMobile, String userSex, HttpServletRequest request) {
        CommonUser commonUser = UserContext.getCommonUser();
        commonUserService.addUserInfo(commonUser.getUserEmail(), userIdenf, userName, userMobile, userSex);
    }

    /**
     * 用户个人中心
     */
    /**
     * @return
     */
    @RequestMapping(value = "/userCenter", method = RequestMethod.GET)
    public Map<String, Object> userCenter() {
        CommonUser commonUser = UserContext.getCommonUser();
        Map<String, Object> model = new HashMap<>();
        // 得到用户的收藏记录
        List<Favourite> favourites = favouriteDao.findFavHos(commonUser.getUserId());
        List<Hospital> hospitals;
        if (favourites.size() != 0) {
            model.put("hospitals", hospitalService.findFavHos(favourites));
        }
        // 得到用户的个人订单
        List<OrderRecords> orderRecords = orderRecordsService.findOrderRecordsByUserID(commonUser.getUserId());
        model.put("orderRecords", orderRecords);
        return model;
    }


    /**
     * 用户更改性别
     *
     * @return
     */
    @RequestMapping(value = "/updateSex", method = RequestMethod.POST)
    public void updateSex(String userSex) {
        CommonUser commonUser = UserContext.getCommonUser();
        int userId = commonUser.getUserId();
        commonUserService.modifySex(userId, userSex);
    }

}
