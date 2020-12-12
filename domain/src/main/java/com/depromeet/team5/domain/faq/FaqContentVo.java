package com.depromeet.team5.domain.faq;

import lombok.Value;

@Value(staticConstructor = "of")
public class FaqContentVo {
    String question;
    String answer;
}
