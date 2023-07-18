package dev.rvz.generate.log;

import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.core.JsonProcessingException;
import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import dev.rvz.generate.log.model.ErrorAppData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;


public class GenerateLog implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Logger LOGGER = LogManager.getLogger(GenerateLog.class);


    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,
                                                      final Context context) {


        final ObjectMapper objectMapper = new ObjectMapper();
        final ErrorAppData errorAppData = getErrorAppDataDeserialized(apiGatewayProxyRequestEvent, objectMapper);

        ThreadContext.put("xpto", "valor");
        LOGGER.info("error {}", errorAppData);

        ThreadContext.clearAll();
        final APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();

        return apiGatewayProxyResponseEvent.withStatusCode(200).withBody("Ok");
    }

    private ErrorAppData getErrorAppDataDeserialized(
            final APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,
            final ObjectMapper objectMapper) {
        try {

            return objectMapper
                    .readValue(apiGatewayProxyRequestEvent.getBody(),
                            ErrorAppData.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("ocorreu error ao deserializar a requisicao. message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
