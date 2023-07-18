package dev.rvz.generate.log.model;

import java.time.LocalDateTime;

public record ErrorAppData(
    String platform,
    String appVersion,
    DeviceModel deviceModel,
    String messageError,
    LocalDateTime dateTimeError
) {}