package com.ru.hrms_service.common.models;

import java.util.Map;

public record MailDto(
        String subject,

        String toMail,

        Map<String, Object> props,

        String templateFileName
) {
}
