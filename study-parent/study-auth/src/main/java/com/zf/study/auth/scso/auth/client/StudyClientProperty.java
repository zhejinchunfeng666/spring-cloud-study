package com.zf.study.auth.scso.auth.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix="study.oauth")
@Data
public class StudyClientProperty {
	 private final List<StudyClient> clients =new ArrayList<StudyClient>();
}
