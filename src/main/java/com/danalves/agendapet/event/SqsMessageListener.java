package com.danalves.agendapet.event;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.danalves.agendapet.dto.AnimalReceived;
import com.danalves.agendapet.dto.AwsSqsMessageReceived;
import com.danalves.agendapet.service.aws.S3Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqsMessageListener {

    @Value("${aws.sqs.queue-name}")
    private String queueName;

    private final AmazonSQS sqs;

    private final S3Service s3;

    @Scheduled(fixedDelay = 5000)
    public void consumeMessages() {
        try {
            String queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();

            ReceiveMessageResult receiveMessageResult = sqs.receiveMessage(queueUrl);

            if (!receiveMessageResult.getMessages().isEmpty()) {
                Message message = receiveMessageResult.getMessages().get(0);

                ObjectMapper jackson = new ObjectMapper();
                AwsSqsMessageReceived myObject = jackson.readValue(message.getBody(), AwsSqsMessageReceived.class);
                AnimalReceived myAnimal = jackson.readValue(myObject.Message(), AnimalReceived.class);

                log.info("Received Animal: " + myAnimal.name());
                sqs.deleteMessage(queueUrl, message.getReceiptHandle());

                CollectionType typeRef = TypeFactory.defaultInstance().constructCollectionType(List.class, AnimalReceived.class);
                List<AnimalReceived> animals = s3.readJsonFromS3(typeRef);

                if (animals == null) {
                    animals = List.of(myAnimal);
                } else {
                    animals.add(myAnimal);
                }

                s3.updateJsonInS3(animals);
            }

        } catch (Exception e) {
            log.error("Queue Exception Message: {}", e.getMessage());
        }
    }

}