package co.minasegura.measurement.handler;

import co.minasegura.measurement.dto.GetMeasurementResponse;
import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.service.IMeasurementService;
import co.minasegura.measurement.util.EntrypointUtil;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;

@Component
public class GetAllMeasurementsFunction implements
    Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);
    private final IMeasurementService measurementService;
    private final EntrypointUtil util;

    public GetAllMeasurementsFunction(IMeasurementService measurementService, EntrypointUtil util) {
        this.measurementService = measurementService;
        this.util = util;
    }

    @Override
    public APIGatewayProxyResponseEvent apply(
        APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {

        LOGGER.info("GET Measurement API started");

        if (apiGatewayProxyRequestEvent.getQueryStringParameters() == null) {
            return new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
        }

        EnumMap<MeasurementFilter, String> searchCriteria = util.getMeasurementFilter(
            apiGatewayProxyRequestEvent.getQueryStringParameters());

        if (!util.hasRequestMinimumCriteria(searchCriteria)) {
            new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
        }
        GetMeasurementResponse response = measurementService.getMeasurements(searchCriteria);

        Optional<String> jsonResponse = Optional.ofNullable(util.toJson(response));
        LOGGER.info("GET Measurement API Finished");

        return jsonResponse
            .map(json -> new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.OK)
                .withBody(json))
            .orElse(new APIGatewayProxyResponseEvent().withStatusCode(
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    }
}
