package co.minasegura.measurement.handler;

import co.minasegura.measurement.handler.route.LambdaFunction;
import co.minasegura.measurement.handler.route.Route;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;

@Component
public class MeasurementLambdaFunction implements
    Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final Map<Route, LambdaFunction> router;

    public MeasurementLambdaFunction(Map<Route, LambdaFunction> router) {
        this.router = router;
    }

    @Override
    public APIGatewayProxyResponseEvent apply(
        APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {

        Route route = buildRouter(apiGatewayProxyRequestEvent);

        LambdaFunction lambdaToExecute = this.router.get(route);

        if (lambdaToExecute == null) {
            return new APIGatewayProxyResponseEvent().withStatusCode(
                HttpStatusCode.INTERNAL_SERVER_ERROR);
        }

        return lambdaToExecute.handle(apiGatewayProxyRequestEvent);
    }

    private Route buildRouter(
        APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {

        String httpMethod = apiGatewayProxyRequestEvent.getHttpMethod();
        String path = apiGatewayProxyRequestEvent.getPath();

        HttpMethod method = HttpMethod.valueOf(httpMethod);

        return new Route(method, path);

    }


}
