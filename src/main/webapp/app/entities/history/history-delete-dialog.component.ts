import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { History } from './history.model';
import { HistoryPopupService } from './history-popup.service';
import { HistoryService } from './history.service';

@Component({
    selector: 'jhi-history-delete-dialog',
    templateUrl: './history-delete-dialog.component.html'
})
export class HistoryDeleteDialogComponent {

    history: History;

    constructor(
        private historyService: HistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.historyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'historyListModification',
                content: 'Deleted an history'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-history-delete-popup',
    template: ''
})
export class HistoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private historyPopupService: HistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.historyPopupService
                .open(HistoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
