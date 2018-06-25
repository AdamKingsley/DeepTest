package cn.edu.nju.software.service.feign;

import cn.edu.nju.software.command.python.PaintSubmitCommand;
import cn.edu.nju.software.dto.PaintSubmitDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(url = "127.0.0.1:5000", name = "python")
public interface PythonFeign {
    // 提交修改图片数据
    @PostMapping(value = "/custom/paint")
    List<PaintSubmitDto> paintSubmit(@RequestBody PaintSubmitCommand command);

}
