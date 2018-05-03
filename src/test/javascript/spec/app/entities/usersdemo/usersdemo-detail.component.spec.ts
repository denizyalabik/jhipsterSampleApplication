/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { UsersdemoDetailComponent } from '../../../../../../main/webapp/app/entities/usersdemo/usersdemo-detail.component';
import { UsersdemoService } from '../../../../../../main/webapp/app/entities/usersdemo/usersdemo.service';
import { Usersdemo } from '../../../../../../main/webapp/app/entities/usersdemo/usersdemo.model';

describe('Component Tests', () => {

    describe('Usersdemo Management Detail Component', () => {
        let comp: UsersdemoDetailComponent;
        let fixture: ComponentFixture<UsersdemoDetailComponent>;
        let service: UsersdemoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [UsersdemoDetailComponent],
                providers: [
                    UsersdemoService
                ]
            })
            .overrideTemplate(UsersdemoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UsersdemoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UsersdemoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Usersdemo(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.usersdemo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
