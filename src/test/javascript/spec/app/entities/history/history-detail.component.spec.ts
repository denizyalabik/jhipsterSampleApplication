/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HistoryDetailComponent } from '../../../../../../main/webapp/app/entities/history/history-detail.component';
import { HistoryService } from '../../../../../../main/webapp/app/entities/history/history.service';
import { History } from '../../../../../../main/webapp/app/entities/history/history.model';

describe('Component Tests', () => {

    describe('History Management Detail Component', () => {
        let comp: HistoryDetailComponent;
        let fixture: ComponentFixture<HistoryDetailComponent>;
        let service: HistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [HistoryDetailComponent],
                providers: [
                    HistoryService
                ]
            })
            .overrideTemplate(HistoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new History(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.history).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
