/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AcademiaUsersComponent } from '../../../../../../main/webapp/app/entities/academia-users/academia-users.component';
import { AcademiaUsersService } from '../../../../../../main/webapp/app/entities/academia-users/academia-users.service';
import { AcademiaUsers } from '../../../../../../main/webapp/app/entities/academia-users/academia-users.model';

describe('Component Tests', () => {

    describe('AcademiaUsers Management Component', () => {
        let comp: AcademiaUsersComponent;
        let fixture: ComponentFixture<AcademiaUsersComponent>;
        let service: AcademiaUsersService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [AcademiaUsersComponent],
                providers: [
                    AcademiaUsersService
                ]
            })
            .overrideTemplate(AcademiaUsersComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AcademiaUsersComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcademiaUsersService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AcademiaUsers(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.academiaUsers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
