import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TestAc } from './test-ac.model';
import { TestAcPopupService } from './test-ac-popup.service';
import { TestAcService } from './test-ac.service';

@Component({
    selector: 'jhi-test-ac-delete-dialog',
    templateUrl: './test-ac-delete-dialog.component.html'
})
export class TestAcDeleteDialogComponent {

    testAc: TestAc;

    constructor(
        private testAcService: TestAcService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.testAcService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'testAcListModification',
                content: 'Deleted an testAc'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-test-ac-delete-popup',
    template: ''
})
export class TestAcDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private testAcPopupService: TestAcPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.testAcPopupService
                .open(TestAcDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
