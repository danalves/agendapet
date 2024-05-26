package com.danalves.agendapet.service.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsSnsService {
    private final AmazonSNS snsClient;
    @Qualifier("catalogEventsTopic")
    private final Topic catalogTopic;

    public void publish(String message) {
        log.info(message);
        this.snsClient.publish(catalogTopic.getTopicArn(), message);
    }
}
