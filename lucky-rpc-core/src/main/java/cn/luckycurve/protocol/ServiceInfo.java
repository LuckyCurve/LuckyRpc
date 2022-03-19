package cn.luckycurve.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LuckyCurve
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInfo {

    private String version;

    private String interfaceName;

    private List<String> methods;
}
