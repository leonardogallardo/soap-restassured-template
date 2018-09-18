package geral;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.InesperadoException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import utils.Contants;
import utils.ObjectShare;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseTest {

    protected static final Log LOG = LogFactory.getLog(BaseTest.class);

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        System.setProperty("current.date", dateFormat.format(new Date()));
    }

    @BeforeClass
    public static void beforeAll() {
        String env = System.getProperty(Contants.ENV);
        if (env == null || env.isEmpty()) {
            env = "homolog";
        }
        ObjectShare.set(Contants.ENV, env);
        LOG.info(MessageFormat.format("Environment: [{0}]", env));
    }

    @Before
    public void beforeEach() {
        // do nothing
    }

    @AfterClass
    public static void afterAll() {
        // do nothing
    }

    @After
    public void afterEach() {
        // do nothing
    }

    public Map<String, Object> jsonToMap(final String file) {
        try {
            return new ObjectMapper().readValue(this.getClass().getResource(file), HashMap.class);
        } catch (IOException e) {
            throw new InesperadoException(e);
        }
    }

}
