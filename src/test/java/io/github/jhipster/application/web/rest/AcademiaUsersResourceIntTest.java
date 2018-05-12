package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.AcademiaUsers;
import io.github.jhipster.application.repository.AcademiaUsersRepository;
import io.github.jhipster.application.service.AcademiaUsersService;
import io.github.jhipster.application.repository.search.AcademiaUsersSearchRepository;
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
 * Test class for the AcademiaUsersResource REST controller.
 *
 * @see AcademiaUsersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class AcademiaUsersResourceIntTest {

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
    private AcademiaUsersRepository academiaUsersRepository;

    @Autowired
    private AcademiaUsersService academiaUsersService;

    @Autowired
    private AcademiaUsersSearchRepository academiaUsersSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAcademiaUsersMockMvc;

    private AcademiaUsers academiaUsers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcademiaUsersResource academiaUsersResource = new AcademiaUsersResource(academiaUsersService);
        this.restAcademiaUsersMockMvc = MockMvcBuilders.standaloneSetup(academiaUsersResource)
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
    public static AcademiaUsers createEntity(EntityManager em) {
        AcademiaUsers academiaUsers = new AcademiaUsers()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .userName(DEFAULT_USER_NAME)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD)
            .uRole(DEFAULT_U_ROLE);
        return academiaUsers;
    }

    @Before
    public void initTest() {
        academiaUsersSearchRepository.deleteAll();
        academiaUsers = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcademiaUsers() throws Exception {
        int databaseSizeBeforeCreate = academiaUsersRepository.findAll().size();

        // Create the AcademiaUsers
        restAcademiaUsersMockMvc.perform(post("/api/academia-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academiaUsers)))
            .andExpect(status().isCreated());

        // Validate the AcademiaUsers in the database
        List<AcademiaUsers> academiaUsersList = academiaUsersRepository.findAll();
        assertThat(academiaUsersList).hasSize(databaseSizeBeforeCreate + 1);
        AcademiaUsers testAcademiaUsers = academiaUsersList.get(academiaUsersList.size() - 1);
        assertThat(testAcademiaUsers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAcademiaUsers.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testAcademiaUsers.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testAcademiaUsers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAcademiaUsers.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testAcademiaUsers.getuRole()).isEqualTo(DEFAULT_U_ROLE);

        // Validate the AcademiaUsers in Elasticsearch
        AcademiaUsers academiaUsersEs = academiaUsersSearchRepository.findOne(testAcademiaUsers.getId());
        assertThat(academiaUsersEs).isEqualToIgnoringGivenFields(testAcademiaUsers);
    }

    @Test
    @Transactional
    public void createAcademiaUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = academiaUsersRepository.findAll().size();

        // Create the AcademiaUsers with an existing ID
        academiaUsers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademiaUsersMockMvc.perform(post("/api/academia-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academiaUsers)))
            .andExpect(status().isBadRequest());

        // Validate the AcademiaUsers in the database
        List<AcademiaUsers> academiaUsersList = academiaUsersRepository.findAll();
        assertThat(academiaUsersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAcademiaUsers() throws Exception {
        // Initialize the database
        academiaUsersRepository.saveAndFlush(academiaUsers);

        // Get all the academiaUsersList
        restAcademiaUsersMockMvc.perform(get("/api/academia-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academiaUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].uRole").value(hasItem(DEFAULT_U_ROLE.toString())));
    }

    @Test
    @Transactional
    public void getAcademiaUsers() throws Exception {
        // Initialize the database
        academiaUsersRepository.saveAndFlush(academiaUsers);

        // Get the academiaUsers
        restAcademiaUsersMockMvc.perform(get("/api/academia-users/{id}", academiaUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(academiaUsers.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.uRole").value(DEFAULT_U_ROLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAcademiaUsers() throws Exception {
        // Get the academiaUsers
        restAcademiaUsersMockMvc.perform(get("/api/academia-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcademiaUsers() throws Exception {
        // Initialize the database
        academiaUsersService.save(academiaUsers);

        int databaseSizeBeforeUpdate = academiaUsersRepository.findAll().size();

        // Update the academiaUsers
        AcademiaUsers updatedAcademiaUsers = academiaUsersRepository.findOne(academiaUsers.getId());
        // Disconnect from session so that the updates on updatedAcademiaUsers are not directly saved in db
        em.detach(updatedAcademiaUsers);
        updatedAcademiaUsers
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .userName(UPDATED_USER_NAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .uRole(UPDATED_U_ROLE);

        restAcademiaUsersMockMvc.perform(put("/api/academia-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAcademiaUsers)))
            .andExpect(status().isOk());

        // Validate the AcademiaUsers in the database
        List<AcademiaUsers> academiaUsersList = academiaUsersRepository.findAll();
        assertThat(academiaUsersList).hasSize(databaseSizeBeforeUpdate);
        AcademiaUsers testAcademiaUsers = academiaUsersList.get(academiaUsersList.size() - 1);
        assertThat(testAcademiaUsers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAcademiaUsers.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testAcademiaUsers.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testAcademiaUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAcademiaUsers.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testAcademiaUsers.getuRole()).isEqualTo(UPDATED_U_ROLE);

        // Validate the AcademiaUsers in Elasticsearch
        AcademiaUsers academiaUsersEs = academiaUsersSearchRepository.findOne(testAcademiaUsers.getId());
        assertThat(academiaUsersEs).isEqualToIgnoringGivenFields(testAcademiaUsers);
    }

    @Test
    @Transactional
    public void updateNonExistingAcademiaUsers() throws Exception {
        int databaseSizeBeforeUpdate = academiaUsersRepository.findAll().size();

        // Create the AcademiaUsers

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAcademiaUsersMockMvc.perform(put("/api/academia-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academiaUsers)))
            .andExpect(status().isCreated());

        // Validate the AcademiaUsers in the database
        List<AcademiaUsers> academiaUsersList = academiaUsersRepository.findAll();
        assertThat(academiaUsersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAcademiaUsers() throws Exception {
        // Initialize the database
        academiaUsersService.save(academiaUsers);

        int databaseSizeBeforeDelete = academiaUsersRepository.findAll().size();

        // Get the academiaUsers
        restAcademiaUsersMockMvc.perform(delete("/api/academia-users/{id}", academiaUsers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean academiaUsersExistsInEs = academiaUsersSearchRepository.exists(academiaUsers.getId());
        assertThat(academiaUsersExistsInEs).isFalse();

        // Validate the database is empty
        List<AcademiaUsers> academiaUsersList = academiaUsersRepository.findAll();
        assertThat(academiaUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAcademiaUsers() throws Exception {
        // Initialize the database
        academiaUsersService.save(academiaUsers);

        // Search the academiaUsers
        restAcademiaUsersMockMvc.perform(get("/api/_search/academia-users?query=id:" + academiaUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academiaUsers.getId().intValue())))
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
        TestUtil.equalsVerifier(AcademiaUsers.class);
        AcademiaUsers academiaUsers1 = new AcademiaUsers();
        academiaUsers1.setId(1L);
        AcademiaUsers academiaUsers2 = new AcademiaUsers();
        academiaUsers2.setId(academiaUsers1.getId());
        assertThat(academiaUsers1).isEqualTo(academiaUsers2);
        academiaUsers2.setId(2L);
        assertThat(academiaUsers1).isNotEqualTo(academiaUsers2);
        academiaUsers1.setId(null);
        assertThat(academiaUsers1).isNotEqualTo(academiaUsers2);
    }
}
