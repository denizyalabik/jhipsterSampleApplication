import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AcademiaUsers } from './academia-users.model';
import { AcademiaUsersPopupService } from './academia-users-popup.service';
import { AcademiaUsersService } from './academia-users.service';

@Component({
    selector: 'jhi-academia-users-dialog',
    templateUrl: './academia-users-dialog.component.html'
})
export class AcademiaUsersDialogComponent implements OnInit {

    academiaUsers: AcademiaUsers;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private academiaUsersService: AcademiaUsersService,
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
        if (this.academiaUsers.id !== undefined) {
            this.subscribeToSaveResponse(
                this.academiaUsersService.update(this.academiaUsers));
        } else {
            this.subscribeToSaveResponse(
                this.academiaUsersService.create(this.academiaUsers));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AcademiaUsers>>) {
        result.subscribe((res: HttpResponse<AcademiaUsers>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AcademiaUsers) {
        this.eventManager.broadcast({ name: 'academiaUsersListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-academia-users-popup',
    template: ''
})
export class AcademiaUsersPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private academiaUsersPopupService: AcademiaUsersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.academiaUsersPopupService
                    .open(AcademiaUsersDialogComponent as Component, params['id']);
            } else {
                this.academiaUsersPopupService
                    .open(AcademiaUsersDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
