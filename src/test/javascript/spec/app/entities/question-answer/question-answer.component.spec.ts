/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { QuestionAnswerComponent } from '../../../../../../main/webapp/app/entities/question-answer/question-answer.component';
import { QuestionAnswerService } from '../../../../../../main/webapp/app/entities/question-answer/question-answer.service';
import { QuestionAnswer } from '../../../../../../main/webapp/app/entities/question-answer/question-answer.model';

describe('Component Tests', () => {

    describe('QuestionAnswer Management Component', () => {
        let comp: QuestionAnswerComponent;
        let fixture: ComponentFixture<QuestionAnswerComponent>;
        let service: QuestionAnswerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [QuestionAnswerComponent],
                providers: [
                    QuestionAnswerService
                ]
            })
            .overrideTemplate(QuestionAnswerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuestionAnswerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionAnswerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new QuestionAnswer(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.questionAnswers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
