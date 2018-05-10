import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { History } from './history.model';
import { HistoryPopupService } from './history-popup.service';
import { HistoryService } from './history.service';
import { QuestionAnswer, QuestionAnswerService } from '../question-answer';
import { AcademiaUsers, AcademiaUsersService } from '../academia-users';

@Component({
    selector: 'jhi-history-dialog',
    templateUrl: './history-dialog.component.html'
})
export class HistoryDialogComponent implements OnInit {

    history: History;
    isSaving: boolean;

    questionanswers: QuestionAnswer[];

    academiausers: AcademiaUsers[];
    solvedDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private historyService: HistoryService,
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
        if (this.history.id !== undefined) {
            this.subscribeToSaveResponse(
                this.historyService.update(this.history));
        } else {
            this.subscribeToSaveResponse(
                this.historyService.create(this.history));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<History>>) {
        result.subscribe((res: HttpResponse<History>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: History) {
        this.eventManager.broadcast({ name: 'historyListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-history-popup',
    template: ''
})
export class HistoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private historyPopupService: HistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.historyPopupService
                    .open(HistoryDialogComponent as Component, params['id']);
            } else {
                this.historyPopupService
                    .open(HistoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
