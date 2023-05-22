package com.project.boardsideproject.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

/**
 * 템플릿을 순수 HTML5로 표현고자함. (태그마다 작성된 th 문법으로 복잡함)
 * 타임리프3부터 Decoupled Template Logic 기능을 제공해서 모든 타임리프 문법을 HTML 과 분리
 * 하지만 이상하게도 Properties에 Decoupled 설정이 없어서
 * 아래와 같이 Config를 통해 Properties 속성을 직접 만들어줌
 * (기본적으로 SpringResourcetemplateResolver 내부에 DecoupledLogic을 사용할거냐 라는 메소드가 있음)
 * @Link : https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#decoupled-template-logic
 *
 * IDE와 매끄럽게 연동하는 방법
 * build gradle > Spring Configuration Processor 설정
 */
@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties)
    {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());
        return defaultTemplateResolver;
    }

    @RequiredArgsConstructor
    @Getter
    @ConstructorBinding
    @ConfigurationProperties("spring.thymeleaf3")
    private static class Thymeleaf3Properties {

        /**
         * Use Thymeleaf 3 Decoupled Logic
         */
        private final boolean decoupledLogic;
    }
}
