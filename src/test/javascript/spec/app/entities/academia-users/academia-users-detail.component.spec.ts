/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AcademiaUsersDetailComponent } from '../../../../../../main/webapp/app/entities/academia-users/academia-users-detail.component';
import { AcademiaUsersService } from '../../../../../../main/webapp/app/entities/academia-users/academia-users.service';
import { AcademiaUsers } from '../../../../../../main/webapp/app/entities/academia-users/academia-users.model';

describe('Component Tests', () => {

    describe('AcademiaUsers Management Detail Component', () => {
        let comp: AcademiaUsersDetailComponent;
        let fixture: ComponentFixture<AcademiaUsersDetailComponent>;
        let service: AcademiaUsersService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [AcademiaUsersDetailComponent],
                providers: [
                    AcademiaUsersService
                ]
            })
            .overrideTemplate(AcademiaUsersDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AcademiaUsersDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcademiaUsersService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AcademiaUsers(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.academiaUsers).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
