package co.minasegura.measurement.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "measuremente.exception.messages")
public class MeasurementExceptionProperties {

    private String mineParamNotPresent;

    public String getMineParamNotPresentMessage() {
        return mineParamNotPresent;
    }

    public void setMineParamNotPresent(String mineParamNotPresent) {
        this.mineParamNotPresent = mineParamNotPresent;
    }
}
