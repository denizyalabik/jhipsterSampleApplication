import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TestAc } from './test-ac.model';
import { TestAcPopupService } from './test-ac-popup.service';
import { TestAcService } from './test-ac.service';
import { QuestionAnswer, QuestionAnswerService } from '../question-answer';
import { AcademiaUsers, AcademiaUsersService } from '../academia-users';

@Component({
    selector: 'jhi-test-ac-dialog',
    templateUrl: './test-ac-dialog.component.html'
})
export class TestAcDialogComponent implements OnInit {

    testAc: TestAc;
    isSaving: boolean;

    questionanswers: QuestionAnswer[];

    academiausers: AcademiaUsers[];
    createdTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private testAcService: TestAcService,
        private questionAnswerService: QuestionAnswerService,
        private academiaUsersService: AcademiaUsersService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.questionAnswerService.query()
            .subscribe((res: HttpResponse<QuestionAnswer[]>) => { this.questionanswers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.academiaUsersService.query()
            .subscribe((res: HttpResponse<AcademiaUsers[]>) => { this.academiausers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.testAc.id !== undefined) {
            this.subscribeToSaveResponse(
                this.testAcService.update(this.testAc));
        } else {
            this.subscribeToSaveResponse(
                this.testAcService.create(this.testAc));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TestAc>>) {
        result.subscribe((res: HttpResponse<TestAc>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TestAc) {
        this.eventManager.broadcast({ name: 'testAcListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackQuestionAnswerById(index: number, item: QuestionAnswer) {
        return item.id;
    }

    trackAcademiaUsersById(index: number, item: AcademiaUsers) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-test-ac-popup',
    template: ''
})
export class TestAcPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private testAcPopupService: TestAcPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.testAcPopupService
                    .open(TestAcDialogComponent as Component, params['id']);
            } else {
                this.testAcPopupService
                    .open(TestAcDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
