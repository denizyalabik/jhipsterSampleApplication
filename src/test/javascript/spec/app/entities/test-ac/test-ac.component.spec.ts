/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TestAcComponent } from '../../../../../../main/webapp/app/entities/test-ac/test-ac.component';
import { TestAcService } from '../../../../../../main/webapp/app/entities/test-ac/test-ac.service';
import { TestAc } from '../../../../../../main/webapp/app/entities/test-ac/test-ac.model';

describe('Component Tests', () => {

    describe('TestAc Management Component', () => {
        let comp: TestAcComponent;
        let fixture: ComponentFixture<TestAcComponent>;
        let service: TestAcService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TestAcComponent],
                providers: [
                    TestAcService
                ]
            })
            .overrideTemplate(TestAcComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TestAcComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestAcService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TestAc(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.testAcs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
