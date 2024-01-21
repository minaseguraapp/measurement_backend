package co.minasegura.measurement.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.util.function.Function;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;

@Component

public class GetAllMeasurementsFunction implements
    Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Logger logger = Logger.getLogger(GetAllMeasurementsFunction.class);

    @Override
    public APIGatewayProxyResponseEvent apply(
        APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {

        logger.info("New Request Started");
        System.out.println("New Request Started V2");
        return new APIGatewayProxyResponseEvent()
            .withStatusCode(HttpStatusCode.OK).withBody("Hola Mundo");
    }
}
