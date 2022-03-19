package cn.luckycurve.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LuckyCurve
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageHeader {
    private Integer opCode;
    /**
     * if streamId < 0, this request is get computer status
     */
    private Long streamId;
}
