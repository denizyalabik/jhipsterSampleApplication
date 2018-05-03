/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { UsersdemoComponent } from '../../../../../../main/webapp/app/entities/usersdemo/usersdemo.component';
import { UsersdemoService } from '../../../../../../main/webapp/app/entities/usersdemo/usersdemo.service';
import { Usersdemo } from '../../../../../../main/webapp/app/entities/usersdemo/usersdemo.model';

describe('Component Tests', () => {

    describe('Usersdemo Management Component', () => {
        let comp: UsersdemoComponent;
        let fixture: ComponentFixture<UsersdemoComponent>;
        let service: UsersdemoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [UsersdemoComponent],
                providers: [
                    UsersdemoService
                ]
            })
            .overrideTemplate(UsersdemoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UsersdemoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UsersdemoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Usersdemo(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.usersdemos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
