import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { QuestionAnswer } from './question-answer.model';
import { QuestionAnswerService } from './question-answer.service';

@Component({
    selector: 'jhi-question-answer-detail',
    templateUrl: './question-answer-detail.component.html'
})
export class QuestionAnswerDetailComponent implements OnInit, OnDestroy {

    questionAnswer: QuestionAnswer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private questionAnswerService: QuestionAnswerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInQuestionAnswers();
    }

    load(id) {
        this.questionAnswerService.find(id)
            .subscribe((questionAnswerResponse: HttpResponse<QuestionAnswer>) => {
                this.questionAnswer = questionAnswerResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInQuestionAnswers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'questionAnswerListModification',
            (response) => this.load(this.questionAnswer.id)
        );
    }
}
