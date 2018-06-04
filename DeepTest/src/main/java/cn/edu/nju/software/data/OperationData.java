package cn.edu.nju.software.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户提交记录的数据
 * 用于进行用户限制的一些操作
 * 如限时查看等功能
 */
@Document(collection = "operation_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationData {
}
