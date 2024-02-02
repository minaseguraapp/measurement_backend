package co.minasegura.measurement.handler.entrypoint;

import co.minasegura.measurement.handler.route.LambdaFunction;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.service.IMeasurementService;
import co.minasegura.measurement.util.CommonsUtil;
import co.minasegura.measurement.util.EntrypointUtil;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;

@Component
public class PostMeasurementLambda implements LambdaFunction {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);
    private final IMeasurementService measurementService;
    private final EntrypointUtil entrypointUtil;
    private final CommonsUtil commonsUtil;

    public PostMeasurementLambda(IMeasurementService measurementService, CommonsUtil commonsUtil, EntrypointUtil entrypointUtil) {
        this.measurementService = measurementService;
        this.commonsUtil = commonsUtil;
        this.entrypointUtil= entrypointUtil;
    }

    @Override
    public APIGatewayProxyResponseEvent handle(
        APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {
        LOGGER.info("POST Measurement API started");

        if (apiGatewayProxyRequestEvent.getBody() == null) {
            return new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
        }

        final Measurement measurementToRegister =
            commonsUtil.toObject(apiGatewayProxyRequestEvent.getBody(), Measurement.class);

        if(!entrypointUtil.isMeasurementValid(measurementToRegister)){
            return new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
        }

        boolean successRegister = measurementService.createMeasurement(measurementToRegister);
        LOGGER.info("POST Measurement API Finished");

        return new APIGatewayProxyResponseEvent().withStatusCode(
            successRegister ? HttpStatusCode.CREATED : HttpStatusCode.INTERNAL_SERVER_ERROR);

    }
}
