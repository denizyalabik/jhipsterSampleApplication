import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AcademiaUsers } from './academia-users.model';
import { AcademiaUsersPopupService } from './academia-users-popup.service';
import { AcademiaUsersService } from './academia-users.service';

@Component({
    selector: 'jhi-academia-users-delete-dialog',
    templateUrl: './academia-users-delete-dialog.component.html'
})
export class AcademiaUsersDeleteDialogComponent {

    academiaUsers: AcademiaUsers;

    constructor(
        private academiaUsersService: AcademiaUsersService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.academiaUsersService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'academiaUsersListModification',
                content: 'Deleted an academiaUsers'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-academia-users-delete-popup',
    template: ''
})
export class AcademiaUsersDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private academiaUsersPopupService: AcademiaUsersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.academiaUsersPopupService
                .open(AcademiaUsersDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
