import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Usersdemo } from './usersdemo.model';
import { UsersdemoPopupService } from './usersdemo-popup.service';
import { UsersdemoService } from './usersdemo.service';

@Component({
    selector: 'jhi-usersdemo-dialog',
    templateUrl: './usersdemo-dialog.component.html'
})
export class UsersdemoDialogComponent implements OnInit {

    usersdemo: Usersdemo;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private usersdemoService: UsersdemoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.usersdemo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.usersdemoService.update(this.usersdemo));
        } else {
            this.subscribeToSaveResponse(
                this.usersdemoService.create(this.usersdemo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Usersdemo>>) {
        result.subscribe((res: HttpResponse<Usersdemo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Usersdemo) {
        this.eventManager.broadcast({ name: 'usersdemoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-usersdemo-popup',
    template: ''
})
export class UsersdemoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private usersdemoPopupService: UsersdemoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.usersdemoPopupService
                    .open(UsersdemoDialogComponent as Component, params['id']);
            } else {
                this.usersdemoPopupService
                    .open(UsersdemoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
