package co.minasegura.measurement.configuration;

import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.handler.entrypoint.GetMeasurementLambda;
import co.minasegura.measurement.handler.entrypoint.PostMeasurementLambda;
import co.minasegura.measurement.handler.route.LambdaFunction;
import co.minasegura.measurement.handler.route.Route;
import co.minasegura.measurement.service.FilterOperator;
import co.minasegura.measurement.service.impl.filter.ZoneTypeFilterOperator;
import com.amazonaws.HttpMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public EnumMap<MeasurementFilter, FilterOperator> getFilterOperator() {
        final var operators = new EnumMap<MeasurementFilter, FilterOperator>(
            MeasurementFilter.class);

        operators.put(MeasurementFilter.ZONE_TYPE, new ZoneTypeFilterOperator());
        return operators;
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
            .build();

        return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();
    }

    @Bean
    public Map<Route, LambdaFunction> getRouter(GetMeasurementLambda getMeasurementLambda, PostMeasurementLambda postMeasurementLambda){
        Map<Route, LambdaFunction> routerConfig= new HashMap<>();

        routerConfig.put(new Route(HttpMethod.GET, "/measurement"), getMeasurementLambda);
        routerConfig.put(new Route(HttpMethod.POST, "/measurement"), postMeasurementLambda);

        return routerConfig;
    }


}
