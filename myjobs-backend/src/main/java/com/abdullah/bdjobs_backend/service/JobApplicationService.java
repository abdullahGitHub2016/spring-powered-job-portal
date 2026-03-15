package com.abdullah.bdjobs_backend.service;

import com.abdullah.bdjobs_backend.dto.JobApplicationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationService {

    private final KafkaTemplate<String, JobApplicationEvent> kafkaTemplate;

    public JobApplicationService(KafkaTemplate<String, JobApplicationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendApplicationEvent(JobApplicationEvent event) {
        kafkaTemplate.send("job-applications", event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("SUCCESS: Message sent to topic [" + result.getRecordMetadata().topic() +
                                "] at offset " + result.getRecordMetadata().offset());
                    } else {
                        System.err.println("FAILED: Unable to send message due to : " + ex.getMessage());
                    }
                });
    }
}