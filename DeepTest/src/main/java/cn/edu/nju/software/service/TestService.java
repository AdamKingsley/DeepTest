package cn.edu.nju.software.service;

import cn.edu.nju.software.command.PaintCommand;
import cn.edu.nju.software.dao.*;
import cn.edu.nju.software.data.*;
import cn.edu.nju.software.data.mutation.DelModelData;
import cn.edu.nju.software.data.mutation.MutationData;
import cn.edu.nju.software.data.mutation.NeuronData;
import cn.edu.nju.software.dto.ActiveDto;
import cn.edu.nju.software.dto.PaintSubmitDto;
import cn.edu.nju.software.service.score.ScoreStrategyContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mengf on 2018/6/3 0003.
 */
@Service
public class TestService {
    @Autowired
    private MongoTemplate template;
    @Autowired
    private SubmitCountDao submitCountDao;
    @Autowired
    private ExamScoreDao examScoreDao;
    @Autowired
    private ExamDao examDao;
    @Autowired
    private CaseDao caseDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private ScoreStrategyContext strategy;

    public List<PaintSubmitDto> getScoresTest() {
        PaintCommand paintCommand = new PaintCommand();
        paintCommand.setUserId("123");
        paintCommand.setExamId(3L);
        //调用python接口跑模型 获取模型运行的结果
        List<PaintSubmitData> submit_datas = Lists.newArrayList();
        //添加mock数据
        PaintSubmitData data1 = new PaintSubmitData();
        data1.setExamId(3L);
        data1.setUserId("123");
        data1.setIsKilled(true);
        data1.setScore(96.49);
        data1.setModelId(158L);
        data1.setImageId(523L);
        PaintSubmitData data2 = new PaintSubmitData();
        data2.setExamId(3L);
        data2.setUserId("123");
        data2.setIsKilled(true);
        data2.setScore(96.50);
        data2.setModelId(26L);
        data2.setImageId(1083L);
        submit_datas.add(data1);
        submit_datas.add(data2);
        //获取last_submit_time
        Date submitTime = submit_datas.get(0).getSubmitTime();
        //count++
        //submitCountDao.updateCount(paintCommand.getExamId(), paintCommand.getUserId(), submitTime);
        ExamScoreData scoreData = examScoreDao.getExamScore(paintCommand.getExamId(), paintCommand.getUserId());
        //获取之前保存的考试成绩的data 注意空指针异常
        //List<Long> killedModelIds = scoreData == null || scoreData.getKilledModelIds() == null ?
        //        Lists.newArrayList() : scoreData.getKilledModelIds();
        List<MseScoreData> mseDatas = scoreData == null || scoreData.getKilledDetail() == null ?
                Lists.newArrayList() : scoreData.getKilledDetail();
        //将之前已经杀死的数据构建出一个map
        Map<Long, MseScoreData> killedMap = Maps.newConcurrentMap();
        mseDatas.forEach(mse -> killedMap.put(mse.getModelId(), mse));
        mseDatas = getKilledDetails(submit_datas, killedMap);
        //更新成绩表
        Double score = calScoreForPaintExam(paintCommand.getExamId(), paintCommand.getUserId(), mseDatas);
        examScoreDao.updateScore(paintCommand.getExamId(), paintCommand.getUserId(), score, mseDatas);
        List<PaintSubmitDto> dtos = Lists.newArrayList();
        submit_datas.forEach(submitData -> {
            PaintSubmitDto dto = new PaintSubmitDto();
            BeanUtils.copyProperties(submitData, dto);
            dtos.add(dto);
        });
        return dtos;
    }

    private Double calScoreForPaintExam(Long examId, String userId, List<MseScoreData> killedDetails) {
        //数据准备
        //考试中总共的model的个数
        List<Long> modelIds = examDao.getModelIds(examId);
        Long totalNum = (long) modelIds.size();
        List<Long> killedModelIds = Lists.newArrayList();
        List<Double> killedMseScore = Lists.newArrayList();
        killedDetails.forEach(detail -> {
            killedModelIds.add(detail.getModelId());
            killedMseScore.add(detail.getScore());
        });
        //提交的次数
        Long count = submitCountDao.getCount(examId, userId);

        //计算第二种考试的成绩
        Double score = strategy.calculateScore("calScoreForPaintSample", (long) killedModelIds.size(), totalNum, count, killedMseScore);
        return score;
    }

    private List<MseScoreData> getKilledDetails(List<PaintSubmitData> submit_datas, Map<Long, MseScoreData> killedMap) {
        //整合数据返回
        submit_datas.forEach(submitData -> {
            //只有本次提交杀死了模型才能计入成绩
            if (submitData.getIsKilled()) {
                MseScoreData killedDetail = killedMap.get(submitData.getModelId());
                //如果之前已经杀死了就比较mse成绩哪家强  强者留下来
                if (killedDetail != null) {
                    if (killedDetail.getScore() < submitData.getScore()) {
                        MseScoreData mseScoreData = new MseScoreData();
                        BeanUtils.copyProperties(submitData, mseScoreData);
                        killedMap.put(submitData.getModelId(), mseScoreData);
                    }
                } else {
                    //如果之前没有杀死的话，保留信息
                    //killedModelIds.add(submitData.getModelId());
                    MseScoreData mseScoreData = new MseScoreData();
                    BeanUtils.copyProperties(submitData, mseScoreData);
                    //mseDatas.add(mseScoreData);
                    killedMap.put(mseScoreData.getModelId(), mseScoreData);
                }
            }
        });
        List<MseScoreData> mseScoreDataList = Lists.newArrayList();
        killedMap.values().forEach(mseScoreData -> mseScoreDataList.add(mseScoreData));
        return mseScoreDataList;
    }


    public List<ActiveDto> getSamples() {
        long image_id = 0;
        List<ActiveDto> datas = Lists.newArrayList();
        for (int i = 0; i < 500; i++) {
            ActiveDto dto = new ActiveDto();
            image_id = i * 20;
            System.out.println("the " + i + " round");
            Query query = new Query(Criteria.where("_id").is(image_id));
            ActiveData standard = template.findOne(query, ActiveData.class, "standard_model");
            ActiveData mutation = template.findOne(query, ActiveData.class, "del_neuron_1_0_model");
            dto.setImageId(image_id);
            dto.setStandard(standard);
            dto.setMutation(mutation);
            datas.add(dto);
        }
        File file = new File("compare.json");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(JSON.toJSONString(datas));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datas;
    }

    // 预存储image数据
    public void loadImageDataFromJson() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("D:/imagedata.json"));
            String text = IOUtils.toString(inputStream, "utf8");
            JSONArray array = JSON.parseArray(text);
            List<ImageData> datas = Lists.newArrayList();
            for (int i = 0; i < array.size(); i++) {
                System.out.println(i);
                Long id = array.getJSONObject(i).getLong("_id");
                List<Integer> tags = array.getJSONObject(i).getObject("tag", new TypeReference<List<Integer>>() {
                });
                List<Tag> tagList = Lists.newArrayList();
                tags.forEach(num -> tagList.add(new Tag("number", num)));
                ImageData data = array.getObject(i, ImageData.class);
                data.setId(id);
                data.setTags(tagList);
                data.setName(data.getTags().get(0).getValue() + "_" + data.getId());
                datas.add(data);
            }
            //打印样本图片总张数并批量插入
            System.out.println(datas.size());
            template.insertAll(datas);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 预存储 model数据
    public void insertModelData() {
        List<MutationData> datas = Lists.newArrayList();
        long count = 1;
        for (int i = 0; i < 128; i++) {
            List<NeuronData> neuronDatas = Lists.newArrayList();
            neuronDatas.add(new NeuronData(1, i));
            DelModelData data = new DelModelData();
            data.setId(count);
            data.setName("del_neuron_1_" + i);
            data.setDataCollection(data.getName() + "_model");
            data.setType(0);
            data.setDeleteNuerons(neuronDatas);
            data.setPath("del_neuron_model_1_" + i + ".hdf5");
            datas.add(data);
            count++;
        }
        for (int i = 0; i < 64; i++) {
            List<NeuronData> neuronDatas = Lists.newArrayList();
            neuronDatas.add(new NeuronData(2, i));
            DelModelData data = new DelModelData();
            data.setId(count);
            data.setName("del_neuron_2_" + i);
            data.setDataCollection(data.getName() + "_model");
            data.setType(0);
            data.setDeleteNuerons(neuronDatas);
            data.setPath("del_neuron_model_2_" + i + ".hdf5");
            datas.add(data);
            count++;
        }
        System.out.println("共有" + datas.size() + "个变异模型！");
        template.insertAll(datas);
    }

    public void resave_avtive_data() {
        for (int i = 0; i < 128; i++) {
            List<ActiveData> datas = template.findAll(ActiveData.class, "del_neuron_1_" + i + "_model");
        }
        for (int i = 0; i < 64; i++) {

        }
    }

    public void insertCases(List<CaseData> datas) {
        datas.forEach(data -> {
            ImageData imagedata = imageDao.findById(data.getImageId());
            data.setPath(imagedata.getPath());
        });
        caseDao.insertMany(datas);
    }
}
