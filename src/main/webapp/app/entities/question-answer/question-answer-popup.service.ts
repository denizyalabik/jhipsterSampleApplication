import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { QuestionAnswer } from './question-answer.model';
import { QuestionAnswerService } from './question-answer.service';

@Injectable()
export class QuestionAnswerPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private questionAnswerService: QuestionAnswerService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.questionAnswerService.find(id)
                    .subscribe((questionAnswerResponse: HttpResponse<QuestionAnswer>) => {
                        const questionAnswer: QuestionAnswer = questionAnswerResponse.body;
                        this.ngbModalRef = this.questionAnswerModalRef(component, questionAnswer);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.questionAnswerModalRef(component, new QuestionAnswer());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    questionAnswerModalRef(component: Component, questionAnswer: QuestionAnswer): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.questionAnswer = questionAnswer;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
