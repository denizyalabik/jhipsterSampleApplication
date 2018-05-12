/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { HistoryComponent } from '../../../../../../main/webapp/app/entities/history/history.component';
import { HistoryService } from '../../../../../../main/webapp/app/entities/history/history.service';
import { History } from '../../../../../../main/webapp/app/entities/history/history.model';

describe('Component Tests', () => {

    describe('History Management Component', () => {
        let comp: HistoryComponent;
        let fixture: ComponentFixture<HistoryComponent>;
        let service: HistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [HistoryComponent],
                providers: [
                    HistoryService
                ]
            })
            .overrideTemplate(HistoryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HistoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new History(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.histories[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
