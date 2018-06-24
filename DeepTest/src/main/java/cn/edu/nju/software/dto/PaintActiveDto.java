package cn.edu.nju.software.dto;

import cn.edu.nju.software.data.ActiveData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaintActiveDto {
    private ActiveData standard;
    private List<ActiveData> mutations;
}
