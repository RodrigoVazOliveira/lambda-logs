package dev.rvz.generate.log;

import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.core.JsonProcessingException;
import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.serialization.factories.JacksonFactory;
import dev.rvz.generate.log.exception.ErrorParsingExcepiton;
import dev.rvz.generate.log.model.ErrorAppData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;


public class GenerateLog implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Logger LOGGER = LogManager.getLogger(GenerateLog.class);


    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,
                                                      final Context context) {

        try {
            final ErrorAppData errorAppData = getErrorAppDataDeserialized(apiGatewayProxyRequestEvent);
            ThreadContext.put("xpto", "valor");
            LOGGER.info("{}", errorAppData);
            ThreadContext.clearAll();
            final APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();

            return apiGatewayProxyResponseEvent.withStatusCode(200).withBody("Ok");
        } catch (ErrorParsingExcepiton errorParsingExcepiton) {
            final APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();

            return apiGatewayProxyResponseEvent.withStatusCode(400).withBody(errorParsingExcepiton.getMessage());
        }


    }

    private ErrorAppData getErrorAppDataDeserialized(
            final APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) throws ErrorParsingExcepiton {
        final ObjectMapper objectMapper = JacksonFactory.getInstance().getMapper();
        try {
            ErrorAppData errorAppData = objectMapper
                    .readValue(apiGatewayProxyRequestEvent.getBody(),
                            ErrorAppData.class);
            LOGGER.info("{}", errorAppData);
            return errorAppData;
        } catch (JsonProcessingException e) {
            LOGGER.error("ocorreu error ao deserializar a requisicao. message: {}", e.getMessage());
            throw new ErrorParsingExcepiton(e.getMessage());
        }
    }
}
