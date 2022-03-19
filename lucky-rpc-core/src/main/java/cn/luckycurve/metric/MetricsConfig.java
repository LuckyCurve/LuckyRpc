package cn.luckycurve.metric;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LuckyCurve
 */
@Configuration
public class MetricsConfig implements ApplicationContextAware {

    @Bean
    public MetricRegistry registry() {
        return new MetricRegistry();
    }

    /**
     * 开启Console和JMX监控
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        final MetricRegistry registry = applicationContext.getBean(MetricRegistry.class);

        final Histogram cpuUsed = registry.histogram("cpu used");
        cpuUsed.update((int) HardwareInfo.cpuUsed());

        final Histogram memoryUsed = registry.histogram("memory used");
        memoryUsed.update((int) HardwareInfo.memoryUsed());

        //final ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(registry)
        //        .build();
        //consoleReporter.start(5, 5, TimeUnit.SECONDS);

        final JmxReporter jmxReporter = JmxReporter.forRegistry(registry).build();
        jmxReporter.start();
    }
}
