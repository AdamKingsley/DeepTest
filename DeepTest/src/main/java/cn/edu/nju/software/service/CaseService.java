package cn.edu.nju.software.service;

import cn.edu.nju.software.common.exception.ServiceException;
import cn.edu.nju.software.dao.CaseDao;
import cn.edu.nju.software.data.CaseData;
import cn.edu.nju.software.dto.CaseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaseService {

    @Autowired
    private CaseDao caseDao;

    /**
     * 通过examId和caseId进行数据的更新操作
     *
     * @param examId
     * @param caseId
     * @return
     */
    public CaseDto getCaseDto(Long examId, String caseId) {
        CaseDto caseDto = new CaseDto();
        CaseData caseData = caseDao.getCaseData(examId, caseId);
        if (caseData == null) {
            throw new ServiceException("获取题目数据失败！没有该题目信息！");
        }
        BeanUtils.copyProperties(caseData, caseDto);
        return caseDto;
    }
}
