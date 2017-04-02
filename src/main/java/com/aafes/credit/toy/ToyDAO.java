package com.aafes.credit.toy;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToyDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ToyDAO.class);
    private Cluster cluster;
    private Session session;
    private MappingManager manager;
    private Mapper<Toy> mapper;
   
    //@PostConstruct
    public void connect(String seedHost) {
        if ((session != null) && (!session.isClosed())) {
            return;
        }
        cluster = Cluster.builder()
                .addContactPoint(seedHost)
                .build();
        session = cluster.connect();
        manager = new MappingManager(session);
        mapper = manager.mapper(Toy.class);
        if (session.isClosed()) {
            LOG.error("Closed!");
        } else {
            LOG.info("Connected.");
        }
    }

    public void save(Toy response) {
        mapper.save(response);
    }

    public Toy find(String name) {

        return mapper.get(name);
    }

   
    }
