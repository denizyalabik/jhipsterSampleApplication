package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Usersdemo;
import io.github.jhipster.application.repository.UsersdemoRepository;
import io.github.jhipster.application.service.UsersdemoService;
import io.github.jhipster.application.repository.search.UsersdemoSearchRepository;
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
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.Role;
/**
 * Test class for the UsersdemoResource REST controller.
 *
 * @see UsersdemoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class UsersdemoResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Role DEFAULT_U_ROLE = Role.ADMIN;
    private static final Role UPDATED_U_ROLE = Role.TEACHER;

    @Autowired
    private UsersdemoRepository usersdemoRepository;

    @Autowired
    private UsersdemoService usersdemoService;

    @Autowired
    private UsersdemoSearchRepository usersdemoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUsersdemoMockMvc;

    private Usersdemo usersdemo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsersdemoResource usersdemoResource = new UsersdemoResource(usersdemoService);
        this.restUsersdemoMockMvc = MockMvcBuilders.standaloneSetup(usersdemoResource)
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
    public static Usersdemo createEntity(EntityManager em) {
        Usersdemo usersdemo = new Usersdemo()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .userName(DEFAULT_USER_NAME)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD)
            .uRole(DEFAULT_U_ROLE);
        return usersdemo;
    }

    @Before
    public void initTest() {
        usersdemoSearchRepository.deleteAll();
        usersdemo = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsersdemo() throws Exception {
        int databaseSizeBeforeCreate = usersdemoRepository.findAll().size();

        // Create the Usersdemo
        restUsersdemoMockMvc.perform(post("/api/usersdemos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usersdemo)))
            .andExpect(status().isCreated());

        // Validate the Usersdemo in the database
        List<Usersdemo> usersdemoList = usersdemoRepository.findAll();
        assertThat(usersdemoList).hasSize(databaseSizeBeforeCreate + 1);
        Usersdemo testUsersdemo = usersdemoList.get(usersdemoList.size() - 1);
        assertThat(testUsersdemo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUsersdemo.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testUsersdemo.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUsersdemo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUsersdemo.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUsersdemo.getuRole()).isEqualTo(DEFAULT_U_ROLE);

        // Validate the Usersdemo in Elasticsearch
        Usersdemo usersdemoEs = usersdemoSearchRepository.findOne(testUsersdemo.getId());
        assertThat(usersdemoEs).isEqualToIgnoringGivenFields(testUsersdemo);
    }

    @Test
    @Transactional
    public void createUsersdemoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usersdemoRepository.findAll().size();

        // Create the Usersdemo with an existing ID
        usersdemo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsersdemoMockMvc.perform(post("/api/usersdemos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usersdemo)))
            .andExpect(status().isBadRequest());

        // Validate the Usersdemo in the database
        List<Usersdemo> usersdemoList = usersdemoRepository.findAll();
        assertThat(usersdemoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUsersdemos() throws Exception {
        // Initialize the database
        usersdemoRepository.saveAndFlush(usersdemo);

        // Get all the usersdemoList
        restUsersdemoMockMvc.perform(get("/api/usersdemos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usersdemo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].uRole").value(hasItem(DEFAULT_U_ROLE.toString())));
    }

    @Test
    @Transactional
    public void getUsersdemo() throws Exception {
        // Initialize the database
        usersdemoRepository.saveAndFlush(usersdemo);

        // Get the usersdemo
        restUsersdemoMockMvc.perform(get("/api/usersdemos/{id}", usersdemo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usersdemo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.uRole").value(DEFAULT_U_ROLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUsersdemo() throws Exception {
        // Get the usersdemo
        restUsersdemoMockMvc.perform(get("/api/usersdemos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsersdemo() throws Exception {
        // Initialize the database
        usersdemoService.save(usersdemo);

        int databaseSizeBeforeUpdate = usersdemoRepository.findAll().size();

        // Update the usersdemo
        Usersdemo updatedUsersdemo = usersdemoRepository.findOne(usersdemo.getId());
        // Disconnect from session so that the updates on updatedUsersdemo are not directly saved in db
        em.detach(updatedUsersdemo);
        updatedUsersdemo
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .userName(UPDATED_USER_NAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .uRole(UPDATED_U_ROLE);

        restUsersdemoMockMvc.perform(put("/api/usersdemos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsersdemo)))
            .andExpect(status().isOk());

        // Validate the Usersdemo in the database
        List<Usersdemo> usersdemoList = usersdemoRepository.findAll();
        assertThat(usersdemoList).hasSize(databaseSizeBeforeUpdate);
        Usersdemo testUsersdemo = usersdemoList.get(usersdemoList.size() - 1);
        assertThat(testUsersdemo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUsersdemo.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testUsersdemo.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUsersdemo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUsersdemo.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUsersdemo.getuRole()).isEqualTo(UPDATED_U_ROLE);

        // Validate the Usersdemo in Elasticsearch
        Usersdemo usersdemoEs = usersdemoSearchRepository.findOne(testUsersdemo.getId());
        assertThat(usersdemoEs).isEqualToIgnoringGivenFields(testUsersdemo);
    }

    @Test
    @Transactional
    public void updateNonExistingUsersdemo() throws Exception {
        int databaseSizeBeforeUpdate = usersdemoRepository.findAll().size();

        // Create the Usersdemo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUsersdemoMockMvc.perform(put("/api/usersdemos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usersdemo)))
            .andExpect(status().isCreated());

        // Validate the Usersdemo in the database
        List<Usersdemo> usersdemoList = usersdemoRepository.findAll();
        assertThat(usersdemoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUsersdemo() throws Exception {
        // Initialize the database
        usersdemoService.save(usersdemo);

        int databaseSizeBeforeDelete = usersdemoRepository.findAll().size();

        // Get the usersdemo
        restUsersdemoMockMvc.perform(delete("/api/usersdemos/{id}", usersdemo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean usersdemoExistsInEs = usersdemoSearchRepository.exists(usersdemo.getId());
        assertThat(usersdemoExistsInEs).isFalse();

        // Validate the database is empty
        List<Usersdemo> usersdemoList = usersdemoRepository.findAll();
        assertThat(usersdemoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUsersdemo() throws Exception {
        // Initialize the database
        usersdemoService.save(usersdemo);

        // Search the usersdemo
        restUsersdemoMockMvc.perform(get("/api/_search/usersdemos?query=id:" + usersdemo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usersdemo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].uRole").value(hasItem(DEFAULT_U_ROLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usersdemo.class);
        Usersdemo usersdemo1 = new Usersdemo();
        usersdemo1.setId(1L);
        Usersdemo usersdemo2 = new Usersdemo();
        usersdemo2.setId(usersdemo1.getId());
        assertThat(usersdemo1).isEqualTo(usersdemo2);
        usersdemo2.setId(2L);
        assertThat(usersdemo1).isNotEqualTo(usersdemo2);
        usersdemo1.setId(null);
        assertThat(usersdemo1).isNotEqualTo(usersdemo2);
    }
}
