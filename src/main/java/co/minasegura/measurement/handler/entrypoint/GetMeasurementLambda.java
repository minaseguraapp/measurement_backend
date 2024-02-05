package co.minasegura.measurement.handler.entrypoint;

import co.minasegura.measurement.dto.GetMeasurementResponse;
import co.minasegura.measurement.dto.MeasurementQueryFilter;
import co.minasegura.measurement.handler.route.LambdaFunction;
import co.minasegura.measurement.service.IMeasurementService;
import co.minasegura.measurement.util.CommonsUtil;
import co.minasegura.measurement.util.EntrypointUtil;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.util.EnumMap;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;

@Component
public class GetMeasurementLambda implements LambdaFunction {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);
    private final IMeasurementService measurementService;
    private final EntrypointUtil util;
    private final CommonsUtil commonsUtil;

    public GetMeasurementLambda(IMeasurementService measurementService, EntrypointUtil util,
        CommonsUtil commonsUtil) {
        this.measurementService = measurementService;
        this.util = util;
        this.commonsUtil = commonsUtil;
    }

    @Override
    public APIGatewayProxyResponseEvent handle(
        APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {
        LOGGER.info("GET Measurement API started");

        if (apiGatewayProxyRequestEvent.getQueryStringParameters() == null) {
            return new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
        }

        EnumMap<MeasurementQueryFilter, String> searchCriteria = util.getMeasurementFilter(
            apiGatewayProxyRequestEvent.getQueryStringParameters());

        if (!util.hasRequestMinimumCriteria(searchCriteria)) {
            return new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
        }

        GetMeasurementResponse response = measurementService.getMeasurements(searchCriteria);

        Optional<String> jsonResponse = Optional.ofNullable(commonsUtil.toJson(response));
        LOGGER.info("GET Measurement API Finished");

        return jsonResponse
            .map(json -> new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.OK)
                .withBody(json))
            .orElse(new APIGatewayProxyResponseEvent().withStatusCode(
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    }
}
