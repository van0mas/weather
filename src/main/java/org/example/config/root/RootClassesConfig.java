package org.example.config.root;

import org.example.config.db.DataSourceConfig;
import org.example.config.db.FlywayConfig;
import org.example.config.db.HibernateConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import({DataSourceConfig.class, HibernateConfig.class, FlywayConfig.class})
@EnableJpaRepositories(basePackages = "org.example.repository")
public class RootClassesConfig {
}
