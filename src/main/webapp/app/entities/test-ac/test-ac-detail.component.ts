import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TestAc } from './test-ac.model';
import { TestAcService } from './test-ac.service';

@Component({
    selector: 'jhi-test-ac-detail',
    templateUrl: './test-ac-detail.component.html'
})
export class TestAcDetailComponent implements OnInit, OnDestroy {

    testAc: TestAc;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private testAcService: TestAcService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTestAcs();
    }

    load(id) {
        this.testAcService.find(id)
            .subscribe((testAcResponse: HttpResponse<TestAc>) => {
                this.testAc = testAcResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTestAcs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'testAcListModification',
            (response) => this.load(this.testAc.id)
        );
    }
}
