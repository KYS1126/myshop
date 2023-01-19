package com.example.myshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //jpa의 auditing(감시)기능을 활성화
public class AuditConfig {
	
	@Bean //빈으로 만들어서 알아서 관리하게 만들어주고, 의존성 주입이 가능해진다.
	public AuditorAware<String> auditorprovider() {
		return new AuditorAwareImpl();
	}
}
