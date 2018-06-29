package cn.edu.nju.software.service.feign;

import cn.edu.nju.software.command.python.ImageDataCommand;
import cn.edu.nju.software.command.python.PaintSubmitCommand;
import cn.edu.nju.software.dto.ImageDataDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${python_api.url}", name = "python")
public interface PythonFeign {
    // 提交修改图片数据
    //@PostMapping(value = "/custom/paint")
    //List<PaintSubmitData> paintSubmit(@RequestBody PaintSubmitCommand command);
    @PostMapping(value = "/custom/paint")
    String paintSubmit(@RequestBody PaintSubmitCommand command);

    @PostMapping(value = "/custom/thin")
    ImageDataDto processThin(@RequestBody ImageDataCommand command);

    @PostMapping(value = "/custom/fat")
    ImageDataDto processFat(@RequestBody ImageDataCommand command);
}
