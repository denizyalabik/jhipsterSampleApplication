/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { QuestionAnswerDetailComponent } from '../../../../../../main/webapp/app/entities/question-answer/question-answer-detail.component';
import { QuestionAnswerService } from '../../../../../../main/webapp/app/entities/question-answer/question-answer.service';
import { QuestionAnswer } from '../../../../../../main/webapp/app/entities/question-answer/question-answer.model';

describe('Component Tests', () => {

    describe('QuestionAnswer Management Detail Component', () => {
        let comp: QuestionAnswerDetailComponent;
        let fixture: ComponentFixture<QuestionAnswerDetailComponent>;
        let service: QuestionAnswerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [QuestionAnswerDetailComponent],
                providers: [
                    QuestionAnswerService
                ]
            })
            .overrideTemplate(QuestionAnswerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuestionAnswerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionAnswerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new QuestionAnswer(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.questionAnswer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
