package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.TestAc;
import io.github.jhipster.application.repository.TestAcRepository;
import io.github.jhipster.application.service.TestAcService;
import io.github.jhipster.application.repository.search.TestAcSearchRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TestAcResource REST controller.
 *
 * @see TestAcResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TestAcResourceIntTest {

    private static final LocalDate DEFAULT_CREATED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TEST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEST_NAME = "BBBBBBBBBB";

    @Autowired
    private TestAcRepository testAcRepository;

    @Autowired
    private TestAcService testAcService;

    @Autowired
    private TestAcSearchRepository testAcSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTestAcMockMvc;

    private TestAc testAc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TestAcResource testAcResource = new TestAcResource(testAcService);
        this.restTestAcMockMvc = MockMvcBuilders.standaloneSetup(testAcResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestAc createEntity(EntityManager em) {
        TestAc testAc = new TestAc()
            .createdTime(DEFAULT_CREATED_TIME)
            .testName(DEFAULT_TEST_NAME);
        return testAc;
    }

    @Before
    public void initTest() {
        testAcSearchRepository.deleteAll();
        testAc = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestAc() throws Exception {
        int databaseSizeBeforeCreate = testAcRepository.findAll().size();

        // Create the TestAc
        restTestAcMockMvc.perform(post("/api/test-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testAc)))
            .andExpect(status().isCreated());

        // Validate the TestAc in the database
        List<TestAc> testAcList = testAcRepository.findAll();
        assertThat(testAcList).hasSize(databaseSizeBeforeCreate + 1);
        TestAc testTestAc = testAcList.get(testAcList.size() - 1);
        assertThat(testTestAc.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testTestAc.getTestName()).isEqualTo(DEFAULT_TEST_NAME);

        // Validate the TestAc in Elasticsearch
        TestAc testAcEs = testAcSearchRepository.findOne(testTestAc.getId());
        assertThat(testAcEs).isEqualToIgnoringGivenFields(testTestAc);
    }

    @Test
    @Transactional
    public void createTestAcWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testAcRepository.findAll().size();

        // Create the TestAc with an existing ID
        testAc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestAcMockMvc.perform(post("/api/test-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testAc)))
            .andExpect(status().isBadRequest());

        // Validate the TestAc in the database
        List<TestAc> testAcList = testAcRepository.findAll();
        assertThat(testAcList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTestAcs() throws Exception {
        // Initialize the database
        testAcRepository.saveAndFlush(testAc);

        // Get all the testAcList
        restTestAcMockMvc.perform(get("/api/test-acs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testAc.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].testName").value(hasItem(DEFAULT_TEST_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTestAc() throws Exception {
        // Initialize the database
        testAcRepository.saveAndFlush(testAc);

        // Get the testAc
        restTestAcMockMvc.perform(get("/api/test-acs/{id}", testAc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testAc.getId().intValue()))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.testName").value(DEFAULT_TEST_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTestAc() throws Exception {
        // Get the testAc
        restTestAcMockMvc.perform(get("/api/test-acs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestAc() throws Exception {
        // Initialize the database
        testAcService.save(testAc);

        int databaseSizeBeforeUpdate = testAcRepository.findAll().size();

        // Update the testAc
        TestAc updatedTestAc = testAcRepository.findOne(testAc.getId());
        // Disconnect from session so that the updates on updatedTestAc are not directly saved in db
        em.detach(updatedTestAc);
        updatedTestAc
            .createdTime(UPDATED_CREATED_TIME)
            .testName(UPDATED_TEST_NAME);

        restTestAcMockMvc.perform(put("/api/test-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTestAc)))
            .andExpect(status().isOk());

        // Validate the TestAc in the database
        List<TestAc> testAcList = testAcRepository.findAll();
        assertThat(testAcList).hasSize(databaseSizeBeforeUpdate);
        TestAc testTestAc = testAcList.get(testAcList.size() - 1);
        assertThat(testTestAc.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testTestAc.getTestName()).isEqualTo(UPDATED_TEST_NAME);

        // Validate the TestAc in Elasticsearch
        TestAc testAcEs = testAcSearchRepository.findOne(testTestAc.getId());
        assertThat(testAcEs).isEqualToIgnoringGivenFields(testTestAc);
    }

    @Test
    @Transactional
    public void updateNonExistingTestAc() throws Exception {
        int databaseSizeBeforeUpdate = testAcRepository.findAll().size();

        // Create the TestAc

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTestAcMockMvc.perform(put("/api/test-acs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testAc)))
            .andExpect(status().isCreated());

        // Validate the TestAc in the database
        List<TestAc> testAcList = testAcRepository.findAll();
        assertThat(testAcList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTestAc() throws Exception {
        // Initialize the database
        testAcService.save(testAc);

        int databaseSizeBeforeDelete = testAcRepository.findAll().size();

        // Get the testAc
        restTestAcMockMvc.perform(delete("/api/test-acs/{id}", testAc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean testAcExistsInEs = testAcSearchRepository.exists(testAc.getId());
        assertThat(testAcExistsInEs).isFalse();

        // Validate the database is empty
        List<TestAc> testAcList = testAcRepository.findAll();
        assertThat(testAcList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTestAc() throws Exception {
        // Initialize the database
        testAcService.save(testAc);

        // Search the testAc
        restTestAcMockMvc.perform(get("/api/_search/test-acs?query=id:" + testAc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testAc.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].testName").value(hasItem(DEFAULT_TEST_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestAc.class);
        TestAc testAc1 = new TestAc();
        testAc1.setId(1L);
        TestAc testAc2 = new TestAc();
        testAc2.setId(testAc1.getId());
        assertThat(testAc1).isEqualTo(testAc2);
        testAc2.setId(2L);
        assertThat(testAc1).isNotEqualTo(testAc2);
        testAc1.setId(null);
        assertThat(testAc1).isNotEqualTo(testAc2);
    }
}
