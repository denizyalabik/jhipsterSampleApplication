package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.QuestionAnswer;
import io.github.jhipster.application.repository.QuestionAnswerRepository;
import io.github.jhipster.application.service.QuestionAnswerService;
import io.github.jhipster.application.repository.search.QuestionAnswerSearchRepository;
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

import io.github.jhipster.application.domain.enumeration.Category;
import io.github.jhipster.application.domain.enumeration.Subject;
import io.github.jhipster.application.domain.enumeration.Level;
/**
 * Test class for the QuestionAnswerResource REST controller.
 *
 * @see QuestionAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class QuestionAnswerResourceIntTest {

    private static final String DEFAULT_QUESTION_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_CORRECT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_CORRECT_ANSWER = "BBBBBBBBBB";

    private static final String DEFAULT_WRONG_ANSWERS = "AAAAAAAAAA";
    private static final String UPDATED_WRONG_ANSWERS = "BBBBBBBBBB";

    private static final Category DEFAULT_CATAGORY = Category.NONE;
    private static final Category UPDATED_CATAGORY = Category.MATH;

    private static final Subject DEFAULT_SUBJECT = Subject.ASD;
    private static final Subject UPDATED_SUBJECT = Subject.ERED;

    private static final Level DEFAULT_LEVEL = Level.EASY;
    private static final Level UPDATED_LEVEL = Level.NORMAL;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Autowired
    private QuestionAnswerSearchRepository questionAnswerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestionAnswerMockMvc;

    private QuestionAnswer questionAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionAnswerResource questionAnswerResource = new QuestionAnswerResource(questionAnswerService);
        this.restQuestionAnswerMockMvc = MockMvcBuilders.standaloneSetup(questionAnswerResource)
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
    public static QuestionAnswer createEntity(EntityManager em) {
        QuestionAnswer questionAnswer = new QuestionAnswer()
            .questionTitle(DEFAULT_QUESTION_TITLE)
            .question(DEFAULT_QUESTION)
            .correctAnswer(DEFAULT_CORRECT_ANSWER)
            .wrongAnswers(DEFAULT_WRONG_ANSWERS)
            .catagory(DEFAULT_CATAGORY)
            .subject(DEFAULT_SUBJECT)
            .level(DEFAULT_LEVEL);
        return questionAnswer;
    }

    @Before
    public void initTest() {
        questionAnswerSearchRepository.deleteAll();
        questionAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionAnswer() throws Exception {
        int databaseSizeBeforeCreate = questionAnswerRepository.findAll().size();

        // Create the QuestionAnswer
        restQuestionAnswerMockMvc.perform(post("/api/question-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswer)))
            .andExpect(status().isCreated());

        // Validate the QuestionAnswer in the database
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionAnswer testQuestionAnswer = questionAnswerList.get(questionAnswerList.size() - 1);
        assertThat(testQuestionAnswer.getQuestionTitle()).isEqualTo(DEFAULT_QUESTION_TITLE);
        assertThat(testQuestionAnswer.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQuestionAnswer.getCorrectAnswer()).isEqualTo(DEFAULT_CORRECT_ANSWER);
        assertThat(testQuestionAnswer.getWrongAnswers()).isEqualTo(DEFAULT_WRONG_ANSWERS);
        assertThat(testQuestionAnswer.getCatagory()).isEqualTo(DEFAULT_CATAGORY);
        assertThat(testQuestionAnswer.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testQuestionAnswer.getLevel()).isEqualTo(DEFAULT_LEVEL);

        // Validate the QuestionAnswer in Elasticsearch
        QuestionAnswer questionAnswerEs = questionAnswerSearchRepository.findOne(testQuestionAnswer.getId());
        assertThat(questionAnswerEs).isEqualToIgnoringGivenFields(testQuestionAnswer);
    }

    @Test
    @Transactional
    public void createQuestionAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionAnswerRepository.findAll().size();

        // Create the QuestionAnswer with an existing ID
        questionAnswer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionAnswerMockMvc.perform(post("/api/question-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionAnswer in the database
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuestionTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionAnswerRepository.findAll().size();
        // set the field null
        questionAnswer.setQuestionTitle(null);

        // Create the QuestionAnswer, which fails.

        restQuestionAnswerMockMvc.perform(post("/api/question-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswer)))
            .andExpect(status().isBadRequest());

        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionAnswerRepository.findAll().size();
        // set the field null
        questionAnswer.setQuestion(null);

        // Create the QuestionAnswer, which fails.

        restQuestionAnswerMockMvc.perform(post("/api/question-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswer)))
            .andExpect(status().isBadRequest());

        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorrectAnswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionAnswerRepository.findAll().size();
        // set the field null
        questionAnswer.setCorrectAnswer(null);

        // Create the QuestionAnswer, which fails.

        restQuestionAnswerMockMvc.perform(post("/api/question-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswer)))
            .andExpect(status().isBadRequest());

        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWrongAnswersIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionAnswerRepository.findAll().size();
        // set the field null
        questionAnswer.setWrongAnswers(null);

        // Create the QuestionAnswer, which fails.

        restQuestionAnswerMockMvc.perform(post("/api/question-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswer)))
            .andExpect(status().isBadRequest());

        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestionAnswers() throws Exception {
        // Initialize the database
        questionAnswerRepository.saveAndFlush(questionAnswer);

        // Get all the questionAnswerList
        restQuestionAnswerMockMvc.perform(get("/api/question-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionTitle").value(hasItem(DEFAULT_QUESTION_TITLE.toString())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].correctAnswer").value(hasItem(DEFAULT_CORRECT_ANSWER.toString())))
            .andExpect(jsonPath("$.[*].wrongAnswers").value(hasItem(DEFAULT_WRONG_ANSWERS.toString())))
            .andExpect(jsonPath("$.[*].catagory").value(hasItem(DEFAULT_CATAGORY.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }

    @Test
    @Transactional
    public void getQuestionAnswer() throws Exception {
        // Initialize the database
        questionAnswerRepository.saveAndFlush(questionAnswer);

        // Get the questionAnswer
        restQuestionAnswerMockMvc.perform(get("/api/question-answers/{id}", questionAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionAnswer.getId().intValue()))
            .andExpect(jsonPath("$.questionTitle").value(DEFAULT_QUESTION_TITLE.toString()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.correctAnswer").value(DEFAULT_CORRECT_ANSWER.toString()))
            .andExpect(jsonPath("$.wrongAnswers").value(DEFAULT_WRONG_ANSWERS.toString()))
            .andExpect(jsonPath("$.catagory").value(DEFAULT_CATAGORY.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionAnswer() throws Exception {
        // Get the questionAnswer
        restQuestionAnswerMockMvc.perform(get("/api/question-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionAnswer() throws Exception {
        // Initialize the database
        questionAnswerService.save(questionAnswer);

        int databaseSizeBeforeUpdate = questionAnswerRepository.findAll().size();

        // Update the questionAnswer
        QuestionAnswer updatedQuestionAnswer = questionAnswerRepository.findOne(questionAnswer.getId());
        // Disconnect from session so that the updates on updatedQuestionAnswer are not directly saved in db
        em.detach(updatedQuestionAnswer);
        updatedQuestionAnswer
            .questionTitle(UPDATED_QUESTION_TITLE)
            .question(UPDATED_QUESTION)
            .correctAnswer(UPDATED_CORRECT_ANSWER)
            .wrongAnswers(UPDATED_WRONG_ANSWERS)
            .catagory(UPDATED_CATAGORY)
            .subject(UPDATED_SUBJECT)
            .level(UPDATED_LEVEL);

        restQuestionAnswerMockMvc.perform(put("/api/question-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionAnswer)))
            .andExpect(status().isOk());

        // Validate the QuestionAnswer in the database
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeUpdate);
        QuestionAnswer testQuestionAnswer = questionAnswerList.get(questionAnswerList.size() - 1);
        assertThat(testQuestionAnswer.getQuestionTitle()).isEqualTo(UPDATED_QUESTION_TITLE);
        assertThat(testQuestionAnswer.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestionAnswer.getCorrectAnswer()).isEqualTo(UPDATED_CORRECT_ANSWER);
        assertThat(testQuestionAnswer.getWrongAnswers()).isEqualTo(UPDATED_WRONG_ANSWERS);
        assertThat(testQuestionAnswer.getCatagory()).isEqualTo(UPDATED_CATAGORY);
        assertThat(testQuestionAnswer.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testQuestionAnswer.getLevel()).isEqualTo(UPDATED_LEVEL);

        // Validate the QuestionAnswer in Elasticsearch
        QuestionAnswer questionAnswerEs = questionAnswerSearchRepository.findOne(testQuestionAnswer.getId());
        assertThat(questionAnswerEs).isEqualToIgnoringGivenFields(testQuestionAnswer);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionAnswer() throws Exception {
        int databaseSizeBeforeUpdate = questionAnswerRepository.findAll().size();

        // Create the QuestionAnswer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestionAnswerMockMvc.perform(put("/api/question-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswer)))
            .andExpect(status().isCreated());

        // Validate the QuestionAnswer in the database
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuestionAnswer() throws Exception {
        // Initialize the database
        questionAnswerService.save(questionAnswer);

        int databaseSizeBeforeDelete = questionAnswerRepository.findAll().size();

        // Get the questionAnswer
        restQuestionAnswerMockMvc.perform(delete("/api/question-answers/{id}", questionAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean questionAnswerExistsInEs = questionAnswerSearchRepository.exists(questionAnswer.getId());
        assertThat(questionAnswerExistsInEs).isFalse();

        // Validate the database is empty
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchQuestionAnswer() throws Exception {
        // Initialize the database
        questionAnswerService.save(questionAnswer);

        // Search the questionAnswer
        restQuestionAnswerMockMvc.perform(get("/api/_search/question-answers?query=id:" + questionAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionTitle").value(hasItem(DEFAULT_QUESTION_TITLE.toString())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].correctAnswer").value(hasItem(DEFAULT_CORRECT_ANSWER.toString())))
            .andExpect(jsonPath("$.[*].wrongAnswers").value(hasItem(DEFAULT_WRONG_ANSWERS.toString())))
            .andExpect(jsonPath("$.[*].catagory").value(hasItem(DEFAULT_CATAGORY.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionAnswer.class);
        QuestionAnswer questionAnswer1 = new QuestionAnswer();
        questionAnswer1.setId(1L);
        QuestionAnswer questionAnswer2 = new QuestionAnswer();
        questionAnswer2.setId(questionAnswer1.getId());
        assertThat(questionAnswer1).isEqualTo(questionAnswer2);
        questionAnswer2.setId(2L);
        assertThat(questionAnswer1).isNotEqualTo(questionAnswer2);
        questionAnswer1.setId(null);
        assertThat(questionAnswer1).isNotEqualTo(questionAnswer2);
    }
}
