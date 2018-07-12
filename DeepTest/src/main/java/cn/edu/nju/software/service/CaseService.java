package cn.edu.nju.software.service;

import cn.edu.nju.software.common.exception.ServiceException;
import cn.edu.nju.software.dao.CaseDao;
import cn.edu.nju.software.data.UserCaseData;
import cn.edu.nju.software.dto.CaseDto;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public CaseDto getCaseDto(Long examId, String userId, String caseId) {
        CaseDto caseDto = new CaseDto();
        UserCaseData caseData = caseDao.getUserCaseData(examId, userId, caseId);
        if (caseData == null) {
            throw new ServiceException("获取题目数据失败！没有该题目信息！");
        }
        BeanUtils.copyProperties(caseData, caseDto);
        return caseDto;
    }

    /**
     * 通过examId获取本次考试所有图片的信息
     *
     * @param examId
     * @return
     */
    @Transactional
    public List<CaseDto> getCaseDtos(Long examId, String userId) {
        List<CaseDto> caseDtos = Lists.newArrayList();
        List<UserCaseData> caseDatas = caseDao.getUserCaseDatas(examId, userId);
        if (caseDatas == null) {
            throw new ServiceException("获取考试题目数据失败！该考试没有题目！");
        }
        caseDatas.forEach(caseData -> {
            CaseDto dto = new CaseDto();
            BeanUtils.copyProperties(caseData, dto);
            caseDtos.add(dto);
        });
        return caseDtos;
    }
}
