package co.minasegura.measurement.configuration;

import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.service.filtering.FilterOperator;
import co.minasegura.measurement.service.impl.filtering.ZoneTypeFilterOperator;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.EnumMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public EnumMap<MeasurementFilter, FilterOperator> getFilterOperator(){
        final var operators= new EnumMap<MeasurementFilter, FilterOperator>(MeasurementFilter.class);

        operators.put(MeasurementFilter.ZONE_TYPE, new ZoneTypeFilterOperator());
        return operators;
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
            .build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB);
    }
}
