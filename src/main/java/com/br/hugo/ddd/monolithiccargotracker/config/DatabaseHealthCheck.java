package com.br.hugo.ddd.monolithiccargotracker.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@ApplicationScoped
public class DatabaseHealthCheck {

    private static final Logger logger = Logger.getLogger(DatabaseHealthCheck.class.getName());

    @Resource(lookup = "jdbc/monolithiccargotracker")
    private DataSource dataSource;

    @PersistenceContext(unitName = "monolithiccargotracker")
    private EntityManager em;

    @PostConstruct
    public void init() {
        checkDataSource();
        checkEntityManager();
    }

    private void checkDataSource() {
        try (Connection conn = dataSource.getConnection()) {
            logger.info("✅ DataSource funcionando - Conectado ao H2");
        } catch (SQLException e) {
            logger.severe("❌ DataSource FALHOU: " + e.getMessage());
        }
    }

    private void checkEntityManager() {
        if (em == null) {
            logger.severe("❌ EntityManager é NULL");
        } else {
            logger.info("✅ EntityManager injetado com sucesso");
            try {
                // Teste mais compatível com H2
                Object result = em.createNativeQuery("SELECT 1").getSingleResult();
                logger.info("✅ EntityManager consegue executar queries: " + result);
            } catch (Exception e) {
                logger.severe("❌ EntityManager não consegue executar queries: " + e.getMessage());
            }
        }
    }
}
