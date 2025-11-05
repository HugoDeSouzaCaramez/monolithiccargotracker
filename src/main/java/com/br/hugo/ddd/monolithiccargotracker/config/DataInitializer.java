package com.br.hugo.ddd.monolithiccargotracker.config;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@ApplicationScoped
public class DataInitializer {

    private static final Logger logger = Logger.getLogger(DataInitializer.class.getName());

    @PersistenceContext(unitName = "monolithiccargotracker")
    private EntityManager em;

    @PostConstruct
    @Transactional
    public void init() {
        logger.info("🚀 Inicializando dados do banco...");

        try {
            // Verificar se as tabelas existem
            Long cargoCount = em.createQuery("SELECT COUNT(c) FROM Cargo c", Long.class).getSingleResult();
            logger.info("✅ Tabela Cargo existe com " + cargoCount + " registros");

            Long voyageCount = em.createQuery("SELECT COUNT(v) FROM Voyage v", Long.class).getSingleResult();
            logger.info("✅ Tabela Voyage existe com " + voyageCount + " registros");

        } catch (Exception e) {
            logger.severe("❌ Erro ao acessar tabelas: " + e.getMessage());
            // Isso é normal na primeira execução
        }

        logger.info("✅ Inicialização do banco concluída");
    }
}
