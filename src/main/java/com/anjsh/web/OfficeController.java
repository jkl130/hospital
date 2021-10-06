package com.anjsh.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anjsh.dto.OfficeInfoDTO;
import com.anjsh.dto.OrderHosPageQuery;
import com.anjsh.dto.OrderOfficePageQuery;
import com.anjsh.entity.CommonCondition;
import com.anjsh.entity.Doctor;
import com.anjsh.entity.Hospital;
import com.anjsh.entity.Office;
import com.anjsh.service.DoctorService;
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

@Controller
public class OfficeController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private PageUtils pageUtils;
    @Autowired
    private DoctorService doctorService;


    @GetMapping("recOffice")
    public List<Office> recOffice() {
        return officeService.list(Wrappers.lambdaQuery(Office.class).gt(Office::getRec, 0));
    }

//    /**
//     * 科室主界面(推荐科室)
//     *
//     * @return
//     */
//    @RequestMapping(value = "/officeIndex/{page}")
//    public String officeIdex(Model model, @PathVariable("page") int page) {
//        // 查询推荐的医院
//        List<Hospital> hospitalRe = hospitalService.findHosByRe();
//        // 设置页面
//        pageUtils.setCurrentPage(page);
//        pageUtils.setTotalRecord(officeService.findOfficeByReNum(hospitalRe));
//        int start;
//        if (pageUtils.getCurrentPage() == 0) {
//            start = 0;
//        } else {
//            start = pageUtils.getPageRecord() * (pageUtils.getCurrentPage() - 1);
//        }
//        Map<String, Object> officeMap = new HashMap<String, Object>();
//        officeMap.put("list", hospitalRe);
//        officeMap.put("start", start);
//        officeMap.put("size", 20);
//        List<Office> officeRe = officeService.findOfficeByRe(officeMap);
//        model.addAttribute("pages", pageUtils);
//        model.addAttribute("officeRe", officeRe);
//        return "office/officeIndex";
//    }

    /**
     * 科室详情
     *
     * @return
     */
    @RequestMapping(value = "/officeInfoShow/{id}", method = RequestMethod.GET)
    public OfficeInfoDTO officeInfoShow(@PathVariable(value = "id") int id) {
        Office office = officeService.findOfficeById(id);
        Hospital hospital = hospitalService.findHosByName(office.getHospitalName());
        List<Doctor> doctor = doctorService.findAreaByHosAndOfficeName(office.getHospitalName(),
                office.getOfficesName());
        OfficeInfoDTO officeInfoDTO = new OfficeInfoDTO();
        officeInfoDTO.setOffice(office);
        officeInfoDTO.setHospital(hospital);
        officeInfoDTO.setDoctor(doctor);
        return officeInfoDTO;
    }


    @PostMapping("/orderOffice")
    public IPage<Office> orderOffice(@RequestBody OrderOfficePageQuery pageQuery) {
        return officeService.page(new Page<>(pageQuery.getPageIndex(), pageQuery.getPageSize()), Wrappers.lambdaQuery(Office.class)
                .likeRight(pageQuery.getHospitalName() != null, Office::getHospitalName, pageQuery.getHospitalName())
                .likeRight(pageQuery.getOfficesName() != null, Office::getOfficesName, pageQuery.getOfficesName()));
    }

//    /**
//     * 全部科室
//     *
//     * @return
//     */
//    @RequestMapping(value = "/orderOffice/{page}")
//    public String orderOffcie(Model model, @PathVariable("page") int page, @ModelAttribute("province") String province,
//                              @ModelAttribute("city") String city, @ModelAttribute("district") String district, Office office) {
//        // 将输入条件传回前台
//        CommonCondition commonCondition = new CommonCondition();
//        commonCondition.setHospitalName(office.getHospitalName());
//        commonCondition.setOfficesName(office.getOfficesName());
//        // 设置页面
//        pageUtils.setCurrentPage(page);
//        pageUtils.setTotalRecord(officeService.findOrderOfficeNum(office));
//        int start;
//        if (pageUtils.getCurrentPage() == 0) {
//            start = 0;
//        } else {
//            start = pageUtils.getPageRecord() * (pageUtils.getCurrentPage() - 1);
//        }
//        List<Office> officeRe = officeService.findOfficeByConditon(office, start, 20);
//        model.addAttribute("pages", pageUtils);
//        model.addAttribute("officeRe", officeRe);
//        // 查询条件
//        model.addAttribute("commonCondition", commonCondition);
//        return "office/orderOffice";
//    }

}
