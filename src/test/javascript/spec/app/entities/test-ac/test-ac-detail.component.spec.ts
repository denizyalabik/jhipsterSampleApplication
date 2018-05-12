/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TestAcDetailComponent } from '../../../../../../main/webapp/app/entities/test-ac/test-ac-detail.component';
import { TestAcService } from '../../../../../../main/webapp/app/entities/test-ac/test-ac.service';
import { TestAc } from '../../../../../../main/webapp/app/entities/test-ac/test-ac.model';

describe('Component Tests', () => {

    describe('TestAc Management Detail Component', () => {
        let comp: TestAcDetailComponent;
        let fixture: ComponentFixture<TestAcDetailComponent>;
        let service: TestAcService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TestAcDetailComponent],
                providers: [
                    TestAcService
                ]
            })
            .overrideTemplate(TestAcDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TestAcDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestAcService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TestAc(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.testAc).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
