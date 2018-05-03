import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { QuestionAnswer } from './question-answer.model';
import { QuestionAnswerPopupService } from './question-answer-popup.service';
import { QuestionAnswerService } from './question-answer.service';

@Component({
    selector: 'jhi-question-answer-delete-dialog',
    templateUrl: './question-answer-delete-dialog.component.html'
})
export class QuestionAnswerDeleteDialogComponent {

    questionAnswer: QuestionAnswer;

    constructor(
        private questionAnswerService: QuestionAnswerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.questionAnswerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'questionAnswerListModification',
                content: 'Deleted an questionAnswer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-question-answer-delete-popup',
    template: ''
})
export class QuestionAnswerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private questionAnswerPopupService: QuestionAnswerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.questionAnswerPopupService
                .open(QuestionAnswerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
