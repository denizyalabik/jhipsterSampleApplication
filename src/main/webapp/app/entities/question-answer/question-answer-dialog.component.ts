import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { QuestionAnswer } from './question-answer.model';
import { QuestionAnswerPopupService } from './question-answer-popup.service';
import { QuestionAnswerService } from './question-answer.service';
import { Usersdemo, UsersdemoService } from '../usersdemo';

@Component({
    selector: 'jhi-question-answer-dialog',
    templateUrl: './question-answer-dialog.component.html'
})
export class QuestionAnswerDialogComponent implements OnInit {

    questionAnswer: QuestionAnswer;
    isSaving: boolean;

    usersdemos: Usersdemo[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private questionAnswerService: QuestionAnswerService,
        private usersdemoService: UsersdemoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.usersdemoService.query()
            .subscribe((res: HttpResponse<Usersdemo[]>) => { this.usersdemos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.questionAnswer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.questionAnswerService.update(this.questionAnswer));
        } else {
            this.subscribeToSaveResponse(
                this.questionAnswerService.create(this.questionAnswer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<QuestionAnswer>>) {
        result.subscribe((res: HttpResponse<QuestionAnswer>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: QuestionAnswer) {
        this.eventManager.broadcast({ name: 'questionAnswerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUsersdemoById(index: number, item: Usersdemo) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-question-answer-popup',
    template: ''
})
export class QuestionAnswerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private questionAnswerPopupService: QuestionAnswerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.questionAnswerPopupService
                    .open(QuestionAnswerDialogComponent as Component, params['id']);
            } else {
                this.questionAnswerPopupService
                    .open(QuestionAnswerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
