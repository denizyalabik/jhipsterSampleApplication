import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { History } from './history.model';
import { HistoryService } from './history.service';

@Component({
    selector: 'jhi-history-detail',
    templateUrl: './history-detail.component.html'
})
export class HistoryDetailComponent implements OnInit, OnDestroy {

    history: History;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private historyService: HistoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHistories();
    }

    load(id) {
        this.historyService.find(id)
            .subscribe((historyResponse: HttpResponse<History>) => {
                this.history = historyResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHistories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'historyListModification',
            (response) => this.load(this.history.id)
        );
    }
}
