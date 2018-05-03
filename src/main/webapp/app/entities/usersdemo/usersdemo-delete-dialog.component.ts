import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Usersdemo } from './usersdemo.model';
import { UsersdemoPopupService } from './usersdemo-popup.service';
import { UsersdemoService } from './usersdemo.service';

@Component({
    selector: 'jhi-usersdemo-delete-dialog',
    templateUrl: './usersdemo-delete-dialog.component.html'
})
export class UsersdemoDeleteDialogComponent {

    usersdemo: Usersdemo;

    constructor(
        private usersdemoService: UsersdemoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.usersdemoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'usersdemoListModification',
                content: 'Deleted an usersdemo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-usersdemo-delete-popup',
    template: ''
})
export class UsersdemoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private usersdemoPopupService: UsersdemoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.usersdemoPopupService
                .open(UsersdemoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
