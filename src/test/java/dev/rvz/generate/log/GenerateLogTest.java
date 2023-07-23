package dev.rvz.generate.log;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.tests.annotations.Event;
import com.amazonaws.services.lambda.runtime.tests.annotations.Events;
import com.amazonaws.services.lambda.runtime.tests.annotations.HandlerParams;
import com.amazonaws.services.lambda.runtime.tests.annotations.Response;
import com.amazonaws.services.lambda.runtime.tests.annotations.Responses;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;

class GenerateLogTest {

    @ParameterizedTest
    @HandlerParams(
            events = @Events(
                    events = {
                        @Event("apigtw/events/apigtw_event.json")
                    },
                    type = APIGatewayProxyRequestEvent.class
            ),
            responses = @Responses(
                    responses = {
                        @Response("apigtw/events/apigtw_event_response_ok.json")
                    },
                    type = APIGatewayProxyResponseEvent.class
            )
    )
    void testInvokeWithSucess(final APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,
                              final APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent) {
        final GenerateLog generateLog = new GenerateLog();
        final APIGatewayProxyResponseEvent apiGatewayProxyResponseEventResult = generateLog
                .handleRequest(apiGatewayProxyRequestEvent, null);

        Assertions.assertNotNull(apiGatewayProxyResponseEvent);
        Assertions.assertEquals(apiGatewayProxyResponseEvent.getStatusCode(),
                apiGatewayProxyResponseEventResult.getStatusCode());
        Assertions.assertEquals(apiGatewayProxyResponseEvent.getBody(),
                apiGatewayProxyResponseEventResult.getBody());

    }
}