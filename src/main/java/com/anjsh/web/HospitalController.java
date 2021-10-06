package com.anjsh.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.anjsh.dto.HosInfoDTO;
import com.anjsh.dto.OrderHosPageQuery;
import com.anjsh.entity.*;
import com.anjsh.service.HospitalService;
import com.anjsh.service.OfficeService;
import com.anjsh.utils.PageUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.anjsh.dao.FavouriteDao;

/**
 * @author sfturing
 * @date 2017年5月19日
 */
@RestController
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private PageUtils pageUtils;
    @Autowired
    private FavouriteDao favouriteDao;

//    /**
//     * 医院主界面(推荐医院)
//     *
//     * @return
//     */
//    @RequestMapping(value = "/hosIndex", method = RequestMethod.GET)
//    public String hosIdex(Model model) {
//        List<Hospital> hospitalRe = hospitalService.findHosByRe();
//        model.addAttribute("hospital", hospitalRe);
//        return "hospital/hosIndex";
//    }

    /**
     * 医院详情
     *
     * @return
     */
    @RequestMapping(value = "/hosInfoShow/{id}", method = RequestMethod.GET)
    public HosInfoDTO hosInfoShow(@PathVariable(value = "id") int id, HttpServletRequest request) {
        Object userInfo = request.getAttribute("userInfo");
        HosInfoDTO hosInfoDTO = new HosInfoDTO();
        if (userInfo != null) {
            //如果用户登录
            Optional.ofNullable(favouriteDao.findFavByuserIdAndHosId(((CommonUser) userInfo).getUserId(), id)).map(favourite -> favourite.getIsLike() > 0).ifPresent(hosInfoDTO::setLike);
        }

        // 通过传入的id返回医院的详细信息
        Hospital hospital = hospitalService.findHosById(id);
        hosInfoDTO.setHospital(hospital);
        // 通过医院的名称返回医院科室信息
        hosInfoDTO.setOffice(officeService.findOfficeByHosName(hospital.getHospitalName()));
        // 预留通知查询

        return hosInfoDTO;
    }

    @PostMapping("/orderHos")
    public IPage<Hospital> orderHos(@RequestBody OrderHosPageQuery pageQuery) {
        return hospitalService.page(new Page<>(pageQuery.getPageIndex(), pageQuery.getPageSize()), Wrappers.lambdaQuery(Hospital.class)
                .eq(Hospital::getIsOpen, 1)
                .eq(pageQuery.getHospitalNature() != null, Hospital::getHospitalNature, pageQuery.getHospitalNature())
                .eq(pageQuery.getHospitalGrade() != null, Hospital::getHospitalGrade, pageQuery.getHospitalGrade())
                .likeRight(pageQuery.getHospitalName() != null, Hospital::getHospitalName, pageQuery.getHospitalName())
                .likeRight(pageQuery.getProvince() != null, Hospital::getHospitalAddress, pageQuery.getProvince())
                .like(pageQuery.getCity() != null, Hospital::getHospitalAddress, pageQuery.getCity())
                .eq(pageQuery.getDistrict() != null, Hospital::getHospitalArea, pageQuery.getDistrict())
        );
    }


    //关注医院
    @ResponseBody
    @RequestMapping(value = "/favourite", method = RequestMethod.POST)
    public Map<String, Object> favourite(int hospitalId, HttpSession session) {
        //通过session信息得到userid
        CommonUser commonUser = (CommonUser) session.getAttribute("userInfo");
        int userId = commonUser.getUserId();
        int isLike = hospitalService.favourite(userId, hospitalId);
        System.out.println(isLike + "*******************************88");
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("isLike", isLike);
        return rtnMap;
    }

}
